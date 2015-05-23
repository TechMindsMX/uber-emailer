package com.tim.one.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tim.one.bean.MessageType;
import com.tim.one.bean.mail.AbonoCuentaBean;
import com.tim.one.bean.mail.CierreBean;
import com.tim.one.bean.mail.CreacionCuentaBean;
import com.tim.one.bean.mail.FinanciamientoInversionBean;
import com.tim.one.bean.mail.SimpleBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/mail-context.xml", "classpath:/services-test-appctx.xml"})
public class TestIntegrationMailService {

  @Autowired
  private NotificationService notificationService;

  private String id = "1";
  private String email = "test@trama.mx";
  private String name = "josdem";
  private String projectName = "projectName";
  private String amount = "100.00";
  private String date = "date";
  private String bankName = "bankName";
  private String concepto = "concepto";
  private String balance = "1234.00";
  private String budget = "4321.00";
  private String difference = "3087.00";
  private String account = "account";

  @Test
  public void shouldSendAbonoCuentaMessage() throws Exception {
    AbonoCuentaBean bean = setAbonoCuentaBeanExpectations();
    bean.setType(MessageType.ABONO_CUENTA);

    notificationService.sendNotification(bean);
  }

  @Test
  public void shouldSendFinanciamientoInversionMessage() throws Exception {
    AbonoCuentaBean bean = setAbonoCuentaBeanExpectations();
    bean.setType(MessageType.TRASPASO_CUENTA_BENEFICIARIO);

    notificationService.sendNotification(bean);
  }

  @Test
  public void shouldSendAbonoProductorMessage() throws Exception {
    AbonoCuentaBean bean = setAbonoCuentaBeanExpectations();
    bean.setName(projectName);
    bean.setType(MessageType.PRODUCTOR);

    notificationService.sendNotification(bean);
  }

  @Test
  public void shouldSendRetornoFondosDocumentacionIncompletaMessage() throws Exception {
    AbonoCuentaBean bean = setAbonoCuentaBeanExpectations();
    bean.setName(projectName);
    bean.setType(MessageType.RETORNO_FONDOS_DOCUMENTACION_INCOMPLETA);

    notificationService.sendNotification(bean);
  }

  @Test
  public void shouldSendCierreProductoMessage() throws Exception {
    AbonoCuentaBean bean = setAbonoCuentaBeanExpectations();
    bean.setName(projectName);
    bean.setType(MessageType.CIERRE_PRODUCTO);

    notificationService.sendNotification(bean);
  }

  @Test
  public void shouldSendCierreProductoProductorMessage() throws Exception {
    AbonoCuentaBean bean = setAbonoCuentaBeanExpectations();
    bean.setName(projectName);
    bean.setType(MessageType.CIERRE_PRODUCTO_PRODUCTOR);

    notificationService.sendNotification(bean);
  }

  @Test
  public void shouldSendRedencionMessage() throws Exception {
    AbonoCuentaBean bean = setAbonoCuentaBeanExpectations();
    bean.setType(MessageType.REDENCION);

    notificationService.sendNotification(bean);
  }

  @Test
  public void shouldSendRetornoFondosNoEntregaProductoMessage() throws Exception {
    AbonoCuentaBean bean = setAbonoCuentaBeanExpectations();
    bean.setName(projectName);
    bean.setType(MessageType.RETORNO_FONDOS_NO_ENTREGA_PRODUCTO);

    notificationService.sendNotification(bean);
  }

  @Test
  public void shouldSendTraspasoCuentaOrdenanteMessage() throws Exception {
    AbonoCuentaBean bean = setAbonoCuentaBeanExpectations();
    bean.setType(MessageType.TRASPASO_CUENTA_ORDENANTE);

    notificationService.sendNotification(bean);
  }

  @Test
  public void shouldSendCashoutMessage() throws Exception {
    AbonoCuentaBean bean = setAbonoCuentaBeanExpectations();
    bean.setName(bankName);
    bean.setType(MessageType.CASHOUT);

    notificationService.sendNotification(bean);
  }

  @Test
  public void shouldSendBridgeTramaMessage() throws Exception {
    AbonoCuentaBean bean = setAbonoCuentaBeanExpectations();
    bean.setName(concepto );
    bean.setType(MessageType.BRIDGE_TRAMA);

    notificationService.sendNotification(bean);
  }

