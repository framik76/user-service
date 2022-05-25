package com.yanchware.userservice.controller;

import com.yanchware.userservice.entity.User;
import com.yanchware.userservice.model.SubscriptionModelRequest;
import com.yanchware.userservice.model.SubscriptionResponse;
import com.yanchware.userservice.model.UserModelRequest;
import com.yanchware.userservice.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jnr.posix.LibC;
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
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @Operation(summary = "Create subscription", description = "Create new subscription", tags = {"Subscription"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "502", description = "Bad gateway")})
    @PostMapping(value = "/subscription", produces = "application/json")
    public ResponseEntity<SubscriptionResponse> postSubscription(@Valid @RequestBody SubscriptionModelRequest subscriptionModelRequest) {
        try {
            log.info("start postSubscription with payload {} ", subscriptionModelRequest);
            var subscriptionResponse = subscriptionService.createSubscription(subscriptionModelRequest);
            log.info("end postSubscription with payload {} ", subscriptionModelRequest);
            return ResponseEntity.ok(subscriptionResponse);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
    }

    @Operation(summary = "Get subscriptions", description = "Get subscriptions list", tags = {"Subscription"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "502", description = "Bad gateway")})
    @GetMapping(value = "/subscriptions", produces = "application/json")
    public ResponseEntity<List<SubscriptionResponse>> getAllSubscriptions() {
        return ResponseEntity.ok(subscriptionService.getAllSubscriptions());
    }

}
