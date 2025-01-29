import { Link } from "react-router-dom"
import Notification from "./Notification"
import useUserNotifications from "../../hooks/useUserNotifications"
import ListNotifications from "../NotificationSidebar/ListNotifications"

function NotificationsSidebar({showSidebarMain, user}) {

    const {userNotifications, viewNotifications} = useUserNotifications(user)

    return (
        <div className="mb-auto">
            <div className="text-start w-100 mb-4 ps-3">
                <Link onClick={() => {
                    viewNotifications()
                    showSidebarMain()
                }} className="nav-link d-flex justify-content-start align-items-center"><ion-icon name="chevron-back-outline"></ion-icon>Voltar</Link>
            </div>
            <ListNotifications notifications={userNotifications}/>
        </div>
    )
}

export default NotificationsSidebar