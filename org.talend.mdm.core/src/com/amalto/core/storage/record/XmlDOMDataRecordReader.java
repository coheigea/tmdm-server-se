/*
 * Copyright (C) 2006-2012 Talend Inc. - www.talend.com
 *
 * This source code is available under agreement available at
 * %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
 *
 * You should have received a copy of the agreement
 * along with this program; if not, write to Talend SA
 * 9 rue Pages 92150 Suresnes, France
 */

package com.amalto.core.storage.record;

import com.amalto.core.metadata.*;
import com.amalto.core.schema.validation.SkipAttributeDocumentBuilder;
import com.amalto.core.storage.record.metadata.DataRecordMetadata;
import com.amalto.core.storage.record.metadata.DataRecordMetadataImpl;
import com.amalto.core.storage.record.metadata.UnsupportedDataRecordMetadata;
import com.amalto.core.util.Util;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import javax.xml.XMLConstants;
import javax.xml.transform.TransformerException;
import java.util.List;

public class XmlDOMDataRecordReader implements DataRecordReader<Element> {

    public static final Logger LOGGER = Logger.getLogger(XmlDOMDataRecordReader.class);

    public XmlDOMDataRecordReader() {
    }

    public DataRecord read(String revisionId, MetadataRepository repository, ComplexTypeMetadata type, Element element) {
        long lastModificationTime = 0;
        String taskId = null;
        // Initialization from DOM values (timestamp, taskId...)
        NodeList timeStamp = element.getElementsByTagName("t"); //$NON-NLS-1$
        if (timeStamp.getLength() > 0) {
            lastModificationTime = Long.parseLong(timeStamp.item(0).getFirstChild().getNodeValue());
        }
        NodeList taskIdElement = element.getElementsByTagName("taskId"); //$NON-NLS-1$
        if (taskIdElement.getLength() > 0) {
            Node firstChild = taskIdElement.item(0).getFirstChild();
            taskId = firstChild == null ? null : firstChild.getNodeValue();
            if (taskId == null || taskId.isEmpty()) {
                taskId = null;
            }
        }
        DataRecordMetadata metadata = new DataRecordMetadataImpl(lastModificationTime, taskId);
        DataRecord dataRecord = new DataRecord(type, metadata);
        dataRecord.setRevisionId(revisionId);
        // Parse all record values from DOM
        NodeList userPayloadElement = element.getElementsByTagName("p"); //$NON-NLS-1$
        Element singleUserPayloadElement = (Element) userPayloadElement.item(0);
        if (singleUserPayloadElement == null) {
            _read(repository, dataRecord, type, element);
        } else {
            _read(repository, dataRecord, type, (Element) singleUserPayloadElement.getElementsByTagName(type.getName()).item(0));
        }
        // Process fields that are links to other field values.
        ComplexTypeMetadata dataRecordType = dataRecord.getType();
        List<FieldMetadata> fields = dataRecordType.getFields();
        for (FieldMetadata field : fields) {
            if (field.getData(ClassRepository.LINK) != null) {
                dataRecord.set(field, dataRecord.get(field.<String>getData(ClassRepository.LINK)));
            }
        }
        return dataRecord;
    }

    private void _read(MetadataRepository repository, DataRecord dataRecord, ComplexTypeMetadata type, Element element) {
        String tagName = element.getTagName();
        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node currentChild = children.item(i);
            if (currentChild instanceof Element) {
                Element child = (Element) currentChild;
                if (!type.hasField(child.getTagName())) {
                    continue;
                }
                FieldMetadata field = type.getField(child.getTagName());
                if (field.getType() instanceof ContainedComplexTypeMetadata) {
                    ComplexTypeMetadata containedType = (ComplexTypeMetadata) field.getType();
                    String xsiType = child.getAttributeNS(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI, "type"); //$NON-NLS-1$
                    if (!xsiType.isEmpty()) {
                        ComplexTypeMetadata actualType = (ComplexTypeMetadata) repository.getNonInstantiableType(repository.getUserNamespace(), xsiType);
                        if (actualType != null) {
                            containedType = actualType;
                        } else {
                            if (LOGGER.isDebugEnabled()) {
                                LOGGER.debug("Ignoring xsi:type '" + xsiType + "' because it is not a data model type.");
                            }
                        }
                    }
                    DataRecord containedRecord = new DataRecord(containedType, UnsupportedDataRecordMetadata.INSTANCE);
                    dataRecord.set(field, containedRecord);
                    _read(repository, containedRecord, containedType, child);
                } else if (ClassRepository.EMBEDDED_XML.equals(field.getType().getName())) {
                    try {
                        dataRecord.set(field, Util.nodeToString(element));
                    } catch (TransformerException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    _read(repository, dataRecord, type, child);
                }
            } else if (currentChild instanceof Text) {
                String textContent = element.getFirstChild().getNodeValue().trim();
                if (!textContent.isEmpty()) {
                    FieldMetadata field = type.getField(tagName);
                    if (field instanceof ReferenceFieldMetadata) {
                        ComplexTypeMetadata actualType = ((ReferenceFieldMetadata) field).getReferencedType();
                        String mdmType = element.getAttributeNS(SkipAttributeDocumentBuilder.TALEND_NAMESPACE, "type"); //$NON-NLS-1$
                        if (!mdmType.isEmpty()) {
                            actualType = repository.getComplexType(mdmType);
                        }
                        dataRecord.set(field, MetadataUtils.convert(textContent, field, actualType));
                    } else {
                        dataRecord.set(field, MetadataUtils.convert(textContent, field, type));
                    }
                }
            }
        }
    }

}
