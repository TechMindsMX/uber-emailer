
package com.lgec.enlacefi.spei.integration.h2h.model;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ordenPagoWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ordenPagoWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="causaDevolucion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="claveCatUsuario1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="claveCatUsuario2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="clavePago" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="claveRastreo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="claveRastreoDevolucion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="conceptoPago" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="conceptoPago2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cuentaBeneficiario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cuentaBeneficiario2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cuentaOrdenante" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="emailBeneficiario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="empresa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="estado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaOperacion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="firma" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="folioOrigen" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idCliente" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="institucionContraparte" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="institucionOperante" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="iva" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="medioEntrega" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="monto" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="nombreBeneficiario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombreBeneficiario2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombreOrdenante" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prioridad" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="referenciaCobranza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="referenciaNumerica" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="rfcCurpBeneficiario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rfcCurpBeneficiario2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rfcCurpOrdenante" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoCuentaBeneficiario" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="tipoCuentaBeneficiario2" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="tipoCuentaOrdenante" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="tipoOperacion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="tipoPago" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="topologia" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="usuario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ordenPagoWS", propOrder = {
    "causaDevolucion",
    "claveCatUsuario1",
    "claveCatUsuario2",
    "clavePago",
    "claveRastreo",
    "claveRastreoDevolucion",
    "conceptoPago",
    "conceptoPago2",
    "cuentaBeneficiario",
    "cuentaBeneficiario2",
    "cuentaOrdenante",
    "emailBeneficiario",
    "empresa",
    "estado",
    "fechaOperacion",
    "firma",
    "folioOrigen",
    "idCliente",
    "institucionContraparte",
    "institucionOperante",
    "iva",
    "medioEntrega",
    "monto",
    "nombreBeneficiario",
    "nombreBeneficiario2",
    "nombreOrdenante",
    "prioridad",
    "referenciaCobranza",
    "referenciaNumerica",
    "rfcCurpBeneficiario",
    "rfcCurpBeneficiario2",
    "rfcCurpOrdenante",
    "tipoCuentaBeneficiario",
    "tipoCuentaBeneficiario2",
    "tipoCuentaOrdenante",
    "tipoOperacion",
    "tipoPago",
    "topologia",
    "usuario"
})
public class OrdenPagoWS {

    protected Integer causaDevolucion;
    protected String claveCatUsuario1;
    protected String claveCatUsuario2;
    protected String clavePago;
    protected String claveRastreo;
    protected String claveRastreoDevolucion;
    protected String conceptoPago;
    protected String conceptoPago2;
    protected String cuentaBeneficiario;
    protected String cuentaBeneficiario2;
    protected String cuentaOrdenante;
    protected String emailBeneficiario;
    protected String empresa;
    protected String estado;
    protected Integer fechaOperacion;
    protected String firma;
    protected String folioOrigen;
    protected String idCliente;
    protected Integer institucionContraparte;
    protected Integer institucionOperante;
    protected Double iva;
    protected Integer medioEntrega;
    protected BigDecimal monto;
    protected String nombreBeneficiario;
    protected String nombreBeneficiario2;
    protected String nombreOrdenante;
    protected Integer prioridad;
    protected String referenciaCobranza;
    protected Integer referenciaNumerica;
    protected String rfcCurpBeneficiario;
    protected String rfcCurpBeneficiario2;
    protected String rfcCurpOrdenante;
    protected Integer tipoCuentaBeneficiario;
    protected Integer tipoCuentaBeneficiario2;
    protected Integer tipoCuentaOrdenante;
    protected Integer tipoOperacion;
    protected Integer tipoPago;
    protected String topologia;
    protected String usuario;

