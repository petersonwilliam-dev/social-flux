import Notification from "../Notifications/Notification"

function ListNotifications({notifications}) {
    return (
        <div className="notifications">
            {notifications.map((notification, index) => (
                <div key={index}>
                    {notification.viewed ? (
                        <Notification notification={notification} viewed="viewed"/>
                    ) : (
                        <Notification notification={notification} viewed="no-viewed"/>
                    )}
                </div>
            ))}
        </div>
    )
}

export default ListNotifications