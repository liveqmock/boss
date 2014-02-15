/*
 * Created on 2004-9-2
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.dtv.oss.service.util;

public class CryptoUtility {
  public static String enpwd(String pwd) {
    if (pwd == null || pwd.equals("")){
      return "";
    }


    int len = 0;

    String newpwd = "";
    if (pwd != null) {
      len = pwd.getBytes().length;
    }

    byte [] newca = new byte[1];
    if (len != 0) {
      byte[] ca = pwd.getBytes();
      newca = new byte[ca.length * 2 + 1];

      int l = ca.length;

      for (int i = 0; i < l; i++) {
        newca[2 * i + 1] = (byte) ( ( ca[i]) / 32);
        newca[2 * i + 2] = (byte) ( ( ca[i]) % 32);
      }
    }

    newca[0] = (byte) len;
    //newpwd = new String(newca);
    //return  newpwd;
    //System.out.println("newpwd:"+newpwd);

    return byte2HexString(newca);
  }

  public static String depwd(String pwd) {
    if (pwd == null || pwd.length() < 1) {
      return "";
    }

    byte[] ca = hexString2Byte(pwd);

    int l = (int) ca[0];

    byte[] newca = new byte[l];

    for (int i = 0; i < l; i++) {
      newca[i] = (byte) (ca[2 * i + 1] * 32 + ca[2 * i + 2]);
    }

    String newpwd = new String(newca);

    return newpwd;
  }

//付:16进制转换成字符串,字符串转换成16进制函数
  static byte[] hexString2Byte(String aS) {
    String s;
    if (aS.length() % 2 == 1) {
      s = "0" + aS.toUpperCase();
    }
    else {
      s = aS.toUpperCase();
    }

    byte[] data = new byte[s.length() / 2];

    int x, y;
    for (int i = 0; i < data.length; i++) {
      if (s.charAt(i * 2) == 'A') {
        x = 10;
      }
      else if (s.charAt(i * 2) == 'B') {
        x = 11;
      }
      else if (s.charAt(i * 2) == 'C') {
        x = 12;
      }
      else if (s.charAt(i * 2) == 'D') {
        x = 13;
      }
      else if (s.charAt(i * 2) == 'E') {
        x = 14;
      }
      else if (s.charAt(i * 2) == 'F') {
        x = 15;
      }
      else {
        x = java.lang.Integer.parseInt(s.substring(i * 2, i * 2 + 1));

      }
      if (s.charAt(i * 2 + 1) == 'A') {
        y = 10;
      }
      else if (s.charAt(i * 2 + 1) == 'B') {
        y = 11;
      }
      else if (s.charAt(i * 2 + 1) == 'C') {
        y = 12;
      }
      else if (s.charAt(i * 2 + 1) == 'D') {
        y = 13;
      }
      else if (s.charAt(i * 2 + 1) == 'E') {
        y = 14;
      }
      else if (s.charAt(i * 2 + 1) == 'F') {
        y = 15;
      }
      else {
        y = java.lang.Integer.parseInt(s.substring(i * 2 + 1, i * 2 + 2));

      }
      data[i] = (byte) (x * 16 + y);
    }
    return data;
  }

  static String byte2HexString(byte[] b) {
    if (b == null) {
      return "";
    }

    String sRet = "";
    String tmp = "";
    int iFrom, iTo;

    for (int i = 0; i < b.length; i++) {
      tmp = "00" + Integer.toHexString(b[i]);
      iFrom = tmp.length() - 2;
      iTo = tmp.length();
      sRet = sRet + tmp.substring(iFrom, iTo);
    }
    //System.out.println("sRet is:" +sRet.toUpperCase());
    return sRet.toUpperCase();
  }


  public static void main(String args[]) throws Exception
  {
    String o = "中123国abc";
    String pwd = enpwd(o);
    System.out.println("main newpwd:"+pwd);
    //String a = new String(hexString2Byte(pwd));
    System.out.println("last:" + depwd("0A03180315030803150309030403010309030C0309"));
  }
}
