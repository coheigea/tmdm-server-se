/*
 * Generated by XDoclet - Do not edit!
 */
package com.amalto.core.objects.storedprocedure.ejb.remote;

/**
 * Home interface for StoredProcedureCtrl.
 * @xdoclet-generated at 31-08-09
 * @copyright The XDoclet Team
 * @author XDoclet
 * @version ${version}
 */
public interface StoredProcedureCtrlHome
   extends javax.ejb.EJBHome
{
   public static final String COMP_NAME="java:comp/env/ejb/StoredProcedureCtrl";
   public static final String JNDI_NAME="amalto/remote/core/storedprocedurectrl";

   public com.amalto.core.objects.storedprocedure.ejb.remote.StoredProcedureCtrl create()
      throws javax.ejb.CreateException,java.rmi.RemoteException;

}
