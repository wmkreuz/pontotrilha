package br.com.pontotrilha.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.stripe.exception.StripeException;

import br.com.pontotrilha.data.vo.v1.EventVO;
import br.com.pontotrilha.data.vo.v1.TicketVO;
import br.com.pontotrilha.mapper.DozerMapper;
import br.com.pontotrilha.model.Event;
import br.com.pontotrilha.repositories.UserRepository;
import br.com.pontotrilha.services.EventServices;
import br.com.pontotrilha.services.TicketServices;
import br.com.pontotrilha.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/ticket/v1")
@Tag(name = "Tickets", description = "Endpoint to manage tickets")
public class TicketController {

	@Autowired
	private TicketServices service;

	@Autowired
	PaymentController paymentController;

	@Autowired
	EventServices eventServices;

	@Autowired
	UserRepository userRepository;

	@CrossOrigin(origins = { "http://localhost:8080", "https://pontotrilha.com.br" })
	@GetMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Find all tickets", description = "Find all tickets", tags = { "Tickets" }, responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TicketVO.class)))
			}),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	})
	public List<TicketVO> findAll() {
		return service.findAll();
	}

	@CrossOrigin(origins = { "http://localhost:8080", "https://pontotrilha.com.br" })
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML })
	@Operation(summary = "Find an ticket", description = "Find an ticket", tags = { "Tickets" }, responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = TicketVO.class))),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	})
	public TicketVO findById(@PathVariable(value = "id") Long id) {
		return service.findById(id);
	}

	@CrossOrigin(origins = { "http://localhost:8080", "https://pontotrilha.com.br" })
	@PostMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML,
			MediaType.APPLICATION_OCTET_STREAM, MediaType.MULTIPART_FORM_DATA }, produces = {
					MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML,
					MediaType.APPLICATION_OCTET_STREAM, MediaType.MULTIPART_FORM_DATA })
	@Operation(summary = "Add a new ticket", description = "Add a new ticket!", tags = {
			"Maps" }, responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = TicketVO.class))),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			})
	public RedirectView create(@RequestParam String username, @RequestParam Long eventId, @RequestParam Long quantity)
			throws StripeException {

		EventVO eventVO = eventServices.findById(eventId);
		var eventEntity = DozerMapper.parseObject(eventVO, Event.class);

		var user = userRepository.findByUsername(username);

		TicketVO ticket = new TicketVO();
		ticket.setEvent(eventEntity);
		ticket.setPurchasedByUser(user);
		service.create(ticket);
		return new RedirectView(paymentController.checkout(eventEntity, user, quantity));
	}
}