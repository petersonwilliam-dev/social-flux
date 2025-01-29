import { Route, Routes } from 'react-router-dom';
import { useSelector } from 'react-redux';

import Login from './pages/Login/Login';
import ProtectedRoute from './components/Layout/ProtectedRoute';
import Perfil from './pages/Perfil/Perfil';
import Home from './pages/Home/Home'
import EditarPerfil from './pages/EditarPerfil/EditarPerfil';
import CriarPostagem from './pages/CriarPostagem/CriarPostagem';
import PostPage from './pages/PostPage/PostPage';
import SearchUser from './pages/SearchUser/SearchUser';
import Notifications from './pages/Notifications/Notifications';
import Settings from './pages/Settings/Settings';
import { toggleDarkMode } from './assets/util/darkMode.js'
import { useEffect, useState } from 'react';
import Toasts from './components/Toasts/Toasts.jsx';

function App() {

  const user = useSelector((store) => store.user.user)
  const darkModeActivated = useSelector((store) => store.darkMode.darkMode)
  const [message, setMessage] = useState(null)
  const [observerDarkMode, setObserverDarkMode] = useState(false)
  const error = "Não dá mais"

  useEffect(() => {
    toggleDarkMode(darkModeActivated)
  }, [darkModeActivated, observerDarkMode])
  
  return (
    <div>
      <Routes>
        <Route path="/login" element={<Login />}/>
        <Route path="/" exact element={<ProtectedRoute element={<Home observerDarkMode={observerDarkMode} setObserverDarkMode={setObserverDarkMode}/>}/>}/>
        <Route path="/perfil/:username" element={<ProtectedRoute element={<Perfil user={user} observerDarkMode={observerDarkMode} setObserverDarkMode={setObserverDarkMode}/>}/>} />
        <Route path="/editar" element={<ProtectedRoute element={<EditarPerfil />}/>} />
        <Route path="/criarPost" element={<ProtectedRoute element={<CriarPostagem />}/>} />
        <Route path="/postagem/:id" element={<ProtectedRoute element={<PostPage observerDarkMode={observerDarkMode} setObserverDarkMode={setObserverDarkMode} />} />}/>
        <Route path="/search" element={<ProtectedRoute element={<SearchUser user={user}/>}/>} />
        <Route path="/notifications" element={<ProtectedRoute element={<Notifications user={user}/>}/>} />
        <Route path="/settings" element={<ProtectedRoute element={<Settings user={user} darkModeActivated={darkModeActivated}/>}/>} />
      </Routes>
    </div>
  )
}

export default App
