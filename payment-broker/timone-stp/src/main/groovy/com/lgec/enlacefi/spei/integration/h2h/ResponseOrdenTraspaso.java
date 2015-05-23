
package com.lgec.enlacefi.spei.integration.h2h;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for responseOrdenTraspaso complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="responseOrdenTraspaso">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="descripcionError" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="existeError" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="folioOrigen" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="responsesOrdenes" type="{http://h2h.integration.spei.enlacefi.lgec.com/}speiServiceFolioOrigenResponse" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="responsesTrapasos" type="{http://h2h.integration.spei.enlacefi.lgec.com/}speiServiceFolioOrigenResponse" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "responseOrdenTraspaso", propOrder = {
    "descripcionError",
    "existeError",
    "folioOrigen",
    "responsesOrdenes",
    "responsesTrapasos"
})
public class ResponseOrdenTraspaso {

    protected String descripcionError;
    protected Boolean existeError;
    protected String folioOrigen;
    @XmlElement(nillable = true)
    protected List<SpeiServiceFolioOrigenResponse> responsesOrdenes;
    @XmlElement(nillable = true)
    protected List<SpeiServiceFolioOrigenResponse> responsesTrapasos;

    /**
     * Gets the value of the descripcionError property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcionError() {
        return descripcionError;
    }

    /**
     * Sets the value of the descripcionError property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcionError(String value) {
        this.descripcionError = value;
    }

    /**
     * Gets the value of the existeError property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isExisteError() {
        return existeError;
    }

    /**
     * Sets the value of the existeError property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setExisteError(Boolean value) {
        this.existeError = value;
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
     * Gets the value of the responsesOrdenes property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the responsesOrdenes property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResponsesOrdenes().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SpeiServiceFolioOrigenResponse }
     * 
     * 
     */
    public List<SpeiServiceFolioOrigenResponse> getResponsesOrdenes() {
        if (responsesOrdenes == null) {
            responsesOrdenes = new ArrayList<SpeiServiceFolioOrigenResponse>();
        }
        return this.responsesOrdenes;
    }

    /**
     * Gets the value of the responsesTrapasos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the responsesTrapasos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResponsesTrapasos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SpeiServiceFolioOrigenResponse }
     * 
     * 
     */
    public List<SpeiServiceFolioOrigenResponse> getResponsesTrapasos() {
        if (responsesTrapasos == null) {
            responsesTrapasos = new ArrayList<SpeiServiceFolioOrigenResponse>();
        }
        return this.responsesTrapasos;
    }

}
