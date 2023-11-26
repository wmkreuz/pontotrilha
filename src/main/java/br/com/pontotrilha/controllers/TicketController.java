package br.com.pontotrilha.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.pontotrilha.data.vo.v1.TicketVO;
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

	/*@CrossOrigin(origins = { "http://localhost:8080", "https://pontotrilha.com.br" })
	@PostMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML,
			MediaType.APPLICATION_OCTET_STREAM, MediaType.MULTIPART_FORM_DATA }, produces = {
					MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML,
					MediaType.APPLICATION_OCTET_STREAM, MediaType.MULTIPART_FORM_DATA })
	@Operation(summary = "Add a new ticket", description = "Add a new ticket by passing a JSON, XML, or YML that represents an ticket!", tags = {
			"Tickets" }, responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = TicketVO.class))),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			})
	public TicketVO create(@RequestParam("file") MultipartFile file, @RequestParam String locationName,
			@RequestParam String street, @RequestParam String neighborhood, @RequestParam String city,
			@RequestParam String state, @RequestParam String zipCode, @RequestParam String complement,
			@RequestParam String ticketName, @RequestParam String description,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, @RequestParam String ticketTitle,
			@RequestParam Long quantity, @RequestParam Double tickePrice,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startOfSales,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endOfSales,
			@RequestParam Long minPurchaseQuantity, @RequestParam Long maxPurchaseQuantity,
			@RequestParam Long ticketStatus,
			@RequestParam String map_description, @RequestParam String latitude, @RequestParam String longitude) {
		MapVO map = new MapVO();
		map.setDescription(map_description);
		map.setLatitude(latitude);
		map.setLongitude(longitude);
		var mapRet = mapController.create(file, map);

		var mapEntity = DozerMapper.parseObject(mapRet, Map.class);

		TicketVO ticket = new TicketVO();
		ticket.setLocationName(locationName);
		ticket.setStreet(street);
		ticket.setNeighborhood(neighborhood);
		ticket.setCity(city);
		ticket.setState(state);
		ticket.setZipCode(zipCode);
		ticket.setComplement(complement);
		ticket.setTicketName(ticketName);
		ticket.setDescription(description);
		ticket.setStartDate(startDate);
		ticket.setEndDate(endDate);
		ticket.setTicketTitle(ticketTitle);
		ticket.setQuantity(maxPurchaseQuantity);
		ticket.setTickePrice(tickePrice);
		ticket.setStartOfSales(startOfSales);
		ticket.setEndOfSales(endOfSales);
		ticket.setMinPurchaseQuantity(minPurchaseQuantity);
		ticket.setMaxPurchaseQuantity(maxPurchaseQuantity);
		ticket.setTicketStatus(ticketStatus);
		ticket.setMap(mapEntity);
		return service.create(ticket);
	}

	@CrossOrigin(origins = { "http://localhost:8080", "https://pontotrilha.com.br" })
	@PutMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML }, produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
					MediaType.APPLICATION_YML })
	@Operation(summary = "Updating an ticket", description = "Updating an ticket by passing a JSON, XML, or YML that represents an ticket!", tags = {
			"Tickets" }, responses = {
					@ApiResponse(description = "Updated", responseCode = "200", content = @Content(schema = @Schema(implementation = TicketVO.class))),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			})
	public TicketVO update(@RequestBody TicketVO ticket) {
		return service.update(ticket);
	}*/
}