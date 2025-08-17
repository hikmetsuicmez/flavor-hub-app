package com.hikmetsuicmez.FlavorHub.docs;

import com.hikmetsuicmez.FlavorHub.cart.dtos.CartDTO;
import com.hikmetsuicmez.FlavorHub.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Cart API", description = "Endpoints for managing the shopping cart")
public interface CartApiDocs {

    @Operation(summary = "Add item to cart", description = "Adds a new item to the shopping cart")
    @ApiResponse(responseCode = "200", description = "Item added successfully", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Menu item not found", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Response.class)))
    ResponseEntity<Response<?>> addItemToCart(@Parameter CartDTO cartDTO);

    @Operation(summary = "Increment item quantity", description = "Increments the quantity of an item in the shopping cart")
    @ApiResponse(responseCode = "200", description = "Item quantity incremented successfully", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Menu item not found", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Response.class)))
    @Parameter(description = "ID of the menu item to increment", required = true)
    ResponseEntity<Response<?>> incrementItem(@Parameter Long menuId);

    @Operation(summary = "Decrement item quantity", description = "Decrements the quantity of an item in the shopping cart")
    @ApiResponse(responseCode = "200", description = "Item quantity decremented successfully", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Menu item not found", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Response.class)))
    @Parameter(description = "ID of the menu item to decrement", required = true)
    ResponseEntity<Response<?>> decrementItem(@Parameter Long menuId);

    @Operation(summary = "Remove item from cart", description = "Removes an item from the shopping cart")
    @ApiResponse(responseCode = "200", description = "Item removed successfully", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Cart item not found", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Response.class)))
    @Parameter(description = "ID of the cart item to remove", required = true)
    ResponseEntity<Response<?>> removeItem(@Parameter Long cartItemId);

    @Operation(summary = "Get shopping cart", description = "Retrieves the current shopping cart")
    @ApiResponse(responseCode = "200", description = "Shopping cart retrieved successfully", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Shopping cart not found", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Response.class)))
    @Parameter(description = "No parameters required")
    public ResponseEntity<Response<?>> getShoppingCart();

    @Operation(summary = "Clear shopping cart", description = "Clears all items from the shopping cart")
    @ApiResponse(responseCode = "200", description = "Shopping cart cleared successfully", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Shopping cart not found", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = Response.class)))
    @Parameter(description = "No parameters required")
    public ResponseEntity<Response<?>> clearShoppingCart();
}
