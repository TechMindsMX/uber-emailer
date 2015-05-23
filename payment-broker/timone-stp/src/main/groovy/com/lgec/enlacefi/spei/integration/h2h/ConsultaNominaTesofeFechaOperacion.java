
package com.lgec.enlacefi.spei.integration.h2h;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for consultaNominaTesofeFechaOperacion complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="consultaNominaTesofeFechaOperacion">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="institucion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="fechaOperacion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "consultaNominaTesofeFechaOperacion", propOrder = {
    "institucion",
    "fechaOperacion"
})
public class ConsultaNominaTesofeFechaOperacion {

    protected Integer institucion;
    protected Integer fechaOperacion;

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

}
