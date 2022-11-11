[![Build Status](https://travis-ci.org/bucharest-gold/keycloak-request-token.svg?branch=master)](https://travis-ci.org/bucharest-gold/keycloak-request-token)  [![Coverage Status](https://coveralls.io/repos/github/bucharest-gold/keycloak-request-token/badge.svg?branch=master)](https://coveralls.io/github/bucharest-gold/keycloak-request-token?branch=master)


## keycloak-request-token


A simple module to request an Access Token from a Keycloak Server


## API Documentation

http://bucharest-gold.github.io/keycloak-request-token/


### Example

    'use strict';

    const tokenRequester = require('keycloak-request-token');

    const baseUrl = 'http://127.0.0.1:8080/auth';
    const settings = {
        username: 'admin',
        password: 'admi',
        grant_type: 'password',
        client_id: 'admin-cli'
    };

    tokenRequester(baseUrl, settings)
      .then((token) => {
        console.log(token);
      }).catch((err) => {
        console.log('err', err);
      });


## Development & Testing

To run the tests, you'll need to have a keycloak server running. No worries!
This is all taken care of for you. Just run `./build/start-server.sh`.
If you don't already have a server downloaded, this script will download one
for you, start it, initialize the admin user, and then restart.

Then just run the tests.

    make test

To stop the server, run `./build/stop-server.sh`.
