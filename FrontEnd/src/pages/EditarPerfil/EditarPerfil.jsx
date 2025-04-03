import { useSelector} from "react-redux"
import useEditarPerfil from "../../hooks/useEditarPerfil"
import foto_perfil from '../../assets/img/profile_photo_default.png'
import FormEditarPerfil from "../../components/Forms/FormEditarPerfil"
import FormAlterarFotoPerfil from "../../components/Forms/FormAlterarFotoPerfil"
import API_BASE_URL from "../../config/apiConfig"
import ButtonBack from '../../components/ButtonBack/ButtonBack'
import "../../styles/Modal.css"

import "../../styles/EditarPerfil.css"
import Toasts from "../../components/Toasts/Toasts"

function EditarPerfil({user}) {

    const {editarPerfil, atualizarFotoPerfil, message, setMessage} = useEditarPerfil(user)

    return (
        <div className="editar-perfil">
            <div className="w-100">
                <ButtonBack />
            </div>
            <div className="row">
                {message && (
                    <Toasts msg={message.mensagem} type="danger" />
                )}
                <div className="col">
                    <div className="d-flex justify-content-center align-items-center flex-column">
                        {user.foto_perfil ? (
                            <img src={`${API_BASE_URL}/img/${user.id}/${user.foto_perfil}`} className="rounded-circle my-2 foto" />
                        ) : (
                            <img src={foto_perfil} alt="Foto perfil"  className="rounded-circle my-2 foto"/>
                        )}
                        <button className="btn btn-dark" data-bs-toggle="modal" data-bs-target="#modalFotoDePerfil">Trocar foto perfil</button>
                    </div>
                </div>
            </div>
            <hr />
            <div className="row">
                <div className="col-12 col-xl-6 mb-3">
                    <FormEditarPerfil editarPerfil={editarPerfil} user={user}/>
                </div>
            </div>

            <div className="modal fade" id="modalFotoDePerfil" tabIndex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div className="modal-dialog">
                    <div className="modal-content dark-m">
                        <div className="modal-body">
                            <p className="lead text-center">Escolher nova foto de perfil</p>
                            <FormAlterarFotoPerfil atualizarFotoPerfil={atualizarFotoPerfil} />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default EditarPerfil