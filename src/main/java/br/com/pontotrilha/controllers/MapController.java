package br.com.pontotrilha.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.pontotrilha.data.vo.v1.MapVO;
import br.com.pontotrilha.services.MapServices;
import br.com.pontotrilha.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/map/v1")
@Tag(name = "Maps", description = "Endpoint to manage maps")
public class MapController {

	@Autowired
	private MapServices service;

	@Autowired
	private FileController fileController;

	@CrossOrigin(origins = { "http://localhost:8080", "https://pontotrilha.com.br" })
	@GetMapping(produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML })
	@Operation(summary = "Find all maps", description = "Find all maps", tags = { "Maps" }, responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = MapVO.class)))
			}),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	})
	public List<MapVO> findAll() {
		return service.findAll();
	}

	@CrossOrigin(origins = { "http://localhost:8080", "https://pontotrilha.com.br" })
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML })
	@Operation(summary = "Find a map", description = "Find a map", tags = { "Maps" }, responses = {
			@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = MapVO.class))),
			@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	})
	public MapVO findById(@PathVariable(value = "id") Long id) {
		return service.findById(id);
	}

	@CrossOrigin(origins = { "http://localhost:8080", "https://pontotrilha.com.br" })
	@PostMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML,
			MediaType.APPLICATION_OCTET_STREAM, MediaType.MULTIPART_FORM_DATA }, produces = {
					MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML,
					MediaType.APPLICATION_OCTET_STREAM, MediaType.MULTIPART_FORM_DATA })
	@Operation(summary = "Add a new map", description = "Add a new map!", tags = {
			"Maps" }, responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = MapVO.class))),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			})
	public MapVO create(MultipartFile file, @RequestBody MapVO map) {
		String fileId = fileController.uploadFile(file);
		map.setIdGoogle(fileId);
		return service.create(map);
	}

	@CrossOrigin(origins = { "http://localhost:8080", "https://pontotrilha.com.br" })
	@PutMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
			MediaType.APPLICATION_YML }, produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
					MediaType.APPLICATION_YML })
	@Operation(summary = "Updating a map", description = "Updating an map!", tags = {
			"Maps" }, responses = {
					@ApiResponse(description = "Updated", responseCode = "200", content = @Content(schema = @Schema(implementation = MapVO.class))),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			})
	public MapVO update(MultipartFile file, @RequestBody MapVO map) {
		fileController.updateFile(map.getIdGoogle(), file);
		return service.update(map);
	}
}