const express = require('express');
const router = express.Router();
const axios = require('axios');

//Cast detail endpoint
router.get('/det/:id', function(req, res) {
    let id = req.params.id;
    let url = "https://api.themoviedb.org/3/person/" + id + "?api_key=1afa3745b922ddd45ad407a3f6ae648d&language=en-US&page=1";
    
    axios.get(url).then(posts => {
        results = posts.data;

        var filtered = {};
        var birthday = results.birthday;
        var gender = results.gender;
        var name = results.name;
        var homepage = results.homepage;
        var also_known_as = results.also_known_as;
        var known_for_department = results.known_for_department;
        var biography = results.biography;

        filtered.birthday = birthday;
        filtered.gender = gender;
        filtered.name = name;
        filtered.homepage = homepage;
        filtered.also_known_as = also_known_as;
        filtered.known_for_department = known_for_department;
        filtered.biography = biography;

        res.json(filtered);
    }).catch(err => {
        res.send(err);
    })
});

//Cast external Ids endpoint
router.get('/eid/:id', function(req, res) {
    let id = req.params.id;
    let url = "https://api.themoviedb.org/3/person/" + id + "/external_ids?api_key=1afa3745b922ddd45ad407a3f6ae648d&language=en-US&page=1";
    
    axios.get(url).then(posts => {
        results = posts.data;
        var filtered = {};
        var imdb_id = results.imdb_id;
        var facebook_id = results.facebook_id;
        var Instagram_id = results.Instagram_id;
        var Twitter_id = results.Twitter_id;

        filtered.imdb_id = "imdb.com/name/" + imdb_id;
        filtered.facebook_id = "facebook.com/" + facebook_id;
        filtered.Instagram_id = "instagram.com/" + Instagram_id;
        filtered.Twitter_id = "twitter.com/" + Twitter_id;


        res.json(filtered);
    }).catch(err => {
        res.send(err);
    })
});

module.exports = router;