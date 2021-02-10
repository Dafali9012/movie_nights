let accessToken;
let signOutButton = document.getElementById("signOutButton");
let eventButton = document.getElementById("event");
let currentUserImage = document.getElementById("currentUserImage");
let currentUserName = document.getElementById("currentUserName");
let currentUser = document.getElementById("currentUser");
let auth2;
let googleUser;

function onLoadCallback() {
  renderButton();
  start();
}

function start() {
  gapi.load('auth2', function() {
    auth2 = gapi.auth2.getAuthInstance({
      client_id: "834224170973-rafg4gcu10p2dbjk594ntg8696ucq06q.apps.googleusercontent.com",
      scope: "https://www.googleapis.com/auth/calendar.events"
    });
  });
}

function signOut() {
    auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
      console.log('User signed out.');
    });
    signOutButton.style.setProperty('display', 'none');
    currentUser.style.setProperty('display', 'none');
    currentUserName.removeChild(currentUserName.firstChild);
}

async function onSuccess(googleUser) {
  auth2.grantOfflineAccess().then(signInCallback);
  let profile = googleUser.getBasicProfile();
  let profileImage = profile.getImageUrl();
  let profileName = document.createTextNode(profile.getName())
  accessToken = googleUser.uc.access_token;
  // console.log(googleUser);
    // console.log('Logged in as: ' + profile.getName());
    // console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
    // console.log('Name: ' + profile.getName());
    // console.log('Image URL: ' + profile.getImageUrl());
    // console.log('Email: ' + profile.getEmail()); // This is null if the 'email' scope is not present.
    // console.log("TOKEN ", accessToken);
    signOutButton.style.setProperty('display', 'block');
    currentUser.style.setProperty('display', 'block');
    currentUserImage.src = profileImage;
    currentUserName.appendChild(profileName);
    if(accessToken){
      sendAccessTokenToServer(accessToken);
    }
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
  }else {
       console.log("there was an error")
     }
  }

function onFailure(error) {
  console.log(error);
}

function renderButton() {
  gapi.signin2.render('my-signin2', {
    'scope': 'profile email',
    'width': 240,
    'height': 50,
    'longtitle': true,
    'theme': 'dark',
    'onsuccess': onSuccess,
    'onfailure': onFailure
  });
}

async function sendAccessTokenToServer(accessToken){
  fetch(`http://localhost:5500/rest/v1/calendar/events/${accessToken}`)
    .then((res) => console.log(res))
    .catch(err => console.log("Error:", err))
}
