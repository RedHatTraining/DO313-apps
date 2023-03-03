const baseUrl = 'http://localhost:8180/auth';

module.exports = {
  registration: {
    endpoint: baseUrl + '/realms/rhsso-training/clients-registrations',
    accessToken: '<Setup your access token>'
  },
  baseUrl: baseUrl,
  token: {
    username: 'alice',
    password: 'password',
    grant_type: 'password',
    client_id: 'test-cli',
    realmName: 'rhsso-training'
  },
  testClient: {
    clientId: 'test-cli',
    consentRequired: "false",
    publicClient: "true",
    standardFlowEnabled: "false",
    directAccessGrantsEnabled: "true",
    fullScopeAllowed: "true"
  }
};
