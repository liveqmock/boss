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
 * �������ʻ���ص�ҵ������
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
	 * �����˻�id���Ҳ���װ�˻���Ϣ
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
			throw new ServiceException("�����˻���Ϣʱ��λ����");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "create", e);
			throw new ServiceException("�����˻���Ϣʱ����");
		}
	}

	/**
	 * ����NewCustAccountInfoDTO�����ͻ��˻���Ϣ
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
		// �����˻���Ϣ
		create(accountDTO, addressDTO);
	}

	/**
	 * ����˻���״̬�������ĸ��£��ͻ����˻�/���ͻ�/�˵����ˣ�
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
			// �ͻ����˻�
			case EJBEvent.CLOSECUSTOMERACCOUNT:
				account.setStatus(CommonConstDefinition.ACCOUNT_STATUS_CLOSE);
				account.setDtLastmod(TimestampUtility.getCurrentDate());
				break;
			// �˵�����
			case EJBEvent.INVOICE_ADJUSTMENT:
				account.setStatus(CommonConstDefinition.ACCOUNT_STATUS_NORMAL);
				account.setDtLastmod(TimestampUtility.getCurrentDate());
				break;
			// �˻�
			case EJBEvent.WITHDRAW:
				account.setStatus(CommonConstDefinition.ACCOUNT_STATUS_CLOSE);
				account.setDtLastmod(TimestampUtility.getCurrentDate());
				break;
			// ���ʵ�
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
			throw new ServiceException("�����˻���Ϣʱ��λ����");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "create", e);
			throw new ServiceException("�����˻���Ϣʱ���Ҵ���");
		}
	}

	/**
	 * ���ݴ�������Ŀͻ��˻���Ϣ�Ͳ�������
	 * 
	 * @param accountDTO
	 *            �ͻ��˻���Ϣ������accountDTO֮����Ϣͨ��servicecontext���룩
	 * @param actonType
	 *            ��������,ͳһ������ejbevent��
	 * @throws ServiceException
	 */
	public void updateAccountInfo(AccountDTO accountDTO, AddressDTO addressDTO,
			int csiID) throws ServiceException {

		try {
			AccountHome accountHome = HomeLocater.getAccountHome();
			Account account = accountHome.findByPrimaryKey(new Integer(
					accountDTO.getAccountID()));
			// ���õ�ַ��Ϣ��id.���ڵ�ַ��Ϣ����ʱʹ��
			if (addressDTO != null) {
				addressDTO.setAddressID(account.getAddressID());
			}
			// ȡ�ø��ѷ�ʽ
			MethodOfPaymentHome mopHome = HomeLocater.getMethodOfPaymentHome();
			MethodOfPayment currentMop = mopHome.findByPrimaryKey(new Integer(
					accountDTO.getMopID()));
			if (currentMop != null) {
				// ��������и��ѣ�Ҫ��Ϊ������
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
						throw new ServiceException("�ø������ͱ������������ʺ�");
					}
				} else {
					if (accountDTO.getBankAccount() != null
							&& !"".equals(accountDTO.getBankAccount().trim())) {
						LogUtility.log(clazz, LogLevel.ERROR,
								"�ø������Ͳ������������ʺ�,accountDTO:", accountDTO);
						throw new ServiceException("�ø������Ͳ������������ʺ�");
					}
					if (account.getBankAccountStatus() != null
							&& !"".equals(account.getBankAccountStatus())) {
						accountDTO.setBankAccountStatus("");
					}
				}
			} else {
				throw new ServiceException("û��ѡ�񸶷�����");
			}

			AddressHome addressHome = HomeLocater.getAddressHome();
			Address address = null;
			if (addressDTO != null) {
				address = addressHome.findByPrimaryKey(new Integer(addressDTO
						.getAddressID()));
				// ��ַ�д����κ�һ��޸�֮�����Ҫ���µ�ַ��Ϣ���趨��ַ���ɱ�־
				if (!addressDTO.getDetailAddress().equals(
						address.getDetailAddress())
					//	|| !addressDTO.getPostcode().equals(  ע�͵�by david.Yang ,�����������ʱ�
					//			address.getPostcode())
						|| addressDTO.getDistrictID() != address
								.getDistrictID()) {
					accountDTO
							.setBillAddressFlag(CommonConstDefinition.YESNOFLAG_NO);
					accountDTO.setInvalidAddressReason("");
				}
			}
			// �ȱ��ݣ������
			createAcctBackupInfo(accountDTO, csiID);
			if (address != null) {
				addressDTO.setAddressID(accountDTO.getAddressID());
				address = addressHome.findByPrimaryKey(new Integer(addressDTO
						.getAddressID()));
				int addressUpdateResult = address.ejbUpdate(addressDTO);
				if (addressUpdateResult == -1) {
					throw new ServiceException("�����˻���ַ��Ϣʱ��������");
				}
				account.setBillAddressFlag(CommonConstDefinition.YESNOFLAG_NO);
				serviceContext.put(Service.ACCOUNT_ADDRESS_EJB, address);
			}

			int updateResult = account.ejbUpdate(accountDTO);
			if (updateResult == -1) {
				throw new ServiceException("�����˻���Ϣʱ��������");
			}
			serviceContext.put(Service.ACCOUNT, account);
		} catch (HomeFactoryException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "create", e);
			throw new ServiceException("�����˻���Ϣʱ��λ����");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "create", e);
			throw new ServiceException("�����˻���Ϣʱ���Ҵ���");
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
			throw new ServiceException("���ҿͻ�������Ϣ�ӿڳ���");
		} catch (CreateException e) {
			LogUtility.log(clazz, LogLevel.ERROR, e);
			throw new ServiceException("�����ͻ�������Ϣ����");
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
			throw new ServiceException("�˻���Ϣʱ��λ����");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR,
					"getOldCustAccountInfoDTOFromAccountID", e);
			throw new ServiceException("�˻���Ϣʱ���Ҵ���");
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
				throw new ServiceException("�˻���Ϣʱ��λ����");
			} catch (FinderException e) {
				LogUtility.log(clazz, LogLevel.ERROR,
						"getOldCustAccountInfoDTOFromAccountID", e);
				throw new ServiceException("�˻���Ϣʱ���Ҵ���");
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
	 * �����˻���Ϣ
	 * 
	 * @param accountDTO
	 * @param addressDTO
	 * @throws ServiceException
	 */
	public void create(AccountDTO accountDTO, AddressDTO addressDTO)
			throws ServiceException {
		try {
			// ȡ�ø��ѷ�ʽ
			MethodOfPaymentHome mopHome = HomeLocater.getMethodOfPaymentHome();
			MethodOfPayment mop = mopHome.findByPrimaryKey(new Integer(
					accountDTO.getMopID()));
			if (mop != null) {
				// ��������и��ѣ�Ҫ��Ϊ������
				if (mop.getPayType().equals(
						CommonConstDefinition.MOPPAYTYPE_PAYBYBANK))
					accountDTO
							.setBankAccountStatus(CommonConstDefinition.ACCOUNTBANKACCOUNT_STATUS_WAIT);
			}

			// ȡ�ÿͻ����ض���
			Customer customerLocal = (Customer) serviceContext
					.get(Service.CUSTOMER);
			if (customerLocal == null) {
				customerLocal = HomeLocater.getCustomerHome().findByPrimaryKey(
						new Integer(accountDTO.getCustomerID()));
			}
			// ȡ�ò����üƷ�������������
			CustAcctCycleCfgHome caccHome = HomeLocater
					.getCustAcctCycleCfgHome();
			Collection caccList = caccHome
					.findBillCycleTypeIDByCustomerTypeAndAccountType(
							customerLocal.getCustomerType(), accountDTO
									.getAccountType());
			Iterator ite4cacc = caccList.iterator();
			if (caccList.isEmpty())
				throw new ServiceException("�Ʒ�����������ͻ����͡��ʻ�����֮��Ĺ�ϵû�ж���");
			int billCycleTypeId = ((CustAcctCycleCfg) ite4cacc.next())
					.getBillingCycleTypeId();
			LogUtility.log(clazz, LogLevel.DEBUG, "����billCycleTypeId����"
					+ billCycleTypeId);
			accountDTO.setBillingCycleTypeID(billCycleTypeId);
			// �����ַ��Ϣ�ǿյĻ����򴴽���ַ��Ϣ,�����˻���Ϣ������
			if (addressDTO != null) {
				AddressHome addressHome = HomeLocater.getAddressHome();
				Address address = addressHome.create(addressDTO);
				accountDTO.setAddressID(address.getAddressID().intValue());
			}
			// �����˻��ĳ�ʼ����Ϣ
			accountDTO
					.setAccountClass(CommonConstDefinition.ACCOUNTCLASS_CUSTOMER);

			accountDTO.setBillAddressFlag(CommonConstDefinition.YESNOFLAG_NO);
			accountDTO.setCurrency(CommonConstDefinition.CURRENCY_RMB);
			accountDTO.setRedirectAccountID(0);
			accountDTO.setStatus(CommonConstDefinition.ACCOUNT_STATUS_NORMAL);
			accountDTO.setCreateTime(TimestampUtility.getCurrentTimestamp());

			AccountHome acctHome = HomeLocater.getAccountHome();
			LogUtility
					.log(clazz, LogLevel.DEBUG, "����accountDTO����" + accountDTO);
			Account account = acctHome.create(accountDTO);
			accountDTO.setAccountID(account.getAccountID().intValue());

			serviceContext.put(Service.ACCOUNT, account);
			serviceContext.put(Service.ACCOUNT_ID, account.getAccountID());

			// ȡ���������ض���
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
			throw new ServiceException("�����˻���Ϣʱ��λ����");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "create", e);
			throw new ServiceException("�����˻���Ϣʱ���Ҵ���");
		} catch (CreateException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "create", e);
			throw new ServiceException("�����˻���Ϣʱ����");
		}
	}

	/**
	 * �����˻���Ϣ
	 */
	public void batchUpdateAccountInfo(int csiID, Collection addressDTOList,
			Collection accountDTOList) throws ServiceException {
		if ((addressDTOList == null || addressDTOList.size() == 0)
				|| (accountDTOList == null || accountDTOList.size() == 0)
				|| (addressDTOList.size() != accountDTOList.size())) {
			throw new ServiceException("��ַ���˻��ļ��ϲ�ƥ��");
		}
		Iterator accountIteror = accountDTOList.iterator();
		Iterator addressIteror = addressDTOList.iterator();
		while (accountIteror.hasNext()) {
			AddressDTO addressDto = (AddressDTO) addressIteror.next();
			AccountDTO accountDto = (AccountDTO) accountIteror.next();
			
			// У�������ʺ�,wp@20080227
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

			// �����˻����˻���ַ��Ϣ
			updateAccountInfo(accountDto, addressDto, csiID);
		}

	}

	/**
	 * �����˻���Ϣ
	 * 
	 * @param accountDTO
	 * @param addressDTO
	 * @PRAM isCreateBakInfo�� �Ƿ񴴽��û��ʻ�������Ϣ
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
			throw new ServiceException("�����˻���Ϣʱ��λ����");
		} catch (CreateException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "create", e);
			throw new ServiceException("�����˻���Ϣʱ����");
		}
	}

	/**
	 * ͨ��Customer�õ��û�����Ϣ����Ҫ���ڹ�����Ǩ��ʱ�޸��û���Ϣ�����ֱ��뱣���ϵĿͻ���Ϣ��T_NEWCUSTACCOUNTINFO
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
	 * ȡ��ҵ���ʻ�
	 * 
	 * @param customerID ��
	 *            �ͻ�ID
	 * @throws ServiceException
	 * @throws HomeFactoryException
	 * @throws FinderException
	 */
	public void close(int customerID) throws ServiceException {
		// �õ�accountList�б�
		try {
			AccountHome accountHome = HomeLocater.getAccountHome();
			Collection accountList = accountHome.findByCustomerId(customerID);
			Account account = null;
			// ����accountList�б�
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
			throw new ServiceException("ȡ���˻���Ϣʱ��λ����");
		} catch (FinderException e) {
			LogUtility.log(clazz, LogLevel.ERROR, "close", e);
			throw new ServiceException("ȡ���˻���Ϣʱ���Ҵ���");
		}
	}
}
