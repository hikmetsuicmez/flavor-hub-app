package com.hikmetsuicmez.FlavorHub.docs;

import com.hikmetsuicmez.FlavorHub.enums.OrderStatus;
import com.hikmetsuicmez.FlavorHub.order.dtos.OrderDTO;
import com.hikmetsuicmez.FlavorHub.order.dtos.OrderItemDTO;
import com.hikmetsuicmez.FlavorHub.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Order API", description = "Endpoints for managing orders in the FlavorHub application")
public interface OrderApiDocs {

    @Operation(summary = "Checkout", description = "Place an order from the user's cart. This endpoint is accessible only to customers.")
    @ApiResponse(responseCode = "200", description = "Order placed successfully", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "400", description = "Bad request, possibly due to empty cart or invalid data", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized, user must be logged in as a customer", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error, unexpected issues during order processing", content = @Content(schema = @Schema(implementation = Response.class)))
    ResponseEntity<Response<?>> checkout();

    @Operation(summary = "Get Order by ID", description = "Retrieve an order by its ID. Accessible to all authenticated users.")
    @ApiResponse(responseCode = "200", description = "Order retrieved successfully", content = @Content(schema = @Schema(implementation = OrderDTO.class)))
    @ApiResponse(responseCode = "404", description = "Order not found", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized, user must be logged in", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error, unexpected issues during retrieval", content = @Content(schema = @Schema(implementation = Response.class)))
    @Parameter(description = "ID of the order to retrieve", required = true)
    ResponseEntity<Response<OrderDTO>> getOrderById(@Parameter Long orderId);

    @Operation(summary = "Get My Orders", description = "Retrieve all orders placed by the authenticated user. Accessible only to customers.")
    @ApiResponse(responseCode = "200", description = "Orders retrieved successfully", content = @Content(schema = @Schema(implementation = List.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized, user must be logged in as a customer", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error, unexpected issues during retrieval", content = @Content(schema = @Schema(implementation = Response.class)))
    @Parameter(description = "No parameters required for this endpoint")
    ResponseEntity<Response<List<OrderDTO>>> getMyOrders();

    @Operation(summary = "Get Order Item by ID", description = "Retrieve a specific order item by its ID. Accessible to all authenticated users.")
    @ApiResponse(responseCode = "200", description = "Order item retrieved successfully", content = @Content(schema = @Schema(implementation = OrderItemDTO.class)))
    @ApiResponse(responseCode = "404", description = "Order item not found", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized, user must be logged in", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error, unexpected issues during retrieval", content = @Content(schema = @Schema(implementation = Response.class)))
    @Parameter(description = "ID of the order item to retrieve", required = true)
    ResponseEntity<Response<OrderItemDTO>> getOrderItemById(@Parameter Long orderItemId);

    @Operation(summary = "Get All Orders", description = "Retrieve all orders in the system. Accessible only to administrators.")
    @ApiResponse(responseCode = "200", description = "Orders retrieved successfully", content = @Content(schema = @Schema(implementation = Page.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized, user must be logged in as an administrator", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error, unexpected issues during retrieval", content = @Content(schema = @Schema(implementation = Response.class)))
    @Parameter(description = "Order status to filter by (optional)", required = false)
    ResponseEntity<Response<Page<OrderDTO>>> getAllOrders(@Parameter OrderStatus orderStatus, @Parameter int page, @Parameter int size);

    @Operation(summary = "Update Order Status", description = "Update the status of an existing order. Accessible only to administrators.")
    @ApiResponse(responseCode = "200", description = "Order status updated successfully", content = @Content(schema = @Schema(implementation = OrderDTO.class)))
    @ApiResponse(responseCode = "400", description = "Bad request, possibly due to invalid order data", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized, user must be logged in as an administrator", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Order not found", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error, unexpected issues during update", content = @Content(schema = @Schema(implementation = Response.class)))
    ResponseEntity<Response<OrderDTO>> updateOrderStatus(@Parameter OrderDTO orderDTO);

    @Operation(summary = "Get Unique Customers Count", description = "Retrieve the count of unique customers who have placed orders. Accessible only to administrators.")
    @ApiResponse(responseCode = "200", description = "Unique customers count retrieved successfully", content = @Content(schema = @Schema(implementation = Long.class)))
    @ApiResponse(responseCode = "401", description = "Unauthorized, user must be logged in as an administrator", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error, unexpected issues during count retrieval", content = @Content(schema = @Schema(implementation = Response.class)))
    ResponseEntity<Response<Long>> getUniqueCustomersCount();

}
