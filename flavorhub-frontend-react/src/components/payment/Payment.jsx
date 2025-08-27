import { useState } from "react";
import { loadStripe } from "@stripe/stripe-js";
import { Elements, CardElement, useStripe, useElements } from "@stripe/react-stripe-js";
import { useEffect } from "react";
import { useError } from "../common/ErrorDisplay";
import { useNavigate } from "react-router-dom";
import ApiService from "../../services/ApiService";

// Stripe publishable key
const stripeInstance = loadStripe('pk_test_51Rxa7NHHNBGTxBmTvQnRAjBhJPTgwSE8yN12FdIDzp6n2ct4P8dp9MNB5LuOSa65tiLD70QRvPmTuJHblXTNuVaV007gnkc8La');

const PaymentForm = ({ amount, orderId, onSuccess }) => {

    const stripe = useStripe();
    const elements = useElements();

    const [loading, setLoading] = useState(false);
    const { ErrorDisplay, SuccessDisplay, showError, showSuccess } = useError();

    const handleSubmit = async (event) => {
        event.preventDefault();

        if (!stripe || !elements) {
            return;
        }

        // Token kontrolü ekleyin
        if (!ApiService.isAuthenticated()) {
            showError("Oturum süreniz dolmuş. Lütfen tekrar giriş yapın.");
            return;
        }

        setLoading(true);

        try {
            // Step 1: 
            const body = {
                amount: amount,
                orderId: orderId
            }
            console.log("Ödeme isteği gönderiliyor:", body);
            const paymentInitializeResponse = await ApiService.initializePayment(body);
            console.log("Ödeme response:", paymentInitializeResponse);

            if (paymentInitializeResponse.statusCode !== 200) {

                throw new Error(paymentInitializeResponse.message || "Ödeme başarısız oldu");
            }

            const uniqueTransactionId = paymentInitializeResponse.data;

            // Step 2: Stripe ile ödeme işlemini başlat
            const { error: stripeError, paymentIntent } = await stripe.confirmCardPayment(uniqueTransactionId, {
                payment_method: {
                    card: elements.getElement(CardElement),
                    billing_details: {
                        // TODO: Add any additional billing details you want
                    }
                }
            });

            if (stripeError) {
                throw stripeError;
            }

            if (paymentIntent.status === "succeeded") {
                console.log("ÖDEME BAŞARILI")

                // Step 3: Ödeme başarılı olduğunda payment tablosunu güncelle
                const response = ApiService.updatePayment({
                    orderId,
                    amount,
                    transactionId: paymentIntent.id,
                    success: true
                });

                onSuccess(paymentIntent);

            } else {

                // Step 3: Ödeme başarılı olduğunda payment tablosunu güncelle
                const response = ApiService.updatePayment({
                    orderId,
                    amount,
                    transactionId: paymentIntent.id,
                    success: false
                });
            }

        } catch (error) {
            console.log("Ödeme hatası: ", error);

            // Daha detaylı hata bilgisi
            if (error.response) {
                console.log("Server response:", error.response.data);
                console.log("Status:", error.response.status);

                // Backend'den gelen hata mesajını göster
                const errorMessage = error.response.data.message ||
                    error.response.data.error ||
                    'Bilinmeyen sunucu hatası';
                showError(`Sunucu hatası: ${errorMessage}`);
            } else if (error.request) {
                console.log("Request error:", error.request);
                showError("Sunucuya bağlanılamıyor. Lütfen internet bağlantınızı kontrol edin.");
            } else {
                showError(error.message || "Beklenmeyen bir hata oluştu");
            }
        } finally {
            setLoading(false);
        }
    }

    return (
        <form onSubmit={handleSubmit} className="payment-form">
            <ErrorDisplay />
            <SuccessDisplay />

            <div className="payment-form-group">
                <CardElement />
            </div>

            <button type="submit" disabled={!stripe || loading} className="pay-button">
                {loading ? "Ödeme yapılıyor..." : `Ödeme yap (${amount} TL)`}
            </button>
        </form>
    )

}


const Payment = ({ amount, orderId, onSuccess }) => {
    console.log("Amount to pay in TL: ", amount);

    return (
        <div className="payment-container">
            <h2>Ödemeyi Tamamla</h2>

            {/* Ödeme Bilgileri */}
            <div className="payment-info">
                <div className="payment-detail">
                    <span>Sipariş Numarası:</span>
                    <span>{orderId}</span>
                </div>
                <div className="payment-detail">
                    <span>Ödenecek Tutar:</span>
                    <span className="payment-amount">{amount.toFixed(2)} TL</span>
                </div>
            </div>

            <Elements stripe={stripeInstance}>
                <PaymentForm
                    amount={amount}
                    orderId={orderId}
                    onSuccess={onSuccess}
                />
            </Elements>
        </div>
    )
}

export default Payment;