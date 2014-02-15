package com.dtv.oss.service.command.config;

import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.Service;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.command.Command;
import com.dtv.oss.service.command.CommandException;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.CommandResponseImp;
import com.dtv.oss.service.component.ConfigPackageLineService;
import com.dtv.oss.service.component.ConfigPackageSegmentService;
import com.dtv.oss.service.component.ConfigServiceToProductService;
import com.dtv.oss.service.component.ProductDependencyService;
import com.dtv.oss.service.component.ProductPackageService;
import com.dtv.oss.service.component.ProductPropertyService;
import com.dtv.oss.service.component.ProductService;
import com.dtv.oss.service.ejbevent.EJBEvent;
import com.dtv.oss.service.ejbevent.config.ConfigProductEJBEvent;

public class ConfigProductCommand extends Command {

	 private static final Class clazz = ConfigProductCommand.class;
		private int operatorID = 0;
		private String machineName = "";	
		CommandResponseImp response = null;
		private ServiceContext context;

		public CommandResponse execute(EJBEvent ev) throws CommandException {
			response = new CommandResponseImp(null);
			ConfigProductEJBEvent inEvent = (ConfigProductEJBEvent) ev;
			operatorID = inEvent.getOperatorID();
			machineName = ev.getRemoteHostAddress();
			LogUtility.log(clazz, LogLevel.DEBUG, "Enter execute() now.");
			 
			try {
				switch (inEvent.getActionType()) {
				  					
					//产品操作
					case EJBEvent.PRODUCT_CREATE:
						createProduct(inEvent);
						break;
					case EJBEvent.PRODUCT_MODIFY:
						modifyProduct(inEvent);
						break;
					case EJBEvent.PRODUCT_DELETE:
						deleteProduct(inEvent);
						break;
					//产品关系操作
					case EJBEvent.PRODUCT_DEPENDENCY_CREATE:
						createProductDependency(inEvent);
						break;
					case EJBEvent.PRODUCT_DEPENDENCY_MODIFY:
						modifyProductDependency(inEvent);
						break;
					case EJBEvent.PRODUCT_DEPENDENCY_DELETE:
						deleteProductDependency(inEvent);
						break;
				 
					case EJBEvent.PRODUCT_PACKAGE_CREATE:
						createProductPackage(inEvent);
						break;
					case EJBEvent.PRODUCT_PACKAGE_MODIFY:
						modifyProductPackage(inEvent);
						break;
						//config package line
					case EJBEvent.DELETE_PRODUCT:
						deletePackageLine(inEvent);
						break;
					case EJBEvent.ADD_PRODUCT:
						createPackageLine(inEvent);
						break;
							
						//config package market segment
					case EJBEvent.CONFIG_PACKAGE_MARKET_SEGMENT:
						createPackageSegment(inEvent);
						break;
						//属性操作增删改,
					case EJBEvent.PRODUCT_PROPERTY_CREATE:
						createProductProperty(inEvent);
						break;
					case EJBEvent.PRODUCT_PROPERTY_MODFIY:
						updateProductProperty(inEvent);
						break;
					case EJBEvent.PRODUCT_PROPERTY_DELETE:
						deleteProductProperty(inEvent);
						break;
					case EJBEvent.ADD_SERVICE:
						addProductService(inEvent);
						break;
					case EJBEvent.DEL_SERVICE:
						deleteProductService(inEvent);
						break;	

                    default :
                    	break;
				}
			}catch(ServiceException ce){
		        LogUtility.log(clazz,LogLevel.ERROR,this,ce);
		        throw new CommandException(ce.getMessage());
		    }catch(Throwable unkown) {
			    LogUtility.log(clazz,LogLevel.FATAL, this, unkown);
			    throw new CommandException("未知错误。");
			}
			return response;
		}
		
	

		private void deleteProduct(ConfigProductEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			ProductService pService=new ProductService(context);
			pService.productBaseInfoDelete(inEvent.getProductDTO());
		}

