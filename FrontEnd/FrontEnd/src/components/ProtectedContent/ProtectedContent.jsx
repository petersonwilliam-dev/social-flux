import cadeado from '../../assets/img/cadeado.webp'

function ProtectedContent() {
    return (
        <div className="text-center">
            <img src={cadeado} alt="Conteúdo protegido" />
            <p className="lead">Conteúdo protegido</p>
        </div>
    )
}

export default ProtectedContent