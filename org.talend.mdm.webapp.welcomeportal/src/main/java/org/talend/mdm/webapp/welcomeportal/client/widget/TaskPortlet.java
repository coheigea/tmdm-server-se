/*
 * Copyright (C) 2006-2019 Talend Inc. - www.talend.com
 *
 * This source code is available under agreement available at
 * %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
 *
 * You should have received a copy of the agreement along with this program; if not, write to Talend SA 9 rue Pages
 * 92150 Suresnes, France
 */
package org.talend.mdm.webapp.welcomeportal.client.widget;

import org.talend.mdm.webapp.base.client.SessionAwareAsyncCallback;
import org.talend.mdm.webapp.base.client.i18n.BaseMessagesFactory;
import org.talend.mdm.webapp.base.client.util.UrlUtil;
import org.talend.mdm.webapp.base.client.widget.PortletConstants;
import org.talend.mdm.webapp.base.shared.AppHeader;
import org.talend.mdm.webapp.base.shared.Constants;
import org.talend.mdm.webapp.welcomeportal.client.MainFramePanel;
import org.talend.mdm.webapp.welcomeportal.client.WelcomePortal;
import org.talend.mdm.webapp.welcomeportal.client.i18n.MessagesFactory;
import org.talend.mdm.webapp.welcomeportal.client.resources.icon.Icons;

import com.allen_sauer.gwt.log.client.Log;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.util.Format;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.HTML;

public class TaskPortlet extends BasePortlet {

    private AppHeader header = (AppHeader) Registry.get(WelcomePortal.APP_HEADER);

    private final String TASK_AMOUNT = "amount";

    private String tdsServiceBaseUrl = GWT.getHostPageBaseURL() + "services/rest/tds/";

    private static String WORKFLOWTASKS_PREFIX = "<span id=\"workflowtasks\" style=\"padding-right:8px;cursor: pointer;\" class=\"labelStyle\" title=\"" + MessagesFactory.getMessages().tasks_title() + "\">"; //$NON-NLS-1$ //$NON-NLS-2$

    private static String DSCTASKS_PREFIX = "<span id=\"dsctasks\" style=\"padding-right:8px;cursor: pointer;\" class=\"labelStyle\" title=\"" + MessagesFactory.getMessages().tasks_title() + "\">"; //$NON-NLS-1$ //$NON-NLS-2$

    private boolean isHiddenWorkFlowTask = true;

    private boolean isHiddenTask = true;

    private String ACCESS_TDS_FAIL = "access_tds_fail";

    private enum TASK_TYPE {
        WORKFLOW_TYPE,
        TDS_TYPE
    };

    private Integer taskNewCount;

    private Integer taskPendingCount;

    private Integer workflowTaskNewCount;

    private ClickHandler workflowClikcHanlder;

    private ClickHandler tdsClikcHanlder;

