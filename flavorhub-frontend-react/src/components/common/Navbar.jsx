import { Link, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import ApiService from "../../services/ApiService";

const Navbar = () => {
    const [isMenuOpen, setIsMenuOpen] = useState(false);
    const [isProfileDropdownOpen, setIsProfileDropdownOpen] = useState(false);
    const [cartItemCount, setCartItemCount] = useState(0);

    const isAuthenticated = ApiService.isAuthenticated();
    const isAdmin = ApiService.isAdmin();
    const isCustomer = ApiService.isCustomer();
    const isDeliveryPerson = ApiService.isDeliveryPerson();
    const navigate = useNavigate();

    // Sepet durumunu kontrol et
    useEffect(() => {
        const checkCartStatus = async () => {
            if (isAuthenticated && isCustomer) {
                try {
                    console.log("Sepet durumu kontrol ediliyor...");
                    const response = await ApiService.getCart();
                    console.log("Sepet API yanıtı:", response);
                    if (response.statusCode === 200 && response.data && response.data.cartItems) {
                        console.log("Sepet öğeleri bulundu:", response.data.cartItems.length);
                        setCartItemCount(response.data.cartItems.length);
                    } else {
                        console.log("Sepet boş veya hata var");
                        setCartItemCount(0);
                    }
                } catch (error) {
                    console.error("Sepet bilgisi alınamadı:", error);
                    setCartItemCount(0);
                }
            } else {
                console.log("Kullanıcı giriş yapmamış veya müşteri değil");
                setCartItemCount(0);
            }
        };

        checkCartStatus();

        // Custom event listener ekle (sepet değiştiğinde)
        const handleCartUpdate = () => {
            console.log("Cart updated event alındı, sepet durumu güncelleniyor...");
            checkCartStatus();
        };

        window.addEventListener('cartUpdated', handleCartUpdate);

        return () => {
            window.removeEventListener('cartUpdated', handleCartUpdate);
        };
    }, [isAuthenticated, isCustomer]);

    // Debug için cartItemCount değerini göster
    useEffect(() => {
        console.log("Güncel sepet öğe sayısı:", cartItemCount);
    }, [cartItemCount]);

    const handleLogout = () => {
        const isLogout = window.confirm("Are you sure you want to logout?");
        if (isLogout) {
            ApiService.logout();
            navigate("/login");
        }
    }

    const toggleMenu = () => {
        setIsMenuOpen(!isMenuOpen);
    }

    const toggleProfileDropdown = () => {
        setIsProfileDropdownOpen(!isProfileDropdownOpen);
    }

    return (
        <nav className="modern-navbar">
            <div className="navbar-container">
                <div className="navbar-brand">
                    <Link to="/home" className="navbar-logo">
                        <span className="logo-text">Flavor Hub</span>
                    </Link>
                </div>

                <div className="navbar-menu">
                    <div className="nav-links">
                        <Link to="/home" className="nav-link">Ana Sayfa</Link>
                        <Link to="/menu" className="nav-link">Menü</Link>
                        <Link to="/categories" className="nav-link">Kategoriler</Link>
                    </div>

                    {isAuthenticated ? (
                        <div className="user-section">
                            <div className="user-nav">
                                {isCustomer && (
                                    <>
                                        <Link to="/orders" className="nav-link">Siparişlerim</Link>
                                        <Link to="/cart" className="nav-link cart-link">
                                            Sepetim
                                            {cartItemCount > 0 && (
                                                <span className="cart-indicator"></span>
                                            )}
                                        </Link>
                                    </>
                                )}
                                {isDeliveryPerson && (
                                    <Link to="/deliveries" className="nav-link">Teslimatlar</Link>
                                )}
                                {isAdmin && (
                                    <Link to="/admin" className="nav-link admin-link">Admin Panel</Link>
                                )}
                            </div>

                            <div className="profile-section">
                                <button
                                    className="profile-button"
                                    onClick={toggleProfileDropdown}
                                >
                                    Profilim
                                </button>

                                {isProfileDropdownOpen && (
                                    <div className="profile-dropdown">
                                        <Link to="/profile" className="dropdown-link">Profil Bilgileri</Link>
                                        <button onClick={handleLogout} className="dropdown-link logout-btn">
                                            Çıkış Yap
                                        </button>
                                    </div>
                                )}
                            </div>
                        </div>
                    ) : (
                        <div className="auth-buttons">
                            <Link to="/login" className="auth-link login-btn">Giriş Yap</Link>
                            <Link to="/register" className="auth-link register-btn">Kayıt Ol</Link>
                        </div>
                    )}
                </div>

                <button className="mobile-menu-toggle" onClick={toggleMenu}>
                    <span></span>
                    <span></span>
                    <span></span>
                </button>
            </div>

            {/* Mobile Menu */}
            {isMenuOpen && (
                <div className="mobile-menu">
                    <Link to="/home" className="mobile-nav-link">Ana Sayfa</Link>
                    <Link to="/menu" className="mobile-nav-link">Menü</Link>
                    <Link to="/categories" className="mobile-nav-link">Kategoriler</Link>

                    {isAuthenticated ? (
                        <>
                            {isCustomer && (
                                <>
                                    <Link to="/orders" className="mobile-nav-link">Siparişlerim</Link>
                                    <Link to="/cart" className="mobile-nav-link">
                                        Sepetim
                                        {cartItemCount > 0 && (
                                            <span className="cart-indicator mobile-cart-indicator"></span>
                                        )}
                                    </Link>
                                </>
                            )}
                            {isDeliveryPerson && (
                                <Link to="/deliveries" className="mobile-nav-link">Teslimatlar</Link>
                            )}
                            {isAdmin && (
                                <Link to="/admin" className="mobile-nav-link">Admin Panel</Link>
                            )}
                            <Link to="/profile" className="mobile-nav-link">Profilim</Link>
                            <button onClick={handleLogout} className="mobile-nav-link logout-btn">
                                Çıkış Yap
                            </button>
                        </>
                    ) : (
                        <>
                            <Link to="/login" className="mobile-nav-link">Giriş Yap</Link>
                            <Link to="/register" className="mobile-nav-link">Kayıt Ol</Link>
                        </>
                    )}
                </div>
            )}
        </nav>
    )
}

export default Navbar;
