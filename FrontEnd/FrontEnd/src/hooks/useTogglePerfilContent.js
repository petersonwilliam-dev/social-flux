import { useState } from 'react'

function useTogglePerfilContent() {

    const [toggleUserPosts, setToggleUserPosts] = useState(true)
    const [toggleUserInterations, setToggleUserInterations] = useState(false)
    const [toggleUserMedia, setToggleUserMedia] = useState(false)

    function showUserPosts() {
        setToggleUserPosts(true)
        setToggleUserInterations(false)
        setToggleUserMedia(false)
    }

    function showUserInterations() {
        setToggleUserPosts(false)
        setToggleUserInterations(true)
        setToggleUserMedia(false)
    }

    function showUserMedia() {
        setToggleUserPosts(false)
        setToggleUserInterations(false)
        setToggleUserMedia(true)
    }

    return {showUserPosts, showUserInterations, showUserMedia, toggleUserPosts, toggleUserInterations, toggleUserMedia}
}

export default useTogglePerfilContent