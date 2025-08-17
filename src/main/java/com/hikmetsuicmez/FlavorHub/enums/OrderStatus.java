package com.hikmetsuicmez.FlavorHub.enums;

public enum OrderStatus {
    INITIALIZED,  // başlangıç durumu
    CONFIRMED, // sipariş onaylandı
    ON_THE_WAY, // sipariş yolda
    DELIVERED, // sipariş teslim edildi
    CANCELLED, // sipariş iptal edildi
    FAILED // sipariş başarısız oldu
}
