/*
 * Created on 2005-10-21
 *
 * @author whq
 */
package com.dtv.oss.service.component;

import java.util.HashMap;

import com.dtv.oss.service.util.CommonConstDefinition;
/*
 * �������Ҫ������ô����¼���������������޵�����action��ȡֵ
 * 
 */
public class Action {
    private static HashMap map4Repair = new HashMap();
    private static HashMap map4JobCard = new HashMap();

    static {
        map4Repair.put(new StatusPair("", CommonConstDefinition.CUSTOMERPROBLEM_STATUS_WAIT), CommonConstDefinition.CUSTPROBLEMPROCESS_ACTION_CREATE);
        map4Repair.put(new StatusPair(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_WAIT, CommonConstDefinition.CUSTOMERPROBLEM_STATUS_PROCESSING), CommonConstDefinition.CUSTPROBLEMPROCESS_ACTION_ASSIGN);
        map4Repair.put(new StatusPair(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_PROCESSING, CommonConstDefinition.CUSTOMERPROBLEM_STATUS_SUCCESS), CommonConstDefinition.CUSTPROBLEMPROCESS_ACTION_SUCCESS);
        map4Repair.put(new StatusPair(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_PROCESSING, CommonConstDefinition.CUSTOMERPROBLEM_STATUS_FAIL), CommonConstDefinition.CUSTPROBLEMPROCESS_ACTION_FAIL);
        map4Repair.put(new StatusPair(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_FAIL, CommonConstDefinition.CUSTOMERPROBLEM_STATUS_TERMINAL), CommonConstDefinition.CUSTPROBLEMPROCESS_ACTION_END);
        map4Repair.put(new StatusPair(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_WAIT, CommonConstDefinition.CUSTOMERPROBLEM_STATUS_WAIT), CommonConstDefinition.CUSTPROBLEMPROCESS_ACTION_TRANSFER);
        map4Repair.put(new StatusPair(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_PROCESSING, CommonConstDefinition.CUSTOMERPROBLEM_STATUS_WAIT), CommonConstDefinition.CUSTPROBLEMPROCESS_ACTION_CANCEL);
        map4Repair.put(new StatusPair(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_WAIT, CommonConstDefinition.CUSTOMERPROBLEM_STATUS_SUCCESS), CommonConstDefinition.CUSTPROBLEMPROCESS_ACTION_STOP);
        map4Repair.put(new StatusPair(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_FAIL, CommonConstDefinition.CUSTOMERPROBLEM_STATUS_WAIT), CommonConstDefinition.CUSTPROBLEMPROCESS_ACTION_RETRANSFER);
        // add by liyanchun
        map4Repair.put(new StatusPair(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_WAIT,CommonConstDefinition.CUSTOMERPROBLEM_STATUS_TERMINAL),CommonConstDefinition.CUSTPROBLEMPROCESS_ACTION_END);
        map4Repair.put(new StatusPair(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_PROCESSING,CommonConstDefinition.CUSTOMERPROBLEM_STATUS_CANCEL),CommonConstDefinition.CUSTPROBLEMPROCESS_ACTION_CANCEL);
		map4Repair.put(new StatusPair(CommonConstDefinition.CUSTOMERPROBLEM_STATUS_FAIL,CommonConstDefinition.CUSTOMERPROBLEM_STATUS_CANCEL),CommonConstDefinition.CUSTPROBLEMPROCESS_ACTION_CANCEL);
      
        map4JobCard.put(new StatusPair("", CommonConstDefinition.JOBCARD_STATUS_WAIT), CommonConstDefinition.JOBCARDPROCESS_ACTION_CREATE); 
        map4JobCard.put(new StatusPair(CommonConstDefinition.JOBCARD_STATUS_WAIT, CommonConstDefinition.JOBCARD_STATUS_CANCEL), CommonConstDefinition.JOBCARDPROCESS_ACTION_CANCEL);
        map4JobCard.put(new StatusPair(CommonConstDefinition.JOBCARD_STATUS_BOOKED, CommonConstDefinition.JOBCARD_STATUS_CANCEL), CommonConstDefinition.JOBCARDPROCESS_ACTION_CANCEL);
        map4JobCard.put(new StatusPair(CommonConstDefinition.JOBCARD_STATUS_WAIT, CommonConstDefinition.JOBCARD_STATUS_BOOKED), CommonConstDefinition.JOBCARDPROCESS_ACTION_BOOKING);
        map4JobCard.put(new StatusPair(CommonConstDefinition.JOBCARD_STATUS_WAIT, CommonConstDefinition.JOBCARD_STATUS_WAIT), CommonConstDefinition.JOBCARDPROCESS_ACTION_BOOKING);
        map4JobCard.put(new StatusPair(CommonConstDefinition.JOBCARD_STATUS_BOOKED, CommonConstDefinition.JOBCARD_STATUS_SUCCESS), CommonConstDefinition.JOBCARDPROCESS_ACTION_SUCCESS);
        map4JobCard.put(new StatusPair(CommonConstDefinition.JOBCARD_STATUS_BOOKED, CommonConstDefinition.JOBCARD_STATUS_FAIL), CommonConstDefinition.JOBCARDPROCESS_ACTION_SUCCESS);
        map4JobCard.put(new StatusPair(CommonConstDefinition.JOBCARD_STATUS_FAIL, CommonConstDefinition.JOBCARD_STATUS_TERMINAL), CommonConstDefinition.JOBCARDPROCESS_ACTION_END);
        map4JobCard.put(new StatusPair(CommonConstDefinition.JOBCARD_STATUS_FAIL, CommonConstDefinition.JOBCARD_STATUS_BOOKED), CommonConstDefinition.JOBCARDPROCESS_ACTION_REBOOKING);
        map4JobCard.put(new StatusPair(CommonConstDefinition.JOBCARD_STATUS_BOOKED, CommonConstDefinition.JOBCARD_STATUS_BOOKED), CommonConstDefinition.JOBCARDPROCESS_ACTION_REBOOKING);
        map4JobCard.put(new StatusPair(CommonConstDefinition.JOBCARD_STATUS_FAIL,CommonConstDefinition.JOBCARD_STATUS_SUCCESS),CommonConstDefinition.JOBCARDPROCESS_ACTION_REBOOKING);
        //add by chen jiang 
        map4JobCard.put(new StatusPair(CommonConstDefinition.JOBCARD_STATUS_WAIT, CommonConstDefinition.JOBCARD_STATUS_SUCCESS), CommonConstDefinition.JOBCARDPROCESS_ACTION_BOOKING);
        
    }
    
    
    //���ݹ�����״̬�仯����ö�Ӧ��JobCardProcess��¼��action��ȡֵ
    public static String getAction4JobCard(String beforeChangeStatus, String afterChangeStatus) {

        StatusPair sp = new StatusPair(beforeChangeStatus, afterChangeStatus);
        if (map4JobCard.containsKey(sp) == false)
            throw new IllegalArgumentException("����״̬��'"+beforeChangeStatus+"'��'"+afterChangeStatus+"'�仯��Ӧ��Actionδ���塣");
        return (String)map4JobCard.get(sp);
    }
    