  private AbonoCuentaBean setAbonoCuentaBeanExpectations() {
    AbonoCuentaBean bean = new AbonoCuentaBean();
    bean.setId(id);
    bean.setName(name);
    bean.setAmount(amount);
    bean.setDate(date);
    bean.setEmail(email);
    return bean;
  }

  @Test
  public void shouldSendInversionMessage() throws Exception {
    FinanciamientoInversionBean bean = setFinanciamientoInversionBeanExpectations();
    bean.setType(MessageType.INVERSION);

    notificationService.sendNotification(bean);
  }

  private FinanciamientoInversionBean setFinanciamientoInversionBeanExpectations() {
    FinanciamientoInversionBean bean = new FinanciamientoInversionBean();
    bean.setId(id);
    bean.setProjectName(projectName);
    bean.setAmount(amount);
    bean.setDate(date);
    bean.setEmail(email);
    return bean;
  }

  @Test
  public void shouldSendCierreProductorMessage() throws Exception {
    CierreBean bean = setCierreBeanExpectations();
    bean.setType(MessageType.CIERRE_PRODUCTOR);

    notificationService.sendNotification(bean);
  }

  @Test
  public void shouldSendCierreAdministradorMessage() throws Exception {
    CierreBean bean = setCierreBeanExpectations();
    bean.setType(MessageType.CIERRE_ADMINISTRADOR);

    notificationService.sendNotification(bean);
  }

  @Test
  public void shouldSendBreakevenProductorMessage() throws Exception {
    CierreBean bean = setCierreBeanExpectations();
    bean.setType(MessageType.BE_PRODUCTOR);

    notificationService.sendNotification(bean);
  }

  @Test
  public void shouldSendBreakevenAdministradorMessage() throws Exception {
    CierreBean bean = setCierreBeanExpectations();
    bean.setType(MessageType.BE_ADMINISTRADOR);

    notificationService.sendNotification(bean);
  }

  @Test
  public void shouldSendBreakevenInvestorMessage() throws Exception {
    CierreBean bean = setCierreBeanExpectations();
    bean.setType(MessageType.BE_SOCIO);

    notificationService.sendNotification(bean);
  }

  @Test
  public void shouldSendCierreVentaProductorMessage() throws Exception {
    CierreBean bean = setCierreBeanExpectations();
    bean.setType(MessageType.CIERRE_VENTA_PRODUCTOR);

    notificationService.sendNotification(bean);
  }

  private CierreBean setCierreBeanExpectations() {
    CierreBean bean = new CierreBean();
    bean.setProjectName(projectName);
    bean.setBalance(balance);
    bean.setBudget(budget);
    bean.setDifference(difference);
    bean.setEmail(email);
    return bean;
  }

  @Test
  public void shouldSendBajaDocumentacionMessage() throws Exception {
    SimpleBean bean = setSimpleBeanExpectations();
    bean.setType(MessageType.BAJA_DOCUMENTACION);

    notificationService.sendNotification(bean);
  }

  @Test
  public void shouldSendCierrePublicacionProductorMessage() throws Exception {
    SimpleBean bean = setSimpleBeanExpectations();
    bean.setType(MessageType.CIERRE_PUBLICACION_PRODUCTOR);

    notificationService.sendNotification(bean);
  }

  @Test
  public void shouldSendCierrePublicacionSocioMessage() throws Exception {
    SimpleBean bean = setSimpleBeanExpectations();
    bean.setType(MessageType.CIERRE_PUBLICACION_SOCIO);

    notificationService.sendNotification(bean);
  }

  private SimpleBean setSimpleBeanExpectations() {
    SimpleBean bean = new SimpleBean();
    bean.setEmail(email);
    bean.setMessage(projectName);
    return bean;
  }

  @Test
  public void shouldSendCreacionCuentaMessage() throws Exception {
    CreacionCuentaBean bean = new CreacionCuentaBean();
    bean.setAccount(account);
    bean.setBankName(bankName);
    bean.setDate(date);
    bean.setEmail(email);
    bean.setType(MessageType.CREACION_CUENTA);

    notificationService.sendNotification(bean);
  }

}
