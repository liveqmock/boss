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
		
		LogUtility.log(clazz,LogLevel.DEBUG, "������ͬ��ʼ");
		
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
				  throw new ServiceException("�ú�ͬ����Ѿ���������������");
			}
 
			String action =CommonConstDefinition.CONTRACT_CREATE;
 
			conHome.create(dto);
			createProcessLog(dto.getContractNo(),CommonConstDefinition.CONTRACTSTATUS_NEW,null);    
			createContractPactage(packageCol);
			 
			
		 	 
		 
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_GROUPCUSTOMER, "������ͬ", "�´����ĺ�ͬNOΪ:" + dto.getContractNo(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
    		throw new ServiceException("��λ����");
		}
		catch(CreateException e){
			LogUtility.log(clazz,LogLevel.WARN, "�����������");
    		throw new ServiceException("�����������");
		} catch (FinderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		LogUtility.log(clazz,LogLevel.DEBUG, "������ͬ����");
	}

	/**
	 * ���º�ͬ״̬Ϊ�ѿ���,���ں�ͬ ����Ŀǰֻ�뵽��ôЩ,���������,��������
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
			
			//�����Ǽ�¼���������Ҫ���幫�����ݳ�����
			createProcessLog(contractNO, CommonConstDefinition.CONTRACT_USED,descpription);
		} catch (FinderException e) {
			e.printStackTrace();
			throw new ServiceException("��ͬ����ʧ�ܣ��޷���λ��ͬ!");
		} catch (HomeFactoryException e) {
			e.printStackTrace();
			throw new ServiceException("��ͬ����ʧ�ܣ��޷���ʼ��!");
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
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
    		throw new ServiceException("��λ����");
		} catch (CreateException e) {
			LogUtility.log(clazz,LogLevel.WARN, "�����������");
    		throw new ServiceException("�����������");
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
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
    		throw new ServiceException("��λ����");
		}
		catch(CreateException e){
			LogUtility.log(clazz,LogLevel.WARN, "�����������");
    		throw new ServiceException("�����������");
		}
		
		LogUtility.log(clazz,LogLevel.DEBUG, "������ͬ��Ʒ������");
	}
	

	public void contractModify(ContractDTO dto,Collection packageCol)throws ServiceException{
		if(dto==null)
			throw new ServiceException("�������󣬺�ͬ��Ϣ����Ϊ�գ�");
		
		LogUtility.log(clazz,LogLevel.DEBUG, "�޸ĺ�ͬ��Ϣ��ʼ");
				
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
				  status="����";
			if("E".equalsIgnoreCase(status))
				  status="��Ч";
			if("N".equalsIgnoreCase(status))
				  status="����";
			if("U".equalsIgnoreCase(status))
				  status="����";
			if("C".equalsIgnoreCase(status1))
				status1="����";
			if("E".equalsIgnoreCase(status1))
				status1="��Ч";
			if("N".equalsIgnoreCase(status1))
				status1="����";
			if("U".equalsIgnoreCase(status1))
				status1="����";
			if(!dateFrom.equals(dto.getDatefrom()))
				description=description+"��Ч����ʼ������ " +TimestampUtility.TimestampToDateString(dateFrom)+
						" �޸�Ϊ:"+TimestampUtility.TimestampToDateString(dto.getDatefrom())+";";
			if(!dateTo.equals(dto.getDateto()))
				description=description+"��Ч�ڽ��������� " +TimestampUtility.TimestampToDateString(dateTo)+
						" �޸�Ϊ:"+TimestampUtility.TimestampToDateString(dto.getDateto())+";";
			
			if(!normalDate.equals(dto.getNormaldate()))
				description=description+"�������������� " +TimestampUtility.TimestampToDateString(normalDate)+
						" �޸�Ϊ:"+TimestampUtility.TimestampToDateString(dto.getNormaldate())+";";
			if(rentFeeTotal!=dto.getRentFeeTotal())
				description=description+"������ܶ��� " +rentFeeTotal+
						" �޸�Ϊ:"+dto.getRentFeeTotal()+";";
			if(oneOffFeeTotal!=dto.getOneOffFeeTotal())
				description=description+"һ���Է��ܶ��� " +oneOffFeeTotal+
						" �޸�Ϊ:"+dto.getOneOffFeeTotal()+";";
			if(prepayAmount!=dto.getPrepayAmount())
				description=description+"�Ѿ�Ԥ���Ľ���� "+prepayAmount+
						" �޸�Ϊ:"+dto.getPrepayAmount()+";";
			if(userCount!=dto.getUserCount())
				description=description+"�û����� " +userCount+
						" �޸�Ϊ:"+dto.getUserCount()+";";
			if(!status.equalsIgnoreCase(dto.getStatus()))
				description=description+"״̬�� " +status+
						" �޸�Ϊ:"+status1+";";
			 
			if(con.ejbUpdate(dto)==-1){
				LogUtility.log(clazz,LogLevel.WARN,"���º�ͬ��Ϣ����");
	    		throw new ServiceException("���º�ͬ��Ϣ����");
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
			 
			//������־
			SystemLogRecorder.createSystemLog(PublicService.getRemoteHostAddress(context), 
	    			PublicService.getCurrentOperatorID(context), 0, 
					SystemLogRecorder.LOGMODULE_GROUPCUSTOMER, "�޸ĺ�ͬ", "�޸ĺ�ͬ��Ϣ,�޸ĵĺ�ͬNOΪ:" +dto.getContractNo(), 
					SystemLogRecorder.LOGCLASS_NORMAL, SystemLogRecorder.LOGTYPE_APP);
		}
		catch(HomeFactoryException e){
			LogUtility.log(clazz,LogLevel.WARN, "��λ����");
    		throw new ServiceException("��λ����");
		}
		catch(FinderException e){
			LogUtility.log(clazz,LogLevel.WARN, "�����������");
    		throw new ServiceException("�����������");
		} catch (EJBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		LogUtility.log(clazz,LogLevel.DEBUG, "���º�ͬ��Ϣ����");
	}
	
	 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
