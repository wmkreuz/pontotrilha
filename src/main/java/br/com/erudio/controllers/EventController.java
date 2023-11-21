package br.com.erudio.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.erudio.data.vo.v1.EventVO;
import br.com.erudio.data.vo.v1.MapVO;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.model.Map;
import br.com.erudio.services.EventServices;
import br.com.erudio.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

//@CrossOrigin
@RestController
@RequestMapping("/api/event/v1")
@Tag(name = "Events", description = "Endpoints for Managing Events")
public class EventController {
	
	@Autowired
	private EventServices service;

	@Autowired
    private MapController mapController;
	
	@GetMapping(
		produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Finds all Events", description = "Finds all Events",
		tags = {"Events"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "200",
				content = {
					@Content(
						mediaType = "application/json",
						array = @ArraySchema(schema = @Schema(implementation = EventVO.class))
					)
				}),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
		}
	)
	public List<EventVO> findAll() {
		return service.findAll();
	}
	
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping(value = "/{id}",
		produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  })
	@Operation(summary = "Finds a Event", description = "Finds a Event",
		tags = {"Events"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "200",
				content = @Content(schema = @Schema(implementation = EventVO.class))
			),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
		}
	)
	public EventVO findById(@PathVariable(value = "id") Long id) {
		return service.findById(id);
	}

	@CrossOrigin(origins = {"http://localhost:8080", "https://erudio.com.br"})
	@PostMapping(
		consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML, MediaType.APPLICATION_OCTET_STREAM, MediaType.MULTIPART_FORM_DATA  },
		produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML, MediaType.APPLICATION_OCTET_STREAM, MediaType.MULTIPART_FORM_DATA   }
		)
	@Operation(summary = "Adds a new Event",
		description = "Adds a new Event by passing in a JSON, XML or YML representation of the event!",
		tags = {"Events"},
		responses = {
			@ApiResponse(description = "Success", responseCode = "200",
				content = @Content(schema = @Schema(implementation = EventVO.class))
			),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
		}
	)
	public EventVO create(@RequestParam("file") MultipartFile file, @RequestParam String locationName, @RequestParam String street, @RequestParam String neighborhood, @RequestParam String city,
                     @RequestParam String state, @RequestParam String zipCode, @RequestParam String complement, @RequestParam String eventName, @RequestParam String description,
                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, @RequestParam String ticketTitle, @RequestParam Long quantity, @RequestParam Double tickePrice,
                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startOfSales, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endOfSales, @RequestParam Long minPurchaseQuantity, @RequestParam Long maxPurchaseQuantity, @RequestParam Long eventStatus, 
					 @RequestParam String map_description, @RequestParam String latitude, @RequestParam String longitude) {
		MapVO map = new MapVO();
		map.setDescription(map_description);
		map.setLatitude(latitude);
		map.setLongitude(longitude);
		var mapRet = mapController.create(file, map);

		var mapEntity = DozerMapper.parseObject(mapRet, Map.class);

		EventVO event = new EventVO();
		event.setLocationName(locationName);
		event.setStreet(street);
		event.setNeighborhood(neighborhood);
		event.setCity(city);
		event.setState(state);
		event.setZipCode(zipCode);
		event.setComplement(complement);
		event.setEventName(eventName);
		event.setDescription(description);
		event.setStartDate(startDate);
		event.setEndDate(endDate);
		event.setTicketTitle(ticketTitle);
		event.setQuantity(maxPurchaseQuantity);
		event.setTickePrice(tickePrice);
		event.setStartOfSales(startOfSales);
		event.setEndOfSales(endOfSales);
		event.setMinPurchaseQuantity(minPurchaseQuantity);
		event.setMaxPurchaseQuantity(maxPurchaseQuantity);
		event.setEventStatus(eventStatus);
		event.setMap(mapEntity);
		return service.create(event);
	}
	
	@PutMapping(
		consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  },
		produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML  })
	@Operation(summary = "Updates a Event",
		description = "Updates a Event by passing in a JSON, XML or YML representation of the event!",
		tags = {"Events"},
		responses = {
			@ApiResponse(description = "Updated", responseCode = "200",
				content = @Content(schema = @Schema(implementation = EventVO.class))
			),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
		}
	)
	public EventVO update(@RequestBody EventVO event) {
		return service.update(event);
	}
	
	/*@DeleteMapping(value = "/{id}")
	@Operation(summary = "Deletes a Event",
		description = "Deletes a Event by passing in a JSON, XML or YML representation of the event!",
		tags = {"Events"},
		responses = {
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
		}
	)
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}*/
}