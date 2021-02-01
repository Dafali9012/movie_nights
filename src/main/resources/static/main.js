const CLIENT_ID =
  "834224170973-rafg4gcu10p2dbjk594ntg8696ucq06q.apps.googleusercontent.com";
let signInButton = document.querySelector("#signinButton");
let auth2;

start();

function start() {
  gapi.load("auth2", function () {
    auth2 = gapi.auth2.init({
      client_id: CLIENT_ID,
      scope: "https://www.googleapis.com/auth/calendar.events",
    });
  });
}

signInButton.addEventListener("click", () => {
  auth2.grantOfflineAccess().then(signInCallback);
});

async function signInCallback(authResult) {
  console.log("authResult", authResult);

  if (authResult["code"]) {
    // Hide the sign-in button now that the user is authorized
    signInButton.style.setProperty("display", "none");

    // Send the code to the server
    let result = await fetch("/api/storeauthcode", {
      method: "POST",
      headers: {
        "Content-Type": "application/octet-stream; charset=utf-8",
        "X-Requested-With": "XMLHttpRequest",
      },
      body: authResult["code"],
    });
    // etc...
  } else {
    // There was an error.
  }
}

console.log("Connected");
