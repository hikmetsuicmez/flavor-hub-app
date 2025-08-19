import { useEffect, useState } from "react"

const ErrorDisplay = ({ message, onDismiss }) => {

    useEffect(() => {
        const timer = setTimeout(() => {
            onDismiss();
        }, 5000)

        return () => clearTimeout(timer);
    }, [message, onDismiss]);

    if (!message) return null;

    return (
        <div className="error-display">
            <div className="error-content">
                <span className="error-message">{message}</span>
                <div className="error-progress"></div>
            </div>
        </div>
    )
};

const SuccessDisplay = ({ message, onDismiss }) => {

    useEffect(() => {
        const timer = setTimeout(() => {
            onDismiss();
        }, 5000)

        return () => clearTimeout(timer);
    }, [message, onDismiss]);

    if (!message) return null;

    return (
        <div className="success-display">
            <div className="success-content">
                <span className="success-message">{message}</span>
                <div className="success-progress"></div>
            </div>
        </div>
    )
};

export const useError = () => {
    const [errorMessage, setErrorMessage] = useState(null);
    const [successMessage, setSuccessMessage] = useState(null);

    const showError = (message) => {
        setSuccessMessage(null); // Success mesajını temizle
        setErrorMessage(message);
    }
    const showSuccess = (message) => {
        setErrorMessage(null); // Error mesajını temizle
        setSuccessMessage(message);
    }
    const dismissError = () => {
        setErrorMessage(null);
    }
    const dismissSuccess = () => {
        setSuccessMessage(null);
    }

    return {
        ErrorDisplay: () => {
            return <ErrorDisplay
                message={errorMessage}
                onDismiss={dismissError} />
        },
        SuccessDisplay: () => {
            return <SuccessDisplay
                message={successMessage}
                onDismiss={dismissSuccess} />
        },
        showError,
        showSuccess,
        dismissError,
        dismissSuccess,
    }
}

export default ErrorDisplay;