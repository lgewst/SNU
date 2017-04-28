// MILE object
var MILE = {
  socket: null,
  connections: [],
  cbfunc: [],
  appid: "",
  author: "",
  version: "",
  appurl: "",
  mileid: "",
  mileurl: "",
  debug: false,
  setAppID: function(id) {
    MILE.appid = id;
  },
  setAuthor: function(author) {
    MILE.author = author;
  },
  setVersion: function(ver) {
    MILE.version = ver;
  },
  setAppURL: function(url) {
    MILE.appurl = url;
  },
  init: function() {
    // Web Socket Init
    socket = new io.connect("ws://mile.cafe24app.com?id=" + MILE.appid + "&author=" + MILE.author + "&version=" + MILE.version + "&appurl=" + MILE.appurl);
    socket.on("connect", function() {
      MILE.log("$mile_init", "success");
    });
    // A message is arrived from the server
    socket.on("message", function(msg){
      console.log(msg);
      MILE.MileHandler(msg.type, msg.data, msg.from);
    });
    MILE.showMileStatus(true);
  },
  // A message is sent to server
  send: function(type, data) {
    socket.emit("message", {type: type, data: data, from: MILE.mileid});
  },
  on: function(type, callback) {
    MILE.cbfunc[type] = callback;
  },
  // A message is classified and handled according to the type
  MileHandler: function(type, data, from) {
    switch (type) {
      case "$mile_id":
        MILE.mileid = data;
        MILE.log(type, "ID: " + data);
        break;
      case "$mile_primary":
        MILE.mileurl = data;
        MILE.updateMileStatus();
        MILE.showMileURL(true);
        MILE.log(type, data);
        break;
      case "$mile_secondary":
        MILE.mileurl = data;
        MILE.updateMileStatus();
        MILE.showMileURL(false);
        MILE.log(type, data);
        break;
      case "$mile_join":
        MILE.connections.push(data);
        MILE.updateMileStatus();
        MILE.log(type, data + " connected");
        break;
      case "$mile_leave":
        MILE.connections.pop(data);
        MILE.updateMileStatus();
        MILE.log(type, data + " disconnected");
        break;
      case "$mile_update":
        var output = "";
        MILE.connections = data.connections;
        MILE.updateMileStatus();
        data = data.connections;
        if (data.length == 1) {
          MILE.showMileURL(true);
        } else if (data.length > 1) {
          MILE.showMileURL(false);
        }
        for (var i = 0; i < data.length; i++) {
          output += data[i] + " ";
        }
        output += "(" + data.length + ")";
        MILE.log(type, output);
        break;
      default:
        if (MILE.cbfunc[type] != undefined) {
          MILE.cbfunc[type].call(MILE, data, from);
          MILE.log(type, data);
        } else {
          MILE.log("**COULDN'T FIND THIS TYPE** " + type, data);
        }
    }
  },
  log: function(type, data) {
    if (MILE.debug && document.getElementById("$mile_debug")) {
      document.getElementById("$mile_debug").innerHTML += "<li><b>[" + type + "]</b> " + data + "</li>";
    }
    console.log("[" + type + "] " + data);
  },
  showMileURL: function(visible) {
    var bgPanel;
    if (visible) {
      bgPanel = document.getElementById("$mile_bg");
      if (bgPanel) {
        document.body.removeChild(document.getElementById("$mile_bg"));
      }
      bgPanel = document.createElement("div");
      var bgStyle = "position: absolute; left: 0px; top: 0px; width: 100%; height: 100%; background: rgba(0, 0, 0, 0.4);";
      bgPanel.setAttribute("id", "$mile_bg");
      bgPanel.setAttribute("style", bgStyle);
      var urlPanel = document.createElement("div");
      var urlStyle = "margin: 70px; padding: 20px; font-size: 40px; color: #FFF; background: rgba(0, 0, 0, 0.6); text-align: center;";
      urlPanel.setAttribute("id", "$mile_url");
      urlPanel.setAttribute("style", urlStyle);
      document.body.appendChild(bgPanel);
      bgPanel.appendChild(urlPanel);
      urlPanel.innerHTML = MILE.mileurl;
    } else {
      bgPanel = document.getElementById("$mile_bg");
      if (bgPanel) {
        document.body.removeChild(document.getElementById("$mile_bg"));
      }
    }
  },
  showDebug: function(visible) {
    var debugPanel;
    if (visible) {
      debugPanel = document.createElement("div");
      var debugStyle = "position: absolute; top: 0px; right: 0px; width: 250px; height: 250px; padding: 5px; border: 2px #489 solid; color: #333; background: rgba(128, 128, 128, 0.1); overflow-y: auto; font-size: 11px; list-style-type: none;";
      debugPanel.setAttribute("id", "$mile_debug");
      debugPanel.setAttribute("style", debugStyle);
      debugPanel.innerHTML = "<div id='$mile_debug_title' style='font-size: 15px; font-weight: bold; color: #379; padding: 8px;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;MILE Debug Panel</div>";
      document.body.appendChild(debugPanel);
    } else {
      debugPanel = document.getElementById("$mile_debug");
      if (debugPanel) {
        document.body.removeChild(document.getElementById("$mile_debug"));
      }
    }
    MILE.debug = visible;
  },
  showMileStatus: function(visible) {
    var statusPanel;
    if (visible) {
      statusPanel = document.createElement("div");
      var bgStyle = "position: absolute; bottom: 0px; left: 0px; width: 100%; height: 40px; background: #356; color: #FFF; font-size: 25px; text-align: center; padding-top: 5px;";
      statusPanel.setAttribute("id", "$mile_status");
      statusPanel.setAttribute("style", bgStyle);
      document.body.appendChild(statusPanel);
    } else {
      statusPanel = document.getElementById("$mile_status");
      if (statusPanel) {
        document.body.removeChild(document.getElementById("$mile_status"));
      }
    }
  },
  updateMileStatus: function() {
    var statusPanel = document.getElementById("$mile_status");
    statusPanel.innerHTML = MILE.mileurl;
    // show my connection id on left side of the status panel
    var myidElm = document.createElement("span");
    var myidElmStyle = "position: absolute; left: 20px;";
    myidElm.setAttribute("style", myidElmStyle);
    myidElm.innerHTML = "ID: " + MILE.mileid;
    statusPanel.appendChild(myidElm);
    // show connected web apps on right side of the status panel
    var connElm, connElmStyle, connPos = 0;
    for (var i = 0; i < MILE.connections.length; i++) {
      if (MILE.connections[i] != MILE.mileid) {
        connElm = document.createElement("span");
        connElmStyle = "position: absolute; right: " + (connPos++ * 100 + 30) + "px;";
        connElm.setAttribute("style", connElmStyle);
        connElm.innerHTML = MILE.connections[i];
        statusPanel.appendChild(connElm);
      }
    }
  }
};
// load socket.io framework
var script = document.createElement("script");
script.src = "socket.io.js";
document.head.appendChild(script);


MILE.setAppID('asdf');
MILE.setAuthor('adf');
MILE.setVersion('1');
MILE.setAppURL('bang');