    //���ݱ��޵���״̬�仯����ö�Ӧ��CustProblemProcess��¼��action��ȡֵ
    public static String getAction4Repair(String beforeChangeStatus, String afterChangeStatus) {
        StatusPair sp = new StatusPair(beforeChangeStatus, afterChangeStatus);
        if (map4Repair.containsKey(sp) == false)
            throw new IllegalArgumentException("���޵�״̬��'"+beforeChangeStatus+"'��'"+afterChangeStatus+"'�仯��Ӧ��Actionδ���塣");
        return (String)map4Repair.get(sp);

    }
    
    /*
     * ��ñ��޵��طò�����Ӧ��action
     */
    public static String getCallBackAction4Repair() {
        return CommonConstDefinition.CUSTPROBLEMPROCESS_ACTION_CALLBACK;
    }

    /*
     * ��ù����طò�����Ӧ��action�������ڸò���
     */
//    public static String getCallBackAction4JobCard() {
//        return "";
//        //return CommonConstDefinition.JOBCARDPROCESS_ACTION_CALLBACK;
//    }
    
    public static void main(String[] args) {
        System.out.println(Action.getAction4JobCard("W", "B"));
        System.out.println(Action.getAction4Repair("", "W"));
    }
}

/*
 * ������״̬��Ψһ��ʶ��һ����
 */
class StatusPair {
    private String beforeChangeStatus;
    private String afterChangeStatus;
    
    
    
    public StatusPair(String beforeChangeStatus, String afterChangeStatus) {
        this.beforeChangeStatus = beforeChangeStatus;
        this.afterChangeStatus = afterChangeStatus;
    }
    
    
    public boolean equals(Object arg0) {
        if (arg0 != null) {
            if (this.getClass().equals(arg0.getClass())) {
                StatusPair another = (StatusPair)arg0;
                return (this.beforeChangeStatus.equals(another.beforeChangeStatus) && 
                        this.afterChangeStatus.equals(another.afterChangeStatus));
                
            }
        }
        return false;
    }
    
    
    public int hashCode() {
        return (beforeChangeStatus+afterChangeStatus).hashCode();
    }
    public String toString() {
        return beforeChangeStatus+","+afterChangeStatus;
    }
}