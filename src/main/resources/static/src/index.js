

fetchMedia().then((a) => {
//    console.log(a)
    document.getElementById("mov").innerHTML=`
    <h2>
    ${
        a.map(a => {
            return a.Title
        })
    }
    </h2>
`});

async function fetchMedia(){
    return await (await fetch("http://localhost:5500/rest/v1/media?title=deadpool")).json();
}