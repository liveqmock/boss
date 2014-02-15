package com.dtv.oss.service.factory;

import javax.ejb.*;
import java.util.*;
import javax.naming.*;
import javax.transaction.UserTransaction;
import javax.sql.DataSource;

/**
 * Home Factory, maintains a simple hashmap cache of EJBHomes
 * For a production implementations, exceptions such as NamingException
 * can be wrapped with a factory exception to futher simplify
 * the client. It also provide business tier related object lookup service
 */
public class HomeFactory implements java.io.Serializable
{

  private Map ejbHomes;
  private static HomeFactory aFactorySingleton;
  private Context ctx;
  private javax.transaction.UserTransaction tx = null;
  private boolean verbose = true;
  private DataSource ds = null;
  private DataSource ds4scna = null;

  /**
   * EJBHomeFactory private constructor.
   */
   private HomeFactory() throws HomeFactoryException
   {
          this.ejbHomes = Collections.synchronizedMap(new HashMap());
          //Hashtable hash = new Hashtable();
          //hash.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
          //hash.put(Context.PROVIDER_URL, "t3://localhost:7001");
          try {
              //ctx = new InitialContext(hash);
              ctx = new InitialContext();
          }catch(NamingException ne) {
              ne.printStackTrace();
              throw new HomeFactoryException(ne);
          }

    }

    /*
     * Returns the singleton instance of the EJBHomeFactory
     * The sychronized keyword is intentionally left out the
     * as I don't think the potential to intialize the singleton
     * twice at startup time (which is not a destructive event)
     * is worth creating a sychronization bottleneck on this
     * VERY frequently used class, for the lifetime of the
     * client application.
     */
     public static HomeFactory getFactory() throws HomeFactoryException
     {
         if ( HomeFactory.aFactorySingleton == null ) {
                HomeFactory.aFactorySingleton = new HomeFactory();
         }
         return HomeFactory.aFactorySingleton;
      }

      public UserTransaction lookUpUserTransaction()  throws HomeFactoryException
      {
          if (tx != null) return tx;

        try
        {
              tx = (UserTransaction)ctx.lookup("javax.transaction.UserTransaction");
              return tx;
        }
        catch (NamingException e)
        {
            if (verbose) e.printStackTrace();
          throw new HomeFactoryException(e);
        }
      }
    /**
     * Lookup and cache an EJBHome object.
     * This 'alternate' implementation delegates JNDI name knowledge
     * to the client. When different developer is in charge for differenct layer, this
     * type of implementation become valuable.
     */
     public EJBLocalHome lookUpHome(Class homeClass, String jndiName)   throws HomeFactoryException
     {
       EJBLocalHome anEJBHome = null;

       anEJBHome = (EJBLocalHome) this.ejbHomes.get(homeClass);

        try
        {
            if(anEJBHome == null)
            {
                //For local home interface no need to use narrow() at all
                //anEJBHome = (EJBLocalHome) PortableRemoteObject.narrow
                //            ( ctx.lookup (jndiName), homeClass);
                anEJBHome = (EJBLocalHome)ctx.lookup(jndiName);
                this.ejbHomes.put(homeClass, anEJBHome);
            }
        }
        catch (ClassCastException e)
        {
            e.printStackTrace();
            throw new HomeFactoryException(e.getMessage());
        }
        catch (NamingException e)
        {
            e.printStackTrace();
            throw new HomeFactoryException(e.getMessage());
        }

        return anEJBHome;
      }

      public DataSource getDataSource4ScnA() throws HomeFactoryException {
          if (ds4scna != null) return ds4scna;
          try {
          	//暂时注释掉，以后需要的话放开注释
          	ds4scna = (DataSource)ctx.lookup("TXOracleDS/SCNA");
          } catch(NamingException ne) {
              throw new HomeFactoryException("JNDI name:TXOracleDS/SCNA for data source is wrong!");
          }
          return ds4scna;
      }

      public DataSource getDataSource() throws HomeFactoryException {
        if (ds != null) return ds;
        try {
            ds = (DataSource)ctx.lookup("TXOracleDS/P5");
        } catch(NamingException ne) {
        		ne.printStackTrace();
            throw new HomeFactoryException("JNDI name:TXOracleDS/P5 for data source is wrong!");
        }
        return ds;
    }

}
