import API_BASE_URL from "../config/apiConfig";
import axios from "axios"

function useNotification() {

    function createNotificationLike(user, post) {
        
        const notification = {
            sender: JSON.stringify(user),
            receiver: JSON.stringify(post.usuario),
            title: `curtiu sua publicação`,
            description: `Curtiu seu post: ${post.legenda ? post.legenda : post.imagem}`,
            category: 'LIKE',
            id_post: post.id
        }

        axios.post(`${API_BASE_URL}/notification`, notification)
        .then(() => {
            console.log("Deu certo")
        })
        .catch(err => console.log(err))
    }

    function createNotificationComent(user, post, coment) {
        
        const notification = {
            sender: JSON.stringify(user),
            receiver: JSON.stringify(post.usuario),
            title: 'comentou na sua publicação',
            description: coment ? coment : null,
            category: 'COMENT',
            id_post: post.id
        }

        axios.post(`${API_BASE_URL}/notification`, notification)
        .then(() => {
            console.log("Deu certo")
        })
        .catch(err => console.log(err))
    }

    function createNotificationFollow(follower, followed) {

        const notification = {
            sender: JSON.stringify(follower),
            receiver: JSON.stringify(followed),
            title: `começou a seguir você`,
            description: '',
            category: 'FOLLOW',
            id_post: null
        }

        axios.post(`${API_BASE_URL}/notification`, notification)
        .then(() => {
            console.log("Deu certo")
        })
        .catch(err => console.log(err))
    }

    

    return {createNotificationLike, createNotificationComent, createNotificationFollow}
}

export default useNotification