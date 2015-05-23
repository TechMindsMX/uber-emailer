
package com.lgec.enlacefi.spei.integration.h2h;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for estadoOperaciones complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="estadoOperaciones">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="empresa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="recorreSubempresas" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="estados" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoOperacion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "estadoOperaciones", propOrder = {
    "empresa",
    "recorreSubempresas",
    "estados",
    "tipoOperacion"
})
public class EstadoOperaciones {

    protected String empresa;
    protected boolean recorreSubempresas;
    protected String estados;
    protected String tipoOperacion;

    /**
     * Gets the value of the empresa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmpresa() {
        return empresa;
    }

    /**
     * Sets the value of the empresa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmpresa(String value) {
        this.empresa = value;
    }

    /**
     * Gets the value of the recorreSubempresas property.
     * 
     */
    public boolean isRecorreSubempresas() {
        return recorreSubempresas;
    }

    /**
     * Sets the value of the recorreSubempresas property.
     * 
     */
    public void setRecorreSubempresas(boolean value) {
        this.recorreSubempresas = value;
    }

    /**
     * Gets the value of the estados property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstados() {
        return estados;
    }

    /**
     * Sets the value of the estados property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstados(String value) {
        this.estados = value;
    }

    /**
     * Gets the value of the tipoOperacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoOperacion() {
        return tipoOperacion;
    }

    /**
     * Sets the value of the tipoOperacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoOperacion(String value) {
        this.tipoOperacion = value;
    }

}
