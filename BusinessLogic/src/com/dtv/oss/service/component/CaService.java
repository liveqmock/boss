package com.dtv.oss.service.component;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.CACondition;
import com.dtv.oss.domain.CAConditionHome;
import com.dtv.oss.domain.CAEventCmdMap;
import com.dtv.oss.domain.CAEventCmdMapHome;
import com.dtv.oss.domain.CAHost;
import com.dtv.oss.domain.CAHostHome;
import com.dtv.oss.domain.CAProduct;
import com.dtv.oss.domain.CAProductDef;
import com.dtv.oss.domain.CAProductDefHome;
import com.dtv.oss.domain.CAProductDefPK;
import com.dtv.oss.domain.CAProductHome;
import com.dtv.oss.domain.CAProductPK;
import com.dtv.oss.dto.CAConditionDTO;
import com.dtv.oss.dto.CAEventCmdMapDTO;
import com.dtv.oss.dto.CAHostDTO;
import com.dtv.oss.dto.CAProductDTO;
import com.dtv.oss.dto.CAProductDefDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
 
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.BusinessUtility;
import com.dtv.oss.service.util.HomeLocater;

public class CaService extends AbstractService {
	private ServiceContext context = null;
	private static Class clazz = CaService.class;

	public CaService() {
		super();
	}

	public CaService(ServiceContext context) {
		super();
		this.context = context;
	}

