import { useState } from "react";

function toggleNotificationsPage() {
    const [toggleNotifications, setToggleNotifications] = useState(true)
    const [toggleSolicitacoes, setToggleSolicitacoes] = useState(false)

    function showNotifications() {
        setToggleNotifications(true)
        setToggleSolicitacoes(false)
    }

    function showSolicitacoes() {
        setToggleNotifications(false)
        setToggleSolicitacoes(true)
    }

    return {toggleNotifications, toggleSolicitacoes, showNotifications, showSolicitacoes}

}

export default toggleNotificationsPage