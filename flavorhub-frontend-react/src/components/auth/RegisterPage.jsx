import { Link, useNavigate } from "react-router-dom";
import { useError } from "../common/ErrorDisplay";
import { useState } from "react";
import ApiService from "../../services/ApiService";

const RegisterPage = () => {

    const { ErrorDisplay, SuccessDisplay, showError, showSuccess } = useError();
    const navigate = useNavigate();

    const [formData, setFormData] = useState({

        name: "",
        email: "",
        password: "",
        phoneNumber: "",
        address: "",
        confirmPassword: "",

    });

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    }

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (
            !formData.name ||
            !formData.email ||
            !formData.password ||
            !formData.phoneNumber ||
            !formData.confirmPassword ||
            !formData.address
        ) {
            showError("Lütfen tüm alanları doldurunuz.");
            return;
        }

        if (formData.password !== formData.confirmPassword) {
            showError("Şifreler eşleşmiyor.");
            return;
        }

        const registrationData = {

            name: formData.name,
            email: formData.email,
            password: formData.password,
            phoneNumber: formData.phoneNumber,
            address: formData.address,

        };

        try {
            const response = await ApiService.registerUser(registrationData);
            if (response.statusCode === 200) {
                showSuccess("Kayıt başarıyla tamamlandı! Yönlendiriliyorsunuz...");
                setFormData({
                    name: "", email: "", password: "", phoneNumber: "", address: "", confirmPassword: ""
                });
                setTimeout(() => {
                    navigate("/login");
                }, 2000);
            } else {
                showError(response.message);
            }

        } catch (error) {
            showError(error.response?.data?.message || error.message);
        }

    };

    return (
        <div className="register-page-food">
            <div className="register-card-food">
                <div className="register-header-food">
                    <h2 className="register-title-food">Kayıt Ol</h2>
                    <p className="register-description-food">Lezzetli yemekleri keşfetmeye hazır mısınız? Hemen kayıt olun ve yeni deneyimlerle buluşun!</p>
                </div>
                <div className="register-content-food">
                    <form className="register-form-food" onSubmit={handleSubmit}>
                        <div className="register-form-group">
                            <label htmlFor="name" className="register-label-food">Adınız ve Soyadınız</label>
                            <input
                                type="text"
                                id="name"
                                name="name"
                                value={formData.name}
                                onChange={handleChange}
                                required
                                placeholder="Adınız ve Soyadınız"
                                className="register-input-food"
                            />
                        </div>
                        <div className="register-form-group">
                            <label htmlFor="name" className="register-label-food">Email Adresiniz</label>
                            <input
                                type="email"
                                id="email"
                                name="email"
                                value={formData.email}
                                onChange={handleChange}
                                required
                                placeholder="Email Adresiniz"
                                className="register-input-food"
                            />
                        </div>
                        <div className="register-form-group">
                            <label htmlFor="name" className="register-label-food">Şifreniz</label>
                            <input
                                type="password"
                                id="password"
                                name="password"
                                value={formData.password}
                                onChange={handleChange}
                                required
                                placeholder="Şifreniz"
                                className="register-input-food"
                            />
                        </div>
                        <div className="register-form-group">
                            <label htmlFor="name" className="register-label-food">Şifre Tekrarı</label>
                            <input
                                type="password"
                                id="confirmPassword"
                                name="confirmPassword"
                                value={formData.confirmPassword}
                                onChange={handleChange}
                                required
                                placeholder="Şifre Tekrarı"
                                className="register-input-food"
                            />
                        </div>
                        <div className="register-form-group">
                            <label htmlFor="name" className="register-label-food">Telefon Numaranız</label>
                            <input
                                type="text"
                                id="phoneNumber"
                                name="phoneNumber"
                                value={formData.phoneNumber}
                                onChange={handleChange}
                                required
                                placeholder="Telefon Numaranız"
                                className="register-input-food"
                            />
                        </div>
                        <div className="register-form-group">
                            <label htmlFor="name" className="register-label-food">Adresiniz</label>
                            <input
                                type="text"
                                id="address"
                                name="address"
                                value={formData.address}
                                onChange={handleChange}
                                required
                                placeholder="Adresiniz"
                                className="register-input-food"
                            />
                        </div>

                        <ErrorDisplay />
                        <SuccessDisplay />

                        <div>
                            <button type="submit" className="register-button-food">
                                Kayıt Ol
                            </button>
                        </div>

                        <div className="already">
                            <Link to="/login" className="register-link-food">
                                Zaten hesabınız var mı? Giriş Yap.
                            </Link>
                        </div>
                    </form>

                    <div className="register-social-food">
                        <div className="register-separator-food">
                            <span className="register-separator-text-food">Veya</span>
                        </div>

                        <div className="register-social-buttons-foods">
                            <button className="register-social-button-food register-social-google-food">
                                <i className="fab fa-google"></i>
                                Google ile Giriş Yap
                            </button>
                            <button className="register-social-button-food register-social-facebook-food">
                                <i className="fab fa-facebook"></i>
                                Facebook ile Giriş Yap
                            </button>
                            <button className="register-social-button-food register-social-github-food">
                                <i className="fab fa-github"></i>
                                Github ile Giriş Yap
                            </button>

                        </div>

                    </div>
                </div>
            </div>
        </div>
    )


}

export default RegisterPage;