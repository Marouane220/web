const express = require('express');
const mongoose = require('mongoose');
const bodyParser = require('body-parser');

const items = require('./Items')

const app = express();

//configuration

app.use(bodyParser.json());

const db = require('./keys').mongoURI;

mongoose.connect(db).then(() => console.log("Mongoose connected ... ")).catch(err => console.log(err));

//router
app.use('/api/items', items);


const port = process.env.PORT || 5000;

app.listen(port, () => console.log(`Server started at port ${port}`))