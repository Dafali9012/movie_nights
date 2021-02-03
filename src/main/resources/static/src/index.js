//document.getElementById("focus").focus();
document.getElementById("btn-Submit").addEventListener('click', function() {
    document.getElementById("mov").innerHTML = "";
    fetchMovies().then(a => {
        a.map(a=>{
            document.getElementById("mov").innerHTML = document.getElementById("mov").innerHTML.concat(`
            <img class="poster" src=${a.Poster} alt="poster"/>`);
            console.log(a.Title+" printed!");
        });
    })
});

async function fetchMovies(){
    return await (await fetch('http://localhost:5500/rest/v1/media?title=' + document.getElementById("searchInput").value + '&type=movie')).json();
}
async function fetchSeries(){
    return await (await fetch('http://localhost:5500/rest/v1/media?title=' + document.getElementById("searchInput").value + '&type=series')).json();
}

/*
Hash:

A hash - # within a hyperlink specifies an html element id to which the window should be scrolled.

href="#some-id" would scroll to an element on the current page such as <div id="some-id">. 
*/
