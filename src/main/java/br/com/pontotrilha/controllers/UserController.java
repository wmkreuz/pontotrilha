package br.com.pontotrilha.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pontotrilha.data.vo.v1.UserVO;
import br.com.pontotrilha.services.UserServices;
import br.com.pontotrilha.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/user/v1")
@Tag(name = "Users", description = "Endpoints for Managing Users")
public class UserController {

    @Autowired
    private UserServices service;

    @GetMapping(
                 produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                              MediaType.APPLICATION_YML })
    @Operation(summary = "Finds all Users", description = "Finds all Users",
        tags = {"Users"},
        responses = {
            @ApiResponse(description = "Sucess", responseCode = "200", 
                content = {
                    @Content(
                        mediaType = "application/json",
                        array = @ArraySchema(schema = @Schema(implementation = UserVO.class))
                    )
                }),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
        })
    public List<UserVO> findAll() {
        return service.findAll();
    }

    
    @GetMapping(value = "/{id}", 
                 produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                              MediaType.APPLICATION_YML })
    @Operation(summary = "Finds a User", description = "Finds a User",
        tags = {"Users"},
        responses = {
            @ApiResponse(description = "Sucess", responseCode = "200", 
                content = @Content(schema = @Schema(implementation = UserVO.class))
            ),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
        })
    public UserVO findById(@PathVariable(value = "id") Long id) {
        return service.findById(id);
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                              MediaType.APPLICATION_YML }, 
                 produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                              MediaType.APPLICATION_YML })
    @Operation(summary = "Adds a new User", 
        description = "Adds a new User by passing in a JSON, XML or YML representation of the user!",
        tags = {"Users"},
        responses = {
            @ApiResponse(description = "Sucess", responseCode = "200", 
                content = @Content(schema = @Schema(implementation = UserVO.class))
            ),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
        })
    public UserVO create(@RequestBody UserVO user) {
        return service.create(user);
    }

    @PutMapping(consumes = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                             MediaType.APPLICATION_YML }, 
                produces = { MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
                             MediaType.APPLICATION_YML })
    @Operation(summary = "Updates a User", 
        description = "Updates a User by passing in a JSON, XML or YML representation of the user!",
        tags = {"Users"},
        responses = {
            @ApiResponse(description = "Updated", responseCode = "200", 
                content = @Content(schema = @Schema(implementation = UserVO.class))
            ),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
        })
    public UserVO update(@RequestBody UserVO user) {
        return service.update(user);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Deletes a User", 
        description = "Deletes a User by passing in a JSON, XML or YML representation of the user!",
        tags = {"Users"},
        responses = {
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
        })
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}