import { Navigate } from 'react-router-dom'
import { useSelector } from 'react-redux'
import SideBar from './Sidebar/Sidebar'
import ContainerMain from './ContainerMain'
import FooterBar from './FooterBar'

function ProtectedRoute({element}) {
    
    const user = useSelector((state) => state.user.user)

    return (
        <>
            {user ? (
                <div>
                    <div className="d-block d-lg-flex">
                        <div className="d-block d-lg-none w-100 py-2 border-bottom border-dark-emphasis text-center small-header sticky-top dark">
                            <a href="/" className='text-decoration-none'>
                                <h1 className='display-6'>Social Flux</h1>
                            </a>
                        </div>
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