package com.dtv.oss.service.commandresponse;

import java.util.HashMap;


public class QueryCommandResponseImpl extends CommandResponse
        implements java.io.Serializable {

    /**
     * the size of the whole result set, not the size of this query
     * You can get the size of query result by list.getSize();
     */
    private int size;
	private int from;
	private int to;
	
    public QueryCommandResponseImpl(int size, Object payload, 
									int from, int to) {
        super(payload);
        this.size = size;
		this.from = from;
		this.to = to;
    }
    
    public QueryCommandResponseImpl(int size, Object payload,Object extraPayload,HashMap map ,int from ,int to){
    	super(payload,extraPayload,map);
    	this.size=size;
    	this.from=from;
    	this.to=to;
    }

    public QueryCommandResponseImpl(int size, Object payload,Object extraPayload,int from ,int to){
    	super(payload,extraPayload);
    	this.size=size;
    	this.from=from;
    	this.to=to;
    }
    
    public int getSize() { return size; }
	public int getFrom() {return from;}
	public int getTo() {return to;}
}