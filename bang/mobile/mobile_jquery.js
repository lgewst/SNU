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
        alert("To check tags: " + content);
        MILE.send("help", content);
    });
});

$(document).on("pageshow", "#cardToSelect", function(){
    var index = "";
    $("#selectCardsList").off("tap").on('tap', function(){
        var tagName = event.target.tagName;
        if(tagName === "IMG"){
            var closest = $(event.target).parent();
            if($('#selected').hasClass('ui-disabled')){
                $('#selected').removeClass('ui-disabled');
            }
            index = closest.parent().children('div').index(closest);
            var able= $(event.target).parent().attr('able');
            if(!able){
                $('#selected').addClass('ui-disabled');
            }
        }
    });
    $('#select').off('tap').on('tap', function(){
        if(index!=""){
            if(!$('#selected').hasClass('ui-disabled')){
                $('#selected').addClass('ui-disabled');
            }
            MILE.send("selectPlayingCard", index);
        }
    });
    $('#endTurn').off('tap').on('tap', function(){
        MILE.send("selectPlayingCard", -1);
    });
});
$(document).on("pageshow", "#cardToDiscard", function(){
    var index="";
    $("#discardCardsList").off("tap").on('tap', function(){
        var tagName = event.target.tagName;
        if(tagName === "IMG"){
            var closest = $(event.target).parent();
            index = closest.parent().children('div').index(closest);
        }
    });
    $('#discard').off('tap').on('tap', function(){
        if(index!=""){
            MILE.send("discardPlayingCard", index);
        }
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
            var selected = $(event.target);
            var index = selected.parent().children('p').index(selected);
            alert(index);
            MILE.send("selectTargetRespond", index);
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

//TODO: Battery api
navigator.getBattery().then(function(battery) {
  function updateAllBatteryInfo(){
    updateChargeInfo();
    updateLevelInfo();
    updateChargingInfo();
    updateDischargingInfo();
  }
  updateAllBatteryInfo();

  battery.addEventListener('chargingchange', function(){
    updateChargeInfo();
  });
  function updateChargeInfo(){
    console.log("Battery charging? " + (battery.charging ? "Yes" : "No"));
  }

  battery.addEventListener('levelchange', function(){
    updateLevelInfo();
  });
  function updateLevelInfo(){
    console.log("Battery level: " + battery.level * 100 + "%");
    if(!battery.charging && (battery.level == 0.3 || battery.level == 0.20 || battery.level == 0.10 || battery.level == 0.05))
        alert('Low Battery: Please charge your phone');
  }

  battery.addEventListener('chargingtimechange', function(){
    updateChargingInfo();
  });
  function updateChargingInfo(){
    console.log("Battery charging time: " + battery.chargingTime + " seconds");
  }

  battery.addEventListener('dischargingtimechange', function(){
    updateDischargingInfo();
  });
  function updateDischargingInfo(){
    console.log("Battery discharging time: " + battery.dischargingTime + " seconds");
  }

});
