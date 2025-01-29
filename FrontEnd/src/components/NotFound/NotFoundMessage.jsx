import img from '../../assets/img/notfound.png'

function NotFoundMessage({msg}) {
    return (
        <div className="text-center">
            <p className="display-5 mt-5 mb-5">{msg}</p>
            <img src={img} alt="Not found message" style={{width: '200px'}}/>
        </div>
    )
}

export default NotFoundMessage