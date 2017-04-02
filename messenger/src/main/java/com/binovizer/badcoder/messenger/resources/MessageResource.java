package com.binovizer.badcoder.messenger.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.binovizer.badcoder.messenger.models.Message;
import com.binovizer.badcoder.messenger.services.MessageService;

@Path("/messages")
public class MessageResource {

	MessageService msgService =  new MessageService();
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<Message> getMessages(){
		return msgService.getAllMessages();
	}
	
	@GET
	@Path("/{messageId}")
	@Produces(MediaType.APPLICATION_XML)
	public Message test(@PathParam("messageId") long messageId){
		return msgService.getMessage(messageId);
	}
	
}
