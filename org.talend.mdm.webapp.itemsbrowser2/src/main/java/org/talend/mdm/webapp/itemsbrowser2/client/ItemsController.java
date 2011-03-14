// ============================================================================
//
// Copyright (C) 2006-2010 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.mdm.webapp.itemsbrowser2.client;

import org.talend.mdm.webapp.itemsbrowser2.client.i18n.MessagesFactory;
import org.talend.mdm.webapp.itemsbrowser2.client.model.ItemBean;
import org.talend.mdm.webapp.itemsbrowser2.client.util.Locale;
import org.talend.mdm.webapp.itemsbrowser2.client.util.UserSession;
import org.talend.mdm.webapp.itemsbrowser2.shared.EntityModel;
import org.talend.mdm.webapp.itemsbrowser2.shared.ViewBean;

import com.allen_sauer.gwt.log.client.Log;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ItemsController extends Controller {

    private ItemsView itemsView;

    private ItemsServiceAsync service;

    public ItemsController() {
        registerEventTypes(ItemsEvents.InitFrame);
        registerEventTypes(ItemsEvents.InitSearchContainer);
        registerEventTypes(ItemsEvents.GetView);
        registerEventTypes(ItemsEvents.SearchView);
        registerEventTypes(ItemsEvents.ViewItemForm);
        registerEventTypes(ItemsEvents.Error);
    }

    @Override
    public void initialize() {
        service = (ItemsServiceAsync) Registry.get(Itemsbrowser2.ITEMS_SERVICE);
        itemsView = new ItemsView(this);
    }

    @Override
    public void handleEvent(AppEvent event) {
        EventType type = event.getType();
        if (type == ItemsEvents.InitFrame) {
            forwardToView(itemsView, event);
        } else if (type == ItemsEvents.InitSearchContainer) {
            forwardToView(itemsView, event);
        } else if (type == ItemsEvents.GetView) {
            onGetView(event);
        } else if (event.getType() == ItemsEvents.SearchView) {
            onSearchView(event);
        } else if (event.getType() == ItemsEvents.ViewItemForm) {
            onViewItemForm(event);
        } else if (type == ItemsEvents.Error) {
            onError(event);
        }
    }

    protected void onViewItemForm(final AppEvent event) {
        // Log.info("View item's form... ");
        // in the controller of ViewItemForm event re-parse model, get-full item
        final ItemBean itemBean = event.getData();
        
        if(Itemsbrowser2.getSession().getAppHeader().isUsingDefaultForm()) {
            EntityModel entityModel = Itemsbrowser2.getSession().getCurrentEntityModel();
            service.getItem(itemBean, entityModel, new AsyncCallback<ItemBean>() {

                public void onFailure(Throwable caught) {
                    Dispatcher.forwardEvent(ItemsEvents.Error, caught);
                }

                public void onSuccess(ItemBean _itemBean) {
                    itemBean.copy(_itemBean);
                    forwardToView(itemsView, event);
                }

            });
        }else {
            forwardToView(itemsView, event);
        }
    }

    protected void onGetView(final AppEvent event) {
        Log.info("Get view... ");
        String viewName = event.getData();
        service.getView(viewName, Locale.getLanguage(Itemsbrowser2.getSession().getAppHeader()), new AsyncCallback<ViewBean>() {

            public void onSuccess(ViewBean view) {

                // Init CURRENT_VIEW
                Itemsbrowser2.getSession().put(UserSession.CURRENT_VIEW, view);

                // Init CURRENT_ENTITY_MODEL
                Itemsbrowser2.getSession().put(UserSession.CURRENT_ENTITY_MODEL, view.getBindingEntityModel());

                // forward
                AppEvent ae = new AppEvent(event.getType(), view);
                forwardToView(itemsView, ae);
            }

            public void onFailure(Throwable caught) {
                Dispatcher.forwardEvent(ItemsEvents.Error, caught);
            }
        });
    }

    protected void onSearchView(final AppEvent event) {
        Log.info("Do view-search... ");
        ViewBean viewBean = (ViewBean) Itemsbrowser2.getSession().getCurrentView();
        AppEvent ae = new AppEvent(event.getType(), viewBean);
        forwardToView(itemsView, ae);
    }

    protected void onError(AppEvent ae) {
        Log.error("error: " + ae.<Object> getData()); //$NON-NLS-1$
        MessageBox.alert(MessagesFactory.getMessages().error_title(), ae.<Object> getData().toString(), null);
    }

}
