package com.dtv.oss.web.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;

import com.dtv.oss.dto.AddressDTO;
import com.dtv.oss.dto.ConstructionBatchItemDTO;
import com.dtv.oss.dto.ExportCustomerDetailDTO;
import com.dtv.oss.dto.NewCustomerInfoDTO;
import com.dtv.oss.dto.TerminalDeviceDTO;
import com.dtv.oss.dto.VodstbDeviceImportDetailDTO;
import com.dtv.oss.dto.custom.GehuaCustInfoForBatchMessageDTO;
import com.dtv.oss.dto.wrap.groupcustomer.GroupCustomerWrap;
import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.util.DBUtil;
import com.dtv.oss.util.Postern;
import com.dtv.oss.util.TimestampUtility;
import com.dtv.oss.web.exception.WebActionException;
import com.dtv.oss.dto.FoundCustomerDetaiDTO;

public class FileUtility {

	public static java.util.Collection parseCSVFileWithGroupCustomer(
			String fileName) throws WebActionException {
		ArrayList result = new ArrayList();
		FileReader fileReader =null;
		BufferedReader bufferedReader = null;
		
		try {
			File file = new File(fileName);
			if(!(file.getName().endsWith("csv")||file.getName().endsWith("CSV"))){
				throw new WebActionException("�Ƿ����ļ�!"+file.getName());
			}
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);

			Map genderMap = Postern
					.getHashKeyValueByName("SET_C_CUSTOMERGENDER");
			Map cardType = Postern
					.getHashKeyValueByName("SET_C_CUSTOMERCARDTYPE");
			Map nationality = Postern
					.getHashKeyValueByName("SET_C_NATIONALITY");
			Map district = Postern.getAllDistrictName();
			Map service = Postern.getAllService();

			String line = "";
			int number = 0;
			while ((line = bufferedReader.readLine()) != null) {
				number++;

				String[] splitLine = line.split(",");

				if (number < 4||splitLine.length<1)
					continue;

				GroupCustomerWrap wrap = new GroupCustomerWrap();
				
				for (int i = 0; i < splitLine.length; i++) {
					NewCustomerInfoDTO custDTO = wrap.getNewCustomerInfoDTO();
					AddressDTO addrDTO = wrap.getAddressDTO();

					String curString = splitLine[i];

//					LogUtility.log(FileUtility.class, LogLevel.DEBUG, "\nucrrent index:"+i+"  current String:"+curString);
					if (curString.equalsIgnoreCase("")) {
						continue;
					}
					String temp = "";
					boolean isValid=true;
					try {
						switch (i) {
						case 0:
							wrap.setNo(curString);
							break;
						case 1:// name
							custDTO.setName(curString);
							break;
						case 2:// GENDER
							temp = getKeyWithCommonSettingData(genderMap,
									curString);
							if (temp.equalsIgnoreCase("")) {
								isValid=false;
								break;
							}
							custDTO.setGender(temp);
							break;
						case 3:// BIRTHDAY
							custDTO.setBirthday(TimestampUtility
									.StringToTimestamp(curString));
							break;
						case 4:// NATIONALITY
							temp = getKeyWithCommonSettingData(nationality,
									curString);
							if (temp.equalsIgnoreCase("")) {
								isValid=false;
								break;
							}
							custDTO.setNationality(temp);
							break;
						case 5:// CARDTYPE
							temp = getKeyWithCommonSettingData(cardType,
									curString);
							if (temp.equalsIgnoreCase("")) {
								isValid=false;
								break;
							}
							custDTO.setCardType(temp);
							break;
						case 6:// CARDID
							custDTO.setCardID(curString);
							break;
						case 7:// SOCIALSECCARDID
							custDTO.setSocialSecCardID(curString);
							break;
						case 8:// TELEPHONE
							custDTO.setTelephone(curString);
							break;
						case 9:// TELEPHONEMOBILE
							custDTO.setMobileNumber(curString);
							break;
						case 10:// FAX
							custDTO.setFaxNumber(curString);
							break;
						case 11:// EMAIL
							custDTO.setEmail(curString);
							break;
						case 12:// PostCode
							addrDTO.setPostcode(curString);
							break;
						case 13:// District
							temp = getKeyWithCommonSettingData(district,
									curString);
							if (temp.equalsIgnoreCase("")) {
								isValid=false;
								break;
							}
							addrDTO.setDistrictID(Integer.parseInt(temp));
							break;
						case 14:// DetailAddress
							addrDTO.setDetailAddress(curString);
							break;
						case 15:// ServiceID
							temp = getKeyWithCommonSettingData(service,
									curString);
							if (temp.equalsIgnoreCase("")) {
								isValid=false;
								break;
							}
							wrap.setServiceID(Integer.parseInt(temp));
							break;
						case 16:// SCSerialNo
							wrap.setSCSerialNo(curString);
							break;
						case 17:// STBSerialNo
							wrap.setSTBSerialNo(curString);
							break;
						case 18:// IPPSerialNo
							wrap.setIPPSerialNo(curString);
							break;
						case 19:// ServiceCode
							wrap.setServiceCode(curString);
							break;
						default:
							break;
						}
						wrap.setValid(isValid);
					} catch (Exception e) {
						LogUtility.log(FileUtility.class, LogLevel.ERROR, e.getMessage());
					}					
				}

				result.add(wrap);

			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new WebActionException("�ļ���ʽ�쳣!!");
		}finally{
			try {
				bufferedReader.close();
				fileReader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * ����map,��һ���ַ���,���ַ���λ��MAP��KEY���value��,������Ч��key
	 * @param map
	 * @param para
	 * @return
	 */
	private static String getKeyWithCommonSettingData(Map map, String para) {
		String result="";
		
		if (map.containsValue(para)) {
			java.util.Iterator it=map.keySet().iterator();
			while(it.hasNext()){
				String key=(String) it.next();
				String value=(String) map.get(key);
				if(para.equalsIgnoreCase(value)){
					result=key;
					break;
				}
			}
		} else if (map.containsKey(para)) {
			result= para;
		}

		return result;
	}
	
	
	/**
	 * ���ڰ��ϴ����ļ�����Ϊ���ϣ��ļ�Ŀǰ����ģ���û����룬�ͻ�������
	 * @param fileName
	 * @return
	 * @throws WebActionException
	 */
	public static java.util.ArrayList parseCSVFileWithConstruction(
			String fileName) throws WebActionException {
		ArrayList result = new ArrayList();
		FileReader fileReader =null;
		BufferedReader bufferedReader = null;
		try {
			File file = new File(fileName);
			if (!(file.getName().toLowerCase().endsWith("csv"))) {
				throw new WebActionException("�Ƿ����ļ�!" + file.getName());
			}
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);

			String line = "";
			int number = 0;
			while ((line = bufferedReader.readLine()) != null) {
				number++;
				line=line.trim();
				String[] splitLine = line.split(",");

				if (number < 4 || splitLine.length < 1||"".equals(line))
					continue;

//				LogUtility.log(FileUtility.class, LogLevel.DEBUG, "line:" +
//						 line);

				ConstructionBatchItemDTO dto = new ConstructionBatchItemDTO();
				for (int i = 0; i < splitLine.length; i++) {
					String curString = splitLine[i];

					// LogUtility.log(FileUtility.class, LogLevel.DEBUG,
					// "\nucrrent index:"+i+" current String:"+curString);
					if ("".equals(curString)) {
						continue;
					}
					switch (i) {
					case 0:
						dto.setDetailAddress(curString);
						break;
					case 1:// 
						dto.setPortNumber(WebUtil.StringToInt(curString));
						break;
					case 2://  
						dto.setComments(curString);
						break;
					case 3://  
						dto.setCatvId(curString);
						break;
					default:
						break;
					}
				}
				result.add(dto);
				dto.setItemNo(result.size());
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new WebActionException("�ļ���ʽ�쳣!!");
		}finally{
			try {
				bufferedReader.close();
				fileReader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return result;
	}
	/**
	 * ȡ�õ�ǰweb ����������·��.
	 * @param directory ��ǰ·���µ�ʲôĿ¼,�����½�.
	 * @return
	 */
	public static String getSystemPath(String directory) {

		String systemPath = System.getProperty("user.dir");
		if (directory != null && !"".equals(directory)) {
			systemPath = systemPath + java.io.File.separator + directory;
		}
		java.io.File file = new java.io.File(systemPath);
		if (!file.exists()) {
			file.mkdir();
		}
		if (file.isDirectory()) {
			return file.getAbsolutePath();
		}

		return "";
	}	
	
	public static Collection parseCSVFileToCustInfo(String fileName) throws WebActionException {
		ArrayList result = new ArrayList();
		//FileReader fileReader =null;
		BufferedReader bufferedReader = null;
		File file=null;
		try {
			file = new File(fileName);
			if(!(file.getName().endsWith(".csv")||file.getName().endsWith(".CSV"))){
				throw new WebActionException("�Ƿ����ļ�!"+file.getName());
			}
			/*------------yangchen modify 2008/10/30-----------start----------------*/
			//fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"GBK"));
			/*------------yangchen modify 2008/10/30-----------end----------------*/
			String line = "";
			//���������һ��title����һ��title����
			bufferedReader.readLine();
			while ((line = bufferedReader.readLine()) != null) {
				String[] splitLine = line.split(",");
				GehuaCustInfoForBatchMessageDTO custInfo = new GehuaCustInfoForBatchMessageDTO();
				for (int i = 0; i < splitLine.length; i++) {
					String curString = splitLine[i];
					switch (i) {
					case 0:
						//ģ���û�����
						custInfo.setCATVID(curString);
						break;
						//�ͻ�����
					case 1:
						custInfo.setName(curString);
						break;
					}					
				}
				result.add(custInfo);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new WebActionException("�ļ���ʽ�쳣!!");
		}finally{
			try {
				if(bufferedReader!=null)
					bufferedReader.close();
				if(file!=null)
					file.delete();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	/*
	 * ��Ʊ����:�ļ�����
	 */
	
	public static List parseCSVFileWithFapiaoIn(
			String fileName) throws WebActionException {
		List result = new ArrayList();
		FileReader fileReader =null;
		BufferedReader bufferedReader = null;
		
		try {
			File file = new File(fileName);
			if(!(file.getName().toLowerCase().endsWith("csv"))){
				throw new WebActionException("�Ƿ����ļ�!"+file.getName());
			}
			fileReader = new FileReader(file);
			bufferedReader = new BufferedReader(fileReader);
			String line = "";
			int number = 0;
			while ((line = bufferedReader.readLine()) != null) {
				number++;
				LogUtility.log(FileUpload.class, LogLevel.DEBUG, "___line="+line);
				if ("".equals(line.trim()))
					continue;
				result.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new WebActionException("�ļ���ʽ�쳣!!");
		}finally{
			try {
				bufferedReader.close();
				fileReader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	public static Collection parseXlsFileToVodDevice(String fileName) throws WebActionException {
		File file= new File(fileName);
		ArrayList result = new ArrayList();
		
	    if(!(file.getName().endsWith(".xls")||file.getName().endsWith(".XLS"))){
			throw new WebActionException("�Ƿ����ļ�!"+file.getName());
    	}
	    
	    Collection  checkCols =new ArrayList();
	    
		jxl.Workbook rwb = null;
		InputStream is =null;
		try{
		  //����Workbook���� ֻ��Workbook����
		  //ֱ�Ӵӱ����ļ�����Workbook
		  //������������Workbook
		  is = new FileInputStream(fileName);
		  rwb = Workbook.getWorkbook(is);
		  //Sheet(���������)����Excel������½ǵ�Sheet1,Sheet2,Sheet3���ڳ�����
		  //Sheet���±��Ǵ�0��ʼ��
		  //��ȡ��һ��Sheet��
		  Sheet rs = rwb.getSheet(0);
		  //��ȡSheet������������������
		  int rsColumns = rs.getColumns();
		  //��ȡSheet������������������
		  int rsRows = rs.getRows();
		  if (rsRows ==1){
			 throw new WebActionException("�ļ���û����Ҫ�ļ�¼!");
		  }
		  /* ������ȥ��
		  if (rsRows >501){
			 throw new WebActionException("�ļ���¼�����ܳ���500��,��ֳɶ���ļ�����!");
		  }
		  */
		  System.out.println("rsColumns--->"+rsColumns);
		  
		  /*
		  if (rsColumns !=3 || rsColumns!=2){
			 throw new WebActionException("�ļ�ģ���ʽ����!");
		  }
		  */
		  
		  //��ȡָ���µ�Ԫ��Ķ�������
		  for(int i=1;i<rsRows;i++){
			  VodstbDeviceImportDetailDTO  detailDto =new VodstbDeviceImportDetailDTO();
			  detailDto.setStatus("W");
		      for(int j=0;j<rsColumns;j++){
		         Cell cell = rs.getCell(j,i);
		         System.out.println("cell.getContents()======== "+cell.getContents());
		         switch (j) {
		         case 0:
		        	if (cell.getContents()==null)
		        		throw new WebActionException("�ļ��е�"+(i+1)+"�е��豸���к�Ϊ��,����!");
		        	detailDto.setSerialNo(cell.getContents());
		        	break;
		         case 1:
		        	if (cell.getContents()==null)
			       		throw new WebActionException("�ļ��е�"+(i+1)+"�е�InterMacaddressΪ��,����!");
		        	detailDto.setIntermacaddress(cell.getContents());
		        	break;
		         case 2:
			        detailDto.setMacaddress((cell.getContents()==null) ? "" :cell.getContents());
		        	break;
		         
		         default:
					break; 
   	             }	         
		      }
		      
		      result.add(detailDto);
		      String checkContext =detailDto.getSerialNo();
		      if (!checkCols.contains(checkContext)){
		          checkCols.add(checkContext);
		      }else{
		          throw new WebActionException(checkContext+"���ظ���,����!");
		      }
		  }
	    }catch(Exception e){
			e.printStackTrace();
			throw new WebActionException(e.getMessage());
		}finally{
		  //�������ʱ���رն���
		  try{
			 if (is !=null)  is.close();
			 if (rwb !=null) rwb.close();
		     if(file!=null) file.delete();
		  }catch(Exception e){
			 throw new WebActionException(e.getMessage());
		  }
        }
		return result;
	}
	
	public static ArrayList pareXlsFiletoDevice(String fileName,String authorFlag) throws WebActionException {
		File file= new File(fileName);
		ArrayList result = new ArrayList();
		
	    if(!(file.getName().endsWith(".xls")||file.getName().endsWith(".XLS"))){
			throw new WebActionException("�Ƿ����ļ�!"+file.getName());
    	}
        Collection  checkCols =new ArrayList();
        Collection  errorCols =new ArrayList();
        Collection  okCols =new ArrayList();
		jxl.Workbook rwb = null;
		InputStream is =null;
		try{
		   //����Workbook���� ֻ��Workbook����
		   //ֱ�Ӵӱ����ļ�����Workbook
		   //������������Workbook
		   is = new FileInputStream(fileName);
		   rwb = Workbook.getWorkbook(is);
		   //Sheet(���������)����Excel������½ǵ�Sheet1,Sheet2,Sheet3���ڳ�����
		   //Sheet���±��Ǵ�0��ʼ��
		   //��ȡ��һ��Sheet��
		   Sheet rs = rwb.getSheet(0);
		   //��ȡSheet������������������
		   // int rsColumns = rs.getColumns();
		   //��ȡSheet������������������
		   int rsRows = rs.getRows();
		   if (rsRows ==1){
			  throw new WebActionException("�ļ���û����Ҫ�ļ�¼!");
		   }   
		   //��ȡָ���µ�Ԫ��Ķ�������
		   for(int i=1;i<rsRows;i++){
			  Cell cell = rs.getCell(0,i);
			  String serialNo =cell.getContents();
			  if (serialNo ==null ||  serialNo.trim().equals(""))
	        	  break;
			  System.out.println("i==========>"+i);
			  if (!checkCols.contains(serialNo)){
		          checkCols.add(serialNo);
		          TerminalDeviceDTO dto=Postern.getTerminalDeviceBySerialNo(serialNo);
		          // Ԥ��Ȩ���
		          if ("Y".equals(authorFlag)){
				     if (dto.getSerialNo()==null){
					     dto.setSerialNo(serialNo);
					     dto.setComments("�豸������");
					     errorCols.add(dto);
				      }else if (!"W".equals(dto.getStatus())){
				    		dto.setComments("�豸�Ǵ���״̬��");
				    		errorCols.add(dto); 
				      }else if ("Y".equals(dto.getPreAuthorization())){
				    	    dto.setComments("�豸��Ԥ��Ȩ��");
				    		errorCols.add(dto);
				      }else{
				    	 if (!Postern.isSaNewFlag(serialNo)){
				    	    dto.setComments("�����ܿ������һ���豸��");
				    	    errorCols.add(dto);
				    	 }else{
				    	    okCols.add(dto);
				    	 }
				      }
		          }else{
		        	  if (dto.getSerialNo()==null){
						  dto.setSerialNo(serialNo);
						  dto.setComments("�豸������");
						  errorCols.add(dto);
					  }else if (!"W".equals(dto.getStatus())){
					      dto.setComments("�豸�Ǵ���״̬��");
					      errorCols.add(dto); 
					   }else if (!"Y".equals(dto.getPreAuthorization())){
					      dto.setComments("�豸δ��Ԥ��Ȩ��");
					      errorCols.add(dto);
					   }else{
					      if (!Postern.isSaNewFlag(serialNo)){
					    	 dto.setComments("�����ܿ������һ���豸��");
					    	 errorCols.add(dto);
					      }else{
					    	 okCols.add(dto);
					      }
					   }
		          }
		      }else{
		    	  TerminalDeviceDTO dto=new TerminalDeviceDTO();
		    	  dto.setSerialNo(serialNo);
		    	  dto.setComments("�豸��Ϣ�ظ���");
		    	  errorCols.add(dto);
		      }
		   }
		}catch(Exception e){
			e.printStackTrace();
			throw new WebActionException(e.getMessage());
		}finally{
		    //�������ʱ���رն���
		    try{
			   if (is !=null)  is.close();
			   if (rwb !=null) rwb.close();
		       if(file!=null) file.delete();
		    }catch(Exception e){
			   throw new WebActionException(e.getMessage());
		    }
        }
		result.add(errorCols);
		result.add(okCols);
		return result;
	}
	
	public static Collection parseXlsFileToExportCustomer(int opId, String fileName,int orgID,HashMap repeatCatvIDMp) throws WebActionException {
		File file= new File(fileName);
		ArrayList result = new ArrayList();
		
	    if(!(file.getName().endsWith(".xls")||file.getName().endsWith(".XLS"))){
			throw new WebActionException("�Ƿ����ļ�!"+file.getName());
    	}
	    
	    Map  custTypeMap =Postern.getCommonDateKeyAndValueMap("SET_C_CUSTOMERTYPE");
	    Map  curDistMap  =Postern.getRuleDistByOrgID(getParentHasCustomerOrgID(orgID));
	    Collection  checkCols =new ArrayList();
	    Collection  checkdeviceCol =new ArrayList();
	    
		jxl.Workbook rwb = null;
		InputStream is =null;
		try{
		  //����Workbook���� ֻ��Workbook����
		  //ֱ�Ӵӱ����ļ�����Workbook
		  //������������Workbook
		  is = new FileInputStream(fileName);
		  deleteExportCustomerOffalRecode(opId); 
		  rwb = Workbook.getWorkbook(is);
		  //Sheet(���������)����Excel������½ǵ�Sheet1,Sheet2,Sheet3���ڳ�����
		  //Sheet���±��Ǵ�0��ʼ��
		  //��ȡ��һ��Sheet��
		  Sheet rs = rwb.getSheet(0);
		  //��ȡSheet������������������
		  int rsColumns = rs.getColumns();
		  //��ȡSheet������������������
		  int rsRows = rs.getRows();
		  if (rsRows ==1){
			 throw new WebActionException("�ļ���û����Ҫ�ļ�¼!");
		  }
		  /* ������ȥ��
		  if (rsRows >501){
			 throw new WebActionException("�ļ���¼�����ܳ���500��,��ֳɶ���ļ�����!");
		  }
		  */
		  if (rsColumns !=11){
			 throw new WebActionException("�ļ�ģ���ʽ����!");
		  }
		  //��ȡָ���µ�Ԫ��Ķ�������
		  for(int i=1;i<rsRows;i++){
			  ExportCustomerDetailDTO detailDto =new ExportCustomerDetailDTO();
			  String distName ="";
		      for(int j=0;j<rsColumns;j++){
		         Cell cell = rs.getCell(j,i);
		         String contents =(cell.getContents()==null) ? "" :cell.getContents().trim();
		         switch (j) {
		         case 0:
		        	//����������֤��Ϊ�գ����������Excel
		        	if (contents.equals("")){
		        		return result;
		        	}
		        	detailDto.setCatvid(contents);
		        	if (!checkCols.contains(detailDto.getCatvid())){
				        checkCols.add(detailDto.getCatvid());
				    }else{
				    	if (!repeatCatvIDMp.containsKey(detailDto.getCatvid())){
					    	int ct =1;
					    	repeatCatvIDMp.put(detailDto.getCatvid(), new Integer(ct));
					    }else{
					    	int ct =((Integer)repeatCatvIDMp.get(detailDto.getCatvid())).intValue();
					        ct =ct +1;
					        repeatCatvIDMp.put(detailDto.getCatvid(), new Integer(ct)); 
					    }
				    }
		        	break;
		         case 1:
		        	if (contents.equals("")){
			       		throw new WebActionException("�ļ��е�"+(i+1)+"�еĿͻ�����Ϊ��,���飡");
			        }
		        	detailDto.setName(contents);
		        	break;
		         case 2:
		        	 distName =contents;
		        	 break;
		         case 3:
		        	 distName = distName +"-"+contents; 
			         int distId =0;
			         Iterator distItr =curDistMap.keySet().iterator();
			         while (distItr.hasNext()){
			           Integer key =(Integer)distItr.next();
			           String  value =(String)curDistMap.get(key);
			           if (value.equals(distName)){
			               distId =key.intValue();
			               break;
			           }
			         }
			         if (distId ==0)
			           throw new WebActionException("�ļ��е�"+(i+1)+"�е�����:"+distName+" �����Ա��Ͻ�����򲻷���,����!");
			         detailDto.setDistrinctId(distId);	
			         break;
		         case 4:
		        	 detailDto.setDetailAddress(contents);
			         break;
		         case 5:
		        	 String custType ="";
				     Iterator itr =custTypeMap.keySet().iterator();
				     while (itr.hasNext()){
				        String key =(String)itr.next();
				        String value =(String)custTypeMap.get(key);
				        if (value.equals(contents)){
				           custType =key;
				       	   break;
				       	}
				     }
				     if (custType.equals("")) 
				        throw new WebActionException("�ļ��е�"+(i+1)+"�еĿͻ�����:"+contents+" ��ϵͳ�еĿͻ����Ͳ�����,����!"); 
				     detailDto.setCustomerType(custType);
			       	break;
		         case 6:
		        	if (contents.equals("")){
				    	throw new WebActionException("�ļ��е�"+(i+1)+"�е��豸��Ϊ��,���飡");
				    }
		        	String[] serialNostrs =contents.split("#");
		        	System.out.println("serialNostrs.length------>"+serialNostrs.length);
		        	if (serialNostrs.length >2){
		        		throw new WebActionException("�ļ��е�"+(i+1)+"�е��豸��ֻ����дһ���豸,���飡");
		        	}
		        	
		        	for (int k=0;k<serialNostrs.length;k++){
		        		if (!checkdeviceCol.contains(serialNostrs[k])){
		        			checkdeviceCol.add(serialNostrs[k]);
		        		}else{
		        			throw new WebActionException("�ļ��е�"+(i+1)+"�е��豸��ǰ����豸�ظ�,���飡");
		        		}
		        	}
		        	System.out.println("checkdeviceCol----->"+checkdeviceCol);
		        	
		        	TerminalDeviceDTO stbDto =Postern.getTerminalDeviceBySerialNo(serialNostrs[0].trim());
		        	if (stbDto.getSerialNo() ==null){
		        	   throw new WebActionException("�ļ��е�"+(i+1)+"�е��豸��:"+serialNostrs[0]+"��ϵͳ���Ҳ���,���飡");
		        	}
		        	if (!"W".equals(stbDto.getStatus())){
		        	   throw new WebActionException("�ļ��е�"+(i+1)+"�е��豸��:"+serialNostrs[0]+"���Ǵ����豸,���飡");
		        	}
		        	if ("SC".equals(stbDto.getDeviceClass())){
  	        		   throw new WebActionException("�ļ��е�"+(i+1)+"�е��豸��:"+serialNostrs[0]+"�����ܿ������ܷ���#ǰ��,�򵥶����"+",���飡");
		        	}else{
		        	   if (Postern.isSaNewFlag(serialNostrs[0].trim())){
  	        		      if (serialNostrs.length !=1){
		        		    throw new WebActionException("�ļ��е�"+(i+1)+"�е��豸��#֮ǰ:"+serialNostrs[0]+"���ǻ���һ���豸�����治�ܸ��豸"+",���飡");
		        		  }       		
		        	   }else{
		        		   if (serialNostrs.length ==1){
		        			   throw new WebActionException("�ļ��е�"+(i+1)+"�е��豸��:"+serialNostrs[0]+"�ǻ���һ���豸������Ӧ�ø������ܿ�"+",���飡");
		        		   }
		        		   TerminalDeviceDTO scDto =Postern.getTerminalDeviceBySerialNo(serialNostrs[1].trim());
		        		   if (scDto.getSerialNo() ==null){
				        	   throw new WebActionException("�ļ��е�"+(i+1)+"�е��豸��#֮��:"+serialNostrs[1]+"��ϵͳ���Ҳ���,���飡");
				           }
		        		   if (!"W".equals(scDto.getStatus())){
				        	   throw new WebActionException("�ļ��е�"+(i+1)+"�е��豸��:"+serialNostrs[1]+"���Ǵ����豸,���飡");
				           }
		        		   if(!"SC".equals(scDto.getDeviceClass())){
		  	        		   throw new WebActionException("�ļ��е�"+(i+1)+"�е��豸��#֮��:"+serialNostrs[1]+"�������ܿ�,���飡");
				           }
		        	   }
		            }
		        	detailDto.setSerialNo(contents);
		        	break;
		         case 7:
		        	 Date ffTime;
		        	 if (cell.getType() != CellType.DATE){
			        	throw new WebActionException("�ļ��е�"+(i+1)+"�еİ�װ���ʱ��: ������ʱ������,����!");
			         }else{
			        	DateCell datec11 = (DateCell)cell;
			        	ffTime =datec11.getDate();
			         }
			         java.util.Date date1 = new java.util.Date();
			         if (date1.before(ffTime)){
			        	throw new WebActionException("�ļ��е�"+(i+1)+"�еİ�װ���ʱ��: ���ܴ���ϵͳ��ǰʱ�䣬���飡");
			         }	
			         java.sql.Timestamp stp_1=new java.sql.Timestamp(ffTime.getTime());
			         detailDto.setFfcreateTime(stp_1);
			         break;
		         case 8:
		        	 detailDto.setTel(contents); 
			         break;
			     case 9:
			         if (contents.equals(""))
			            throw new WebActionException("�ļ��е�"+(i+1)+"�е����֤�Ų���Ϊ��,����!");
			         if (contents.length()!=15 && contents.length()!=18){
			        	 throw new WebActionException("�ļ��е�"+(i+1)+"�е����֤��Ӧ����15��18λ,����!"); 
			         }
	                 detailDto.setCardID(contents);
			         break;
			     case 10:
			    	 detailDto.setComments(contents);
			         break;
			     default:
				 	 break;
		         }
		      }
		      result.add(detailDto);
		  }
		}catch(Exception e){
			e.printStackTrace();
			throw new WebActionException(e.getMessage());
		}finally{
		  //�������ʱ���رն���
		  try{
			 if (is !=null)  is.close();
			 if (rwb !=null) rwb.close();
		     if(file!=null) file.delete();
		  }catch(Exception e){
			 throw new WebActionException(e.getMessage());
		  }
        }
		return result;
	}
	
	public static Collection parseXlsFileTofoundCustomer(int opId, String fileName,int orgID) throws WebActionException {
		File file= new File(fileName);
		ArrayList result = new ArrayList();
		
	    if(!(file.getName().endsWith(".xls")||file.getName().endsWith(".XLS"))){
			throw new WebActionException("�Ƿ����ļ�!"+file.getName());
    	}
	    Map custTypeMap =Postern.getCommonDateKeyAndValueMap("SET_C_CUSTOMERTYPE");
	    Map proMap =Postern.getAllSellProductIDAndName("6","S");
	    
	    Map  curDistMap = Postern.getRuleDistByOrgID(getParentHasCustomerOrgID(orgID));
	    Collection  checkCols =new ArrayList();
	    
		jxl.Workbook rwb = null;
		InputStream is =null;
		try{
		  //����Workbook���� ֻ��Workbook����
		  //ֱ�Ӵӱ����ļ�����Workbook
		  //������������Workbook
		  is = new FileInputStream(fileName);
		  deleteFoundCustomerOffalRecode(opId); 
		  rwb = Workbook.getWorkbook(is);
		  //Sheet(���������)����Excel������½ǵ�Sheet1,Sheet2,Sheet3���ڳ�����
		  //Sheet���±��Ǵ�0��ʼ��
		  //��ȡ��һ��Sheet��
		  Sheet rs = rwb.getSheet(0);
		  //��ȡSheet������������������
		  int rsColumns = rs.getColumns();
		  //��ȡSheet������������������
		  int rsRows = rs.getRows();
		  if (rsRows ==1){
			 throw new WebActionException("�ļ���û����Ҫ�ļ�¼!");
		  }
		  /* ������ȥ��
		  if (rsRows >501){
			 throw new WebActionException("�ļ���¼�����ܳ���500��,��ֳɶ���ļ�����!");
		  }
		  */
		  if (rsColumns !=15){
			 throw new WebActionException("�ļ�ģ���ʽ����!");
		  }
		  
		  //��ȡָ���µ�Ԫ��Ķ�������
		  for(int i=1;i<rsRows;i++){
			  FoundCustomerDetaiDTO  detailDto =new FoundCustomerDetaiDTO();
			  String distName ="";
		      for(int j=0;j<rsColumns;j++){
		         Cell cell = rs.getCell(j,i);
		        // System.out.println("cell.getContents()======== "+cell.getContents());
		         switch (j) {
		         case 0:
		        	detailDto.setName((cell.getContents()==null) ? "" :cell.getContents());
		        	break;
		         case 1:
		        	distName =(cell.getContents()==null) ? "" :cell.getContents();
		        	break;
		         case 2:
		        	distName = distName +"-"+cell.getContents(); 
		        	int distId =0;
		        	Iterator distItr =curDistMap.keySet().iterator();
		        	while (distItr.hasNext()){
		        	   Integer key =(Integer)distItr.next();
		        	   String  value =(String)curDistMap.get(key);
		        	   if (value.equals(distName)){
		        	       distId =key.intValue();
		        	       break;
		        	   }
		        	}
		        	//����ͻ�����,�ͻ��ϼ���������,�ͻ���������ͬʱΪ��,����Ϊ�ļ�����
		        	if (detailDto.getName().trim().equals("") && distName.trim().equals("-"))
		        		return result;
		        	
		        	if (distId ==0)
		        	   throw new WebActionException("�ļ��е�"+(i+1)+"�е�����:"+distName+" �����Ա��Ͻ�����򲻷���,����!");
		        	detailDto.setDistrinctId(distId);	
		        	break;
		         case 3:
		        	detailDto.setDetailAddress(cell.getContents());
		        	break;
		         case 4:
		        	int serviceAcctCount =0;
		        	try{
		        		serviceAcctCount =Integer.parseInt(cell.getContents());
		        	}catch(Exception e){
		        		throw new WebActionException("�ļ��е�"+(i+1)+"�е��շ��ն�:"+cell.getContents()+" Ӧ����������,����!");
		        	}
		        	if (serviceAcctCount <=0)
		        		throw new WebActionException("�ļ��е�"+(i+1)+"�е��շ��ն�:"+cell.getContents()+" С�ڵ���0��,����");
		        	detailDto.setServiceAccountCount(serviceAcctCount);
		        	break;
		         case 5:
		        	String custType ="";
			        Iterator itr =custTypeMap.keySet().iterator();
			       	while (itr.hasNext()){
			       	   String key =(String)itr.next();
			       	   String value =(String)custTypeMap.get(key);
			       	   if (value.equals(cell.getContents())){
			       		  custType =key;
			       		  break;
			       	   }
			       	}
			       	if (custType.equals("")) 
			       	   throw new WebActionException("�ļ��е�"+(i+1)+"�еĿͻ�����:"+cell.getContents()+" ��ϵͳ�еĿͻ����Ͳ�����,����!"); 
			       	detailDto.setCustomerType(custType);
			       	break;
		         case 6:
		        	int productId =0;
		        	Iterator proitr =proMap.keySet().iterator();
		        	while (proitr.hasNext()){
		        		String key =(String)proitr.next();
				       	String value =(String)proMap.get(key);
				       	if (value.equals(cell.getContents())){
				       		productId =Integer.parseInt(key);
				       		break;
				       	}
		        	}
		        	if (productId ==0)
		        		throw new WebActionException("�ļ��е�"+(i+1)+"�еĲ�Ʒ:"+cell.getContents()+" ��ϵͳ�еĲ�Ʒ������,����!");
		        	detailDto.setProductId(productId);
		        	break;
		         case 7:
		        	Date createTime ;
		        	System.out.println("cell.getType()------>"+cell.getType());
		        	if (cell.getType() != CellType.DATE){
		        		throw new WebActionException("�ļ��е�"+(i+1)+"�еı�װʱ��: ������ʱ������,����!");
		        	}else{
		        		DateCell datec11 = (DateCell)cell;
		        		createTime =datec11.getDate();
		        	}	
		        	java.sql.Timestamp stp=new java.sql.Timestamp(createTime.getTime());
		        	detailDto.setCreateTime(stp);
		        	break;
		         case 8:
		        	Date installTime;
		        	System.out.println("cell.getType()------>"+cell.getType());
		        	if (cell.getType() != CellType.DATE){
		        		throw new WebActionException("�ļ��е�"+(i+1)+"�еİ�װ���ʱ��: ������ʱ������,����!");
		        	}else{
		        		DateCell datec11 = (DateCell)cell;
		        		installTime =datec11.getDate();
		        	}
		        	java.util.Date date1 = new java.util.Date();
		        	if (date1.before(installTime)){
		        		throw new WebActionException("�ļ��е�"+(i+1)+"�еİ�װ���ʱ��: ���ܴ���ϵͳ��ǰʱ�䣬���飡");
		        	}	
		        	java.sql.Timestamp stp_1=new java.sql.Timestamp(installTime.getTime());
		        	detailDto.setInstallTime(stp_1);
		        	break;
		         case 9:
		        	float oncePaymoney =0;
		        	try{
		        		oncePaymoney =Float.parseFloat(cell.getContents());
		        	}catch(Exception e){
		        		throw new WebActionException("�ļ��е�"+(i+1)+"�еĹ���������:"+cell.getContents()+" Ӧ���Ǹ����͵�,����!");
		        	}
		        	detailDto.setOncepaymoney(oncePaymoney);
		        	break;
		         case 10:
		        	float prePaymoney =0;
		        	try{
		        		prePaymoney =Float.parseFloat(cell.getContents());
		        	}catch(Exception e){
		        		throw new WebActionException("�ļ��е�"+(i+1)+"�е����ӷ�:"+cell.getContents()+" Ӧ���Ǹ����͵�,����!");
		        	}
		        	detailDto.setPrePaymoney(prePaymoney);
		        	break;
		         case 11:
		        	detailDto.setTel(cell.getContents()); 
		        	break;
		         case 12:
		        	String cardId =cell.getContents();
		        	if (cardId ==null || cardId.trim().equals(""))
		        	   throw new WebActionException("�ļ��е�"+(i+1)+"�е����֤�Ų���Ϊ��,����!");
                    detailDto.setCardId(cardId);
		        	break;
		         case 13:
		        	String posteCode =cell.getContents();
		        	if (posteCode ==null || posteCode.trim().equals(""))
		        		throw new WebActionException("�ļ��е�"+(i+1)+"�е��ʱ಻��Ϊ��,����!");
		        	detailDto.setPosterCode(posteCode);
		        	break;
		         case 14:
		        	detailDto.setComments(cell.getContents());
		        	break;
		         default:
					break; 
   	             }	         
		      }
		      detailDto.setStatus("��ʼ");
		      result.add(detailDto);
		      String checkContext =detailDto.getName()+"-"+detailDto.getDistrinctId()+"-"+detailDto.getDetailAddress();
		      if (!checkCols.contains(checkContext)){
		          checkCols.add(checkContext);
		      }else{
		          throw new WebActionException(detailDto.getName()+"���ظ���,����!");
		      }
		  }
	    }catch(Exception e){
			e.printStackTrace();
			throw new WebActionException(e.getMessage());
		}finally{
		  //�������ʱ���رն���
		  try{
			 if (is !=null)  is.close();
			 if (rwb !=null) rwb.close();
		     if(file!=null) file.delete();
		  }catch(Exception e){
			 throw new WebActionException(e.getMessage());
		  }
        }
		return result;
	}

	/**
	 * @param orgID
	 * @return ���ݵ�ǰ����Ա����֯idͨ���ݹ�ķ�ʽ�ҵ��ܹ�����ͻ�����֯id
	 */
	private static int getParentHasCustomerOrgID(int orgID) {
		int currenthasCustomerOrgID = 0;
		int currentParentOrgID = 0;
		String haveCustomerFlag = "";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBUtil.getConnection();
			stmt = conn
					.prepareStatement("SELECT ORGID,PARENTORGID,HASCUSTOMERFLAG FROM T_ORGANIZATION WHERE STATUS='V'and ORGID="
							+ orgID);
			rs = stmt.executeQuery();
			if (rs.next()) {
				currentParentOrgID = rs.getInt("PARENTORGID");
				haveCustomerFlag = rs.getString("HASCUSTOMERFLAG");
				if (!"Y".equals(haveCustomerFlag)) {
					currenthasCustomerOrgID = getParentHasCustomerOrgID(currentParentOrgID);
				} else {
					currenthasCustomerOrgID = orgID;
				}
			}
		} catch (SQLException e) {
			LogUtility.log(FileUtility.class, LogLevel.WARN,
					"getParentHasCustomerOrgID", e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException sqle) {
				}
				rs = null;
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException sqle) {
				}
				stmt = null;
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException sqle) {
				}
				conn = null;
			}
		}
		return currenthasCustomerOrgID;
	}
	
	private static void deleteExportCustomerOffalRecode(int opid) throws WebActionException {
		Connection con=null;
		Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement preHeadStmt = null;
        PreparedStatement preDetailStmt= null;
        String   sql =" select batchNo from T_EXPORTCUSTOMERHEAD t "
		             +" where t.CREATEOPID ="+opid+" and t.status ='N'";
	    String sql_1 =" delete from T_EXPORTCUSTOMERHEAD t where t.batchNo=?";
	    String sql_2 =" delete from T_EXPORTCUSTOMERDETAIL t where t.batchNo=?";
	    try{
			con = DBUtil.getConnection();
            stmt = con.createStatement();
            preHeadStmt = con.prepareStatement(sql_1);
            preDetailStmt =con.prepareStatement(sql_2);           
            rs = stmt.executeQuery(sql);            
            while (rs.next()){
               	int batchNo =rs.getInt("batchNo");
               	preHeadStmt.setInt(1,batchNo);
               	preHeadStmt.addBatch();
               	preDetailStmt.setInt(1, batchNo);
               	preDetailStmt.addBatch();
            }
            preHeadStmt.executeBatch();
            preDetailStmt.executeBatch();	
            
		} catch(SQLException E3){
            E3.printStackTrace();
            LogUtility.log(FileUtility.class, LogLevel.WARN,
                    "deleteExportCustomerOffalRecode", E3);
            throw new WebActionException("����ò���Ա���������ݳ���");
        }finally {
            try{
               if (rs != null){
                    try {
                    	rs.close();
                    } catch (SQLException e) {
                    }
               }  
               if (stmt != null){
                  try {
                     stmt.close();
                  } catch (SQLException e) {
                  }
               }        
               if (preHeadStmt != null){
                  try {
            	     preHeadStmt.close();
                  } catch (SQLException e) {
                  }
               }    
               if (preDetailStmt != null){
                  try {
                	 preDetailStmt.close();
                  } catch (SQLException e) {
                  }
                }      
                if (con != null) {
                  try {
                    con.close();
                  } catch (SQLException e) {
                  }
                }
            } catch(Exception e4){
        	   throw new WebActionException(e4.getMessage());
            }
        } 
	}
	
	
	private static void deleteFoundCustomerOffalRecode(int opid) throws WebActionException  {
		Connection con=null;
		Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement preHeadStmt = null;
        PreparedStatement preDetailStmt= null;

        String sql=" select batchNo from T_FOUNDCUSTOMERHEAD t "
			      +" where t.CREATEOPID ="+opid+" and t.status ='��ʼ'";
		String sql_1 =" delete from T_FOUNDCUSTOMERHEAD t where t.batchNo=?";
		String sql_2 =" delete from T_FOUNDCUSTOMERDETAIL t where t.batchNo=?";
		
		try{
			con = DBUtil.getConnection();
            stmt = con.createStatement();
            preHeadStmt = con.prepareStatement(sql_1);
            preDetailStmt =con.prepareStatement(sql_2);           
            rs = stmt.executeQuery(sql);            
            while (rs.next()){
               	int batchNo =rs.getInt("batchNo");
               	preHeadStmt.setInt(1,batchNo);
               	preHeadStmt.addBatch();
               	preDetailStmt.setInt(1, batchNo);
               	preDetailStmt.addBatch();
            }
            preHeadStmt.executeBatch();
            preDetailStmt.executeBatch();	
            
		} catch(SQLException E3){
            E3.printStackTrace();
            LogUtility.log(FileUtility.class, LogLevel.WARN,
                    "deleteOffalRecode", E3);
            throw new WebActionException("����ò���Ա���������ݳ���");
        }finally {
         try{
        	if (rs != null){
                 try {
                	 rs.close();
                 } catch (SQLException e) {
                 }
            }  
            if (stmt != null){
               try {
                   stmt.close();
               } catch (SQLException e) {
               }
            }        
            if (preHeadStmt != null){
               try {
            	   preHeadStmt.close();
               } catch (SQLException e) {
               }
            }    
            if (preDetailStmt != null){
                try {
                	preDetailStmt.close();
                } catch (SQLException e) {
                }
             }      
            if (con != null) {
                try {
                  con.close();
                } catch (SQLException e) {
                }
            }
         } catch(Exception e4){
        	 throw new WebActionException(e4.getMessage());
         }
       } 
	}
	
}
