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

package com.amalto.core.storage.record;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Iterator;

import com.amalto.core.storage.SecuredStorage;
import com.amalto.core.storage.StorageResults;

/**
 *
 */
public interface DataRecordWriter {

    /**
     * Writes a {@link DataRecord} record to the <code>output</code>.
     *
     * @param record A {@link DataRecord} instance.
     * @param output A {@link OutputStream} instance.
     * @throws IOException In case of errors while writing to <code>output</code>.
     * @see DataRecordReader#read(org.talend.mdm.commmon.metadata.MetadataRepository, org.talend.mdm.commmon.metadata.ComplexTypeMetadata, Object)
     */
    void write(DataRecord record, OutputStream output) throws IOException;

    /**
     * Writes all of {@link DataRecord} record to the <code>output</code>.
     * @param recordList
     * @param output
     * @throws IOException
     */
    default void write(StorageResults recordList, OutputStream output) throws IOException {
        for (Iterator<DataRecord> iterator = recordList.iterator(); iterator.hasNext();) {
            write(iterator.next(), output);
        }
    }

    /**
     * Writes a {@link DataRecord} record to the <code>writer</code>.
     *
     * @param record A {@link DataRecord} instance.
     * @param writer A {@link Writer} instance.
     * @throws IOException In case of errors while writing to <code>output</code>.
     * @see DataRecordReader#read(org.talend.mdm.commmon.metadata.MetadataRepository, org.talend.mdm.commmon.metadata.ComplexTypeMetadata, Object)
     */
    void write(DataRecord record, Writer writer) throws IOException;

    /**
     * Sets a implementation of {@link com.amalto.core.storage.SecuredStorage.UserDelegator} to filter out elements the
     * current user is not allowed to see.
     *
     * @param delegator An implementation of {@link com.amalto.core.storage.SecuredStorage.UserDelegator}, not <code>null</code>.
     * @see SecuredStorage
     * @see SecuredStorage#UNSECURED
     */
    void setSecurityDelegator(SecuredStorage.UserDelegator delegator);
}
