import { Link } from "react-router-dom"
import { useState, useEffect } from "react"
import { calcTempPost } from "../../assets/util/convertDates"
import token from "../../config/getToken"

import perfilDefault from '../../assets/img/profile_photo_default.png'
import Comentario from '../ActionsPostagem/Comentario'
import Curtida from '../ActionsPostagem/Curtida'
import FormComentar from '../Forms/FormComentar'
import useCurtida from "../../hooks/useCurtida"
import usePostagem from "../../hooks/usePostagem"
import API_BASE_URL from "../../config/apiConfig"

import "../../styles/PostSecondary.css"
import DropdownActions from "./DropdownActions"
import ModalExcluirPostagem from "../Modals/ModalExcluirPostagem"


function PostSecondary({postagem, usuarioLogado, excluirPostagem, idReferencia, observerDarkMode, setObserverDarkMode}) {

    const [mostrarFormularioComentar, setMostrarFormularioCometar] = useState(false)
    const {buscarNumeroRespostas, criarPostagem} = usePostagem(token)
    const {buscarNumeroCurtidas} = useCurtida(token)
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
    }, [])

    function showFormularioComentar() {
        setMostrarFormularioCometar(!mostrarFormularioComentar)
    }

    return (
        <>
            {postagem.postagem && idReferencia && idReferencia !== postagem.postagem.id && (
                <PostSecondary postagem={postagem.postagem} usuarioLogado={usuarioLogado} excluirPostagem={excluirPostagem} idReferencia={idReferencia} observerDarkMode={observerDarkMode} setObserverDarkMode={setObserverDarkMode}/>
            )}
            <div className="post-secondary dark-m" id={postagem.id}>
                <div className="d-flex ps-2 ps-lg-4 pt-2 pb-1">
                    <div className="d-flex">
                        <div className="d-flex">
                            <div className="foto-usuario">
                                {postagem.usuario.foto_perfil ? (
                                    <Link to={`/perfil/${postagem.usuario.username}`}>
                                        <img src={`${API_BASE_URL}/img/${postagem.usuario.id}/${postagem.usuario.foto_perfil}`} alt={`Foto de ${postagem.usuario.username}`} className="foto-perfil-postagem rounded-circle"/>
                                    </Link>
                                ) : (
                                    <Link to={`/perfil/${postagem.usuario.username}`}>
                                        <img src={perfilDefault} alt={`Foto de ${postagem.usuario.username}`} className="foto-perfil-postagem rounded-circle"/>
                                    </Link>
                                )}
                            </div>
                        </div>
                    </div>
                    <div className="d-flex flex-column w-100 ps-2">
                        <div className="d-flex infos-user justify-content-between w-100 align-items-center">
                            <div className="d-flex text-truncate align-items-center">
                                <Link to={`/perfil/${postagem.usuario.username}`} className="me-2 text-decoration-none d-flex w-100 text-truncate">
                                    {postagem.usuario.nome}
                                    <p className="ms-2 text-truncate my-0">@{postagem.usuario.username}</p>
                                </Link>
                                <p className="text-secondary my-0">{calcTempPost(postagem.data_postagem)}</p>
                            </div>
                            {postagem.usuario.id == usuarioLogado.id && (
                                <div className="d-flex">
                                    <DropdownActions postagem={postagem} setObserverDarkMode={setObserverDarkMode}/>
                                </div>
                            )}
                        </div>
                        <Link className="text-decoration-none text-body mt-1" to={`/postagem/${postagem.id}`} >
                            {postagem.postagem && (
                                <div className="text-secondary text-break referente">
                                    <p className="text-start">Em resposta a <Link to={`/perfil/${postagem.postagem.usuario.username}`} className="text-primary text-decoration-none">@{postagem.postagem.usuario.username}</Link></p>
                                </div>
                            )}
                            <div className="text-start legenda text-break pe-2">
                                <p>{postagem.legenda}</p>
                            </div>
                            {postagem.imagem && (
                                <div className="d-flex justify-content-start mt-2">
                                    <img src={`${API_BASE_URL}/img/${postagem.usuario.id}/${postagem.imagem}`} className="post-image"/>
                                </div>
                            )}
                        </Link>
                        <div className="actions mt-1 d-flex">
                            <div className="likes d-flex me-2">
                                <Curtida user={usuarioLogado} postagem={postagem} setNumeroCurtidas={setNumeroCurtidas} token={token}/> {numeroCurtidas}
                            </div>
                            <div className="coments d-flex mx-2">
                                <Comentario showFormularioComentar={showFormularioComentar} />{numeroRespostas}
                            </div>
                        </div>
                        {mostrarFormularioComentar && (
                            <div className="w-100 mt-4 pe-2">
                                <FormComentar user={usuarioLogado} postagem={postagem} criarPostagem={criarPostagem}/>
                            </div>
                        )}
                        
                    </div>
                </div>
            </div>
            <hr />
            <ModalExcluirPostagem postagem={postagem} excluirPostagem={excluirPostagem}/>
        </>
    )
}

export default PostSecondary