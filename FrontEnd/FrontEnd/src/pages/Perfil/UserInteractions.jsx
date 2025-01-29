import useUserPosts from "../../hooks/useUserPosts"
import NoContent from "../../components/NotContent/NotContent"
import ListarPostagens from "../../components/Postagens/ListarPostagens"

function UserInteractions({userProfile, user, observerDarkMode, setObserverDarkMode}) {
    
    const {postsInteracoesUsuario, setPostsInteracoesUsuario} = useUserPosts(userProfile)
    
    return (
        <>
            {postsInteracoesUsuario.length > 0 ? (
                <ListarPostagens postagens={postsInteracoesUsuario} usuarioLogado={user} setPostagens={setPostsInteracoesUsuario}  observerDarkMode={observerDarkMode} setObserverDarkMode={setObserverDarkMode} />
            ) : (
                <NoContent msg="Não há publicações" />
            )}
        </>
    )
}

export default UserInteractions