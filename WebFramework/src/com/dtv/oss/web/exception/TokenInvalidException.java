package com.dtv.oss.web.exception;

/**
 * This exception will be thrown when token is invalid
 */
public class TokenInvalidException extends WebActionException
{
    public TokenInvalidException() {}

    public TokenInvalidException(String str) {
      super(str);
    }

}