const url = 'localhost:3000/auth/login?email=dicenicepl@gmail.com&password=1234';
const request = new XMLHttpRequest();

request.onload = function () {
    if (request.status === 200) {
        const data = request.responseText;

        document.cookie = 'token=${data}';
        console.log(data)
    } else {
        console.log('nieudane przypisanie tokenu')
    }
};
request.open('GET', url);
request.send();