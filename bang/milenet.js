var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var express = require('express');

var connections = [];
var mileNumbers = [];
var mileurl = "";

app.use(express.static('.'));
// app.use(express.static('connect'));
// app.use(app.router);

// process /
app.get('/', function (req, res) {
  // console.log(req);
  res.send("<h2>MILE platform is running now</h2><h3>Please input the specified mile number after 'http://147.47.249.199:8001/'</h3>");
});

// process /admin
app.get('/admin', function (req, res) {
  var output = "Connections: ";
  for (var i in connections) {
    output += connections[i].id + " ";
  }
  output += "<br><br>mileNumbers: ";
  for (var i in mileNumbers) {
    output += mileNumbers[i] + " ";
  }
    res.send("<h3>" + output + "</h3> ");
});

// process /mobile
app.get('/mobile', function (req, res) {
  console.log('New connection');
});

// process websocket server
io.on('connection', function(socket){
  // console.log('here 111');
  // Step 1: generate mileurl
  var identifier = socket.handshake.query.id + socket.handshake.query.author + socket.handshake.query.version;
  if (mileNumbers[identifier]) {
    mileurl = "http://147.47.249.199:8001?" + mileNumbers[identifier];
  } else {
    while (true) {
      // generate random number (1000~9999)
      var number = parseInt(Math.random()*10000);
      if (number < 1000)
        continue;
      // check if the generated number exists in mileNumbers
      var pass = true;
      for (var i in mileNumbers) {
        if (number == mileNumbers[i]) {
          pass = false;
          break;
        }
      }
      // register the generated number only if it doesn't exist in mileNumbers
      if (pass) {
        mileNumbers[identifier] = number;
        mileurl = "http://147.47.249.199:8001?" + mileNumbers[identifier];
        // bind the mileurl to web server
        app.get("/"+number, function (req, res) {
          res.redirect(socket.handshake.query.appurl);
          console.log('redirect');
        });
        break;
      }
    }
  }
  // Step 2: manage connection information
  connections.push({socket: socket, id: socket.id.substring(0,5)});
  socket.emit('message', {type: "$mile_id", data: socket.id.substring(0,5)});
  console.log(socket.id.substring(0,5) + ' connected');
  // check whether it's primary or not
  if (connections.length == 1) { // URL maker
    socket.emit('message', {type: "$mile_primary", data: mileurl});
  } else if (connections.length > 1) { // URL follower
    socket.emit('message', {type: "$mile_secondary", data: mileurl});
    socket.broadcast.emit('message', {type: "$mile_join", data: socket.id.substring(0,5)}); // send the message except me
    updateConnection();
  }
  // receive the message and send it to other clients
  socket.on('message', function(msg){
    console.log('[type]: ' + msg.type + ', [data]: ' + msg.data + ' (from ' + socket.id.substring(0,5) + ')');
    io.emit('message', msg);
  });
  // remove the socket and send the update to other clients
  socket.on('disconnect', function(){
    console.log(socket.id.substring(0,5) + ' disconnected');
    socket.broadcast.emit('message', {type: "$mile_leave", data: socket.id.substring(0,5)});
    for (var i = 0; i < connections.length; i++) {
      if (connections[i].id == socket.id.substring(0,5)) {
        connections.splice(i, 1);
      }
    }
    if (connections.length == 0)
      mileNumbers = [];
    updateConnection();
  });
  // send the connection status informatino to clients
  function updateConnection() {
    var conns = [];
    for (var i = 0; i < connections.length; i++) {
      conns[i] = connections[i].id;
    }
    io.emit('message', {type: "$mile_update", data: {connections: conns}});
  }
});
// Start HTTP Server
http.listen(8001, function(){
  console.log("listening on *: 8001");
  console.log("Server running at http://147.47.249.199:8001");
});
