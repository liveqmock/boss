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
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "CA主机配置更新");
		CAHostHome home = null;
		CAHost host;
		try {
			home = HomeLocater.getCAHostHome();
			host=home.findByPrimaryKey(new Integer(dto.getHostID()));
			dto.setDtLastmod(host.getDtLastmod());
			if(host.ejbUpdate(dto)<0){
				LogUtility.log(this.getClass(), LogLevel.ERROR, "CA主机更新出错！");
				throw new ServiceException("CA主机更新出错！");
			}
		}catch (FinderException e){
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "CA主机更新查找出错！");
			throw new ServiceException("CA主机更新查找出错！");
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "CA主机更新服务出错！");
			throw new ServiceException("CA主机更新出错！");
		}
	}
	
	public void createCaHost(CAHostDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "CA主机新增");
		try {
			
			CAHostHome home = HomeLocater.getCAHostHome();
			CAHost host=home.create(dto);
	  	    dto.setHostID(host.getHostID().intValue());
		} catch (CreateException e) {
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "CA主机创建出错！");
			throw new ServiceException("CA主机创建出错！");
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "CA主机创建服务出错！");
			throw new ServiceException("CA主机创建出错！");
		}
	}

	public void updateCaProduct(CAProductDefDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "CA产品更新");
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
			LogUtility.log(this.getClass(), LogLevel.ERROR, "CA产品更新服务出错！");
			throw new ServiceException("CA产品更新服务出错！");
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
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "CA产品更新");
		try {
			 
			 CAProductDefHome home=HomeLocater.getCAProductDefHome();
			 
			 CAProductDefPK pk=new CAProductDefPK(dto.getHostID(),dto.getOpiID(),dto.getCaProductID());
			 CAProductDef productDef=home.findByPrimaryKey(pk); 
			 productDef.remove();
			} 
		    catch (FinderException e){
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "CA产品定义更新查找出错！");
			throw new ServiceException("CA产品定义查找出错！");
		    }catch (HomeFactoryException e) {
			  e.printStackTrace();
			 LogUtility.log(this.getClass(), LogLevel.ERROR, "CA产品定义删除服务出错！");
			throw new ServiceException("CA产品定义删除服务出错！");
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
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "CA产品定义新增");
		try {
			checkCanDo(dto);
			CAProductDefHome home=HomeLocater.getCAProductDefHome();
			CAProductDef productDef =home.create(dto);
		 } catch (HomeFactoryException e) {
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "CA产品定义新增服务出错！");
			throw new ServiceException("CA产品定义新增服务出错！");
		} catch (CreateException e) {
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "CA产品定义新增服务出错！");
			throw new ServiceException("CA产品定义新增服务出错！");
		}
	}
	public void updateCaSMSProduct(CAProductDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "CA产品更新");
		try {
			 
			CAProductHome home=HomeLocater.getCAProductHome();
			 
			CAProductPK pk=new CAProductPK(dto.getProductId(),dto.getConditionId());
			CAProduct product=home.findByPrimaryKey(pk); 
			 
			if (product.ejbUpdate(dto) == -1) {
				LogUtility.log(clazz, LogLevel.WARN, "更新CA-SMS接口出错。");
				throw new ServiceException("更新CA-SMS接口出错！");
			} 
		 
			} catch (HomeFactoryException e) {
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "SMS接口出错！");
			throw new ServiceException("SMS接口出错！");
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
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "删除CA-SMS接口");
		try {
			 
			 CAProductHome home=HomeLocater.getCAProductHome();
			 
			 CAProductPK pk=new CAProductPK(dto.getProductId(),dto.getConditionId());
			 CAProduct product=home.findByPrimaryKey(pk); 
			 product.remove();
			} 
		    catch (FinderException e){
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "CA-SMS产品映射查找出错！");
			throw new ServiceException("CA-SMS产品映射查找出错！");
		    }catch (HomeFactoryException e) {
			  e.printStackTrace();
			 LogUtility.log(this.getClass(), LogLevel.ERROR, "CA-SMS产品映射查找出错！");
			throw new ServiceException("CA-SMS产品映射查找出错！");
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
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "SMS-CA产品映射新增");
		try {
			checkCanSMSProductDo(dto);
			CAProductHome home=HomeLocater.getCAProductHome();
			CAProduct product =home.create(dto);
		 } catch (HomeFactoryException e) {
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "SMS-CA产品映射新增服务出错！");
			throw new ServiceException("SMS-CA产品映射新增服务出错！");
		} catch (CreateException e) {
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "SMS-CA产品映射新增服务出错！");
			throw new ServiceException("SMS-CA产品映射新增服务出错！");
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
			throw new ServiceException("该SMS-CA产品映射已经存在！");
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
			throw new ServiceException("该产品定义已经存在！");
		}
		
		
	}

	public void updateCaCondition(CAConditionDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "CA条件更新");
		try {
			CAConditionHome home=HomeLocater.getCAConditionHome();
			CACondition condition=home.findByPrimaryKey(new Integer(dto.getCondID())); 
			dto.setDtLastmod(condition.getDtLastmod());
			if(condition.ejbUpdate(dto)<0){
				LogUtility.log(this.getClass(), LogLevel.ERROR, "CA条件更新出错！");
				throw new ServiceException("CA条件更新出错！");
			}
		} catch (HomeFactoryException e) {
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "CA条件更新服务出错！");
			throw new ServiceException("CA条件更新服务出错！");
		} catch (FinderException e) {
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "CA条件更新定位出错！");
			throw new ServiceException("CA条件更新定位出错！");
		}
	}

	public void createCaCondition(CAConditionDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "CA条件新增");
		try {
			CAConditionHome home=HomeLocater.getCAConditionHome();
			CACondition condition=null;
			try {
				condition = home.findByPrimaryKey(new Integer(dto.getCondID()));
				LogUtility.log(this.getClass(), LogLevel.ERROR, "CA条件重复增加！");
				throw new ServiceException("CA条件重复增加");
			} catch (FinderException e) {
				condition=home.create(dto);
			}
			dto.setCondID(condition.getCondID().intValue());
		} catch (HomeFactoryException e) {
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "CA条件新增服务出错！");
			throw new ServiceException("CA条件新增服务出错！");
		} catch (CreateException e) {
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "CA条件新增出错！");
			throw new ServiceException("CA条件新增出错！");
		}
	}

	public void updateCaEventCmdMap(CAEventCmdMapDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "CA事件命令映射更新");
		try {
			CAEventCmdMapHome home=HomeLocater.getCAEventCmdMapHome();
			CAEventCmdMap EventCmdMap=home.findByPrimaryKey(new Integer(dto.getMapID())); 
			dto.setDtLastmod(EventCmdMap.getDtLastmod());
			if(EventCmdMap.ejbUpdate(dto)<0){
				LogUtility.log(this.getClass(), LogLevel.ERROR, "CA事件命令映射更新出错！");
				throw new ServiceException("CA事件命令映射更新出错！");
			}
		} catch (HomeFactoryException e) {
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "CA事件命令映射更新服务出错！");
			throw new ServiceException("CA事件命令映射更新服务出错！");
		} catch (FinderException e) {
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "CA事件命令映射更新定位出错！");
			throw new ServiceException("CA事件命令映射更新定位出错！");
		}
	}

	public void createCaEventCmdMap(CAEventCmdMapDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "CA事件命令映射新增");
		try {
			CAEventCmdMapHome home=HomeLocater.getCAEventCmdMapHome();
			CAEventCmdMap EventCmdMap=null;
			try {
				EventCmdMap = home.findByPrimaryKey(new Integer(dto.getMapID()));
				LogUtility.log(this.getClass(), LogLevel.ERROR, "CA事件命令映射重复增加！");
				throw new ServiceException("CA事件命令映射重复增加");
			} catch (FinderException e) {
				EventCmdMap=home.create(dto);
			}
			dto.setMapID(EventCmdMap.getMapID().intValue());
		} catch (HomeFactoryException e) {
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "CA事件命令映射新增服务出错！");
			throw new ServiceException("CA事件命令映射新增服务出错！");
		} catch (CreateException e) {
			e.printStackTrace();
			LogUtility.log(this.getClass(), LogLevel.ERROR, "CA事件命令映射新增出错！");
			throw new ServiceException("CA事件命令映射新增出错！");
		}
	}
	
}
