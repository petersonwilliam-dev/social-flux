import { Link } from "react-router-dom"
import API_BASE_URL from "../../config/apiConfig"

import '../../styles/Notifications.css'

function Notification({notification, viewed}) {

    function renderNotificationCategory(category) {
        if (category == 'LIKE') {
            return <ion-icon name="heart-outline" style={{color: 'red'}}></ion-icon>
        } else if (category == 'COMENT') {
            return <ion-icon name="chatbubble-outline" style={{color: 'blue'}}></ion-icon>
        } else if (category == 'FOLLOW') {
            return <ion-icon name="person-add-outline" style={{color: 'blue'}}></ion-icon>
        } else {
            return null
        }
    }

    return (
        <Link className={`notification d-flex border-bottom text-decoration-none  py-2 pe-3  ${viewed}`} to={notification.category == 'FOLLOW' ? `/perfil/${notification.sender.username}` : `/postagem/${notification.id_post}`}>
            <div className="mx-2 d-flex">
                {renderNotificationCategory(notification.category)}
                <div>
                    <img src={`${API_BASE_URL}/img/${notification.sender.id}/${notification.sender.foto_perfil}`} style={{width: '30px'}} alt="" className="rounded-circle" />
                </div>
            </div>
            <div className="d-flex flex-column">
                <p>
                    <span style={{fontWeight: '500'}}>{notification.sender.nome}</span> {notification.title}
                </p>
            </div>
        </Link>
    )
}

export default Notification