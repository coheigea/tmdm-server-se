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

@XmlType(name="WSGetRoutingOrderV2")
public class WSGetRoutingOrderV2 {
    protected com.amalto.core.webservice.WSRoutingOrderV2PK wsRoutingOrderPK;

    public WSGetRoutingOrderV2() {
    }

    public WSGetRoutingOrderV2(com.amalto.core.webservice.WSRoutingOrderV2PK wsRoutingOrderPK) {
        this.wsRoutingOrderPK = wsRoutingOrderPK;
    }

    public com.amalto.core.webservice.WSRoutingOrderV2PK getWsRoutingOrderPK() {
        return wsRoutingOrderPK;
    }

    public void setWsRoutingOrderPK(com.amalto.core.webservice.WSRoutingOrderV2PK wsRoutingOrderPK) {
        this.wsRoutingOrderPK = wsRoutingOrderPK;
    }
}
