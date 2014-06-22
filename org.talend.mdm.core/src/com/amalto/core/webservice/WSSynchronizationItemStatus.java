// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation （1.1.2_01，编译版 R40）
// Generated source version: 1.1.2

package com.amalto.core.webservice;


import java.util.Map;
import java.util.HashMap;

public class WSSynchronizationItemStatus {
    private java.lang.String value;
    private static Map valueMap = new HashMap();
    public static final String _PENDINGString = "PENDING";
    public static final String _MANUALString = "MANUAL";
    public static final String _RESOLVEDString = "RESOLVED";
    public static final String _EXECUTEDString = "EXECUTED";
    
    public static final java.lang.String _PENDING = new java.lang.String(_PENDINGString);
    public static final java.lang.String _MANUAL = new java.lang.String(_MANUALString);
    public static final java.lang.String _RESOLVED = new java.lang.String(_RESOLVEDString);
    public static final java.lang.String _EXECUTED = new java.lang.String(_EXECUTEDString);
    
    public static final WSSynchronizationItemStatus PENDING = new WSSynchronizationItemStatus(_PENDING);
    public static final WSSynchronizationItemStatus MANUAL = new WSSynchronizationItemStatus(_MANUAL);
    public static final WSSynchronizationItemStatus RESOLVED = new WSSynchronizationItemStatus(_RESOLVED);
    public static final WSSynchronizationItemStatus EXECUTED = new WSSynchronizationItemStatus(_EXECUTED);
    
    protected WSSynchronizationItemStatus(java.lang.String value) {
        this.value = value;
        valueMap.put(this.toString(), this);
    }
    
    public java.lang.String getValue() {
        return value;
    }
    
    public static WSSynchronizationItemStatus fromValue(java.lang.String value)
        throws java.lang.IllegalStateException {
        if (PENDING.value.equals(value)) {
            return PENDING;
        } else if (MANUAL.value.equals(value)) {
            return MANUAL;
        } else if (RESOLVED.value.equals(value)) {
            return RESOLVED;
        } else if (EXECUTED.value.equals(value)) {
            return EXECUTED;
        }
        throw new IllegalArgumentException();
    }
    
    public static WSSynchronizationItemStatus fromString(String value)
        throws java.lang.IllegalStateException {
        WSSynchronizationItemStatus ret = (WSSynchronizationItemStatus)valueMap.get(value);
        if (ret != null) {
            return ret;
        }
        if (value.equals(_PENDINGString)) {
            return PENDING;
        } else if (value.equals(_MANUALString)) {
            return MANUAL;
        } else if (value.equals(_RESOLVEDString)) {
            return RESOLVED;
        } else if (value.equals(_EXECUTEDString)) {
            return EXECUTED;
        }
        throw new IllegalArgumentException();
    }
    
    public String toString() {
        return value.toString();
    }
    
    private Object readResolve()
        throws java.io.ObjectStreamException {
        return fromValue(getValue());
    }
    
    public boolean equals(Object obj) {
        if (!(obj instanceof WSSynchronizationItemStatus)) {
            return false;
        }
        return ((WSSynchronizationItemStatus)obj).value.equals(value);
    }
    
    public int hashCode() {
        return value.hashCode();
    }
}
