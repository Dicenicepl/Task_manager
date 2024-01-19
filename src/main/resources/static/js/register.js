

function communication_api() {
    const username = document.getElementById('username').value
    const email =  document.getElementById('email').value
    const password =  document.getElementById('password').value
    const userBody = {
        username: username,
        email:email,
        password:password
    };
    const apiSettings = {
        method: 'POST',
        mode: 'cors',
        headers:{
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(userBody)
    }
    fetch("http://localhost:8080/api/s/register", apiSettings)
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