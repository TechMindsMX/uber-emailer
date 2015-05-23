
package com.lgec.enlacefi.spei.integration.h2h;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for traspasoWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="traspasoWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="claveRastreo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="estado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fechaOperacion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="folioOrigen" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="institucionCASFIM" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="institucionSpei" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="medioEntrega" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="monto" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="tipoTraspaso" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="tsLiquidacion" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "traspasoWS", propOrder = {
    "claveRastreo",
    "estado",
    "fechaOperacion",
    "folioOrigen",
    "institucionCASFIM",
    "institucionSpei",
    "medioEntrega",
    "monto",
    "tipoTraspaso",
    "tsLiquidacion"
})
public class TraspasoWS {

    protected String claveRastreo;
    protected String estado;
    protected Integer fechaOperacion;
    protected String folioOrigen;
    protected String institucionCASFIM;
    protected Integer institucionSpei;
    protected Integer medioEntrega;
    protected BigDecimal monto;
    protected Integer tipoTraspaso;
    protected Long tsLiquidacion;

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
     * Gets the value of the institucionCASFIM property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstitucionCASFIM() {
        return institucionCASFIM;
    }

    /**
     * Sets the value of the institucionCASFIM property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstitucionCASFIM(String value) {
        this.institucionCASFIM = value;
    }

    /**
     * Gets the value of the institucionSpei property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInstitucionSpei() {
        return institucionSpei;
    }

    /**
     * Sets the value of the institucionSpei property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInstitucionSpei(Integer value) {
        this.institucionSpei = value;
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
     * Gets the value of the tipoTraspaso property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTipoTraspaso() {
        return tipoTraspaso;
    }

    /**
     * Sets the value of the tipoTraspaso property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTipoTraspaso(Integer value) {
        this.tipoTraspaso = value;
    }

    /**
     * Gets the value of the tsLiquidacion property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTsLiquidacion() {
        return tsLiquidacion;
    }

    /**
     * Sets the value of the tsLiquidacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTsLiquidacion(Long value) {
        this.tsLiquidacion = value;
    }

}
