import { useEffect, useState } from "react";
import ApiService from "../../services/ApiService";
import { useNavigate } from "react-router-dom";
import { useError } from "../common/ErrorDisplay";


const OrderHistoryPage = () => {

    const [orders, setOrders] = useState([]);
    const { ErrorDisplay, SuccessDisplay, showError, showSuccess } = useError();
    const navigate = useNavigate();


    useEffect(() => {
        const fetchOrders = async () => {
            try {
                const response = await ApiService.getMyOrders();
                if (response.statusCode === 200) {

                    // response.data bir array mi yoksa tek obje mi kontrol et
                    let ordersData = response.data;
                    if (!Array.isArray(ordersData)) {
                        // Eğer tek obje ise, array'e çevir
                        ordersData = [ordersData];
                    }

                    if (ordersData.length === 0) {
                        setOrders([]);
                        return;
                    }

                    const enhancedOrders = [];
                    for (const order of ordersData) {
                        // Order items zaten mevcut, ekstra API çağrısına gerek yok
                        enhancedOrders.push(order);
                    }
                    setOrders(enhancedOrders);
                }

            } catch (error) {
                showError(error.response?.data?.message || error.message);
            }
        }
        fetchOrders();
    }, []);

    const formatDate = (dateString) => {
        if (!dateString) return 'Tarih bilgisi yok';

        try {
            const date = new Date(dateString);
            if (isNaN(date.getTime())) return 'Geçersiz tarih';

            const options = {
                year: 'numeric',
                month: 'long',
                day: 'numeric',
                hour: '2-digit',
                minute: '2-digit',
            }
            return date.toLocaleDateString('tr-TR', options);
        } catch (error) {
            console.error('Date formatting error:', error);
            return 'Tarih formatı hatası';
        }
    }

    const handleLeaveReview = (orderId, menuId) => {
        navigate(`/leave-review?orderId=${orderId}&menuId=${menuId}`);
    }

    if (!orders || orders.length === 0) {
        return (
            <div className="order-history-container">
                <div className="no-orders-message">
                    <p>Henüz siparişiniz yok.</p>
                </div>
            </div>
        );
    }


    return (
        <div className="order-history-container">
            <ErrorDisplay />
            <SuccessDisplay />

            <h1 className="order-history-title">Sipariş Geçmişiniz</h1>
            <div className="order-list">
                {orders.map((order) => (
                    <div className="order-card" key={order.id}>
                        <div className="order-header">
                            <span className="order-id">Sipariş No: {order.id}</span>
                            <span className="order-date">Tarih: {formatDate(order.orderDate || order.createdAt || new Date())}</span>
                            <span className="order-status">Durum: <span className={`status-${(order.orderStatus || order.status || 'UNKNOWN').toLowerCase()}`}>{order.orderStatus || order.status || 'UNKNOWN'}</span></span>
                            <span className="order-total">Toplam: {(order.totalAmount || 0).toFixed(2)} TL</span>
                        </div>
                        <div className="order-items">
                            <h2 className="order-items-title">Sipariş Ögeleri: </h2>
                            {order.orderItems && order.orderItems.length > 0 ? (
                                order.orderItems.map((item) => (
                                    <div className="order-item" key={item.id}>
                                        <div className="item-details">
                                            <span className="item-name">{item.menu?.name || item.name || 'Bilinmeyen Ürün'}</span>
                                            <span className="item-quantity">Adet: {item.quantity || 1}</span>
                                            <span className="item-price">Fiyat: {(item.menu?.price || item.price || 0).toFixed(2)} TL</span>
                                            <span className="item-subtotal">Toplam: {((item.menu?.price || item.price || 0) * (item.quantity || 1)).toFixed(2)} TL</span>
                                            {(order.orderStatus || order.status || '').toLowerCase() === 'delivered' && !item.hasReview && (
                                                <button
                                                    className="review-button"
                                                    onClick={() => handleLeaveReview(order.id, item.menu?.id || item.id)}
                                                >
                                                    Yorum Bırak
                                                </button>
                                            )}
                                        </div>
                                        <div className="item-image-container">
                                            <img src={item.menu?.imageUrl || item.imageUrl || '/placeholder-image.jpg'} alt={item.menu?.name || item.name || 'Ürün'} className="item-image" />
                                        </div>
                                    </div>
                                ))
                            ) : (
                                <p>Sipariş öğesi bulunamadı.</p>
                            )}
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
}

export default OrderHistoryPage;