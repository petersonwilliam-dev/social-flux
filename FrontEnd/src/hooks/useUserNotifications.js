import { useEffect, useState } from "react";
import API_BASE_URL from "../config/apiConfig";
import axios from "axios";
import token from "../config/getToken";

function useUserNotifications(user) {
    const [userNotifications, setUserNotifications] = useState([])
    const [notificationsNotSeen, setNotificationsNotSeen] = useState(0)

    useEffect(() => {
        axios.get(`${API_BASE_URL}/notification?userId=${user.id}`, {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        })
        .then(response => setUserNotifications(response.data))
        .catch(err => console.log(err))

        axios.get(`${API_BASE_URL}/notification?userId=${user.id}&count=True`, {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        })
        .then(response => setNotificationsNotSeen(response.data))
        .catch(err => console.log(err))

    }, [user])

    function viewNotifications() {
        axios.patch(`${API_BASE_URL}/notification/${user.id}`, {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        })
        .catch(err => console.log(err))
    }

    return {userNotifications, notificationsNotSeen,setNotificationsNotSeen, viewNotifications}
}

export default useUserNotifications