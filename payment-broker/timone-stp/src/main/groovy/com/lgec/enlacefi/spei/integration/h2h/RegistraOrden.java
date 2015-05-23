
package com.lgec.enlacefi.spei.integration.h2h;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for registraOrden complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="registraOrden">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ordenPago" type="{http://h2h.integration.spei.enlacefi.lgec.com/}ordenPagoWS" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "registraOrden", propOrder = {
    "ordenPago"
})
public class RegistraOrden {

    protected OrdenPagoWS ordenPago;

    /**
     * Gets the value of the ordenPago property.
     * 
     * @return
     *     possible object is
     *     {@link OrdenPagoWS }
     *     
     */
    public OrdenPagoWS getOrdenPago() {
        return ordenPago;
    }

    /**
     * Sets the value of the ordenPago property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdenPagoWS }
     *     
     */
    public void setOrdenPago(OrdenPagoWS value) {
        this.ordenPago = value;
    }

}
