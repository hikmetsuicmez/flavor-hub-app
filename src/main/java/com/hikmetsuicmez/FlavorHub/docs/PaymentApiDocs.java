package com.hikmetsuicmez.FlavorHub.docs;

import com.hikmetsuicmez.FlavorHub.payment.dtos.PaymentDTO;
import com.hikmetsuicmez.FlavorHub.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Ödeme API'ları", description = "Ödeme işlemleri için gerekli API'ler")
public interface PaymentApiDocs {


    @Operation(summary = "Ödeme Başlatma", description = "Belirtilen ödeme bilgileri ile yeni bir ödeme başlatır.")
    @ApiResponse(responseCode = "200", description = "Ödeme başarıyla başlatıldı.", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "400", description = "Geçersiz istek.", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Ödeme başlatılamadı, sipariş bulunamadı.", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Sunucu hatası.", content = @Content(schema = @Schema(implementation = Response.class)))
    ResponseEntity<Response<?>> initializePayment(@Parameter PaymentDTO paymentRequest);

    @Operation(summary = "Sipariş için Ödeme Güncelleme", description = "Belirtilen ödeme bilgileri ile siparişin ödemesini günceller.")
    @ApiResponse(responseCode = "200", description = "Ödeme başarıyla güncellendi.", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "400", description = "Geçersiz istek.", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Ödeme güncellenemedi, sipariş bulunamadı.", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Sunucu hatası.", content = @Content(schema = @Schema(implementation = Response.class)))
    void updatePaymentForOrder(@Parameter PaymentDTO paymentDTO);

    @Operation(summary = "Tüm Ödemeleri Getirme", description = "Sistemdeki tüm ödemeleri listeler.")
    @ApiResponse(responseCode = "200", description = "Ödemeler başarıyla listelendi.", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Sunucu hatası.", content = @Content(schema = @Schema(implementation = Response.class)))
    ResponseEntity<Response<List<PaymentDTO>>> getAllPayments();

    @Operation(summary = "Ödeme ID'sine Göre Getirme", description = "Belirtilen ödeme ID'sine göre ödeme bilgilerini getirir.")
    @ApiResponse(responseCode = "200", description = "Ödeme başarıyla getirildi.", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "404", description = "Ödeme bulunamadı.", content = @Content(schema = @Schema(implementation = Response.class)))
    @ApiResponse(responseCode = "500", description = "Sunucu hatası.", content = @Content(schema = @Schema(implementation = Response.class)))
    @Parameter(name = "paymentId", description = "Getirilecek ödeme ID'si", required = true, schema = @Schema(type = "Long"))
    ResponseEntity<Response<PaymentDTO>> getPaymentById(@Parameter Long paymentId);

}
