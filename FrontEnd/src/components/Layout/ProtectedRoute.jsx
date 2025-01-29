import { Navigate } from 'react-router-dom'
import { useSelector } from 'react-redux'
import SideBar from './Sidebar'
import ContainerMain from './ContainerMain'
import FooterBar from './FooterBar'

function ProtectedRoute({element}) {
    
    const user = useSelector((state) => state.user.user)

    return (
        <>
            {user ? (
                <div>
                    <div className="d-block d-lg-flex">
                        <SideBar user={user}/>
                        <ContainerMain>
                            {element}
                        </ContainerMain>
                        <FooterBar user={user} />
                    </div>
                    
                </div>
            ) : (
                <Navigate to="/login"/>
            )}
        </>
    )
}

export default ProtectedRoute