document.addEventListener("DOMContentLoaded", function () {
    const loginButton = document.getElementById("button");
    loginButton.addEventListener("click", function () {
        const email = document.getElementById("email").value;
        getUserByEmail(email)
    });
});

function getUserByEmail(email) {
    console.log('email:'+email);
    const request = new XMLHttpRequest();
    request.open('GET', 'http://localhost:3000/api/user/get/?email='+email);
    request.setRequestHeader("email", email)
    request.send();
    request.onload = function () {
        if (request.status === 302){
            console.log(request.response);
        }else console.log("No response");
    }
}