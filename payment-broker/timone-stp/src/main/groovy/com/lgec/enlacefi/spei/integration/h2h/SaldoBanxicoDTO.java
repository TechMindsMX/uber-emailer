
package com.lgec.enlacefi.spei.integration.h2h;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for saldoBanxicoDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="saldoBanxicoDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fechaOperacion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="institucion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="saldoNoReservado" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="saldoReservado" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "saldoBanxicoDTO", propOrder = {
    "fechaOperacion",
    "institucion",
    "saldoNoReservado",
    "saldoReservado"
})
public class SaldoBanxicoDTO {

    protected Integer fechaOperacion;
    protected Integer institucion;
    protected BigDecimal saldoNoReservado;
    protected BigDecimal saldoReservado;

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
     * Gets the value of the institucion property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInstitucion() {
        return institucion;
    }

    /**
     * Sets the value of the institucion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInstitucion(Integer value) {
        this.institucion = value;
    }

    /**
     * Gets the value of the saldoNoReservado property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSaldoNoReservado() {
        return saldoNoReservado;
    }

    /**
     * Sets the value of the saldoNoReservado property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSaldoNoReservado(BigDecimal value) {
        this.saldoNoReservado = value;
    }

    /**
     * Gets the value of the saldoReservado property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSaldoReservado() {
        return saldoReservado;
    }

    /**
     * Sets the value of the saldoReservado property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSaldoReservado(BigDecimal value) {
        this.saldoReservado = value;
    }

}
