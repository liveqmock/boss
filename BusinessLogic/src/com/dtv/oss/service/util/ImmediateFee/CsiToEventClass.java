/*
 * Created on 2005-12-7
 *
 * @author whq
 * 
 * 该接口代表受理类型/事件类型的对应关系, 具体的实现方式见其实现类
 */
package com.dtv.oss.service.util.ImmediateFee;

import java.util.Collection;

public interface CsiToEventClass {
    /*
     * get Collection of EventClass by CsiType and installType(if exist)
     */
    public abstract Collection getEventClass(String csiType,
            String installType,String anOSType);
    public abstract Collection getEventClass4CP(String problemLevel, String problemCategory);
}