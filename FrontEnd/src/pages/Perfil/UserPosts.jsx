import useUserPosts from "../../hooks/useUserPosts"
import ListarPostagens from "../../components/Postagens/ListarPostagens"
import NoContent from "../../components/NotContent/NotContent"

function UserPosts({userProfile, user, observerDarkMode, setObserverDarkMode}) {

    const {postagensUsuario, setPostagensUsuario, message} = useUserPosts(userProfile)

    return (
        <>
            {message && (
                <Toasts mensagem={message}/>
            )}
            {postagensUsuario.length > 0 ? (
                <ListarPostagens postagens={postagensUsuario} usuarioLogado={user} setPostagens={setPostagensUsuario} classe="postagem-main" observerDarkMode={observerDarkMode} setObserverDarkMode={setObserverDarkMode}/>
            ) : (
                <NoContent msg="Não há publicações" />
            )}
        </>
    )
}

export default UserPosts