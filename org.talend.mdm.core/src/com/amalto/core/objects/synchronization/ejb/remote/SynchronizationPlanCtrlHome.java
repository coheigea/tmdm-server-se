/*
 * Generated by XDoclet - Do not edit!
 */
package com.amalto.core.objects.synchronization.ejb.remote;

/**
 * Home interface for SynchronizationPlanCtrl.
 * @xdoclet-generated at 31-08-09
 * @copyright The XDoclet Team
 * @author XDoclet
 * @version ${version}
 */
public interface SynchronizationPlanCtrlHome
   extends javax.ejb.EJBHome
{
   public static final String COMP_NAME="java:comp/env/ejb/SynchronizationPlanCtrl";
   public static final String JNDI_NAME="amalto/remote/core/synchronizationPlanctrl";

   public com.amalto.core.objects.synchronization.ejb.remote.SynchronizationPlanCtrl create()
      throws javax.ejb.CreateException,java.rmi.RemoteException;

}
