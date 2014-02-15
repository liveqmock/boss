package com.dtv.oss.service.component;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import com.dtv.oss.domain.LogisticsSetting;
import com.dtv.oss.domain.LogisticsSettingHome;
import com.dtv.oss.dto.LogisticsSettingDTO;
import com.dtv.oss.dto.ProductDependencyDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;

public class LogisticsSettingService extends AbstractService {
	private ServiceContext context = null;

	public LogisticsSettingService() {
		super();
	}

	public LogisticsSettingService(ServiceContext context) {
		super();
		this.context = context;
	}

	public void updateLogisticsSetting(LogisticsSettingDTO dto) throws ServiceException {
		LogUtility.log(this.getClass(), LogLevel.DEBUG, "设备管理配置更新");
		LogisticsSettingHome home = null;
		LogisticsSetting LogisticsSetting;
		try {
			checkProductMuti(dto);
			home = HomeLocater.getLogisticsSettingHome();
			LogisticsSetting=home.findByPrimaryKey(new Integer(dto.getSeqNo()));
			LogisticsSetting.setInAndOut(dto.getInAndOut());
			LogisticsSetting.setMatchAndPreauth(dto.getMatchAndPreauth());
			LogisticsSetting.setOutOrgnization(dto.getOutOrgnization());
			LogisticsSetting.setPreauthProductid1(dto.getPreauthProductid1());
			LogisticsSetting.setPreauthProductid2(dto.getPreauthProductid2());
			LogisticsSetting.setPreauthProductid3(dto.getPreauthProductid3());
			LogisticsSetting.setPreauthProductid4(dto.getPreauthProductid4());
			LogisticsSetting.setPreauthProductid5(dto.getPreauthProductid5());
			LogisticsSetting.setStatus(dto.getStatus());
			LogisticsSetting.setDtLastmod(dto.getDtLastmod());
		}catch (FinderException e){
			try {
				LogisticsSetting=home.create(dto);
			} catch (CreateException e1) {
				LogUtility.log(this.getClass(), LogLevel.ERROR, "设备管理配置更新创建出错！");
				throw new ServiceException("设备管理配置创建出错！");
			}
		} catch (HomeFactoryException e1) {
			LogUtility.log(this.getClass(), LogLevel.ERROR, "设备管理配置更新服务出错！");
			throw new ServiceException("设备管理配置更新出错！");
		}
	}
	private void checkProductMuti(LogisticsSettingDTO dto) throws ServiceException{
		
		String productIdStr=";";
		 
		int referProduct1 = dto.getPreauthProductid1();
		int referProduct2 = dto.getPreauthProductid2();
		int referProduct3 = dto.getPreauthProductid3();
		int referProduct4 = dto.getPreauthProductid4();
		int referProduct5 = dto.getPreauthProductid5();
		if(referProduct1>0)
			productIdStr=productIdStr+referProduct1+";";
		if(referProduct2>0)
			productIdStr=productIdStr+referProduct2+";";
		if(referProduct3>0)
			productIdStr=productIdStr+referProduct3+";";
		if(referProduct4>0)
			productIdStr=productIdStr+referProduct4+";";
		if(referProduct5>0)
			productIdStr=productIdStr+referProduct5+";";
		 
	    if(referProduct1>0 && productIdStr.indexOf(String.valueOf(dto.getPreauthProductid1())+";")!=productIdStr.lastIndexOf(String.valueOf(dto.getPreauthProductid1())+";"))
	    	throw new ServiceException("产品"+ dto.getPreauthProductid1() + "重复");
	    if(referProduct2>0 && productIdStr.indexOf(String.valueOf(dto.getPreauthProductid2())+";")!=productIdStr.lastIndexOf(String.valueOf(dto.getPreauthProductid2())+";"))
	    	throw new ServiceException("产品"+ dto.getPreauthProductid2() + "重复");
	    if(referProduct3>0 && productIdStr.indexOf(String.valueOf(dto.getPreauthProductid3())+";")!=productIdStr.lastIndexOf(String.valueOf(dto.getPreauthProductid3())+";"))
	    	throw new ServiceException("产品"+ dto.getPreauthProductid3() + "重复");
	    if(referProduct4>0 && productIdStr.indexOf(String.valueOf(dto.getPreauthProductid4())+";")!=productIdStr.lastIndexOf(String.valueOf(dto.getPreauthProductid4())+";"))
	    	throw new ServiceException("产品"+ dto.getPreauthProductid4() + "重复");
	    if(referProduct5>0 && productIdStr.indexOf(String.valueOf(dto.getPreauthProductid5())+";")!=productIdStr.lastIndexOf(String.valueOf(dto.getPreauthProductid5())+";"))
	    	throw new ServiceException("产品"+ dto.getPreauthProductid5() + "重复");
	}
	
}
