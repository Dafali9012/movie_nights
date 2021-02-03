let type;

document.getElementById("infoSite").addEventListener('click', function() {
    document.getElementById("textInput").style.visibility = "hidden";
})
document.getElementById("movSite").addEventListener('click', function() {
    document.getElementById('mov').innerHTML = "";
    document.getElementById("textInput").style.visibility = "visible";
    type = 'movie';
    printContent('mov');
})
document.getElementById("serSite").addEventListener('click', function() {
    document.getElementById('show').innerHTML = "";
    document.getElementById("textInput").style.visibility = "visible";
    type = 'series';
    printContent('show');
})

async function printContent(elementId){
document.getElementById("btn-Submit").addEventListener('click', function() {
    document.getElementById(elementId).innerHTML = "";
    fetchMovie().then(a => {
        a.map(a=>{
            document.getElementById(elementId).innerHTML = document.getElementById(elementId).innerHTML.concat(`
            <img class="poster" src=${a.Poster} alt="poster"/>`);
        });
    })
})}

async function fetchMovie(){
    console.log("searching for " + document.getElementById("searchInput").value + " in "+type);
    let result = document.getElementById("searchInput").value;
    return await (await fetch(`http://localhost:5500/rest/v1/media?title=${result}&type=${type}` )).json();
}


/*
Hash:

A hash - # within a hyperlink specifies an html element id to which the window should be scrolled.

href="#some-id" would scroll to an element on the current page such as <div id="some-id">. 
*/
