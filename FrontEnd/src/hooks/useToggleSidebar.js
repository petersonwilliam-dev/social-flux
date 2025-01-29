import { useState } from "react"

function useToggleSidebar() {
    
    const [toggleListaRelacoesNaoAceitas, setToggleListaRelacoesNaoAceitas] = useState(false)
    const [toggleSearchUsuario, setToggleSearchUsuario] = useState(false)
    const [toggleSidebar, setToggleSidebar] = useState(true)
    const [toggleNotifications, setToggleNotifications] = useState(false)

    function showListaRelacoesNaoAceitas() {
        setToggleListaRelacoesNaoAceitas(true)
        setToggleSearchUsuario(false)
        setToggleSidebar(false)
        setToggleNotifications(false)
    }

    function showSidebarMenu() {
        setToggleSidebar(true)
        setToggleListaRelacoesNaoAceitas(false)
        setToggleSearchUsuario(false)
        setToggleNotifications(false)
    }

    function showSearchUsuarios() {
        setToggleSearchUsuario(true)
        setToggleListaRelacoesNaoAceitas(false)
        setToggleSidebar(false)
        setToggleNotifications(false)
    }

    function showNotifications() {
        setToggleSearchUsuario(false)
        setToggleListaRelacoesNaoAceitas(false)
        setToggleSidebar(false)
        setToggleNotifications(true)
    }

    return {toggleSidebar, toggleSearchUsuario, toggleListaRelacoesNaoAceitas, toggleNotifications, showListaRelacoesNaoAceitas, showSearchUsuarios, showSidebarMenu, showNotifications}
}

export default useToggleSidebar