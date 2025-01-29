import { createSlice } from "@reduxjs/toolkit";

const darkModeSlice = createSlice({
    name: 'darkMode',
    initialState : {
        darkMode: false
    },
    reducers: {
        toggle: state => {
            state.darkMode = !state.darkMode
        }
    }
})

export const {toggle} = darkModeSlice.actions

export default darkModeSlice.reducer