    public TaskPortlet(final MainFramePanel portal) {
        super(PortletConstants.TASKS_NAME, portal);
        setIcon(AbstractImagePrototype.create(Icons.INSTANCE.task()));
        setHeading(MessagesFactory.getMessages().tasks_title());
        isHiddenWorkFlowTask = portal.isHiddenWorkFlowTask();
        isHiddenTask = portal.isHiddenTask();
        initConfigSettings();
        label.setText(MessagesFactory.getMessages().loading_task_msg());
        updateTaskes();
        autoRefresh(configModel.isAutoRefresh());

        workflowClikcHanlder = new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                portal.itemClick(WelcomePortal.WORKFLOW_TASKCONTEXT, WelcomePortal.WORKFLOW_TASKAPP);
            }
        };

        tdsClikcHanlder = new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                UrlUtil.openSingleWindow(header.getTdsBaseUrl() + "/#/mytasks", Constants.TDS_NAME);
            }
        };
    }

    @Override
    public void refresh() {
        updateTaskes();
    }

    private void updateTaskes() {
        if (!isHiddenWorkFlowTask && isHiddenTask) {
            service.getWorkflowTaskMsg(new SessionAwareAsyncCallback<Integer>() {

                @Override
                public void onSuccess(Integer workflowTaskCount) {
                    if (workflowTaskCount != null) {
                        if (workflowTaskNewCount == null || workflowTaskNewCount != workflowTaskCount) {
                            workflowTaskNewCount = workflowTaskCount;
                            updateTaskPanel(workflowTaskNewCount, null, 0, 0);
                        }
                    }
                }
            });
        }

        if (!isHiddenTask && isHiddenWorkFlowTask) {
            String url = tdsServiceBaseUrl + TASK_AMOUNT;
            RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
            builder.setHeader("Accept", "application/json");
            try {
                builder.sendRequest("", new RequestCallback() {

                    @Override
                    public void onResponseReceived(Request request, Response response) {
                        HTML errorHTML = null;
                        if (Response.SC_OK == response.getStatusCode()) {
                            try {
                                Integer taskCount = Integer.valueOf(response.getText());
                                if (taskNewCount == null || taskNewCount != taskCount) {
                                    taskNewCount = taskCount;
                                    updateTaskPanel(0, TASK_TYPE.TDS_TYPE, taskNewCount, 0);
                                }
                            } catch (NumberFormatException exception) {
                                errorHTML = buildErrorHTML(BaseMessagesFactory.getMessages().unknown_error());
                            }
                        } else {
                            errorHTML = buildErrorHTML(getResponseErrorMessage(response));
                        }
                        if (errorHTML != null) {
                            label.setText(MessagesFactory.getMessages().no_tasks());
                            fieldSet.removeAll();
                            fieldSet.add(errorHTML);
                            fieldSet.layout(true);
                        }
                    }

                    @Override
                    public void onError(Request request, Throwable exception) {
                        handleServiceException(exception);

                    }

                });
            } catch (RequestException exception) {
                handleServiceException(exception);
            }
        }

        if (!isHiddenWorkFlowTask && !isHiddenTask) {
            service.getWorkflowTaskMsg(new SessionAwareAsyncCallback<Integer>() {

                @Override
                public void onSuccess(final Integer workflowTaskCount) {
                    final boolean workflowTaskChanged = workflowTaskCount != null
                            && (workflowTaskNewCount == null || workflowTaskNewCount != workflowTaskCount);
                    if (workflowTaskChanged) {
                        workflowTaskNewCount = workflowTaskCount;
                    }
                    String url = tdsServiceBaseUrl + TASK_AMOUNT;
                    RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
                    builder.setHeader("Accept", "application/json");
                    try {
                        builder.sendRequest("", new RequestCallback() {

                            @Override
                            public void onResponseReceived(Request request, Response response) {
                                HTML errorHTML = null;
                                if (Response.SC_OK == response.getStatusCode()) {
                                    try {
                                        Integer taskCount = Integer.valueOf(response.getText());
                                        boolean taskChanged = taskNewCount == null || taskNewCount != taskCount;
                                        if (workflowTaskChanged || taskChanged) {
                                            taskNewCount = taskCount;
                                            updateTaskPanel(workflowTaskNewCount, TASK_TYPE.TDS_TYPE, taskNewCount, 0);
                                        }
                                    } catch (NumberFormatException exception) {
                                        errorHTML = buildErrorHTML(BaseMessagesFactory.getMessages().unknown_error());
                                    }
                                } else {
                                    errorHTML = buildErrorHTML(getResponseErrorMessage(response));
                                }
                                if (errorHTML != null) {
                                    if (workflowTaskCount > 0) {
                                        updateTaskPanel(workflowTaskNewCount, TASK_TYPE.TDS_TYPE, 0, 0);
                                    } else {
                                        label.setText(MessagesFactory.getMessages().no_tasks());
                                        fieldSet.removeAll();
                                    }
                                    fieldSet.add(errorHTML);
                                    fieldSet.layout(true);
                                }
                            }

                            @Override
                            public void onError(Request request, Throwable exception) {
                                handleServiceException(exception);
                            }

                        });
                    } catch (RequestException exception) {
                        handleServiceException(exception);
                    }
                }
            });
        }
    }

    private HTML buildTaskHTML(TASK_TYPE type, Integer count1, Integer count2) {
        HTML taskHtml = new HTML();
        StringBuilder taskStringBuilder;
        String countString;
        if (type == TASK_TYPE.WORKFLOW_TYPE) {
            taskStringBuilder = new StringBuilder(WORKFLOWTASKS_PREFIX);
            countString = buildMessage(String.valueOf(count1), MessagesFactory.getMessages().waiting_workflowtask_suffix());
            taskHtml.addClickHandler(workflowClikcHanlder);
        } else if (type == TASK_TYPE.TDS_TYPE) {
            taskStringBuilder = new StringBuilder(DSCTASKS_PREFIX);
            countString = buildMessage(MessagesFactory.getMessages().waiting_task(count1), MessagesFactory.getMessages()
                    .waiting_dsctask_suffix());
            taskHtml.addClickHandler(tdsClikcHanlder);
        } else {
            return null;
        }
        taskStringBuilder.append(countString);
        label.setText(MessagesFactory.getMessages().tasks_desc());
        fieldSet.setVisible(true);
        taskStringBuilder.append("</span>"); //$NON-NLS-1$
        taskHtml.setHTML(taskStringBuilder.toString());
        return taskHtml;
    }

    private String buildMessage(String countString, String task_suffix) {
        StringBuilder message = new StringBuilder("<IMG SRC=\"secure/img/genericUI/task-list-icon.png\"/>&nbsp;"); //$NON-NLS-1$
        message.append(MessagesFactory.getMessages().waiting_task_prefix());
        message.append("&nbsp;<b style=\"color: red;\">"); //$NON-NLS-1$
        message.append(countString);
        message.append("</b>&nbsp;"); //$NON-NLS-1$
        message.append(task_suffix);
        return message.toString();
    }

    private void updateTaskPanel(int workflowTaskCount, TASK_TYPE taskType, int taskCount1, int taskCount2) {
        if ((workflowTaskCount + taskCount1 + taskCount2) == 0) {
            label.setText(MessagesFactory.getMessages().no_tasks());
            fieldSet.setVisible(false);
        } else {
            fieldSet.removeAll();
            if (workflowTaskCount > 0) {
                workflowTaskNewCount = workflowTaskCount;
                HTML taskHtml = buildTaskHTML(TASK_TYPE.WORKFLOW_TYPE, workflowTaskNewCount, 0);
                if (taskHtml != null) {
                    fieldSet.add(taskHtml);
                }
            }
            if (taskCount1 + taskCount2 > 0) {
                taskNewCount = taskCount1;
                taskPendingCount = taskCount2;
                HTML taskHtml = buildTaskHTML(taskType, taskNewCount, taskPendingCount);
                if (taskHtml != null) {
                    fieldSet.add(taskHtml);
                }
            }
        }
        fieldSet.layout(true);
    }

    private void handleServiceException(Throwable caught) {
        String errorMsg = caught.getLocalizedMessage();
        if (Log.isDebugEnabled()) {
            errorMsg = caught.toString();
        } else {
            errorMsg = BaseMessagesFactory.getMessages().unknown_error();
        }
        errorMsg = Format.htmlEncode(errorMsg);
        MessageBox.alert(BaseMessagesFactory.getMessages().error_title(), errorMsg, null);
    }

    private HTML buildErrorHTML(String errorMessage) {
        HTML errorHtml = new HTML();
        StringBuilder errorStringBuilder = new StringBuilder("</span>");
        errorStringBuilder.append("<IMG SRC=\"secure/img/genericUI/alert-icon.png\"/>&nbsp;"); //$NON-NLS-1$
        errorStringBuilder.append("&nbsp;"); //$NON-NLS-1$
        errorStringBuilder.append(errorMessage);
        errorStringBuilder.append("&nbsp;"); //$NON-NLS-1$
        errorHtml.setHTML(errorStringBuilder.toString());
        return errorHtml;
    }

    private String getResponseErrorMessage(Response response) {
        switch (response.getStatusCode()) {
        case Response.SC_SERVICE_UNAVAILABLE:
            return MessagesFactory.getMessages().connect_tds_fail();
        case Response.SC_UNAUTHORIZED:
            return MessagesFactory.getMessages().login_tds_fail();
        case Response.SC_FORBIDDEN:
            if (ACCESS_TDS_FAIL.equals(response.getText())) {
                return MessagesFactory.getMessages().access_tds_fail();
            } else {
                return MessagesFactory.getMessages().retrieve_campaign_fail();
            }
        default:
            return BaseMessagesFactory.getMessages().unknown_error();
        }
    }
}
