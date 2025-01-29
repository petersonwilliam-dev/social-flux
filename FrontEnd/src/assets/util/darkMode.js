
export function toggleDarkMode(darkModeActivated) {
    if (darkModeActivated) {
        activeDarkMode()
    } else {
        desativeDarkMode()
    }
}

function activeDarkMode() {
    
    const elements = document.getElementsByClassName('dark-m')
    document.getElementsByTagName('body')[0].style.background = '#292929'

    for (let i = 0; i < elements.length; i++) {
        elements[i].classList.add('dark')
    }
}

function desativeDarkMode() {
    
    const elements = document.getElementsByClassName('dark-m')
    document.getElementsByTagName('body')[0].style.background = '#fff'

    for (let i = 0; i < elements.length; i++) {
        elements[i].classList.remove('dark')
    }
}