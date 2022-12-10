let button = document.getElementById('button');
let deletebutton = document.getElementById('delete');
let scandatabutton = document.getElementById('scandata');
let vorname = document.getElementById('vorname');
let nachname = document.getElementById('nachname');
let geburtsdatum = document.getElementById('geburtsdatum');
let status = document.getElementById('status');
let id = document.getElementById('id');

button.addEventListener('click', function () {
    let data = {
        nachname: nachname.value,
        vorname: vorname.value,
        geburtsdatum: geburtsdatum.value,
        status: status.value
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


deletebutton.addEventListener('click', function () {
    let headers = new Headers({
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    });
    console.log(id.value);
    if(id.value>0) {
        fetch('http://localhost:7777/personal/items/' + id.value, {
            method: 'DELETE',
            headers: headers,
            credentials: 'same-origin'
        }).then(()=>console.log("removed")).catch(error => console.error(error));
    } else {
        alert("zu loeschende ID eingeben!")
    }
});

scandatabutton.addEventListener('click', function () {
    let headers = new Headers({
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    });
    if(id.value>0) {
        fetch('http://localhost:7777/scanner/items/' + id.value, {
            method: 'GET',
            headers: headers,
            credentials: 'same-origin'
        })
            .then(response => response.json())
            .then(response => {
                let data = JSON.stringify(response);
                document.querySelector('data').innerHTML = data;
            })
            .catch(error => console.error(error));
    } else {
        alert("ID der abzuholenden Scandaten eingeben!")
    }
});