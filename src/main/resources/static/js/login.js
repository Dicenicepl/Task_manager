document.addEventListener("DOMContentLoaded", function () {
    const loginButton = document.getElementById("login_button");
    loginButton.addEventListener("click", function () {
        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;
        login_api_request(email, password);
    });
});

function login_api_request(email, password) {
    const request = new XMLHttpRequest();

    request.open('POST', 'http://localhost:3000/api/auth/user/login/');
    request.setRequestHeader('Content-Type', 'application/json');

    const data = JSON.stringify({ email: email, password: password });
    request.send(data);


    request.onload = function () {
        if (request.status === 200) {
            alert('Zalogowano pomyślnie');
            document.cookie = request.responseText;
            console.log(document.cookie);
        } else {
            alert('Wystąpił błąd podczas logowania');
        }
    }
}
