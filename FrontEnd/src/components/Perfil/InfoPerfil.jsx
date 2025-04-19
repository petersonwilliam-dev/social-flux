import NumeroRelacoes from "./NumeroRelacoes"
import API_BASE_URL from "../../config/apiConfig"

import foto_default from "../../assets/img/profile_photo_default.png"
import RelacoesEmComum from "../Relacao/RelacoesEmComum"
import ActionsProfile from "./ActionsProfile"

function InfoPerfil({userProfile, user,numeroSeguidores, numeroSeguidos, relacao, createRelationship, removeRelationShip, setObserverDarkMode}) {
    
    return (
        <div className="row g-0 px-2 px-lg-5 py-3">
            <div className="col-3 d-flex justify-content-center align-items-start">
                <div className="mx-3">
                    {userProfile.foto_perfil ? (
                        <img src={`${API_BASE_URL}/img/${userProfile.id}/${userProfile.foto_perfil}`} className="foto-perfil rounded-circle" alt={`Foto de perfil de ${user.username}`}/>
                    ) : (
                        <img src={foto_default} className="foto-perfil rounded-circle" alt={`Foto de perfil de ${user.username}`}/>
                    )}
                </div>
            </div>
            <div className="col-9 d-flex flex-column">
                <div className="d-flex justify-content-between w-100">
                    <div className="d-flex flex-column w-100">
                        <p className="display-6 ajustavel-4">{userProfile.username}</p>
                        <p className="h5 ajustavel-6">{userProfile.nome}</p>
                    </div>
                    <div className="d-flex justify-content-end w-100">
                        <NumeroRelacoes numero={numeroSeguidores} relacao="Seguidores" id="#seguidores" />
                        <NumeroRelacoes numero={numeroSeguidos} relacao="Seguidos" id="#seguidos" />
                    </div>
                </div>
                <div className="mt-1">
                    {userProfile.biografia && (
                        <p className="lead ajustavel-6">{userProfile.biografia}</p>
                    )}
                </div>
                {userProfile != user && (
                    <RelacoesEmComum user={user} profileUser={userProfile} setObserverDarkMode={setObserverDarkMode}/>
                )}
            </div>
            <div className="d-flex justify-content-end">
                <ActionsProfile userProfile={userProfile} user={user} relacao={relacao} criarRelacao={createRelationship} removerRelacao={removeRelationShip} />
            </div>
        </div>
    )
}

export default InfoPerfil