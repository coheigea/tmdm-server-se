/*
 * Copyright (C) 2006-2019 Talend Inc. - www.talend.com
 *
 * This source code is available under agreement available at
 * %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
 *
 * You should have received a copy of the agreement along with this program; if not, write to Talend SA 9 rue Pages
 * 92150 Suresnes, France
 */
package com.amalto.core.webservice;

import javax.xml.bind.annotation.XmlType;

@XmlType(name="WSDeleteTransformerV2")
public class WSDeleteTransformerV2 {
    protected com.amalto.core.webservice.WSTransformerV2PK wsTransformerV2PK;

    public WSDeleteTransformerV2() {
    }

    public WSDeleteTransformerV2(com.amalto.core.webservice.WSTransformerV2PK wsTransformerV2PK) {
        this.wsTransformerV2PK = wsTransformerV2PK;
    }

    public com.amalto.core.webservice.WSTransformerV2PK getWsTransformerV2PK() {
        return wsTransformerV2PK;
    }

    public void setWsTransformerV2PK(com.amalto.core.webservice.WSTransformerV2PK wsTransformerV2PK) {
        this.wsTransformerV2PK = wsTransformerV2PK;
    }
}
