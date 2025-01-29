function calcTempPost(data_postagem) {

    const date = new Date(data_postagem[0], data_postagem[1] - 1, data_postagem[2], data_postagem[3], data_postagem[4], data_postagem[5])

    const currentDate = new Date()
    const diffMilliseconds = currentDate - date
    const diffSeconds = diffMilliseconds / 1000
    const diffMinutes = diffSeconds / 60
    const diffHours = diffMinutes / 60
    const diffDays = diffHours / 24

    const formattedDate = new Intl.DateTimeFormat("pt-BR").format(date)

    if (diffSeconds > 1 && diffSeconds < 60) {
        return ` ${Math.floor(diffSeconds)} seg`
    } else if (diffMinutes < 60) {
        return `${Math.floor(diffMinutes)} min`
    } else if (diffHours < 24) {
        return `${Math.floor(diffHours)} h`
    } else if (diffDays < 7) {
        return `${Math.floor(diffDays)} d`
    } else {
        return `${formattedDate}`
    }
}

function formatDate(data_postagem) {
    
    const date = `${data_postagem[2]}/${data_postagem[1]}/${data_postagem[0]} - ${data_postagem[3]}:${data_postagem[4]}`
    return date
}

export {calcTempPost, formatDate}