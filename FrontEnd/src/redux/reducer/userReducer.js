import { createSlice } from "@reduxjs/toolkit";

const userSlice = createSlice({
    name: "users",
    initialState : {
        user: null
    },
    reducers: {
        alterarUsuario : (state, action) => {
            state.user = action.payload
        },
        logout: state => {
            state.user = null
        }
    }
})

export const {alterarUsuario, logout} = userSlice.actions

export default userSlice.reducer