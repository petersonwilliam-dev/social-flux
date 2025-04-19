import useUserPosts from "../../hooks/useUserPosts"
import NoContent from "../../components/NotContent/NotContent"
import ListarPostagens from "../../components/Postagens/ListarPostagens"
import Toasts from "../../components/Toasts/Toasts"

function UserInteractions({userProfile, user, observerDarkMode, setObserverDarkMode}) {
    
    const {postsInteracoesUsuario, setPostsInteracoesUsuario, message} = useUserPosts(userProfile)
    
    return (
        <>
            {message && (
                <Toasts mensagem={message}/>
            )}
            {postsInteracoesUsuario.length > 0 ? (
                <ListarPostagens postagens={postsInteracoesUsuario} usuarioLogado={user} setPostagens={setPostsInteracoesUsuario}  observerDarkMode={observerDarkMode} setObserverDarkMode={setObserverDarkMode} />
            ) : (
                <NoContent msg="Não há publicações" />
            )}
        </>
    )
}

export default UserInteractions