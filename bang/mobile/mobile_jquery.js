$(document).on("pageshow", "#readyPage", function(){
    $("#ready").off("tap").on("tap", function(){
        MILE.send("ready", "Client ready");
    });
});
$(document).on("pageshow", "#background", function(){
    console.log('chage background');
    // MILE.send("playerInfo", "Request basic information");
    $("#otherPlayersList").off("tap").on('tap', function(){
        var tagName = event.target.tagName;
        var id = event.target.id;
        if(tagName ==="P"){
            var content = document.getElementById(id).innerHTML;
            MILE.send("otherPlayerInfo", content);
        }
    });
    $("#cards").off("tap").on('tap', function(){
        var tagName = event.target.tagName;
        if(tagName === "IMG"){
            var content = $(event.target).attr('src');
            MILE.send('requestCardInfo',content);
        }
    });
});
$(document).on("pageshow", "#playerInfo", function(){
    $("#othersMountedCards").off("tap").on('tap', function(){
        var tagName = event.target.tagName;
        if(tagName === "IMG"){
            var content = $(event.target).attr('src');
            MILE.send('requestCardInfo', content);
        }
    });
    $('#back').off("tap").on('tap', function(){
        //$.mobile.changePage('#background');
        MILE.send("playerInfo", "Request basic information");
    });
});
$(document).on("pageshow", "#showCardInfo", function(){
    $("#help").off("tap").on("tap", function(){
        var content = $(this).siblings("img").attr('src');
        alert("To check tags: " + content);
        MILE.send("help", content);
    });
});

$(document).on("pageshow", "#cardToSelect", function(){
    var index = "";
    $("#selectCardsList").off("tap").on('tap', function(){
        var tagName = event.target.tagName;
        if(tagName === "IMG"){

            //var parent = $(event.target).parent();
            //if($(parent).hasClass('ui-disabled')){
            //$(parent).removeClass('ui-disabled');
            //}
            var selected = $(event.target.id);
            index = $('img').index(selected);
            //var able= $(event.target).parent().attr('able');
            //if(!able){
            //$(parent).addClass('ui-disabled');
            //}
        }
    });
    $('#select').off('tap').on('tap', function(){
        if(index!=""){
            MILE.send("selectPlayingCard", content);
        }
    });
    $('#endTurn').off('tap').on('tap', function(){
        MILE.send("selectPlayingCard", -1);
    });
});
$(document).on("pageshow", "#cardToDiscard", function(){
    var content;
    $("#discardCardsList").off("tap").on('tap', function(){
        var tagName = event.target.tagName;
        if(tagName === "IMG"){
            var selected = $(event.target.id);
            content = $('img').index(selected);
        }
    });
    $('#discard').off('tap').on('tap', function(){
        MILE.send("discardPlayingCard", content);
    });
});
$(document).on("pageshow", "#willUseBang", function(){
    $('#bangYes').off("tap").on('tap', function(){
        MILE.send("bangRespond", true);
    });
    $('#bangNo').off("tap").on('tap', function(){
        MILE.send("bangRespond", false);
    });
});
$(document).on("pageshow", "#willUseMiss", function(){
    $('#missYes').off("tap").on('tap', function(){
        MILE.send("missRespond", true);
    });
    $('#missNo').off("tap").on('tap', function(){
        MILE.send("missRespond", false);
    });
});
$(document).on("pageshow", "#willUseBeer", function(){
    $('#beerYes').off("tap").on('tap', function(){
        MILE.send("beerRespond", true);
    });
    $('#beerNo').off("tap").on('tap', function(){
        MILE.send("beerRespond", false);
    });
});
$(document).on("pageshow", "#selectTarget", function(){
    $('#selectTargetList').off('tap').on('tap', function(){
        var tagName = event.target.tagName;
        var id = event.target.id;
        if(tagName ==="P"){
            var selected = $(event.target.id);
            var content = $('p').index(selected);
            MILE.send("selectTargetRespond", content);
        }
    });
});
$(document).on("pageshow", "#selectTargetCard", function(){
    $('#targetWeaponCardsList').off('tap').on('tap', function(){
        var tagName = event.target.tagName;
        if(tagName === "IMG"){
            MILE.send("selectTargetCardRespond", -2);
        }
    });
    $('#targetConditionCardsList').off('tap').on('tap', function(){
        var tagName = event.target.tagName;
        if(tagName === "IMG"){
            MILE.send("selectTargetCardRespond", 0);
        }
    });
    $('#targetInHandCardsList').off('tap').on('tap', function(){
        var tagName = event.target.tagName;
        if(tagName === "IMG"){
            MILE.send("selectTargetCardRespond", -1);
        }
    });
    $('#cancel').off('tap').on('tap', function(){
        MILE.send("selectTargetCardRespond", -3);
    });
});
/* TODO: respond, select target/targetcard */
