package com.readysteady;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

abstract public class DefaultExceptionHandler {
	final static Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

	@ExceptionHandler(NotFoundException.class)
	public @ResponseBody 
	JSONException notFound(final NotFoundException aNotFoundException,final HttpServletResponse aResponse) {
		return statusAndLog(aNotFoundException,aResponse,HttpServletResponse.SC_NOT_FOUND);
	}

	private JSONException statusAndLog(final Exception anException, final HttpServletResponse aResponse, final int aStatus){
		aResponse.setStatus(aStatus);
		if (logger.isDebugEnabled()) {
			logger.debug(ExceptionUtils.getFullStackTrace(anException));
		}
		return new JSONException(anException);
	}
	
	@ExceptionHandler(ResourceConflictException.class)
	public @ResponseBody 
	JSONException resourceExists(final ResourceConflictException aConflictException,final HttpServletResponse aResponse) {
		return statusAndLog(aConflictException,aResponse,HttpServletResponse.SC_CONFLICT);
	}

	@ExceptionHandler(ForbiddenException.class)
	public @ResponseBody 
	JSONException  forbiddenRequest(final ForbiddenException aForbiddenException, final HttpServletResponse aResponse) {
		return statusAndLog(aForbiddenException,aResponse,HttpServletResponse.SC_FORBIDDEN);
	}
	
	@ExceptionHandler(Exception.class)
	public @ResponseBody
	JSONException unknownError(final Exception anException, final HttpServletResponse aResponse){
		return statusAndLog(anException, aResponse,HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
}
