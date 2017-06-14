MILE.on("gameScreen", function(data, from){
    $.mobile.changePage('#sharedMain');
    var totalInfo = JSON.parse(data);
    var total = totalInfo.total;
    var numPlayer = total.length;
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

        var charName = character.name;
        var charImage = character.image;
        var charEffect = character.effect;
        var jobName = job.name;
        var jobImage = job.image;
        var information = "<div id='status" + i + "' <img src='" + charImage + "' width='50' heigth='100'> <img src='" + jobImage + "'width='50' height='100'> <p>" + charName + "\nEFFECT: " + charEffect + "\nLIFE(cur/max): " + curLife + "/" + maxLife + "\n In Hand: " + inHand +"</p> </div>";
        playerN = playerN + information;
        if(!isDead){
            var totalImage = ""
            for(j = 0; j < mounted.length; j ++){
                var imageN = "<img src='" + mounted[j] + "' width='50' heigth='100'>";
                totalImage = totalImage + imageN;
            }
            playerN = playerN + totalImage;
        }
        else{
            var dead = "<p style='font-size: 15px;'>Dead player\nJOB: " + job + "</p>";
            playerN = playerN + dead;
        }
        playerN = playerN + "</div>"
        $('#mainPage').append(playerN);
    }
});

MILE.on('personalAction', function(data, from){
    var info = JSON.parse(data);
    var from = info.from;
    var to = info.to;
    var used = info.used;
}
MILE.on('publicAction', function(data, from){
    var info = JSON.parse(data);
    var who = info.who;
    var used = info.used;
}
MILE.on('loseLife', function(data, from){
    var info = JSON.parse(data);
    var who = info.who;
    var much = info.much;
    var by = info.by;
}
MILE.on('gainLife', function(data, from){
    var info = JSON.parse(data);
    var who = info.who;
    var much = info.much;
    var by = info.by;
}
MILE.on('dead', function(data, from){
    var info = JSON.parse(data);
    var who = info.who;
    var by = info.by;
    var job = info.job;
}
