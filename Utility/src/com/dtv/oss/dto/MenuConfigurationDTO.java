package com.dtv.oss.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MenuConfigurationDTO implements Serializable {
    private String menuKey;
    private String menuName;
    private String menuType;
    private String menuDescription;
    private String parentMenu;
    private String menuAction;
    private String parameterList;
    private int priority;
    private String status;
    private int menuWidth;
    private int menuHeigth;
    private Timestamp dtCreate;
    private Timestamp dtLastmod;
    private List childMenuList=null;
    private int subMenuWidth;
    
    public String getMenuKey() {
        return menuKey;
    }

    public void setMenuKey(String menuKey) {
        this.menuKey = menuKey;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getMenuDescription() {
        return menuDescription;
    }

    public void setMenuDescription(String menuDescription) {
        this.menuDescription = menuDescription;
    }

    public String getParentMenu() {
        return parentMenu;
    }

    public void setParentMenu(String parentMenu) {
        this.parentMenu = parentMenu;
    }

    public String getMenuAction() {
        return menuAction;
    }

    public void setMenuAction(String menuAction) {
        this.menuAction = menuAction;
    }

    public String getParameterList() {
        return parameterList;
    }

    public void setParameterList(String parameterList) {
        this.parameterList = parameterList;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMenuWidth() {
        return menuWidth;
    }

    public void setMenuWidth(int menuWidth) {
        this.menuWidth = menuWidth;
    }

    public int getMenuHeigth() {
        return menuHeigth;
    }

    public void setMenuHeigth(int menuHeigth) {
        this.menuHeigth = menuHeigth;
    }

    public Timestamp getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(Timestamp dtCreate) {
        this.dtCreate = dtCreate;
    }

    public Timestamp getDtLastmod() {
        return dtLastmod;
    }

    public void setDtLastmod(Timestamp dtLastmod) {
        this.dtLastmod = dtLastmod;
    }

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof MenuConfigurationDTO))
            return false;
        MenuConfigurationDTO that = (MenuConfigurationDTO) obj;
        if (!(that.menuKey == null ? this.menuKey == null :
              that.menuKey.equals(this.menuKey)))
            return false;
        if (!(that.menuName == null ? this.menuName == null :
              that.menuName.equals(this.menuName)))
            return false;
        if (!(that.menuType == null ? this.menuType == null :
              that.menuType.equals(this.menuType)))
            return false;
        if (!(that.menuDescription == null ? this.menuDescription == null :
              that.menuDescription.equals(this.menuDescription)))
            return false;
        if (!(that.parentMenu == null ? this.parentMenu == null :
              that.parentMenu.equals(this.parentMenu)))
            return false;
        if (!(that.menuAction == null ? this.menuAction == null :
              that.menuAction.equals(this.menuAction)))
            return false;
        if (!(that.parameterList == null ? this.parameterList == null :
              that.parameterList.equals(this.parameterList)))
            return false;
        if (that.priority != this.priority)
            return false;
        if (!(that.status == null ? this.status == null :
              that.status.equals(this.status)))
            return false;
        if (that.menuWidth != this.menuWidth)
            return false;
        if (that.menuHeigth != this.menuHeigth)
            return false;
        if (!(that.dtCreate == null ? this.dtCreate == null :
              that.dtCreate.equals(this.dtCreate)))
            return false;
        if (!(that.dtLastmod == null ? this.dtLastmod == null :
              that.dtLastmod.equals(this.dtLastmod)))
    	if (that.subMenuWidth!=this.subMenuWidth)
            return false;
        return true;
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + this.menuKey.hashCode();
        result = 37 * result + this.menuName.hashCode();
        result = 37 * result + this.menuType.hashCode();
        result = 37 * result + this.menuDescription.hashCode();
        result = 37 * result + this.parentMenu.hashCode();
        result = 37 * result + this.menuAction.hashCode();
        result = 37 * result + this.parameterList.hashCode();
        result = 37 * result + (int)this.priority;
        result = 37 * result + this.status.hashCode();
        result = 37 * result + (int)this.menuWidth;
        result = 37 * result + (int)this.menuHeigth;
        result = 37 * result + this.dtCreate.hashCode();
        result = 37 * result + this.dtLastmod.hashCode();
        result = 37 * result + this.subMenuWidth;
        return result;
    }

    public String toString() {
        StringBuffer returnStringBuffer = new StringBuffer(416);
        returnStringBuffer.append("[");
        returnStringBuffer.append("menuKey:").append(menuKey);
        returnStringBuffer.append("menuName:").append(menuName);
        returnStringBuffer.append("menuType:").append(menuType);
        returnStringBuffer.append("menuDescription:").append(menuDescription);
        returnStringBuffer.append("parentMenu:").append(parentMenu);
        returnStringBuffer.append("menuAction:").append(menuAction);
        returnStringBuffer.append("parameterList:").append(parameterList);
        returnStringBuffer.append("priority:").append(priority);
        returnStringBuffer.append("status:").append(status);
        returnStringBuffer.append("menuWidth:").append(menuWidth);
        returnStringBuffer.append("menuHeigth:").append(menuHeigth);
        returnStringBuffer.append("dtCreate:").append(dtCreate);
        returnStringBuffer.append("dtLastmod:").append(dtLastmod);
        returnStringBuffer.append("subMenuWidth:").append(subMenuWidth);
        returnStringBuffer.append("]");
        return returnStringBuffer.toString();
    }

	/**
	 * @return the childMenuList
	 */
	public List getChildMenuList() {
		return childMenuList;
	}

	/**
	 * @param childMenuList the childMenuList to set
	 */
	public void setChildMenuList(List childMenuList) {
		this.childMenuList = childMenuList;
	}
	/**
	 * @param dto
	 */
	public void addChildMenu(MenuConfigurationDTO dto) {
		if(this.childMenuList==null)
			this.childMenuList=new ArrayList();
		this.childMenuList.add(dto);
	}

	/**
	 * @return the subMenuWidth
	 */
	public int getSubMenuWidth() {
		return subMenuWidth;
	}

	/**
	 * @param subMenuWidth the subMenuWidth to set
	 */
	public void setSubMenuWidth(int subMenuWidth) {
		this.subMenuWidth = subMenuWidth;
	}
}
