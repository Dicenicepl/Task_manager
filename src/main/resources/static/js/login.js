
function login_api_request() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const userData = {
        email: email,
        password: password
    };
    const apiSettings = {
        method: 'POST',
        mode: "cors",
        headers:{
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(userData)
    }
    fetch('http://localhost:8080/api/s/login', apiSettings)
        .then(response => {
            if (response.status === 200) {
                response.json().then(data => {
                    alert(JSON.stringify(data));
                }).catch(error => {
                    console.log('Error parsing JSON:', error);
                });
            } else {
                console.log('No response');
            }
        }).catch(error => {
            console.log('Fetch error:', error);
        });


}
