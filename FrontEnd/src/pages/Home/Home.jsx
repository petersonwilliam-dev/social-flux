import ListarPostagens from "../../components/Postagens/ListarPostagens";
import NoContent from "../../components/NotContent/NotContent";
import usePostagem from "../../hooks/usePostagem";
import { useEffect } from "react";

function Home({observerDarkMode, setObserverDarkMode, user}) {

    const {homePosts, setHomePosts, getHomePosts} = usePostagem()

    useEffect(() => {
        getHomePosts(user)
    }, [])

    return (
        <div className="home">
            {homePosts.length > 0 ? (
                <ListarPostagens postagens={homePosts} usuarioLogado={user} setPostagens={setHomePosts} classe="postagem-main" observerDarkMode={observerDarkMode} setObserverDarkMode={setObserverDarkMode}/>
            ) : (
                <div className="px-3">
                    <NoContent msg="Não há postagens para ver, siga mais usuários para ver suas postagens" />
                </div>
            )}
        </div>
    )
}

export default Home