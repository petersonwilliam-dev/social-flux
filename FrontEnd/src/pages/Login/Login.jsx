import '../../styles/Login.css'
import FormSignin from '../../components/Forms/FormSignin'
import Toasts from '../../components/Toasts/Toasts'
import { useState } from 'react'
import FormSignup from '../../components/Forms/FormSignup'
import { Navigate } from 'react-router-dom'
import useAuth from '../../hooks/useAuth'
import decodeJwt from '../../assets/util/decode-jwt.js'
import token from '../../config/getToken.js'


function Login() {

    const [showLogin, setShowLogin] = useState(true)
    const {login, register, message, setMessage} = useAuth()

    function toggleForm() {
        setShowLogin(!showLogin)
    }

    if (token) {
        const user = decodeJwt(token)
        if (user) {
            return <Navigate to="/" replace/>
        }
    }

    return (
        <div className="Login">
            {message && (
                <Toasts msg={message}/>
            )}
            <div className="row g-0 bg-primary">
                <div className="col-12 col-lg-6 bg-light container-login p-5 d-flex align-items-center justify-content-center">
                    {showLogin ? (
                        <FormSignin toggleForm={toggleForm} buscarUsuario={login} />
                    ) : ( 
                        <div>
                            <FormSignup  toggleForm={toggleForm} adicionarUsuario={register} />
                        </div>
                    )}
                    
                </div>
                <div className="col-lg-6 d-none d-lg-flex align-items-center justify-content-center">
                    <h1 className="text-light display-1"> Social Flux</h1>
                </div>
            </div>
        </div>
    )
}

export default Login