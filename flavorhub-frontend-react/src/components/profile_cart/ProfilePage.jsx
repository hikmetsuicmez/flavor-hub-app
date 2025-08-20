import { useNavigate } from "react-router-dom";
import ApiService from "../../services/ApiService";
import { useError } from "../common/ErrorDisplay";
import { useEffect, useState } from "react";

const ProfilePage = () => {

    const [user, setUser] = useState(null);
    const { ErrorDisplay, SuccessDisplay, showError, showSuccess } = useError();
    const navigate = useNavigate();

    useEffect(() => {
        const fetchUserProfile = async () => {
            try {
                const response = await ApiService.myProfile();
                if (response.statusCode === 200) {
                    setUser(response.data);
                } else {
                    showError(response.message);
                }
            } catch (error) {
                showError(error.response?.data?.message || error.message);
            }
        }
        fetchUserProfile();
    }, []);

    const navigateToEditProfile = () => {
        navigate("/update")
    }

    const navigateToOrderHistory = () => {
        navigate("/my-order-history")
    }

    if (user) {
        return (
            <div className="profile-container">
                <ErrorDisplay />
                <SuccessDisplay />

                <h1 className="profile-title">Profil Bilgileri</h1>

                <div className="profile-card">
                    <div className="profile-card-header">
                        <div className="profile-card-title">
                            <div className="profile-avatar">
                                {user.profileUrl ? (
                                    <img
                                        className="avatar-image"
                                        src={user.profileUrl}
                                        alt={user.name}
                                    />
                                ) : (
                                    <div className="avatar-fallback">
                                        {user.name.substring(0, 2).toUpperCase()}
                                    </div>
                                )}
                            </div>
                            <span className="profile-name">{user.name} {user.surname}</span>
                        </div>
                    </div>
                    <div className="profile-card-content">
                        <div className="profile-info">
                            <p>
                                <span className="profile-info-label">Mail Adresi :</span>
                                <span>{user.email}</span>
                            </p>
                            <p>
                                <span className="profile-info-label">Telefon Numarası :</span>
                                <span>{user.phoneNumber}</span>
                            </p>
                            <p>
                                <span className="profile-info-label">Adres :</span>
                                <span>{user.address}</span>
                            </p>
                            <p>
                                <span className="profile-info-label">Durum :</span>
                                <span className={user.active ? "profile-status-active" : "profile-status-inactive"}>
                                    {user.active ? "Aktif" : "Pasif"}
                                </span>
                            </p>
                        </div>
                        <div className="profile-actions">
                            <button className="profile-edit-button" onClick={navigateToEditProfile}>
                                Profili Düzenle
                            </button>
                            <button className="profile-orders-button" onClick={navigateToOrderHistory}>
                                Siparişlerim
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default ProfilePage;