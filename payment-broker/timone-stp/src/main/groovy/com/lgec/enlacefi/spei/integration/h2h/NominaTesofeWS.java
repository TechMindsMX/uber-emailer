
package com.lgec.enlacefi.spei.integration.h2h;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for nominaTesofeWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="nominaTesofeWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="montoPagos" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="numeroPagos" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "nominaTesofeWS", propOrder = {
    "montoPagos",
    "numeroPagos"
})
public class NominaTesofeWS {

    protected BigDecimal montoPagos;
    protected Integer numeroPagos;

    /**
     * Gets the value of the montoPagos property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMontoPagos() {
        return montoPagos;
    }

    /**
     * Sets the value of the montoPagos property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMontoPagos(BigDecimal value) {
        this.montoPagos = value;
    }

    /**
     * Gets the value of the numeroPagos property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumeroPagos() {
        return numeroPagos;
    }

    /**
     * Sets the value of the numeroPagos property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumeroPagos(Integer value) {
        this.numeroPagos = value;
    }

}
