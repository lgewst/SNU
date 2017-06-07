var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var express = require('express');
var java = require('java');
var fs = require('fs');
var spawn = require('child_process').spawn;
//java.classpath.push('./java_algorithm/src/');
// TODO: *.java files are need to be compiled into *.class
//var bangUI = java.import('bang/userinterface/JavaUserInterface');
//var bangGame = java.import('bang/Game');

var connections = [];
var mileNumbers = [];
var mileurl = "";
var chatRoomId = null;
var gameStart = false;
var players = [];
var forSync = false;
var playersInfoText;

var child;

app.use(express.static('.'));

// process /
  // console.log(req.query);
  app.get('/', function (req, res) {
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

// process /admin
app.get('/observer', function (req, res) {
  var output = "Here's observer";
  output += "<br><br>Connections: ";
  for (var i in connections) {
    output += connections[i].id + " ";
  }
  output += "<br>mileNumbers: ";
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
                res.redirect("http://localhost:8001/observer");
            }
            else if(connections.length < 8) {
                console.log("Connections length is " + String(connections.length));
                console.log('Redirect: User in');
                // res.redirect(socket.handshake.query.appurl);
                // res.sendFile(__dirname + "/cards/playing card(back).jpg");
                res.sendFile(__dirname + "/mobile/mobile_index.html");
            } else {
                //TODO: Make Observer html additional!
                res.redirect("http://localhost:8001/observer");
        }
        });
        break;
    }
    }
  }

  // Step 2: manage connection information
  // TODO: connection should have ready attribute
  // connections.push({socket: socket, id: socket.id});
  connections.push({socket: socket, id: socket.id, ready: false});
  socket.emit('message', {type: "$mile_id", data: socket.id});
  console.log(socket.id + ' connected');
  // check whether it's primary or not

  if (connections.length == 1) { // URL maker
    socket.emit('message', {type: "$mile_primary", data: mileurl});
  } else if (connections.length > 1 && connections.length <= 8) { // URL follower and Game user
    socket.emit('message', {type: "$mile_secondary", data: mileurl});
    socket.broadcast.emit('message', {type: "$mile_join", data: socket.id}); // send the message except me
    updateConnection();
  }

  else if (connections.length > 8) {
    //TODO: To make Game observer html
    updateConnection();
  }
  // receive the message and send it to other clients
  socket.on('message', function(msg){
    console.log('[type]: ' + msg.type + ', [data]: ' + msg.data + ' (from ' + socket.id + ')');

    // TODO: if msg.type == 'ready', modify connection info
    if(msg.type == 'ready') {
        for (var i = 0; i < connections.length; i++) {
          if (connections[i].id == socket.id) {
            connections[i].ready = !connections[i].ready;
            console.log(String(connections[i].id) + " is now ready: " + connections[i].ready);
            socket.emit('message', msg);
            break;
          }
        }

        //TODO: Setting gameStart Originally connections.length >= 5
        if(connections.length >= 3 && connections.length <= 8) {
            var tmpGameStart = true;
            for (var i = 1; i < connections.length; i++) {
                if(connections[i].ready == false) {
                    tmpGameStart = false;
                    break;
                }
            }
            gameStart = tmpGameStart;

            if(gameStart) {
                setTimeout(() => {
                    forSync = true;
                }, 5050);
                io.emit('message',{type: "game_start", data: "Game Start"});
                console.log("Game Start in 5 seconds");
            }
            if(gameStart) {
                io.emit('message',{type: "bangCard", data: "../cards/playing card(back).jpg"});
                console.log("Sending: Image to Client");

                // Initialize File txt
                // fs.writeFile('java2js.txt', '', function(err) {
                //   if(err) {
                //     return console.log("Error while writing on file: java2js.txt");
                //   }
                //   console.log("FILE INITIALIZED");
                // });
                fs.writeFile('js2java.txt', '', function(err) {
                  if(err) {
                    return console.log("Error while writing on file: js2java.txt");
                  }
                  console.log("FILE INITIALIZED");
                });
                fs.writeFile('players.txt', '', function(err) {
                  if(err) {
                    return console.log("Error while writing on file: js2java.txt");
                  }
                  console.log("FILE INITIALIZED");
                });
                //TODO: Send msg to clients to make the buttons disable
                child = spawn('java', ['Test', connections.length - 1]);
            }
        }
    } else if(msg.type == 'playerInfo') {
        for(var i=1; i < connections.length; i++) {
            if(connections[i].id == socket.id) {
                // TODO: each client
                // socket.emit('message',{type: "playerInfo", data: JSON.parse(players[i-1])});
                console.log("Server to Client: playerInfo");
                break;
            }
        }
    } else if(msg.type == 'initPlayerInfo') {
        for(var i=1; i < connections.length; i++) {
            if(connections[i].id == socket.id) {
                io.to(connections[i].id).emit('message',{type:'playerInfo', data: playersInfoText[i-1]});
                console.log("Server to Client: playerInfo");
                break;
            }
        }
    } else if(msg.type == 'otherPlayerInfo') {
      for(var i = 0; i < playersInfoText.length - 1; i++) {
        if (JSON.parse(playersInfoText[i]).character.name == msg.data) {
          console.log('find' + i);
          fs.appendFile('js2java.txt', 'D\t' + i, function(err) {
            if(err) {
              return console.log("Error while writing on file: js2java.txt");
            }
            console.log("FILE WRITED");
          });
          //TODO
          setTimeout(function() {
              fs.watch('java2js.txt', function(event, filename) {
                console.log('watch java2js');
                  if(filename) {
                          var Texts = fs.readFileSync('java2js.txt','utf-8');
                          console.log(Texts);
                          socket.emit('message',{type:'otherPlayerInfo', data: Texts});
                          console.log("Server to Client: OtherPlayerInfo");
                  } else {
                      console.log('File Error: ' + filename);
                  }
              });
          },100);
          break;
        }
      }
    } else if(msg.type == 'help') {
        //TODO: Server side request
    }
  });
  // remove the socket and send the update to other clients
  socket.on('disconnect', function(){
    console.log(socket.id + ' disconnected');
    socket.broadcast.emit('message', {type: "$mile_leave", data: socket.id});
    for (var i = 0; i < connections.length; i++) {
      if (connections[i].id == socket.id) {
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

  //TODO:
  // fs.watch('java2js.txt', function(event, filename) {
  //   console.log('watch java2js');
  //     if(filename) {
  //         var Texts = fs.readFileSync('java2js.txt','utf-8');
  //         console.log(Texts);
  //         io.emit('message',{type:'otherPlayerInfo', data: Texts});
  //         console.log("Server to Client: OtherPlayerInfo");
  //     } else {
  //         console.log('File Error: ' + filename);
  //     }
  // });
  // //TODO:
  // fs.watch('js2java.txt', function(event, filename) {
  //     if(filename) {
  //
  //     } else {
  //         console.log('File Error: ' + filename);
  //     }

  // });
  var playerChecker = true;
  var cnt = 0;
  setInterval(function() {
      if(gameStart && playerChecker) {
          var playersText = fs.readFileSync('players.txt','utf-8');
          playersInfoText = playersText.split('\n');
        //   console.log("Con len: " + connections.length + " Info len: " + playersInfoText.length);
          if(playersInfoText.length == connections.length) {
              playerChecker = false;
              for(var i = 0; i < playersInfoText.length-1; i++) {
                  players.push(playersInfoText[i]);
              }
          }
      }
  },1000);

});

// Start HTTP Server
http.listen(8001, function(){
  console.log("listening on *: 8001");
  // console.log("Server running at http://147.47.249.199:8001");
  console.log("Server running at localhost:8001");
});
