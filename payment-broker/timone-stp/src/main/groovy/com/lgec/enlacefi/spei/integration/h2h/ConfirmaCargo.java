
package com.lgec.enlacefi.spei.integration.h2h;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for confirmaCargo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="confirmaCargo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idOrden" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="razonCancelacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nuevoEstado" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="folio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "confirmaCargo", propOrder = {
    "idOrden",
    "razonCancelacion",
    "nuevoEstado",
    "folio"
})
public class ConfirmaCargo {

    protected Integer idOrden;
    protected String razonCancelacion;
    protected String nuevoEstado;
    protected String folio;

    /**
     * Gets the value of the idOrden property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getIdOrden() {
        return idOrden;
    }

    /**
     * Sets the value of the idOrden property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setIdOrden(Integer value) {
        this.idOrden = value;
    }

    /**
     * Gets the value of the razonCancelacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRazonCancelacion() {
        return razonCancelacion;
    }

    /**
     * Sets the value of the razonCancelacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRazonCancelacion(String value) {
        this.razonCancelacion = value;
    }

    /**
     * Gets the value of the nuevoEstado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNuevoEstado() {
        return nuevoEstado;
    }

    /**
     * Sets the value of the nuevoEstado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNuevoEstado(String value) {
        this.nuevoEstado = value;
    }

    /**
     * Gets the value of the folio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFolio() {
        return folio;
    }

    /**
     * Sets the value of the folio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFolio(String value) {
        this.folio = value;
    }

}
