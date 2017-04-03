// MILE Framework Code (FIXME: remove a piece of app code regarding event listeners)
var MILE = {
  socket: null,
  cbfunc: [],
  mileurl: "",
  init: function() {
    // Web Socket Init
    socket = new io.connect("ws://mile.cafe24app.com");
    socket.on("connect", function() {
      MILE.log("init", "success");
    });
    // A message is arrived from the server
    socket.on("message", function(msg){
      console.log(msg);
      MILE.MileHandler(msg.type, msg.data);
    });
  },
  // A message is sent to server
  send: function(type, data) {
    socket.emit("message", {type: type, data: data});
  },
  on: function(type, callback) {
    MILE.cbfunc[type] = callback;
  },
  // A message is classified and handled according to the type
  MileHandler: function(type, data) {
    switch (type) {
      case "$mile_id":
        MILE.log(type, "ID: " + data);
        break;
      case "$mile_primary":
        MILE.mileurl = data;
        MILE.url_show(MILE.mileurl);
        MILE.log(type, data);
        break;
      case "$mile_secondary":
        MILE.mileurl = data;
        MILE.url_hide();
        MILE.log(type, data);
        break;
      case "$mile_join":
        MILE.log(type, data + " has been connected");
        break;
      case "$mile_leave":
        MILE.log(type, data + " has been disconnected");
        break;
      case "$mile_update":
        var output = "";
        data = data.connections;
        if (data.length == 1) {
          MILE.url_show(MILE.mileurl);
        } else if (data.length > 1) {
          MILE.url_hide();
        }
        for (var i = 0; i < data.length; i++) {
          output += data[i] + " ";
        }
        output += "(" + data.length + ")";
        MILE.log(type, output)
        break;
    }
    if (MILE.cbfunc[type] != undefined) {
      MILE.cbfunc[type].call(MILE, data);
    } else if (type.indexOf("$mile_") == -1) {
      MILE.log("ERROR!!! " + type, data);
    }
  },
  log: function(type, data) {
    console.log("[type]: " + type + ", [data]: " + data);
  },
  url_show: function(url) {
    var popup;

    if(document.getElementById("url_popup")) {
      popup = document.getElementById("url");
    }
    else {
      popup = document.createElement("div");
      popup.setAttribute("id", "url");
      popup.appendChild(document.createTextNode(url));
      document.documentElement.appendChild(popup);
    }
    popup.style = "display: block";
  },
  url_hide: function() {
    if(document.getElementById("url")) {
      document.getElementById("url").style = "display: none";
    }
  }
}