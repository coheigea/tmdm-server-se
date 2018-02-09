// ============================================================================
//
// Copyright (C) 2006-2018 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package com.amalto.webapp.core.util;

import java.io.StringReader;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.StringEscapeUtils;
import org.dom4j.Document;
import org.dom4j.io.DocumentResult;
import org.dom4j.io.DocumentSource;


public class XmlUtil {
    
    public static Document parseDocument(org.w3c.dom.Document doc) {
        if (doc == null) {
            return (null);
        }
        org.dom4j.io.DOMReader xmlReader = new org.dom4j.io.DOMReader();
        return (xmlReader.read(doc));
    }
    
    public static Document styleDocument(Document document, String stylesheet) throws Exception {
        TransformerFactory factory = TransformerFactory.newInstance(
                "net.sf.saxon.TransformerFactoryImpl", Thread.currentThread().getContextClassLoader()); //$NON-NLS-1$
        Transformer transformer = factory.newTransformer(new StreamSource(new StringReader(stylesheet)));
        // now lets style the given document
        DocumentSource source = new DocumentSource(document);
        DocumentResult result = new DocumentResult();
        transformer.transform(source, result);
        // return the transformed document
        return result.getDocument();
    }

    public static String escapeXml(String value) {
        if (value == null)
            return null;
        boolean isEscaped=false;
        if (value.contains("&quot;") || //$NON-NLS-1$
                value.contains("&amp;") || //$NON-NLS-1$
                value.contains("&lt;") || //$NON-NLS-1$
                value.contains("&gt;")) { //$NON-NLS-1$
            isEscaped = true;
        }
        if(!isEscaped) {
            value=StringEscapeUtils.escapeXml(value);
        }
        return value;
    }

}
