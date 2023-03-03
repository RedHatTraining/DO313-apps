const express = require('express');
const app = express();
const port = 8081;

app.use('/marketing-html5', express.static('src/main/webapp/'));

app.listen(port, () => {
  console.log(`marketing-html5 listening on port ${port}`);
});
