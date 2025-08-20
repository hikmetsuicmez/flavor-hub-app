import { useEffect, useState, useRef } from "react";
import ApiService from "../../services/ApiService";
import { useError } from "../common/ErrorDisplay";
import { useNavigate } from "react-router-dom";

const UpdateProfilePage = () => {

    const [name, setName] = useState("");
    const [surname, setSurname] = useState("");
    const [email, setEmail] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");
    const [address, setAddress] = useState("");

    const [profileImage, setProfileImage] = useState(null); // Dosya seçildiğinde dosya değişkenine atanır
    const [previewImage, setPreviewImage] = useState(''); // Dosya seçildiğinde görüntülenecek olan fotoğrafın URL'si

    const fileInputRef = useRef(null);  // Dosya seçme input'una referans oluşturur - profil fotoğrafı yüklemek için kullanılır

    const { ErrorDisplay, SuccessDisplay, showError, showSuccess } = useError();
    const navigate = useNavigate();

    useEffect(() => {

        const fetchUserProfile = async () => {
            try {
                const response = await ApiService.myProfile();
                if (response.statusCode === 200) {

                    const userData = response.data;
                    setName(userData.name);
                    setSurname(userData.surname);
                    setEmail(userData.email);
                    setPhoneNumber(userData.phoneNumber);
                    setAddress(userData.address);
                    setPreviewImage(userData.profileUrl);
                }
                else {
                    showError(response.message);
                }
            } catch (error) {
                showError(error.response?.data?.message || error.message);
            }
        }
        fetchUserProfile();
    }, []);

    const handleImageChange = (e) => {

        const file = e.target.files[0];
        if (file) {
            setProfileImage(file);
            setPreviewImage(URL.createObjectURL(file));
        }
    }

    // Dosya seçme input'unu tetiklemek için kullanılır
    const triggerFileInput = () => {
        fileInputRef.current.click();
    }

    const handleUpdateProfile = async (e) => {
        e.preventDefault();

        if (!window.confirm("Profil bilgilerinizi güncellemek istediğinize emin misiniz?")) {
            return;
        }

        try {
            const formData = new FormData();
            formData.append("name", name);
            formData.append("surname", surname);
            formData.append("email", email);
            formData.append("phoneNumber", phoneNumber);
            formData.append("address", address);

            if (profileImage) {
                formData.append("imageFile", profileImage);
            }

            const response = await ApiService.updateProfile(formData);
            if (response.statusCode === 200) {
                showSuccess("Profil bilgileriniz başarıyla güncellendi!");
                navigate("/profile");
            }

        } catch (error) {
            showError(error.response?.data?.message || error.message);
        }
    }

    const handleDeactivateProfile = async () => {
        if (!window.confirm("Hesabınızı pasifleştirmek istediğinize emin misiniz?")) {
            return;
        }

        try {
            const response = await ApiService.deactivateAccount();
            if (response.statusCode === 200) {
                showSuccess("Hesabınız başarıyla pasifleştirildi!");
                ApiService.logout();
                navigate("/home")
            }

        } catch (error) {
            showError(error.response?.data?.message || error.message);
        }
    }

    return (
        <div className="profile-container">
            <ErrorDisplay />
            <SuccessDisplay />

            <div className="profile-header">
                <h1 className="profile-title">Profili Güncelle</h1>
                <div className="profile-image-container">

                    <img src={previewImage}
                        alt="Profil Fotoğrafı"
                        onClick={triggerFileInput}
                        className="profile-image"
                    />

                    <input
                        type="file"
                        ref={fileInputRef}
                        onChange={handleImageChange}
                        accept="image/*"
                        style={{ display: "none" }}
                    />
                    <button
                        className="profile-image-upload"
                        onClick={triggerFileInput}
                    >
                        Profil Fotoğrafını Değiştir
                    </button>
                </div>
            </div>

            <form onSubmit={handleUpdateProfile} className="profile-form">
                <div className="form-grid">
                    <div className="profile-form-group">
                        <label htmlFor="name" className="profile-form-label">Adınız: </label>
                        <input
                            type="text"
                            id="name"
                            name="name"
                            value={name}
                            onChange={(e) => setName(e.target.value)}
                            required
                            placeholder="Adınız"
                            className="profile-form-input"
                        />
                    </div>
                    <div className="profile-form-group">
                        <label htmlFor="surname" className="profile-form-label">Soyadınız</label>
                        <input
                            type="text"
                            id="surname"
                            name="surname"
                            value={surname}
                            onChange={(e) => setSurname(e.target.value)}
                            required
                            placeholder="Soyadınız"
                            className="profile-form-input"
                        />
                    </div>
                    <div className="profile-form-group">
                        <label htmlFor="email" className="profile-form-label">Email Adresiniz: </label>
                        <input
                            type="email"
                            id="email"
                            name="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                            placeholder="Email"
                            className="profile-form-input"
                        />
                    </div>
                    <div className="profile-form-group">
                        <label htmlFor="phoneNumber" className="profile-form-label">Telefon Numarası</label>
                        <input
                            type="tel"
                            id="phoneNumber"
                            name="phoneNumber"
                            value={phoneNumber}
                            onChange={(e) => setPhoneNumber(e.target.value)}
                            required
                            placeholder="Telefon Numarası"
                            className="profile-form-input"
                        />
                    </div>
                    <div className="profile-form-group">
                        <label htmlFor="address" className="profile-form-label">Adresiniz: </label>
                        <input
                            type="text"
                            id="address"
                            name="address"
                            value={address}
                            onChange={(e) => setAddress(e.target.value)}
                            required
                            placeholder="Adres"
                            className="profile-form-input"
                        />
                    </div>
                </div>

                <div className="form-actions">
                    <button type="submit" className="btn btn-primary">
                        Profili Güncelle
                    </button>
                    <button type="button" className="btn btn-danger" onClick={handleDeactivateProfile}>
                        Hesabı Pasifleştir
                    </button>
                </div>
            </form>

        </div>
    )
}


export default UpdateProfilePage;