package com.binovizer.badcoder.messenger.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.binovizer.badcoder.messenger.models.ErrorMessage;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable>{

	@Override
	public Response toResponse(Throwable exception) {
		ErrorMessage error = new ErrorMessage("Internal Server Error", 500, "http://google.com");
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(error)
				.build();
	}
	
}
