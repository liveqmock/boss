package com.dtv.oss.service.component;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.ProductDependency;
import com.dtv.oss.domain.ProductDependencyHome;
import com.dtv.oss.dto.ProductDependencyDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;

public class ProductDependencyService extends AbstractService {

	ServiceContext context=null;
	private static Class clazz=ProductDependencyService.class;
	
	public ProductDependencyService(ServiceContext s){
		this.context=s;
	}
	
	public void productDependencyCreate(ProductDependencyDTO dto)throws ServiceException{
		
		if(dto==null){
			LogUtility.log(clazz,LogLevel.WARN,"传入的参数为空，不能创建产品关系!");
			return;
		}
		
		checkProductExist(dto);
		
		LogUtility.log(clazz,LogLevel.DEBUG, "创建产品关系开始");
		
		ProductDependency p=null;
		
		try{
			ProductDependencyHome pHome=HomeLocater.getProductDependencyHome();
			p=pHome.create(dto);
			
			//context.put(Service.PRODUCT_ID,p.getSeqNo());
			
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "创建产品关系", 
					"创建产品关系,产品ID为：" + p.getProductId() +",创建的产品关系ID为:" + p.getSeqNo().intValue(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("定位出错");
		}
		catch(CreateException e){
			LogUtility.log(clazz,LogLevel.WARN, "创建对象出错");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("创建对象出错");
		}
		
		LogUtility.log(clazz,LogLevel.DEBUG, "创建产品关系信息结束");
		
	}
	
	public void productDependencyModify(ProductDependencyDTO dto)throws ServiceException{
		if(dto==null || dto.getSeqNo()==0){
			LogUtility.log(clazz,LogLevel.WARN,"传入的参数为空，不能修改产品关系!");
			return;
		}
		
		checkProductExist(dto);
		
		LogUtility.log(clazz,LogLevel.DEBUG, "修改产品关系开始 ");
		ProductDependency p=null;
		
		try{
			ProductDependencyHome pHome=HomeLocater.getProductDependencyHome();
			p=pHome.findByPrimaryKey(new Integer(dto.getSeqNo()));
			if(p==null)
				throw new ServiceException("没有该关系的相关信息,不能进行修改");
			
			if(p.ejbUpdate(dto)==-1)
				throw new ServiceException("修改产品关系失败！");
			
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "修改产品关系", 
					"修改产品关系,产品ID为：" + p.getProductId() +",修改的产品关系ID为:" + p.getSeqNo().intValue(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("定位出错");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "创建对象出错");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("创建对象出错");
		}
		
		LogUtility.log(clazz,LogLevel.DEBUG, "修改产品关系结束");
	}
	
	public void productDependencyDelete(String seqNosList)throws ServiceException{
		if(seqNosList==null || "".equals(seqNosList)){
			LogUtility.log(clazz,LogLevel.DEBUG, "删除序号信息为空，不能进行操作！");
			return;
		}
		
		LogUtility.log(clazz,LogLevel.DEBUG, "删除产品关系开始 ");
		
		String seNos[]=seqNosList.split(",");
		
		if(seNos==null || seNos.length==0){
			LogUtility.log(clazz,LogLevel.DEBUG, "没有产品关系序号，不能进行操作！");
			return;
		}
		try{
			ProductDependency p=null;
			ProductDependencyHome pHome=HomeLocater.getProductDependencyHome();
			for(int i=0;i<seNos.length;i++){
				p=pHome.findByPrimaryKey(Integer.valueOf(seNos[i]));
				p.remove();
			}
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_CONFIG, "删除产品关系", 
					"删除产品关系,产品关系序列为：" + seqNosList, 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("定位出错");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "查找出错");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("查找出错");
		}
		catch(RemoveException e){
			LogUtility.log(clazz,LogLevel.WARN, "删除出错");
			LogUtility.log(clazz,LogLevel.WARN, e);
    		throw new ServiceException("删除出错");
		}		
		LogUtility.log(clazz,LogLevel.DEBUG, "删除产品关系结束");
	}
	
	
	private void checkProductExist(ProductDependencyDTO dto) throws ServiceException{
 
		
		String productIdStr=dto.getReferProductIDList();
		String[] productIdArr = productIdStr.split(",");
		List checkList = new ArrayList();
		for (int i=0 ; i<productIdArr.length;i++){
			if (productIdArr[i]!=null)
				checkList.add(productIdArr[i]);
		}
		if(checkList.contains(String.valueOf(dto.getProductId())))
			throw new ServiceException("产品"+ dto.getProductId() + "重复");
				
	 }
	 
	 
	 
}
