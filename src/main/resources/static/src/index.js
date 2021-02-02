document.getElementById("focus").focus();
document.getElementById("btn-Submit").addEventListener('click', function() {
    document.getElementById("mov").innerHTML = "";
    fetchMedia().then(a => {
        a.map(a=>{
            document.getElementById("mov").innerHTML = document.getElementById("mov").innerHTML.concat(`
            <img class="poster" src=${a.Poster} alt="poster"/>`);
        });
    })
});




async function fetchMedia(){
    console.log("searching for " + document.getElementById("searchInput").value);
    return await (await fetch('http://localhost:5500/rest/v1/media?title=' + document.getElementById("searchInput").value)).json();
}
