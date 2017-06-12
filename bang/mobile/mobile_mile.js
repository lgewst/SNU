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
            // $.mobile.changePage("#background");
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
    var mounted = info.mountedCards; // weapon, condition
    var inHand = info.inHandCards; //array
    var weapon = mounted.weapon;
    var condition = mounted.condition;

    var jobImage = "<img src=\"" + job.image + "\" width=\"50\" height=\"100\">";
    var jobName = "<p id=\"jobName\"> JOB: " + job.name + "</p>";
    var jobMission = "<p id=\"jobMission\"> MISSION: " + job.mission + "</p>";

    var characterImage = "<img src=\"" + character.image + "\" width=\"50\" height=\"100\">";
    var charName = "<p id=\"charName\"> CHARARCTER: " + character.name + "</p>";
    var charEffect = "<p id=\"charEffect\"> EFFECT: " + character.effect + "</p>";
    var life = "<p id=\"life\"> LIFE(cur/max): " + curLife + "/" + maxLife +"</p>"
    $('#listContent').empty();
    $('#images').empty();
    $('#contents').empty();
    $('#mountedWeaponCardsList').empty();
    $('#mountedConditionCardsList').empty();
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
    for(i = 0; i < weapon.length; i++){
        var cardImageN = "<img src=\"" + weapon[i] + "\" width=\"50\" height=\"90\" >";
        var cardDiv = "<div id=\"mountedWeaponCard" + i + "\" style=\"display: inline;\">" + cardImageN + "</div>";
        $('#mountedWeaponCardsList').append(cardDiv);
    }
    for(i = 0; i < condition.length; i++){
        var cardImageN = "<img src=\"" + condition[i] + "\" width=\"50\" height=\"90\" >";
        var cardDiv = "<div id=\"mountedConditionCard" + i + "\" style=\"display: inline;\">" + cardImageN + "</div>";
        $('#mountedConditionCardsList').append(cardDiv);
    }
    for(i = 0; i < inHand.length; i++){
        var cardImageN = "<img src=\"" + inHand[i] + "\" activated=" + inHand[i] + " width=\"50\" height=\"90\" >";
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
    var weapon = mounted.weapon;
    var condition = mounted.condition;

    var jobImage = "<img src=\"" + job.image + "\" width=\"50\" height=\"100\">";
    var jobName = "<p id=\"jobName\"> JOB: " + job.name + "</p>";
    var jobMission = "<p id=\"jobMission\"> MISSION: " + job.mission + "</p>";

    var characterImage = "<img src=\"" + character.image + "\" width=\"50\" height=\"100\">";
    var charName = "<p id=\"charName\"> CHARARCTER: " + character.name + "</p>";
    var charEffect = "<p id=\"charEffect\"> EFFECT: " + character.effect + "</p>";
    var life = "<p id=\"life\"> LIFE(cur/max): " + curLife + "/" + maxLife +"</p>"

    var inHandCardBack = "<img src=\"" + inHand.image + "\" width=\"50\" height=\"90\" >";
    var inHandCardNum = inHand.num;
    $('#othersImages').empty();
    $('#othersContents').empty();
    $('#othersWeaponCardsList').empty();
    $('#othersConditionCardsList').empty();
    $('#othersInHandCardsList').empty();

    $('#othersImages').append(jobImage);
    $('#othersImages').append(characterImage);

    $('#othersContents').append(jobName);
    $('#othersContents').append(jobMission);
    $('#othersContents').append(life);
    $('#othersContents').append(charName);
    $('#othersContents').append(charEffect);
    for(i = 0; i < weapon.length; i++){
        var cardImageN = "<img src=\"" + weapon[i] + "\" width=\"50\" height=\"90\" >";
        var cardDiv = "<div id=\"othersWeaponCard" + i + "\" style=\" display: inline;\">" + cardImageN + "</div>";
        $('#othersWeaponCardsList').append(cardDiv);
    }
    for(i = 0; i < condition.length; i++){
        var cardImageN = "<img src=\"" + condition[i] + "\" width=\"50\" height=\"90\" >";
        var cardDiv = "<div id=\"othersConditionCard" + i + "\" style=\" display: inline;\">" + cardImageN + "</div>";
        $('#othersConditionCardsList').append(cardDiv);
    }
    for(i = 0; i < inHandCardNum; i++){
        var cardDiv = "<div id=\"inHandCard" + i + "\" style=\"display: inline;\">" + inHandCardBack + "</div>";
        $('#othersInHandCardsList').append(cardDiv);
    }
});
MILE.on('respondCardInfo', function(data, from){
    //imgSrc: img source of the card
    $.mobile.changePage('#showCardInfo');
    // var info = JSON.parse(data);
    // var imgSrc = info.image;
    var imgSrc = data;
    var image = "<img src=\"" + imgSrc + "\">";
    $('#showCardImage').empty();
    $('#showCardImage').append(image);
    /* add div attribute if the card is selectable*/
});
MILE.on('help', function(data, from){
    $.mobile.changePage('#cardExplain');
    var info = JSON.parse(data);
    var helpStr = "<p id=\"helpStr\">" + info.help + "</p>";
    $('#explanation').empty();
    $('#explanation').append(helpStr);

});
MILE.on('askPlay', function(data, from){
    //my turn started, what card are you going to use?
    $.mobile.changePage('#cardToSelect');
    var info = JSON.parse(data);
    var cardList = info.cardList;
    var helpStr = "Your turn! Select card to play.";
    $('#selectSentence').empty();
    $('#selectCardsList').empty();

    $('#selectSentence').append(helpStr);

    for(i = 0; i < cardList.length; i++){
        console.log(cardList[i].image + '\t' + cardList[i].able);
        var cardImageN = "<img src=\"" + cardList[i].image + "\" width=\"50\" height=\"90\" >";
        var cardDiv = "<div id=\"selectableCard" + i + "\" able=" + cardList[i].able + " style=\" display: inline;\">" + cardImageN + "</div>";
        $('#selectCardsList').append(cardDiv);
    }
});
MILE.on('askDiscard', function(data, from){
    //what card are you going to discard?(my turn ends)
    $.mobile.changePage('#cardToDiscard');
    var info = JSON.parse(data);
    var cardList = info.cardList;
    var limit = info.limit;
    var helpStr = "Your turn ends! Select card to discard. The number of remainig cards must be less than your current life.(" + limit + ")" ;
    $('#discardSentence').empty();
    $('#discardCardsList').empty();

    $('#discardSentence').append(helpStr);

    for(i = 0; i < cardList.length; i++){
        var cardImageN = "<img src=\"" + cardList[i] + "\" width=\"50\" height=\"90\" >";
        var cardDiv = "<div id=\"discardingCard" + i + "\" style=\" display: inline;\">" + cardImageN + "</div>";
        $('#discardCardsList').append(cardDiv);
    };
});
MILE.on('respondBang', function(data, from){
    //Will you use your bang card for dual or indian?
    $.mobile.changePage('#willUseBang');
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
    $.mobile.changePage('#willUseMiss');
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
    $.mobile.changePage('#willUseBeer');
    var info = JSON.parse(data);
    var helpStr = "You don't have any life now. You can revive if you use your 'Beer' card, or you will be dead. Will you use it?";
    $('#beerSentence').empty();
    $('#beerSentence').append(helpStr);
});
MILE.on('askTarget', function(data, from){
    $.mobile.changePage('#selectTarget');
    var info = JSON.parse(data);
    var targetList = info.targetList;
    $('#selectTargetSentence').empty();
    $('#selectTargetList').empty();

    var helpStr = "You should choose target player.";
    $('#selectTargetSentence').append();
    for(i = 0; i < targetList.length; i++){
       var targetN = "<p id='target" + i + "'>"+ targetList[i] + " </p>";
       $('#selectTargetList').append(targetN);
    }
});
MILE.on('askTargetCard', function(data, from){
    $.mobile.changePage('#selectTargetCard');
    var info = JSON.parse(data);
    var targetCardList = info.targetCardList;
    var who = info.who;
    var weapon = targetCardList.weapon;
    var condition = targetCardList.condition;
    var inHandBack = "<img src=\"" + targetCardList.inHand.image + "\" width=\"50\" height=\"90\" >"; //imgsrc
    var inHandNum = targetCardList.inHand.num;
    $('#selectTargetCardSentence').empty();
    $('#targetWeaponCardsList').empty();
    $('#targetConditionCardslist').empty();
    $('#targetInHandCardsList').empty();
    var helpStr = "You should choose target card from " + who +"." ;
    $('#selectTargetCardSentence').append();
    for(i = 0; i < weapon.length; i++){
       var cardImageN = "<img src=\"" + weapon[i] + "\" width=\"50\" height=\"90\" >";
       $('#targetWeaponCardsList').append(cardImageN);
    }
    for(i = 0; i < condition.length; i++){
       var cardImageN = "<img src=\"" + weapon[i] + "\" width=\"50\" height=\"90\" >";
       $('#targetConditionCardsList').append(cardImageN);
    }
    for(i = 0; i < inHandNum; i++){
       $('#targetInHandCardsList').append(inHandBack);
    }
});
MILE.on('loseLife', function(data, from){
    $.mobile.changePage('#lifeLost');
    var info = JSON.parse(data);
    var much = info.much;
    var remain = info.remain;
    var sentence = "<p> You lost "+ much + "life. Your remaining life is " + remain+".</p>";
    $('#lifeLostPopUp').empty();
    $('#lifeLostPopUp').append(sentence);
});
