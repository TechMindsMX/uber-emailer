
package com.lgec.enlacefi.spei.integration.h2h;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for registraOrdenes complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="registraOrdenes">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ordenesPago" type="{http://h2h.integration.spei.enlacefi.lgec.com/}ordenPagoWS" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "registraOrdenes", propOrder = {
    "ordenesPago"
})
public class RegistraOrdenes {

    protected List<OrdenPagoWS> ordenesPago;

    /**
     * Gets the value of the ordenesPago property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ordenesPago property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrdenesPago().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrdenPagoWS }
     * 
     * 
     */
    public List<OrdenPagoWS> getOrdenesPago() {
        if (ordenesPago == null) {
            ordenesPago = new ArrayList<OrdenPagoWS>();
        }
        return this.ordenesPago;
    }

}
