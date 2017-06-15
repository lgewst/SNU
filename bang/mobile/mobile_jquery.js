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
            // var parent = $(event.target).parent();
            // var index = $('img').index(parent);
            var content =
        MILE.send('requestCardInfo', $(event.target).attr('src'));
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
        var content = $('#showCardImage').children("img").attr('src');
        // alert("To check tags: " + content);
        MILE.send("help", content);
    });
});

$(document).on("pageshow", "#cardToSelect", function(){
    var index, able;
    while($('#select').hasClass('ui-disabled')){
        $('#select').removeClass('ui-disabled');
    }
    $("#selectCardsList").off("tap").on('tap', function(){
        while($('#select').hasClass('ui-disabled')){
            $('#select').removeClass('ui-disabled');
        }
        var tagName = event.target.tagName;
        if(tagName === "IMG"){
            var closest = $(event.target).parent();
            closest.parent().children('div').removeAttr("style");
            closest.parent().children('div').css("display", "inline");
            closest.css("border-bottom", "2px solid red");
            index = closest.parent().children('div').index(closest);
            able = closest.attr('able');
            if(able == 'false'){
                $('#select').addClass('ui-disabled');
            }
        }
    });
    $('#select').off('tap').on('tap', function(){
        if(index != null){
            MILE.send("selectPlayingCard", index);
            $.mobile.changePage('#waiting');
        }
    });
    $('#endTurn').off('tap').on('tap', function(){
        MILE.send("selectPlayingCard", -1);
    });
});
$(document).on("pageshow", "#cardToDiscard", function(){
    var index;
    $("#discardCardsList").off("tap").on('tap', function(){
        var tagName = event.target.tagName;
        if(tagName === "IMG"){
            var closest = $(event.target).parent();
            closest.parent().children('div').removeAttr("style");
            closest.parent().children('div').css("display", "inline");
            closest.css("border-bottom", "2px solid red");
            index = closest.parent().children('div').index(closest);
        }
    });
    $('#discard').off('tap').on('tap', function(){
        if(index != null){
            MILE.send("discardPlayingCard", index);
        }
    });
});
$(document).on("pageshow", "#willUseBang", function(){
    $('#bangYes').off("tap").on('tap', function(){
        MILE.send("bangRespond", true);
        $.mobile.changePage('#waiting');
    });
    $('#bangNo').off("tap").on('tap', function(){
        MILE.send("bangRespond", false);
        $.mobile.changePage('#waiting');
    });
});
$(document).on("pageshow", "#willUseMiss", function(){
    $('#missYes').off("tap").on('tap', function(){
        MILE.send("missRespond", true);
        $.mobile.changePage('#waiting');
    });
    $('#missNo').off("tap").on('tap', function(){
        MILE.send("missRespond", false);
        $.mobile.changePage('#waiting');
    });
});
$(document).on("pageshow", "#willUseBeer", function(){
    $('#beerYes').off("tap").on('tap', function(){
        MILE.send("beerRespond", true);
        $.mobile.changePage('#waiting');
    });
    $('#beerNo').off("tap").on('tap', function(){
        MILE.send("beerRespond", false);
        $.mobile.changePage('#waiting');
    });
});
$(document).on("pageshow", "#selectTarget", function(){
    $('#selectTargetList').off('tap').on('tap', function(){
        var tagName = event.target.tagName;
        var id = event.target.id;
        if(tagName ==="P"){
            var selected = $(event.target);
            selected.parent().children('p').removeAttr("style");
            selected.css("border-right", "2px solid red");
            var index = selected.parent().children('p').index(selected);
            // alert(index);
            MILE.send("selectTargetRespond", index);
            $.mobile.changePage('#waiting');
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
            var selected = $(event.target);
            var index = selected.parent().children('img').index(selected);
            MILE.send("selectTargetCardRespond", index);
        }
        $.mobile.changePage('#waiting');
    });
    $('#targetInHandCardsList').off('tap').on('tap', function(){
        var tagName = event.target.tagName;
        if(tagName === "IMG"){
            MILE.send("selectTargetCardRespond", -1);
            $.mobile.changePage('#waiting');
        }
    });
    $('#cancel').off('tap').on('tap', function(){
        MILE.send("selectTargetCardRespond", -3);
    });
});
