import axios from "axios";

export default class ApiService {

    static BASE_URL = "http://localhost:6161/api/v1";

    static saveToken(token) {
        localStorage.setItem("token", token);
    }

    static getToken() {
        return localStorage.getItem("token");
    }

    // save role
    static saveRole(roles) {
        localStorage.setItem("roles", JSON.stringify(roles));
    }

    // get roles from local storage
    static getRoles() {
        const roles = localStorage.getItem("roles");
        return roles ? JSON.parse(roles) : null;
    }

    // check if the user has a specific role
    static hadRole(role) {
        const roles = this.getRoles();
        return roles ? roles.includes(role) : false;
    }

    static isAdmin() {
        return this.hadRole('ADMIN');
    }


    static isCustomer() {
        return this.hadRole('CUSTOMER');
    }


    static isDeliveryPerson() {
        return this.hadRole('DELIVERY');
    }

    static logout() {
        localStorage.removeItem("token");
        localStorage.removeItem("roles");
    }

    static isAuthenticated() {
        const token = this.getToken();
        return !!token;
    }

    static getHeader() {
        const token = this.getToken();
        return {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json"
        }
    }


    //REGISTER USER
    static async registerUser(registrationData) {
        const response = await axios.post(`${this.BASE_URL}/auth/register`, registrationData);
        return response.data;
    }

    //LOGIN USER
    static async loginUser(loginData) {
        const response = await axios.post(`${this.BASE_URL}/auth/login`, loginData);
        return response.data;
    }

    /**USERS PROFILE MANAGEMENT SECTION */

    static async getAllUsers() {
        const response = await axios.get(`${this.BASE_URL}`, {
            headers: this.getHeader()
        })
        return response.data;
    }


    static async myProfile() {
        const response = await axios.get(`${this.BASE_URL}/users/account`, {
            headers: this.getHeader()
        })
        return response.data;
    }

    static async updateProfile(formData) {
        const response = await axios.put(`${this.BASE_URL}/users/update`, formData, {
            headers: {
                ...this.getHeader(),
                "Content-Type": "multipart/form-data"
            }
        })
        return response.data;
    }

    static async deactivateAccount() {
        const response = await axios.delete(`${this.BASE_URL}/users/deactive`, {
            headers: this.getHeader()
        })
        return response.data;
    }


    // CART MANAGEMENT SECTION

    static async addToCart(cartData) {
        const response = await axios.post(`${this.BASE_URL}/carts/items`, cartData, {
            headers: this.getHeader()
        })
        return response.data;
    }

    static async incrementCartItem(menuId) {
        const response = await axios.post(`${this.BASE_URL}/carts/items/increment/${menuId}`, {
            headers: this.getHeader()
        })
        return response.data;
    }

    static async decrementCartItem(menuId) {
        const response = await axios.post(`${this.BASE_URL}/carts/items/decrement/${menuId}`, {
            headers: this.getHeader()
        })
        return response.data;
    }

    static async removeCartItem(cartItemId) {
        const response = await axios.delete(`${this.BASE_URL}/carts/items/${cartItemId}`, {
            headers: this.getHeader()
        })
        return response.data;
    }

    static async getCart() {
        const response = await axios.get(`${this.BASE_URL}/carts`, {
            headers: this.getHeader()
        })
        return response.data;
    }

    static async clearCart() {
        const response = await axios.delete(`${this.BASE_URL}/carts`, {
            headers: this.getHeader()
        })
        return response.data;
    }


    // MENU MANAGEMENT SECTION

    static async getAllMenus(categoryId, search) {
        const response = await axios.get(`${this.BASE_URL}/menus/get-all`, {
            params: {
                categoryId,
                search
            },
            headers: this.getHeader()
        })
        return response.data;
    }

    static async getAllMenuByCategoryId(categoryId) {
        const response = await axios.get(`${this.BASE_URL}/menus/get-all`, {
            params: {
                categoryId: categoryId
            },
            headers: this.getHeader()
        })
        return response.data;
    }

    static async getMenuById(menuId) {
        const response = await axios.get(`${this.BASE_URL}/menus/${menuId}`, {
            headers: this.getHeader()
        })
        return response.data;
    }

    static async deleteMenu(menuId) {
        const response = await axios.delete(`${this.BASE_URL}/menus/${menuId}`, {
            headers: this.getHeader()
        })
        return response.data;
    }

    static async addMenu(formData) {
        const response = await axios.post(`${this.BASE_URL}/menus`, formData, {
            headers: {
                ...this.getHeader(),
                "Content-Type": "multipart/form-data"
            }
        })
        return response.data;
    }

    static async updateMenu(formData) {
        const response = await axios.put(`${this.BASE_URL}/menus`, formData, {
            headers: {
                ...this.getHeader(),
                "Content-Type": "multipart/form-data"
            }
        })
        return response.data;
    }

    static async searchMenu(searchTerm) {
        const response = await axios.get(`${this.BASE_URL}/menus`, {
            params: {
                searchTerm: searchTerm
            },
            headers: this.getHeader()
        })
        return response.data;
    }

