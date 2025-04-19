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

function App() {

  const darkModeActivated = useSelector((store) => store.darkMode.darkMode)
  const [observerDarkMode, setObserverDarkMode] = useState(false)

  useEffect(() => {
    toggleDarkMode(darkModeActivated)
  }, [darkModeActivated, observerDarkMode])
  
  return (
    <div>
      <Routes>
        <Route path="/login" element={<Login />}/>
        <Route path="/" exact element={<ProtectedRoute  element={<Home  observerDarkMode={observerDarkMode} setObserverDarkMode={setObserverDarkMode}/>}/>}/>
        <Route path="/perfil/:username" element={<ProtectedRoute  element={<Perfil   observerDarkMode={observerDarkMode} setObserverDarkMode={setObserverDarkMode}/>}/>} />
        <Route path="/editar" element={<ProtectedRoute  element={<EditarPerfil  />}/>} />
        <Route path="/criarPost" element={<ProtectedRoute  element={<CriarPostagem  />}/>} />
        <Route path="/postagem/:id" element={<ProtectedRoute  element={<PostPage  observerDarkMode={observerDarkMode} setObserverDarkMode={setObserverDarkMode} />} />}/>
        <Route path="/search" element={<ProtectedRoute  element={<SearchUser  />}/>} />
        <Route path="/notifications" element={<ProtectedRoute  element={<Notifications  />}/>} />
        <Route path="/settings" element={<ProtectedRoute  element={<Settings darkModeActivated={darkModeActivated} setObserverDarkMode={setObserverDarkMode} />}/>} />
      </Routes>
    </div>
  )
}

export default App
