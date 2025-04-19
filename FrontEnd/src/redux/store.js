import { configureStore } from "@reduxjs/toolkit";
import darkModeReducer from "./reducer/darkmodeReducer"
import { persistStore, persistReducer } from "redux-persist";
import storage from 'redux-persist/lib/storage';

const persistConfigDarkMode = {
    key: 'darkMode',
    storage
}

const persistedReducerDarkMode = persistReducer(persistConfigDarkMode, darkModeReducer)

const store = configureStore({
    reducer : {
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