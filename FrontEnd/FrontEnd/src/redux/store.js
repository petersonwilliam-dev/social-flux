import { configureStore } from "@reduxjs/toolkit";
import userReducer from "./reducer/userReducer";
import darkModeReducer from "./reducer/darkmodeReducer"
import { persistStore, persistReducer } from "redux-persist";
import storage from 'redux-persist/lib/storage';

const persistConfig = {
    key: 'root',
    storage
}

const persistedReducerUser = persistReducer(persistConfig, userReducer)
const persistedReducerDarkMode = persistReducer(persistConfig, darkModeReducer)

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