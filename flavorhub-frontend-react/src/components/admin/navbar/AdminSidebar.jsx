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
        <div>
            <div>
                <h2>Admin Panel</h2>
            </div>

            <nav>
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
                            to="/admin"
                            className={location.pathname.includes("/admin/categories") ? "active" : ""}
                            end>
                            <FontAwesomeIcon icon={faChartLine} />
                            <span>Kategoriler</span>
                        </NavLink>
                    </li><li>
                        <NavLink
                            to="/admin"
                            className={location.pathname.includes("/admin/menu-items") ? "active" : ""}
                            end>
                            <FontAwesomeIcon icon={faChartLine} />
                            <span>Menü Öğeleri</span>
                        </NavLink>
                    </li><li>
                        <NavLink
                            to="/admin"
                            className={location.pathname.includes("/admin/orders") ? "active" : ""}
                            end>
                            <FontAwesomeIcon icon={faChartLine} />
                            <span>Siparişler</span>
                        </NavLink>
                    </li><li>
                        <NavLink
                            to="/admin"
                            className={location.pathname.includes("/admin/payments") ? "active" : ""}
                            end>
                            <FontAwesomeIcon icon={faChartLine} />
                            <span>Ödemeler</span>
                        </NavLink>
                    </li>
                </ul>
            </nav>
        </div>
    )
}

export default AdminSidebar;