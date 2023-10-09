
function test() {
    let email = document.getElementById("email").value
    let password = document.getElementById("password").value
    if (email.length !== 0 && password.length !== 0){
        console.log("email: " + email)
        console.log("password: " + password)
    }
    document.getElementById("toChange").innerHTML = "Sup bro";

}