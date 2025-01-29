import axios from 'axios'
import PostSecondary from './PostSecondary'

function ListarPostagens({postagens, setPostagens, usuarioLogado, idReferencia, observerDarkMode, setObserverDarkMode}) {

    function excluirPostagem(id) {
        axios.delete(`http://localhost:8000/postagem/${id}`)
        .then(() => {
            setPostagens(postagens.filter(postagem => postagem.id !== id))
        })
        .catch(err => console.log(err))
    }
    
    return (
        <div>
            {postagens.map((postagem, index) => (
                <div key={index} className="w-100 mb-5">
                    <PostSecondary postagem={postagem} usuarioLogado={usuarioLogado} excluirPostagem={excluirPostagem} idReferencia={idReferencia} observerDarkMode={observerDarkMode} setObserverDarkMode={setObserverDarkMode}/>
                </div>
            ))}
            <div className="w-100 text-center">
                <p className='text-secondary'>°</p>
            </div>
        </div>
    )
}

export default ListarPostagens