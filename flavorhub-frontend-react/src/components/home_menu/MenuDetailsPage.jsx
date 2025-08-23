import { useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import ApiService from "../../services/ApiService";
import { useError } from "../common/ErrorDisplay";

const MenuDetailsPage = () => {

    const { id } = useParams();
    const navigate = useNavigate();
    const [menu, setMenu] = useState(null);
    const [averageRating, setAverageRating] = useState(0);
    const [quantity, setQuantity] = useState(1);
    const [cartSuccess, setCartSuccess] = useState(false);

    const isAuthenticated = ApiService.isAuthenticated();
    const { ErrorDisplay, SuccessDisplay, showError, showSuccess } = useError();

    useEffect(() => {
        const fetchMenuDetails = async () => {
            try {

                const response = await ApiService.getMenuById(id);

                if (response.statusCode === 200) {
                    setMenu(response.data);

                    // Ortalama puanı hesapla
                    const ratingResponse = await ApiService.getMenuAverageOverallReview(id);

                    if (ratingResponse.statusCode === 200) {
                        setAverageRating(ratingResponse.data);
                    }

                } else {
                    showError(response.message);
                }

            } catch (error) {
                showError(error.response?.data?.message || error.message);
            }
        }
        fetchMenuDetails();
    }, [id]);

    const handleBackMenu = () => {
        navigate(-1); // Bir önceki sayfaya dön
    }

    const handleAddToCart = async () => {
        handleCheckout();
        setCartSuccess(false);

        try {
            const response = await ApiService.addToCart({
                menuId: menu.id,
                quantity: quantity,
            });

            if (response.statusCode === 200) {
                setCartSuccess(true);
                setTimeout(() => {
                    setCartSuccess(false);
                }, 3000);
            }

        } catch (error) {
            showError(error.response?.data?.message || error.message);
        }
    }

    const incrementQuantity = () => {
        setQuantity(prev => prev + 1);
    }

    const decrementQuantity = () => {
        setQuantity(prev => prev > 1 ? prev - 1 : 1);
    }

    const handleCheckout = () => {
        if (!isAuthenticated) {
            showError("Devam etmek için giriş yapınız, Eğer hesabınız yoksa kayıt olunuz.");
            return;
        }
    }

    if (!menu) {
        return (
            <div className="menu-details-not-found">
                <p>Bu menü bulunamadı.</p>
                <button className="back-button" onClick={handleBackMenu}>
                    Geri
                </button>
            </div>
        );
    }

    return (
        <div className="menu-details-container">
            <ErrorDisplay />
            <SuccessDisplay />

            <button onClick={handleBackMenu} className="back-button">
                &larr; Menüye Geri Dön
            </button>

            <div className="menu-item-header">
                <div className="menu-item-image-container">
                    <img src={menu.imageUrl} alt={menu.name} className="menu-item-image-detail" />
                </div>

                <div className="menu-item-info">
                    <h1 className="menu-item-name">{menu.name}</h1>
                    <p className="menu-item-description">{menu.description}</p>

                    <div className="menu-item-price-rating">
                        <span className="price">
                            {typeof menu.price === 'string'
                                ? parseFloat(menu.price.replace(/[^\d.,]/g, '')).toFixed(2)
                                : (menu.price || 0).toFixed(2)
                            } TL
                        </span>
                        <div className="rating">
                            <span className="rating-value">{averageRating.toFixed(1)}</span>
                            <span className="rating-star">★</span>
                            <span className="rating-count">({menu.reviews?.length || 0} yorumlar)</span>
                        </div>
                    </div>


                    <div className="add-to-cart-section">
                        <div className="quantity-selector">
                            <button onClick={decrementQuantity} className="quantity-btn" disabled={quantity <= 1}>
                                -
                            </button>

                            <span className="quantity">{quantity}</span>

                            <button onClick={incrementQuantity} className="quantity-btn">
                                +
                            </button>

                        </div>
                        <button onClick={handleAddToCart} className="add-to-cart-btn">
                            Sepete Ekle
                        </button>

                        {cartSuccess && (
                            <div className="cart-success-message">
                                Sepete eklendi!
                            </div>
                        )}

                    </div>
                </div>
            </div>
            <div className="reviews-section">
                <h2 className="reviews-title">Müşteri Yorumları</h2>

                {menu.reviews && menu.reviews.length > 0 ? (
                    <div className="reviews-list">
                        {menu.reviews.map((review) => (
                            <div className="review-card" key={review.id}>
                                <div className="review-header">
                                    <span className="review-user">{review.userName.toUpperCase()}</span>
                                    <span className="review-date">
                                        {new Date(review.createdAt).toLocaleDateString()}
                                    </span>
                                </div>
                                <div className="review-rating">
                                    <span className="rating-value">{review.rating}/10 {'★'.repeat(review.rating)}{'☆'.repeat(10 - review.rating)}</span>

                                </div>
                                <p className="review-comment">{review.comment}</p>
                            </div>
                        ))}

                    </div>
                ) : (
                    <p className="no-reviews">Henüz yorum yapılmadı. İlk yorumu siz yapın!</p>
                )}
            </div>
        </div>
    )

}

export default MenuDetailsPage;