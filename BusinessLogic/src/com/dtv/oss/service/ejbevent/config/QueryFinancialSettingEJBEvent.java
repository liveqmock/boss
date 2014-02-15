package com.dtv.oss.service.ejbevent.config;

import com.dtv.oss.service.ejbevent.QueryEJBEvent;

public class QueryFinancialSettingEJBEvent extends QueryEJBEvent {
	/**
	 * the query action flag to query all info(detialed) of the financial
	 * general setting (only one object,for db only one record).
	 */
	public static final int QUERY_GENERAL_SETTING = 1;

	/**
	 * the query action flag to query the brief info of some charge cycle
	 * objects.
	 */
	public static final int QUERY_CHARGE_CYCLE_TYPE_BRIEF_LIST = 10;

	/**
	 * the query action flag to query one charge cycle object' detail info
	 */
	public static final int QUERY_CHARGE_CYCLE_TYPE_DETAIL = 11;

	/**
	 * the query action flag to query the brief info of some account cycle
	 * objects
	 */
	public static final int QUERY_ACCOUNT_CYCLE_TYPE_BRIEF_LIST = 20;

	/**
	 * the query action flag to query one accont cycle object's detail info
	 */
	public static final int QUERY_ACCOUNT_CYCLE_TYPE_DETAIL = 21;

	/**
	 * the query action flag to query the brief info of some reference objects
	 * to account cycle objects by customers
	 */
	public static final int QUERY_CUSTOMER_ACCOUNT_CYCLE_BRIEF_LIST = 31;

	/**
	 * the query action flag to query the detail info of one reference object to
	 * account cycle objects
	 */
	public static final int QUERY_CUSTOMER_ACCOUNT_CYCLE_DETAIL = 32;

	/**
	 * the query action flag to query the brief info of some account type
	 * objects
	 */
	public static final int QUERY_ACCOUNT_TYPE_BRIEF_LIST = 41;

	/**
	 * the query action flag to query one account type object's detail info
	 */
	public static final int QUERY_ACCOUNT_TYPE_DETAIL = 42;

	public QueryFinancialSettingEJBEvent(Object dto,int actionType) {
		setDto(dto);
		setType(actionType);
	}
	
	public QueryFinancialSettingEJBEvent() {
		this(0, 10);
	}

	public QueryFinancialSettingEJBEvent(int from, int to) {
		this(null, from, to, QUERY_GENERAL_SETTING);		
	}

	public QueryFinancialSettingEJBEvent(Object dto, int from, int to,
			int actionType) {
		super(dto, from, to, actionType);
		//setOperatorID(1);
	}

	public void setDto(Object dto) {
		super.setCriteriaDTO(dto);
	}

	public Object getDto() {
		return super.getCriteriaDTO();
	}

}
