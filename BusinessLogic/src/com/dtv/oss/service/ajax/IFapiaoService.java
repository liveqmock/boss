package com.dtv.oss.service.ajax;

import com.dtv.oss.ajax.entity.LjFapiao;
import com.dtv.oss.domain.AccountItem;

import java.util.List;

/**
 * Created by lili on 2014/9/21.
 */
public interface IFapiaoService {
    public int getFapiaoCount(String fapiaodaima,String fapiaohaoma);
    public List getFapiaoList(int startIdx,int endIdx,String fapiaodaima,String fapiaohaoma);
}
