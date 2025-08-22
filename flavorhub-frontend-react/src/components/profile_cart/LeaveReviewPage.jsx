import { useEffect, useState } from "react";
import ApiService from "../../services/ApiService";
import { useNavigate, useLocation } from "react-router-dom";
import { useError } from "../common/ErrorDisplay";

const LeaveReviewPage = () => {

    const location = useLocation();
    const urlParams = new URLSearchParams(location.search);
    const orderId = urlParams.get('orderId');
    const menuId = urlParams.get('menuId');

    const navigate = useNavigate();
    const { ErrorDisplay, SuccessDisplay, showError, showSuccess } = useError();

    const [menu, setMenu] = useState(null);
    const [rating, setRating] = useState(0);
    const [comment, setComment] = useState('');
    const [success, setSuccess] = useState(false);

    useEffect(() => {

        const fetchMenu = async () => {
            try {
                const response = await ApiService.getMenuById(menuId);
                if (response.statusCode === 200) {
                    setMenu(response.data);
                }

            } catch (error) {
                showError(error.response?.data?.message || error.message);

            }
        }

        if (menuId) {
            fetchMenu();
        } else {
            showError("Menü bulunamadı.");
        }

    }, [menuId]);


    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const reviewData = {

                menuId,
                orderId,
                rating,
                comment
            }
            const response = await ApiService.addReview(reviewData);
            if (response.statusCode === 200) {
                setSuccess(true);
                setTimeout(() => {
                    navigate(-1);
                }, 2000);
            }
        } catch (error) {
            showError(error.response?.data?.message || error.message);
        }
    }

    return (

        <div className="leave-review-container">
            <div className="review-header">
                <h1>Yorum Bırak</h1>
                {menu && (
                    <div className="menu-item-info">
                        <img src={menu.imageUrl} alt={menu.name} className="menu-item-image-review" />
                        <h2 className="menu-item-name">{menu.name}</h2>
                    </div>
                )}
            </div>

            <form onSubmit={handleSubmit} className="review-form">

                <div className="rating-section">
                    <label>Puan</label>
                    <div className="star-rating">
                        {[1, 2, 3, 4, 5, 6, 7, 8, 9, 10].map((star) => (
                            <span
                                key={star}
                                className={`star ${rating >= star ? 'filled' : ''}`}
                                onClick={() => setRating(star)}
                                onMouseEnter={() => document.activeElement === document.body && setRating(star)}
                            >
                                ★
                            </span>
                        ))}
                    </div>
                    <div className="rating-value">{rating}/10</div>
                </div>
                <div className="comment-section">
                    <label htmlFor="comment">Yorumunuz: </label>
                    <textarea
                        id="comment"
                        value={comment}
                        onChange={(e) => setComment(e.target.value)}
                        placeholder="Yorumunuzu buraya yazınız..."
                        required
                    />
                </div>

                <ErrorDisplay />
                <SuccessDisplay />

                {success && (
                    <div className="form-success">
                        Yorumunuz başarıyla gönderildi! Geri yönlendiriliyorsunuz...
                    </div>
                )}

                <div className="form-actions">
                    <button
                        type="button"
                        className="cancel-button"
                        onClick={() => navigate(-1)}
                    >
                        İptal
                    </button>
                    <button
                        type="submit"
                        className="submit-button"
                        disabled={rating === 0}
                    >
                        Yorumu Gönder
                    </button>
                </div>
            </form>
        </div>
    )
}

export default LeaveReviewPage;