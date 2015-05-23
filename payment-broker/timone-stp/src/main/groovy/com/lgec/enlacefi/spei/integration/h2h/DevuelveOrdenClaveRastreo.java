
package com.lgec.enlacefi.spei.integration.h2h;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for devuelveOrdenClaveRastreo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="devuelveOrdenClaveRastreo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="claveInstitucion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="claveRastreo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="causaDevolucion" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="nuevoRastreo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="medioEntrega" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="usuario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "devuelveOrdenClaveRastreo", propOrder = {
    "claveInstitucion",
    "claveRastreo",
    "causaDevolucion",
    "nuevoRastreo",
    "medioEntrega",
    "usuario"
})
public class DevuelveOrdenClaveRastreo {

    protected Integer claveInstitucion;
    protected String claveRastreo;
    protected Integer causaDevolucion;
    protected String nuevoRastreo;
    protected Integer medioEntrega;
    protected String usuario;

    /**
     * Gets the value of the claveInstitucion property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getClaveInstitucion() {
        return claveInstitucion;
    }

    /**
     * Sets the value of the claveInstitucion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setClaveInstitucion(Integer value) {
        this.claveInstitucion = value;
    }

    /**
     * Gets the value of the claveRastreo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaveRastreo() {
        return claveRastreo;
    }

    /**
     * Sets the value of the claveRastreo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaveRastreo(String value) {
        this.claveRastreo = value;
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

}