    /**
     * Gets the value of the causaDevolucion property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCausaDevolucion() {
        return causaDevolucion;
    }

    /**
     * Sets the value of the causaDevolucion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCausaDevolucion(Integer value) {
        this.causaDevolucion = value;
    }

    /**
     * Gets the value of the claveCatUsuario1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaveCatUsuario1() {
        return claveCatUsuario1;
    }

    /**
     * Sets the value of the claveCatUsuario1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaveCatUsuario1(String value) {
        this.claveCatUsuario1 = value;
    }

    /**
     * Gets the value of the claveCatUsuario2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaveCatUsuario2() {
        return claveCatUsuario2;
    }

    /**
     * Sets the value of the claveCatUsuario2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaveCatUsuario2(String value) {
        this.claveCatUsuario2 = value;
    }

    /**
     * Gets the value of the clavePago property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClavePago() {
        return clavePago;
    }

    /**
     * Sets the value of the clavePago property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClavePago(String value) {
        this.clavePago = value;
    }

    /**
     * Gets the value of the claveRastreo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaveRastreo() {
        return claveRastreo;
    }

    /**
     * Sets the value of the claveRastreo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaveRastreo(String value) {
        this.claveRastreo = value;
    }

    /**
     * Gets the value of the claveRastreoDevolucion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaveRastreoDevolucion() {
        return claveRastreoDevolucion;
    }

    /**
     * Sets the value of the claveRastreoDevolucion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaveRastreoDevolucion(String value) {
        this.claveRastreoDevolucion = value;
    }

    /**
     * Gets the value of the conceptoPago property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConceptoPago() {
        return conceptoPago;
    }

    /**
     * Sets the value of the conceptoPago property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConceptoPago(String value) {
        this.conceptoPago = value;
    }

    /**
     * Gets the value of the conceptoPago2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConceptoPago2() {
        return conceptoPago2;
    }

    /**
     * Sets the value of the conceptoPago2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConceptoPago2(String value) {
        this.conceptoPago2 = value;
    }

    /**
     * Gets the value of the cuentaBeneficiario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCuentaBeneficiario() {
        return cuentaBeneficiario;
    }

    /**
     * Sets the value of the cuentaBeneficiario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCuentaBeneficiario(String value) {
        this.cuentaBeneficiario = value;
    }

    /**
     * Gets the value of the cuentaBeneficiario2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCuentaBeneficiario2() {
        return cuentaBeneficiario2;
    }

    /**
     * Sets the value of the cuentaBeneficiario2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCuentaBeneficiario2(String value) {
        this.cuentaBeneficiario2 = value;
    }

    /**
     * Gets the value of the cuentaOrdenante property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCuentaOrdenante() {
        return cuentaOrdenante;
    }

    /**
     * Sets the value of the cuentaOrdenante property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCuentaOrdenante(String value) {
        this.cuentaOrdenante = value;
    }

    /**
     * Gets the value of the emailBeneficiario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailBeneficiario() {
        return emailBeneficiario;
    }

    /**
     * Sets the value of the emailBeneficiario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailBeneficiario(String value) {
        this.emailBeneficiario = value;
    }

    /**
     * Gets the value of the empresa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmpresa() {
        return empresa;
    }

    /**
     * Sets the value of the empresa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmpresa(String value) {
        this.empresa = value;
    }

    /**
     * Gets the value of the estado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Sets the value of the estado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstado(String value) {
        this.estado = value;
    }

    /**
     * Gets the value of the fechaOperacion property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFechaOperacion() {
        return fechaOperacion;
    }

    /**
     * Sets the value of the fechaOperacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFechaOperacion(Integer value) {
        this.fechaOperacion = value;
    }

    /**
     * Gets the value of the firma property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirma() {
        return firma;
    }

    /**
     * Sets the value of the firma property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirma(String value) {
        this.firma = value;
    }

    /**
     * Gets the value of the folioOrigen property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFolioOrigen() {
        return folioOrigen;
    }

    /**
     * Sets the value of the folioOrigen property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFolioOrigen(String value) {
        this.folioOrigen = value;
    }

    /**
     * Gets the value of the idCliente property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdCliente() {
        return idCliente;
    }

    /**
     * Sets the value of the idCliente property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdCliente(String value) {
        this.idCliente = value;
    }

    /**
     * Gets the value of the institucionContraparte property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInstitucionContraparte() {
        return institucionContraparte;
    }

    /**
     * Sets the value of the institucionContraparte property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInstitucionContraparte(Integer value) {
        this.institucionContraparte = value;
    }

    /**
     * Gets the value of the institucionOperante property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInstitucionOperante() {
        return institucionOperante;
    }

    /**
     * Sets the value of the institucionOperante property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInstitucionOperante(Integer value) {
        this.institucionOperante = value;
    }

    /**
     * Gets the value of the iva property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getIva() {
        return iva;
    }

    /**
     * Sets the value of the iva property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setIva(Double value) {
        this.iva = value;
    }

    /**
     * Gets the value of the medioEntrega property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMedioEntrega() {
        return medioEntrega;
    }

    /**
     * Sets the value of the medioEntrega property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMedioEntrega(Integer value) {
        this.medioEntrega = value;
    }

    /**
     * Gets the value of the monto property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMonto() {
        return monto;
    }

    /**
     * Sets the value of the monto property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMonto(BigDecimal value) {
        this.monto = value;
    }

    /**
     * Gets the value of the nombreBeneficiario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreBeneficiario() {
        return nombreBeneficiario;
    }

    /**
     * Sets the value of the nombreBeneficiario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreBeneficiario(String value) {
        this.nombreBeneficiario = value;
    }

    /**
     * Gets the value of the nombreBeneficiario2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreBeneficiario2() {
        return nombreBeneficiario2;
    }

    /**
     * Sets the value of the nombreBeneficiario2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreBeneficiario2(String value) {
        this.nombreBeneficiario2 = value;
    }

    /**
     * Gets the value of the nombreOrdenante property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreOrdenante() {
        return nombreOrdenante;
    }

    /**
     * Sets the value of the nombreOrdenante property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreOrdenante(String value) {
        this.nombreOrdenante = value;
    }

    /**
     * Gets the value of the prioridad property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPrioridad() {
        return prioridad;
    }

    /**
     * Sets the value of the prioridad property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPrioridad(Integer value) {
        this.prioridad = value;
    }

    /**
     * Gets the value of the referenciaCobranza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenciaCobranza() {
        return referenciaCobranza;
    }

    /**
     * Sets the value of the referenciaCobranza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenciaCobranza(String value) {
        this.referenciaCobranza = value;
    }

    /**
     * Gets the value of the referenciaNumerica property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getReferenciaNumerica() {
        return referenciaNumerica;
    }

    /**
     * Sets the value of the referenciaNumerica property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setReferenciaNumerica(Integer value) {
        this.referenciaNumerica = value;
    }

    /**
     * Gets the value of the rfcCurpBeneficiario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRfcCurpBeneficiario() {
        return rfcCurpBeneficiario;
    }

    /**
     * Sets the value of the rfcCurpBeneficiario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRfcCurpBeneficiario(String value) {
        this.rfcCurpBeneficiario = value;
    }

    /**
     * Gets the value of the rfcCurpBeneficiario2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRfcCurpBeneficiario2() {
        return rfcCurpBeneficiario2;
    }

    /**
     * Sets the value of the rfcCurpBeneficiario2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRfcCurpBeneficiario2(String value) {
        this.rfcCurpBeneficiario2 = value;
    }

    /**
     * Gets the value of the rfcCurpOrdenante property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRfcCurpOrdenante() {
        return rfcCurpOrdenante;
    }

    /**
     * Sets the value of the rfcCurpOrdenante property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRfcCurpOrdenante(String value) {
        this.rfcCurpOrdenante = value;
    }

    /**
     * Gets the value of the tipoCuentaBeneficiario property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTipoCuentaBeneficiario() {
        return tipoCuentaBeneficiario;
    }

    /**
     * Sets the value of the tipoCuentaBeneficiario property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTipoCuentaBeneficiario(Integer value) {
        this.tipoCuentaBeneficiario = value;
    }

    /**
     * Gets the value of the tipoCuentaBeneficiario2 property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTipoCuentaBeneficiario2() {
        return tipoCuentaBeneficiario2;
    }

    /**
     * Sets the value of the tipoCuentaBeneficiario2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTipoCuentaBeneficiario2(Integer value) {
        this.tipoCuentaBeneficiario2 = value;
    }

    /**
     * Gets the value of the tipoCuentaOrdenante property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTipoCuentaOrdenante() {
        return tipoCuentaOrdenante;
    }

    /**
     * Sets the value of the tipoCuentaOrdenante property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTipoCuentaOrdenante(Integer value) {
        this.tipoCuentaOrdenante = value;
    }

    /**
     * Gets the value of the tipoOperacion property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTipoOperacion() {
        return tipoOperacion;
    }

    /**
     * Sets the value of the tipoOperacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTipoOperacion(Integer value) {
        this.tipoOperacion = value;
    }

    /**
     * Gets the value of the tipoPago property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTipoPago() {
        return tipoPago;
    }

    /**
     * Sets the value of the tipoPago property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTipoPago(Integer value) {
        this.tipoPago = value;
    }

    /**
     * Gets the value of the topologia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTopologia() {
        return topologia;
    }

    /**
     * Sets the value of the topologia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTopologia(String value) {
        this.topologia = value;
    }

    /**
     * Gets the value of the usuario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Sets the value of the usuario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsuario(String value) {
        this.usuario = value;
    }

}
