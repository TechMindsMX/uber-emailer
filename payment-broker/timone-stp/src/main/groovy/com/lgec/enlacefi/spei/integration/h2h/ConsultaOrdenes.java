
package com.lgec.enlacefi.spei.integration.h2h;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for consultaOrdenes complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="consultaOrdenes">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tipoOrden" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaLiqInicial" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="fechaLiqFinal" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="institucionOperante" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="institucionCotraparte" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="nombreOrdenante" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nombreBeneficiario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cveRastreo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cuentaOrdenante" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cuentaBeneficiario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="monto" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultaOrdenes", propOrder = {
    "tipoOrden",
    "fechaLiqInicial",
    "fechaLiqFinal",
    "institucionOperante",
    "institucionCotraparte",
    "nombreOrdenante",
    "nombreBeneficiario",
    "cveRastreo",
    "cuentaOrdenante",
    "cuentaBeneficiario",
    "monto"
})
public class ConsultaOrdenes {

    protected String tipoOrden;
    protected Integer fechaLiqInicial;
    protected Integer fechaLiqFinal;
    protected Integer institucionOperante;
    protected Integer institucionCotraparte;
    protected String nombreOrdenante;
    protected String nombreBeneficiario;
    protected String cveRastreo;
    protected String cuentaOrdenante;
    protected String cuentaBeneficiario;
    protected BigDecimal monto;

    /**
     * Gets the value of the tipoOrden property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoOrden() {
        return tipoOrden;
    }

    /**
     * Sets the value of the tipoOrden property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoOrden(String value) {
        this.tipoOrden = value;
    }

    /**
     * Gets the value of the fechaLiqInicial property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFechaLiqInicial() {
        return fechaLiqInicial;
    }

    /**
     * Sets the value of the fechaLiqInicial property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFechaLiqInicial(Integer value) {
        this.fechaLiqInicial = value;
    }

    /**
     * Gets the value of the fechaLiqFinal property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFechaLiqFinal() {
        return fechaLiqFinal;
    }

    /**
     * Sets the value of the fechaLiqFinal property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFechaLiqFinal(Integer value) {
        this.fechaLiqFinal = value;
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
     * Gets the value of the institucionCotraparte property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInstitucionCotraparte() {
        return institucionCotraparte;
    }

    /**
     * Sets the value of the institucionCotraparte property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInstitucionCotraparte(Integer value) {
        this.institucionCotraparte = value;
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
     * Gets the value of the cveRastreo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCveRastreo() {
        return cveRastreo;
    }

    /**
     * Sets the value of the cveRastreo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCveRastreo(String value) {
        this.cveRastreo = value;
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

}
