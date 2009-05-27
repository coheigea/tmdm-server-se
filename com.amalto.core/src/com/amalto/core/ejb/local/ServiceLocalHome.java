/*
 * Generated by XDoclet - Do not edit!
 */
package com.amalto.core.ejb.local;

/**
 * Local home interface for Service.
 * @xdoclet-generated at 27-05-09
 * @copyright The XDoclet Team
 * @author XDoclet
 * @version ${version}
 */
public interface ServiceLocalHome
   extends javax.ejb.EJBLocalHome
{
   public static final String COMP_NAME="java:comp/env/ejb/ServiceLocal";
   public static final String JNDI_NAME="amalto/local/core/service";

   public com.amalto.core.ejb.local.ServiceLocal create(com.amalto.core.ejb.remote.ServiceValue vo)
      throws javax.ejb.CreateException;

   public com.amalto.core.ejb.local.ServiceLocal findByPrimaryKey(com.amalto.core.ejb.remote.ServicePK primaryKey)
      throws javax.ejb.FinderException;

   public com.amalto.core.ejb.local.ServiceLocal findIfExists(com.amalto.core.ejb.remote.ServicePK primaryKey)
      throws javax.ejb.FinderException;

   public java.util.Collection findAll()
      throws javax.ejb.FinderException;

}
