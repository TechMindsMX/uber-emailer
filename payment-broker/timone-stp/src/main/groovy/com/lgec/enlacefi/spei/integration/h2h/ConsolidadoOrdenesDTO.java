
package com.lgec.enlacefi.spei.integration.h2h;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for consolidadoOrdenesDTO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="consolidadoOrdenesDTO">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="countCanceladasE" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="countConfirmadasE" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="countDevolucionesE" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="countDevolucionesR" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="countEnviadasE" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="countLiquidadasE" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="countLiquidadasR" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="countPendientesE" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="countPorEnviarE" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="countRechazadasAdapterE" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="countRechazadasBanxicoE" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="countRecibidasR" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="sumCanceladasE" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="sumConfirmadasE" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="sumDevolucionesE" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="sumDevolucionesR" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="sumEnviadasE" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="sumLiquidadasE" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="sumLiquidadasR" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="sumPendientesE" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="sumPorEnviarE" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="sumRechazadasAdapterE" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="sumRechazadasBanxicoE" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="sumRecibidasR" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consolidadoOrdenesDTO", propOrder = {
    "countCanceladasE",
    "countConfirmadasE",
    "countDevolucionesE",
    "countDevolucionesR",
    "countEnviadasE",
    "countLiquidadasE",
    "countLiquidadasR",
    "countPendientesE",
    "countPorEnviarE",
    "countRechazadasAdapterE",
    "countRechazadasBanxicoE",
    "countRecibidasR",
    "sumCanceladasE",
    "sumConfirmadasE",
    "sumDevolucionesE",
    "sumDevolucionesR",
    "sumEnviadasE",
    "sumLiquidadasE",
    "sumLiquidadasR",
    "sumPendientesE",
    "sumPorEnviarE",
    "sumRechazadasAdapterE",
    "sumRechazadasBanxicoE",
    "sumRecibidasR"
})
public class ConsolidadoOrdenesDTO {

    protected Long countCanceladasE;
    protected Long countConfirmadasE;
    protected Long countDevolucionesE;
    protected Long countDevolucionesR;
    protected Long countEnviadasE;
    protected Long countLiquidadasE;
    protected Long countLiquidadasR;
    protected Long countPendientesE;
    protected Long countPorEnviarE;
    protected Long countRechazadasAdapterE;
    protected Long countRechazadasBanxicoE;
    protected Long countRecibidasR;
    protected BigDecimal sumCanceladasE;
    protected BigDecimal sumConfirmadasE;
    protected BigDecimal sumDevolucionesE;
    protected BigDecimal sumDevolucionesR;
    protected BigDecimal sumEnviadasE;
    protected BigDecimal sumLiquidadasE;
    protected BigDecimal sumLiquidadasR;
    protected BigDecimal sumPendientesE;
    protected BigDecimal sumPorEnviarE;
    protected BigDecimal sumRechazadasAdapterE;
    protected BigDecimal sumRechazadasBanxicoE;
    protected BigDecimal sumRecibidasR;

