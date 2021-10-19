const express = require('express');
const router = express.Router();
const axios = require('axios');

router.get('/', function (req, res) {
    let url = "https://jsonplaceholder.typicode.com/posts";
    axios.get(url).then(posts => {
        res.json(posts.data);
    }).catch(err => {
        res.send(err);
    })
});

router.get('/:id', function(req, res) {
    let id = req.params.id;
    let url = "https://jsonplaceholder.typicode.com/posts/" + id;
    axios.get(url).then(posts => {
        res.json(posts.data);
    }).catch(err => {
        res.send(err);
    })
});

module.exports = router;