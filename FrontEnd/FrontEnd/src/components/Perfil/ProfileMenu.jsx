function ProfileMenu({showUserPosts, showUserInteractions, showUserMedia}) {

    function activatedLink(index) {
        const links = document.getElementsByClassName('profile-menu-link')

        for (let i = 0; i < links.length; i++) {
            if (!links[i].classList.contains('text-decoration-none')) {
                links[i].classList.add('text-decoration-none')
            }
        }

        links[index].classList.remove('text-decoration-none')
    }

    return (
        <nav className="nav justify-content-between px-2 px-md-3 px-lg-5 profile-menu" style={{fontWeight: '600'}}>
            <a className="profile-menu-link link-offset-2 " aria-current="page" onClick={() => {
                activatedLink(0)
                showUserPosts()  
            }}>Posts</a>
            <a className="profile-menu-link text-decoration-none link-offset-2" onClick={() => {
                activatedLink(1)
                showUserInteractions()    
            }}>Posts e interações</a>
            <a className="profile-menu-link text-decoration-none link-offset-2" onClick={() => {
                activatedLink(2)
                showUserMedia()
            }}>Mídia</a>
        </nav>
    )
}

export default ProfileMenu