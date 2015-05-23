package com.tim.one.collaborator;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.tim.one.bean.MessageType;
import com.tim.one.bean.ProjectTxType;
import com.tim.one.bean.TransactionType;
import com.tim.one.exception.NonSufficientFundsException;
import com.tim.one.model.Project;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.ProjectUnitSale;
import com.tim.one.model.UnitTx;
import com.tim.one.model.User;
import com.tim.one.packer.MessagePacker;
import com.tim.one.repository.ProjectFinancialDataRepository;
import com.tim.one.repository.ProjectRepository;
import com.tim.one.repository.ProjectUnitSaleRepository;
import com.tim.one.repository.UserRepository;
import com.tim.one.service.TransactionLogService;
import com.tim.one.state.ApplicationState;

@Component
public class RevertCollaborator {
	
	@Autowired
	private MessagePacker messagePacker;
	@Autowired
	private UnitCollaborator unitCollaborator;
	@Autowired
	private TransactionLogService transactionLogService;
	@Autowired
	@Qualifier("properties")
	private Properties properties;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
  private ProjectUnitSaleRepository projectUnitSaleRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProjectFinancialDataRepository projectFinancialDataRepository;

	public Set<User> revertTransactions(Project project, Integer reason) throws NonSufficientFundsException {
		ProjectFinancialData projectFinancialData = project.getProjectFinancialData();
		List<UnitTx> units = unitCollaborator.getUnitsByProjectAndType(projectFinancialData, TransactionType.FUNDING);
		Set<User> users = new HashSet<User>();
		
		for (UnitTx unitTx : units) {
			ProjectUnitSale projectUnitSale = projectUnitSaleRepository.findOne(unitTx.getProjectUnitSaleId());
			BigDecimal amount = projectUnitSale.getUnitSale().multiply(new BigDecimal(unitTx.getQuantity()));
			
			if (projectFinancialData.getBalance().subtract(amount).compareTo(new BigDecimal("0")) == -1){
				throw new NonSufficientFundsException(projectFinancialData.getId());
			}
			
			projectFinancialData.setBalance(projectFinancialData.getBalance().subtract(amount));
			projectFinancialDataRepository.save(projectFinancialData);

			User user = userRepository.findOne(unitTx.getUserId());
			user.setBalance(user.getBalance().add(amount));
			userRepository.save(user);
			users.add(user);
			
			//Transaction logs creation 
			Integer transactionId = transactionLogService.createUserLog(null, user.getId(), user.getId(), amount, null, TransactionType.RETURN_FUNDS);
			transactionLogService.createProjectLog(projectUnitSale.getProjectFinancialData().getId(), user.getId(), amount, ProjectTxType.RETURN_FUNDS);
			
			if(reason.equals(new Integer(properties.getProperty(ApplicationState.NON_DELIVERY_PRODUCT_REASON)))){
				messagePacker.sendAbonoCuenta(amount, project.getName(), user.getEmail(), transactionId, MessageType.RETORNO_FONDOS_NO_ENTREGA_PRODUCTO);
			} 
			if(reason.equals(new Integer(properties.getProperty(ApplicationState.INCOMPLETE_DOCUMENTATION_REASON)))){
				messagePacker.sendSimpleMessage(project.getName(), project.getUser().getId(), MessageType.BAJA_DOCUMENTACION);
				messagePacker.sendAbonoCuenta(amount, project.getName(), user.getEmail(), transactionId, MessageType.RETORNO_FONDOS_DOCUMENTACION_INCOMPLETA);
			}
		}
		return users;
	}
	
}
