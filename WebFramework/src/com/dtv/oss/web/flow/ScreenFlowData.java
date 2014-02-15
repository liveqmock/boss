package com.dtv.oss.web.flow;

import java.util.HashMap;

public class ScreenFlowData implements java.io.Serializable {

    private HashMap exceptionMappings;
    private String defaultScreen = null;

    public ScreenFlowData (HashMap exceptionMappings,
                           String defaultScreen) {
        this.exceptionMappings = exceptionMappings;
        this.defaultScreen = defaultScreen;
    }

    public String getDefaultScreen() {
        return defaultScreen;
    }



    public HashMap getExceptionMappings() {
        return exceptionMappings;
    }

    public String toString() {
        return "ScreenFlowData: {defaultScreen=" + defaultScreen + ", " +
                    " exceptionMappings=" + exceptionMappings + "}";
    }
}


