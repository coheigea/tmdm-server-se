// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation 
// Generated source version: 1.1.2

package com.amalto.webapp.util.webservices;


public class WSSetTaskPriority {
    protected java.lang.String taskUUID;
    protected java.lang.String priority;
    
    public WSSetTaskPriority() {
    }
    
    public WSSetTaskPriority(java.lang.String taskUUID, java.lang.String priority) {
        this.taskUUID = taskUUID;
        this.priority = priority;
    }
    
    public java.lang.String getTaskUUID() {
        return taskUUID;
    }
    
    public void setTaskUUID(java.lang.String taskUUID) {
        this.taskUUID = taskUUID;
    }
    
    public java.lang.String getPriority() {
        return priority;
    }
    
    public void setPriority(java.lang.String priority) {
        this.priority = priority;
    }
}
