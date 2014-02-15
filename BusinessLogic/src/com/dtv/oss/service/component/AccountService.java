/*
 * Created on 2005-9-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.service.component;

import javax.ejb.*;

import java.util.*;

import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.log.*;
import com.dtv.oss.dto.*;
import com.dtv.oss.domain.*;
import com.dtv.oss.service.util.*;
import com.dtv.oss.service.factory.*;
import com.dtv.oss.util.*;
import com.dtv.oss.service.ejbevent.*;

/**
 * @author Leon Liu
 * 
 * 处理与帐户相关的业务增加
 */
public class AccountService extends AbstractService {
	private static final Class clazz = AccountService.class;

	private ServiceContext serviceContext = null;

	/**
	 * constructer method
	 * 
	 * @param context
	 */
	public AccountService(ServiceContext serviceContext) {
		this.serviceContext = serviceContext;
		setOperatorID(PublicService.getCurrentOperatorID(serviceContext));
	}

	/**
	 * 根据账户id查找并封装账户信息
	 * 
	 * @param accountID
	 * @return
	 * @throws ServiceException
	 */
	public static AccountDTO encapsulateAccountDto(int accountID)
			throws ServiceException {
		try {
			AccountDTO accountDTO = new AccountDTO();
			AccountHome accountHome = HomeLocater.getAccountHome();
			Account account = accountHome.findByPrimaryKey(new Integer(
					accountID));
			accountDTO.setAccountID(accountID);
			accountDTO.setAccountType(account.getAccountType());
			return accountDTO;
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "create", e);
			throw new ServiceException("查找账户信息时定位错误");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "create", e);
			throw new ServiceException("查找账户信息时错误");
		}
	}

	/**
	 * 根据NewCustAccountInfoDTO创建客户账户信息
	 * 
	 * @param acctInfoDto
	 * @throws ServiceException
	 */
	public void create(NewCustAccountInfoDTO acctInfoDto, AddressDTO addressDTO)
			throws ServiceException {
		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setAccountType(acctInfoDto.getAccountType());
		accountDTO.setMopID(acctInfoDto.getMopID());
		accountDTO.setAccountName(acctInfoDto.getAccountName());
		accountDTO.setBankAccountName(acctInfoDto.getBankAccountName());
		accountDTO.setBankAccount(acctInfoDto.getBankAccount());
		if (addressDTO == null) {
			accountDTO.setAddressID(acctInfoDto.getAddressID());
		}
		// 创件账户信息
		create(accountDTO, addressDTO);
	}

	/**
	 * 针对账户的状态所作做的更新（客户销账户/销客户/账单调账）
	 * 
	 * @param accountDTO
	 * @param actionType
	 * @throws ServiceException
	 */
	public static void updateAccountInfo(AccountDTO accountDTO, int actionType)
			throws ServiceException {
		try {
			AccountHome accountHome = HomeLocater.getAccountHome();
			Account account = accountHome.findByPrimaryKey(new Integer(
					accountDTO.getAccountID()));
			switch (actionType) {
			// 客户销账户
			case EJBEvent.CLOSECUSTOMERACCOUNT:
				account.setStatus(CommonConstDefinition.ACCOUNT_STATUS_CLOSE);
				account.setDtLastmod(TimestampUtility.getCurrentDate());
				break;
			// 账单调账
			case EJBEvent.INVOICE_ADJUSTMENT:
				account.setStatus(CommonConstDefinition.ACCOUNT_STATUS_NORMAL);
				account.setDtLastmod(TimestampUtility.getCurrentDate());
				break;
			// 退户
			case EJBEvent.WITHDRAW:
				account.setStatus(CommonConstDefinition.ACCOUNT_STATUS_CLOSE);
				account.setDtLastmod(TimestampUtility.getCurrentDate());
				break;
			// 付帐单
			case EJBEvent.PAYBYBILL:
				if (!CommonConstDefinition.ACCOUNT_STATUS_NORMAL.equals(account
						.getStatus())) {
					account
							.setStatus(CommonConstDefinition.ACCOUNT_STATUS_NORMAL);
					account.setDtLastmod(TimestampUtility.getCurrentDate());
				}
				break;
			default:
				break;
			}

		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "create", e);
			throw new ServiceException("更新账户信息时定位错误");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "create", e);
			throw new ServiceException("更新账户信息时查找错误");
		}
	}

	/**
	 * 根据传入进来的客户账户信息和操作类型
	 * 
	 * @param accountDTO
	 *            客户账户信息（其它accountDTO之外信息通过servicecontext传入）
	 * @param actonType
	 *            操作类型,统一定义在ejbevent中
	 * @throws ServiceException
	 */
	public void updateAccountInfo(AccountDTO accountDTO, AddressDTO addressDTO,
			int csiID) throws ServiceException {

		try {
			AccountHome accountHome = HomeLocater.getAccountHome();
			Account account = accountHome.findByPrimaryKey(new Integer(
					accountDTO.getAccountID()));
			// 设置地址信息的id.便于地址信息更新时使用
			if (addressDTO != null) {
				addressDTO.setAddressID(account.getAddressID());
			}
			// 取得付费方式
			MethodOfPaymentHome mopHome = HomeLocater.getMethodOfPaymentHome();
			MethodOfPayment currentMop = mopHome.findByPrimaryKey(new Integer(
					accountDTO.getMopID()));
			if (currentMop != null) {
				// 如果是银行付费，要置为待串配
				if (CommonConstDefinition.MOPPAYTYPE_PAYBYBANK
						.equals(currentMop.getPayType())) {
					if (accountDTO.getBankAccount() != null
							&& !"".equals(accountDTO.getBankAccount())) {
						if (!accountDTO.getBankAccount().equals(
								account.getBankAccount())) {
							accountDTO
									.setBankAccountStatus(CommonConstDefinition.ACCOUNTBANKACCOUNT_STATUS_WAIT);
						}
					} else {
						throw new ServiceException("该付费类型必须输入银行帐号");
					}
				} else {
					if (accountDTO.getBankAccount() != null
							&& !"".equals(accountDTO.getBankAccount().trim())) {
						LogUtility.log(clazz, LogLevel.ERROR,
								"该付费类型不必输入银行帐号,accountDTO:", accountDTO);
						throw new ServiceException("该付费类型不必输入银行帐号");
					}
					if (account.getBankAccountStatus() != null
							&& !"".equals(account.getBankAccountStatus())) {
						accountDTO.setBankAccountStatus("");
					}
				}
			} else {
				throw new ServiceException("没有选择付费类型");
			}

			AddressHome addressHome = HomeLocater.getAddressHome();
			Address address = null;
			if (addressDTO != null) {
				address = addressHome.findByPrimaryKey(new Integer(addressDTO
						.getAddressID()));
				// 地址中存在任何一项被修改之后就需要更新地址信息并设定地址可疑标志
				if (!addressDTO.getDetailAddress().equals(
						address.getDetailAddress())
					//	|| !addressDTO.getPostcode().equals(  注释掉by david.Yang ,因丽江不用邮编
					//			address.getPostcode())
						|| addressDTO.getDistrictID() != address
								.getDistrictID()) {
					accountDTO
							.setBillAddressFlag(CommonConstDefinition.YESNOFLAG_NO);
					accountDTO.setInvalidAddressReason("");
				}
			}
			// 先备份，后更新
			createAcctBackupInfo(accountDTO, csiID);
			if (address != null) {
				addressDTO.setAddressID(accountDTO.getAddressID());
				address = addressHome.findByPrimaryKey(new Integer(addressDTO
						.getAddressID()));
				int addressUpdateResult = address.ejbUpdate(addressDTO);
				if (addressUpdateResult == -1) {
					throw new ServiceException("更新账户地址信息时发生错误");
				}
				account.setBillAddressFlag(CommonConstDefinition.YESNOFLAG_NO);
				serviceContext.put(Service.ACCOUNT_ADDRESS_EJB, address);
			}

			int updateResult = account.ejbUpdate(accountDTO);
			if (updateResult == -1) {
				throw new ServiceException("更新账户信息时发生错误");
			}
			serviceContext.put(Service.ACCOUNT, account);
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "create", e);
			throw new ServiceException("更新账户信息时定位错误");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "create", e);
			throw new ServiceException("更新账户信息时查找错误");
		}
	}

	public void createAcctBackupInfo(AccountDTO acctDto, int csiID)
			throws ServiceException {
		OldCustAccountInfoDTO oldCustAcctInfoDto = getOldCustAccountInfoDTOFromAccountInfo(
				csiID, acctDto.getAccountID());
		try {
			OldCustAccountInfoHome oldCustAcctInfoHome = HomeLocater
					.getOldCustAccountInfoHome();
			oldCustAcctInfoHome.create(oldCustAcctInfoDto);
			NewCustAccountInfoDTO newCustAcctInfoDto = getNewCustAccountInfoDTOFromAccountInfo(
					csiID, acctDto);
			NewCustAccountInfoHome newCustAcctInfoHome = HomeLocater
					.getNewCustAccountInfoHome();
			newCustAcctInfoHome.create(newCustAcctInfoDto);
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("查找客户备份信息接口出错");
		} catch (CreateException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("创建客户备份信息出错");
		}
	}

	private OldCustAccountInfoDTO getOldCustAccountInfoDTOFromAccountInfo(
			int csiID, int accountID) throws ServiceException {
		OldCustAccountInfoDTO oldCustAcctInfoDto = null;
		Account acct = null;
		try {
			AccountHome acctHome = HomeLocater.getAccountHome();
			acct = acctHome.findByPrimaryKey(new Integer(accountID));
			oldCustAcctInfoDto = new OldCustAccountInfoDTO();
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR,
					"getOldCustAccountInfoDTOFromAccountID", e);
			throw new ServiceException("账户信息时定位错误");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR,
					"getOldCustAccountInfoDTOFromAccountID", e);
			throw new ServiceException("账户信息时查找错误");
		}
		oldCustAcctInfoDto.setAccountID(acct.getAccountID().intValue());
		oldCustAcctInfoDto.setAccountName(acct.getAccountName());
		oldCustAcctInfoDto.setAccountType(acct.getAccountType());
		oldCustAcctInfoDto.setAddressFlag(acct.getBillAddressFlag());
		oldCustAcctInfoDto.setAddressID(acct.getAddressID());
		oldCustAcctInfoDto.setBankAccount(acct.getBankAccount());
		oldCustAcctInfoDto.setBankAccountName(acct.getBankAccountName());
		oldCustAcctInfoDto.setCsiID(csiID);
		oldCustAcctInfoDto.setDtCreate(TimestampUtility.getCurrentTimestamp());
		oldCustAcctInfoDto.setDtLastmod(TimestampUtility.getCurrentTimestamp());
		oldCustAcctInfoDto.setMopID(acct.getMopID());
		oldCustAcctInfoDto.setComments(acct.getComments());
		return oldCustAcctInfoDto;
	}

	private NewCustAccountInfoDTO getNewCustAccountInfoDTOFromAccountInfo(
			int csiID, AccountDTO acctDto) throws ServiceException {
		Address bakAddress = null;
		if (acctDto.getAddressID() != 0)
			bakAddress = PublicService.createAddressBakup(acctDto
					.getAddressID());
		int addressID = 0;
		if (bakAddress == null) {
			try {
				AccountHome acctHome = HomeLocater.getAccountHome();
				Account acct = acctHome.findByPrimaryKey(new Integer(acctDto
						.getAccountID()));
				addressID = acct.getAddressID();
			} catch (HomeFactoryException e) {
				LogUtility.log(clazz, LogLevel.ERROR,
						"getOldCustAccountInfoDTOFromAccountID", e);
				throw new ServiceException("账户信息时定位错误");
			} catch (FinderException e) {
				LogUtility.log(clazz, LogLevel.ERROR,
						"getOldCustAccountInfoDTOFromAccountID", e);
				throw new ServiceException("账户信息时查找错误");
			}
		} else {
			addressID = bakAddress.getAddressID().intValue();
		}
		NewCustAccountInfoDTO newCustAcctInfoDto = new NewCustAccountInfoDTO();
		newCustAcctInfoDto.setAccountID(acctDto.getAccountID());
		newCustAcctInfoDto.setAccountName(acctDto.getAccountName());
		newCustAcctInfoDto.setAccountType(acctDto.getAccountType());
		newCustAcctInfoDto.setAddressFlag(acctDto.getBillAddressFlag());
		newCustAcctInfoDto.setAddressID(addressID);
		newCustAcctInfoDto.setBankAccount(acctDto.getBankAccount());
		newCustAcctInfoDto.setBankAccountName(acctDto.getBankAccountName());
		newCustAcctInfoDto.setCsiID(csiID);
		newCustAcctInfoDto.setDtCreate(TimestampUtility.getCurrentTimestamp());
		newCustAcctInfoDto.setDtLastmod(TimestampUtility.getCurrentTimestamp());
		newCustAcctInfoDto.setMopID(acctDto.getMopID());
		newCustAcctInfoDto.setComments(acctDto.getComments());
		if (bakAddress != null)
			acctDto.setAddressID(bakAddress.getAddressID().intValue());
		return newCustAcctInfoDto;
	}

	/**
	 * 创建账户信息
	 * 
	 * @param accountDTO
	 * @param addressDTO
	 * @throws ServiceException
	 */
	public void create(AccountDTO accountDTO, AddressDTO addressDTO)
			throws ServiceException {
		try {
			// 取得付费方式
			MethodOfPaymentHome mopHome = HomeLocater.getMethodOfPaymentHome();
			MethodOfPayment mop = mopHome.findByPrimaryKey(new Integer(
					accountDTO.getMopID()));
			if (mop != null) {
				// 如果是银行付费，要置为待串配
				if (mop.getPayType().equals(
						CommonConstDefinition.MOPPAYTYPE_PAYBYBANK))
					accountDTO
							.setBankAccountStatus(CommonConstDefinition.ACCOUNTBANKACCOUNT_STATUS_WAIT);
			}

			// 取得客户本地对象
			Customer customerLocal = (Customer) serviceContext
					.get(Service.CUSTOMER);
			if (customerLocal == null) {
				customerLocal = HomeLocater.getCustomerHome().findByPrimaryKey(
						new Integer(accountDTO.getCustomerID()));
			}
			// 取得并设置计费帐务周期类型
			CustAcctCycleCfgHome caccHome = HomeLocater
					.getCustAcctCycleCfgHome();
			Collection caccList = caccHome
					.findBillCycleTypeIDByCustomerTypeAndAccountType(
							customerLocal.getCustomerType(), accountDTO
									.getAccountType());
			Iterator ite4cacc = caccList.iterator();
			if (caccList.isEmpty())
				throw new ServiceException("计费周期类型与客户类型、帐户类型之间的关系没有定义");
			int billCycleTypeId = ((CustAcctCycleCfg) ite4cacc.next())
					.getBillingCycleTypeId();
			LogUtility.log(clazz, LogLevel.DEBUG, "■■billCycleTypeId■■"
					+ billCycleTypeId);
			accountDTO.setBillingCycleTypeID(billCycleTypeId);
			// 如果地址信息非空的话，则创建地址信息,并在账户信息中设置
			if (addressDTO != null) {
				AddressHome addressHome = HomeLocater.getAddressHome();
				Address address = addressHome.create(addressDTO);
				accountDTO.setAddressID(address.getAddressID().intValue());
			}
			// 设置账户的初始化信息
			accountDTO
					.setAccountClass(CommonConstDefinition.ACCOUNTCLASS_CUSTOMER);

			accountDTO.setBillAddressFlag(CommonConstDefinition.YESNOFLAG_NO);
			accountDTO.setCurrency(CommonConstDefinition.CURRENCY_RMB);
			accountDTO.setRedirectAccountID(0);
			accountDTO.setStatus(CommonConstDefinition.ACCOUNT_STATUS_NORMAL);
			accountDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());

			AccountHome acctHome = HomeLocater.getAccountHome();
			LogUtility
					.log(clazz, LogLevel.DEBUG, "■■accountDTO■■" + accountDTO);
			Account account = acctHome.create(accountDTO);
			accountDTO.setAccountID(account.getAccountID().intValue());

			serviceContext.put(Service.ACCOUNT, account);
			serviceContext.put(Service.ACCOUNT_ID, account.getAccountID());

			// 取得受理单本地对象
			CustServiceInteraction csi = (CustServiceInteraction) serviceContext
					.get(Service.CSI);
			csi.setAccountID(account.getAccountID().intValue());
			SystemEventRecorder.AddEvent4Customer(
					SystemEventRecorder.SE_ACCOUNT_OPEN, customerLocal
							.getCustomerID().intValue(), account.getAccountID()
							.intValue(), 0, csi.getId().intValue(),
					PublicService.getCurrentOperatorID(serviceContext),
					SystemEventRecorder.SE_STATUS_CREATE);
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "create", e);
			throw new ServiceException("创建账户信息时定位错误");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "create", e);
			throw new ServiceException("创建账户信息时查找错误");
		} catch (CreateException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "create", e);
			throw new ServiceException("创建账户信息时错误");
		}
	}

	/**
	 * 创建账户信息
	 */
	public void batchUpdateAccountInfo(int csiID, Collection addressDTOList,
			Collection accountDTOList) throws ServiceException {
		if ((addressDTOList == null || addressDTOList.size() == 0)
				|| (accountDTOList == null || accountDTOList.size() == 0)
				|| (addressDTOList.size() != accountDTOList.size())) {
			throw new ServiceException("地址和账户的集合不匹配");
		}
		Iterator accountIteror = accountDTOList.iterator();
		Iterator addressIteror = addressDTOList.iterator();
		while (accountIteror.hasNext()) {
			AddressDTO addressDto = (AddressDTO) addressIteror.next();
			AccountDTO accountDto = (AccountDTO) accountIteror.next();
			
			// 校验银行帐号,wp@20080227
			if (accountDto.getBankAccount() != null
					&& !"".equals(accountDto.getBankAccount())) {
				if (accountDto.getMopID() != 0) {
					String functionResult = BusinessUtility
							.callFunctionForCheckBankAccount(accountDto.getMopID(),
									accountDto.getBankAccount());
					if (!"true".equals(functionResult))
						throw new ServiceException(functionResult);
				}
			}			

			// 更新账户和账户地址信息
			updateAccountInfo(accountDto, addressDto, csiID);
		}

	}

	/**
	 * 创建账户信息
	 * 
	 * @param accountDTO
	 * @param addressDTO
	 * @PRAM isCreateBakInfo： 是否创建用户帐户备份信息
	 * @throws ServiceException
	 */
	public void create(AccountDTO accountDTO, AddressDTO addressDTO,
			boolean isCreateBakInfo) throws ServiceException {
		create(accountDTO, addressDTO);
		try {
			if (isCreateBakInfo) {
				NewCustAccountInfoDTO newAcctDTO = getNewCustAccountInfoFromCustomerAccount((Account) serviceContext
						.get(Service.ACCOUNT));
				if (newAcctDTO != null) {
					NewCustAccountInfoHome home = HomeLocater
							.getNewCustAccountInfoHome();
					home.create(newAcctDTO);
				}
			}
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "create", e);
			throw new ServiceException("创建账户信息时定位错误");
		} catch (CreateException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "create", e);
			throw new ServiceException("创建账户信息时错误");
		}
	}

	/**
	 * 通过Customer得到用户的信息，主要用于过户、迁移时修改用户信息，而又必须保存老的客户信息于T_NEWCUSTACCOUNTINFO
	 * 
	 * @param ca
	 * @return
	 */
	private NewCustAccountInfoDTO getNewCustAccountInfoFromCustomerAccount(
			Account ca) {
		if (ca == null)
			return null;

		NewCustAccountInfoDTO newCAInfoDTO = new NewCustAccountInfoDTO();
		int csiid = 0;
		if (serviceContext.get(Service.CSI_ID) != null)
			csiid = ((Integer) serviceContext.get(Service.CSI_ID)).intValue();

		newCAInfoDTO.setAccountID(ca.getAccountID().intValue());
		newCAInfoDTO.setAccountType(ca.getAccountType());
		newCAInfoDTO.setAddressFlag(ca.getBillAddressFlag());
		newCAInfoDTO.setAddressID(ca.getAddressID());
		newCAInfoDTO.setBankAccount(ca.getBankAccount());
		newCAInfoDTO.setBankAccountName(ca.getBankAccountName());
		newCAInfoDTO.setCsiID(csiid);
		// newCAInfoDTO.setDescription(ca.get);
		newCAInfoDTO.setAccountName(ca.getAccountName());
		newCAInfoDTO.setMopID(ca.getMopID());
		return newCAInfoDTO;
	}

	/**
	 * 取消业务帐户
	 * 
	 * @param customerID ：
	 *            客户ID
	 * @throws ServiceException
	 * @throws HomeFactoryException
	 * @throws FinderException
	 */
	public void close(int customerID) throws ServiceException {
		// 得到accountList列表
		try {
			AccountHome accountHome = HomeLocater.getAccountHome();
			Collection accountList = accountHome.findByCustomerId(customerID);
			Account account = null;
			// 处理accountList列表
			Iterator itAccount = accountList.iterator();
			while (itAccount.hasNext()) {
				account = (Account) itAccount.next();
				if (!(CommonConstDefinition.ACCOUNT_STATUS_CLOSE.equals(account
						.getStatus()))) {
					account
							.setStatus(CommonConstDefinition.ACCOUNT_STATUS_CLOSE);
					account.setDtLastmod(TimestampUtility.getCurrentDate());
				}
			}
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "close", e);
			throw new ServiceException("取消账户信息时定位错误");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "close", e);
			throw new ServiceException("取消账户信息时查找错误");
		}
	}
}
