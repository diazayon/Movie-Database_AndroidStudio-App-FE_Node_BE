const express = require('express');
const router = express.Router();
const axios = require('axios');

//trending endpoint
router.get('/trending', function(req, res) {
    let url = "https://api.themoviedb.org/3/trending/movie/day?api_key=1afa3745b922ddd45ad407a3f6ae648d";
    axios.get(url).then(posts => {
        results = posts.data['results'];

        filtered = [];
        for (i = 0; i < results.length; i++) {
            var id = results[i].id;
            var title = results[i].title;
            var poster_path = results[i].poster_path;

            var temparray = {};
            temparray.id = id;
            temparray.title = title;
            temparray.poster_path = "https://image.tmdb.org/t/p/w500" + poster_path;
            temparray.contenttype = "MOVIE";
            
            filtered.push(temparray);
        }
        res.json(filtered);
    }).catch(err => {
        res.send(err);
    })
});

//top-rated endpoint
router.get('/toprated', function(req, res) {
    let url = "https://api.themoviedb.org/3/movie/top_rated?api_key=1afa3745b922ddd45ad407a3f6ae648d&language=en-US&page=1";
    axios.get(url).then(posts => {
        results = posts.data['results'];

        filtered = [];
        for (i = 0; i < results.length; i++) {
            var id = results[i].id;
            var title = results[i].title;
            var poster_path = results[i].poster_path;

            var temparray = {};
            temparray.id = id;
            temparray.title = title;
            temparray.poster_path = "https://image.tmdb.org/t/p/w500" + poster_path;
            temparray.contenttype = "MOVIE";
            
            filtered.push(temparray);
        }
        res.json(filtered);
    }).catch(err => {
        res.send(err);
    })
});

//currently playing endpoint
router.get('/current', function(req, res) {
    let url = "https://api.themoviedb.org/3/movie/now_playing?api_key=1afa3745b922ddd45ad407a3f6ae648d&language=en-US&page=1";
    axios.get(url).then(posts => {
        results = posts.data['results'];

        filtered = [];
        for (i = 0; i < 5; i++) {
            var id = results[i].id;
            var title = results[i].title;
            var backdrop_path = results[i].backdrop_path;

            var temparray = {};
            temparray.id = id;
            temparray.title = title;
            temparray.backdrop_path = "https://image.tmdb.org/t/p/original" + backdrop_path;
            temparray.contenttype = "MOVIE";
            
            filtered.push(temparray);
        }
        res.json(filtered);
    }).catch(err => {
        res.send(err);
    })
});

//popular endpoint
router.get('/popular', function(req, res) {
    let url = "https://api.themoviedb.org/3/movie/popular?api_key=1afa3745b922ddd45ad407a3f6ae648d&language=en-US&page=1";
    axios.get(url).then(posts => {
        results = posts.data['results'];
        filtered = [];
        for (i = 0; i < results.length; i++) {
            var id = results[i].id;
            var title = results[i].title;
            var poster_path = results[i].poster_path;

            var temparray = {};
            temparray.id = id;
            temparray.title = title;
            temparray.poster_path = "https://image.tmdb.org/t/p/w500" + poster_path;
            temparray.contenttype = "MOVIE";
            
            filtered.push(temparray);
        }
        res.json(filtered);
    }).catch(err => {
        res.send(err);
    })
});


module.exports = router;