const express = require('express');
const router = express.Router();
const axios = require('axios');

//trending endpoint
router.get('/trending', function(req, res) {
    let url = "https://api.themoviedb.org/3/trending/tv/day?api_key=1afa3745b922ddd45ad407a3f6ae648d";
    axios.get(url).then(posts => {
        results = posts.data['results'];

        filtered = [];
        for (i = 0; i < results.length; i++) {
            var id = results[i].id;
            var name = results[i].name;
            var poster_path = results[i].poster_path;

            var temparray = {};
            temparray.id = id;
            temparray.name = name;
            temparray.poster_path = "https://image.tmdb.org/t/p/w500" + poster_path;
            temparray.contenttype = "TV";
            
            filtered.push(temparray);
        }
        res.json(filtered);
    }).catch(err => {
        res.send(err);
    })
});

//top-rated endpoint
router.get('/toprated', function(req, res) {
    let url = "https://api.themoviedb.org/3/tv/top_rated?api_key=1afa3745b922ddd45ad407a3f6ae648d&language=en-US&page=1";
    axios.get(url).then(posts => {
        results = posts.data['results'];

        filtered = [];
        for (i = 0; i < results.length; i++) {
            var id = results[i].id;
            var name = results[i].name;
            var poster_path = results[i].poster_path;

            var temparray = {};
            temparray.id = id;
            temparray.name = name;
            temparray.poster_path = "https://image.tmdb.org/t/p/w500" + poster_path;
            temparray.contenttype = "TV";
            
            filtered.push(temparray);
        }
        res.json(filtered);
    }).catch(err => {
        res.send(err);
    })
});

//popular endpoint
router.get('/popular', function(req, res) {
    let url = "https://api.themoviedb.org/3/tv/popular?api_key=1afa3745b922ddd45ad407a3f6ae648d&language=en-US&page=1";
    axios.get(url).then(posts => {
        results = posts.data['results'];
        filtered = [];
        for (i = 0; i < results.length; i++) {
            var id = results[i].id;
            var name = results[i].name;
            var poster_path = results[i].poster_path;

            var temparray = {};
            temparray.id = id;
            temparray.name = name;
            temparray.poster_path = "https://image.tmdb.org/t/p/w500" + poster_path;
            temparray.contenttype = "TV";
            
            filtered.push(temparray);
        }


        res.json(filtered);
    }).catch(err => {
        res.send(err);
    })
});


module.exports = router;