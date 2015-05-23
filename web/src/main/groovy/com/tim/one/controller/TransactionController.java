package com.tim.one.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.tim.one.bean.PaymentType;
import com.tim.one.bean.PurchaseTransactionBean;
import com.tim.one.bean.TransactionBean;
import com.tim.one.bean.TransferUserLimitBean;
import com.tim.one.command.CapitalReturnCommand;
import com.tim.one.command.TransactionCommand;
import com.tim.one.command.ContributeProducerCommand;
import com.tim.one.command.DeleteLimitAmountCommand;
import com.tim.one.command.MaxAmountCommand;
import com.tim.one.command.ProducerFundingCommand;
import com.tim.one.command.ProviderPartnershipCommand;
import com.tim.one.command.TransferFundsCommand;
import com.tim.one.command.TransactionUserStatementCommand;
import com.tim.one.exception.AdvanceProviderPaidException;
import com.tim.one.exception.MaxTransferLimitExceedException;
import com.tim.one.exception.NonProjectProviderException;
import com.tim.one.exception.NonSufficientFundsException;
import com.tim.one.exception.NotBulkTransactionFoundExeption;
import com.tim.one.exception.NotValidParameterException;
import com.tim.one.exception.SettlementProviderPaidException;
import com.tim.one.exception.TransferUserLimitNotExistException;
import com.tim.one.exception.UserNotFoundException;
import com.tim.one.helper.TransactionManagerHelper;
import com.tim.one.manager.TransactionFreakfundManager;
import com.tim.one.model.UserTx;
import com.tim.one.service.SecurityService;
import com.tim.one.service.TransactionService;
import com.tim.one.state.ErrorState;
import com.tim.one.state.ResponseState;
import com.tim.one.status.TimoneErrorStatus;
import com.tim.one.util.DateUtil;

/**
 * @author josdem
 * @understands A class who knows how to manage trama and freakfund transactions
 */

@Controller
@RequestMapping("/tx/*")
public class TransactionController {

  @Autowired
  private SecurityService securityService;
  @Autowired
  private TransactionFreakfundManager transactionManager;
  @Autowired
  private TransactionService transactionService;
  @Autowired
  private TransactionManagerHelper transactionManagerHelper;
  @Autowired
  private DateUtil dateUtil;

  private Log log = LogFactory.getLog(getClass());

  @RequestMapping(method = GET, value = "/get/transaction/{transactionId}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<PurchaseTransactionBean> getTransaction(TransactionCommand command) {
    log.info("LISTING transaction by id: " + command.getTransactionId());
    try {
      return transactionService.getTransactions(command.getTransactionId());
    } catch (NotBulkTransactionFoundExeption nte) {
      log.warn(nte, nte);
    }
    return new ArrayList<PurchaseTransactionBean>();
  }

  @RequestMapping(method = GET, value = "/get/userTransaction/{transactionId}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public UserTx getUserTransaction(TransactionCommand command) {
    log.info("LISTING User's transaction w/id: " + command.getTransactionId());
    return transactionService.getUserTransaction(command.getTransactionId());
  }

  @RequestMapping(method = GET, value = "/getLimitAmountsToTransfer/{userId}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<TransferUserLimitBean> getLimitAmountsToTransfer(TransactionCommand command) {
    log.info("LISTING transaction's limit amounts by userId: " + command.getUserId());
    return transactionService.getLimitAmountsToTransfer(command.getUserId());
  }

  @RequestMapping(method = GET, value = "/getUserStatement/{userId}/{startDate}/{endDate}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<TransactionBean> getUserStatement(TransactionUserStatementCommand command) {
    log.info("LISTING user's transactions by userId: " + command.getUserId() + " BETWEEN: " + command.getStartDate() + " AND " + command.getEndDate());
    try {
      Long start = dateUtil.dateStartFormat(command.getStartDate());
      Long end = dateUtil.dateEndFormat(command.getEndDate());
      List<TransactionBean> transactionBeans = transactionManagerHelper.getUserTransactionBeans(command.getUserId(), start, end);
      BigDecimal startingBalance = transactionManager.getStartingBalance(command.getUserId(), command.getStartDate());
      return transactionService.setBalances(transactionBeans, startingBalance);
    } catch (ParseException pse) {
      log.warn(pse, pse);
    }
    return new ArrayList<TransactionBean>();
  }

