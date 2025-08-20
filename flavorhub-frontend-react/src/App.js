import { BrowserRouter, Route, Routes } from "react-router-dom";
import Navbar from "./components/common/Navbar";
import Footer from "./components/common/Footer";
import RegisterPage from "./components/auth/RegisterPage";
import LoginPage from "./components/auth/LoginPage";
import HomePage from "./components/home_menu/HomePage";
import CategoriesPage from "./components/home_menu/CategoriesPage";
import MenuPage from "./components/home_menu/MenuPage";

function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <div className="content">
        <Routes>
          {/* AUTH PAGE */}
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/login" element={<LoginPage />} />

          {/* HOME PAGE */}
          <Route path="/home" element={<HomePage />} />

          {/* CATEGORIES PAGE */}
          <Route path="/categories" element={<CategoriesPage />} />

          {/* MENU PAGE */}
          <Route path="/menu" element={<MenuPage />} />
        </Routes>
      </div>
      <Footer />
    </BrowserRouter>
  );
}

export default App;
