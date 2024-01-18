document.addEventListener("DOMContentLoaded", function () {
    const loginButton = document.getElementById("login-submit");
    loginButton.addEventListener("click", function () {
        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;
        console.log(email + " " + password)
        login_api_request(email, password);
    });
});

function login_api_request(email, password) {
    const request = new XMLHttpRequest();
    request.open('POST', 'http://localhost:3000/api/s/login');
    request.setRequestHeader('Content-Type', 'application/json');
    const data = JSON.stringify({email: email, password: password});
    request.send(data);
    request.onload = function () {
        if (request.status === 200) {
            alert('Zalogowano pomyślnie');
        } else {
            alert('Wystąpił błąd podczas logowania');
        }
    }
    // const userData = {
    //     email: email,
    //     password: password
    // };
    // const apiSettings = {
    //     method: 'POST',
    //     mode: "cors",
    //     headers:{
    //         'Content-Type': 'application/json'
    //     },
    //     body: JSON.stringify(userData)
    // }
    // fetch('http://localhost:3000/api/s/login', apiSettings)
    //     .then(response => {
    //         if (response.status === 200) {
    //             response.json().then(data => {
    //                 alert(JSON.stringify(data));
    //             }).catch(error => {
    //                 console.log('Error parsing JSON:', error);
    //             });
    //         } else {
    //             console.log('No response');
    //         }
    //     }).catch(error => {
    //         console.log('Fetch error:', error);
    //     });


}
