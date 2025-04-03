import React from 'react'
import { Navigate } from 'react-router-dom'
import SideBar from './Sidebar/Sidebar'
import ContainerMain from './ContainerMain'
import FooterBar from './FooterBar'
import decodeJwt from '../../assets/util/decode-jwt.js'
import token from '../../config/getToken.js'

function ProtectedRoute({element}) {

    
    const user = decodeJwt(token)

    return (
        <>
            {user ? (
                <div>
                    <div className="d-block d-lg-flex">
                        <div className="d-block d-lg-none w-100 py-2 border-bottom border-dark-emphasis text-center small-header sticky-top dark-m">
                            <a href="/" className='text-decoration-none'>
                                <h1 className='display-6'>Social Flux</h1>
                            </a>
                        </div>
                        <SideBar user={user} token={token}/>
                        <ContainerMain>
                            {React.cloneElement(element, {user})}
                        </ContainerMain>
                        <FooterBar user={user} token={token} />
                    </div>
                    
                </div>
            ) : (
                <Navigate to="/login"/>
            )}
        </>
    )
}

export default ProtectedRoute