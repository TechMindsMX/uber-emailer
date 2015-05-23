
package com.lgec.enlacefi.spei.integration.h2h;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for estadoOperacionesResponseWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="estadoOperacionesResponseWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="operacion" type="{http://h2h.integration.spei.enlacefi.lgec.com/}estadoOperacionesWS" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "estadoOperacionesResponseWS", propOrder = {
    "operacion"
})
public class EstadoOperacionesResponseWS {

    @XmlElement(nillable = true)
    protected List<EstadoOperacionesWS> operacion;

    /**
     * Gets the value of the operacion property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the operacion property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOperacion().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EstadoOperacionesWS }
     * 
     * 
     */
    public List<EstadoOperacionesWS> getOperacion() {
        if (operacion == null) {
            operacion = new ArrayList<EstadoOperacionesWS>();
        }
        return this.operacion;
    }

}
