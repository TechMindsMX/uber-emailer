
package com.lgec.enlacefi.spei.integration.h2h;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for registraOrdenesTraspasos complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="registraOrdenesTraspasos">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ordenesTraspasos" type="{http://h2h.integration.spei.enlacefi.lgec.com/}ordenesTraspasos" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "registraOrdenesTraspasos", propOrder = {
    "ordenesTraspasos"
})
public class RegistraOrdenesTraspasos {

    protected OrdenesTraspasos ordenesTraspasos;

    /**
     * Gets the value of the ordenesTraspasos property.
     * 
     * @return
     *     possible object is
     *     {@link OrdenesTraspasos }
     *     
     */
    public OrdenesTraspasos getOrdenesTraspasos() {
        return ordenesTraspasos;
    }

    /**
     * Sets the value of the ordenesTraspasos property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdenesTraspasos }
     *     
     */
    public void setOrdenesTraspasos(OrdenesTraspasos value) {
        this.ordenesTraspasos = value;
    }

}
