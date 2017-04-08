package com.binovizer.badcoder.messenger.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.binovizer.badcoder.messenger.models.Message;
import com.binovizer.badcoder.messenger.resources.bean.MessageFilterBean;
import com.binovizer.badcoder.messenger.services.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
public class MessageResource {

	MessageService messageService = new MessageService();
	
	@GET
	public List<Message> getMessages(@BeanParam MessageFilterBean filterBean) {
		if (filterBean.getYear() > 0) {
			return messageService.getAllMessagesForYear(filterBean.getYear());
		}
		if (filterBean.getStart() >= 0 && filterBean.getSize() > 0) {
			return messageService.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		}
		return messageService.getAllMessages();
	}

	@POST
	public Response addMessage(Message message, @Context UriInfo uriInfo) throws URISyntaxException {
		Message newMessage = messageService.addMessage(message);
		String id  = String.valueOf(newMessage.getId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(id).build();
		Response response = Response.created(uri)
									.entity(newMessage)
									.build();
		return response;
		// return messageService.addMessage(message);
	}
	
	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long id, Message message) {
		message.setId(id);
		return messageService.updateMessage(message);
	}
	
	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") long id) {
		messageService.removeMessage(id);
	}
	
	
	@GET
	@Path("/{messageId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Message getMessageAsJSON(@PathParam("messageId") long id, @Context UriInfo uriInfo) {
		System.out.println("JSON Response");
		Message message = messageService.getMessage(id);
		message.addLink(getUriForSelf(message, uriInfo), "self");
		message.addLink(getUriForProfile(message, uriInfo), "profile");
		message.addLink(getUriForComment(message, uriInfo), "comments");
		return message;
	}
	
	@GET
	@Path("/{messageId}")
	@Produces(MediaType.TEXT_XML)
	public Message getMessageAsXML(@PathParam("messageId") long id, @Context UriInfo uriInfo) {
		System.out.println("XML Response");
		Message message = messageService.getMessage(id);
		message.addLink(getUriForSelf(message, uriInfo), "self");
		message.addLink(getUriForProfile(message, uriInfo), "profile");
		message.addLink(getUriForComment(message, uriInfo), "comments");
		return message;
	}

	private String getUriForComment(Message message, UriInfo uriInfo) {
		String uri = uriInfo.getBaseUriBuilder()
							.path(MessageResource.class)
							.path(MessageResource.class, "getCommentResource")
							.path(CommentResource.class)
							.resolveTemplate("messageId", message.getId())
							.build()
							.toString();
		return uri;
	}

	private String getUriForProfile(Message message, UriInfo uriInfo) {
		String uri = uriInfo.getBaseUriBuilder()
							.path(ProfileResource.class)
							.path(message.getAuthor())
							.build()
							.toString();
		return uri;
	}

	private String getUriForSelf(Message message, UriInfo uriInfo) {
		String uri = uriInfo.getBaseUriBuilder()
							.path(MessageResource.class)
							.path(Long.toString(message.getId()))
							.build()
							.toString();
		return uri;
	}
	
	
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}
	
}
