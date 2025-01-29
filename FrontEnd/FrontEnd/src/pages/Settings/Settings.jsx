import ButtonBack from "../../components/ButtonBack/ButtonBack"
import "../../styles/Settings.css"
import { useDispatch } from "react-redux"
import { toggle } from "../../redux/reducer/darkmodeReducer"

function Settings({darkModeActivated}) {

    const dispatch = useDispatch()

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
                    <div className="display border-top border-bottom border-dark-emphasis py-2">
                        <p className="subtitle">Como o contúdo será exibido para você</p>
                        <ul className="nav d-flex flex-column">
                            <li>
                                <div className="form-check form-switch">
                                    <input class="form-check-input" type="checkbox" role="switch" id="darkMode" onClick={darkMode} defaultChecked={darkModeActivated}/>
                                    <label htmlFor="darkMode" className="form-check-label">Modo escuro</label>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </section>
    )
}

export default Settings