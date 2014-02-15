package com.dtv.oss.web.util;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Horace Lin
 * @version 1.0
 */
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import com.dtv.oss.dto.OperatorDTO;
import com.dtv.oss.dto.OrganizationDTO;
import com.dtv.oss.util.Postern;

public class CurrentLogonOperator {
	/**
	 * @param session
	 * @return ȡ�õ�ǰ����Ա
	 */
	public static OperatorDTO getOperator(HttpSession session)
	{
		WebCurrentOperatorData wrapOp = CurrentOperator.GetCurrentOperator(session);
		if (wrapOp==null) return null;
		
		OperatorDTO dtoOp = (OperatorDTO)wrapOp.getCurrentOperator();
		return dtoOp;
	}
	  public static boolean isParentCompany(HttpServletRequest request)
    {
        return isParentCompany(request.getSession());
    }
     /**
     *  �жϲ���Ա�����ܹ�˾
     * @return int
     */
      public static boolean isParentCompany(HttpSession session)
      {
        OperatorDTO dtoOp = getOperator(session);
        if (dtoOp==null) return false;

        return WebOperationUtil.isParentCompany(dtoOp.getOrgID());
      }

    /**
	 *  �ж��Ƿ��Ƿֹ�˾
	 * @return int
	 */
	public static boolean isSubcompany(int OrgID)
	{
		OrganizationDTO dto = Postern.getOrganizationByID(OrgID);
		if (dto==null) return false;
		
		if (dto.getOrgType()!=null)
		{
			//SET_S_ORGANIZATIONORGTYPE="B"="�ֹ�˾"
			if (dto.getOrgType().equals("B")) return true;
		}
		
		return false;
		
	}
	 /**
     *  �жϲ���Ա���ڷֹ�˾���ֵ�վ���⣩
     * @return int
     */
  public static boolean isSubcompany(HttpSession session)
  {
      OperatorDTO dtoOp = getOperator(session);
      if (dtoOp==null) return false;

      if (WebOperationUtil.isSubcompany(dtoOp.getOrgID()))
      {
        if (WebOperationUtil.isStreetStaion(dtoOp.getOrgID())) return false;
        return true;
      }

      return false;

  }
 public static boolean isStreetStation(HttpSession session)
    {
        OperatorDTO dtoOp = getOperator(session);
        if (dtoOp==null) return false;

        if (WebOperationUtil.isStreetStaion(dtoOp.getOrgID())) return true;

        return false;
    }


	  public static String getCurrentOperatorStreetStationName(HttpSession session)
    {
        String strOrgName = Postern.getOrgNameByID(getCurrentOperatorStreetStationID(session));
        if (strOrgName==null) strOrgName="";

        return strOrgName;
    }
    public static int getCurrentOperatorStreetStationID(HttpSession session)
    {
        OperatorDTO dtoOp = getOperator(session);
        if (dtoOp==null) return 0;

        if (!isStreetStation(session)) return 0;

        return dtoOp.getOrgID();
    }
	
	
	/**
	 * @param request
	 * @return ȡ�õ�ǰ����Ա
	 */
	public static OperatorDTO getOperator(HttpServletRequest request)
	{
		return getOperator(request.getSession());
	}
	/**
	 * @param session
	 * @return ȡ�õ�ǰ����Ա��֯id
	 */
	public static int getCurrentOperatorOrgID(HttpSession session)
	{
		OperatorDTO dtoOp = getOperator(session);
		if (dtoOp==null) return 0;
		
		return dtoOp.getOrgID();
	}
	
	/**
	 * @param request
	 * @return ȡ�õ�ǰ����Ա��֯id
	 */
	public static int getCurrentOperatorOrgID(HttpServletRequest request)
	{
		return getCurrentOperatorOrgID(request.getSession());
	}
	
	/**
	 * @param session
	 * @return ȡ�õ�ǰ����Ա��֯����
	 */
	public static String getCurrentOperatorOrgName(HttpSession session)
	{
		String strOrgName = Postern.getOrgNameByID(getCurrentOperatorOrgID(session));
		if (strOrgName==null) strOrgName="";
		
		return strOrgName;
	}
	
	/**
	 * @param request
	 * @return ȡ�õ�ǰ����Ա��֯����
	 */
	public static String getCurrentOperatorOrgName(HttpServletRequest request)
	{
		return getCurrentOperatorOrgName(request.getSession());
	}
	/**
	 * @param session
	 * @return ȡ�õ�ǰ����Ա�ܹ�����ͻ�����֯id
	 */
	public static int getParentHaveCustomerOrgID(HttpSession session){
		
		return Postern.getParentHasCustomerOrgID(getCurrentOperatorOrgID(session));
	}
	/**
	 * @param request
	 * @return ȡ�õ�ǰ����Ա�ܹ�����ͻ�����֯id
	 */
	public static int getParentHaveCustomerOrgID(HttpServletRequest request){
		return getParentHaveCustomerOrgID(request.getSession());
	}
	/**
	 * @param session
	 * @return ȡ�õ�ǰ����Ա�ܹ�����ͻ�����֯����
	 */
	public static String getParentHaveCustomerOrgName(HttpSession session){
		
		return Postern.getOrgNameByID(getParentHaveCustomerOrgID(session));
	}
	/**
	 * @param request
	 * @return ȡ�õ�ǰ����Ա�ܹ�����ͻ�����֯����
	 */
	public static String getParentHaveCustomerOrgName(HttpServletRequest request){
		return getParentHaveCustomerOrgName(request.getSession());
	}
	
}