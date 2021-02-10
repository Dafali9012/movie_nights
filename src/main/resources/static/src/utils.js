export function loginCheck(){
    let loggedin = gapi.auth2.getAuthInstance().isSignedIn.get();
    console.log(loggedin);
    return loggedin;
}
