
package com.lgec.enlacefi.spei.integration.h2h;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for devuelveOrdenRazonRechazo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="devuelveOrdenRazonRechazo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idOrden" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="causaDevolucion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="nuevoRastreo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="medioEntrega" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="usuario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="razonRechazo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "devuelveOrdenRazonRechazo", propOrder = {
    "idOrden",
    "causaDevolucion",
    "nuevoRastreo",
    "medioEntrega",
    "usuario",
    "razonRechazo"
})
public class DevuelveOrdenRazonRechazo {

    protected Integer idOrden;
    protected Integer causaDevolucion;
    protected String nuevoRastreo;
    protected Integer medioEntrega;
    protected String usuario;
    protected String razonRechazo;

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
     * Gets the value of the causaDevolucion property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCausaDevolucion() {
        return causaDevolucion;
    }

    /**
     * Sets the value of the causaDevolucion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCausaDevolucion(Integer value) {
        this.causaDevolucion = value;
    }

    /**
     * Gets the value of the nuevoRastreo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNuevoRastreo() {
        return nuevoRastreo;
    }

    /**
     * Sets the value of the nuevoRastreo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNuevoRastreo(String value) {
        this.nuevoRastreo = value;
    }

    /**
     * Gets the value of the medioEntrega property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMedioEntrega() {
        return medioEntrega;
    }

    /**
     * Sets the value of the medioEntrega property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMedioEntrega(Integer value) {
        this.medioEntrega = value;
    }

    /**
     * Gets the value of the usuario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Sets the value of the usuario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsuario(String value) {
        this.usuario = value;
    }

    /**
     * Gets the value of the razonRechazo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRazonRechazo() {
        return razonRechazo;
    }

    /**
     * Sets the value of the razonRechazo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRazonRechazo(String value) {
        this.razonRechazo = value;
    }

}
