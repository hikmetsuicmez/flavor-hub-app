package com.hikmetsuicmez.FlavorHub.contants;

public class ApiEndpoints {

    private ApiEndpoints() {
    }

    public static final class Auth {
        private Auth() {
        }

        public static final String BASE = "/api/v1/auth";
        public static final String LOGIN = "/login"; // Giriş yapma
        public static final String REGISTER = "/register"; // Kayıt olma
    }

    public static final class User {
        private User() {
        }

        public static final String BASE = "/api/v1/users";
        public static final String GET_ALL = "/get-all"; // Tüm kullanıcıları getir
        public static final String UPDATE = "/update"; // Kullanıcıyı güncelle
        public static final String DELETE = "/deactive"; // Kullanıcıyı sil
        public static final String GET_OWN_ACCOUNT = "/account"; // Kendi hesabını getir
    }

    public static final class Cart {
        private Cart() {
        }

        public static final String BASE = "/api/v1/carts";
        public static final String ADD_ITEM = "/items"; // Sepete ürün ekleme
        public static final String INCREMENT_ITEM = "/items/increment/{menuId}"; // Ürünü artırma
        public static final String DECREMENT_ITEM = "/items/decrement/{menuId}"; // Ürünü azaltma
        public static final String REMOVE_ITEM = "/items/{cartItemId}"; // Sepetten ürün kaldırma
        public static final String GET_CART = ""; // Sepeti görüntüleme
        public static final String CLEAR_CART = ""; // Sepeti temizleme
    }

    public static final class Menu {
        private Menu() {
        }

        public static final String BASE = "/api/v1/menus";
        public static final String GET_ALL = "/get-all"; // Tüm menüleri getir
        public static final String GET_BY_ID = "/{menuId}"; // Menü ID ile getir
        public static final String CREATE = ""; // Menü oluşturma
        public static final String UPDATE = ""; // Menü güncelleme
        public static final String DELETE = "/{menuId}"; // Menü silme
    }

    public static final class Order {
        private Order() {
        }

        public static final String BASE = "/api/v1/orders";
        public static final String CHECKOUT = "/checkout"; // Sepetten sipariş oluşturma
        public static final String GET_BY_ID = "/{orderId}"; // Siparişi ID ile getir
        public static final String GET_MY_ORDERS = "/me"; // Kullanıcının siparişlerini getir
        public static final String GET_ORDER_ITEM_BY_ID = "/order-item/{orderItemId}"; // Sipariş öğesini ID ile getir
        public static final String GET_ALL_ORDERS = "/all"; // Tüm siparişleri getir
        public static final String UPDATE_ORDER_STATUS = "/update"; // Sipariş durumunu güncelleme
        public static final String GET_UNIQUE_CUSTOMERS_COUNT = "/unique-customers"; // Benzersiz müşteri sayısını getir
    }

    public static final class Review {
        private Review() {
        }

        public static final String BASE = "/api/v1/reviews";
        public static final String CREATE = ""; // Yorum oluşturma
        public static final String GET_BY_MENU_ID = "/menu-item/{menuId}"; // Menü ID ile yorumları getir
        public static final String GET_AVERAGE_RATING = "/menu-item/{menuId}/average-rating"; // Menü ID ile ortalama puanı getir
    }

    public static final class Role {
        private Role() {
        }

        public static final String BASE = "/api/v1/roles";
        public static final String CREATE = ""; // Rol oluşturma
        public static final String UPDATE = ""; // Rol güncelleme
        public static final String GET_ALL = ""; // Tüm rolleri getir
        public static final String DELETE = "/{roleId}"; // Rol silme
    }

    public static final class Category {
        private Category() {
        }

        public static final String BASE = "/api/v1/categories";
        public static final String CREATE = ""; // Kategori oluşturma
        public static final String UPDATE = ""; // Kategori güncelleme
        public static final String GET_BY_ID = "/{categoryId}"; // Kategori ID ile getir
        public static final String GET_ALL = "get-all"; // Tüm kategorileri getir
        public static final String DELETE = "/{categoryId}"; // Kategori silme
    }

    public static final class Payment {
        private Payment() {
        }

        public static final String BASE = "/api/v1/payments";
        public static final String INITIALIZE_PAYMENT = "/pay"; // Ödeme başlatma
        public static final String UPDATE_PAYMENT = "/update"; // Ödeme güncelleme
        public static final String GET_ALL_PAYMENTS = "/all"; // Tüm ödemeleri getir
        public static final String GET_PAYMENT_BY_ID = "/{paymentId}"; // Ödeme ID ile getir
    }
}
