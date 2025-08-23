import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import Navbar from "./components/common/Navbar";
import Footer from "./components/common/Footer";
import RegisterPage from "./components/auth/RegisterPage";
import LoginPage from "./components/auth/LoginPage";
import HomePage from "./components/home_menu/HomePage";
import CategoriesPage from "./components/home_menu/CategoriesPage";
import MenuPage from "./components/home_menu/MenuPage";
import MenuDetailsPage from "./components/home_menu/MenuDetailsPage";
import ProfilePage from "./components/profile_cart/ProfilePage";
import UpdateProfilePage from "./components/profile_cart/UpdateProfilePage";
import OrderHistoryPage from "./components/profile_cart/OrderHistoryPage";
import LeaveReviewPage from "./components/profile_cart/LeaveReviewPage";
import CartPage from "./components/profile_cart/CartPage";
import { AdminRoute, CustomerRoute } from "./services/Guard";

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
          <Route path="/menu/:id" element={<MenuDetailsPage />} />

          {/* PROFILE PAGE */}
          <Route path="/profile" element={<CustomerRoute element={<ProfilePage />} />} />

          {/* UPDATE PROFILE PAGE */}
          <Route path="/update" element={<CustomerRoute element={<UpdateProfilePage />} />} />

          {/* ORDER HISTORY PAGE */}
          <Route path="/my-order-history" element={<CustomerRoute element={<OrderHistoryPage />} />} />

          {/* LEAVE REVIEW PAGE */}
          <Route path="/leave-review" element={<CustomerRoute element={<LeaveReviewPage />} />} />

          {/* CART PAGE */}
          <Route path="/cart" element={<CustomerRoute element={<CartPage />} />} />








          <Route path="*" element={<Navigate to={"/home"} />}></Route>
        </Routes>
      </div>
      <Footer />
    </BrowserRouter>
  );
}

export default App;
