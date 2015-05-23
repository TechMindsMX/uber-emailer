
package com.lgec.enlacefi.spei.integration.h2h;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for registraTraspaso complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="registraTraspaso">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="traspaso" type="{http://h2h.integration.spei.enlacefi.lgec.com/}traspasoWS" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "registraTraspaso", propOrder = {
    "traspaso"
})
public class RegistraTraspaso {

    protected TraspasoWS traspaso;

    /**
     * Gets the value of the traspaso property.
     * 
     * @return
     *     possible object is
     *     {@link TraspasoWS }
     *     
     */
    public TraspasoWS getTraspaso() {
        return traspaso;
    }

    /**
     * Sets the value of the traspaso property.
     * 
     * @param value
     *     allowed object is
     *     {@link TraspasoWS }
     *     
     */
    public void setTraspaso(TraspasoWS value) {
        this.traspaso = value;
    }

}
