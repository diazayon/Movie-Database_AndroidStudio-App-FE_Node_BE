const express = require('express');
const router = express.Router();
const axios = require('axios');

//TV recommended endpoint
router.get('/rec/:id', function(req, res) {
    let id = req.params.id;
    let url = "https://api.themoviedb.org/3/tv/" + id + "/recommendations?api_key=1afa3745b922ddd45ad407a3f6ae648d&language=en-US&page=1";
    
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

//TV similar endpoint
router.get('/sim/:id', function(req, res) {
    let id = req.params.id;
    let url = "https://api.themoviedb.org/3/tv/" + id + "/similar?api_key=1afa3745b922ddd45ad407a3f6ae648d&language=en-US&page=1";
    
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
            
            filtered.push(temparray);
        }
        
        res.json(filtered);
    }).catch(err => {
        res.send(err);
    })

});

//TV video endpoint
router.get('/vid/:id', function(req, res) {
    let id = req.params.id;
    let url = "https://api.themoviedb.org/3/tv/" + id + "/videos?api_key=1afa3745b922ddd45ad407a3f6ae648d&language=en-US&page=1";
    
    axios.get(url).then(posts => {
        results = posts.data['results'];

        filtered = [];
        trailer = [];
        teaser = [];
        for (i = 0; i < results.length; i++) {
            var site = results[i].site;
            var name = results[i].name;
            var type = results[i].type;
            var key = results[i].key;

            var temparray = {};
            if(type == 'Trailer'){
                //temparray.site = site;
                //temparray.name = name;
                //temparray.type = type;
                temparray.key = key;
                trailer.push(temparray);
            }
            if(type == 'Teaser'){
                //temparray.site = site;
                //temparray.name = name;
                //temparray.type = type;
                temparray.key = key;
                teaser.push(temparray);
            }
            if(type == null || type == ''){
                //temparray.site = site;
                //temparray.name = name;
                //temparray.type = type;
                temparray.key = "tzkWB85ULJY";
                teaser.push(temparray);
            }
        }
        if(results.length == 0){
            var temparray = {};
            temparray.key = "tzkWB85ULJY";
            teaser.push(temparray);
        }
        if(trailer.length <1){
            filtered = teaser;
        }else {
            filtered = trailer;
        }



        res.json(filtered[0]);
    }).catch(err => {
        res.send(err);
    })
    
});

//TV detail endpoint
router.get('/det/:id', function(req, res) {
    let id = req.params.id;
    let url = "https://api.themoviedb.org/3/tv/" + id + "?api_key=1afa3745b922ddd45ad407a3f6ae648d&language=en-US&page=1";
    
    axios.get(url).then(posts => {
        results = posts.data;

        filtered = {};
        var genre = "";

        var title = results.name;
        var genres = results.genres;
        var spoken_languages = results.spoken_languages;
        var first_air_date = results.first_air_date.slice(0,4);
        var episode_run_time = results.episode_run_time;
        var overview = results.overview;
        var vote_average = results.vote_average;
        var tagline = results.tagline;

        for (i = 0; i < genres.length; i++) {
            var name = genres[i].name;
            if (i < genres.length - 1){
            genre += name + ', ';} else{
                genre += name
            }
        }


        filtered.title = title;
        filtered.genres = genre;
        filtered.spoken_languages = spoken_languages;
        filtered.release_date = first_air_date;
        filtered.episode_run_time = episode_run_time;
        filtered.overview = overview;
        filtered.vote_average = vote_average;
        filtered.tagline = tagline;

        res.json(filtered);
    }).catch(err => {
        res.send(err);
    })
});

//TV reviews endpoint
router.get('/rev/:id', function(req, res) {
    let id = req.params.id;
    let url = "https://api.themoviedb.org/3/tv/" + id + "/reviews?api_key=1afa3745b922ddd45ad407a3f6ae648d&language=en-US&page=1";
    
    axios.get(url).then(posts => {
        results = posts.data['results'];

        filtered = [];
        for (i = 0; i < results.length; i++) {
            var author = results[i].author;
            var content = results[i].content;
            var created_at = results[i].created_at;
            var url = results[i].url;
            var rating = results[i]['author_details'].rating/2;
            var avatar_path = results[i]['author_details'].avatar_path;

            const date = new Date(created_at);

            const dateOptions = {
            weekday: 'long',
            year: 'numeric',
            month: 'long',
            day: 'numeric',
            };

            created_at = date.toLocaleDateString('en-US', dateOptions);

            author = "by " + author + " on " + created_at;
            rating = rating + "/5";

            var temparray = {};
            temparray.author = author;
            temparray.content = content;
            //temparray.created_at = created_at;
            //temparray.url = url;
            temparray.rating = rating;
            //temparray.avatar_path = avatar_path;
            
            filtered.push(temparray);
        }

        res.json(filtered);
    }).catch(err => {
        res.send(err);
    })
});

//TV cast endpoint
router.get('/cast/:id', function(req, res) {
    let id = req.params.id;
    let url = "https://api.themoviedb.org/3/tv/" + id + "/credits?api_key=1afa3745b922ddd45ad407a3f6ae648d&language=en-US&page=1";
    
    axios.get(url).then(posts => {
        results = posts.data['cast'];

        filtered = [];
        for (i = 0; i < results.length; i++) {
            var id = results[i].id;
            var name = results[i].name;
            var character = results[i].character;
            var profile_path = results[i].profile_path;
           
            var temparray = {};
            if(profile_path != null){
            temparray.id = id;
            temparray.name = name;
            temparray.character = character;
            temparray.profile_path = "https://image.tmdb.org/t/p/w500/" + profile_path;
            filtered.push(temparray);
            }
        }

        res.json(filtered);
    }).catch(err => {
        res.send(err);
    })
});


module.exports = router;