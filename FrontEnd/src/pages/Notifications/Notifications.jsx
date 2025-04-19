import ButtonBack from '../../components/ButtonBack/ButtonBack'
import useUserNotifications from '../../hooks/useUserNotifications'
import MenuNotifications from '../../components/Notifications/MenuNotifications'
import toggleNotificationsPage from '../../hooks/toggleNotificationPage'

import "../../styles/NotificationsPage.css"
import ListNotifications from "../../components/Notifications/ListNotifications"
import ListaRelacoesNaoAceitas from '../../components/Relacao/ListaRelcoesNaoAceitas'
import useRelacao from '../../hooks/useRelacao'
import { useEffect } from 'react'
import Toasts from '../../components/Toasts/Toasts'

function Notifications({user}) {

    const {userNotifications, viewNotifications, message} = useUserNotifications(user)
    const {unacceptedRelationships, getUnacceptedRelationships, acceptRelationship, removeRelationship, message: messageRelacao} = useRelacao()
    const {toggleNotifications, toggleSolicitacoes, showNotifications, showSolicitacoes} = toggleNotificationsPage()

    useEffect(() => {
        getUnacceptedRelationships(user)
    })

    function render() {
        if (toggleNotifications) return <ListNotifications notifications={userNotifications}/>
        if (toggleSolicitacoes) return <ListaRelacoesNaoAceitas relacoesNaoAceitas={unacceptedRelationships} aceitarRelacao={acceptRelationship} removerRelacao={removeRelationship} />
    }

    return (
        <div className="notifications">
            {message || messageRelacao && (
                <Toasts mensagem={message ? message : messageRelacao}/>
            )}
            <div className=" mb-2">
                <div className='d-flex' onClick={viewNotifications}>
                    <ButtonBack />
                </div>
            </div>
            <MenuNotifications showNotifications={showNotifications} showSolicitacoes={showSolicitacoes} />
            {render()}
        </div>
    )
}

export default Notifications