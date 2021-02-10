import {Spinner} from '../spin/spin.js';

const infoContainer = document.getElementById("info-text");
const mediaContainer = document.getElementById("media-list");
const inputContainer = document.getElementById("textInput");
const input = document.getElementById("searchInput");
const sectionTitle = document.getElementById("sectionTitle");
const modal = document.getElementById("modal");
const darken = document.getElementById("darken");
let type = "movie";
let dataList = [];


let options = {
    lines: 13, // The number of lines to draw
    length: 38, // The length of each line
    width: 17, // The line thickness
    radius: 45, // The radius of the inner circle
    scale: 0.5, // Scales overall size of the spinner
    corners: 1, // Corner roundness (0..1)
    speed: 1, // Rounds per second
    rotate: 0, // The rotation offset
    animation: 'spinner-line-fade-quick', // The CSS animation name for the lines
    direction: 1, // 1: clockwise, -1: counterclockwise
    color: '#ffffff', // CSS color or array of colors
    fadeColor: 'transparent', // CSS color or array of colors
    top: '0', // Top position relative to parent
    left: '0', // Left position relative to parent
    shadow: '0 0 1px transparent', // Box-shadow for the lines
    zIndex: 2000000000, // The z-index (defaults to 2e9)
    className: 'spinner', // The CSS class to assign to the spinner
    position: 'relative', // Element positioning
  };




  
let spinner = new Spinner(options);

document.getElementById("home").addEventListener('click', e => {
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
    dataList = [];
    sectionTitle.innerText = e.currentTarget.id=="home"?"Home":e.currentTarget.id=="movie"?"Movies":e.currentTarget.id=="series"?"Series":"";
    if(e.currentTarget.id=="home") {
        inputContainer.style.display = "none"
        infoContainer.style.display = "flex"
    }
    else {
        type = e.currentTarget.id;
        inputContainer.style.display = "flex"
        infoContainer.style.display = "none"
    }
}

document.getElementById("btn-Submit").addEventListener('click', () => {
    mediaContainer.innerHTML = "";
    dataList = [];
    spinner.spin(mediaContainer);
    fetchMedia(type).then(a => {
        spinner.stop();
        a.map((a,i)=>{
            dataList.push(a);
            mediaContainer.innerHTML = mediaContainer.innerHTML.concat(`
            <img class="poster" src="${a.Poster}" data="${i}" alt="${a.Title}" />`);
        });
        document.querySelectorAll(".poster").forEach(el=>{
            el.addEventListener('click', e=>{
                modal.style.display = "flex";
                darken.style.display = "block";
                document.getElementById("movie-poster").src = dataList[e.currentTarget.getAttribute("data")].Poster;
                document.getElementById("movie-plot").textContent = dataList[e.currentTarget.getAttribute("data")].Plot;
                document.getElementById("movie-title").textContent = dataList[e.currentTarget.getAttribute("data")].Title;
                document.getElementById("movie-genre").textContent = dataList[e.currentTarget.getAttribute("data")].Genre;
                document.getElementById("movie-runtime").textContent = dataList[e.currentTarget.getAttribute("data")].Runtime;
                document.getElementById("movie-rating").textContent = dataList[e.currentTarget.getAttribute("data")].imdbRating + " | imdb";
                console.log("Hej, du har valt film:", dataList[e.currentTarget.getAttribute("data")].Title);
            });
        });
    })
});

darken.addEventListener("click", ()=>{
    modal.style.display = "none";
    darken.style.display = "none";
});

document.getElementById("close").addEventListener("click", ()=>{
    modal.style.display = "none";
    darken.style.display = "none";
});

async function fetchMedia(type){
    console.log("searching for " + input.value + " in "+type);
    return await (await fetch(`http://localhost:5500/rest/v1/media?title=${ input.value }&type=${ type }`)).json();
}
