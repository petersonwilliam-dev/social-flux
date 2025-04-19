import NoContent from "../../components/NotContent/NotContent"
import ListarPostagens from "../../components/Postagens/ListarPostagens"
import useUserPosts from "../../hooks/useUserPosts"

function UserMedia({user, userProfile, observerDarkMode, setObserverDarkMode}) {

    const {postagenMidiaUsuario, setPostagensMidiaUsuario, message} = useUserPosts(userProfile)

    return (
        <>
            {message && (
                <Toasts mensagem={message}/>
            )}
            {postagenMidiaUsuario.length > 0 ? (
                <ListarPostagens postagens={postagenMidiaUsuario} usuarioLogado={user} setPostagens={setPostagensMidiaUsuario} observerDarkMode={observerDarkMode} setObserverDarkMode={setObserverDarkMode} />
            ) : (
                <NoContent msg="Este usuário não postou nenhuma mídia"/>
            )}
        </>
    )
}

export default UserMedia