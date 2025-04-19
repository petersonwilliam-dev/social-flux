import { Link } from "react-router-dom"
import { formatDate } from "../../assets/util/convertDates"
import { useEffect, useState } from "react"
import token from "../../config/getToken"

import API_BASE_URL from "../../config/apiConfig"
import foto_perfil from "../../assets/img/profile_photo_default.png"
import PostSecondary from "./PostSecondary"

import "../../styles/Postagem.css"

import Curtida from "../ActionsPostagem/Curtida"
import Comentario from "../ActionsPostagem/Comentario"
import usePostagem from "../../hooks/usePostagem"
import useCurtida from "../../hooks/useCurtida"
import FormComentar from "../Forms/FormComentar"
import DropdownActions from "./DropdownActions"
import ModalExcluirPostagem from "../Modals/ModalExcluirPostagem"
import Toasts from "../Toasts/Toasts"

function Postagem({postagem, user, excluiPostagem, observerDarkMode, setObserverDarkMode}) {

    const [mostrarFormularioComentar, setMostrarFormularioCometar] = useState(false)
    const {buscarNumeroRespostas, criarPostagem, message} = usePostagem()
    const {buscarNumeroCurtidas, message: messageCurtida} = useCurtida()
    const [numeroRespostas, setNumeroRespostas] = useState(0)
    const [numeroCurtidas, setNumeroCurtidas] = useState(0)

    useEffect(() => {
        const fetchNumeroCurtida = async () => {
            const response = await buscarNumeroCurtidas(postagem)
            setNumeroCurtidas(response)
        }
        const fetchNumeroRespostas = async () => {
            const response = await buscarNumeroRespostas(postagem.id)
            setNumeroRespostas(response)
        }

        fetchNumeroCurtida()
        fetchNumeroRespostas()
        setObserverDarkMode(!observerDarkMode)
    }, [postagem])

    function showFormularioComentar() {
        setMostrarFormularioCometar(!mostrarFormularioComentar)
    }

    return (
        <>
            {message || messageCurtida && (
                <Toasts mensagem={message ? message : messageCurtida}/>
            )}
            {postagem.postagem && (
                <PostSecondary postagem={postagem.postagem} usuarioLogado={user} excluirPostagem={excluiPostagem} idReferencia={postagem.id} observerDarkMode={observerDarkMode} setObserverDarkMode={setObserverDarkMode}/>
            )}
            <div className="post dark-m my-3" id={postagem.id}>
                <div className="d-flex px-4">
                    <div className="">
                        <div className="foto-perfil">
                            <Link>
                                {postagem.usuario.foto_perfil ? (
                                    <img src={`${API_BASE_URL}/img/${postagem.usuario.id}/${postagem.usuario.foto_perfil}`} className="rounded-circle foto-perfil" alt={`Foto de perfil de ${postagem.usuario.username}`} />
                                ) : (
                                    <img src={foto_perfil} className="rounded-circle foto-perfil" alt={`Foto de perfil de ${postagem.usuario.username}`} />
                                )}
                            </Link>
                        </div>
                    </div>
                    <div className="w-100 ps-4 d-flex flex-column">
                        <div className="d-flex infos-user mb-1 justify-content-between">
                            <Link className="d-flex text-decoration-none align-items-center w-75 text-truncate" to={`/perfil/${postagem.usuario.username}`}>
                                {postagem.usuario.nome}
                                <p className="mx-2 my-0 text-truncate">@{postagem.usuario.username}</p>
                            </Link>
                            {postagem.usuario.id === user.id && (
                                <DropdownActions postagem={postagem} setObserverDarkMode={setObserverDarkMode}/>
                            )}
                        </div>
                        <Link className="text-decoration-none text-body" to={`/postagem/${postagem.id}`}>
                            {postagem.postagem && (
                                <p className="text-secondary referente">Em resposta a <Link to={`/perfil/${postagem.postagem.usuario.username}`} className="text-decoration-none text-primary">@{postagem.postagem.usuario.username}</Link></p>
                            )}
                            {postagem.legenda && (
                                <div className="text-start legenda">
                                    <p>{postagem.legenda}</p>
                                </div>
                            )}
                            {postagem.imagem && (
                                <div className="d-flex justify-content-start mt-2">
                                    <img src={`${API_BASE_URL}/img/${postagem.usuario.id}/${postagem.imagem}`} className="post-image"/>
                                </div>
                            )}
                            <div className="data-publicacao">
                                <p className="text-secondary">
                                    {formatDate(postagem.data_postagem)}
                                </p>
                            </div>
                        </Link>
                        <div className="infos-post d-flex">
                            <div className="likes me-2 d-flex">
                                {numeroCurtidas} Curtidas
                            </div>
                            <div className="d-flex coments mx-2">
                                {numeroRespostas} Comentários
                            </div>
                        </div>
                    </div>
                </div>
                <hr />
                <div className="actions d-flex px-4">
                    <Curtida user={user} postagem={postagem} setNumeroCurtidas={setNumeroCurtidas} token={token}/>
                    <Comentario showFormularioComentar={showFormularioComentar}/>
                </div>
                {mostrarFormularioComentar && (
                    <div className="px-3 my-3">
                        <FormComentar user={user} postagem={postagem} criarPostagem={criarPostagem} />                        
                    </div>
                )}
                <hr />
                <ModalExcluirPostagem postagem={postagem} excluirPostagem={excluiPostagem}/>
            </div>
        </>
    )
}

export default Postagem