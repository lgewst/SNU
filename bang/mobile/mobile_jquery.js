$(document).on("pageshow", "#readyPage", function(){
        $("#ready").on("tap", function(){
            MILE.send("ready", "Client ready");
            });
        });
$(document).on("pageshow", "#background", function(){
        //TODO: Changed
        MILE.send("playerInfo", "Request basic information");
        $("#otherPlayersList").on('tap', function(){
            var tagName = event.target.tagName;
            var id = event.target.id;
            if(tagName ==="P"){
                var content = document.getElementById(id).innerHTML;
                MILE.send("otherPlayerInfo", content);
                }
            });
        $("#mountedCards").on('tap', function(){
            var tagName = event.target.tagName;
            if(tagName === "IMG"){
                var content = $(event.target).attr('src');
                MILE.send("inHandCardInfo", content);
                }
            });
        $("#inHandCardsList").on('tap', function(){
            var tagName = event.target.tagName;
            if(tagName === "IMG"){
                var content = $(event.target).attr('src');
                MILE.send("inHandCardInfo", content);
                }
            });
        /* TODO
           1. Ask server
           - other player list(character names)
           - my information(class Player)
           2. Display information
           - player list to collapsible list
           - my information
           3. Add event listener on each cards
           - If the user tap on the card, make a popup with card image and buttons
           4. Add event listener on each player names
           - If the user tap on the name, ask server to give information about the player and make a popup
         */
});
$(document).on("pageshow", "#playerInfo", function(){
        $("#mountedCards").on('tap', function(){
            var tagName = event.target.tagName;
            if(tagName === "IMG"){
                var content = $(event.target).attr('src');
                }
            });
        //$().on(,function(){
        //    $.mobile.back();
        //}
        /* TODO
           1. Ask server
           Display information
           - player information
           2. Add event listener on each cards
           - If the user tap on the card, make a popup with card image and buttons
         */
        });
$(document).on("pageshow", "#cardInfo", function(){
        var content; /* img src */
        $("#help").on("tap", function(){
            var content = $(this).siblings("img").attr('src');
            alert("To check tags: " + content);
            MILE.send("help", content);
            });
        $("#select").on("tap", function(){
            var content = $(this).siblings("img").attr('src');
            alert("To check tags: " + content);
            MILE.send("select", content);
            });
        /* TODO
           1. Display card image and buttons
           2. Add event listener on each buttons
           - If the user tap on the "Select" button, send the information to the server and close the popup
           - If the user tap on the "Cancel" button, just close the popup
           - If the user tap on the "Help" button, ask server to give information and make a popup
         */
        });
