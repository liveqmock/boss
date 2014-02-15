/*
 * Created on 2005-10-21
 *
 * @author whq
 */
package com.dtv.oss.service.component;

import java.util.HashMap;

import com.dtv.oss.service.util.CommonConstDefinition;
/*
 * 这个类主要用来获得处理记录表（包括工单、报修单）中action的取值
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
    
    
    //根据工单的状态变化来获得对应的JobCardProcess记录的action的取值
    public static String getAction4JobCard(String beforeChangeStatus, String afterChangeStatus) {

        StatusPair sp = new StatusPair(beforeChangeStatus, afterChangeStatus);
        if (map4JobCard.containsKey(sp) == false)
            throw new IllegalArgumentException("工单状态从'"+beforeChangeStatus+"'到'"+afterChangeStatus+"'变化对应的Action未定义。");
        return (String)map4JobCard.get(sp);
    }
    
    //根据报修单的状态变化来获得对应的CustProblemProcess记录的action的取值
    public static String getAction4Repair(String beforeChangeStatus, String afterChangeStatus) {
        StatusPair sp = new StatusPair(beforeChangeStatus, afterChangeStatus);
        if (map4Repair.containsKey(sp) == false)
            throw new IllegalArgumentException("报修单状态从'"+beforeChangeStatus+"'到'"+afterChangeStatus+"'变化对应的Action未定义。");
        return (String)map4Repair.get(sp);

    }
    
    /*
     * 获得报修单回访操作对应的action
     */
    public static String getCallBackAction4Repair() {
        return CommonConstDefinition.CUSTPROBLEMPROCESS_ACTION_CALLBACK;
    }

    /*
     * 获得工单回访操作对应的action，不存在该操作
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
 * 由两个状态来唯一标识的一个类
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