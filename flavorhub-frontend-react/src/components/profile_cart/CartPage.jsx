import { useEffect, useState } from "react";
import ApiService from "../../services/ApiService";
import { useError } from "../common/ErrorDisplay";
import { useNavigate } from "react-router-dom";

const CartPage = () => {

    const [cart, setCart] = useState(null);
    const navigate = useNavigate();
    const { ErrorDisplay, SuccessDisplay, showError, showSuccess } = useError();


    const [message, setMessage] = useState(null);

    // Helper function to calculate item subtotal
    const calculateItemSubtotal = (item) => {
        return (item.menu.price * item.quantity) || 0;
    };

    // Helper function to calculate total cart amount
    const calculateCartTotal = () => {
        if (!cart || !cart.cartItems) return 0;
        return cart.cartItems.reduce((total, item) => total + calculateItemSubtotal(item), 0);
    };

    const fetchCart = async () => {
        try {
            const response = await ApiService.getCart();
            if (response.statusCode === 200) {
                setCart(response.data);
            } else {
                showError(response.message);
            }

        } catch (error) {
            showError(error.response?.data?.message || error.message);

        }
    };

    useEffect(() => {
        fetchCart();
    }, []);

    const handleIncrement = async (menuId) => {
        try {
            const response = await ApiService.incrementItem(menuId);
            if (response.statusCode === 200) {
                fetchCart();
                // Navbar'ı güncellemek için custom event gönder
                window.dispatchEvent(new CustomEvent('cartUpdated'));
            }
        } catch (error) {
            showError(error.response?.data?.message || error.message);
        }
    }

    const handleDecrement = async (menuId) => {
        try {
            const response = await ApiService.decrementItem(menuId);
            if (response.statusCode === 200) {
                fetchCart();
                // Navbar'ı güncellemek için custom event gönder
                window.dispatchEvent(new CustomEvent('cartUpdated'));
            }
        } catch (error) {
            showError(error.response?.data?.message || error.message);
        }
    }

    const handleRemove = async (cartItemId) => {
        try {
            const response = await ApiService.removeItem(cartItemId);
            if (response.statusCode === 200) {
                fetchCart();
                // Navbar'ı güncellemek için custom event gönder
                window.dispatchEvent(new CustomEvent('cartUpdated'));
            }
        } catch (error) {
            showError(error.response?.data?.message || error.message);
        }
    }

    const handleCheckout = async () => {

        try {
            const response = await ApiService.createOrder();
            if (response.statusCode === 200) {
                setMessage(response.message);
                // Navbar'ı güncellemek için custom event gönder
                window.dispatchEvent(new CustomEvent('cartUpdated'));
                setTimeout(() => {
                    setMessage(null);
                    fetchCart();
                    navigate("/my-order-history");
                }, 3000);
            }
        } catch (error) {
            showError(error.response?.data?.message || error.message);
        }
    }

    if (!cart || cart.cartItems.length === 0) {
        return (
            <div className="cart-container empty-cart-container">
                <div className="empty-cart">
                    <h2>Sepetiniz boş.</h2>
                    <p>Menüleri keşfedin ve sepetinize ekleyin.</p>
                    <button className="browse-btn" onClick={() => navigate("/menu")}>
                        Keşfet
                    </button>
                </div>
            </div>
        );
    }

    return (

        <div className="cart-container">
            <ErrorDisplay />
            <SuccessDisplay />

            {message && (
                <p className="success-message">{message}</p>
            )}

            <h1 className="cart-title">Sepetim</h1>

            <div className="cart-items">
                {cart.cartItems.map((item) => (
                    <div key={item.id} className="cart-item">
                        <div className="item-image-container">
                            <img
                                src={item.menu.imageUrl}
                                alt={item.menu.name}
                                className="item-image"
                            />
                        </div>
                        <div className="item-details">
                            <h2 className="item-name">{item.menu.name}</h2>
                            <p className="item-description">{item.menu.description}</p>
                            <p className="item-price">Fiyat: {
                                typeof item.menu.price === 'string'
                                    ? parseFloat(item.menu.price.replace(/[^\d.,]/g, '')).toFixed(2)
                                    : (item.menu.price || 0).toFixed(2)
                            } TL</p>

                            <div className="quantity-controls">
                                <button
                                    onClick={() => handleDecrement(item.menu.id)}
                                    disabled={item.quantity <= 1}
                                    className="quantity-btn"
                                >
                                    -
                                </button>
                                <span className="quantity">{item.quantity}</span>
                                <button
                                    onClick={() => handleIncrement(item.menu.id)}
                                    className="quantity-btn"
                                >
                                    +
                                </button>
                            </div>
                        </div>
                        <div className="item-subtotal">
                            <p className="item-subtotal-price">
                                {calculateItemSubtotal(item).toFixed(2)} TL
                            </p>
                            <button
                                onClick={() => handleRemove(item.id)}
                                className="remove-btn"
                            >
                                Sepetten Çıkar
                            </button>
                        </div>
                    </div>
                ))}
            </div>

            <div className="cart-summary">
                <div className="summary-row">
                    <span>Toplam Fiyat:</span>
                    <span>{calculateCartTotal().toFixed(2)} TL</span>
                </div>

                <div className="summary-row total">
                    <span>Toplam:</span>
                    <span>{calculateCartTotal().toFixed(2)} TL</span>
                </div>

                <button
                    onClick={handleCheckout}
                    className="checkout-btn"
                >
                    Sipariş Oluştur
                </button>
            </div>
        </div>
    );
}

export default CartPage;