    // Örnek kullanım için yardımcı metod (opsiyonel)
    static createMenuFormData(menuData, imageFile) {
        const formData = new FormData();

        // Menu verilerini ekle
        Object.keys(menuData).forEach(key => {
            if (key !== 'imageFile') {
                formData.append(key, menuData[key]);
            }
        });

        // Resim dosyasını ekle
        if (imageFile) {
            formData.append('imageFile', imageFile);
        }

        return formData;
    }


    // CATEGORY MANAGEMENT SECTION

    static async createCategory(formData) {
        const response = await axios.post(`${this.BASE_URL}/categories`, formData, {
            headers: this.getHeader()
        });
        return response.data;
    }

    static async updateCategory(formData) {
        const response = await axios.put(`${this.BASE_URL}/categories`, formData, {
            headers: this.getHeader()
        });
        return response.data;
    }

    static async deleteCategory(categoryId) {
        const response = await axios.delete(`${this.BASE_URL}/categories/${categoryId}`, {
            headers: this.getHeader()
        })
        return response.data;
    }

    static async getAllCategories() {
        const response = await axios.get(`${this.BASE_URL}/categories/get-all`, {
            headers: this.getHeader()
        })
        return response.data;
    }

    static async getCategoryById(categoryId) {
        const response = await axios.get(`${this.BASE_URL}/categories/${categoryId}`, {
            headers: this.getHeader()
        })
        return response.data;
    }



    // PAYMENT MANAGEMENT SECTION

    static async initializePayment(formData) {
        const response = await axios.post(`${this.BASE_URL}/payments/pay`, formData, {
            headers: this.getHeader()
        })
        return response.data;
    }

    static async updatePayment(formData) {
        const response = await axios.put(`${this.BASE_URL}/payments/update`, formData, {
            headers: this.getHeader()
        })
        return response.data;
    }

    static async getAllPayments() {
        const response = await axios.get(`${this.BASE_URL}/payments/all`, {
            headers: this.getHeader()
        })
        return response.data;
    }

    static async getPaymentById(paymentId) {
        const response = await axios.get(`${this.BASE_URL}/payments/${paymentId}`, {
            headers: this.getHeader()
        })
        return response.data;
    }



    // REVIEW MANAGEMENT SECTION

    static async addReview(reviewData) {
        const response = await axios.post(`${this.BASE_URL}/reviews`, reviewData, {
            headers: this.getHeader()
        })
        return response.data;
    }

    static async getReviewsByMenuId(menuId) {
        const response = await axios.get(`${this.BASE_URL}/reviews/menu-item/${menuId}`, {
            headers: this.getHeader()
        })
        return response.data;
    }

    static async getAverageRating(menuId) {
        const response = await axios.get(`${this.BASE_URL}/reviews/menu-item/${menuId}/average-rating`, {
            headers: this.getHeader()
        })
        return response.data;
    }



    // ORDER MANAGEMENT SECTION

    static async createOrder() {
        const response = await axios.post(`${this.BASE_URL}/orders/checkout`, {
            headers: this.getHeader()
        })
        return response.data;
    }

    static async getOrderById(orderId) {
        const response = await axios.get(`${this.BASE_URL}/orders/${orderId}`, {
            headers: this.getHeader()
        })
        return response.data;
    }

    static async getMyOrders() {
        const response = await axios.get(`${this.BASE_URL}/orders/me`, {
            headers: this.getHeader()
        })
        return response.data;
    }

    static async getAllOrders() {
        const response = await axios.get(`${this.BASE_URL}/orders/all`, {
            headers: this.getHeader()
        })
        return response.data;
    }

    static async getOrdersByUserId(orderItemId) {
        const response = await axios.get(`${this.BASE_URL}/orders/order-item/${orderItemId}`, {
            headers: this.getHeader()
        })
        return response.data;
    }

    static async getOrdersByStatus(formData) {
        const response = await axios.put(`${this.BASE_URL}/orders/update`, formData, {
            headers: this.getHeader()
        })
        return response.data;
    }

    static async getUniqueCustomersCount() {
        const response = await axios.get(`${this.BASE_URL}/orders/unique-customers`, {
            headers: this.getHeader()
        })
        return response.data;
    }




    // ROLE MANAGEMENT SECTION

    static async createRole(formData) {
        const response = await axios.post(`${this.BASE_URL}/roles`, formData, {
            headers: this.getHeader()
        })
        return response.data;
    }

    static async updateRole(formData) {
        const response = await axios.put(`${this.BASE_URL}/roles`, formData, {
            headers: this.getHeader()
        })
        return response.data;
    }

    static async getAllRoles() {
        const response = await axios.get(`${this.BASE_URL}/roles`, {
            headers: this.getHeader()
        })
        return response.data;
    }

    static async deleteRole(roleId) {
        const response = await axios.delete(`${this.BASE_URL}/roles/${roleId}`, {
            headers: this.getHeader()
        })
        return response.data;
    }
}