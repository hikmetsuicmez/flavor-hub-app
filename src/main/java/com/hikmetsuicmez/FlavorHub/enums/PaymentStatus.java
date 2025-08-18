package com.hikmetsuicmez.FlavorHub.enums;

public enum PaymentStatus {
    PENDING, // Ödeme beklemede
    PROCESSING,
    COMPLETED, // Ödeme tamamlandı
    FAILED, // Ödeme başarısız oldu
    REFUNDED, // Ödeme iade edildi
}
