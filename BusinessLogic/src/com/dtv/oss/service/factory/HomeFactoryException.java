package com.dtv.oss.service.factory;

public class HomeFactoryException  extends Exception
{
    public HomeFactoryException()
    {
    }

    public HomeFactoryException(String s)
    {
        super(s);
    }
    public HomeFactoryException(Exception e)
    {
        super(e.getMessage());
    }
}