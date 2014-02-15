package com.dtv.oss.web.util;

/**
 * This interface contains all the keys that are used to
 * store data in the different scopes of web-tier. These
 * values are the same as those used in the JSP
 * pages (useBean tags).
 */
public class WebKeys {

    protected WebKeys() {} // prevent instanciation

    public static final String ISWRAPPARANAME="QueryWebActionIsWrap"; 
    public static final String ISDOWNPARANAME="QueryWebActionIsDown"; 
    public static final String DOWNLOADWEBACTION_FILECONTENT="DownloadWebActionfileContent"; 
    public static final String DOWNLOADWEBACTION_FILENAME="DownloadWebActionFileName"; 
    public static final String DOWNLOADWEBACTION_FILE="DownloadWebActionFile"; 
    public static final String DOWNLOADWEBACTION_FILETYPE="DownloadWebActionFileType"; 
    
    public static final String COMPONENT_MANAGER = "com.dtv.oss.web.controller.COMPONENT_MANAGER";
    public static final String SCREEN_FLOW_MANAGER = "com.dtv.oss.web.controller.SCREEN_FLOW_MANAGER";
    public static final String REQUEST_PROCESSOR = "com.dtv.oss.web.controller.REQUEST_PROCESSOR";
    public static final String CURRENT_SCREEN = "com.dtv.oss.web.controller.CURRENT_SCREEN";
    public static final String PREVIOUS_SCREEN = "com.dtv.oss.web.controller.PREVIOUS_SCREEN";
    public static final String CURRENT_URL = "com.dtv.oss.web.controller.CURRENT_URL";
    public static final String PREVIOUS_URL = "com.dtv.oss.web.controller.PREVIOUS_URL";
    public static final String PREVIOUS_REQUEST_PARAMETERS = "com.dtv.oss.web.controller.PREVIOUS_REQUEST_PARAMETERS";
    public static final String PREVIOUS_REQUEST_ATTRIBUTES = "com.dtv.oss.web.controller.PREVIOUS_REQUEST_ATTRIBUTES";
    public static final String URL_MAPPINGS = "com.dtv.oss.web.URL_MAPPINGS";
    public static final String EVENT_MAPPINGS = "com.dtv.oss.web.controller.EVENT_MAPPINGS";
    public static final String MISSING_FORM_DATA = "com.dtv.oss.web.controller.MISSING_FORM_DATA";
    public static final String SERVER_TYPE = "com.dtv.oss.web.controller.SERVER_TYPE";
    public static final String LOCALE = "com.dtv.oss.web.controller.LOCALE";
    public static final String WEB_CONTROLLER = "com.dtv.oss.web.controller.WEB_CLIENT_CONTROLLER";
    public static final String EJB_CONTROLLER = "com.dtv.oss.ejb.service.controller.EJB_CLIENT_CONTROLLER";

    public static final String FILTER_PRIVILEGE = "com.dtv.oss.web.filter.FILTER_PRIVILEGEE";
    public static final String FILTER_BOOKMARK = "com.dtv.oss.web.filter.FILTER_BOOKMARK";
    public static final String TABLE_DEFINITION = "com.dtv.oss.ejb.service.controller.TABLEDEF";

    public static final String CURRENT_RESULTSET_PROPERTY = "com.dtv.oss.web.action.CURRENT_RESULTSET_PROPERTY";

    public static final String CURRENT_OPER_PROBLEM_FLAG = "com.dtv.oss.web.action.CurrentOperProblemFlag";
    public static final String CURRENT_OPER_JOB_CARD_FLAG = "com.dtv.oss.web.action.CurrentOperJobCardFlag";
    /**
     * Sometimes you want to back to the previous screen, but in fact PREVIOUS_SCREEN store the current screen which is now displayed.
     * So I add BACK_SCREEN store the genuine previous screen on Jan 12, 2004 !!!
     */
    //public static final String BACK_SCREEN = "com.dtv.oss.web.controller.BACK_SCREEN";

    /**
     * The session attributes key under which our tokens of transactions are
     * stored in a map, if it is used.
     */
    public static final String TRANSACTION_TOKEN_KEY = "com.dtv.oss.web.action.TOKEN";
    /**
     * The request attributes key under which our transaction token is
     * stored, if it is used.
     */
    public static final String PAGE_TOKEN_KEY = "webpage.TOKEN";

    //operator session name
    public static final String PRIVILEGE_SESSION_NAME = "SystemPrivilegeList";
    public static final String OPERATOR_SESSION_NAME = "OperatorInfo";

    //the session name of the selected bar of the current customer tree
    public static final String TREE_SELECTED_BAR_SESSION_NAME = "CurrentSidebar";
    //the session name of the open folder of the current customer tree
    public static final String TREE_OPEN_FOLDER_SESSION_NAME = "CurrentOpenFolder";



    //attribute of request: store exception
    public static final String EXCEPTION_REQUEST_ATTRIBUTE = "javax.servlet.jsp.jspException";
   
    public static final String REQUEST_ATTRIBUTE ="RequestAttribute";
   
    //attribute of request: page error code and message
    //public static final String PAGE_ERROR_CODE_REQUEST_ATTRIBUTE = "PageErrorCode";
    //public static final String PAGE_ERROR_MESSAGE_REQUEST_ATTRIBUTE = "PageErrorMessage";

    //environment flag: true=debug , and false = release
    public static boolean DEBUG_ENVIRONMENT_FLAG = true;

    //privilege debug flag: true=debug , and false = release
    public static boolean DEBUG_PRIVILEGE_FLAG = true;

    //whether printing parameters in webaction is needed
    public static boolean WEBACTION_GET_PARAMETER_FLAG = true;

    //whether printing information of debug of tag: true=print
    public static boolean DEBUG_TAG_DEBUG_FLAG = false;

    //whether printing error information in webaction is needed
    public static final boolean EXPORT_ERROR_FLAG = true;
    
    public static String SIDERBARMENUID ="SiderBarMenuId";
    
    public static String SIDERBARTDID ="SiderBarTdId";
    //用来存放系统登陆时的headtitle。
    public static String CMS_MSOSYSTEMNAME="CMS_MSOSYSTEMNAME";
    
  
}

