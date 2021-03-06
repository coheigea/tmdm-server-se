/*
 * Copyright (C) 2006-2019 Talend Inc. - www.talend.com
 *
 * This source code is available under agreement available at
 * %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
 *
 * You should have received a copy of the agreement along with this program; if not, write to Talend SA 9 rue Pages
 * 92150 Suresnes, France
 */
package com.amalto.core.storage.hibernate;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.lucene.document.Document;

public class ShortIdFieldBridge extends ToLowerCaseFieldBridge {
    private static final Logger LOGGER = LogManager.getLogger(ShortIdFieldBridge.class);

    @Override
    public Object get(String name, Document document) {
        try {
            return new Short(document.get(name));
        } catch (NumberFormatException e) {
            LOGGER.error("Error occured while trying to cast field to Short"); //$NON-NLS-1$
            return document.get(name);
        }
    }
}
