/**
 * FinalcialEJBEvent.java
 */
package com.dtv.oss.service.ejbevent.config;

import com.dtv.oss.service.ejbevent.AbstractEJBEvent;

/**
 * @author 260425w 2006-5-30 14:51:29
 */
public class FinancialSettingEJBEvent extends AbstractEJBEvent {
	/**
	 * the action flag to update the financial general setting object(only one
	 * object,for db only one record).
	 */
	public static final int UPDATE_GENERAL_SETTING = 1;

	/**
	 * the action flag to create a charge cycle object .
	 */
	public static final int NEW_CHARGE_CYCLE_TYPE = 20;

	/**
	 * the action flag to update a charge cycle object .
	 */
	public static final int UPDATE_CHARGE_CYCLE_TYPE = 21;

	/**
	 * the action flag to delete a charge cycle object .
	 */
	public static final int DELETE_CHARGE_CYCLE_TYPE = 22;

	/**
	 * the action flag to create a account cycle object
	 */
	public static final int NEW_ACCOUNT_CYCLE_TYPE = 30;

	/**
	 * the action flag to update a account cycle object
	 */
	public static final int UPDATE_ACCOUNT_CYCLE_TYPE = 31;

	/**
	 * the action flag to delete a account cycle object
	 */
	public static final int DELETE_ACCOUNT_CYCLE_TYPE = 32;

	/**
	 * the action flag to a object of reference to a account cycle by customer
	 */
	public static final int NEW_CUSTOMER_ACCOUNT_CYCLE = 40;

	/**
	 * the action flag to a object of reference to a account cycle by customer
	 */
	public static final int UPDATE_CUSTOMER_ACCOUNT_CYCLE = 41;

	/**
	 * the action flag to a object of reference to a account cycle by customer
	 */
	public static final int DELETE_CUSTOMER_ACCOUNT_CYCLE = 42;

	/**
	 * the action flag to create a account type object
	 */
	public static final int NEW_ACCOUNT_TYPE = 50;

	/**
	 * the action flag to update a account type object
	 */
	public static final int UPDATE_ACCOUNT_TYPE = 51;

	/**
	 * the action flag to delete a account type object
	 */
	public static final int DELETE_ACCOUNT_TYPE = 52;

	/**
	 * the goal dto to be created ,updated,or deleted.
	 */
	Object dto;

	public FinancialSettingEJBEvent() {

	}

	public FinancialSettingEJBEvent(int actionType) {
		setActionType(actionType);
	}

	public FinancialSettingEJBEvent(Object dto, int actionType) {
		setDto(dto);
		setActionType(actionType);
	}

	public Object getDto() {
		return dto;
	}

	public void setDto(Object dto) {
		this.dto = dto;
	}

}
