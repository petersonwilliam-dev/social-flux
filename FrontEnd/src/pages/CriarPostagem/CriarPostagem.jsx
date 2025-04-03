import { useSelector } from "react-redux"
import FormCriarPostagem from "../../components/Forms/FormCriarPostagem"
import usePostagem from "../../hooks/usePostagem"
import ButtonBack from "../../components/ButtonBack/ButtonBack"
import Toasts from "../../components/Toasts/Toasts"


function CriarPostagem({user}) {

    const {criarPostagem, message, setMessage} = usePostagem()

    return (
        <div className="criar_postagem">
            <div className="w-100">
                <ButtonBack />
            </div>
            <div className="text-center">
                <p className="display-4">Criar postagem</p>
            </div>
            <div className="p-3 dark-m"> 
                {message && (
                    <Toasts msg={message.mensagem} type="danger" onClose={() => setMessage(null)}/>
                )}
                <FormCriarPostagem criarPostagem={criarPostagem} user={user}/>
            </div>
        </div>
    )
}

export default CriarPostagem