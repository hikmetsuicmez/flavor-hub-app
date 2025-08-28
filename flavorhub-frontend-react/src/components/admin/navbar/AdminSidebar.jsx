import { NavLink, useLocation } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
    faList,
    faShoppingBag,
    faUtensils,
    faChartLine,
    faCreditCard,
} from "@fortawesome/free-solid-svg-icons";

const AdminSidebar = () => {

    const location = useLocation();

    return (
        <div className="admin-sidebar">
            <div className="sidebar-header">
                <h2 className="sidebar-header">Admin Panel</h2>
            </div>

            <nav className="sidebar-nav">
                <ul>
                    <li>
                        <NavLink
                            to="/admin"
                            className={location.pathname === "/admin" ? "active" : ""}
                            end>
                            <FontAwesomeIcon icon={faChartLine} />
                            <span>Kontrol Paneli</span>
                        </NavLink>
                    </li>
                    <li>
                        <NavLink
                            to="/admin/categories"
                            className={location.pathname.includes("/admin/categories") ? "active" : ""}
                            end>
                            <FontAwesomeIcon icon={faList} />
                            <span>Kategoriler</span>
                        </NavLink>
                    </li><li>
                        <NavLink
                            to="/admin/menu-items"
                            className={location.pathname.includes("/admin/menu-items") ? "active" : ""}
                            end>
                            <FontAwesomeIcon icon={faUtensils} />
                            <span>Menü Öğeleri</span>
                        </NavLink>
                    </li><li>
                        <NavLink
                            to="/admin/orders"
                            className={location.pathname.includes("/admin/orders") ? "active" : ""}
                            end>
                            <FontAwesomeIcon icon={faShoppingBag} />
                            <span>Siparişler</span>
                        </NavLink>
                    </li><li>
                        <NavLink
                            to="/admin/payments"
                            className={location.pathname.includes("/admin/payments") ? "active" : ""}
                            end>
                            <FontAwesomeIcon icon={faCreditCard} />
                            <span>Ödemeler</span>
                        </NavLink>
                    </li>
                </ul>
            </nav>
        </div>
    )
}

export default AdminSidebar;