package com.dtv.oss.dto;

public class JqPrintConfigDTO implements  java.io.Serializable{
   private int     belongto ;
   private int     position_width;
   private int     position_height;
   private int     position_prior;
   private String  printName ;
   private int     printName_Blank ;
   private String  printFlag ;
   private String  printContext ;
   private int     printContext_Blank  ;
   private String  paramtype   ;
   private String  paramname  ;
   
   
   public int getBelongto() {
	  return belongto;
   }
   public void setBelongto(int belongto) {
	  this.belongto = belongto;
   }
   
   public String getParamtype() {
	  return paramtype;
   }
   public void setParamtype(String paramtype) {
	  this.paramtype = paramtype;
   }
    
   public String getParamname() {
	  return paramname;
   }
   public void setParamname(String paramname) {
	   this.paramname = paramname;
   }
   
   public int getPosition_height() {
	  return position_height;
   }
   public void setPosition_height(int position_height) {
	  this.position_height = position_height;
   }
   
   public int getPosition_width() {
	  return position_width;
   }
   public void setPosition_width(int position_width) {
	  this.position_width = position_width;
   }
      
   public int getPosition_prior() {
	 return position_prior;
   }
   public void setPosition_prior(int position_prior) {
	  this.position_prior = position_prior;
   }
   
   public String getPrintFlag() {
	  return printFlag;
   }
   public void setPrintFlag(String printFlag) {
	  this.printFlag = printFlag;
   }
   
   public String getPrintName() {
	  return printName;
   }
   public void setPrintName(String printName) {
	  this.printName = printName;
   }
   
   public int getPrintName_Blank() {
	  return printName_Blank;
   }
   public void setPrintName_Blank(int printName_Blank) {
	  this.printName_Blank = printName_Blank;
   }
   
   public String getPrintContext() {
	  return printContext;
   }
   public void setPrintContext(String printContext) {
	  this.printContext = printContext;
   }
   
   public int getPrintContext_Blank() {
	  return printContext_Blank;
   }
   public void setPrintContext_Blank(int printContext_Blank) {
	  this.printContext_Blank = printContext_Blank;
   }  
   
}
