package com.tim.one.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.gson.Gson;
import com.tim.one.exception.BusinessException;
import com.tim.one.integra.command.IntegraCashinCommand;
import com.tim.one.integra.command.IntegraTransferFundsCommand;
import com.tim.one.integra.command.IntegraUserCommand;
import com.tim.one.integra.command.IntegraUserTxCommand;
import com.tim.one.model.IntegraUserTx;
import com.tim.one.service.IntegraTransactionService;
import com.tim.one.util.DateUtil;
import com.tim.one.validator.CommandValidator;

/**
 * @author josdem
 * @understands A class who knows how to manage integradora transactions
 */

@Controller
@RequestMapping("/integra/tx/*")
public class IntegraTransactionController {

	@Autowired
	private IntegraTransactionService service;
	@Autowired 
	private CommandValidator validator;
	@Autowired
  private DateUtil dateUtil;

	private Log log = LogFactory.getLog(getClass());

	@RequestMapping(method = GET, value = "/{uuid}")
	@ResponseBody
	public IntegraUserTx getByIntegraUuid(IntegraUserCommand command){
		log.info("LISTING User's transaction by uuid:" + command.getUuid());
		return service.getTransactionByUuid(command.getUuid());
	}
	
	@RequestMapping(method = GET, value = "/getTransaction/{uuid}")
	@ResponseBody
	public List<IntegraUserTx> getTransactionByUuid(IntegraUserCommand command){
		log.info("LISTING User transactions by user uuid:" + command.getUuid());
		return service.getTransactionsByUuid(command.getUuid());
	}
	
	@RequestMapping(method = GET, value = "/getTransactions/{uuid}/{startDate}/{endDate}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<IntegraUserTx> getUserStatement(IntegraUserTxCommand command) {
    log.info("LISTING user's transactions by uuid: " + command.getUuid() + " BETWEEN: " + command.getStartDate() + " AND " + command.getEndDate());
    try {
      Long start = dateUtil.dateStartFormat(command.getStartDate());
      Long end = dateUtil.dateEndFormat(command.getEndDate());
      return service.getTransactionsByUuidAndDate(command.getUuid(), start, end);
    } catch (ParseException pse) {
      log.warn(pse, pse);
    }
    return new ArrayList<IntegraUserTx>();
  }
	
	@RequestMapping(method = POST, value = "/transferFunds")
	public ResponseEntity<String> transferFunds(@RequestBody String json){
		IntegraTransferFundsCommand command = new Gson().fromJson(json, IntegraTransferFundsCommand.class);
		log.info("TRANSFER FUNDS from: " + command.getUuidOrigin() + " to: " + command.getUuidDestination());
		try{
			if(!validator.isValid(command)){
				return new ResponseEntity<String>("Validation error", HttpStatus.BAD_REQUEST);
			}
			String transferId = service.transfer(command.getUuidOrigin(), command.getUuidDestination(), command.getAmount());
			return new ResponseEntity<String>(transferId, HttpStatus.OK);
		}  catch (BusinessException be) {
			return new ResponseEntity<String>(be.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}
	
	@RequestMapping(method = POST, value = "/cashin")
	public ResponseEntity<String> cashin(@RequestBody String json){
		IntegraCashinCommand command = new Gson().fromJson(json, IntegraCashinCommand.class);
		log.info("Cashin to user w/uuid: " + command.getUuid() + " by: " + command.getAmount());
		try{
			if(!validator.isValid(command)){
				return new ResponseEntity<String>("Validation error", HttpStatus.BAD_REQUEST);
			}
			String transactionUuid = service.cashIn(command.getUuid(), command.getAmount());
			return new ResponseEntity<String>(transactionUuid, HttpStatus.OK);
		}  catch (BusinessException be) {
			return new ResponseEntity<String>(be.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}

}
