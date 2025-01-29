function MenuNotifications({showNotifications, showSolicitacoes}) {

    function activatedLink(index) {
        const links = document.getElementsByClassName('menu-item')

        for (let i = 0; i < links.length; i++) {
            if (links[i].classList.contains('notification-menu')) {
                links[i].classList.remove('notification-menu')
            }
        }

        links[index].classList.add('notification-menu')
    }

    return (
        <div className="d-flex border-bottom border-dark-emphasis shadow-bottom">
            <div className='text-center w-100 menu-item notification-menu' onClick={() => {
                activatedLink(0)
                showNotifications()
            }}>
                <p className='mb-1'>Notificações</p>
            </div>
            <div className='text-center menu-item w-100' onClick={() => {
                activatedLink(1)
                showSolicitacoes()
            }}>
                <p className='mb-1'>Solicitações</p>
            </div>
        </div>
    )
}

export default MenuNotifications