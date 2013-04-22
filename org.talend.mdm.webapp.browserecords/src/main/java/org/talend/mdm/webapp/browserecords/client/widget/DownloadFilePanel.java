// ============================================================================
//
// Copyright (C) 2006-2012 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.mdm.webapp.browserecords.client.widget;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.talend.mdm.webapp.base.client.model.DataTypeConstants;
import org.talend.mdm.webapp.base.client.model.ItemBaseModel;
import org.talend.mdm.webapp.base.client.util.PostDataUtil;
import org.talend.mdm.webapp.base.shared.TypeModel;
import org.talend.mdm.webapp.browserecords.client.BrowseRecords;
import org.talend.mdm.webapp.browserecords.client.i18n.MessagesFactory;
import org.talend.mdm.webapp.browserecords.client.model.ItemBean;
import org.talend.mdm.webapp.browserecords.client.model.QueryModel;
import org.talend.mdm.webapp.browserecords.client.util.CommonUtil;
import org.talend.mdm.webapp.browserecords.client.util.LabelUtil;
import org.talend.mdm.webapp.browserecords.shared.EntityModel;
import org.talend.mdm.webapp.browserecords.shared.ViewBean;

import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.CheckBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FormData;

/**
 * DOC Administrator class global comment. Detailled comment
 */
public class DownloadFilePanel extends FormPanel {

    private final String SPLIT_CHARACTER = "@@"; //$NON-NLS-1$

    private TextField<String> fileName;

    private CheckBox fkResovled;

    private ComboBox<ItemBaseModel> fkDisplayCombo;

    private QueryModel queryModel;

    private Button exportBtn;

    private Window window;

    private ViewBean viewBean = BrowseRecords.getSession().getCurrentView();

    public DownloadFilePanel(QueryModel queryModel, Window window) {
        this.queryModel = queryModel;
        this.window = window;

        this.setFrame(false);
        this.setHeaderVisible(false);
        this.setWidth("100%"); //$NON-NLS-1$

        fileName = new TextField<String>();
        fileName.setFieldLabel(MessagesFactory.getMessages().picture_field_label());
        fileName.setAllowBlank(false);
        fileName.setValue(viewBean.getBindingEntityModel().getConceptName());
        this.add(fileName, new FormData("90%")); //$NON-NLS-1$

        fkResovled = new CheckBox();
        fkResovled.setFieldLabel(MessagesFactory.getMessages().fkinfo_display_label());
        fkResovled.setLabelStyle("width:90px"); //$NON-NLS-1$
        fkResovled.addListener(Events.Change, new Listener<BaseEvent>() {

            public void handleEvent(BaseEvent be) {
                if (fkResovled.getValue().booleanValue()) {
                    fkDisplayCombo.setEnabled(true);
                } else {
                    fkDisplayCombo.setEnabled(false);
                }
            }
        });

        this.add(fkResovled, new FormData("90%")); //$NON-NLS-1$

        List<ItemBaseModel> fkDisplayList = new ArrayList<ItemBaseModel>();
        ItemBaseModel idPlusInfo = new ItemBaseModel();
        idPlusInfo.set("label", "Id-FKInfo"); //$NON-NLS-1$ //$NON-NLS-2$
        idPlusInfo.set("key", "Id-FKInfo"); //$NON-NLS-1$ //$NON-NLS-2$
        fkDisplayList.add(idPlusInfo);

        ItemBaseModel infoOnly = new ItemBaseModel();
        infoOnly.set("label", "FKInfo"); //$NON-NLS-1$ //$NON-NLS-2$
        infoOnly.set("key", "FKInfo"); //$NON-NLS-1$ //$NON-NLS-2$
        fkDisplayList.add(infoOnly);

        ListStore<ItemBaseModel> fkDisplayStoreList = new ListStore<ItemBaseModel>();
        fkDisplayStoreList.add(fkDisplayList);

        fkDisplayCombo = new ComboBox<ItemBaseModel>();
        fkDisplayCombo.setId("fkDisplay");//$NON-NLS-1$
        fkDisplayCombo.setName("fkDisplay");//$NON-NLS-1$
        fkDisplayCombo.setFieldLabel(MessagesFactory.getMessages().fkinfo_display_type_label());
        fkDisplayCombo.setDisplayField("label"); //$NON-NLS-1$
        fkDisplayCombo.setValueField("key"); //$NON-NLS-1$
        fkDisplayCombo.setStore(fkDisplayStoreList);
        fkDisplayCombo.setTriggerAction(TriggerAction.ALL);
        fkDisplayCombo.setEnabled(false);
        fkDisplayCombo.setSelection(fkDisplayList);
        this.add(fkDisplayCombo, new FormData("90%")); //$NON-NLS-1$

        exportBtn = new Button(MessagesFactory.getMessages().export_btn());
        exportBtn.addSelectionListener(new SelectionListener<ButtonEvent>() {

            @Override
            public void componentSelected(ButtonEvent ce) {
                if (!DownloadFilePanel.this.isValid()) {
                    return;
                }
                Map<String, String> param = buildExportParameter();
                PostDataUtil.postData("/browserecords/download", param); //$NON-NLS-1$
                DownloadFilePanel.this.window.hide();
            }
        });
        this.add(exportBtn);
    }

