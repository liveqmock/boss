/*
 * Created on 2005-12-7
 *
 * @author whq
 * 
 * �ýӿڴ�����������/�¼����͵Ķ�Ӧ��ϵ, �����ʵ�ַ�ʽ����ʵ����
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