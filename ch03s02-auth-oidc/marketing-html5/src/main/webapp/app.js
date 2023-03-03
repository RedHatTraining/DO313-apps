
var keycloak = new Keycloak();
var serviceUrl = 'http://localhost:3000/campaign'

function notAuthenticated() {
    document.getElementById('not-authenticated').style.display = 'block';
    document.getElementById('authenticated').style.display = 'none';
}

function authenticated() {
    document.getElementById('not-authenticated').style.display = 'none';
    document.getElementById('authenticated').style.display = 'block';
    document.getElementById('message').innerHTML = 'User: ' + keycloak.tokenParsed['preferred_username'];
}

function showTokens(){
   
    document.getElementById("idtoken").innerHTML = "ID Token</br>"+JSON.stringify(jwt_decode(keycloak.idToken), null, '  ');
    document.getElementById("accesstoken").innerHTML = "Access Token (bearer)</br>"+JSON.stringify(jwt_decode(keycloak.token), null, '  ');
    document.getElementById("refreshtoken").innerHTML = "Refresh Token</br>"+JSON.stringify(jwt_decode(keycloak.refreshToken), null, '  ');
   //document.getElementById("output").innerHTML = jwt_decode(keycloak.showToken);
}
function request(endpoint) {
    var req = function() {
        var req = new XMLHttpRequest();
        var output = document.getElementById('message');
        req.open('GET', serviceUrl + '/' + endpoint, true);

        if (keycloak.authenticated) {
           req.setRequestHeader('Authorization', 'Bearer ' + keycloak.token);
           //req.setRequestHeader('Authorization', 'Bearer ' + keycloak.idToken); --> does not have roles, by default
        }

        req.onreadystatechange = function () {
            if (req.readyState == 4) {
                if (req.status == 200) {
                    output.innerHTML =  JSON.stringify(req.responseText);
                } else if (req.status == 0) {
                    output.innerHTML = '<span class="error">Request to Marketing API failed</span>';
                } else if (req.status == 403) {
                    output.innerHTML = 'You have not the required role: <span class="error">' + req.status + ' ' + req.statusText + '</span>';
                } else {
                    output.innerHTML = 'Error: <span class="error">' + req.status + ' ' + req.statusText + '</span>';
                }
            }
        };

        req.send();
    };

    if (keycloak.authenticated) {
        keycloak.updateToken(30).success(req);
    } else {
        req();
    }
}

window.onload = function () {
    keycloak.init({ onLoad: 'check-sso', checkLoginIframeInterval: 1 }).success(function () {
        if (keycloak.authenticated) {
            authenticated();
        } else {
            notAuthenticated();
        }

        document.body.style.display = 'block';
    });
}

keycloak.onAuthLogout = notAuthenticated;
