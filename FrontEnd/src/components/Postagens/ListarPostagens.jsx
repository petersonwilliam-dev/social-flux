import axios from 'axios'
import PostSecondary from './PostSecondary'
import token from '../../config/getToken'
import API_BASE_URL from '../../config/apiConfig'
import { useState } from 'react'
import Toasts from '../Toasts/Toasts'

function ListarPostagens({postagens, setPostagens, usuarioLogado, idReferencia, observerDarkMode, setObserverDarkMode}) {

    const [message, setMessage] = useState(null)

    function excluirPostagem(id) {
        axios.delete(`${API_BASE_URL}/postagem/${id}`, {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        })
        .then(() => {
            setPostagens(postagens.filter(postagem => postagem.id !== id))
        })
        .catch(err => {
            setMessage(err.response.data)
            setInterval(() => setMessage(null), 10000)
        })
    }
    
    return (
        <div>
            {message && (
                <Toasts mensagem={message} /> 
            )}
            {postagens.map((postagem, index) => (
                <div key={index} className="w-100 mb-5">
                    <PostSecondary token={token} postagem={postagem} usuarioLogado={usuarioLogado} excluirPostagem={excluirPostagem} idReferencia={idReferencia} observerDarkMode={observerDarkMode} setObserverDarkMode={setObserverDarkMode}/>
                </div>
            ))}
            <div className="w-100 text-center">
                <p className='text-secondary'>°</p>
            </div>
        </div>
    )
}

export default ListarPostagens