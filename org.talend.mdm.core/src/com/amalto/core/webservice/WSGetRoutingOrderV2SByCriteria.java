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

@XmlType(name="WSGetRoutingOrderV2SByCriteria")
public class WSGetRoutingOrderV2SByCriteria {
    protected com.amalto.core.webservice.WSRoutingOrderV2SearchCriteria wsSearchCriteria;

    public WSGetRoutingOrderV2SByCriteria() {
    }

    public WSGetRoutingOrderV2SByCriteria(com.amalto.core.webservice.WSRoutingOrderV2SearchCriteria wsSearchCriteria) {
        this.wsSearchCriteria = wsSearchCriteria;
    }

    public com.amalto.core.webservice.WSRoutingOrderV2SearchCriteria getWsSearchCriteria() {
        return wsSearchCriteria;
    }

    public void setWsSearchCriteria(com.amalto.core.webservice.WSRoutingOrderV2SearchCriteria wsSearchCriteria) {
        this.wsSearchCriteria = wsSearchCriteria;
    }
}
