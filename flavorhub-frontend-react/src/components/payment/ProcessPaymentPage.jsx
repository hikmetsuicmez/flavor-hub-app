import { useEffect, useState } from "react";
import ApiService from "../../services/ApiService";
import { useError } from "../common/ErrorDisplay";
import { Link, useNavigate, useSearchParams } from "react-router-dom";
import Payment from "./Payment";

const ProcessPaymentPage = () => {

    const [searchParams] = useSearchParams();
    const [paymentCompleted, setPaymentCompleted] = useState(false);

    const navigate = useNavigate();
    const { ErrorDisplay, SuccessDisplay, showError, showSuccess } = useError();

    const [orderDetails, setOrderDetails] = useState({
        orderId: '',
        amount: 0
    });

    useEffect(() => {
        const orderId = searchParams.get('orderid');
        const amount = searchParams.get('amount');

        console.log("URL'den gelen parametreler:", { orderId, amount });

        if (!orderId || !amount) {
            showError("Ödeme bilgileri geçersiz");
            return;
        }

        if (isNaN(amount)) {
            showError("Ödeme bilgileri geçersiz");
            return;
        }

        console.log("Parametreler geçerli, sipariş detayları çekiliyor...");
        // Sipariş detaylarını API'den çek
        fetchOrderDetails(orderId, parseFloat(amount));

    }, [searchParams])

    const fetchOrderDetails = async (orderId, amount) => {
        try {
            const response = await ApiService.getOrderById(orderId);
            if (response.statusCode === 200) {
                const order = response.data;
                setOrderDetails({
                    orderId: orderId,
                    amount: amount,
                });
                // console.log("Sipariş detayları:", order);
            } else {
                showError("Sipariş detayları alınamadı");
            }
        } catch (error) {
            console.error("Sipariş detayları hatası:", error);
            // Hata olsa bile temel bilgileri set et
            setOrderDetails({
                orderId: orderId,
                amount: amount,
            });
        }
    };

    const handlePaymentSuccess = (paymentIntent) => {

        console.log("Ödeme başarılı: ", paymentIntent);
        setPaymentCompleted(true);

        setTimeout(() => {
            navigate('/my-order-history');
        }, 6000);
    };

    if (paymentCompleted) {
        return (
            <div className="payment-success">
                <h2>Ödeme Başarılı!</h2>
                <p>Siparişiniz için teşekkür ederiz. Sipariş Numarası: {orderDetails.orderId}</p>
                <p>Siparişiniz alındı ve hazırlanıyor.</p>
                <p>Ödemenizin başarılı olduğuna dair bir e-posta alacaksınız.</p>
                <p>Siparişinizin detaylarını <Link to="/my-order-history">Siparişlerim</Link> sayfasından takip edebilirsiniz.</p>
            </div>
        );
    }

    return (
        <div className="checkout-container">
            <ErrorDisplay />
            <SuccessDisplay />

            <h1>Flavor Hub</h1>

            {/* Sipariş Özeti */}
            <div className="order-summary">
                <h2>Sipariş Özeti</h2>
                <div className="order-info">
                    <p><strong>Sipariş Numarası:</strong> {orderDetails.orderId}</p>
                    <p><strong>Sipariş Tarihi:</strong> {orderDetails.orderDate ? new Date(orderDetails.orderDate).toLocaleDateString('tr-TR') : 'Belirtilmemiş'}</p>
                    <p><strong>Durum:</strong> {orderDetails.status || 'Beklemede'}</p>
                </div>

                {/* Sipariş Öğeleri */}
                {orderDetails.orderItems && orderDetails.orderItems.length > 0 && (
                    <div className="order-items">
                        <h3>Sipariş Edilen Ürünler:</h3>
                        {orderDetails.orderItems.map((item, index) => (
                            <div key={index} className="order-item">
                                <div className="item-info">
                                    <img
                                        src={item.menu?.imageUrl || item.imageUrl}
                                        alt={item.menu?.name || item.name}
                                        className="item-image"
                                    />
                                    <div className="item-details">
                                        <h4>{item.menu?.name || item.name}</h4>
                                        <p>{item.menu?.description || item.description}</p>
                                        <p><strong>Adet:</strong> {item.quantity}</p>
                                        <p><strong>Birim Fiyat:</strong> {(item.menu?.price || item.price || 0).toFixed(2)} TL</p>
                                    </div>
                                </div>
                                <div className="item-total">
                                    <p><strong>Toplam:</strong> {((item.menu?.price || item.price || 0) * item.quantity).toFixed(2)} TL</p>
                                </div>
                            </div>
                        ))}
                    </div>
                )}

                {/* Toplam */}
                <div className="order-total">
                    <h3>Toplam Tutar: {orderDetails.amount.toFixed(2)} TL</h3>
                </div>
            </div>

            <Payment
                amount={orderDetails.amount}
                orderId={orderDetails.orderId}
                onSuccess={handlePaymentSuccess}
            />
        </div>
    )

}


export default ProcessPaymentPage;