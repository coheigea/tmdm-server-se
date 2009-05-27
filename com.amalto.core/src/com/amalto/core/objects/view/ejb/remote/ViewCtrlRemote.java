 /*
 * Generated by XDoclet - Do not edit!
 * this class was prodiuced by xdoclet automagically...
 */
package com.amalto.core.objects.view.ejb.remote;

import java.util.*;

/**
 * This class is remote adapter to ViewCtrl. It provides convenient way to access
 * facade session bean. Inverit from this class to provide reasonable caching and event handling capabilities.
 *
 * Remote facade for ViewCtrl.
 * @xdoclet-generated at 27-05-09
 * @copyright The XDoclet Team
 * @author XDoclet
 * @version ${version}
 */

public class ViewCtrlRemote extends Observable
{
    static ViewCtrlRemote _instance = null;
    public static ViewCtrlRemote getInstance() {
        if(_instance == null) {
	   _instance = new ViewCtrlRemote();
	}
	return _instance;
    }

  /**
   * cached remote session interface
   */
  com.amalto.core.objects.view.ejb.remote.ViewCtrl _session = null;
  /**
   * return session bean remote interface
   */
   protected com.amalto.core.objects.view.ejb.remote.ViewCtrl getSession() {
      try {
   	if(_session == null) {
	   _session = com.amalto.core.objects.view.ejb.local.ViewCtrlUtil.getHome().create();
	}
	return _session;
      } catch(Exception ex) {
        // just catch it here and return null.
        // somebody can provide better solution
	ex.printStackTrace();
	return null;
      }
   }

   public com.amalto.core.objects.view.ejb.ViewPOJOPK putView ( com.amalto.core.objects.view.ejb.ViewPOJO view )
	  throws com.amalto.core.util.XtentisException, java.rmi.RemoteException
   {
        com.amalto.core.objects.view.ejb.ViewPOJOPK retval;
       retval =  getSession().putView( view );

      return retval;

   }

   public com.amalto.core.objects.view.ejb.ViewPOJO getView ( com.amalto.core.objects.view.ejb.ViewPOJOPK pk )
	  throws com.amalto.core.util.XtentisException, java.rmi.RemoteException
   {
        com.amalto.core.objects.view.ejb.ViewPOJO retval;
       retval =  getSession().getView( pk );

      return retval;

   }

   public com.amalto.core.objects.view.ejb.ViewPOJO existsView ( com.amalto.core.objects.view.ejb.ViewPOJOPK pk )
	  throws com.amalto.core.util.XtentisException, java.rmi.RemoteException
   {
        com.amalto.core.objects.view.ejb.ViewPOJO retval;
       retval =  getSession().existsView( pk );

      return retval;

   }

   public com.amalto.core.objects.view.ejb.ViewPOJOPK removeView ( com.amalto.core.objects.view.ejb.ViewPOJOPK pk )
	  throws com.amalto.core.util.XtentisException, java.rmi.RemoteException
   {
        com.amalto.core.objects.view.ejb.ViewPOJOPK retval;
       retval =  getSession().removeView( pk );

      return retval;

   }

   public java.util.Collection getViewPKs ( java.lang.String regex )
	  throws com.amalto.core.util.XtentisException, java.rmi.RemoteException
   {
        java.util.Collection retval;
       retval =  getSession().getViewPKs( regex );

      return retval;

   }

   public java.util.ArrayList getAllViews ( java.lang.String regex )
	  throws com.amalto.core.util.XtentisException, java.rmi.RemoteException
   {
        java.util.ArrayList retval;
       retval =  getSession().getAllViews( regex );

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
