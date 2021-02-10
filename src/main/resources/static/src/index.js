import {Spinner} from '../spin/spin.js';
import {loginCheck} from "./utils.js";

const infoContainer = document.getElementById("info-text");
const mediaContainer = document.getElementById("media-list");
const inputContainer = document.getElementById("textInput");
const input = document.getElementById("searchInput");
const sectionTitle = document.getElementById("sectionTitle");
const modalInfo = document.getElementById("modal-info");
const modalEvent = document.getElementById("modal-event");
const darken = document.getElementById("darken");
const participantsContainer = document.querySelector("#list-participants");
const emailBtn = document.querySelector("#email-btn");
const emailInput = document.querySelector("#email-input");
const selectMonth = document.querySelector("#select-month");
const selectDate = document.querySelector("#select-date");

let type = "movie";
let dataList = [];
let selectedData;
let participantsList = [];

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

let today = new Date();

selectMonth.value = today.getMonth();
updateDateOptions();
selectDate.value = today.getDate();

function updateDateOptions(month) {
    console.log("updating");
    selectDate.innerHTML = ``;
    let date = new Date();
    date.setMonth(parseInt(selectMonth.value)+1, 0);
    console.log(date);
    for(let i = 0; i < date.getDate(); i++) {
        selectDate.innerHTML = selectDate.innerHTML.concat(`
            <option value="${i+1}">${i+1}</option>
        `);
    }
}

selectMonth.onchange = e => {
    console.log(e.currentTarget.value);
    updateDateOptions(selectMonth.value);
}

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
                modalInfo.style.display = "flex";
                darken.style.display = "block";
                selectedData = e.currentTarget.getAttribute("data");
                document.getElementById("movie-poster").src = dataList[selectedData].Poster;
                document.getElementById("movie-plot").textContent = dataList[selectedData].Plot;
                document.getElementById("movie-title").textContent = dataList[selectedData].Title;
                document.getElementById("movie-genre").textContent = dataList[selectedData].Genre;
                document.getElementById("movie-runtime").textContent = dataList[selectedData].Runtime;
                document.getElementById("movie-rating").textContent = dataList[selectedData].imdbRating + " | imdb";
                console.log("Hej, du har valt film:", dataList[selectedData].Title);
            });
        });
    })
});

darken.addEventListener("click", ()=>{
    closeAllModals();
});

document.querySelectorAll(".close-modal").forEach(a => {
    a.addEventListener("click", ()=>{
        closeAllModals();
    });
});

function closeAllModals() {
    modalEvent.style.display = "none";
    modalInfo.style.display = "none";
    darken.style.display = "none";
}

document.getElementById("event-form").addEventListener("click", ()=> {
    if(loginCheck() || true) {
        modalInfo.style.display = "none";
        modalEvent.style.display = "flex";
    
        participantsContainer.innerText = "";
        participantsList = [];
        document.querySelector("#summary").value = "Movie Night: "+dataList[selectedData].Title;
        document.querySelector("#desc").value = "Gott välkommen till filmkväll!\n\nFilmbeskrivning:\n"+dataList[selectedData].Plot;
    }
});

emailBtn.addEventListener("click", ()=>{
    participantsList.push(emailInput.value);
    emailInput.value = "";
    participantsContainer.innerText = "";
    for(let email of participantsList) {
        participantsContainer.innerHTML = participantsContainer.innerHTML.concat(`
            ${email}<br>
        `);
    }
});

async function fetchMedia(type){
    console.log("searching for " + input.value + " in "+type);
    return await (await fetch(`http://localhost:5500/rest/v1/media?title=${ input.value }&type=${ type }`)).json();
}
