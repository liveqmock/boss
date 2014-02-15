package com.dtv.oss.service.command.config;

import com.dtv.oss.service.command.QueryCommand;
import com.dtv.oss.service.ejbevent.QueryEJBEvent;
import com.dtv.oss.service.ejbevent.config.QueryFinancialSettingEJBEvent;
import com.dtv.oss.service.listhandler.ValueListHandler;
import com.dtv.oss.service.listhandler.config.FinancialAccountCycleTypeDetailHandler;
import com.dtv.oss.service.listhandler.config.FinancialAccountCycleTypeListHandler;
import com.dtv.oss.service.listhandler.config.FinancialAccountTypeDetailHandler;
import com.dtv.oss.service.listhandler.config.FinancialAccountTypeListHandler;
import com.dtv.oss.service.listhandler.config.FinancialChargeCycleTypeDetailHandler;
import com.dtv.oss.service.listhandler.config.FinancialChargeCycleTypeListHandler;
import com.dtv.oss.service.listhandler.config.FinancialCustomerAccountCycleDetailHandler;
import com.dtv.oss.service.listhandler.config.FinancialCustomerAccountCycleListHandler;
import com.dtv.oss.service.listhandler.config.FinancialGeneralSettingDetailHandler;

public class QueryConfigFinancialSettingCommand extends QueryCommand {

	public ValueListHandler getActualListHandler(QueryEJBEvent event) {
		ValueListHandler handler = null;
		System.out.println(getClass().getName()
				+ ":getActualListHandler:event1:" + event);
		System.out.println(getClass().getName()
				+ ":getActualListHandler:event.getType:" + event.getType());
		int type = event.getType();
		switch (type) {
		case QueryFinancialSettingEJBEvent.QUERY_GENERAL_SETTING: {
			handler = new FinancialGeneralSettingDetailHandler();
			break;
		}
		case QueryFinancialSettingEJBEvent.QUERY_ACCOUNT_TYPE_BRIEF_LIST: {
			handler = new FinancialAccountTypeListHandler();

			break;
		}
		case QueryFinancialSettingEJBEvent.QUERY_ACCOUNT_TYPE_DETAIL: {
			handler = new FinancialAccountTypeDetailHandler();
			break;
		}
		case QueryFinancialSettingEJBEvent.QUERY_CHARGE_CYCLE_TYPE_BRIEF_LIST: {
			handler = new FinancialChargeCycleTypeListHandler();
			System.out.println(getClass().getName()
					+ ":getActualListHandler:event:" + event);
			break;
		}
		case QueryFinancialSettingEJBEvent.QUERY_CHARGE_CYCLE_TYPE_DETAIL: {
			handler = new FinancialChargeCycleTypeDetailHandler();
			break;
		}
		case QueryFinancialSettingEJBEvent.QUERY_ACCOUNT_CYCLE_TYPE_BRIEF_LIST: {
			handler = new FinancialAccountCycleTypeListHandler();
			break;
		}
		case QueryFinancialSettingEJBEvent.QUERY_ACCOUNT_CYCLE_TYPE_DETAIL: {
			handler = new FinancialAccountCycleTypeDetailHandler();
			break;
		}
		case QueryFinancialSettingEJBEvent.QUERY_CUSTOMER_ACCOUNT_CYCLE_BRIEF_LIST: {
			handler = new FinancialCustomerAccountCycleListHandler();
			break;
		}
		case QueryFinancialSettingEJBEvent.QUERY_CUSTOMER_ACCOUNT_CYCLE_DETAIL: {
			handler = new FinancialCustomerAccountCycleDetailHandler();
			break;
		}
		}
		return handler;
	}
}