		private void createProduct(ConfigProductEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			ProductService pService=new ProductService(context);
			pService.productBaseInfoCreate(inEvent.getProductDTO());
			
			this.response.setPayload(context.get(Service.PRODUCT_ID));
		}
		
		private void modifyProduct(ConfigProductEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			ProductService pService=new ProductService(context);
			pService.productBaseInfoModify(inEvent.getProductDTO());
		}
		
		private void createProductDependency(ConfigProductEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			ProductDependencyService pService=new ProductDependencyService(context);
			pService.productDependencyCreate(inEvent.getProductDependencyDTO());
		}
		
		private void modifyProductDependency(ConfigProductEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			ProductDependencyService pService=new ProductDependencyService(context);
			pService.productDependencyModify(inEvent.getProductDependencyDTO());
		}
		
		private void deleteProductDependency(ConfigProductEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			ProductDependencyService pService=new ProductDependencyService(context);
			pService.productDependencyDelete(inEvent.getProductDependencySeqNos());
		}
		
		 
		private void createProductPackage(ConfigProductEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			ProductPackageService pService=new ProductPackageService(context);
			pService.productPackageCreate(inEvent);
			Integer packageID = (Integer)context.get(Service.PRODUCT_PACKAGE_ID);
			ConfigPackageSegmentService p1Service=new ConfigPackageSegmentService(context);
			inEvent.setPackageID(packageID.intValue());
			p1Service.configPackageSegment(inEvent);
			
			this.response.setPayload(context.get(Service.PRODUCT_PACKAGE_ID));
		}
		private void modifyProductPackage(ConfigProductEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			ProductPackageService pService=new ProductPackageService(context);
			pService.productPackageModify(inEvent);
			ConfigPackageSegmentService p1Service=new ConfigPackageSegmentService(context);
			p1Service.configPackageSegment(inEvent);
			
		}
		private void createPackageLine(ConfigProductEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			ConfigPackageLineService pService=new ConfigPackageLineService(context);
			pService.addProductForPackage(inEvent.getPackageLineCol(),inEvent.getProductIDstr());
		}
		private void deletePackageLine(ConfigProductEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			ConfigPackageLineService pService=new ConfigPackageLineService(context);
			pService.deleteProductForPackage(inEvent.getPackageLineCol());
		}
		private void createPackageSegment(ConfigProductEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			ConfigPackageSegmentService pService=new ConfigPackageSegmentService(context);
			pService.configPackageSegment(inEvent);
		}
		private void createProductProperty(ConfigProductEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			ProductPropertyService pService=new ProductPropertyService(context);
			pService.productPropertyCreate(inEvent.getProductPropertyDTO());
		}
		private void updateProductProperty(ConfigProductEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			ProductPropertyService pService=new ProductPropertyService(context);
			pService.productPropertyModify(inEvent.getProductPropertyDTO());
		}
		private void deleteProductProperty(ConfigProductEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			ProductPropertyService pService=new ProductPropertyService(context);
			pService.productPropertyDelete(inEvent.getProductPropertyDTO(),inEvent.getProductPropertyNames());
		}
		private void addProductService(ConfigProductEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			ConfigServiceToProductService pService=new ConfigServiceToProductService(context);
			pService.addService(inEvent.getPackageLineCol());
		}
		private void deleteProductService(ConfigProductEJBEvent inEvent) throws ServiceException{
			ServiceContext context=initServiceContext(inEvent);
			ConfigServiceToProductService pService=new ConfigServiceToProductService(context);
			pService.deleteService(inEvent.getPackageLineCol());
		}
		private ServiceContext initServiceContext(ConfigProductEJBEvent event){
			ServiceContext serviceContext=new ServiceContext();
		    serviceContext.put(Service.OPERATIOR_ID,new Integer(operatorID));
		    serviceContext.put(Service.REMOTE_HOST_ADDRESS,event.getRemoteHostAddress());
		    return serviceContext;
		}
			
}
