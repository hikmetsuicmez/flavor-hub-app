import { Link, useLocation, useNavigate } from "react-router-dom";
import { useError } from "../common/ErrorDisplay";
import { useState } from "react";
import ApiService from "../../services/ApiService";

const LoginPage = () => {

    const { ErrorDisplay, SuccessDisplay, showError, showSuccess } = useError();
    const navigate = useNavigate();
    const { state } = useLocation();
    const redirectPath = state?.from?.pathname || "/home";

    const [formData, setFormData] = useState({

        email: "",
        password: "",
    });

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    }

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (
            !formData.email || !formData.password) {
            showError("Email ve şifre alanlarını doldurunuz.");
            return;
        }
        const loginData = {
            email: formData.email,
            password: formData.password,
        };

        try {
            const response = await ApiService.loginUser(loginData);
            if (response.statusCode === 200) {
                ApiService.saveToken(response.data.token);
                ApiService.saveRole(response.data.roles);
                showSuccess("Giriş başarılı! Yönlendiriliyorsunuz...");
                setTimeout(() => {
                    navigate(redirectPath, { replace: true });
                }, 1000);
            } else {
                showError(response.message);
            }

        } catch (error) {
            showError(error.response?.data?.message || error.message);
        }
    };

    return (
        <div className="login-page-food">

            <ErrorDisplay />
            <SuccessDisplay />
            <div className="login-card-food">

                <div className="login-header-food">
                    <h2 className="login-title-food">Giriş Yap</h2>
                    <p className="login-description-food">Lezzetli yemekleri keşfetmeye hazır mısınız? Hemen giriş yapın ve yeni deneyimlerle buluşun!</p>
                </div>

                <div className="login-content-food">
                    <form className="login-form-food" onSubmit={handleSubmit}>
                        <div className="login-form-group">
                            <label htmlFor="email" className="login-label-food">Email</label>
                            <input
                                type="email"
                                id="email"
                                name="email"
                                value={formData.email}
                                onChange={handleChange}
                                required
                                placeholder="Email"
                                className="login-input-food"
                            />
                        </div>
                        <div className="login-form-group">
                            <label htmlFor="password" className="login-label-food">Şifre</label>
                            <input
                                type="password"
                                id="password"
                                name="password"
                                value={formData.password}
                                onChange={handleChange}
                                required
                                placeholder="Şifre"
                                className="login-input-food"
                            />
                        </div>
                        <div>
                            <button type="submit" className="login-button-food">
                                Giriş Yap
                            </button>
                        </div>
                        <div className="already">
                            <Link to="/register" className="register-link-food">
                                Hesabınız yok mu? Kayıt Ol.
                            </Link>
                        </div>
                    </form>
                    <div className="login-social-food">
                        <div className="login-separator-food">
                            <span className="login-separator-text-food">Veya</span>
                        </div>
                        <div className="login-social-buttons-food">
                            <button className="login-social-button-food login-social-google-food">Google</button>
                            <button className="login-social-button-food login-social-facebook-food">Facebook</button>
                            <button className="login-social-button-food login-social-github-food">Github</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default LoginPage;