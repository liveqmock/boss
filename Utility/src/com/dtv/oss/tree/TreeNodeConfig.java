/*
 * Created on 2004-11-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.dtv.oss.tree;

import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @author 240910y
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TreeNodeConfig {
	private  static TreeNodeConfig instance=null;
	private  static Properties referenceProperty=null;
	/**
	 * 
	 */
	private  TreeNodeConfig() {
		super();
		initMailServerProvider();
	}
	public static TreeNodeConfig getInstance() {
		if(instance==null){
			instance =new TreeNodeConfig();
		}
        return instance;
    }
	private void initMailServerProvider(){
		try{
			ResourceBundle resourceBundle=ResourceBundle.getBundle("treeNodeConfig");
			referenceProperty=System.getProperties();
			referenceProperty.put("child_brother_open",resourceBundle.getString("child_brother_open"));
			referenceProperty.put("child_brother_close",resourceBundle.getString("child_brother_close")); 
			referenceProperty.put("child_nobrother_open",resourceBundle.getString("child_nobrother_open")); 
			referenceProperty.put("child_nobrother_close",resourceBundle.getString("child_nobrother_close"));
			referenceProperty.put("nochild_brother",resourceBundle.getString("nochild_brother"));
			referenceProperty.put("nochild_nobrother",resourceBundle.getString("nochild_nobrother"));
			referenceProperty.put("uprightness_beeline",resourceBundle.getString("uprightness_beeline"));
			referenceProperty.put("target_submit",resourceBundle.getString("target_submit"));
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	public String getPictureReference(String key){
		return (String)referenceProperty.getProperty(key);
	}
}
