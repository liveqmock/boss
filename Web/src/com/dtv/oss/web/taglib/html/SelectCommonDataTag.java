package com.dtv.oss.web.taglib.html;

/**
 * <p>Title: BOSS</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Digivision</p>
 * @author Horace Lin
 * @version 1.0
 */

import javax.servlet.jsp.JspException;
import java.util.*;

import com.dtv.oss.dto.OperatorDTO;
import com.dtv.oss.util.Constant;
import com.dtv.oss.util.Postern;
import com.dtv.oss.web.util.CommonSettingValue;
import com.dtv.oss.web.util.WebCurrentOperatorData;
import com.dtv.oss.web.util.WebKeys;
import com.dtv.oss.web.util.WebPrint;
import com.dtv.oss.web.taglib.html.SelectTag;

public class SelectCommonDataTag extends SelectTag {
        protected String mapName = null;
        protected String defaultFlag ="false";
        protected String showAllFlag ="true";
        protected String judgeGradeFlag ="false";

        public String getMapName() {

            return mapName;
        }
        
    	public void setMapName(String pMapName) {
            mapName = pMapName;
        }

        public String getDefaultFlag() {
			return defaultFlag;
		}

		public void setDefaultFlag(String defaultFlag) {
			this.defaultFlag = defaultFlag;
		} 

        public int doStartTag() throws JspException {
            WebPrint.PrintTagDebugInfo(this.getClass().getName(), "doStartTag enter ...");

            if (mapName!=null)
            {
                String sPgName="";
                if (super.set!=null) sPgName=super.set;
                else
                {
                    sPgName=mapName+"_forStore_100";
                    setSet(sPgName);
                }

                int operatorLevel =0;
                WebCurrentOperatorData webCurrentOperatorData =(WebCurrentOperatorData)pageContext.getSession().getAttribute(WebKeys.OPERATOR_SESSION_NAME);
                if (webCurrentOperatorData !=null)
                   operatorLevel =((OperatorDTO)webCurrentOperatorData.getCurrentOperator()).getLevelID();
                
                Map map=Postern.getCommonSettingDataCache(mapName,showAllFlag,judgeGradeFlag,operatorLevel);
                if (map!=null) pageContext.setAttribute(sPgName, map);
                
                if ("true".equalsIgnoreCase(defaultFlag)){
                   Iterator iterator = ((Map)map).entrySet().iterator();
                   while(iterator.hasNext()){
                		Map.Entry item = (Map.Entry)iterator.next();
                		CommonSettingValue settingValue =(CommonSettingValue)item.getValue();
                		if (Constant.GENERAL_FLAG_Y.equalsIgnoreCase(settingValue.getDefaultOrNot())){
                			setDefaultValue(settingValue.getKey());
                		}
                   }
                }
            }

            WebPrint.PrintTagDebugInfo(this.getClass().getName(), "doStartTag return.");

            return super.doStartTag();

        }

    /**
    * Release any acquired resources.
    */
    public void release() {

        super.release();
        mapName = null;

    }

	public String getJudgeGradeFlag() {
		return judgeGradeFlag;
	}

	public void setJudgeGradeFlag(String judgeGradeFlag) {
		this.judgeGradeFlag = judgeGradeFlag;
	}

	public String getShowAllFlag() {
		return showAllFlag;
	}

	public void setShowAllFlag(String showAllFlag) {
		this.showAllFlag = showAllFlag;
	}


}