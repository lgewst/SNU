window.addEventListener("load", function(){
        MILE.setAppID("connect");
        MILE.setAuthor("snu");
        MILE.setVersion("0.1");
        MILE.setAppURL("http://localhost:8001/mobile/");
        MILE.showMileURL(true);
        MILE.showDebug(false);
        MILE.init();
        // ready
        });
MILE.on('ready', function(data, from) {
        console.log("From server considering ready: " + data);
        });
MILE.on('game_start', function(data, from) {
        console.log("From server considering ready: " + data);
        $('#willStart').empty();
        $('#willStart').append("Game will start in <span id=\"count\">5</span> seconds.");
        var counter = 5;
        setInterval(function(){
            counter--;
            if (counter >= 0){
            span = document.getElementById("count");
            span.innerHTML = counter;
            }
            if (counter == 0){
            clearInterval(counter);
            $.mobile.changePage("#background");
            MILE.send('initPlayerInfo', '');
            }
            }, 1000);
        });
MILE.on('playerInfo', function(data, from) {
        console.log(data);
        var info = JSON.parse(data);
        var otherPlayers = info.otherPlayers; // str array
        var job = info.job; // image, str(name), str(mission)
        var character = info.character; // image, str(name), str(effect)
        var curLife = info.curLife; // number
        var maxLife = info.maxLife;
        var mounted = info.mountedCards; // {image, cardName} array
        var inHand = info.inHandCards; //array
        var jobImage = "<img src=\"" + job.image + "\" width=\"50\" heigth=\"100\">";
        var jobName = "<p id=\"jobName\"> JOB: " + job.name + "</p>";
        var jobMission = "<p id=\"jobMission\"> MISSION: " + job.mission + "</p>";

        var characterImage = "<img src=\"" + character.image + "\" width=\"50\" heigth=\"100\">";
        var charName = "<p id=\"charName\"> CHARARCTER: " + character.name + "</p>";
        var charEffect = "<p id=\"charEffect\"> EFFECT: " + character.effect + "</p>";
        var life = "<p id=\"life\"> LIFE(cur/max): " + curLife + "/" + maxLife +"</p>"
        for(i = 0; i < otherPlayers.length; i++){
            var playerN = "<p id=\"player" + i + "\" >" + otherPlayers[i] + "</p>";
            $('#listContent').append(playerN);
        }
        $('#otherPlayersList').trigger("collapse");
        $('#images').append(jobImage);
        $('#images').append(characterImage);

        $('#contents').append(jobName);
        $('#contents').append(jobMission);
        $('#contents').append(charName);
        $('#contents').append(charEffect);
        for(i = 0; i < mounted.length; i++){
            var cardImageN = "<img src=\"" + mounted[i].image + "\" width=\"50\" height=\"90\" >";
            var cardDiv = "<div id=\"mountedCard" + i + "\" style=\"display: inline;\">" + cardImageN + "</div>";
            $('#mountedCards').append(cardDiv);
        }
        for(i = 0; i < inHand.length; i++){
            var cardImageN = "<img src=\"" + inHand[i] + "\" width=\"50\" height=\"90\" >";
            var cardDiv = "<div id=\"inHandCard" + i + "\" style=\"display: inline;\" >" + cardImageN + "</div>";
            $('#inHandCardsList').append(cardDiv);
        }
});
MILE.on('otherPlayerInfo', function(data, from){
        var info = JSON.parse(data);
        var job = info.job; // image, str(name), str(mission)
        /* Other players job show when he/she is a sceriff or is dead */
        var character = info.character; // image, str(name), str(effect)
        var curLife = info.curLife; // number
        var maxLife = info.maxLife;
        var mounted = info.mountedCards; // image array
        var inHand = info.inHandCards; //image, num
        var jobImage = "<img src=\"" + job.image + "\" width=\"50\" heigth=\"100\">";
        var jobName = "<p id=\"jobName\"> JOB: " + job.name + "</p>";
        var jobMission = "<p id=\"jobMission\"> MISSION: " + job.mission + "</p>";

        var characterImage = "<img src=\"" + character.image + "\" width=\"50\" heigth=\"100\">";
        var charName = "<p id=\"charName\"> CHARARCTER: " + character.name + "</p>";
        var charEffect = "<p id=\"charEffect\"> EFFECT: " + character.effect + "</p>";
        var life = "<p id=\"life\"> LIFE(cur/max): " + curLife + "/" + maxLife +"</p>"

        var inHandCardBack = "<img src=\"" + inHand.image + "\" >";
        var inHandCardNum = inHand.num;
        $('#images').append(jobImage);
        $('#images').append(characterImage);

        $('#contents').append(jobName);
        $('#contents').append(jobMission);
        $('#contents').append(charName);
        $('#contents').append(charEffect);
        for(i = 0; i < mounted.length; i++){
            var cardImageN = "<img src=\"" + mounted[i].image + "\" >";
            var cardDiv = "<div id=\"mountedCard" + i + "\" style=\" display: inline;\">" + cardImageN + "</div>";
            $('#mountedCards').append(cardDiv);
        }
        for(i = 0; i < inHandCardNum; i++){
            var cardDiv = "<div id=\"inHandCard" + i + "\" style=\"display: inline;\">" + inHandCardBack + "</div>";
            $('#inHandCardsList').append(cardDiv);
        }
});
MILE.on('bangCard', function(data, from) {
        // console.log("<img src=\"" + data + "\" width=\"50\" height=\"100\">");
        });
MILE.on('mountedCardInfo', function(data, from){
        $.mobile.changePage('#cardInfo');
        var info = JSON.parse(data);
        var imageSrc = info.image;
        var activated = info.activated;
        var image = "<img src=\"" + imageSrc + "\">";
        $('#cardImage').append(image);
        /* add div attribute if the card is selectable*/
        });
MILE.on('inHandCardInfo', function(data, from){
        $.mobile.changePage('#cardInfo');
        var info = JSON.parse(data);
        var imageSrc = info.image;
        var activated = info.activated;
        var image = "<img src=\"" + imageSrc + "\">";
        $('#cardImage').append(image);
        /* add div attribute if the card is selectable*/
        });
MILE.on('help', function(data, from){
        $.mobile.changePage('#cardExplain');
        var info = JSON.parse(data);
        var helpStr = "<p id=\"helpStr\">" + info.help + "</p>";
        $('#explanation').append(helpStr);

        });
MILE.on('select', function(data, from){
        });
