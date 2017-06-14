MILE.on("gameStart", function(data, from){
    $.mobile.changePage('#sharedMain');
    var totalInfo = JSON.parse(data);
    var numPlayer = totalInfo.length;
    for(i = 0; i < numPlayer; i ++){
        var playerN = "<div id=\"player" + i + "\">";
        var info = totalInfo[i];
        var job = info.job;
        var character = info.character;
        var maxLife = info.maxLife;
        var curLife = info.curLife;
        var isDead = info.dead;
        var mounted = info.mounted;

        var charName = character.name;
        var charImage = character.image;
        var charEffect = character.effect;
        var jobName = job.name;
        var jobImage = job.image;
        var information = "<div id='status" + i + "' <img src='" + charImage + "' width='50' heigth='100'> <img src='" + jobImage + "'width='50' height='100'> <p>" + charName + "\nEFFECT: " + charEffect + "\nLIFE(cur/max): " + curLife + "/" + maxLife + "</p> </div>";
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

