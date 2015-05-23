package com.tim.one.packer;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

public class TestMessagePacker {

	@InjectMocks
	private MessagePacker messagePacker = new MessagePacker();

	@Mock
	private MessageService messageDispatcher;
	@Mock
	private MessagePackerHelper messagePackerHelper;
	@Mock
	private AbonoCuentaBean abonoCuentaBean;
	@Mock
	private Project project;
	@Mock
	private List<UnitTx> units;
	@Mock
	private FinanciamientoInversionBean financiamientoInversionBean;
	@Mock
	private MessagePackerCollaborator messagePackerCollaborator;
	@Mock
	private CierreBean cierreBean;
	@Mock
	private UserRepository userRepository;
	@Mock
	private User user;
	@Mock
	private ProjectFinancialData projectFinancialData;
	@Mock
	private DateUtil dateUtil;
	@Mock
	private Date date;
	@Mock
	private SimpleBean simpleBean;
	@Mock
	private CurrencyUtil currencyUtil;
	@Mock
	private CreacionCuentaBean creacionCuentaBean;
	@Mock
	private CommonMessagePackerHelper commonMessagePackerHelper;
	@Mock
	private Properties properties;
	@Mock
	private ProjectRepository projectRepository;

	private Integer userTxId = 1;
	private Integer transactionId = 2;
	private Integer userId = 3;
	private Integer projectId = 4;
	private String name = "name";
	private String email = "josdem@trama.mx";
	private String account = "email";
	private String bankCode = "bankCode";

	private BigDecimal amount = new BigDecimal("100");
	private MessageType type = MessageType.ABONO_CUENTA;
	private Set<User> users = new HashSet<User>();
	private BigDecimal balance = new BigDecimal("300");
	private BigDecimal budget = new BigDecimal("200");
  private Set<UnitTx> unitsAsSet = new HashSet<UnitTx>();

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldSendAbonoCuenta() throws Exception {
		when(user.getName()).thenReturn(name);
		when(user.getEmail()).thenReturn(email);
		when(commonMessagePackerHelper.createAbonoCuentaBean()).thenReturn(abonoCuentaBean);
		when(dateUtil.createDate()).thenReturn(date);

		when(currencyUtil.format(amount)).thenReturn(amount.toString());

		messagePacker.sendAbonoCuenta(amount, name, email, userTxId, type);

		verify(abonoCuentaBean).setAmount(amount.toString());
		verify(abonoCuentaBean).setName(name);
		verify(abonoCuentaBean).setDate(date.toString());
		verify(abonoCuentaBean).setId(userTxId.toString());
		verify(abonoCuentaBean).setEmail(email);
		verify(abonoCuentaBean).setType(type);

		verify(messageDispatcher).message(abonoCuentaBean);
	}

	@Test
	public void shouldSendInversion() throws Exception {
		setFinanciamientoInversionExpectations();
		when(dateUtil.createDate()).thenReturn(date);

		when(currencyUtil.format(amount)).thenReturn(amount.toString());
		messagePacker.sendInversionFinanciamiento(userId, project, unitsAsSet, transactionId);

		verifyFinanciamientoInversionExpectations();
		verify(financiamientoInversionBean).setType(MessageType.INVERSION);
	}

	private void setFinanciamientoInversionExpectations() {
		when(messagePackerHelper.createFinanciamientoInversionBean()).thenReturn(financiamientoInversionBean);
		when(messagePackerCollaborator.getAmount(unitsAsSet)).thenReturn(amount);
		when(userRepository.findOne(userId)).thenReturn(user);
		when(user.getEmail()).thenReturn(email);
	}

	private void verifyFinanciamientoInversionExpectations() {
		verify(financiamientoInversionBean).setProjectName(project.getName());
		verify(financiamientoInversionBean).setAmount(amount.toString());
		verify(financiamientoInversionBean).setDate(date.toString());
		verify(financiamientoInversionBean).setId(transactionId.toString());
		verify(financiamientoInversionBean).setEmail(email);
	}

