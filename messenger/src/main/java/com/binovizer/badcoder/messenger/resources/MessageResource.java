package com.binovizer.badcoder.messenger.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.binovizer.badcoder.messenger.models.Message;
import com.binovizer.badcoder.messenger.services.MessageService;

@Path("/messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessageResource {

	MessageService msgService =  new MessageService();
	
	@GET
	public List<Message> getMessages(){
		return msgService.getAllMessages();
	}
	
	@GET
	@Path("/{messageId}")
	public Message test(@PathParam("messageId") long messageId){
		return msgService.getMessage(messageId);
	}
	
	@POST
	public Message createMessage(Message message){
		 return msgService.addMessage(message);
	}
	
	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long messageId, Message message){
		message.setId(messageId);
		return msgService.updateMessage(message);
	}
	
	@DELETE
	@Path("/{messageId}")
	public void removeMessage(@PathParam("messageId") long messageId){
		msgService.removeMessage(messageId);
	}
}
