fetchMedia().then(a => {
    a.map(a=>{
        document.getElementById("mov").innerHTML = document.getElementById("mov").innerHTML.concat(`<img class="poster" src=${a.Poster} />`); 
    });
});

async function fetchMedia(){
    return await (await fetch("http://localhost:5500/rest/v1/media?title=super mario")).json();
}
