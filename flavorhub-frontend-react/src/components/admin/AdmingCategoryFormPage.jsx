import { useNavigate, useParams } from "react-router-dom";
import { useError } from "../common/ErrorDisplay";
import { useState, useEffect } from "react";
import ApiService from "../../services/ApiService";



const AdminCategoryFormPage = () => {

    const { categoryId } = useParams();
    const navigate = useNavigate();
    const { ErrorDisplay, SuccessDisplay, showError, showSuccess } = useError();

    const [category, setCategory] = useState({
        name: "",
        description: "",
    });

    useEffect(() => {
        if (categoryId) {
            fetchCategory();
        }
    }, [categoryId]);

    const fetchCategory = async () => {
        try {
            const response = await ApiService.getCategoryById(categoryId);
            if (response.statusCode === 200) {
                setCategory(response.data);
            }
        } catch (error) {
            showError(error.response?.data?.message || error.message);
        }
    }

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setCategory(prev => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            let response;
            if (categoryId) {
                response = await ApiService.updateCategory(category);
            } else {
                response = await ApiService.createCategory(category);
            }
            if (response.statusCode === 200) {
                showSuccess(response.message);
                navigate("/admin/categories");
            }
        } catch (error) {
            showError(error.response?.data?.message || error.message);
        }
    };

    return (
        <div className="admin-category-form">
            <ErrorDisplay />
            <SuccessDisplay />

            <div className="content-header">
                <h1>{categoryId ? "Kategoriyi Güncelle" : "Yeni Kategori Ekle"}</h1>
                <button
                    className="back-btn"
                    onClick={() => navigate("/admin/categories")}
                >
                    Geri Dön
                </button>
            </div>

            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label htmlFor="name" className="form-label">İsim</label>
                    <input
                        type="text"
                        id="name"
                        name="name"
                        value={category.name}
                        onChange={handleInputChange}
                        required
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="description" className="form-label">Açıklama</label>
                    <textarea
                        id="description"
                        name="description"
                        value={category.description}
                        onChange={handleInputChange}
                        rows={4}
                    />
                </div>
                <div className="form-actions">
                    {categoryId ? (
                        <button
                            type="submit"
                            className="save-btn"
                        >
                            Güncelle
                        </button>
                    ) : (
                        <button
                            type="submit"
                            className="save-btn"
                        >
                            Kaydet
                        </button>


                    )}
                </div>
            </form>

        </div>
    );
}

export default AdminCategoryFormPage;