import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import ApiService from "../../services/ApiService";
import { useError } from "../common/ErrorDisplay";


const HomePage = () => {

    const [categories, setCategories] = useState([]);
    const navigate = useNavigate();
    const { ErrorDisplay, SuccessDisplay, showError, showSuccess } = useError();

    useEffect(() => {
        const fetchCategories = async () => {
            try {
                const response = await ApiService.getAllCategories();
                if (response.statusCode === 200) {
                    setCategories(response.data);
                } else {
                    showError(response.message);
                }
            } catch (error) {
                showError(error.response?.data?.message || error.message);
            }
        };

        fetchCategories();
    }, []);

    const handleCategoryClick = (categoryId) => {
        navigate(`/menu?category=${categoryId}`);
    }

    return (
        <div className="home-page">
            <ErrorDisplay />
            <SuccessDisplay />

            <header className="home-hero-section">
                <div className="home-hero-content">
                    <h1 className="home-hero-title">Lezzetli Yemekleri Keşfet</h1>
                    <p className="home-hero-subtitle">En güncel yemekleri keşfet, en lezzetli yemekleri deneyimle!</p>
                    <button className="home-explore-button" onClick={() => navigate("/menu")}>
                        Keşfet
                    </button>
                </div>
            </header>

            <section className="home-featured-categories">
                <h2 className="home-section-title">Öne Çıkan Kategoriler</h2>
                <div className="home-category-carousel">
                    {categories.map((category) => (
                        <div
                            key={category.id}
                            className="home-category-card"
                            onClick={() => handleCategoryClick(category.id)}>
                            <h3 className="home-category-name">{category.name}</h3>
                            <p className="home-category-description">{category.description.slice(0, 100)}...</p>
                        </div>
                    ))
                    }
                </div>
            </section>

            <section className="home-call-to-action">
                <div className="home-cta-content">
                    <h2 className="home-cta-title">Sipariş vermeye hazır mısın?</h2>
                    <p className="home-cta-text">Menümüze göz at ve sipariş vermeye başla!</p>
                    <button
                        className="home-order-now-button"
                        onClick={() => navigate("/menu")}
                    >
                        Sipariş Ver
                    </button>
                </div>
            </section>

        </div>
    )
}

export default HomePage;