    private Map<String, String> buildExportParameter() {
        List<String> viewableXpaths = viewBean.getViewableXpaths();
        EntityModel entityModel = viewBean.getBindingEntityModel();
        Map<String, TypeModel> dataTypes = entityModel.getMetaDataTypes();

        List<String> headerList = new ArrayList<String>();
        List<String> xPathList = new ArrayList<String>();
        List<String> fkColXPathList = new ArrayList<String>();
        List<String> fkInfoList = new ArrayList<String>();

        for (String xpath : viewableXpaths) {
            TypeModel typeModel = dataTypes.get(xpath);
            if (typeModel != null) {
                String header = CommonUtil.getDownloadFileHeadName(typeModel);
                headerList.add(header);
                xPathList.add(xpath);
                if (typeModel.getForeignkey() != null) {
                    fkColXPathList.add(xpath + "," + typeModel.getForeignkey()); //$NON-NLS-1$
                    List<String> fkInfo = typeModel.getForeignKeyInfo();
                    if (fkInfo.size() == 0) {
                        fkInfoList.add(" "); //$NON-NLS-1$
                    } else {
                        StringBuilder sb = new StringBuilder(fkInfo.get(0));
                        for (int i = 1; i < fkInfo.size(); i++) {
                            sb.append(",").append(fkInfo.get(i)); //$NON-NLS-1$
                        }
                        fkInfoList.add(sb.toString());
                    }
                }
            }
        }

        List<String> selectItemXmlList = new ArrayList<String>();
        Grid<ItemBean> grid = ItemsListPanel.getInstance().getGrid();
        if (grid != null) {
            List<ItemBean> selectItemList = grid.getSelectionModel().getSelectedItems();
            for (int i = 0; i < selectItemList.size(); i++) {
                selectItemXmlList.add(selectItemList.get(i).getItemXml());
            }
        }

        Map<String, String> param = new HashMap<String, String>();

        queryModel.getModel();
        queryModel.getCriteria();
        queryModel.getLanguage();

        param.put("fileName", fileName.getValue()); //$NON-NLS-1$
        param.put("fkResovled", fkResovled.getValue().toString()); //$NON-NLS-1$
        param.put("fkDisplay", fkDisplayCombo.getValue().get("key").toString()); //$NON-NLS-1$ //$NON-NLS-2$
        param.put("tableName", viewBean.getViewPK()); //$NON-NLS-1$
        param.put("header", LabelUtil.convertList2String(headerList, SPLIT_CHARACTER)); //$NON-NLS-1$ 
        param.put("xpath", LabelUtil.convertList2String(xPathList, SPLIT_CHARACTER)); //$NON-NLS-1$ 
        param.put("fkColXPath", LabelUtil.convertList2String(fkColXPathList, SPLIT_CHARACTER)); //$NON-NLS-1$ 
        param.put("fkInfo", LabelUtil.convertList2String(fkInfoList, SPLIT_CHARACTER)); //$NON-NLS-1$ 
        param.put("itemXmlString", selectItemXmlList.size() > 0 ? "<results>" + LabelUtil.convertList2String(selectItemXmlList, "") + "</results>" : ""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$

        param.put("dataCluster", queryModel.getDataClusterPK()); //$NON-NLS-1$
        param.put("viewPk", queryModel.getView().getViewPK()); //$NON-NLS-1$
        param.put("criteria", queryModel.getCriteria()); //$NON-NLS-1$
        param.put("language", queryModel.getLanguage()); //$NON-NLS-1$

        PagingLoadConfig pagingLoad = queryModel.getPagingLoadConfig();
        String sortDir = null;
        if (SortDir.ASC.equals(pagingLoad.getSortDir())) {
            sortDir = "ascending"; //$NON-NLS-1$
        }
        if (SortDir.DESC.equals(pagingLoad.getSortDir())) {
            sortDir = "descending"; //$NON-NLS-1$
        }
        Map<String, TypeModel> types = queryModel.getModel().getMetaDataTypes();
        TypeModel typeModel = types.get(pagingLoad.getSortField());
        if (typeModel != null) {
            if (DataTypeConstants.INTEGER.getTypeName().equals(typeModel.getType().getBaseTypeName())
                    || DataTypeConstants.INT.getTypeName().equals(typeModel.getType().getBaseTypeName())
                    || DataTypeConstants.LONG.getTypeName().equals(typeModel.getType().getBaseTypeName())
                    || DataTypeConstants.DECIMAL.getTypeName().equals(typeModel.getType().getBaseTypeName())
                    || DataTypeConstants.FLOAT.getTypeName().equals(typeModel.getType().getBaseTypeName())
                    || DataTypeConstants.DOUBLE.getTypeName().equals(typeModel.getType().getBaseTypeName())) {
                sortDir = "NUMBER:" + sortDir; //$NON-NLS-1$
            }
        }

        if (pagingLoad.getSortField() != null) {
            param.put("sortField", pagingLoad.getSortField()); //$NON-NLS-1$
        }
        if (sortDir != null) {
            param.put("sortDir", sortDir); //$NON-NLS-1$
        }
        param.put("offset", Integer.toString(pagingLoad.getOffset())); //$NON-NLS-1$
        param.put("limit", Integer.toString(pagingLoad.getLimit())); //$NON-NLS-1$
        return param;
    }
}
