/*
 * Created on 2005-11-30
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.test;

import com.dtv.oss.dto.AccountDTO;
import com.dtv.oss.dto.AddressDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.service.controller.EJBController;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.csr.*;
import com.dtv.oss.util.*;
import com.dtv.oss.service.commandresponse.*;
import com.dtv.oss.service.util.*;
/**
 * @author 240910y
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AccountCommandTest {
	
	
	
	public static void testAlterAccountInfo(EJBController controller){
		try{
			//设置账户信息
			AccountDTO dto = new AccountDTO();
			dto.setAccountID(110);
			dto.setCustomerID(111);
			dto.setAccountName("杨测试账号");
			//参考公用数据SET_F_ACCOUNTCLASS
/*			
 			dto.setAccountClass("");
			//参考工用数据SET_F_ACCOUNTTYPE
		
			dto.setAccountType("");
			dto.setInvoiceLayoutFormat("");
*/	
			//付费类型指向T_MethodOfPayment 表
			dto.setMopID(102);
			dto.setBankAccount("1235345456457656");
/*			
			//参考SET_C_CUSTOMERCARDTYPE
			dto.setCardType("");
			dto.setCardID("");
			//银行账号的状态(SET_F_ACCOUNTBANKACCOUNTSTATUS)
			dto.setBankAccountStatus("");
			dto.setAddressID(158);
			dto.setBillAddressFlag("");
			dto.setCurrency("");
			dto.setRedirectAccountID(1);
			dto.setBalance(0);
			dto.setBbf(0);
			dto.setSmallChange(0);
			dto.setCashDeposit(0);
			dto.setTokenDeposit(0);
			dto.setBillingCycleTypeID(0);
			dto.setNextInvoiceDate(null);
			//dto.setCreateTime(null);
			//参考固定公用数据：SET_F_ACCOUNTSTATUS
			dto.setStatus("");
			dto.setDtCreate(null);
*/
			dto.setDtLastmod(TestDbuitl.getTimeStamp("T_ACCOUNT","DT_LASTMOD","ACCOUNTID","110"));
			
			//设置地址信息
			AddressDTO addressDto = new AddressDTO();
			addressDto.setAddressID(158);
			addressDto.setPostcode("9999");
			addressDto.setDetailAddress("杨测试地址");
			addressDto.setDistrictID(1);
			addressDto.setDtCreate(null);
			addressDto.setDtLastmod(TestDbuitl.getTimeStamp("T_ADDRESS","DT_LASTMOD","ADDRESSID","158"));
			CustServiceInteractionDTO csiDTO=new CustServiceInteractionDTO();
			//SET_V_CUSTSERVICEINTERACTIONTYPE
			csiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_IA);
			AccountEJBEvent accountEJBEvent=new AccountEJBEvent(EJBEvent.UPDATEACCOUNTINFO);
			accountEJBEvent.setCsiDto(csiDTO);
			accountEJBEvent.setAccountDTO(dto);
			accountEJBEvent.setAcctAddrDto(addressDto);
			accountEJBEvent.setEJBCommandClassName("com.dtv.oss.service.command.csr.AccountCommand");
			accountEJBEvent.setOperatorID(101);
			System.out.println(accountEJBEvent.getAccountDTO().getAccountID());
			controller.processEJBEvent(accountEJBEvent);;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void testCloseAccount(EJBController controller){
		try{
			//设置账户信息
			AccountDTO dto = new AccountDTO();
			dto.setAccountID(117);
			dto.setCustomerID(107);
/*			
			dto.setAccountName("杨测试账号");
			//参考公用数据SET_F_ACCOUNTCLASS
			dto.setAccountClass("");
			//参考工用数据SET_F_ACCOUNTTYPE
			dto.setAccountType("");
			dto.setInvoiceLayoutFormat("");
			//付费类型指向T_MethodOfPayment 表
			dto.setMopID(102);
			dto.setBankAccount("1235345456457656");
			//参考SET_C_CUSTOMERCARDTYPE
			dto.setCardType("");
			dto.setCardID("");
			//银行账号的状态(SET_F_ACCOUNTBANKACCOUNTSTATUS)
			dto.setBankAccountStatus("");
			dto.setAddressID(158);
			dto.setBillAddressFlag("");
			dto.setCurrency("");
			dto.setRedirectAccountID(1);
			dto.setBalance(0);
			dto.setBbf(0);
			dto.setSmallChange(0);
			dto.setCashDeposit(0);
			dto.setTokenDeposit(0);
			dto.setBillingCycleTypeID(0);
			dto.setNextInvoiceDate(null);
			//dto.setCreateTime(null);
			//参考固定公用数据：SET_F_ACCOUNTSTATUS
			dto.setStatus("");
			dto.setDtCreate(null);
			dto.setDtLastmod(TestDbuitl.getTimeStamp("T_ACCOUNT","DT_LASTMOD","ACCOUNTID","110"));

			//设置地址信息
			AddressDTO addressDto = new AddressDTO();
			addressDto.setAddressID(158);
			addressDto.setPostcode("9999");
			addressDto.setDetailAddress("杨测试地址");
			addressDto.setDistrictID(1);
			addressDto.setDtCreate(null);
			addressDto.setDtLastmod(TestDbuitl.getTimeStamp("T_ADDRESS","DT_LASTMOD","ADDRESSID","158"));
			
*/			
			CustServiceInteractionDTO csiDTO=new CustServiceInteractionDTO();
			//SET_V_CUSTSERVICEINTERACTIONTYPE
			csiDTO.setType(CommonConstDefinition.CUSTSERVICEINTERACTIONTYPE_OC);
			AccountEJBEvent accountEJBEvent=new AccountEJBEvent(EJBEvent.CLOSECUSTOMERACCOUNT);
			accountEJBEvent.setAccountDTO(dto);
			//accountEJBEvent.setAcctAddrDto(addressDto);
			accountEJBEvent.setCsiDto(csiDTO);
			accountEJBEvent.setEJBCommandClassName("com.dtv.oss.service.command.csr.AccountCommand");
			accountEJBEvent.setOperatorID(101);
			controller.processEJBEvent(accountEJBEvent);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public static void testCreateAccount(EJBController controller){
		try{
			//设置账户信息
			AccountDTO dto = new AccountDTO();
			//dto.setAccountID(110);
			dto.setCustomerID(107);
			
			dto.setAccountName("杨测试账号3");
			//参考公用数据SET_F_ACCOUNTCLASS
			dto.setAccountClass("C");
			//参考工用数据SET_F_ACCOUNTTYPE
			dto.setAccountType("N");
			dto.setInvoiceLayoutFormat("");
			//付费类型指向T_MethodOfPayment 表
			dto.setMopID(102);
			dto.setBankAccount("8567567567567");
			//参考SET_C_CUSTOMERCARDTYPE
			dto.setCardType("C");
			dto.setCardID("444");
			//银行账号的状态(SET_F_ACCOUNTBANKACCOUNTSTATUS)
			//dto.setBankAccountStatus("");
			//dto.setAddressID(158);
/*			dto.setBillAddressFlag("");
			dto.setCurrency("");
			dto.setRedirectAccountID(1);
			dto.setBalance(0);
			dto.setBbf(0);
			dto.setSmallChange(0);
			dto.setCashDeposit(0);
			dto.setTokenDeposit(0);
			dto.setBillingCycleTypeID(0);
		dto.setNextInvoiceDate(null);
	*/			//dto.setCreateTime(null);
			//参考固定公用数据：SET_F_ACCOUNTSTATUS
			//dto.setStatus("");
			dto.setDtCreate(TimestampUtility.getCurrentDate());
			dto.setDtLastmod(TestDbuitl.getTimeStamp("T_ACCOUNT","DT_LASTMOD","ACCOUNTID","110"));

			//设置地址信息
			AddressDTO addressDto = new AddressDTO();
			addressDto.setAddressID(158);
			addressDto.setPostcode("9999");
			addressDto.setDetailAddress("杨测试地址");
			addressDto.setDistrictID(1);
			addressDto.setDtCreate(TimestampUtility.getCurrentDate());
			addressDto.setDtLastmod(TestDbuitl.getTimeStamp("T_ADDRESS","DT_LASTMOD","ADDRESSID","158"));
			CustServiceInteractionDTO csiDTO=new CustServiceInteractionDTO();
			//SET_V_CUSTSERVICEINTERACTIONTYPE
			csiDTO.setType("OA");
			AccountEJBEvent accountEJBEvent=new AccountEJBEvent(EJBEvent.OPENACCOUNTFORCUSTOMER);
			accountEJBEvent.setCsiDto(csiDTO);
			accountEJBEvent.setAccountDTO(dto);
			accountEJBEvent.setCustomerID(107);
			accountEJBEvent.setAcctAddrDto(addressDto);
			accountEJBEvent.setEJBCommandClassName("com.dtv.oss.service.command.csr.AccountCommand");
			accountEJBEvent.setOperatorID(101);
			controller.processEJBEvent(accountEJBEvent);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void testDepositInAdvance(EJBController controller){
		
		
	}
	public static void testPayInvoice(EJBController controller){
		
		
	}
	public static void testAdjust(EJBController controller){
		
		
	}
}
