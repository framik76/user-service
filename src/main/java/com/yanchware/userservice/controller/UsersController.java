package com.yanchware.userservice.controller;

import com.yanchware.userservice.entity.User;
import com.yanchware.userservice.model.UserModelRequest;
import com.yanchware.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class UsersController {

    private final UserService userService;

    @Operation(summary = "Create user", description = "Create new user", tags = {"User"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "502", description = "Bad gateway")})
    @PostMapping(value = "/user", produces = "application/json")
    public ResponseEntity<User> postUser(@Valid @RequestBody UserModelRequest userModelRequest) {
        try {
            log.info("start postUser with payload {} ", userModelRequest);
            var user = userService.createUser(userModelRequest);
            log.info("end postUser with payload {} ", userModelRequest);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }

    }

    @Operation(summary = "Get users", description = "Get all users", tags = {"User"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "502", description = "Bad gateway")})
    @GetMapping(value = "/users", produces = "application/json")
	public ResponseEntity<List<User>> getUsers() {
		var users = userService.getAllUsers();
        return ResponseEntity.ok(users);
	}
}
