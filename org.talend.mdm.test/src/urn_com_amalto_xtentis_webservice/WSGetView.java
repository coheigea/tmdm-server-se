/**
 * WSGetView.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package urn_com_amalto_xtentis_webservice;

public class WSGetView  implements java.io.Serializable {
    private urn_com_amalto_xtentis_webservice.WSViewPK wsViewPK;

    public WSGetView() {
    }

    public WSGetView(
           urn_com_amalto_xtentis_webservice.WSViewPK wsViewPK) {
           this.wsViewPK = wsViewPK;
    }


    /**
     * Gets the wsViewPK value for this WSGetView.
     * 
     * @return wsViewPK
     */
    public urn_com_amalto_xtentis_webservice.WSViewPK getWsViewPK() {
        return wsViewPK;
    }


    /**
     * Sets the wsViewPK value for this WSGetView.
     * 
     * @param wsViewPK
     */
    public void setWsViewPK(urn_com_amalto_xtentis_webservice.WSViewPK wsViewPK) {
        this.wsViewPK = wsViewPK;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WSGetView)) return false;
        WSGetView other = (WSGetView) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.wsViewPK==null && other.getWsViewPK()==null) || 
             (this.wsViewPK!=null &&
              this.wsViewPK.equals(other.getWsViewPK())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getWsViewPK() != null) {
            _hashCode += getWsViewPK().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WSGetView.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("urn-com-amalto-xtentis-webservice", "WSGetView"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsViewPK");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsViewPK"));
        elemField.setXmlType(new javax.xml.namespace.QName("urn-com-amalto-xtentis-webservice", "WSViewPK"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
