/*
 * Generated by XDoclet - Do not edit!
 */
package com.amalto.core.objects.transformers.v2.ejb.local;

/**
 * Local home interface for TransformerV2Ctrl.
 * @xdoclet-generated at 27-05-09
 * @copyright The XDoclet Team
 * @author XDoclet
 * @version ${version}
 */
public interface TransformerV2CtrlLocalHome
   extends javax.ejb.EJBLocalHome
{
   public static final String COMP_NAME="java:comp/env/ejb/TransformerV2CtrlLocal";
   public static final String JNDI_NAME="amalto/local/core/transformerv2ctrl";

   public com.amalto.core.objects.transformers.v2.ejb.local.TransformerV2CtrlLocal create()
      throws javax.ejb.CreateException;

}
