import ButtonBack from '../../components/ButtonBack/ButtonBack'
import useUserNotifications from '../../hooks/useUserNotifications'
import MenuNotifications from '../../components/Notifications/MenuNotifications'
import toggleNotificationsPage from '../../hooks/toggleNotificationPage'

import "../../styles/NotificationsPage.css"
import ListNotifications from '../../components/NotificationSidebar/ListNotifications'
import ListaRelacoesNaoAceitas from '../../components/Relacao/ListaRelcoesNaoAceitas'
import useRelacao from '../../hooks/useRelacao'
import { useEffect } from 'react'

function Notifications({user}) {

    const {userNotifications, viewNotifications} = useUserNotifications(user)
    const {unacceptedRelationships, getUnacceptedRelationships, acceptRelationship, removeRelationship} = useRelacao()
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