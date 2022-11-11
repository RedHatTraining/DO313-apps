
const express = require('express');
const session = require('express-session');
const bodyParser = require('body-parser');
const Keycloak = require('keycloak-connect');
const cors = require('cors');
const jwt_decode= require('jwt-decode')
const app = express();
app.use(bodyParser.json());

// Enable CORS support
app.use(cors());

// Create a session-store to be used by both the express-session
// middleware and the keycloak middleware.

const memoryStore = new session.MemoryStore();

app.use(session({
  secret: 'some secret',
  resave: false,
  saveUninitialized: true,
  store: memoryStore
}));

// Provide the session store to the Keycloak so that sessions
// can be invalidated from the Keycloak console callback.
//
// Additional configuration is read from keycloak.json file
// installed from the Keycloak web console.

const keycloak = new Keycloak({
  store: memoryStore
});

app.use(keycloak.middleware({
  logout: '/logout',
  admin: '/admin'
}));

app.get('/campaign/list', function (req, res) {
  logTokens(req);
  req.
  res.json({message: 'Listing all public campaigns'});
});

app.get('/campaign/add', keycloak.protect('realm:marketing-user'), function (req, res) {
  logTokens(req);
  
    if (res.status == 403) {
      res.json({message: 'You need the marketing-user role'});
    } else {
      res.json({message: 'You can add a campaign'});
    }
  
  
});

app.get('/campaign/delete', keycloak.protect('realm:marketing-admin'), function (req, res) {
  logTokens(req);
  
    if (res.status == 403) {
      res.json({message: 'You need the marketing-admin role'});
    } else{
      res.json({message: 'You can select one to delete'});
    }
  
  
});

app.use('*', function (req, res) {
  res.send('Not found!');
});

app.listen(3000, function () {
  console.log('Started at port 3000');
});

function logTokens(req){
  //console.log("ID Token: "+JSON.stringify(jwt_decode(keycloak.idToken), null, '  '));
  //console.log("Access Token: "+JSON.stringify(jwt_decode(keycloak.token), null, '  '));
  console.log("Peticion "+req.message);
  
}