	private void verifyCierreBeanExpectations() {
		verify(cierreBean).setBalance(balance.toString());
		verify(cierreBean).setBudget(budget.toString());
		verify(cierreBean).setEmail(email);
		verify(cierreBean).setDifference(amount.toString());
	}

	private void setCierreBeanExpectations() {
		users.add(user);
		when(project.getProjectFinancialData()).thenReturn(projectFinancialData);
		when(project.getUser()).thenReturn(user);
		when(projectRepository.findOne(projectId)).thenReturn(project);
		when(messagePackerHelper.createCierreBean()).thenReturn(cierreBean);
		when(projectFinancialData.getBalance()).thenReturn(balance);
		when(projectFinancialData.getBudget()).thenReturn(budget);
		when(user.getEmail()).thenReturn(email);

		when(currencyUtil.format(balance)).thenReturn(balance.toString());
		when(currencyUtil.format(budget)).thenReturn(budget.toString());
		when(currencyUtil.format(amount)).thenReturn(amount.toString());
	}

	@Test
	public void shouldSendCierreProductor() throws Exception {
		setCierreBeanExpectations();

		messagePacker.sendCierreProductor(project);

		verifyCierreBeanExpectations();
		verify(cierreBean).setType(MessageType.CIERRE_PRODUCTOR);

		verify(messageDispatcher).message(cierreBean);
	}

	@Test
	public void shouldSendCierreAdministrador() throws Exception {
		setCierreBeanExpectations();

		messagePacker.sendCierreAdministrador(project);

		verifyCierreBeanExpectations();
		verify(cierreBean).setType(MessageType.CIERRE_ADMINISTRADOR);

		verify(messageDispatcher).message(cierreBean);
	}

	@Test
	public void shouldSendBreakevenReachedAdministrador() throws Exception {
		setCierreBeanExpectations();

		messagePacker.sendBreakevenReachedAdministrador(project);

		verifyCierreBeanExpectations();
		verify(cierreBean).setType(MessageType.BE_ADMINISTRADOR);

		verify(messageDispatcher).message(cierreBean);
	}

	@Test
	public void shouldSendBreakevenReachedProductor() throws Exception {
		setCierreBeanExpectations();

		messagePacker.sendBreakevenReachedProductor(project);

		verifyCierreBeanExpectations();
		verify(cierreBean).setType(MessageType.BE_PRODUCTOR);

		verify(messageDispatcher).message(cierreBean);
	}

	@Test
	public void shouldSendBajaProyectoProductor() throws Exception {
		when(messagePackerHelper.createSimpleBean()).thenReturn(simpleBean);
		when(simpleBean.getEmail()).thenReturn(email);
		when(simpleBean.getMessage()).thenReturn(name);
		when(userRepository.findOne(userId)).thenReturn(user);
		when(user.getEmail()).thenReturn(email);

		messagePacker.sendSimpleMessage(name, userId, MessageType.BAJA_DOCUMENTACION);

		verify(messageDispatcher).message(simpleBean);
		verify(simpleBean).setMessage(name);
		verify(simpleBean).setEmail(email);
		verify(simpleBean).setType(MessageType.BAJA_DOCUMENTACION);
	}

	@Test
	public void shouldSendCierreVentaProductor() throws Exception {
		setCierreBeanExpectations();

		messagePacker.sendCierreVentaProductor(project, projectFinancialData);

		verifyCierreBeanExpectations();
		verify(cierreBean).setType(MessageType.CIERRE_VENTA_PRODUCTOR);

		verify(messageDispatcher).message(cierreBean);
	}

	@Test
	public void shouldSendCreacionCuenta() throws Exception {
		when(messagePackerHelper.createCreacionCuentaBean()).thenReturn(creacionCuentaBean);
		when(dateUtil.createDate()).thenReturn(date);

		messagePacker.sendCreacionCuenta(account, bankCode, email);

		verify(creacionCuentaBean).setAccount(account);
		verify(creacionCuentaBean).setBankName(bankCode);
		verify(creacionCuentaBean).setDate(date.toString());
		verify(creacionCuentaBean).setEmail(email);

		verify(messageDispatcher).message(creacionCuentaBean);
	}

}
