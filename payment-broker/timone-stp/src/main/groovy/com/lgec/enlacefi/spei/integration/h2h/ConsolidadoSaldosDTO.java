
package com.lgec.enlacefi.spei.integration.h2h;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for consolidadoSaldosDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="consolidadoSaldosDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="abonosOrdenes" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="abonosTraspasos" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="cargosOrdenes" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="cargosTraspasos" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="consolidadoOrdenes" type="{http://h2h.integration.spei.enlacefi.lgec.com/}consolidadoOrdenesDTO" minOccurs="0"/>
 *         &lt;element name="saldoBanxico" type="{http://h2h.integration.spei.enlacefi.lgec.com/}saldoBanxicoDTO" minOccurs="0"/>
 *         &lt;element name="saldoCuenta" type="{http://h2h.integration.spei.enlacefi.lgec.com/}saldoCuenta" minOccurs="0"/>
 *         &lt;element name="saldoInicial" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="totalLocal" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consolidadoSaldosDTO", propOrder = {
    "abonosOrdenes",
    "abonosTraspasos",
    "cargosOrdenes",
    "cargosTraspasos",
    "consolidadoOrdenes",
    "saldoBanxico",
    "saldoCuenta",
    "saldoInicial",
    "totalLocal"
})
public class ConsolidadoSaldosDTO {

    protected BigDecimal abonosOrdenes;
    protected BigDecimal abonosTraspasos;
    protected BigDecimal cargosOrdenes;
    protected BigDecimal cargosTraspasos;
    protected ConsolidadoOrdenesDTO consolidadoOrdenes;
    protected SaldoBanxicoDTO saldoBanxico;
    protected SaldoCuenta saldoCuenta;
    protected BigDecimal saldoInicial;
    protected BigDecimal totalLocal;

    /**
     * Gets the value of the abonosOrdenes property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAbonosOrdenes() {
        return abonosOrdenes;
    }

    /**
     * Sets the value of the abonosOrdenes property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAbonosOrdenes(BigDecimal value) {
        this.abonosOrdenes = value;
    }

    /**
     * Gets the value of the abonosTraspasos property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAbonosTraspasos() {
        return abonosTraspasos;
    }

    /**
     * Sets the value of the abonosTraspasos property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAbonosTraspasos(BigDecimal value) {
        this.abonosTraspasos = value;
    }

    /**
     * Gets the value of the cargosOrdenes property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCargosOrdenes() {
        return cargosOrdenes;
    }

    /**
     * Sets the value of the cargosOrdenes property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCargosOrdenes(BigDecimal value) {
        this.cargosOrdenes = value;
    }

    /**
     * Gets the value of the cargosTraspasos property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCargosTraspasos() {
        return cargosTraspasos;
    }

    /**
     * Sets the value of the cargosTraspasos property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCargosTraspasos(BigDecimal value) {
        this.cargosTraspasos = value;
    }

    /**
     * Gets the value of the consolidadoOrdenes property.
     * 
     * @return
     *     possible object is
     *     {@link ConsolidadoOrdenesDTO }
     *     
     */
    public ConsolidadoOrdenesDTO getConsolidadoOrdenes() {
        return consolidadoOrdenes;
    }

    /**
     * Sets the value of the consolidadoOrdenes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConsolidadoOrdenesDTO }
     *     
     */
    public void setConsolidadoOrdenes(ConsolidadoOrdenesDTO value) {
        this.consolidadoOrdenes = value;
    }

    /**
     * Gets the value of the saldoBanxico property.
     * 
     * @return
     *     possible object is
     *     {@link SaldoBanxicoDTO }
     *     
     */
    public SaldoBanxicoDTO getSaldoBanxico() {
        return saldoBanxico;
    }

    /**
     * Sets the value of the saldoBanxico property.
     * 
     * @param value
     *     allowed object is
     *     {@link SaldoBanxicoDTO }
     *     
     */
    public void setSaldoBanxico(SaldoBanxicoDTO value) {
        this.saldoBanxico = value;
    }

    /**
     * Gets the value of the saldoCuenta property.
     * 
     * @return
     *     possible object is
     *     {@link SaldoCuenta }
     *     
     */
    public SaldoCuenta getSaldoCuenta() {
        return saldoCuenta;
    }

    /**
     * Sets the value of the saldoCuenta property.
     * 
     * @param value
     *     allowed object is
     *     {@link SaldoCuenta }
     *     
     */
    public void setSaldoCuenta(SaldoCuenta value) {
        this.saldoCuenta = value;
    }

    /**
     * Gets the value of the saldoInicial property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    /**
     * Sets the value of the saldoInicial property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSaldoInicial(BigDecimal value) {
        this.saldoInicial = value;
    }

    /**
     * Gets the value of the totalLocal property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalLocal() {
        return totalLocal;
    }

    /**
     * Sets the value of the totalLocal property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalLocal(BigDecimal value) {
        this.totalLocal = value;
    }

}
