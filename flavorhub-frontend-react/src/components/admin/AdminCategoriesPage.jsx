import { useEffect, useState } from "react";
import ApiService from "../../services/ApiService";
import { useError } from "../common/ErrorDisplay";
import { useNavigate } from "react-router-dom";

const AdminCategoriesPage = () => {

    const [categories, setCategories] = useState([]);
    const { ErrorDisplay, showError, SuccessDisplay, showSuccess } = useError();
    const navigate = useNavigate();

    useEffect(() => {
        fetchCategories();
    }, []);

    const fetchCategories = async () => {
        try {
            const response = await ApiService.getAllCategories();
            console.log("API Response:", response); // Debug için
            if (response.statusCode === 200) {
                setCategories(response.data);
            } else {
                showError(response.message || "Kategoriler yüklenirken bir hata oluştu");
            }

        } catch (error) {
            console.error("API Error:", error); // Debug için
            showError(error.response?.data?.message || error.message);
        }
    }

    const handleAddCategory = () => {
        navigate("/admin/categories/new");
    };

    const handleEditCategory = (categoryId) => {
        navigate(`/admin/categories/edit/${categoryId}`);
    };

    const handleDeleteCategory = async (categoryId) => {
        if (window.confirm("Bu kategoriyi silmek istediğinize emin misiniz?")) {
            try {
                const response = await ApiService.deleteCategory(categoryId);
                if (response.statusCode === 200) {
                    showSuccess("Kategori başarıyla silindi");
                    fetchCategories();
                } else {
                    showError(response.message || "Kategori silinirken bir hata oluştu");
                }
            } catch (error) {
                showError(error.response?.data?.message || error.message);
            }
        }
    };

    return (
        <div className="admin-categories">
            <ErrorDisplay />
            <SuccessDisplay />

            <div className="content-header">
                <h1>Kategori Yönetimi</h1>
                <button className="add-btn" onClick={handleAddCategory}>
                    <i className="fas fa-plus"></i>Kategori Ekle
                </button>
            </div>

            <div className="categories-table">
                <table>
                    <thead>
                        <tr>
                            <th>Sıra</th>
                            <th>İsim</th>
                            <th>Açıklama</th>
                            <th>Aksiyonlar</th>
                        </tr>
                    </thead>
                    <tbody>
                        {categories.map(category => (
                            <tr key={category.id}>
                                <td>{category.id}</td>
                                <td>{category.name}</td>
                                <td>{category.description}</td>
                                <td className="actions">
                                    <button
                                        className="edit-btn"
                                        onClick={() => handleEditCategory(category.id)}
                                    >
                                        <i className="fas fa-edit">
                                            Düzenle
                                        </i>
                                    </button>
                                    <button
                                        className="delete-btn"
                                        onClick={() => handleDeleteCategory(category.id)}
                                    >
                                        <i className="fas fa-trash"></i>
                                        Sil
                                    </button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    )

}
export default AdminCategoriesPage;