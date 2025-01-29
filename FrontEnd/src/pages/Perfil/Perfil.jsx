import '../../styles/Perfil.css'

import { useSelector } from "react-redux"
import { useParams } from "react-router-dom"
import { Link } from 'react-router-dom'

import useRelacao from "../../hooks/useRelacao.js"
import useUserProfile from "../../hooks/useUserProfile.js"

import ModalSeguidores from "../../components/Modals/ModalSeguidores.jsx"
import ModalSeguidos from "../../components/Modals/ModalSeguidos.jsx"
import ModalDeixarDeSeguir from "../../components/Modals/ModalDeixarDeSeguir.jsx"

import NotFoundMessage from "../../components/NotFound/NotFoundMessage"
import Loading from "../../components/Loading/Loading"
import ProtectedContent from "../../components/ProtectedContent/ProtectedContent"
import InfoPerfil from "../../components/Perfil/InfoPerfil.jsx"
import ProfileMenu from '../../components/Perfil/ProfileMenu.jsx'
import UserPosts from './UserPosts.jsx'
import useTogglePerfilContent from '../../hooks/useTogglePerfilContent.js'
import UserInteractions from './UserInteractions.jsx'
import UserMedia from './UserMedia.jsx'
import ButtonBack from '../../components/ButtonBack/ButtonBack.jsx'
import { useEffect } from 'react'
import usePostagem from '../../hooks/usePostagem.js'
import ListarPostagens from '../../components/Postagens/ListarPostagens.jsx'


function Perfil({observerDarkMode, setObserverDarkMode}) {

    const user = useSelector((state) => state.user.user)
    const {username} = useParams()

    const {userProfile, message, setMessage} = useUserProfile(username)
    const {relacao, numeroSeguidores, numeroSeguidos, seguidores, seguidos, seekRelationship, getProfileUserRelationships, getCommomRelationships, createRelationship, removeRelationship} = useRelacao()
    const {showUserPosts, showUserInterations, showUserMedia, toggleUserPosts, toggleUserInterations, toggleUserMedia} = useTogglePerfilContent()
 
    function renderProfileContent() {
        if (toggleUserPosts) return <UserPosts observerDarkMode={observerDarkMode} setObserverDarkMode={setObserverDarkMode} user={user} userProfile={userProfile}/>
        if (toggleUserInterations) return <UserInteractions observerDarkMode={observerDarkMode} setObserverDarkMode={setObserverDarkMode} user={user} userProfile={userProfile}/>
        if (toggleUserMedia) return <UserMedia observerDarkMode={observerDarkMode} setObserverDarkMode={setObserverDarkMode} user={user} userProfile={userProfile}/>
    }

    useEffect(() => {
        if (userProfile) {
            seekRelationship(user, userProfile)
            getProfileUserRelationships(userProfile)
            getCommomRelationships(user, userProfile)
        }
    }, [userProfile])

    return (
        <>
            {userProfile ? (
                <div className="perfil ">
                    <div className="w-100 d-flex justify-content-between">
                        <ButtonBack />
                        <Link to="/settings" className="d-flex d-lg-none icon-settings"><ion-icon name="settings-outline"></ion-icon></Link>
                    </div>
                    <InfoPerfil userProfile={userProfile} user={user} numeroSeguidores={numeroSeguidores} numeroSeguidos={numeroSeguidos} relacao={relacao} removeRelationShip={removeRelationship} createRelationship={createRelationship} />

                        <ProfileMenu showUserPosts={showUserPosts} showUserInteractions={showUserInterations} showUserMedia={showUserMedia}/>
                    <hr />
                    <div className="posts mt-4">
                        <div className="text-center">
                            {(userProfile.username == user.username) || (relacao && relacao.aceito) || (!userProfile.privado) ? (
                                <>
                                    {renderProfileContent()}
                                </>
                            ) : (
                                <div className="mt-5">
                                    <ProtectedContent />
                                </div>
                            )}
                        </div>
                        
                    </div>
                </div>
            ) : (
                <>
                    <div className="w-100">
                        <ButtonBack />
                    </div>
                    <div className="perfil d-flex justify-content-center align-items-center px-3">
                        {message ? (
                            <>
                            {message.status == 404 && (
                                <NotFoundMessage msg="Usuário não encontrado"/>
                            )}
                            </>
                        ) : (
                            <Loading />
                        )}
                    </div>
                </>
            )}
            {userProfile && (
                <div>
                    <ModalDeixarDeSeguir removerRelacao={removeRelationship} userProfile={userProfile} setOberserDarkMode={setObserverDarkMode}/>
                    <ModalSeguidores seguidores={seguidores}/>
                    <ModalSeguidos seguidos={seguidos}/>
                </div>
            )}
        </>
    )
}

export default Perfil