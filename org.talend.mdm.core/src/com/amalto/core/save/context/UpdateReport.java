/*
 * Copyright (C) 2006-2019 Talend Inc. - www.talend.com
 *
 * This source code is available under agreement available at
 * %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
 *
 * You should have received a copy of the agreement
 * along with this program; if not, write to Talend SA
 * 9 rue Pages 92150 Suresnes, France
 */

package com.amalto.core.save.context;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.talend.mdm.commmon.metadata.ComplexTypeMetadata;
import org.w3c.dom.Document;

import com.amalto.core.history.Action;
import com.amalto.core.history.MutableDocument;
import com.amalto.core.history.accessor.Accessor;
import com.amalto.core.objects.UpdateReportPOJO;
import com.amalto.core.save.DocumentSaverContext;
import com.amalto.core.save.SaverSession;
import com.amalto.core.util.Util;

class UpdateReport implements DocumentSaver {

    public static final String UPDATE_REPORT_DATA_MODEL = "UpdateReport"; //$NON-NLS-1$

    public static final String UPDATE_REPORT_TYPE = "Update"; //$NON-NLS-1$

    private final DocumentSaver next;

    UpdateReport(DocumentSaver next) {
        this.next = next;
    }

    @Override
    public void save(SaverSession session, DocumentSaverContext context) {
        UpdateReportDocument updateReportDocument;
        Document updateReportAsDOM = (Document) SaverContextFactory.EMPTY_UPDATE_REPORT.cloneNode(true);
        if (context.isInvokeBeforeSaving()) {
            if (context.getUpdateReportDocument() != null) {
                updateReportAsDOM = context.getUpdateReportDocument().asDOM();
            }
        }       
        updateReportDocument = new UpdateReportDocument(updateReportAsDOM);
        
        StringBuilder key = new StringBuilder();
        String[] ids = context.getId();
        for (int i = 0; i < ids.length; i++) {
            key.append(ids[i]);
            if (i < ids.length - 1) {
                key.append('.');
            }
        }

        ComplexTypeMetadata type = context.getUserDocument().getType();
        List<Action> actions = context.getActions();
        boolean hasHeader = false;
        for (Action action : actions) {
            if (!hasHeader) {
                setHeader(updateReportDocument, "UserName", session.getSaverSource().getLegitimateUser()); //$NON-NLS-1$
                setHeader(updateReportDocument, "Source", String.valueOf(action.getSource())); //$NON-NLS-1$
                setHeader(updateReportDocument, "TimeInMillis", String.valueOf(action.getDate().getTime())); //$NON-NLS-1$
                setHeader(updateReportDocument, "UUID", UUID.randomUUID().toString()); //$NON-NLS-1$
                setHeader(updateReportDocument, "DataCluster", String.valueOf(context.getDataCluster())); //$NON-NLS-1$
                setHeader(updateReportDocument, "DataModel", String.valueOf(context.getDataModelName())); //$NON-NLS-1$
                setHeader(updateReportDocument, "Concept", String.valueOf(type.getName())); //$NON-NLS-1$
                setHeader(updateReportDocument, "Key", key.toString()); //$NON-NLS-1$
                setHeaderForPrimaryKeyInfo(updateReportDocument, context);
                hasHeader = true;
                updateReportDocument.enableRecordFieldChange();
            }
            action.perform(updateReportDocument);
            action.undo(updateReportDocument);
        }
        if (!updateReportDocument.isCreated()) {
            updateReportDocument.setOperationType(UpdateReportPOJO.OPERATION_TYPE_UPDATE);
        }
        updateReportDocument.disableRecordFieldChange();

        context.setUpdateReportDocument(updateReportDocument);
        next.save(session, context);
    }

    private void setHeaderForPrimaryKeyInfo(MutableDocument updateReportDocument, DocumentSaverContext context) {
        String primaryKeyInfo = Util.getPrimaryKeyInfo(context);
        if (StringUtils.isNotBlank(primaryKeyInfo)) {
            Accessor accessor = updateReportDocument.createAccessor("PrimaryKeyInfo"); //$NON-NLS-1$
            accessor.createAndSet(primaryKeyInfo);
        }
    }

    private void setHeader(MutableDocument updateReportDocument, String fieldName, String value) {
        Accessor accessor = updateReportDocument.createAccessor(fieldName);
        accessor.set(value);
    }

    @Override
    public String[] getSavedId() {
        return next.getSavedId();
    }

    @Override
    public String getSavedConceptName() {
        return next.getSavedConceptName();
    }

    @Override
    public String getBeforeSavingMessage() {
        return next.getBeforeSavingMessage();
    }

    @Override
    public String getBeforeSavingMessageType() {
        return next.getBeforeSavingMessageType();
    }
}
