import { Link } from "react-router-dom"
import useUserNotifications from "../../hooks/useUserNotifications"
import "../../styles/FooterBar.css"
import Toasts from "../Toasts/Toasts"

function FooterBar({user}) {

    const {notificationsNotSeen, setNotificationsNotSeen, message} = useUserNotifications(user)

    return (
        <footer className="fixed-bottom">
            {message && (
                <Toasts mensagem={message} />
            )}
            <ul className="d-flex justify-content-between d-lg-none nav nav-pills p-2 dark-m footer-bar">
                <li>
                    <Link to="/" className="nav-link d-flex align-items-center">
                        <ion-icon name="home-outline"></ion-icon>
                    </Link>
                </li>
                <li>
                    <Link to="/search" className="nav-link d-flex align-items-center">
                        <ion-icon name="search-outline"></ion-icon>
                    </Link>
                </li>
                <li>
                    <Link to="/criarPost" className="nav-link d-flex align-items-center">
                        <ion-icon name="create-outline"></ion-icon>
                    </Link>
                </li>
                <li>
                    <Link onClick={() => setNotificationsNotSeen(0)} to="/notifications" className="nav-link d-flex align-items-center position-relative ">
                        <ion-icon name="notifications-outline"></ion-icon>
                        <span className="position-absolute top-5 translate-middle badge rounded-pill bg-primary">
                            {notificationsNotSeen > 0 && (
                                <>
                                    {notificationsNotSeen}
                                </>
                            )}
                        </span>
                    </Link>
                </li>
                <li>
                    <Link to={`/perfil/${user.username}`} className="nav-link d-flex align-items-center">
                        <ion-icon name="person-circle-outline"></ion-icon>
                    </Link>
                </li>
            </ul>
        </footer>
    )
}

export default FooterBar