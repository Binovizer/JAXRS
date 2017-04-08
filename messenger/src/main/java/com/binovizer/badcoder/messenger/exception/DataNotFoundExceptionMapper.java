package com.binovizer.badcoder.messenger.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.binovizer.badcoder.messenger.models.ErrorMessage;

@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException>{

	@Override
	public Response toResponse(DataNotFoundException exception) {
		ErrorMessage error = new ErrorMessage(exception.getMessage(), 404, "htttp://www.google.com");
		return Response.status(Status.NOT_FOUND)
					.entity(error)
					.build();
	}

}
