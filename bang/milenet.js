var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var express = require('express');
var java = require('java');
var fs = require('fs');
// var spawn = require('child_process').spawn;
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

var children = [];
var child;
// child.kill('SIGINT');

var helpText = {"Bang":'사정거리 이내의 한 명을 지목하여 공격할 수 있는 카드로, 표적이 된 사람은 이를 피하지 못할 시 생명력이 1 줄어든다. 특수한 경우가 아니라면 자신의 차례에 단 한 번 사용 가능하다.',
    'Barrel':'"Bang!"의 표적(단, "결투" 제외)이 되었을 때, 카드 펼치기를 하여 하트가 나오면 피할 수 있다. 펼친 카드는 버린다.',
    'Beer':'효과는 생명력 1추가. 단, 자신의 한계 생명력을 넘을 수는 없다. 일반적으로는 자신의 차례에만 사용할 수 있지만, 자신의 생명력이 0이하가 되었을 때에는 자신의 차례가 아니라도, 다시 1로 복구할 수 있는 만큼의 맥주를 가지고 있다면, 그를 사용하여 되살아날 수 있다.',
    'Catbalou':'카드 더미를 제외한 모든 영역에서, 게임카드 하나를 임의로 버리게 한다. 단, 그렇게 버려진 카드는 Cat balou 위에 버려진다.',
    'Duel':'표적이 된 사람부터 결투를 신청한 사람과 번갈아 "뱅!"을 한 장씩 내고, 먼저 "뱅!"을 낼 수 없게 된(혹은 내지 않은) 사람이 생명력 1을 잃는다.',
    'Dynamite':'먼저 자신의 필드 위에 장착한다. 자신의 차례는 이미 시작한 뒤므로, 다이너마이트가 발동하려면 차례가 한 바퀴 돌아 다시 자신의 차례가 와야 한다. 그 다음 차례가 오기 전에 죽으면, 다이너마이트는 자신의 필드 위에 있으므로, 버려진다.죽지 않고 다음 차례가 오면, 카드 가져오기 이전에 카드 펼치기를 한다. 펼친 카드는 버리고, 그 문양이 스페이드 2~9인 경우, 다이너마이트가 터쳐 생명력 3을 잃고[11], 아닌 경우엔 기본 순서(시계 방향)에 따라 다이너마이트를 왼쪽으로 넘긴다.',
    'Gatling':'사용한 사람을 제외한 모두가 "Missed" 카드를 내야하며, 내지 못할 시엔 생명력을 1 잃는다.',
    'Indians':'사용한 사람을 제외한 모든 사람들이 "Bang!"을 한 장 버려야 하며, 그렇지 못하면 생명력 1을 잃게 된다.',
    'Jail':'보안관을 제외한 인물에게만 사용할 수 있는 카드로, 감옥에 갇힌 사람은 자신의 차례에 오기 전에 없어지지(캣 벌로우, 강탈) 않는 이상, 카드 가져오기 단계에 앞서 카드 펼치기를 한다. 카드는 한 장만 펼치며, 하트인 경우 펼친 카드와 감옥을 버리고 카드 두 장을 가져오며, 하트가 아닌 경우엔 펼친 카드와 감옥을 버린 채로 차례가 종료된다.(감옥안에 걸린경우 듀얼,인디언등을 피할 수없다 모두다 적용된다 그리고 뱅같은걸 맞으면 빗나감 또는 술통을 사용할 수 있다)',
    'Missed':'이를 사용함으로 "뱅!과 "기관총"을 피할 수 있다.',
    'Mustang':'타인이 볼 때, 자신과의 거리가 멀어진다. 하지만 자신의 관점에서의 거리는 사용 전과 동일하다.',
    'Panic':'사정거리 1이내의 상대로부터 패를 한 장 가져올 수 있다. 이는 필드 위의 패에도 해당되며, 가져온 카드는 일단 수중에 넣는다.',
    'Saloon':'플레이 중인 인원 전부 생명력 1을 올린다. 단, 한계 이상으로의 회복은 불가하며, 맥주와는 달리, 생명력이 0이하로 떨어진 후엔 자신의 차례이든 아니든 사용할 수 없다.',
    'Scope':'타인을 볼 때, 거리가 1 가까워진다. 타인의 관점에서의 자신과의 거리는 사용 전과 변함 없다.',
    'Stagecoach':'카드 더미 맨 위의 두 장을 가져올 수 있다.',
    'Wellsfargo':'카드 더미 맨 위의 세 장을 가져올 수 있다.',
    'Remington':"사정거리가 3으로 변경된다.",
    'Rev carabine':'사정거리가 4로 변경된다.',
    'Schofield':'사정거리가 2로 변경된다.',
    'Volcanic':'사정거리는 기본 사정거리(1) 그대로이지만, "Bang!"을 원하는 만큼 사용할 수 있다.',
    'Winchester':'사정거리가 5로 변경된다.'
};

