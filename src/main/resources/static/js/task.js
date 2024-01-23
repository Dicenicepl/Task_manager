const p = document.getElementById("test");
//
// const body = {
//     name:"home"
// };
// const apiSettings = {
//     method: 'GET',
//     mode: "cors",
//     headers:{
//         'Content-Type': 'application/json'
//     },
//     body: JSON.stringify(body)
// }

fetch('http://localhost:8080/api/p/find/project?name=test')
    .then(response =>{
        if (response.ok){
            p.textContent = "Dane sa tutaj";
            return response.json();
        }
    })
