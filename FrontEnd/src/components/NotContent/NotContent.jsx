import post from '../../assets/img/posts.png'

function NoContent({msg}) {
    return(
        <div className="w-100 text-center my-4">
            <img src={post} alt="No content" style={{width: '100px'}}/>
            <p className="lead mt-4">{msg}</p>
        </div>
    )
}

export default NoContent