  @RequestMapping(method = GET, value = "/getProjectStatement/{projectId}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<TransactionBean> getProjectStatement(TransactionCommand command) {
    log.info("LISTING project's transactions by projectId: " + command.getProjectId());
    List<TransactionBean> transactionBeans = transactionManagerHelper.getProjectTransactionBeans(command.getProjectId());
    return transactionService.setBalances(transactionBeans, new BigDecimal("0"));
  }

  @RequestMapping(method = GET, value = "/getBulkTransactions/{bulkId}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<TransactionBean> getBulkTransactions(TransactionCommand command) {
    log.info("LISTING bulks's transactions by bulkId: " + command.getBulkId());
    return transactionService.getBulkTransactions(command.getBulkId());
  }
  
  @RequestMapping(method = POST, value = "/transferFunds")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public void transferFunds(@Valid TransferFundsCommand command, HttpServletResponse response) throws IOException {
    log.info("TRYING save user's transfer: " + command.getSenderId());

    if (securityService.isValid(command.getToken())) {
      try {
        Integer transactionId = transactionService.transferFunds(command.getSenderId(), command.getReceiverId(), command.getAmount());
        log.info("SAVED User's transfer senderId: " + command.getSenderId() + " " + "receiverId: " + command.getReceiverId());
        response.sendRedirect(command.getCallback() + ResponseState.getResponse(transactionId));
      } catch (UserNotFoundException une) {
        log.warn(une, une);
        response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.USER_NOT_FOUND));
      } catch (NonSufficientFundsException nfe) {
        log.warn(nfe, nfe);
        response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.NON_SUFFICIENT_FUNDS));
      } catch (NotValidParameterException nvp) {
        log.warn(nvp, nvp);
        response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.INCORRECT_PARAMETER_REQUEST));
      } catch (MaxTransferLimitExceedException mtle) {
        log.warn(mtle, mtle);
        response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.TRANSFER_AMOUNT_EXCEED));
      }
    } else {
      response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
    }
  }
  
  @RequestMapping(method = POST, value = "/producerFunding")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public void producerFunding(@Valid ProducerFundingCommand command, HttpServletResponse response ) throws IOException {

    log.info("CALLING Producer's funding");

    if (securityService.isValid(command.getToken())) {
      log.info("SAVING Producer's funding");
      try {
        transactionService.createProducerFunding(command.getProjectId(), command.getAmount());
        response.sendRedirect(command.getCallback());
      } catch (UserNotFoundException une) {
        log.warn(une, une);
        response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.USER_NOT_FOUND));
      } catch (NonSufficientFundsException nfe) {
        log.warn(nfe, nfe);
        response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.NON_SUFFICIENT_FUNDS));
      }
    } else {
      response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
    }
  }

  @RequestMapping(method = POST, value = "/maxAmountToTransfer")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public String maxAmountToTransfer(@Valid MaxAmountCommand command, HttpServletResponse response) throws URISyntaxException {

    response.addHeader("Allow-Control-Allow-Methods", "POST");
    response.addHeader("Access-Control-Allow-Origin", "*");

    log.info("CALLING user's maximum amount transfer limit");

    if (securityService.isValid(command.getToken())) {
      log.info("SAVING user's maximum amount transfer limit");
      try {
        Integer transactionId = transactionService.measureAmountToTransfer(command.getUserId(), command.getDestinationId(), command.getAmount());
        return ResponseState.getJsonResponse(transactionId);
      } catch (UserNotFoundException une) {
        log.warn(une, une);
        return ErrorState.getJsonErrorCode(TimoneErrorStatus.USER_NOT_FOUND);
      } catch (NotValidParameterException nvp) {
        log.warn(nvp, nvp);
        return ErrorState.getJsonErrorCode(TimoneErrorStatus.INCORRECT_PARAMETER_REQUEST);
      }
    } else {
      return ErrorState.getJsonErrorCode(TimoneErrorStatus.TOKEN_EXPIRED);
    }
  }
  
  @RequestMapping(method = POST, value = "/providerPartnership")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public void saveProviderPartnership(@Valid ProviderPartnershipCommand command, HttpServletResponse response ) throws IOException {

    log.info("CALLING Provider's partnership");

    if (securityService.isValid(command.getToken())) {
      log.info("SAVING Provider's partnership");

      try {
        transactionService.createProviderPartnership(command.getProviderId(), command.getProjectId(), PaymentType.getTypeByCode(command.getType()));
        response.sendRedirect(command.getCallback());
      } catch (NonProjectProviderException nppe) {
        response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.PROVIDER_NOT_FOUND));
      } catch (AdvanceProviderPaidException appe) {
        response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.ADVANCE_PAYMENT_ALREADY_PAID));
      } catch (SettlementProviderPaidException sppe) {
        response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.SETTLEMENT_ALREADY_PAID));
      }
    } else {
      response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
    }
  }
  
  @RequestMapping(method = POST, value = "/deleteLimitAmountToTransfer")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public String deleteLimitAmountToTransfer(@Valid DeleteLimitAmountCommand command, HttpServletResponse response) throws URISyntaxException {

    response.addHeader("Allow-Control-Allow-Methods", "POST");
    response.addHeader("Access-Control-Allow-Origin", "*");

    log.info("CALLING delete maximum amount transfer limit");

    if (securityService.isValid(command.getToken())) {
      log.info("DELETING maximum amount transfer limit");
      try{
        transactionService.changeLimitAmountToTransfer(command.getUserId(), command.getDestinationId());
        return ResponseState.getJsonResponse(200);
      } catch (TransferUserLimitNotExistException tul){
        log.error(tul, tul);
        return ErrorState.getJsonErrorCode(TimoneErrorStatus.NOT_LIMIT_AMOUNT_REGISTER);
      }
    } else {
      return ErrorState.getJsonErrorCode(TimoneErrorStatus.TOKEN_EXPIRED);
    }
  }
  
  @RequestMapping(method = POST, value = "/contributeProducerRevenue")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public void contributeProducerRevenue(@Valid ContributeProducerCommand command, HttpServletResponse response) throws IOException {

    log.info("CALLING Producer's partnership");

    if (securityService.isValid(command.getToken())) {
      log.info("SAVING Producer's partnership");
      try {
        transactionService.createProducerPartnership(command.getProjectId());
        response.sendRedirect(command.getCallback());
      } catch (UserNotFoundException une) {
        log.warn(une, une);
        response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.USER_NOT_FOUND));
      }
    } else {
      response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
    }
  }

  @RequestMapping(method = POST, value = "/returnOfCapitalInjection")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public void returnOfCapitalInjection(@Valid CapitalReturnCommand command, HttpServletResponse response ) throws IOException {

    log.info("CALLING return of capital injection");

    if (securityService.isValid(command.getToken())) {
      log.info("SAVING return of capital injection");
      try {
        transactionService.createReturnOfCapitalInjection(command.getProjectId(), command.getProviderId(), command.getAmount());
        response.sendRedirect(command.getCallback());
      } catch (NonSufficientFundsException nsfe) {
        log.warn(nsfe, nsfe);
        response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.NON_SUFFICIENT_FUNDS));
      }
    } else {
      response.sendRedirect(command.getCallback() + ErrorState.getErrorCode(TimoneErrorStatus.TOKEN_EXPIRED));
    }
  }

}
