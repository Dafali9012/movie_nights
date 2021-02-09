let accessToken;
let signOutButton = document.getElementById("signOutButton");
let eventButton = document.getElementById("event");
let currentUserImage = document.getElementById("currentUserImage");
let currentUserName = document.getElementById("currentUserName");
let currentUser = document.getElementById("currentUser");
let auth2;
let googleUser;
renderButton()

function loginCheck(){
    if (gapi.auth2.getAuthInstance().isSignedIn.get()){
        console.log('Signed in: ' + gapi.auth2.getAuthInstance().isSignedIn.get())
    } else {
        console.log('Signed in: ' + gapi.auth2.getAuthInstance().isSignedIn.get())
    }
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
