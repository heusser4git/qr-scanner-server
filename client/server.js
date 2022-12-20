const express = require('express');
const app = express();
const port = process.env.PORT || 5000;
const cors = require('cors');
const path = require('path');
const bodyParser = require('body-parser');

let data = [{title: "test", id: 1}, {title: "test", id: 2}]
app.use(cors());
app.use(bodyParser.json());

app.get('/giphy', (req, res) => {
  console.log(`Searching for a gif with the term: ${req.query.term}`);
  res.send({
      success: true,
      data: data
  });
});

app.post('/giphy/item', (req , res)=> {
    let payload = req.body
    data.push(payload);
    res.send({
        succes: true,
        data: data
    })
});


app.use(express.static('public'));
app.get('*', (req, res) => {
   res.sendFile(path.resolve(__dirname, 'public', 'index.html'));
});
app.listen(port, () => {
   console.log(`Server is up at port ${port}`);
});