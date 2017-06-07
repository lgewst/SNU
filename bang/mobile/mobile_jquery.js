$(document).on("pageshow", "#readyPage", function(){
        $("#ready").on("tap", function(){
            MILE.send("ready", "Client ready");
            });
        });
$(document).on("pageshow", "#background", function(){
        MILE.send("playerInfo", "Request basic information");
        $("#otherPlayersList").on('tap', function(){
            var tagName = event.target.tagName;
            var id = event.target.id;
            if(tagName ==="P"){
                var content = document.getElementById(id).innerHTML;
                MILE.send("otherPlayerInfo", content);
                }
            });
        $("#mountedCardsList").on('tap', function(){
            var tagName = event.target.tagName;
            if(tagName === "IMG"){
                var content = $(event.target).attr('src');
                $.mobile.changePage('#mountedCardInfo', {data: {'imgsrc': content}});
            }
            });
        $("#inHandCardsList").on('tap', function(){
            var tagName = event.target.tagName;
            if(tagName === "IMG"){
                var content = $(event.target).attr('src');
                MILE.send("inHandCardInfo", content);
                }
            });
});
$(document).on("pageshow", "#playerInfo", function(){
        $("#othersMountedCardsList").on('tap', function(){
            var tagName = event.target.tagName;
            if(tagName === "IMG"){
                var content = $(event.target).attr('src');
                $.mobile.changePage('#mountedCardInfo', {data: {'imgsrc': content}});
                }
            });
        });
$(document).on("pageshow", "#mountedCardInfo", function(){
        var content = $(this).data('imgsrc'); /* img src */
        var image = "<img src=\"" + content + "\">";
        $('#mountedCardImage').empty();
        $('#mountedCardImage').append(image);
        $("#help").on("tap", function(){
            //var content = $(this).siblings("img").attr('src');
            alert("To check tags: " + content);
            MILE.send("help", content);
            });
        });
$(document).on("pageshow", "#inHandCardInfo", function(){
        $("#help").on("tap", function(){
            //var content = $(this).siblings("img").attr('src');
            alert("To check tags: " + content);
            MILE.send("help", content);
            });
        $("#select").on("tap", function(){
            //var content = $(this).siblings("img").attr('src');
            alert("To check tags: " + content);
            MILE.send("select", content);
            });
        });
