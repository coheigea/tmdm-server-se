/*
 * Copyright (C) 2006-2018 Talend Inc. - www.talend.com
 *
 * This source code is available under agreement available at
 * %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
 *
 * You should have received a copy of the agreement
 * along with this program; if not, write to Talend SA
 * 9 rue Pages 92150 Suresnes, France
 */

package com.amalto.core.save.context;

import com.amalto.core.history.MutableDocument;
import com.amalto.core.objects.ItemPOJOPK;
import com.amalto.core.objects.datacluster.DataClusterPOJOPK;
import com.amalto.core.objects.datamodel.DataModelPOJOPK;
import com.amalto.core.save.generator.AutoIncrementGenerator;
import com.amalto.core.server.api.DataModel;
import com.amalto.core.server.api.RoutingEngine;
import com.amalto.core.server.api.XmlServer;
import org.talend.mdm.commmon.metadata.MetadataUtils;
import com.amalto.core.save.DOMDocument;
import com.amalto.core.schema.validation.SkipAttributeDocumentBuilder;
import org.talend.mdm.commmon.metadata.ComplexTypeMetadata;
import org.talend.mdm.commmon.metadata.MetadataRepository;
import com.amalto.core.save.DocumentSaverContext;
import com.amalto.core.schema.validation.XmlSchemaValidator;
import com.amalto.core.server.MetadataRepositoryAdmin;
import com.amalto.core.server.ServerContext;
import com.amalto.core.servlet.LoadServlet;
import com.amalto.core.util.*;
import org.talend.mdm.commmon.metadata.TypeMetadata;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DefaultSaverSource implements SaverSource {

    private final XmlServer database;

    private final DataModel dataModel;

    private final Map<String, String> schemasAsString = new HashMap<>();

    private final String userName;

    public DefaultSaverSource(String userName) {
        database = Util.getXmlServerCtrlLocal();
        dataModel = Util.getDataModelCtrlLocal();
        this.userName = userName;
    }

    public static SaverSource getDefault() {
        return new StorageSaverSource();
    }

    public static SaverSource getDefault(String userName) {
        return new StorageSaverSource(userName);
    }

    public MutableDocument get(String dataClusterName, String dataModelName, String typeName, String[] key) {
        try {
            StringBuilder builder = new StringBuilder();
            builder.append(dataClusterName).append('.').append(typeName).append('.');
            for (int i = 0; i < key.length; i++) {
                builder.append(key[i]);
                if (i < key.length - 1) {
                    builder.append('.');
                }
            }
            String uniqueId = builder.toString();
            String documentAsString = database.getDocumentAsString(dataClusterName, uniqueId, "UTF-8"); //$NON-NLS-1$
            if (documentAsString == null) {
                return null;
            }
            DocumentBuilder documentBuilder;
            documentBuilder = new SkipAttributeDocumentBuilder(SaverContextFactory.DOCUMENT_BUILDER, false);
            ComplexTypeMetadata type = ServerContext.INSTANCE.get().getMetadataRepositoryAdmin().get(dataModelName).getComplexType(typeName);
            Document databaseDomDocument = documentBuilder.parse(new ByteArrayInputStream(documentAsString.getBytes("UTF-8")));
            Element userXmlElement = getUserXmlElement(databaseDomDocument);
            return new DOMDocument(userXmlElement, type, dataClusterName, dataModelName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Element getUserXmlElement(Document databaseDomDocument) {
        NodeList userXmlPayloadElement = databaseDomDocument.getElementsByTagName("p"); //$NON-NLS-1$
        if (userXmlPayloadElement.getLength() > 1) {
            throw new IllegalStateException("Document has multiple payload elements.");
        }
        Node current = userXmlPayloadElement.item(0).getFirstChild();
        while (current != null) {
            if (current instanceof Element) {
                Element newRoot = (Element) current;
                NamedNodeMap attrs = databaseDomDocument.getDocumentElement().getAttributes();
                for (int i = 0; i < attrs.getLength(); i++) {
                    Node attrNode = attrs.item(i);
                    newRoot.setAttributeNS(attrNode.getNamespaceURI(), attrNode.getNodeName(), attrNode.getNodeValue());
                }
                return (Element) current;
            }
            current = current.getNextSibling();
        }
        throw new IllegalStateException("Element 'p' is expected to have an XML element as child.");
    }

    public boolean exist(String dataCluster, String dataModelName, String typeName, String[] key) {
        return get(dataCluster, dataModelName, typeName, key) != null;
    }

    public MetadataRepository getMetadataRepository(String dataModelName) {
        try {
            MetadataRepositoryAdmin admin = ServerContext.INSTANCE.get().getMetadataRepositoryAdmin();
            return admin.get(dataModelName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public InputStream getSchema(String dataModelName) {
        try {
            synchronized (schemasAsString) {
                if (schemasAsString.get(dataModelName) == null) {
                    String schemaAsString = dataModel.getDataModel(new DataModelPOJOPK(dataModelName)).getSchema();
                    schemasAsString.put(dataModelName, schemaAsString);
                }
            }
            return new ByteArrayInputStream(schemasAsString.get(dataModelName).getBytes("UTF-8")); //$NON-NLS-1$
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public OutputReport invokeBeforeSaving(DocumentSaverContext context, MutableDocument updateReportDocument) {
        try {
            return Util.beforeSaving(context.getUserDocument().getType().getName(),
                    context.getDatabaseDocument().exportToString(),
                    updateReportDocument.exportToString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Set<String> getCurrentUserRoles() {
        try {
            // get user roles from current user.
            return LocalUser.getLocalUser().getRoles();
        } catch (XtentisException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUserName() {
        try {
            return LocalUser.getLocalUser().getUsername();
        } catch (XtentisException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean existCluster(String dataClusterName) {
        try {
            return database.existCluster(dataClusterName);
        } catch (XtentisException e) {
            throw new RuntimeException(e);
        }
    }

    public void resetLocalUsers() {
        try {
            LocalUser.resetLocalUsers();
        } catch (XtentisException e) {
            throw new RuntimeException(e);
        }
    }

    public void initAutoIncrement() {
        AutoIncrementGenerator.get().init();
    }

    public void routeItem(String dataCluster, String typeName, String[] id) {
        try {
            RoutingEngine ctrl = Util.getRoutingEngineV2CtrlLocal();
            DataClusterPOJOPK dataClusterPOJOPK = new DataClusterPOJOPK(dataCluster);
            ctrl.route(new ItemPOJOPK(dataClusterPOJOPK, typeName, id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void invalidateTypeCache(String dataModelName) {
        XmlSchemaValidator.invalidateCache(dataModelName);
        synchronized (schemasAsString) {
            schemasAsString.remove(dataModelName);
        }
        synchronized (LoadServlet.typeNameToKeyDef) {
            LoadServlet.typeNameToKeyDef.clear();
        }
    }

    public void saveAutoIncrement() {
        AutoIncrementGenerator.get().saveState(Util.getXmlServerCtrlLocal());
    }

    public String nextAutoIncrementId(String dataCluster, String dataModelName, String conceptName) {
        String autoIncrementId = null;
        String concept;
        String field;
        if (conceptName.contains(".")) { //$NON-NLS-1$
            String[] conceptArray = conceptName.split("\\."); //$NON-NLS-1$
            concept = conceptArray[0];
            field = conceptArray[1];
        } else {
            concept = conceptName;
            field = null;
        }
        MetadataRepository metadataRepository = getMetadataRepository(dataModelName);
        if (metadataRepository != null) {
            ComplexTypeMetadata complexType = metadataRepository.getComplexType(concept);
            if (complexType != null) {
                TypeMetadata superType = MetadataUtils.getSuperConcreteType(complexType);
                if (superType != null) {
                    concept = superType.getName();
                }
                String autoIncrementFieldName = field != null ? concept + "." + field : concept; //$NON-NLS-1$
                autoIncrementId = AutoIncrementGenerator.get().generateId(dataCluster, conceptName, autoIncrementFieldName);
            } 
        }
        return autoIncrementId;
    }

    public String getLegitimateUser() {
        // web service caller
        if (userName != null) {
            return userName;
        }
        return getUserName();
    }

}
