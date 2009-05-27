 /*
 * Generated by XDoclet - Do not edit!
 * this class was prodiuced by xdoclet automagically...
 */
package com.amalto.core.objects.datamodel.ejb.remote;

import java.util.*;

/**
 * This class is remote adapter to DataModelCtrl. It provides convenient way to access
 * facade session bean. Inverit from this class to provide reasonable caching and event handling capabilities.
 *
 * Remote facade for DataModelCtrl.
 * @xdoclet-generated at 27-05-09
 * @copyright The XDoclet Team
 * @author XDoclet
 * @version ${version}
 */

public class DataModelCtrlRemote extends Observable
{
    static DataModelCtrlRemote _instance = null;
    public static DataModelCtrlRemote getInstance() {
        if(_instance == null) {
	   _instance = new DataModelCtrlRemote();
	}
	return _instance;
    }

  /**
   * cached remote session interface
   */
  com.amalto.core.objects.datamodel.ejb.remote.DataModelCtrl _session = null;
  /**
   * return session bean remote interface
   */
   protected com.amalto.core.objects.datamodel.ejb.remote.DataModelCtrl getSession() {
      try {
   	if(_session == null) {
	   _session = com.amalto.core.objects.datamodel.ejb.local.DataModelCtrlUtil.getHome().create();
	}
	return _session;
      } catch(Exception ex) {
        // just catch it here and return null.
        // somebody can provide better solution
	ex.printStackTrace();
	return null;
      }
   }

   public com.amalto.core.objects.datamodel.ejb.DataModelPOJOPK putDataModel ( com.amalto.core.objects.datamodel.ejb.DataModelPOJO dataModel )
	  throws com.amalto.core.util.XtentisException, java.rmi.RemoteException
   {
        com.amalto.core.objects.datamodel.ejb.DataModelPOJOPK retval;
       retval =  getSession().putDataModel( dataModel );

      return retval;

   }

   public com.amalto.core.objects.datamodel.ejb.DataModelPOJO getDataModel ( com.amalto.core.objects.datamodel.ejb.DataModelPOJOPK pk )
	  throws com.amalto.core.util.XtentisException, java.rmi.RemoteException
   {
        com.amalto.core.objects.datamodel.ejb.DataModelPOJO retval;
       retval =  getSession().getDataModel( pk );

      return retval;

   }

   public com.amalto.core.objects.datamodel.ejb.DataModelPOJO existsDataModel ( com.amalto.core.objects.datamodel.ejb.DataModelPOJOPK pk )
	  throws com.amalto.core.util.XtentisException, java.rmi.RemoteException
   {
        com.amalto.core.objects.datamodel.ejb.DataModelPOJO retval;
       retval =  getSession().existsDataModel( pk );

      return retval;

   }

   public com.amalto.core.objects.datamodel.ejb.DataModelPOJOPK removeDataModel ( com.amalto.core.objects.datamodel.ejb.DataModelPOJOPK pk )
	  throws com.amalto.core.util.XtentisException, java.rmi.RemoteException
   {
        com.amalto.core.objects.datamodel.ejb.DataModelPOJOPK retval;
       retval =  getSession().removeDataModel( pk );

      return retval;

   }

   public java.util.Collection getDataModelPKs ( java.lang.String regex )
	  throws com.amalto.core.util.XtentisException, java.rmi.RemoteException
   {
        java.util.Collection retval;
       retval =  getSession().getDataModelPKs( regex );

      return retval;

   }

   public java.lang.String checkSchema ( java.lang.String schema )
	  throws com.amalto.core.util.XtentisException, java.rmi.RemoteException
   {
        java.lang.String retval;
       retval =  getSession().checkSchema( schema );

      return retval;

   }

   public java.lang.String putBusinessConceptSchema ( com.amalto.core.objects.datamodel.ejb.DataModelPOJOPK pk,java.lang.String conceptSchemaString )
	  throws com.amalto.core.util.XtentisException, java.rmi.RemoteException
   {
        java.lang.String retval;
       retval =  getSession().putBusinessConceptSchema( pk,conceptSchemaString );

      return retval;

   }

   public java.lang.String deleteBusinessConcept ( com.amalto.core.objects.datamodel.ejb.DataModelPOJOPK pk,java.lang.String businessConceptName )
	  throws com.amalto.core.util.XtentisException, java.rmi.RemoteException
   {
        java.lang.String retval;
       retval =  getSession().deleteBusinessConcept( pk,businessConceptName );

      return retval;

   }

   public java.lang.String[] getAllBusinessConceptsNames ( com.amalto.core.objects.datamodel.ejb.DataModelPOJOPK pk )
	  throws com.amalto.core.util.XtentisException, java.rmi.RemoteException
   {
        java.lang.String[] retval;
       retval =  getSession().getAllBusinessConceptsNames( pk );

      return retval;

   }

  /**
   * override this method to provide feedback to interested objects
   * in case collections were changed.
   */
  public void invalidate() {

  	setChanged();
	notifyObservers();
  }
}
