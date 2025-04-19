import { useEffect, useState } from "react";
import API_BASE_URL from "../config/apiConfig";
import axios from "axios";
import token from "../config/getToken";

function useUserNotifications(user) {

    const [message, setMessage] = useState(null)
    const [userNotifications, setUserNotifications] = useState([])
    const [notificationsNotSeen, setNotificationsNotSeen] = useState(0)

    useEffect(() => {
        axios.get(`${API_BASE_URL}/notification?userId=${user.id}`, {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        })
        .then(response => setUserNotifications(response.data))
        .catch(err => {
            setMessage(err.response.data)
            setInterval(() => setMessage(null), 10000)
            setUserNotifications([])
        })

        axios.get(`${API_BASE_URL}/notification?userId=${user.id}&count=True`, {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        })
        .then(response => setNotificationsNotSeen(response.data))
        .catch(err => {
            setMessage(err.response.data)
            setInterval(() => {
                setMessage(null)
            }, 10000)
            setNotificationsNotSeen(0)
        })

    }, [user])

    function viewNotifications() {
        axios.patch(`${API_BASE_URL}/notification/${user.id}`, null,{
            headers: {
                "Authorization": `Bearer ${token}`
            }
        })
        .then(() => {
            console.log("Deu certo")
        })
        .catch(err => {
            setMessage(err.response.data)
            setInterval(() => {
                setMessage(null)
            }, 10000)
        })
    }

    return {userNotifications, notificationsNotSeen,setNotificationsNotSeen, viewNotifications, message}
}

export default useUserNotifications