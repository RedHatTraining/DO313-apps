

const express = require('express');
const session = require('express-session');
const bodyParser = require('body-parser');

const Keycloak = require('keycloak-connect');
const cors = require('cors');

const audit = require('express-requests-logger');
const app = express();
app.use(bodyParser.json());

// Enable CORS support
app.use(cors());

//request logger:
app.use(audit());
/*app.use(audit({
  
  request: {
      excludeBody: '*', // Exclude all body
  },
  response: {
      excludeBody: '*' // Exclude all body from responses
  }
}));*/

// Create a session-store to be used by both the express-session
// middleware and the keycloak middleware.

const memoryStore = new session.MemoryStore();

app.use(session({
  secret: 'f60OrkxQNIlIv8P9BbD69pH62dq1ySeE',
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

app.get('/campaign/list', keycloak.protect('realm:marketing-user'), function (req, res) {
  console.log("Listing campaigns");
  
  if (res.status == 403) {
    console.log("You need to be authenticated");
    res.json({ message: 'You need to be authenticated' });
  } else {
    //res.json({message: 'You can list the campaigns'});
    res.json(
      [
        { 'name': 'New Product announce', 'description':'We are releasing a new product' },
        { 'name': 'Summer Time Season', 'description':'Summer is coming' },
        { 'name': 'Singles day Promotions', 'description':'We have big discounts for singles!!!' },
        { 'name': 'Spring Collection', 'description':'Spring is coming' },
        { 'name': 'Black Friday Discounts', 'description':'Almost everything for free' }]
      );
  }

});

/*app.get('/campaign/add', keycloak.protect('realm:marketing-user'), function (req, res) {
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
      //res.json({message: 'You need the marketing-admin role'});
      res.send(403,'You need the marketing-admin role');
    } else{
      res.json({message: 'You can select one to delete'});
    }
  
  
});*/

app.use('*', function (req, res) {
  res.send('Not found!');
});

app.listen(3000, function () {
  console.log('Started at port 3000');
});
