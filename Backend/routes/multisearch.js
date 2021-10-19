const express = require('express');
const router = express.Router();
const axios = require('axios');

//multi search query
router.get('/:query', function(req, res) {
    let query = req.params.query;
    let url = "https://api.themoviedb.org/3/search/multi?api_key=1afa3745b922ddd45ad407a3f6ae648d&language=en-US&page=1&include_adult=false&query=" + query;
    axios.get(url).then(posts => {
        results = posts.data['results'];
        var filtered = [];
        var finaldata = [];

        for (i = 0; i < 20; i++) {
            if (results[i].media_type == "tv"){
                var id = results[i].id;
                var name = results[i].name;
                var backdrop_path = results[i].backdrop_path;
                var media_type = results[i].media_type;
                var release_date = results[i].first_air_date.slice(0,4);
                var rating = results[i].vote_average/2;

                release_date = "(" + release_date + ")";

                if (backdrop_path != null) {
                var temparray = {};
                temparray.id = id;
                temparray.name = name.toUpperCase();
                temparray.backdrop_path = "https://image.tmdb.org/t/p/original" + backdrop_path;
                temparray.media_type = media_type.toUpperCase();
                temparray.date = release_date;
                temparray.rating = rating;

                filtered.push(temparray);}
            }

            if (results[i].media_type == "movie"){
                var id = results[i].id;
                var title = results[i].title;
                var backdrop_path = results[i].backdrop_path;
                var media_type = results[i].media_type;
                var release_date = results[i].release_date.slice(0,4);
                var rating = results[i].vote_average/2;

                release_date = "(" + release_date + ")";

                if (backdrop_path != null) {
                var temparray = {};
                temparray.id = id;
                temparray.name = title.toUpperCase();
                temparray.backdrop_path = "https://image.tmdb.org/t/p/original" + backdrop_path;
                temparray.media_type = media_type.toUpperCase();
                temparray.date = release_date;
                temparray.rating = rating;

                filtered.push(temparray);}
            }
        };
        for (i = 0; i < 20; i++) {
            finaldata.push(filtered[i]);

        };


        res.json(finaldata);
    }).catch(err => {
        res.send(err);
    })
});

module.exports = router;