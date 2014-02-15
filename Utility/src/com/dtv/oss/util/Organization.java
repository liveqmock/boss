package com.dtv.oss.util;

import com.dtv.oss.dto.*;
import java.util.*;

public class Organization extends OrganizationDTO{
  private Map underling = new HashMap();
  public void setUnderling(Map val){
	underling = val;
  }

  public Map getUnderling(){
	return underling;
  }
}