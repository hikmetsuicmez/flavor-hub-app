import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useError } from "../../common/ErrorDisplay"
import ApiService from "../../../services/ApiService";
import { faSignOut, faBars, faSignOutAlt } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const AdminTopbar = () => {

    const navigate = useNavigate();
    const [userProfile, setUserProfile] = useState(null);
    const { ErrorDisplay, showError, SuccessDisplay, showSuccess } = useError();

    useEffect(() => {

        const fetchProfile = async () => {

            try {
                const response = await ApiService.myProfile();
                if (response.statusCode === 200) {
                    setUserProfile(response.data);
                }

            } catch (error) {
                showError(error.response?.data?.message || error.message);
            }

        }
        fetchProfile();
    }, []);

    const handleLogout = () => {
        ApiService.logout();
        navigate("/login");
    }

    const toggleSidebar = () => {
        const sidebar = document.querySelector(".admin-sidebar");
        sidebar.classList.toggle("active");
    };

    return (
        <header className="admin-topbar">
            <div className="topbar-left">
                <button onClick={toggleSidebar}>
                    <FontAwesomeIcon icon={faBars} />
                </button>
            </div>

            <ErrorDisplay />
            <SuccessDisplay />

            <div className="topbar-right">
                <div className="user-info">
                    <img src={userProfile?.profileUrl} alt="User" className="profile-image" />
                </div>

                <div className="profile-info">
                    <span className="profile-name">{userProfile?.name || 'Admin'}</span>
                    <span className="profile-role">Admin</span>
                </div>

                <button onClick={handleLogout} className="logout-btn">
                    <FontAwesomeIcon icon={faSignOutAlt} />
                </button>

            </div>

        </header>
    )
}

export default AdminTopbar;