package com.dtv.oss.service.component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.dtv.oss.domain.Contract;
import com.dtv.oss.domain.ContractHome;
import com.dtv.oss.domain.ContractProcessLogHome;
import com.dtv.oss.domain.ContractToPackage;
import com.dtv.oss.domain.ContractToPackageHome;
import com.dtv.oss.dto.ContractDTO;
import com.dtv.oss.dto.ContractProcessLogDTO;
import com.dtv.oss.dto.ContractToPackageDTO;
import com.dtv.oss.dto.CustServiceInteractionDTO;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.ServiceContext;
import com.dtv.oss.service.ServiceException;
import com.dtv.oss.service.factory.HomeFactoryException;
import com.dtv.oss.service.util.CommonConstDefinition;
import com.dtv.oss.service.util.HomeLocater;
import com.dtv.oss.service.util.SystemLogRecorder;
import com.dtv.oss.util.TimestampUtility;

public class ContractService extends AbstractService {

	ServiceContext context=null;
	private static Class clazz=ContractService.class;
	
	public ContractService(ServiceContext s){
		this.context=s;
	}
	
	public void contractCreate(ContractDTO dto,Collection packageCol)throws ServiceException{
		if(dto==null)
			return;
		
		LogUtility.log(clazz,LogLevel.DEBUG, "创建合同开始");
		
		try{
			
			ContractHome conHome=HomeLocater.getContractHome();
			 
			Collection   con= conHome.findByAll();
			Collection primKeyCol= new ArrayList();
			if (con!=null){
				Iterator ite = con.iterator();
				while(ite.hasNext()){
					Contract contract=(Contract)ite.next();
					primKeyCol.add(contract.getContractNo());
				}
				if(primKeyCol.contains(dto.getContractNo()))
				  throw new ServiceException("该合同编号已经存在请重新输入");
			}
 
			String action =CommonConstDefinition.CONTRACT_CREATE;
 
			conHome.create(dto);
			createProcessLog(dto.getContractNo(),CommonConstDefinition.CONTRACTSTATUS_NEW,null);    
			createContractPactage(packageCol);
			 
			
		 	 
		 
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_GROUPCUSTOMER, "创建合同", "新创建的合同NO为:" + dto.getContractNo(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
    		throw new ServiceException("定位出错");
		}
		catch(CreateException e){
			LogUtility.log(clazz,LogLevel.WARN, "创建对象出错");
    		throw new ServiceException("创建对象出错");
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		LogUtility.log(clazz,LogLevel.DEBUG, "创建合同结束");
	}

	/**
	 * 更新合同状态为已开户,关于合同 处理目前只想到这么些,如果有扩充,加在这里
	 * @param contractNO
	 * @throws ServiceException
	 */
	public void processContractWithGroupCustomerOpen(String contractNO,int groupCustmerID,int userCount,String descpription) throws ServiceException{
		ContractHome chome;
		try {
			chome = HomeLocater.getContractHome();
			Contract contract = chome.findByPrimaryKey(contractNO);
			if(!CommonConstDefinition.CONTRACTSTATUS_OPEN.equals(contract.getStatus())){
				contract.setStatus(CommonConstDefinition.CONTRACTSTATUS_OPEN);
			}
			if(contract.getUsedCustomerID()==0){
				contract.setUsedCustomerID(groupCustmerID);
			}
			contract.setUsedCount(contract.getUsedCount()+userCount);
			contract.setDtLastmod(TimestampUtility.getCurrentTimestamp());			
			
			//以下是记录动作，这个要定义公用数据常量，
			createProcessLog(contractNO, CommonConstDefinition.CONTRACT_USED,descpription);
		} catch (FinderException e) {
			e.printStackTrace();
			throw new ServiceException("合同更新失败！无法定位合同!");
		} catch (HomeFactoryException e) {
			e.printStackTrace();
			throw new ServiceException("合同更新失败！无法初始化!");
		}
	}
	 

	/**
	 * @param dto
	 * @param string
	 * @param descpription
	 */
	private void createProcessLog(String contractNo, String action,String descpription) throws ServiceException {
		 ContractProcessLogDTO cpldto = new ContractProcessLogDTO();
		 cpldto.setAction(action);
		 cpldto.setContractNo(contractNo);
		 cpldto.setActionTime(TimestampUtility.getCurrentTimestamp());
		 cpldto.setDescription(descpription);
		 try{
		   ContractProcessLogHome cplHome= HomeLocater.getContractProcessLogHome();
		   cplHome.create(cpldto);
		 }catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
    		throw new ServiceException("定位出错");
		} catch (CreateException e) {
			LogUtility.log(clazz,LogLevel.WARN, "创建对象出错");
    		throw new ServiceException("创建对象出错");
		}
		 
	}

	/**
	 * @param contractNo
	 * @param packageCol
	 */
	private void createContractPactage(Collection packageCol) throws ServiceException{ 
		
	  try{
	  	
		 ContractToPackageHome ctpHome = HomeLocater.getContractToPackageHome();
		 if(packageCol!=null && packageCol.isEmpty()==false){
			Iterator ite = packageCol.iterator();
			 while (ite.hasNext()){    
			  
			 	ContractToPackageDTO packageDto = (ContractToPackageDTO) ite.next();
			 	 
			 	ctpHome.create(packageDto);
			 	
	         }
		 }
		} catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
    		throw new ServiceException("定位出错");
		}
		catch(CreateException e){
			LogUtility.log(clazz,LogLevel.WARN, "创建对象出错");
    		throw new ServiceException("创建对象出错");
		}
		
		LogUtility.log(clazz,LogLevel.DEBUG, "创建合同产品包结束");
	}
	

