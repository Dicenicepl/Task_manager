// document.addEventListener("DOMContentLoaded", function () {
//     const loginButton = document.getElementById("button");
//     loginButton.addEventListener("click", function () {
//         const email = document.getElementById("email").value;
//         // getUserByEmail(email)
//         getUserByEmailFetch(email)
//     });
// });
//
// function getUserByEmail(email) {
//     console.log('email:'+email);
//     const request = new XMLHttpRequest();
//     request.open('GET', 'http://localhost:3000/api/user/get/?email='+email);
//     request.setRequestHeader("email", email)
//     request.send();
//     request.onload = function () {
//         if (request.status === 200){
//             console.log(request.response);
//         }else console.log("No response");
//     }
// }
// function getUserByEmailFetch(email) {
//     try {
//         fetch('http://localhost:3000/api/user/get/?email=' + email, {
//             method: 'GET'
//         }).then(response => {
//             if (response.status === 200) {
//                 response.json().then(data => {
//                     alert(JSON.stringify(data)); // Display the JSON data
//                 }).catch(error => {
//                     console.log('Error parsing JSON:', error);
//                 });
//             } else {
//                 console.log('No response');
//             }
//         }).catch(error => {
//             console.log('Fetch error:', error);
//         });
//     } catch (e) {
//         console.log("Error", e);
//     }
// }
