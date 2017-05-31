var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var express = require('express');
var java = require('java');
java.classpath.push('./java_algorithm/src/');
// TODO: *.java files are need to be compiled into *.class
// var bangUI = java.import('bang/userinterface/JavaUserInterface');
//var bangGame = java.import('bang/Game');

var connections = [];
var mileNumbers = [];
var mileurl = "";
var chatRoomId = null;
var gameStart = false;

var readyCount = 0;

app.use(express.static('.'));

// process /
app.get('/', function (req, res) {
  // console.log(req.query);
  // res.send("<h2>MILE platform is running now</h2><h3>Please input the specified mile number after 'http://147.47.249.199:8001/'</h3>");
  res.send("<h2>MILE platform is running now</h2><h3>Please input the specified mile number after 'http://localhost:8001/'</h3>");
  console.log("This is main page");
  // res.sendFile(/*__dirname + */ "/connect/index.html");
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
    console.log("You can't access here directly!");
    res.redirect("http://localhost:8001/admin");
});

//TODO: Make gameStart true by using ready button
//If Game starts, then initialize game.
// if(gameStart) {
//   bangGame.Game(connections.length);
// }
//
var spawn = require('child_process').spawn;
// //var child = spawn('java', ['java_algorithm/src/bang/Test']);
var child;  // = spawn('java', ['Test']);
var fs = require('fs');
fs.writeFile('in.txt', 'ASDF', function(err) {
  if(err) {
    return console.log("err");
  }
  console.log("FILE WRITE");
});

// process websocket server
io.on('connection', function(socket){

  // Step 1: generate mileurl
  var identifier = socket.handshake.query.id + socket.handshake.query.author + socket.handshake.query.version;
  if (mileNumbers[identifier]) {

    // mileurl = "http://147.47.249.199:8001/" + mileNumbers[identifier];
    mileurl = "http://localhost:8001/" + mileNumbers[identifier];
    // console.log("http://147.47.249.199:8001/" + mileNumbers[identifier]);

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
        // mileurl = "http://147.47.249.199:8001/" + mileNumbers[identifier];
        mileurl = "http://localhost:8001/" + mileNumbers[identifier];

        // bind the mileurl to web server
        app.get("/" + number, function(req, res){
            if(gameStart) {
                console.log("You can't join while game is running");
                res.redirect("http://localhost:8001/admin");
            }
            if(connections.length < 8) {
                console.log("Connections length is " + String(connections.length));
                console.log('Redirect: User in');
                // res.redirect(socket.handshake.query.appurl);
                // res.sendFile(__dirname + "/cards/playing card(back).jpg");
                res.sendFile(__dirname + "/mobile/mobile_index.html");
            } else {
                //TODO: Make Observer html additional!
                res.redirect("http://localhost:8001/admin");
        }
        });
        break;
    }

    }
  }

  // Step 2: manage connection information
  // TODO: connection should have ready attribute
  // connections.push({socket: socket, id: socket.id.substring(0,5)});
  connections.push({socket: socket, id: socket.id.substring(0,5), ready: false});
  socket.emit('message', {type: "$mile_id", data: socket.id.substring(0,5)});
  console.log(socket.id.substring(0,5) + ' connected');
  // check whether it's primary or not

  if (connections.length == 1) { // URL maker
    socket.emit('message', {type: "$mile_primary", data: mileurl});
  } else if (connections.length > 1 && connections.length <= 8) { // URL follower and Game user
    socket.emit('message', {type: "$mile_secondary", data: mileurl});
    socket.broadcast.emit('message', {type: "$mile_join", data: socket.id.substring(0,5)}); // send the message except me
    updateConnection();
  }

  else if (connections.length > 8) {
    //TODO: To make Game observer html
    updateConnection();
  }
  // receive the message and send it to other clients
  socket.on('message', function(msg){
    console.log('[type]: ' + msg.type + ', [data]: ' + msg.data + ' (from ' + socket.id.substring(0,5) + ')');

    // TODO: if msg.type == 'ready', modify connection info
    if(msg.type == 'ready') {
        for (var i = 0; i < connections.length; i++) {
          if (connections[i].id == socket.id.substring(0,5)) {
            connections[i].ready = !connections[i].ready;
            console.log(String(connections[i].id) + " is now ready: " + connections[i].ready);
            socket.emit('message', msg);
            break;
          }
        }

        //TODO: Setting gameStart Originally connections.length >= 5
        if(connections.length >= 2 && connections.length <= 8) {
            var tmpGameStart = true;
            for (var i = 1; i < connections.length; i++) {
                if(connections[i].ready == false) {
                    tmpGameStart = false;
                    break;
                }
            }
            gameStart = tmpGameStart;
            //TODO: Send msg to clients to make the buttons disable
            if(gameStart) {
                socket.broadcast.emit('message',{type: "game_start", data: "Game Start"});
                socket.emit('message',{type: "game_start", data: "Game Start"});
                console.log("Game Start!!!!!");
            }
            //TODO: Try Something
            if(gameStart) {
                socket.broadcast.emit('message',{type: "bangCard", data: "../cards/playing card(back).jpg"});
                socket.emit('message',{type: "bangCard", data: "<img src=\"../cards/playing card(back).jpg\""});
                console.log("Image ~~");

                child = spawn('java', ['Test']);
            }
        }
    }
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
    if (connections.length == 0) {
      mileNumbers = [];
    }
    updateConnection();
  });


  // send the connection status informatino to clients
  function updateConnection() {
    var conns = [];
    console.log("This is update, and connections length is " + connections.length);
    for (var i = 0; i < connections.length; i++) {
      conns[i] = connections[i].id;
    }
    io.emit('message', {type: "$mile_update", data: {connections: conns}});
  }

});

// Start HTTP Server
http.listen(8001, function(){
  console.log("listening on *: 8001");
  // console.log("Server running at http://147.47.249.199:8001");
  console.log("Server running at localhost:8001");
});
