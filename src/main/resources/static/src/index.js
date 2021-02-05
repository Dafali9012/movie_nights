import {Spinner} from '../spin/spin.js';

const mediaContainer = document.getElementById("media-list");
const inputContainer = document.getElementById("textInput");
const input = document.getElementById("searchInput");
const sectionTitle = document.getElementById("sectionTitle");
let type = "movie";

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
spinner.spin(mediaContainer);

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
    sectionTitle.innerText = e.currentTarget.id=="info"?"Home":e.currentTarget.id=="movie"?"Movies":e.currentTarget.id=="series"?"Series":"";
    if(e.currentTarget.id=="info") {
        inputContainer.style.display = "none"
    }
    else {
        type = e.currentTarget.id;
        inputContainer.style.display = "flex"
    }
}

document.getElementById("btn-Submit").addEventListener('click', () => {
    mediaContainer.innerHTML = "";
    spinner.spin(mediaContainer);
    fetchMedia(type).then(a => {
        spinner.stop();
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
