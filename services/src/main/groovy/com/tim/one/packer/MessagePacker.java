package com.tim.one.packer;

import java.math.BigDecimal;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tim.one.bean.MessageType;
import com.tim.one.bean.mail.AbonoCuentaBean;
import com.tim.one.bean.mail.CierreBean;
import com.tim.one.bean.mail.CreacionCuentaBean;
import com.tim.one.bean.mail.FinanciamientoInversionBean;
import com.tim.one.bean.mail.SimpleBean;
import com.tim.one.collaborator.MessagePackerCollaborator;
import com.tim.one.integration.MessageService;
import com.tim.one.helper.CommonMessagePackerHelper;
import com.tim.one.helper.MessagePackerHelper;
import com.tim.one.model.Project;
import com.tim.one.model.ProjectFinancialData;
import com.tim.one.model.UnitTx;
import com.tim.one.model.User;
import com.tim.one.repository.ProjectRepository;
import com.tim.one.repository.UserRepository;
import com.tim.one.util.CurrencyUtil;
import com.tim.one.util.DateUtil;

@Service
public class MessagePacker {

	@Autowired
	private MessageService messageDispatcher;
	@Autowired
	private MessagePackerHelper messagePackerHelper;
	@Autowired
	private CommonMessagePackerHelper commonMessagePackerHelper;
	@Autowired
	private MessagePackerCollaborator messagePackerCollaborator;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private DateUtil dateUtil;
	@Autowired
	@Qualifier("properties")
	private Properties properties;
	@Autowired
	private CurrencyUtil currencyUtil;
	@Autowired
	private ProjectRepository projectRepository;

	public void sendAbonoCuenta(BigDecimal amount, String name, String email, Integer userTxId, MessageType type) {
		AbonoCuentaBean bean = commonMessagePackerHelper.createAbonoCuentaBean();
		bean.setAmount(currencyUtil.format(amount));
		bean.setName(name);
		bean.setDate(dateUtil.createDate().toString());
		bean.setId(userTxId.toString());
		bean.setEmail(email);
		bean.setType(type);

		messageDispatcher.message(bean);
	}

	public void sendInversionFinanciamiento(Integer userId, Project project, Set<UnitTx> units, Integer transactionId) {
		FinanciamientoInversionBean bean = messagePackerHelper.createFinanciamientoInversionBean();
		bean.setProjectName(project.getName());
		bean.setAmount(currencyUtil.format(messagePackerCollaborator.getAmount(units)));
		bean.setDate(dateUtil.createDate().toString());
		bean.setId(transactionId.toString());
		bean.setEmail(userRepository.findOne(userId).getEmail());
		bean.setType(MessageType.INVERSION);
		messageDispatcher.message(bean);
	}

	public void sendCierreProductor(Project project) {
		CierreBean bean = setCierreBeanExpectations(project);
		bean.setType(MessageType.CIERRE_PRODUCTOR);

		messageDispatcher.message(bean);
	}

	private CierreBean setCierreBeanExpectations(Project project) {
		ProjectFinancialData projectFinancialData = project.getProjectFinancialData();
		CierreBean bean = messagePackerHelper.createCierreBean();
		bean.setProjectName(project.getName());
		bean.setBalance(currencyUtil.format(projectFinancialData.getBalance()));
		bean.setBudget(currencyUtil.format(projectFinancialData.getBudget()));
		bean.setEmail(project.getUser().getEmail());
		BigDecimal difference = projectFinancialData.getBalance().subtract(projectFinancialData.getBudget());
		bean.setDifference(currencyUtil.format(difference));
		return bean;
	}

	public void sendCierreAdministrador(Project project) {
		CierreBean bean = setCierreBeanExpectations(project);
		bean.setType(MessageType.CIERRE_ADMINISTRADOR);

		messageDispatcher.message(bean);
	}

	public void sendBreakevenReachedAdministrador(Project project) {
		CierreBean bean = setCierreBeanExpectations(project);
		bean.setType(MessageType.BE_ADMINISTRADOR);

		messageDispatcher.message(bean);
	}

	public void sendBreakevenReachedProductor(Project project) {
		CierreBean bean = setCierreBeanExpectations(project);
		bean.setType(MessageType.BE_PRODUCTOR);

		messageDispatcher.message(bean);
	}

	public void sendSimpleMessage(String name, Integer userId, MessageType type) {
		User user = userRepository.findOne(userId);
		SimpleBean bean = messagePackerHelper.createSimpleBean();
		bean.setMessage(name);
		bean.setEmail(user.getEmail());
		bean.setType(type);

		messageDispatcher.message(bean);
	}

	public void sendCierreVentaProductor(Project project, ProjectFinancialData projectFinancialData) {
		CierreBean bean = setCierreBeanExpectations(project);
		bean.setType(MessageType.CIERRE_VENTA_PRODUCTOR);

		messageDispatcher.message(bean);
	}

	public void sendCreacionCuenta(String account, String bank, String email) {
		CreacionCuentaBean bean = messagePackerHelper.createCreacionCuentaBean();
		bean.setAccount(account);
		bean.setBankName(bank);
		bean.setDate(dateUtil.createDate().toString());
		bean.setEmail(email);

		messageDispatcher.message(bean);
	}

}
