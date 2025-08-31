import { useEffect, useState } from "react";
import ApiService from "../../services/ApiService";
import { useError } from "../common/ErrorDisplay";
import { useNavigate } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faEdit, faTrash, faPlus } from "@fortawesome/free-solid-svg-icons";

const AdminMenuPage = () => {

    const [menus, setMenus] = useState([]);
    const { ErrorDisplay, SuccessDisplay, showError, showSuccess } = useError();
    const navigate = useNavigate();

    useEffect(() => {
        fetchMenus();
    }, []);

    const fetchMenus = async () => {
        try {
            const response = await ApiService.getAllMenus();
            if (response.statusCode === 200) {
                setMenus(response.data);
            }
        } catch (error) {
            showError(error.response?.data?.message || error.message);
        }
    };

    const handleAddMenuItem = () => {
        navigate("/admin/menu-items/new");
    };

    const handleEditMenuItem = (menuId) => {
        navigate(`/admin/menu-items/edit/${menuId}`);
    };

    const handleDeleteMenuItem = async (menuId) => {
        if (window.confirm("Bu menüyü silmek istediğinize emin misiniz?")) {
            try {
                const response = await ApiService.deleteMenuItem(menuId);
                if (response.statusCode === 200) {
                    showSuccess("Menü başarıyla silindi");
                    fetchMenus();
                }

            } catch (error) {
                showError(error.response?.data?.message || error.message);
            }
        }
        fetchMenus();
    };

    return (
        <div className="admin-menu-items">
            <ErrorDisplay />
            <SuccessDisplay />

            <div className="content-header">
                <h1>Menü Yönetimi</h1>
                <button className="add-btn" onClick={handleAddMenuItem}>
                    <FontAwesomeIcon icon={faPlus} /> Menü Ekle
                </button>
            </div>

            <div className="menu-items-grid">
                {menus.map(item => (
                    <div className="menu-item-card" key={item.id}>
                        <div className="manu-item-image">
                            <img src={item.imageUrl} alt={item.name} />
                        </div>
                        <div className="item-details">
                            <h3>{item.name}</h3>
                            <p className="item-price">{item.price.toFixed(2)} TL</p>
                            <p className="item-description">{item.description}</p>
                            <div className="item-footer">
                                <span className="reviews-count">
                                    {item.reviews?.length || 0} yorum
                                </span>
                                <div className="item-actions">
                                    <button
                                        className="edit-btn"
                                        onClick={() => handleEditMenuItem(item.id)}
                                    >
                                        <FontAwesomeIcon icon={faEdit} />
                                    </button>
                                    <button
                                        className="delete-btn"
                                        onClick={() => handleDeleteMenuItem(item.id)}
                                    >
                                        <FontAwesomeIcon icon={faTrash} />
                                    </button>

                                </div>
                            </div>
                        </div>
                    </div>

                ))}
            </div>
        </div>
    );

}


export default AdminMenuPage;