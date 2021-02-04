const mediaContainer = document.getElementById("media-list");
const inputContainer = document.getElementById("textInput");
const input = document.getElementById("searchInput");
let type = "movie";

document.getElementById("info").addEventListener('click', e => {
    changePage(e);
})
document.getElementById("movie").addEventListener('click', e => {
    changePage(e);
})
document.getElementById("series").addEventListener('click', e => {
    changePage(e);
})

function changePage(e) {
    mediaContainer.innerHTML = "";
    input.value = "";
    
    if(e.currentTarget.id=="info") {
        inputContainer.style.visibility = "hidden"
    }
    else {
        type = e.currentTarget.id;
        inputContainer.style.visibility = "visible"
    }
}

document.getElementById("btn-Submit").addEventListener('click', () => {
    mediaContainer.innerHTML = "";
    fetchMedia(type).then(a => {
        a.map(a=>{
            console.log(a);
            mediaContainer.innerHTML = mediaContainer.innerHTML.concat(`
            <img class="poster" src=${a.Poster} data=${a} alt=${a.Title} />`);
        });
    })
});

async function fetchMedia(type){
    console.log("searching for " + input.value + " in "+type);
    return await (await fetch(`http://localhost:5500/rest/v1/media?title=${ input.value }&type=${ type }`)).json();
}
