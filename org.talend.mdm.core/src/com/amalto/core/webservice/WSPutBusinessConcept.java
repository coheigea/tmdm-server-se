// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation （1.1.2_01，编译版 R40）
// Generated source version: 1.1.2

package com.amalto.core.webservice;


public class WSPutBusinessConcept {
    protected com.amalto.core.webservice.WSDataModelPK wsDataModelPK;
    protected com.amalto.core.webservice.WSBusinessConcept businessConcept;
    
    public WSPutBusinessConcept() {
    }
    
    public WSPutBusinessConcept(com.amalto.core.webservice.WSDataModelPK wsDataModelPK, com.amalto.core.webservice.WSBusinessConcept businessConcept) {
        this.wsDataModelPK = wsDataModelPK;
        this.businessConcept = businessConcept;
    }
    
    public com.amalto.core.webservice.WSDataModelPK getWsDataModelPK() {
        return wsDataModelPK;
    }
    
    public void setWsDataModelPK(com.amalto.core.webservice.WSDataModelPK wsDataModelPK) {
        this.wsDataModelPK = wsDataModelPK;
    }
    
    public com.amalto.core.webservice.WSBusinessConcept getBusinessConcept() {
        return businessConcept;
    }
    
    public void setBusinessConcept(com.amalto.core.webservice.WSBusinessConcept businessConcept) {
        this.businessConcept = businessConcept;
    }
}
