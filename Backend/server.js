const express = require('express');
const cors = require('cors');
var posts = require('./routes/posts');
var query = require('./routes/multisearch');
var movies = require('./routes/movies');
var tv = require('./routes/tv');
var moviedetails = require('./routes/movie_details');
var tvdetails = require('./routes/tv_details');
var cast = require('./routes/cast');

var app = express();
app.use(cors());

app.get('/', function(req, res) {
    res.send('Hello World');
});

app.use('/posts', posts);
app.use('/query', query);
app.use('/movies', movies);
app.use('/tv', tv);
app.use('/moviedetails', moviedetails);
app.use('/tvdetails', tvdetails);
app.use('/cast', cast);


var server = app.listen(8080, function() {
    console.log("Backend Application listening at http://localhost:8080")
})