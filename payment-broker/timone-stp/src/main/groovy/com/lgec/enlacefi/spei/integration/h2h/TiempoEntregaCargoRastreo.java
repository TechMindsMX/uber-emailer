
package com.lgec.enlacefi.spei.integration.h2h;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tiempoEntregaCargoRastreo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="tiempoEntregaCargoRastreo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="fechaOperacion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="rastreo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="instOperante" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tiempoEntregaCargoRastreo", propOrder = {
    "fechaOperacion",
    "rastreo",
    "instOperante"
})
public class TiempoEntregaCargoRastreo {

    protected Integer fechaOperacion;
    protected String rastreo;
    protected Integer instOperante;

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
     * Gets the value of the rastreo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRastreo() {
        return rastreo;
    }

    /**
     * Sets the value of the rastreo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRastreo(String value) {
        this.rastreo = value;
    }

    /**
     * Gets the value of the instOperante property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInstOperante() {
        return instOperante;
    }

    /**
     * Sets the value of the instOperante property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInstOperante(Integer value) {
        this.instOperante = value;
    }

}
