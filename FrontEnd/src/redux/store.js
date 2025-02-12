import { configureStore } from "@reduxjs/toolkit";
import userReducer from "./reducer/userReducer";
import darkModeReducer from "./reducer/darkmodeReducer"
import { persistStore, persistReducer } from "redux-persist";
import storage from 'redux-persist/lib/storage';
import expireReduce from 'redux-persist-expire'

const expireReducer = expireReduce("user", {
    expireSeconds: 259200,
    expiredState: null,
    autoExpire: true
})

const persistConfigUser = {
    key: 'user',
    storage,
    transforms: [expireReducer]
}


const persistConfigDarkMode = {
    key: 'darkMode',
    storage
}

const persistedReducerUser = persistReducer(persistConfigUser, userReducer)
const persistedReducerDarkMode = persistReducer(persistConfigDarkMode, darkModeReducer)

const store = configureStore({
    reducer : {
        user: persistedReducerUser,
        darkMode: persistedReducerDarkMode
    },
    middleware: (getDefaultMiddleware) => 
        getDefaultMiddleware({
            serializableCheck: {
                ignoredActions: ['persist/PERSIST', 'persist/REHYDRATE'], // Ignorar ações do persist
            },
        }),
})

const persistor = persistStore(store)

export {store, persistor}