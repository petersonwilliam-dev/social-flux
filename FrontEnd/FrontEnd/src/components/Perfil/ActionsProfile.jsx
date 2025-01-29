import { Link } from "react-router-dom"

function ActionsProfile({userProfile, user, relacao, removerRelacao, criarRelacao}) {
    return (
        <div className="d-flex align-items-end justify-content-end h-100 button-relacao">
            {userProfile && user.username == userProfile.username ? (
                <div className="d-flex justify-content-between align-items-end">
                    <Link to="/editar" className="btn btn-primary mx-1">Editar perfil</Link>
                </div>
            ) : (
                <>
                    {relacao ? (
                        <>
                            {relacao.aceito ? (
                                <div className="d-flex justify-content-between">
                                    <button className="btn btn-light d-flex align-items-center" data-bs-toggle="modal" data-bs-target="#modalDeixarDeSeguir"><ion-icon name="people-outline" className="relacao"></ion-icon> Seguindo</button>
                                </div>
                            ) : (
                                <div className="d-flex justify-content-between">
                                    <button className="btn btn-dark d-flex align-items-center" onClick={removerRelacao}>Solicitado</button>
                                </div>
                            )}
                        </>
                    ): (
                        <div className="d-flex justify-content-between">
                            <button className="btn btn-primary d-flex align-items-center" onClick={() => criarRelacao(user, userProfile)}><ion-icon name="person-add-outline"></ion-icon> Seguir</button>
                        </div>
                    )}
                </>
                                
            )}
        </div>
    )
}

export default ActionsProfile