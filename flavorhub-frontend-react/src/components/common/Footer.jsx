const Footer = () => {

    return (
        <footer className="footer">
            <div className="footer-content">
                <p>&copy; {new Date().getFullYear()} Flavor Hub. Tüm hakları saklıdır.</p>
                <div className="footer-links">
                    <a href="/home" className="footer-link">Kullanım Koşulları</a>
                    <a href="/home" className="footer-link">KVKK ve Gizlilik Politikası</a>
                    <a href="/home" className="footer-link">Bizim ile iletişime geçin!</a>
                </div>
            </div>
        </footer>
    )
}

export default Footer;