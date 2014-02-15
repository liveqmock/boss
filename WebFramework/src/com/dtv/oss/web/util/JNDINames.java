package com.dtv.oss.web.util;

/**
 * This class is the central location to store the internal
 * JNDI names of various entities. Any change here should
 * also be reflected in the deployment descriptors.
 *
 * 2004-10-28
 * EJB_CONTROLLER_EJBHOME and COMPONENT_MANAGER is fixed in WebFramework before.
 * Now EJB_CONTROLLER_EJBHOME and COMPONENT_MANAGER'value can be defined in web.xml
 *
  <context-param>
   <param-name>JNDINames.EJB_CONTROLLER_EJBHOME</param-name>
    <param-value>EJBController</param-value>
  </context-param>

  <context-param>
    <param-name>JNDINames.COMPONENT_MANAGER</param-name>
    <param-value>java:comp/env/param/ComponentManager</param-value>
  </context-param>
 *
 * these parameters of context are gained in ControlServlet's init
 */
public class JNDINames {

    private JNDINames(){} // prevent instanciation

    /** JNDI name of the home interface of EJBController */
    public static String EJB_CONTROLLER_EJBHOME;

    //public static final String EJB_CONTROLLER_EJBLOCALHOME =
    //        "EJBControllerLocal";

    //public static String COMPONENT_MANAGER =
    //    "java:comp/env/param/ComponentManager";

}