    /**
     * Gets the value of the countCanceladasE property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCountCanceladasE() {
        return countCanceladasE;
    }

    /**
     * Sets the value of the countCanceladasE property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCountCanceladasE(Long value) {
        this.countCanceladasE = value;
    }

    /**
     * Gets the value of the countConfirmadasE property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCountConfirmadasE() {
        return countConfirmadasE;
    }

    /**
     * Sets the value of the countConfirmadasE property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCountConfirmadasE(Long value) {
        this.countConfirmadasE = value;
    }

    /**
     * Gets the value of the countDevolucionesE property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCountDevolucionesE() {
        return countDevolucionesE;
    }

    /**
     * Sets the value of the countDevolucionesE property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCountDevolucionesE(Long value) {
        this.countDevolucionesE = value;
    }

    /**
     * Gets the value of the countDevolucionesR property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCountDevolucionesR() {
        return countDevolucionesR;
    }

    /**
     * Sets the value of the countDevolucionesR property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCountDevolucionesR(Long value) {
        this.countDevolucionesR = value;
    }

    /**
     * Gets the value of the countEnviadasE property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCountEnviadasE() {
        return countEnviadasE;
    }

    /**
     * Sets the value of the countEnviadasE property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCountEnviadasE(Long value) {
        this.countEnviadasE = value;
    }

    /**
     * Gets the value of the countLiquidadasE property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCountLiquidadasE() {
        return countLiquidadasE;
    }

    /**
     * Sets the value of the countLiquidadasE property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCountLiquidadasE(Long value) {
        this.countLiquidadasE = value;
    }

    /**
     * Gets the value of the countLiquidadasR property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCountLiquidadasR() {
        return countLiquidadasR;
    }

    /**
     * Sets the value of the countLiquidadasR property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCountLiquidadasR(Long value) {
        this.countLiquidadasR = value;
    }

    /**
     * Gets the value of the countPendientesE property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCountPendientesE() {
        return countPendientesE;
    }

    /**
     * Sets the value of the countPendientesE property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCountPendientesE(Long value) {
        this.countPendientesE = value;
    }

    /**
     * Gets the value of the countPorEnviarE property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCountPorEnviarE() {
        return countPorEnviarE;
    }

    /**
     * Sets the value of the countPorEnviarE property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCountPorEnviarE(Long value) {
        this.countPorEnviarE = value;
    }

    /**
     * Gets the value of the countRechazadasAdapterE property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCountRechazadasAdapterE() {
        return countRechazadasAdapterE;
    }

    /**
     * Sets the value of the countRechazadasAdapterE property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCountRechazadasAdapterE(Long value) {
        this.countRechazadasAdapterE = value;
    }

    /**
     * Gets the value of the countRechazadasBanxicoE property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCountRechazadasBanxicoE() {
        return countRechazadasBanxicoE;
    }

    /**
     * Sets the value of the countRechazadasBanxicoE property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCountRechazadasBanxicoE(Long value) {
        this.countRechazadasBanxicoE = value;
    }

    /**
     * Gets the value of the countRecibidasR property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCountRecibidasR() {
        return countRecibidasR;
    }

    /**
     * Sets the value of the countRecibidasR property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCountRecibidasR(Long value) {
        this.countRecibidasR = value;
    }

    /**
     * Gets the value of the sumCanceladasE property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSumCanceladasE() {
        return sumCanceladasE;
    }

    /**
     * Sets the value of the sumCanceladasE property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSumCanceladasE(BigDecimal value) {
        this.sumCanceladasE = value;
    }

    /**
     * Gets the value of the sumConfirmadasE property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSumConfirmadasE() {
        return sumConfirmadasE;
    }

    /**
     * Sets the value of the sumConfirmadasE property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSumConfirmadasE(BigDecimal value) {
        this.sumConfirmadasE = value;
    }

    /**
     * Gets the value of the sumDevolucionesE property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSumDevolucionesE() {
        return sumDevolucionesE;
    }

    /**
     * Sets the value of the sumDevolucionesE property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSumDevolucionesE(BigDecimal value) {
        this.sumDevolucionesE = value;
    }

    /**
     * Gets the value of the sumDevolucionesR property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSumDevolucionesR() {
        return sumDevolucionesR;
    }

    /**
     * Sets the value of the sumDevolucionesR property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSumDevolucionesR(BigDecimal value) {
        this.sumDevolucionesR = value;
    }

    /**
     * Gets the value of the sumEnviadasE property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSumEnviadasE() {
        return sumEnviadasE;
    }

    /**
     * Sets the value of the sumEnviadasE property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSumEnviadasE(BigDecimal value) {
        this.sumEnviadasE = value;
    }

    /**
     * Gets the value of the sumLiquidadasE property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSumLiquidadasE() {
        return sumLiquidadasE;
    }

    /**
     * Sets the value of the sumLiquidadasE property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSumLiquidadasE(BigDecimal value) {
        this.sumLiquidadasE = value;
    }

    /**
     * Gets the value of the sumLiquidadasR property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSumLiquidadasR() {
        return sumLiquidadasR;
    }

    /**
     * Sets the value of the sumLiquidadasR property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSumLiquidadasR(BigDecimal value) {
        this.sumLiquidadasR = value;
    }

    /**
     * Gets the value of the sumPendientesE property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSumPendientesE() {
        return sumPendientesE;
    }

    /**
     * Sets the value of the sumPendientesE property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSumPendientesE(BigDecimal value) {
        this.sumPendientesE = value;
    }

    /**
     * Gets the value of the sumPorEnviarE property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSumPorEnviarE() {
        return sumPorEnviarE;
    }

    /**
     * Sets the value of the sumPorEnviarE property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSumPorEnviarE(BigDecimal value) {
        this.sumPorEnviarE = value;
    }

    /**
     * Gets the value of the sumRechazadasAdapterE property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSumRechazadasAdapterE() {
        return sumRechazadasAdapterE;
    }

    /**
     * Sets the value of the sumRechazadasAdapterE property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSumRechazadasAdapterE(BigDecimal value) {
        this.sumRechazadasAdapterE = value;
    }

    /**
     * Gets the value of the sumRechazadasBanxicoE property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSumRechazadasBanxicoE() {
        return sumRechazadasBanxicoE;
    }

    /**
     * Sets the value of the sumRechazadasBanxicoE property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSumRechazadasBanxicoE(BigDecimal value) {
        this.sumRechazadasBanxicoE = value;
    }

    /**
     * Gets the value of the sumRecibidasR property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSumRecibidasR() {
        return sumRecibidasR;
    }

    /**
     * Sets the value of the sumRecibidasR property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSumRecibidasR(BigDecimal value) {
        this.sumRecibidasR = value;
    }

}
