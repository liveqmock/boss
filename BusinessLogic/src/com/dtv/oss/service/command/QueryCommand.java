/**
 * <p>Title: BOSS 2</p>
 * <p>Description: BOSS second iterative development project</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Shanghai DigiVision Technology Co. Ltd</p>
 * @author Mac Wang
 * @version 1.2
 */

package com.dtv.oss.service.command;

//J2EE imports
import java.util.List;


import com.dtv.oss.log.LogLevel;
import com.dtv.oss.log.LogUtility;
import com.dtv.oss.service.commandresponse.CommandResponse;
import com.dtv.oss.service.commandresponse.ErrorCode;
import com.dtv.oss.service.commandresponse.QueryCommandResponseImpl;
import com.dtv.oss.service.ejbevent.*;
import com.dtv.oss.service.listhandler.*;

/**
 *  Now QueryCommand is a abstract class,
 * each module has it's QueryCommand implementation, eg CsrQueryCommand
 * and the QueryCommand distinguish them with the querytype constant in QueryEJBEvent in getActualListHandler method
 */
public abstract class QueryCommand extends Command {

public CommandResponse execute(EJBEvent ev) throws com.dtv.oss.service.command.CommandException {

    LogUtility.log(QueryCommand.class,LogLevel.DEBUG,"in execute method QueryCommand execute...");
    QueryEJBEvent event = (QueryEJBEvent) ev;
    ValueListHandler handler = null;
    String hashCodeName = "";
    List list;

    if (event.getOperatorID() == 0) {
      throw new CommandException(ErrorCode.APP_OPERATOR_ISNOT_VALID);
    }
    hashCodeName = "nocache";	//不进行数据缓存
    handler = getActualListHandler(event);

    try {

      LogUtility.log(QueryCommand.class,LogLevel.DEBUG,"execute",hashCodeName);
      if (this.processor.getAttribute(hashCodeName) != null) { //query happened
        handler = (ValueListHandler) processor.getAttribute(hashCodeName);
      } else { //do the query
        if (!hashCodeName.endsWith("nocache")) {
          processor.setAttribute(hashCodeName, handler);
        }
      }

      LogUtility.log(QueryCommand.class,LogLevel.DEBUG,"in execute method begin handler setCriteria in QueryCommand...");
      
      handler.setOperatorID(event.getOperatorID());
      checkFrom2To(event.getFrom(), event.getTo());
      handler.setFrom(event.getFrom());
      handler.setTo(event.getTo());
      handler.setDown(event.isDown());
      handler.setCriteria(event.getCriteriaDTO());

      LogUtility.log(QueryCommand.class,LogLevel.DEBUG,"in execute method begin getResultList in QueryCommand...");
      list = handler.getList();
      LogUtility.log(QueryCommand.class,LogLevel.DEBUG,"in execute method begin return in QueryCommand...");
      int size = handler.getTotalRecordSize();
      if (size == 0) {
        list = handler.getResultList(event.getFrom(), event.getTo());
        size = handler.getList().size();
      }
      return new QueryCommandResponseImpl(size, list, handler.getExtraObj(),handler.getSumByGroupHashMap(),event.getFrom(),
              event.getTo());
      
    } catch (IteratorException ie) {
      ie.printStackTrace();
      QueryCommandResponseImpl response = new QueryCommandResponseImpl(0, null,null,
          0, 0);
      throw new CommandException(ErrorCode.APP_QUERY_LIST_ERROR);
    } catch (ListHandlerException lhe) {
      lhe.printStackTrace();
      QueryCommandResponseImpl response = new QueryCommandResponseImpl(0, null,null,
          0, 0);
      throw new CommandException(ErrorCode.APP_QUERY_PARA_ERROR);
    }
  }

  private void checkFrom2To(int from, int to) throws CommandException {
    if ( (from < 0) || (to < 0) ||
        (to - from > 500) || (to - from < 0)) {
      throw new CommandException(ErrorCode.APP_ILL_PARAMETER);
    }
  }

  public abstract ValueListHandler getActualListHandler(QueryEJBEvent event);
  
  public static void main(String[] args) throws Exception {
  }
}