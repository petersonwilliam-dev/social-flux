import { Link } from "react-router-dom"
import { useEffect } from "react"
import useAuth from "../../../hooks/useAuth"

import "../../../styles/Sidebar.css"

import SidebarRelacoes from './SidebarRelacoes'
import foto_perfil_default from "../../../assets/img/profile_photo_default.png"
import SidebarMenu from "./SidebarMenu"
import SearchUsuario from "./SearchUsuario"
import useToggleSidebar from "../../../hooks/useToggleSidebar"
import useSearchUsuario from "../../../hooks/useSearchUsuario"
import NotificationsSidebar from "./NotificationsSideBar"
import useUserNotifications from "../../../hooks/useUserNotifications"
import API_BASE_URL from "../../../config/apiConfig"
import useRelacao from "../../../hooks/useRelacao"
import Toasts from "../../Toasts/Toasts"


function SideBar({user}) {

    const { logout } = useAuth()
    const {unacceptedRelationships,getUnacceptedRelationships, acceptRelationship, removeRelationship, message: messageRelacao} = useRelacao()
    const {toggleSidebar, toggleSearchUsuario, toggleListaRelacoesNaoAceitas, toggleNotifications, showListaRelacoesNaoAceitas, showSearchUsuarios, showSidebarMenu, showNotifications} = useToggleSidebar()
    const {setSearch, search, usuariosPesquisados, message: messageSearchUsuario} = useSearchUsuario()
    const {notificationsNotSeen, setNotificationsNotSeen, message : messageNotification} = useUserNotifications(user)

    useEffect(() => {
        getUnacceptedRelationships(user)
    }, [])

    function renderSidebar() {
        if (toggleSidebar) return <SidebarMenu relacoesNaoAceitas={unacceptedRelationships} showListaRelacoesNaoAceitas={showListaRelacoesNaoAceitas} showSearchUsuarios={showSearchUsuarios} showNotifications={showNotifications} notificationsNotSeen={notificationsNotSeen} setNotificationsNotSeen={setNotificationsNotSeen} user={user}/>
        if (toggleSearchUsuario) return <SearchUsuario showSidebarMenu={showSidebarMenu} setSearch={setSearch} usuariosPesquisados={usuariosPesquisados} search={search} user={user} />
        if (toggleListaRelacoesNaoAceitas) return <SidebarRelacoes relacoesNaoAceitas={unacceptedRelationships} showSidebarMenu={showSidebarMenu} aceitarRelacao={acceptRelationship} removerRelacao={removeRelationship} />
        if (toggleNotifications) return <NotificationsSidebar showSidebarMenu={showSidebarMenu} user={user} />
    }

    return (
        <section>
            {messageNotification && (<Toasts mensagem={messageNotification}/>)}
            {messageRelacao && (<Toasts mensagem={messageRelacao}/>)}
            {messageSearchUsuario && (<Toasts mensagem={messageSearchUsuario}/>)}
            
            <div className="sidebar d-none d-lg-flex flex-column flex-shrink-0 dark-m sticky-top" style={{width: "280px", height: "100vh"}}>
                <Link to="/" className="d-flex align-items-center my-3 ps-3 mb-md-0 me-md-auto text-decoration-none">
                    <span className="fs-4">Social Flux</span>
                </Link>
                <hr />
                {renderSidebar()}
                <hr />
                <div className="dropdown p-3 ">
                    <a href="#" className="d-flex align-items-center text-decoration-none dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                        {user.foto_perfil ? (
                            <img src={`${API_BASE_URL}/img/${user.id}/${user.foto_perfil}`} className="rounded-circle" style={{width: '30px', marginRight: '10px'}}/>    
                        ) : (
                            <img src={foto_perfil_default} alt="" style={{width: '30px', marginRight: '10px'}}/>
                        )}
                        {user.username}
                    </a>
                    <ul className="dropdown-menu text-small">
                        <li><Link className="dropdown-item d-flex align-items-center" to={`/perfil/${user.username}`}><ion-icon name="person-circle-outline"></ion-icon>Perfil</Link></li>
                        <li><Link to="/settings" className="dropdown-item d-flex align-items-center" ><ion-icon name="settings-outline"></ion-icon>Configurações</Link></li>
                        <li><hr /></li>
                        <li><a className="dropdown-item d-flex align-items-center" style={{cursor: 'pointer'}} onClick={logout}><ion-icon name="log-out-outline"></ion-icon> Sair</a></li>
                    </ul>
                </div>
            </div>
        </section> 
    )
}

export default SideBar