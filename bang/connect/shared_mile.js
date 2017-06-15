if ('serviceWorker' in navigator) {
  window.addEventListener('load', function() {
    navigator.serviceWorker.register('sw.js').then(function(registration) {
      // Registration was successful
      console.log('ServiceWorker registration successful with scope: ', registration.scope);
    }).catch(function(err) {
      // registration failed :(
      console.log('ServiceWorker registration failed: ', err);
    });
  });
}

// navigator.serviceWorker.register('sw.js');
Notification.requestPermission(function(result) {
  if (result === 'granted') {
    navigator.serviceWorker.ready.then(function(registration) {
      registration.showNotification('Welcome to "Bang!" Board Game!', {
          timeout: 3000
      });
      setTimeout(function () {
          registration.close.bind(registration);
      }, 3000);
    });
  }
});

MILE.on('game_start', function(data, from) {
    document.getElementById("$mile_status").setAttribute("hidden",true);
    Notification.requestPermission(function(result) {
      if (result === 'granted') {
        navigator.serviceWorker.ready.then(function(registration) {
          registration.showNotification('Game Start!', {
              body: '',
              timeout: 3000
          });
        });
      }
    });
});

MILE.on("gameScreen", function(data, from){
    $.mobile.changePage('#sharedMain');
    var totalInfo = JSON.parse(data);
    var total = totalInfo.total;
    var numPlayer = total.length;
    $('#mainPage').empty();
    for(i = 0; i < numPlayer; i ++){
        var playerN = "<div id=\"player" + i + "\">";
        var info = total[i];
        var job = info.job;
        var character = info.character;
        var maxLife = info.maxLife;
        var curLife = info.curLife;
        var isDead = info.dead;
        var mounted = info.mounted;
        var inHand = info.inHand;
        var turn = info.turn;

        var charName = character.name;
        var charImage = character.image;
        var charEffect = character.effect;
        var jobName = job.name;
        var jobImage = job.image;
        var information = "<div id='images'> <img src='" + charImage + "' width='50' height='77.38'> <img src='" + jobImage + "'width='50' height='77.38'></div> <div id='contents'> <p>" + charName + "</p><p>EFFECT: " + charEffect + "</p><p>LIFE(cur/max): " + curLife + "/" + maxLife + "</p><p> In Hand: " + inHand +"</p> </div> <div id='cards'>";
        playerN = playerN + information;
        if(!isDead){
            var totalImage = ""
            for(j = 0; j < mounted.length; j ++){
                var imageN = "<img src='" + mounted[j] + "' width='50' height='77.38'>";
                totalImage = totalImage + imageN;
            }
            playerN = playerN + totalImage;
        }
        else{
            var dead = "<p style='font-size: 15px;'>Dead player</p><p>JOB: " + job.name + "</p>";
            playerN = playerN + dead;
        }
        playerN = playerN + "</div>"
        $('#mainPage').append(playerN);
        if(turn){
            $('#player' + i).css('border', '1px solid red');
}
        else{
            $('#player' + i).css('border', '1px solid black');
        }
    }
    $('#contents').css('float', 'left');
    $('#contents').css('display', 'inline');
});

MILE.on('personalAction', function(data, from){
    var info = JSON.parse(data);
    var _from = info.from;
    var to = info.to;
    var used = info.used;
    Notification.requestPermission(function(result) {
      if (result === 'granted') {
        navigator.serviceWorker.ready.then(function(registration) {
          registration.showNotification(used, {
              body: _from + " used " + used + " to " + to,
              timeout: 3000
          });
        });
      }
    });
});

MILE.on('publicAction', function(data, from){
    var info = JSON.parse(data);
    var who = info.who;
    var used = info.used;

    Notification.requestPermission(function(result) {
      if (result === 'granted') {
        navigator.serviceWorker.ready.then(function(registration) {
          registration.showNotification(used, {
              body: who + " used " + used,
              timeout: 3000
          });
        });
      }
    });
    // self.registration.showNotification(used, {
    //     body: who + " used " + used,
    //     // tag: 'simple-push-demo-notification',
    //     timeout: 3000
    // });
});

MILE.on('loseLife', function(data, from){
    var info = JSON.parse(data);
    var who = info.who;
    var much = info.much;
    var by = info.by;
    var title = "Life changes";
    var message = who + " loses life " + much + " by " + by;

    Notification.requestPermission(function(result) {
      if (result === 'granted') {
        navigator.serviceWorker.ready.then(function(registration) {
          registration.showNotification(title, {
              body: message,
              timeout: 3000
          });
        });
      }
    });
    // self.registration.showNotification(title, {
    //     body: message,
    //     tag: 'simple-push-demo-notification',
    //     timeout: 3000
    // });
    // self.addEventListener('push', function() {
    //     if (!(self.Notification && self.Notification.permission === 'granted')) {
    //         console.log('ssibal');
    //         return;
    //     }
    //     var title = "Life changes";
    //     var message = who + " loses life " + much + " by " + by;
    //     var notification = new self.registration.Notification(title, {
    //         body: message,
    //         tag: 'simple-push-demo-notification',
    //         timeout: 3000
    //     });
    // });
});

MILE.on('gainLife', function(data, from){
    var info = JSON.parse(data);
    var who = info.who;
    var much = info.much;
    var by = info.by;
    var title = "Life changes";
    var message = who + " gains life " + much + " by " + by;

    Notification.requestPermission(function(result) {
      if (result === 'granted') {
        navigator.serviceWorker.ready.then(function(registration) {
          registration.showNotification(title, {
              body: message,
              timeout: 3000
          });
        });
      }
    });
});

MILE.on('dead', function(data, from){
    var info = JSON.parse(data);
    var who = info.who;
    var by = info.by;
    var job = info.job;

    Notification.requestPermission(function(result) {
      if (result === 'granted') {
        navigator.serviceWorker.ready.then(function(registration) {
          registration.showNotification('Dead Player', {
              body: 'Job: ' + job + '\n' + who + ' dead by ' + by,
              timeout: 3000
          });
        });
      }
    });
});

MILE.on('gameover', function(data, from){
    $.mobile.changePage('#gameover');
    var info = JSON.parse(data);
    var winner = info.winner;
    var condition = info.condition;
    var gameOverStr = "<p>Game Over.</p><p>Winner: " +winner + "</p><p>Condition: "+ condition + "</p>";
    Notification.requestPermission(function(result) {
      if (result === 'granted') {
        navigator.serviceWorker.ready.then(function(registration) {
          registration.showNotification('Game over', {
              body: 'The winner is ' + winner,
              timeout: 3000
          });
        });
      }
    });
    $('#overScreen').append(gameOverStr);
});
