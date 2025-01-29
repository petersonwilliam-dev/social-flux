import { Link } from "react-router-dom"
import { useDispatch } from "react-redux"
import { logout } from "../../redux/reducer/userReducer"

import '../../styles/SideBar.css'

import ListaAmizadesNaoAceitas from '../Relacao/ListaAmizadesNaoAceitas'
import foto_perfil_default from '../../assets/img/profile_photo_default.png'
import SidebarMain from "../SidebarMain/SidebarMain"
import SearchUsuario from "../SearchUsuario/SearchUsuario"
import useToggleSidebar from "../../hooks/useToggleSidebar"
import useSearchUsuario from "../../hooks/useSearchUsuario"
import NotificationsSidebar from "../Notifications/NotificationsSideBar"
import useUserNotifications from "../../hooks/useUserNotifications"
import API_BASE_URL from "../../config/apiConfig"
import useRelacao from "../../hooks/useRelacao"
import { useEffect } from "react"

function SideBar({user}) {

    const dispatch = useDispatch()    
    const {unacceptedRelationships,getUnacceptedRelationships, acceptRelationship, removeRelationship} = useRelacao()
    const {toggleSidebar, toggleSearchUsuario, toggleListaRelacoesNaoAceitas, toggleNotifications, showListaRelacoesNaoAceitas, showSearchUsuarios, showSidebarMain, showNotifications} = useToggleSidebar()
    const {setSearch, search, usuariosPesquisados} = useSearchUsuario()
    const {notificationsNotSeen, setNotificationsNotSeen} = useUserNotifications(user)

    useEffect(() => {
        getUnacceptedRelationships(user)
    }, [])

    function renderSidebar() {
        if (toggleSidebar) return <SidebarMain relacoesNaoAceitas={unacceptedRelationships} showListaRelacoesNaoAceitas={showListaRelacoesNaoAceitas} showSearchUsuarios={showSearchUsuarios} showNotifications={showNotifications} notificationsNotSeen={notificationsNotSeen} setNotificationsNotSeen={setNotificationsNotSeen} user={user}/>
        if (toggleSearchUsuario) return <SearchUsuario showSidebarMain={showSidebarMain} setSearch={setSearch} usuariosPesquisados={usuariosPesquisados} search={search} user={user}/>
        if (toggleListaRelacoesNaoAceitas) return <ListaAmizadesNaoAceitas relacoesNaoAceitas={unacceptedRelationships} showSidebarMain={showSidebarMain} aceitarRelacao={acceptRelationship} removerRelacao={removeRelationship} />
        if (toggleNotifications) return <NotificationsSidebar showSidebarMain={showSidebarMain} user={user} />
    }

    return (
        <section>
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
                        <li><a className="dropdown-item d-flex align-items-center" style={{cursor: 'pointer'}} onClick={() => dispatch(logout())}><ion-icon name="log-out-outline"></ion-icon> Sair</a></li>
                    </ul>
                </div>
            </div>
        </section> 
    )
}

export default SideBar