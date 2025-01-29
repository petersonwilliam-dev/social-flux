import { Link } from "react-router-dom";

function SidebarMenu({showListaRelacoesNaoAceitas, relacoesNaoAceitas, showSearchUsuarios, showNotifications, notificationsNotSeen, setNotificationsNotSeen}) {

    return (
        <ul className="nav nav-pills flex-column mb-auto">
            <li>
                <Link to="/" className="nav-link d-flex align-items-center">
                    <ion-icon name="home-outline"></ion-icon>
                    Home
                </Link>
            </li>
            <li>
                <Link to="/criarPost" className="nav-link d-flex align-items-center">
                    <ion-icon name="create-outline"></ion-icon>
                    Criar postagem
                </Link>
            </li>
            <li>
                <Link onClick={showListaRelacoesNaoAceitas} className="nav-link d-flex align-items-center position-relative">
                    <ion-icon name="person-add-outline"></ion-icon>
                    <span className="position-absolute top-1 start-100 translate-middle badge rounded-pill bg-primary">
                        {relacoesNaoAceitas.length > 0 && (
                            <>
                                {relacoesNaoAceitas.length}
                            </>
                        )}
                    </span>
                    Solicitações de amizade
                </Link>
            </li>
            <li>
                <Link onClick={showSearchUsuarios} className="nav-link d-flex align-items-center">
                    <ion-icon name="search-outline"></ion-icon>
                    Buscar usuário
                </Link>
            </li>
            <li>
                <Link onClick={() => {
                    setNotificationsNotSeen(0)
                    showNotifications()
                }} className="nav-link d-flex align-items-center position-relative">
                    <ion-icon name="notifications-outline"></ion-icon>
                    <span className="position-absolute top-1 start-100 translate-middle badge rounded-pill bg-primary">
                        {notificationsNotSeen > 0 && (
                            <>
                                {notificationsNotSeen}
                            </>
                        )}
                    </span>
                    Notifications
                </Link>
            </li>
        </ul>
    )
}

export default SidebarMenu