	public void contractModify(ContractDTO dto,Collection packageCol)throws ServiceException{
		if(dto==null)
			throw new ServiceException("参数错误，合同信息不能为空！");
		
		LogUtility.log(clazz,LogLevel.DEBUG, "修改合同信息开始");
				
		try{
			String description="";
			ContractHome conHome=HomeLocater.getContractHome();
			Contract con=conHome.findByPrimaryKey(dto.getContractNo());
			Timestamp dateFrom = con.getDatefrom();
			
			Timestamp dateTo = con.getDateto();
			Timestamp  normalDate = con.getNormaldate();
			double  rentFeeTotal = con.getRentFeeTotal();
			double oneOffFeeTotal=con.getOneOffFeeTotal();
			double prepayAmount=con.getPrepayAmount();
			int userCount = con.getUserCount();
			String status = con.getStatus();
			String status1=dto.getStatus();
			if("C".equalsIgnoreCase(status))
				  status="作废";
			if("E".equalsIgnoreCase(status))
				  status="生效";
			if("N".equalsIgnoreCase(status))
				  status="创建";
			if("U".equalsIgnoreCase(status))
				  status="开户";
			if("C".equalsIgnoreCase(status1))
				status1="作废";
			if("E".equalsIgnoreCase(status1))
				status1="生效";
			if("N".equalsIgnoreCase(status1))
				status1="创建";
			if("U".equalsIgnoreCase(status1))
				status1="开户";
			if(!dateFrom.equals(dto.getDatefrom()))
				description=description+"有效期起始日期由 " +TimestampUtility.TimestampToDateString(dateFrom)+
						" 修改为:"+TimestampUtility.TimestampToDateString(dto.getDatefrom())+";";
			if(!dateTo.equals(dto.getDateto()))
				description=description+"有效期截至日期由 " +TimestampUtility.TimestampToDateString(dateTo)+
						" 修改为:"+TimestampUtility.TimestampToDateString(dto.getDateto())+";";
			
			if(!normalDate.equals(dto.getNormaldate()))
				description=description+"开户截至日期由 " +TimestampUtility.TimestampToDateString(normalDate)+
						" 修改为:"+TimestampUtility.TimestampToDateString(dto.getNormaldate())+";";
			if(rentFeeTotal!=dto.getRentFeeTotal())
				description=description+"月租费总额由 " +rentFeeTotal+
						" 修改为:"+dto.getRentFeeTotal()+";";
			if(oneOffFeeTotal!=dto.getOneOffFeeTotal())
				description=description+"一次性费总额由 " +oneOffFeeTotal+
						" 修改为:"+dto.getOneOffFeeTotal()+";";
			if(prepayAmount!=dto.getPrepayAmount())
				description=description+"已经预付的金额由 "+prepayAmount+
						" 修改为:"+dto.getPrepayAmount()+";";
			if(userCount!=dto.getUserCount())
				description=description+"用户数由 " +userCount+
						" 修改为:"+dto.getUserCount()+";";
			if(!status.equalsIgnoreCase(dto.getStatus()))
				description=description+"状态由 " +status+
						" 修改为:"+status1+";";
			 
			if(con.ejbUpdate(dto)==-1){
				LogUtility.log(clazz,LogLevel.WARN,"更新合同信息出错。");
	    		throw new ServiceException("更新合同信息出错！");
			}
			 
			ContractToPackageHome ctpHome = HomeLocater.getContractToPackageHome();
		    Collection	ctpCol = ctpHome.findByContractNo(dto.getContractNo());
		    
			if(ctpCol!=null){
				Iterator ite = ctpCol.iterator();
				 while (ite.hasNext()){
				 	ContractToPackage ctp = (ContractToPackage) ite.next();
				 	ctp.remove();
				  
				 	
				 }
			}
			createContractPactage(packageCol);	
			String action =CommonConstDefinition.CONTRACT_MODIFY;
			createProcessLog(dto.getContractNo(),action,description);  
			 
			//创建日志
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_GROUPCUSTOMER, "修改合同", "修改合同信息,修改的合同NO为:" +dto.getContractNo(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "定位出错");
    		throw new ServiceException("定位出错");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "创建对象出错");
    		throw new ServiceException("创建对象出错");
		} catch (EJBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		LogUtility.log(clazz,LogLevel.DEBUG, "更新合同信息结束");
	}
	
	 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
