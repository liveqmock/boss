package com.dtv.oss.dto.custom;

public interface ReflectionSupport {
  /**
   *   store changed field name
   *   Note: implementation in DTO is simple
   *       private java.util.Map map = new HashMap();
   *       public void put(String field) {
   *         map.put(field, Boolean.TRUE);
   *       }
   */
  public void put(String field);
  public java.util.Map getMap();


}