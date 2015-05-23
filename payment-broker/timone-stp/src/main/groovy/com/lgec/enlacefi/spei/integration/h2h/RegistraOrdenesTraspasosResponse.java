
package com.lgec.enlacefi.spei.integration.h2h;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for registraOrdenesTraspasosResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="registraOrdenesTraspasosResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://h2h.integration.spei.enlacefi.lgec.com/}responseOrdenTraspaso" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "registraOrdenesTraspasosResponse", propOrder = {
    "_return"
})
public class RegistraOrdenesTraspasosResponse {

    @XmlElement(name = "return")
    protected ResponseOrdenTraspaso _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseOrdenTraspaso }
     *     
     */
    public ResponseOrdenTraspaso getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseOrdenTraspaso }
     *     
     */
    public void setReturn(ResponseOrdenTraspaso value) {
        this._return = value;
    }

}
