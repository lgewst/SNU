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
    $.mobile.changePage('#background');
    //console.log(data);
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
    $('#listContent').empty();
$('#images').empty();
$('#contents').empty();
$('#mountedCardsList').empty();
$('#inHandCardsList').empty();

for(i = 0; i < otherPlayers.length; i++){
    var playerN = "<p id=\"player" + i + "\" >" + otherPlayers[i] + "</p>";
    $('#listContent').append(playerN);
}
$('#otherPlayersList').trigger("collapse");

$('#images').append(jobImage);
$('#images').append(characterImage);

$('#contents').append(jobName);
$('#contents').append(jobMission);
$('#contents').append(life);
$('#contents').append(charName);
$('#contents').append(charEffect);
for(i = 0; i < mounted.length; i++){
    var cardImageN = "<img src=\"" + mounted[i].image + "\" width=\"50\" height=\"90\" >";
    var cardDiv = "<div id=\"mountedCard" + i + "\" style=\"display: inline;\">" + cardImageN + "</div>";
    $('#mountedCardsList').append(cardDiv);
}
for(i = 0; i < inHand.length; i++){
    var cardImageN = "<img src=\"" + inHand[i] + "\" width=\"50\" height=\"90\" >";
    var cardDiv = "<div id=\"inHandCard" + i + "\" style=\"display: inline;\" >" + cardImageN + "</div>";
    $('#inHandCardsList').append(cardDiv);
}
});
MILE.on('otherPlayerInfo', function(data, from){
    $.mobile.changePage('#playerInfo');
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

    var inHandCardBack = "<img src=\"" + inHand.image + "\" width=\"50\" height=\"90\" >";
var inHandCardNum = inHand.num;
$('#othersImages').empty();
$('#othersContents').empty();
$('#othersMountedCardsList').empty();
$('#othersInHandCardsList').empty();

$('#othersImages').append(jobImage);
$('#othersImages').append(characterImage);

$('#othersContents').append(jobName);
$('#othersContents').append(jobMission);
$('#othersContents').append(life);
$('#othersContents').append(charName);
$('#othersContents').append(charEffect);
for(i = 0; i < mounted.length; i++){
    var cardImageN = "<img src=\"" + mounted[i].image + "\" width=\"50\" heigth=\"90\" >";
    var cardDiv = "<div id=\"mountedCard" + i + "\" style=\" display: inline;\">" + cardImageN + "</div>";
    $('#othersMountedCardsList').append(cardDiv);
}
for(i = 0; i < inHandCardNum; i++){
    var cardDiv = "<div id=\"inHandCard" + i + "\" style=\"display: inline;\">" + inHandCardBack + "</div>";
    $('#othersInHandCardsList').append(cardDiv);
}
});
MILE.on('inHandCardInfo', function(data, from){
    //imgSrc: img source of the card
    $.mobile.changePage('#inHandCardInfo');
    var info = JSON.parse(data);
    var imgSrc = info.image;
    var activated = info.activated;
    var image = "img src=\"" + imgSrc + "\">";
    $('#inHandCardImage').empty();
    $('#inHandCardImage').append(image);
    /* add div attribute if the card is selectable*/
    if (activated === "False"){
        $('#select').addClass('ui-disabled');
    }
});
MILE.on('help', function(data, from){
    $.mobile.changePage('#cardExplain');
    var info = JSON.parse(data);
    var helpStr = "<p id=\"helpStr\">" + info.help + "</p>";
    $('#explanation').empty();
    $('#explanation').append(helpStr);

});
/*MILE.on('select', function(data, from){
  });
  */

MILE.on('askPlay', function(data, from){
    //my turn started, what card are you going to use?
    $.mobile.changePage('#cardToSelect');
    var info = JSON.parse(data);
    var cardList = info.cardList;
    var helpStr = "Your turn! Select card to play.";
    $('#selectSentence').empty();
    $('#selectCardsList').empty();

    $('#sentence').append(helpStr);

    for(i = 0; i < cardList.length; i++){
        var cardImageN = "<img src=\"" + cardList[i] + "\" width=\"50\" heigth=\"90\" >";
        var cardDiv = "<div id=\"selectableCard" + i + "\" style=\" display: inline;\">" + cardImageN + "</div>";
        $('#selectCardsList').append(cardDiv);
    });
});
MILE.on('askDiscard', function(data, from){
    //what card are you going to discard?(my turn ends)
    $.mobile.changePage('#cardToDiscard');
    var info = JSON.parse(data);
    var cardList = info.cardList;
    var limit = info.limit;
    var helpStr = "Your turn ends! Select card to discard. The number of remainig cards must be less than your maxlife(" + limit + ")" ;
    $('#discardSentence').empty();
    $('#discardCardsList').empty();

    $('#discardSentence').append(helpStr);

    for(i = 0; i < cardList.length; i++){
        var cardImageN = "<img src=\"" + cardList[i] + "\" width=\"50\" heigth=\"90\" >";
        var cardDiv = "<div id=\"discardingCard" + i + "\" style=\" display: inline;\">" + cardImageN + "</div>";
        $('#discardCardsList').append(cardDiv);
    });
});
MILE.on('respondBang', function(data, from){
    //Will you use your bang card for dual or indian?
    var info = JSON.parse(data);
    var who = info.who;
    var what = info.what;
    var num = info.num;
    var target;
    if(info.target === "True"){
        target = "you";
    }
    else{
        target = "all players";
    }
    var helpStr = who + " used " + what + " to " + target + ". You can respond by using 'Bang!' card, or you will lose 1 life. Will you use it? (Number of 'Bang!': " + num;
    $('#bangSentence').empty();
    $('#bangSentence').append(helpStr);
});
MILE.on('respondMiss', function(data, from){
    //Will you use your miss card for bang or gatling?
    //at least one miss card
    var info = JSON.parse(data);
    var who = info.who;
    var what = info.what;
    var num = info.num;
    var target;
    if(info.target === "True"){
        target = "you";
    }
    else{
        target = "all players";
    }
    var helpStr = who + " used " + what + " to " + target + ". You can avoid the attack if you use your 'Miss!' card. Will you use it? (Number of 'Miss!': " + num;
    $('#missSentence').empty();
    $('#missSentence').append(helpStr);
});
MILE.on('respondBeer', function(data, from){
    //Will you use your beer card to charge your life?
    var info = JSON.parse(data);
    var helpStr = "You don't have any life now. You can revive if you use your 'Beer' card, or you will be dead. Will you use it?";
    $('#beerSentence').empty();
    $('#beerSentence').append(helpStr);
});
MILE.on('askTarget', function(data, from){
    var info = JSON.parse(data);
    var targetList = info.targetList;
    $('#selectTargetSentence').empty();
    $('#selectTargetList').empty();

    var helpStr = "You should choose target player.";
    $('#selectTargetSentence').append();
    for(i = 0; i < targetList.length; i++){
       var targetN = "<p id='target'" + i + ">"+ targetList[i] + " </p>";
       $('#selectTargetList').append(targetN);
    }
});
MILE.on('askTargetCard', function(data, from){
    var info = JSON.parse(data);
    var targetCardList = info.targetCardList;
    $('#selectTargetCardSentence').empty();
    $('#selectTargetCardList').empty();

    var helpStr = "You should choose target card.";
    $('#selectTargetCardSentence').append();
    for(i = 0; i < targetCardList.length; i++){
       var cardImageN = "<img src=\"" + targetCardList[i] + "\" width=\"50\" heigth=\"90\" >";
       $('#selectTargetCardList').append(targetN);
    }
});
