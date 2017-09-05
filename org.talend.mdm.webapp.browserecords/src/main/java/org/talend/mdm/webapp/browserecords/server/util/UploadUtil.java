// ============================================================================
//
// Copyright (C) 2006-2017 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.mdm.webapp.browserecords.server.util;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.talend.mdm.webapp.browserecords.shared.Constants;

@SuppressWarnings("nls")
public class UploadUtil {
    
    public static Set<String> chechMandatoryField(String mandatoryField, Set<String> fields) {
        String[] mandatoryFields = mandatoryField.split(Constants.FILE_EXPORT_IMPORT_SEPARATOR);
        Set<String> mandatorySet = new HashSet<String>();
        for (String field : mandatoryFields)
            mandatorySet.add(field);

        for (String fieldValue : fields) {
            String fieldName = getFieldName(fieldValue);
            if (mandatorySet.contains(fieldName))
                mandatorySet.remove(fieldName);
        }

        return mandatorySet;
    }
    
    public static String getFieldName(String fieldValue){
        return fieldValue.split(Constants.HEADER_VISIBILITY_SEPARATOR)[0];
    }
    
    public static boolean getFieldVisible(String fieldValue){
        return Boolean.valueOf(fieldValue.split(Constants.HEADER_VISIBILITY_SEPARATOR)[1]);
    }
    
    public static String[] getDefaultHeader(String headerString){
        List<String> headers = new LinkedList<String>();  
        String fields[] = headerString.split(Constants.FILE_EXPORT_IMPORT_SEPARATOR);
        for (int i=0;i<fields.length;i++){
            headers.add(getFieldName(fields[i]));  
        }
        return headers.toArray(new String[headers.size()]);
    }
    
    public static String getRootCause(Throwable throwable) {
        String message = ""; //$NON-NLS-1$
        Throwable currentCause = throwable;
        while (currentCause != null) {           
            message = currentCause.getMessage();
            currentCause = currentCause.getCause();
        }
        return message;
    }
    
    public static boolean isViewableXpathValid(Set<String> viewableXpathSet, String concept){
        for(String path : viewableXpathSet){
            String str = path.substring(0, path.indexOf("/"));
            if(!str.equalsIgnoreCase(concept))
                return false;
        }
        return true;
    }
}
