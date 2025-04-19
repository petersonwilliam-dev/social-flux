import ButtonBack from "../../components/ButtonBack/ButtonBack"

import "../../styles/Settings.css"
import { useDispatch } from "react-redux"
import { toggle } from "../../redux/reducer/darkmodeReducer"
import useUser from "../../hooks/useUser"
import ModalAction from "../../components/Modals/ModalAction"
import useAuth from "../../hooks/useAuth"

function Settings({darkModeActivated, setObserverDarkMode, user}) {

    const dispatch = useDispatch()
    const {excluirUsuario} = useUser()
    const { logout } = useAuth()

    function darkMode() {
        dispatch(toggle())
    }

    return (
        <section className="settings">
            <div className="w-100 my-2">
                <ButtonBack />
            </div>
            <div className="px-4">
                <h2 className="display-4 mb-4">Configurações</h2>
                <div className="my-2">
                    <div className="border-top border-bottom border-dark-emphasis py-2">
                        <p className="subtitle">Como o contúdo será exibido para você</p>
                        <ul className="nav d-flex flex-column">
                            <li>
                                <div className="form-check form-switch">
                                    <input className="form-check-input" type="checkbox" role="switch" id="darkMode" onClick={darkMode} defaultChecked={darkModeActivated}/>
                                    <label htmlFor="darkMode" className="form-check-label">Modo escuro</label>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <div className="border-bottom border-dark-emphasis py-2">
                        <p className="subtitle">Entrar</p>
                        <ul className="nav d-flex flex-column">
                            <li data-bs-toggle="modal" data-bs-target="#modalLogout" style={{cursor: "pointer"}}>
                                <p className="text-danger d-flex align-items-center fs-6"><ion-icon style={{fontSize: "25px"}} name="log-out-outline"></ion-icon> Sair</p>
                            </li>
                            <li data-bs-toggle="modal" data-bs-target="#modalExcluirConta" style={{cursor: "pointer"}}>
                                <p className="text-danger d-flex align-items-center fs-6"><ion-icon style={{fontSize: "25px"}} name="close-circle-outline"></ion-icon> Excluir conta</p>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <ModalAction id="modalLogout" title="Deseja sair?" action={logout} titleAction="Sair" colorText="red" setOberserDarkMode={setObserverDarkMode}/>
            <ModalAction id="modalExcluirConta" title="Deseja excluir a conta?" action={excluirUsuario} titleAction="Excluir conta" colorText="red" setOberserDarkMode={setObserverDarkMode} />
        </section>
    )
}

export default Settings