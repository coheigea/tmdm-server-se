/*
 * Generated by XDoclet - Do not edit!
 */
package com.amalto.core.objects.routing.v2.ejb.remote;

/**
 * Home interface for RoutingRuleCtrl.
 * @xdoclet-generated at 31-08-09
 * @copyright The XDoclet Team
 * @author XDoclet
 * @version ${version}
 */
public interface RoutingRuleCtrlHome
   extends javax.ejb.EJBHome
{
   public static final String COMP_NAME="java:comp/env/ejb/RoutingRuleCtrl";
   public static final String JNDI_NAME="amalto/remote/core/routingrulectrl";

   public com.amalto.core.objects.routing.v2.ejb.remote.RoutingRuleCtrl create()
      throws javax.ejb.CreateException,java.rmi.RemoteException;

}
