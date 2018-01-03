const http = require('http');
const url = require('url');
const fs = require('fs');
const Hue = require('philips-hue');
const nconf = require('nconf');
nconf.file({ file: './config.json' });

const hue = new Hue();

hue.bridge = nconf.get("bridge");
hue.username = nconf.get("username");

http.createServer(function(request, response) {
    let cmd = JSON.parse(request.headers.cmd);
    const data = cmd['data'];
    if (cmd['cmd'] === 'color') {
        const cHue = parseInt(data.split(",")[0]);
        const id = parseInt(data.split(",")[1]);
        const state = {bri: 200, sat: 120, hue: cHue};
        hue.light(id).setState(state).then(() => console.log("Set hue of light " + id + " to " + cHue)).catch(console.error);
    }
    response.writeHead(200);
    response.end();
}).listen(8080);

console.log("Server started, listening for connections on port 8080");

function setVar(key, value) {
    nconf.set(key, value);
    nconf.save();
}

function getVar(key) {
    return nconf.get(key);
}
