const exec = require("child_process").exec;
const fs = require('fs');
const bodyParser = require("body-parser");
const mongoose = require("mongoose");
const router = require('express').Router();
const User = require('./User');
const bcrypt = require('bcryptjs');
const {ensureAuthenticated} = require('./auth');
const path = require("path");
const crypto = require("crypto");
const multer = require("multer");
const methodeOverride = require("method-override");
const passport = require('passport');



// register page
router.get('/register', (req, res)=>{
  res.render('register');
});

router.post('/register', (req, res) => {
  const { name, email, password, password2 } = req.body;
  let errors = [];

  if (!name || !email || !password || !password2) {
    errors.push({ msg: 'Please enter all fields' });
  }

  if (password != password2) {
    errors.push({ msg: 'Passwords do not match' });
  }

  if (password.length < 6) {
    errors.push({ msg: 'Password must be at least 6 characters' });
  }

  if (errors.length > 0) {
    res.render('register', {
      errors,
      name,
      email,
      password,
      password2
    });
  } else {
      User.findOne({ email: email }).then(user => {
      if (user) {
        errors.push({ msg: 'Email already exists' });
        res.render('register', {
          errors,
          name,
          email,
          password,
          password2
        });
      } else {
        const newUser = new User({
          name,
          email,
          password
        });

        bcrypt.genSalt(10, (err, salt) => {
          bcrypt.hash(newUser.password, salt, (err, hash) => {
            if (err) throw err;
            newUser.password = hash;
            newUser
              .save()
              .then(user => {
                req.flash(
                  'success_msg',
                  'You are now registered and can log in'
                );
                res.redirect('/login');
              })
              .catch(err => console.log(err));
          });
        });
      }
    });
  }
});

//Login Page
router.get('/login', (req, res)=>{
  res.render('login');
});


router.post('/login', (req, res, next) => {
  passport.authenticate('local', {
    successRedirect: '/',
    failureRedirect: '/login',
    failureFlash: true
  })(req, res, next);
});
// Logout
router.get('/logout', ensureAuthenticated, (req, res) => {
  req.logout();
  req.flash('success_msg', 'You are logged out');
  res.redirect('/login');
});


//Start page
router.get('/', (req, res)=>{
  res.render('start');
});


router.get('/start', (req, res)=>{
  res.render('start');
});


//Find Service
router.get('/find', ensureAuthenticated, (req, res)=>{
  exec("ls", { timeout: 10000,
	 			maxBuffer: 2000*1024 },
		function (error, stdout, stderr) {
			res.send(stdout);
		});
});


//Upload and show  Service

var storage = multer.diskStorage({
    destination: (req, file, cb) => {
        cb(null, 'uploads')
    },
    filename: (req, file, cb) => {
        cb(null, file.fieldname + '-' + Date.now())
    }
});

var upload = multer({ storage: storage });

var imgModel = require('./image');

// Retriving the image
router.get('/show', ensureAuthenticated, (req, res) => {
  imgModel.find({}, (err, items) => {
      if (err) {
          console.log(err);
      }
      else {
          res.render('show', { items: items });
      }
  });
});

// Uploading the image
router.get('/upload', ensureAuthenticated, (req, res) => {
      res.render('index')});

router.post('/upload', upload.single('image'), (req, res, next) => {

  var obj = {
      name: req.body.name,
      desc: req.body.desc,
      img: {
          data: fs.readFileSync(path.join(__dirname + '/uploads/' + req.file.filename)),
          contentType: 'image/png' || 'image/jpg'
      }
  }
  imgModel.create(obj, (err, item) => {
      if (err) {
          console.log(err);
      }
      else {
          // item.save();
          res.redirect('/show');
      }
  });
});

//logout Service
router.get('/logout', ensureAuthenticated, (req, res) => {
 req.logout();
 return res.redirect('/');
});


//route doesn't exist
router.use((req, res)=>{
  res.status(404).send("sorry that's route doesn't existe!!")
})



module.exports = router;
