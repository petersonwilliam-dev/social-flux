import { useEffect, useState } from "react"
import { useParams } from "react-router-dom"

import usePostagem from "../../hooks/usePostagem"
import Postagem from "../../components/Postagens/Postagem"
import ListarPostagens from "../../components/Postagens/ListarPostagens"
import ButtonBack from '../../components/ButtonBack/ButtonBack'

function PostPage({observerDarkMode, setObserverDarkMode, user, }) {

    const {id} = useParams()
    const [postagem, setPostagem] = useState(null)
    const [respostas, setRespostas] = useState([])
    const {buscarPostagem, removerPostagem, buscarRespostas} = usePostagem()

    useEffect(() => {

        const fetchPostagem = async () => {
            const response = await buscarPostagem(id)
            setPostagem(response)
        }

        const fetchRespostas = async () => {
            const response = await buscarRespostas(id)
            setRespostas(response)
        }

        fetchRespostas()
        fetchPostagem()
    }, [id])

    return (
        <div className="postPage">
            <div className="w-100 my-2">
                <ButtonBack />
            </div>
            {postagem && (
                <Postagem postagem={postagem} user={user} excluirPostagem={removerPostagem} observerDarkMode={observerDarkMode} setObserverDarkMode={setObserverDarkMode}/>
            )}
            {respostas.length > 0 && (
                <ListarPostagens postagens={respostas} setPostagens={setRespostas} usuarioLogado={user} idReferencia={postagem.id} observerDarkMode={observerDarkMode} setObserverDarkMode={setObserverDarkMode}/>
            )}
        </div>
    )
}

export default PostPage