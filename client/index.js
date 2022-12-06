let button = document.getElementById('button');
let vorname = document.getElementById('vorname');
let nachname = document.getElementById('nachname');
let id = document.getElementById('id');
button.addEventListener('click', function () {
    let data = {
        nachname: nachname.value,
        vorname: vorname.value
    }
    if(id.value>0) {
        data.id = id.value;
    }

    console.log(data)
   fetch('http://localhost:7777/personal/items', {
       method: 'POST',
       mode: 'no-cors',
       headers: {
           'Accept': 'application/json',
           'Content-Type': 'application/json'
       },
       body: JSON.stringify(data)
   }).then(response => response.json())
       .then(response => console.log(JSON.stringify(response)))
       .catch(error => console.error(error));
});