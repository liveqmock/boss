package com.dtv.oss.service.listhandler;

import java.util.List;
import java.sql.SQLException;

import com.dtv.oss.service.dao.GenericDAO;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public abstract class DefaultListHandler extends ValueListHandler {

    protected GenericDAO dao = null;
    private String selectCriteria = "";

      /**
       * Use setCriteria() method to check whether or not the QUERY is the same
       * @return
       */
      public void setCriteria(Object dto)  throws ListHandlerException{
          //check the result
          String select = getSelectQueryString(dto);
          if (selectCriteria.equals(select) == false) {
              selectCriteria = select;
              executeSearch(selectCriteria);
          }
      }

      protected abstract String getSelectQueryString(Object dto);

      /**
       * executes search. Client can invoke this
      * provided that the search criteria has been
      * properly set. Used to perform search to refresh
      * the list with the latest data.
      */
      private void executeSearch(String selectCriteria) throws ListHandlerException
      {
//          try {
          if (selectCriteria == null) {
              throw new ListHandlerException("Query criteria required...");
          }
          try{
              List resultsList = dao.executeSelect(selectCriteria);
              setList(resultsList);
          } catch (SQLException se) {
              System.out.println("Exception occurs in DefaultListHandler:");
              se.printStackTrace();
          } catch(IteratorException ie){
            System.out.println("Exception occurs in DefaultListHandler:");
            ie.printStackTrace();
          }
      }

}