package com.dtv.oss.web.controller;

import java.util.HashMap;

public class URLMapping implements java.io.Serializable {

    private String url;
    private boolean isAction = false;
    private boolean useFlowHandler = false;
    private String flowHandler = null;
    private String webActionClass = null;
    private String ejbActionClass = null;
    private HashMap resultMappings;
    private String screen;

    public URLMapping(String url, String screen) {
        this.url = url;
        this.screen = screen;
    }

    public URLMapping(String url,
                                    String screen,
                                    boolean isAction,
                                    boolean useFlowHandler,
                                    String webActionClass,
                                    String flowHandler,
                                    HashMap resultMappings) {
        this.url = url;
        this.flowHandler = flowHandler;
        this.webActionClass = webActionClass;
        this.isAction = isAction;
        this.useFlowHandler = useFlowHandler ;
        this.resultMappings = resultMappings;
        this.screen = screen;
    }

    public boolean useFlowHandler() {
        return useFlowHandler;
    }

    public boolean isAction() {
        return isAction;
    }

    public String getWebAction() {
        return webActionClass;
    }

    public String getFlowHandler() {
        return flowHandler;
    }

    public String getScreen() {
        return screen;
    }

    public String getResultScreen(String key) {
        if (resultMappings != null) {
            return (String)resultMappings.get(key);
        } else {
            return null;
        }
    }

    public HashMap getResultMappings() {
        return resultMappings;
    }

    public String toString() {
        return "[URLMapping: url=" + url +
                ", isAction=" + isAction +
                ", useFlowHandler=" + useFlowHandler +
                ", webActionClass=" + webActionClass +
                ", ejbActionClass=" + ejbActionClass +
                ", flowHandler=" + flowHandler +
                ", resultMappings=" + resultMappings +
                "]";
    }
}


