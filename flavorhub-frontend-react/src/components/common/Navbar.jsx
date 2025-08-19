import { Link, useNavigate } from "react-router-dom";
import ApiService from "../../services/ApiService";

const Navbar = () => {

    const isAuthenticated = ApiService.isAuthenticated();
    const isAdmin = ApiService.isAdmin();
    const isCustomer = ApiService.isCustomer();
    const isDeliveryPerson = ApiService.isDeliveryPerson();
    const navigate = useNavigate();

    const handleLogout = () => {
        const isLogout = window.confirm("Are you sure you want to logout?");
        if (isLogout) {
            ApiService.logout();
            navigate("/login");
        }
    }

    return (
        <nav>
            <div className="logo">
                <Link to="/home" className="logo-link">
                    Flavor Hub
                </Link>
            </div>

            <div className="desktop-nav">
                <Link to="/home" className="nav-link">Ana Sayfa</Link>
                <Link to="/menu" className="nav-link">Menü</Link>
                <Link to="/categories" className="nav-link">Kategoriler</Link>

                {isAuthenticated ? (
                    <>
                        {isCustomer && (
                            <Link to="/orders" className="nav-link">Siparişlerim</Link>,
                            <Link to="/cart" className="nav-link">Sepetim</Link>
                        )}
                        {isDeliveryPerson && (
                            <Link to="/deliveries" className="nav-link">Teslimatlar</Link>
                        )}
                        {isAdmin && (
                            <Link to="/admin" className="nav-link">Admin</Link>
                        )}
                        <Link to="/profile" className="nav-link">Profilim</Link>
                        <button onClick={handleLogout} className="nav-button">
                            Çıkış Yap
                        </button>
                    </>
                ) : (
                    <>
                        <Link to="/login" className="nav-link">Giriş Yap</Link>
                        <Link to="/register" className="nav-link">Kayıt Ol</Link>
                    </>
                )}
            </div>
        </nav>
    )

}
export default Navbar;