process.on('exit', function() {
    child.kill();
    console.log('kill');
});


app.use(express.static('.'));

// process /
  // console.log(req.query);
  app.get('/', function (req, res) {
  // res.send("<h2>MILE platform is running now</h2><h3>Please input the specified mile number after 'http://147.47.249.199:29494/'</h3>");
  // res.send("<h2>MILE platform is running now</h2><h3>Please input the specified mile number after 'http://maro.io:29494/'</h3>");
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
    // res.redirect("http://maro.io:29494/admin");
    res.redirect("http://localhost:8001/admin");
});

// process websocket server
io.on('connection', function(socket){

  // Step 1: generate mileurl
  var identifier = socket.handshake.query.id + socket.handshake.query.author + socket.handshake.query.version;
  if (mileNumbers[identifier]) {

    // mileurl = "http://147.47.249.199:29494/" + mileNumbers[identifier];
    // mileurl = "http://maro.io:29494/" + mileNumbers[identifier];
    mileurl = "http://localhost:8001/" + mileNumbers[identifier];
    // console.log("http://147.47.249.199:29494/" + mileNumbers[identifier]);

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
        // mileurl = "http://147.47.249.199:29494/" + mileNumbers[identifier];
        // mileurl = "http://maro.io:29494/" + mileNumbers[identifier];
        mileurl = "http://localhost:8001/" + mileNumbers[identifier];

        // bind the mileurl to web server
        app.get("/" + number, function(req, res){
            if(gameStart) {
                console.log("You can't join while game is running");
                // res.redirect("http://maro.io:29494/observer");
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
                // res.redirect("http://maro.io:29494/observer");
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
        if(connections.length >= 5 && connections.length <= 8) {
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

                console.log("Sending: Image to Client");

                fs.writeFile('text/js2java_0.txt', '', function(err) {
                    if(err) {
                        return console.log("Error while writing on file: js2java_0.txt");
                    }
                    console.log("js2java_0.txt FILE INITIALIZED");
                });
                fs.writeFile('text/js2java_1.txt', '', function(err) {
                    if(err) {
                        return console.log("Error while writing on file: js2java_1.txt");
                    }
                    console.log("js2java_1.txt FILE INITIALIZED");
                });
                fs.writeFile('text/js2java_2.txt', '', function(err) {
                    if(err) {
                        return console.log("Error while writing on file: js2java_2.txt");
                    }
                    console.log("js2java_2.txt FILE INITIALIZED");
                });
                fs.writeFile('text/js2java_3.txt', '', function(err) {
                    if(err) {
                        return console.log("Error while writing on file: js2java_3.txt");
                    }
                    console.log("js2java_3.txt FILE INITIALIZED");
                });
                fs.writeFile('text/js2java_4.txt', '', function(err) {
                    if(err) {
                        return console.log("Error while writing on file: js2java_4.txt");
                    }
                    console.log("js2java_4.txt FILE INITIALIZED");
                });
                fs.writeFile('text/js2java_5.txt', '', function(err) {
                    if(err) {
                        return console.log("Error while writing on file: js2java_5.txt");
                    }
                    console.log("js2java_5.txt FILE INITIALIZED");
                });
                fs.writeFile('text/js2java_6.txt', '', function(err) {
                    if(err) {
                        return console.log("Error while writing on file: js2java_6.txt");
                    }
                    console.log("js2java_6.txt FILE INITIALIZED");
                });
                fs.writeFile('text/js2java_7.txt', '', function(err) {
                    if(err) {
                        return console.log("Error while writing on file: js2java_7.txt");
                    }
                    console.log("js2java_7.txt FILE INITIALIZED");
                });
                fs.writeFile('text/java2js_0.txt', '', function(err) {
                    if(err) {
                        return console.log("Error while writing on file: js2java_7.txt");
                    }
                    console.log("java2js_0.txt FILE INITIALIZED");
                });
                fs.writeFile('text/java2js_1.txt', '', function(err) {
                    if(err) {
                        return console.log("Error while writing on file: js2java_7.txt");
                    }
                    console.log("java2js_1.txt FILE INITIALIZED");
                });
                fs.writeFile('text/java2js_2.txt', '', function(err) {
                    if(err) {
                        return console.log("Error while writing on file: js2java_7.txt");
                    }
                    console.log("java2js_2.txt FILE INITIALIZED");
                });
                fs.writeFile('text/java2js_3.txt', '', function(err) {
                    if(err) {
                        return console.log("Error while writing on file: js2java_7.txt");
                    }
                    console.log("java2js_3.txt FILE INITIALIZED");
                });
                fs.writeFile('text/java2js_4.txt', '', function(err) {
                    if(err) {
                        return console.log("Error while writing on file: js2java_7.txt");
                    }
                    console.log("java2js_4.txt FILE INITIALIZED");
                });
                fs.writeFile('text/java2js_5.txt', '', function(err) {
                    if(err) {
                        return console.log("Error while writing on file: js2java_7.txt");
                    }
                    console.log("java2js_5.txt FILE INITIALIZED");
                });
                fs.writeFile('text/java2js_6.txt', '', function(err) {
                    if(err) {
                        return console.log("Error while writing on file: js2java_7.txt");
                    }
                    console.log("java2js_6.txt FILE INITIALIZED");
                });
                fs.writeFile('text/java2js_7.txt', '', function(err) {
                    if(err) {
                        return console.log("Error while writing on file: js2java_7.txt");
                    }
                    console.log("java2js_7.txt FILE INITIALIZED");
                });

                fs.writeFile('text/debug.txt', '', function(err) {
                    if(err) {
                        return console.log("Error while writing on file: debug.txt");
                    }
                });

                fs.writeFile('text/players.txt', '', function(err) {
                  if(err) {
                    return console.log("Error while writing on file: js2java.txt");
                  }
                  console.log("players.txt FILE INITIALIZED");
                });

                fs.writeFile('text/java2js_-1.txt', '', function(err) {
                  if(err) {
                    return console.log("Error while writing on file: js2java.txt");
                  }
                  console.log("java2js_-1.txt FILE INITIALIZED");
                });
                //TODO: Send msg to clients to make the buttons disable
                // child = spawn('java', ['Test', connections.length - 1]);
                child = require('child_process').spawn('java', ['Test', connections.length - 1]);
                children.push(child);
                console.log('children length is ' + children.length);
            }
        }
    } else if(msg.type == 'playerInfo') {
            for(var i = 1; i < connections.length; i++) {
                if(connections[i].id == socket.id) {
                    console.log(i + ' playerInfo');
                    // TODO: Do not use D\t because it has to find itself
                    fs.appendFile('text/js2java_' + i + '.txt', 'D\t' + i + '\n', function(err) {
                        if(err) {
                            return console.log("Error while writing on file: js2java.txt");
                        }
                        console.log("js2java_" + i +".txt FILE WRITED");
                    });
                    // //TODO
                    // setTimeout(function() {
                    //         var Texts = fs.readFileSync('text/java2js_'+ i +'.txt','utf-8');
                    //         // console.log(Texts);
                    //         // socket.emit('message',{type:'playerInfo', data: Texts});
                    // },100);
                    break;
                }
            }
    } else if(msg.type == 'initPlayerInfo') {
        for(var i=1; i < connections.length; i++) {
            if(connections[i].id == socket.id) {
                var parseInit = JSON.parse(playersInfoText[i-1]);
                if (parseInit.job.name == 'Sheriff')
                  continue;
                io.to(connections[i].id).emit('message',{type:'playerInfo', data: playersInfoText[i-1]});
                console.log("Server to Client: initPlayerInfo");
                break;
            }
        }
    } else if(msg.type == 'otherPlayerInfo') {
        var reqInx = 0;
        var isSend = true;
        for(var i = 1; i < connections.length; i++) {
            if(connections[i].id == socket.id) {
                reqInx = i;
                break;
            }
        }
        for(var i = 0; i < playersInfoText.length; i++) {
            if (JSON.parse(playersInfoText[i]).character.name == msg.data) {
                var j = i+1;
                // console.log(msg.data);
                console.log(reqInx + ' finds ' + j);
                fs.appendFile('text/js2java_' + reqInx + '.txt', 'D\t' + j + '\n', function(err) {
                    if(err) {
                        return console.log("Error while writing on file: js2java.txt");
                    }
                    console.log("js2java_" + reqInx +".txt FILE WRITED");
                    isSend = false;
                });
                //TODO
                // setTimeout(function() {
                //     var Texts = fs.readFileSync('text/java2js_' + reqInx +'.txt','utf-8');
                //     // console.log(Texts);
                //     socket.emit('message',{type:'otherPlayerInfo', data: Texts});
                // },100);
                break;
            }
        }
        // var tmpF = true;
        // while(tmpF) {
        //     tmpF = tmpFunc(reqInx);
        // }
    } else if(msg.type == 'help') {
        //TODO: Server side request
        var getData = msg.data;
        var toParseSrc = getData.split("/");
        var toParseSrcIdx;
        if(toParseSrc.length > 0)
            toParseSrcIdx = toParseSrc.length - 1;
        else
            toParseSrcIdx = 0;
        var helpData = toParseSrc[toParseSrcIdx].split('_')[0];
        console.log('is help right? ' + helpData);
        console.log("help msg is " + helpText[helpData]);
        socket.emit('message', {type: "help",data:helpText[helpData]});
    } else if(msg.type == 'selectPlayingCard' || msg.type == 'discardPlayingCard' || msg.type == 'bangRespond'
     || msg.type == 'missRespond' || msg.type == 'beerRespond' || msg.type == 'selectTargetRespond' || msg.type == 'selectTargetCardRespond') {
        var reqInx = 0;
        for(var i = 1; i < connections.length; i++) {
            if(connections[i].id == socket.id) {
                reqInx = i;
                break;
            }
        }
        console.log(reqInx + ' send type: ' + msg.type +' index: ' + msg.data);
        fs.appendFile('text/js2java_' + reqInx + '.txt', msg.data + '\n', function(err) {
          if(err) {
            return console.log("Error while writing on file: js2java.txt");
          }
          console.log("js2java_" + reqInx +".txt FILE WRITED");
        });
    } else if(msg.type == 'requestCardInfo') {
        setTimeout(function() {
            socket.emit('message',{type:'respondCardInfo', data: msg.data});
        },500);
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

  var playerChecker = true;
  var cnt = 0;
  setInterval(function() {
      if(gameStart && playerChecker) {
          var playersText = fs.readFileSync('text/players.txt','utf-8');
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

  function tmpFunc(idx) {
    // console.log(idx + ' changed');
    var Texts = fs.readFileSync('text/java2js_'+ idx +'.txt','utf-8');
    try {
        //   console.log(Texts);
          var parse = JSON.parse(Texts);
          console.log('send ' + parse.type);
          // return false;
    } catch(e) {
          // return true;
          return;
    }
    setTimeout(function(){
      if(idx === -1)
            io.to(connections[0].id).emit('message',{type:parse.type, data: parse.data});
      else
            io.to(connections[idx].id).emit('message',{type:parse.type, data: parse.data});
    },300);
    fs.writeFile('text/java2js_'+idx+'.txt', '', function(err) {
        if(err) {
            return console.log("Error while writing on file: js2java_"+ idx +".txt");
        }
        // console.log("java2js_"+idx+".txt FILE INITIALIZED");
    });
  }

  setInterval(function() {
      if(gameStart) {
          var Texts = fs.readFileSync('text/java2js_1.txt','utf-8');
          if(Texts != '')
            tmpFunc(1);
    }
  },500);
  setInterval(function() {
      if(gameStart) {
          var Texts = fs.readFileSync('text/java2js_2.txt','utf-8');
          if(Texts != '')
            tmpFunc(2);
    }
  },500);
  setInterval(function() {
      if(gameStart) {
          var Texts = fs.readFileSync('text/java2js_3.txt','utf-8');
          if(Texts != '')
            tmpFunc(3);
    }
  },500);
  setInterval(function() {
      if(gameStart) {
          var Texts = fs.readFileSync('text/java2js_4.txt','utf-8');
          if(Texts != '')
            tmpFunc(4);
    }
  },500);
  setInterval(function() {
      if(gameStart && connections.length >= 6) {
          var Texts = fs.readFileSync('text/java2js_5.txt','utf-8');
          if(Texts != '')
            tmpFunc(5);
    }
  },500);
  setInterval(function() {
      if(gameStart && connections.length >= 7) {
          var Texts = fs.readFileSync('text/java2js_6.txt','utf-8');
          if(Texts != '')
            tmpFunc(6);
    }
  },500);
  setInterval(function() {
      if(gameStart && connections.length >= 8) {
          var Texts = fs.readFileSync('text/java2js_7.txt','utf-8');
          if(Texts != '')
            tmpFunc(7);
    }
  },500);
  setInterval(function() {
      if(gameStart) {
          var Texts = fs.readFileSync('text/java2js_0.txt','utf-8');
          if(Texts != '')
            tmpFunc(0);
    }
  },500);
  setInterval(function() {
      if(gameStart) {
          var Texts = fs.readFileSync('text/java2js_-1.txt','utf-8');
          if(Texts != '')
            tmpFunc(-1);
    }
  },500);


  // fs.watch('text/java2js_0.txt', function(event, filename) {
  //     setTimeout(function() {
  //         tmpFunc(0);
  //     },50);
  // });
});



// Start HTTP Server
// http.listen(29494, '0.0.0.0', function(){
http.listen(8001, function(){
    // console.log("Server running at http://147.47.249.199:29494");
  // console.log("listening on *: 29494");
  console.log("listening on *: 8001");
});
  // console.log("Server running at maro.io:29494");
  console.log("Server running at localhost:8001");
