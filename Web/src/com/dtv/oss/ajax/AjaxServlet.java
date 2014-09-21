package com.dtv.oss.ajax;

import com.dtv.oss.ajax.entity.LjFapiao;
import com.dtv.oss.service.ajax.FapiaoServiceImpl;
import com.dtv.oss.service.ajax.IFapiaoService;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lili on 2014/9/20.
 */
public class AjaxServlet extends HttpServlet {
    public void doGet(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws javax.servlet.ServletException, java.io.IOException {
        String servletPath= req.getServletPath();
        if(servletPath!=null&&servletPath.startsWith("/ajax/")){
            String realServletName=servletPath.split("/ajax/")[1];
            if("fapiao.ajax".equalsIgnoreCase(realServletName)){
               fapiao(req, resp);
            }
            else{

            }
        }
    }
    public void doPost(javax.servlet.http.HttpServletRequest req, javax.servlet.http.HttpServletResponse resp) throws javax.servlet.ServletException, java.io.IOException {
        this.doGet(req, resp);
    }
    private void fapiao(HttpServletRequest req,HttpServletResponse rep){
        String action=req.getParameter("action");
        if("list".equalsIgnoreCase(action)){

            String fapiaodaima=req.getParameter("fapiaodaima");
            String fapiaohaoma=req.getParameter("fapiaohaoma");
            String startIdx=req.getParameter("jtStartIndex");
            String pageSize=req.getParameter("jtPageSize");
            IFapiaoService fapiaoService=new FapiaoServiceImpl();
            AjaxTableResult ajaxTableResult=new AjaxTableResult();
            int count=fapiaoService.getFapiaoCount(fapiaodaima,fapiaohaoma);
            List ljFapiaoList= fapiaoService.getFapiaoList(Integer.parseInt(startIdx),Integer.parseInt(pageSize)+Integer.parseInt(startIdx),fapiaodaima,fapiaohaoma);
            ajaxTableResult.setRecords(ljFapiaoList);
            ajaxTableResult.setTotalRecordCount(count);

            try {
                JSONObject jsonObject=JSONObject.fromObject(ajaxTableResult);
                rep.getOutputStream().write( replaceForJTable(jsonObject.toString()).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            };
        }
    }
    private String replaceForJTable(String result){
        result= result.replaceAll("records","Records");
        result= result.replaceAll("totalRecordCount","TotalRecordCount");
        result= result.replaceAll("result","Result");
        return result;
    }
}
