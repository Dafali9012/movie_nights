let signOutButton = document.getElementById("signOutButton");
let signInButton = document.getElementById("signInButton");
let eventButton = document.getElementById("event");
let currentUserImage = document.getElementById("currentUserImage");
let currentUserName = document.getElementById("currentUserName");
let currentUser = document.getElementById("currentUser");
let auth2;
//onLoadCallback();

function onLoadCallback() {
    start();
    signInButton.addEventListener('click', () => {
        signIn();
    });
}

function start() {
    gapi.load('auth2', () => {
        auth2 = gapi.auth2.init({
            client_id: "834224170973-rafg4gcu10p2dbjk594ntg8696ucq06q.apps.googleusercontent.com",
            scope: "https://www.googleapis.com/auth/calendar"
        });
    });
}

function signIn(){
    auth2.grantOfflineAccess().then(signInCallback);
}

function signOut() {
    auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
        console.log('User signed out.');
    });
    signInButton.style.setProperty('display','inline');
    signOutButton.style.setProperty('display', 'none');
    currentUser.style.setProperty('display', 'none');
    currentUserName.removeChild(currentUserName.firstChild);
}


async function signInCallback(authResult) {
    console.log('authResult', authResult);
    if (authResult['code']) {
        let result = await fetch('/api/storeauthcode', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/octet-stream; charset=utf-8',
                'X-Requested-With': 'XMLHttpRequest',
            },
            body: authResult['code']
        });
        console.log(result);
    }else {
        console.log("there was an error")
    }
    showUserInfoAndSignOutButton();
}

function showUserInfoAndSignOutButton(){
    let googleUser = gapi.auth2.getAuthInstance().currentUser.get();
    let accessToken = googleUser.uc.access_token;
    let profile = googleUser.getBasicProfile();
    signInButton.style.display = 'none';
    signOutButton.style.setProperty('display', 'block');
    currentUser.style.setProperty('display', 'block');
    currentUserImage.src = profile.getImageUrl();
    currentUserName.appendChild(document.createTextNode(profile.getName()));
    /*
    if(accessToken){
        sendAccessTokenToServer(accessToken);
    }
    */
}

/*
async function sendAccessTokenToServer(accessToken){
    fetch(`http://localhost:5500/rest/v1/calendar/events/${accessToken}`)
        .then((res) => console.log(res))
        .catch(err => console.log("Error:", err))
}
*/