	public void updateCaHost(CAHostDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "CA�������ø���");
		CAHostHome home = null;
		CAHost host;
		try {
			home = HomeLocater.getCAHostHome();
			host=home.findByPrimaryKey(new Integer(dto.getHostID()));
			dto.setDtLastmod(host.getDtLastmod());
			if(host.ejbUpdate(dto)<0){
				LogUtility.log(this.getClass(), LogLevel.ERROR, "CA�������³���");
				throw new ServiceException("CA�������³���");
			}
		}catch (FinderException e){
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "CA�������²��ҳ���");
			throw new ServiceException("CA�������²��ҳ���");
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "CA�������·������");
			throw new ServiceException("CA�������³���");
		}
	}
	
	public void createCaHost(CAHostDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "CA��������");
		try {
			
			CAHostHome home = HomeLocater.getCAHostHome();
			CAHost host=home.create(dto);
	  	    dto.setHostID(host.getHostID().intValue());
		} catch (CreateException e) {
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "CA������������");
			throw new ServiceException("CA������������");
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "CA���������������");
			throw new ServiceException("CA������������");
		}
	}

	public void updateCaProduct(CAProductDefDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "CA��Ʒ����");
		try {
			 
			CAProductDefHome home=HomeLocater.getCAProductDefHome();
		    System.out.println("***hostId****==="+dto.getHostID()+"***opiid*=="+dto.getOpiID()
		    		+"******caproductId==****"+dto.getCaProductID());
			CAProductDefPK pk=new CAProductDefPK(dto.getHostID(),dto.getOpiID(),dto.getCaProductID());
			CAProductDef productDef=home.findByPrimaryKey(pk); 
			productDef.setProductName(dto.getProductName());
			productDef.setDtCreate(new Timestamp(System.currentTimeMillis()));
			productDef.setDtLastmod(new Timestamp(System.currentTimeMillis())); 
		 
			} catch (HomeFactoryException e) {
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "CA��Ʒ���·������");
			throw new ServiceException("CA��Ʒ���·������");
		}  
		  catch (EJBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		  catch (FinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void deleteCaProduct(CAProductDefDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "CA��Ʒ����");
		try {
			 
			 CAProductDefHome home=HomeLocater.getCAProductDefHome();
			 
			 CAProductDefPK pk=new CAProductDefPK(dto.getHostID(),dto.getOpiID(),dto.getCaProductID());
			 CAProductDef productDef=home.findByPrimaryKey(pk); 
			 productDef.remove();
			} 
		    catch (FinderException e){
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "CA��Ʒ������²��ҳ���");
			throw new ServiceException("CA��Ʒ������ҳ���");
		    }catch (HomeFactoryException e) {
			  e.printStackTrace();
			 LogUtility.log(this.getClass(), LogLevel.ERROR, "CA��Ʒ����ɾ���������");
			throw new ServiceException("CA��Ʒ����ɾ���������");
		}  
		  catch (EJBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   catch (RemoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createCaProduct(CAProductDefDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "CA��Ʒ��������");
		try {
			checkCanDo(dto);
			CAProductDefHome home=HomeLocater.getCAProductDefHome();
			CAProductDef productDef =home.create(dto);
		 } catch (HomeFactoryException e) {
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "CA��Ʒ���������������");
			throw new ServiceException("CA��Ʒ���������������");
		} catch (CreateException e) {
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "CA��Ʒ���������������");
			throw new ServiceException("CA��Ʒ���������������");
		}
	}
	public void updateCaSMSProduct(CAProductDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "CA��Ʒ����");
		try {
			 
			CAProductHome home=HomeLocater.getCAProductHome();
			 
			CAProductPK pk=new CAProductPK(dto.getProductId(),dto.getConditionId());
			CAProduct product=home.findByPrimaryKey(pk); 
			 
			if (product.ejbUpdate(dto) == -1) {
				LogUtility.log(clazz, LogLevel.WARN, "����CA-SMS�ӿڳ���");
				throw new ServiceException("����CA-SMS�ӿڳ���");
			} 
		 
			} catch (HomeFactoryException e) {
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "SMS�ӿڳ���");
			throw new ServiceException("SMS�ӿڳ���");
		}  
		  catch (EJBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		  catch (FinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void deleteCaSMSProduct(CAProductDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "ɾ��CA-SMS�ӿ�");
		try {
			 
			 CAProductHome home=HomeLocater.getCAProductHome();
			 
			 CAProductPK pk=new CAProductPK(dto.getProductId(),dto.getConditionId());
			 CAProduct product=home.findByPrimaryKey(pk); 
			 product.remove();
			} 
		    catch (FinderException e){
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "CA-SMS��Ʒӳ����ҳ���");
			throw new ServiceException("CA-SMS��Ʒӳ����ҳ���");
		    }catch (HomeFactoryException e) {
			  e.printStackTrace();
			 LogUtility.log(this.getClass(), LogLevel.ERROR, "CA-SMS��Ʒӳ����ҳ���");
			throw new ServiceException("CA-SMS��Ʒӳ����ҳ���");
		}  
		  catch (EJBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   catch (RemoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createCaSMSProduct(CAProductDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "SMS-CA��Ʒӳ������");
		try {
			checkCanSMSProductDo(dto);
			CAProductHome home=HomeLocater.getCAProductHome();
			CAProduct product =home.create(dto);
		 } catch (HomeFactoryException e) {
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "SMS-CA��Ʒӳ�������������");
			throw new ServiceException("SMS-CA��Ʒӳ�������������");
		} catch (CreateException e) {
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "SMS-CA��Ʒӳ�������������");
			throw new ServiceException("SMS-CA��Ʒӳ�������������");
		}
	}


	/**
	 * @param dto
	 */
	private void checkCanSMSProductDo(CAProductDTO dto) throws ServiceException {
		int productID = dto.getProductId();
		int conditionID = dto.getConditionId();
		int count = BusinessUtility.getCASMSProductCount(productID,conditionID);
		if (count>0){
			throw new ServiceException("��SMS-CA��Ʒӳ���Ѿ����ڣ�");
		}
		
	}

	/**
	 * @param dto
	 */
	private void checkCanDo(CAProductDefDTO dto) throws ServiceException {
		int hostID=dto.getHostID();
		int opiID = dto.getOpiID();
		String caProductID = dto.getCaProductID();
		int count=BusinessUtility.getCAProductDefCount(hostID,opiID,caProductID);
		if (count>0){
			throw new ServiceException("�ò�Ʒ�����Ѿ����ڣ�");
		}
		
		
	}

	public void updateCaCondition(CAConditionDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "CA��������");
		try {
			CAConditionHome home=HomeLocater.getCAConditionHome();
			CACondition condition=home.findByPrimaryKey(new Integer(dto.getCondID())); 
			dto.setDtLastmod(condition.getDtLastmod());
			if(condition.ejbUpdate(dto)<0){
				LogUtility.log(this.getClass(), LogLevel.ERROR, "CA�������³���");
				throw new ServiceException("CA�������³���");
			}
		} catch (HomeFactoryException e) {
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "CA�������·������");
			throw new ServiceException("CA�������·������");
		} catch (FinderException e) {
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "CA�������¶�λ����");
			throw new ServiceException("CA�������¶�λ����");
		}
	}

	public void createCaCondition(CAConditionDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "CA��������");
		try {
			CAConditionHome home=HomeLocater.getCAConditionHome();
			CACondition condition=null;
			try {
				condition = home.findByPrimaryKey(new Integer(dto.getCondID()));
				LogUtility.log(this.getClass(), LogLevel.ERROR, "CA�����ظ����ӣ�");
				throw new ServiceException("CA�����ظ�����");
			} catch (FinderException e) {
				condition=home.create(dto);
			}
			dto.setCondID(condition.getCondID().intValue());
		} catch (HomeFactoryException e) {
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "CA���������������");
			throw new ServiceException("CA���������������");
		} catch (CreateException e) {
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "CA������������");
			throw new ServiceException("CA������������");
		}
	}

	public void updateCaEventCmdMap(CAEventCmdMapDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "CA�¼�����ӳ�����");
		try {
			CAEventCmdMapHome home=HomeLocater.getCAEventCmdMapHome();
			CAEventCmdMap EventCmdMap=home.findByPrimaryKey(new Integer(dto.getMapID())); 
			dto.setDtLastmod(EventCmdMap.getDtLastmod());
			if(EventCmdMap.ejbUpdate(dto)<0){
				LogUtility.log(this.getClass(), LogLevel.ERROR, "CA�¼�����ӳ����³���");
				throw new ServiceException("CA�¼�����ӳ����³���");
			}
		} catch (HomeFactoryException e) {
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "CA�¼�����ӳ����·������");
			throw new ServiceException("CA�¼�����ӳ����·������");
		} catch (FinderException e) {
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "CA�¼�����ӳ����¶�λ����");
			throw new ServiceException("CA�¼�����ӳ����¶�λ����");
		}
	}

	public void createCaEventCmdMap(CAEventCmdMapDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "CA�¼�����ӳ������");
		try {
			CAEventCmdMapHome home=HomeLocater.getCAEventCmdMapHome();
			CAEventCmdMap EventCmdMap=null;
			try {
				EventCmdMap = home.findByPrimaryKey(new Integer(dto.getMapID()));
				LogUtility.log(this.getClass(), LogLevel.ERROR, "CA�¼�����ӳ���ظ����ӣ�");
				throw new ServiceException("CA�¼�����ӳ���ظ�����");
			} catch (FinderException e) {
				EventCmdMap=home.create(dto);
			}
			dto.setMapID(EventCmdMap.getMapID().intValue());
		} catch (HomeFactoryException e) {
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "CA�¼�����ӳ�������������");
			throw new ServiceException("CA�¼�����ӳ�������������");
		} catch (CreateException e) {
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "CA�¼�����ӳ����������");
			throw new ServiceException("CA�¼�����ӳ����������");
		}
	}
	
}
