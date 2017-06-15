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
        console.log("Battery charging? "
            + (battery.charging ? "Yes" : "No"));
    }

    battery.addEventListener('levelchange', function(){
        updateLevelInfo();
    });
    function updateLevelInfo(){
        console.log("Battery level: " + battery.level * 100 + "%");
        // document.write("Battery level: " + battery.level * 100 + "%");
    }

    battery.addEventListener('chargingtimechange', function(){
        updateChargingInfo();
    });
    function updateChargingInfo(){
        console.log("Battery charging time: "
                + battery.chargingTime + " seconds");
    }

    battery.addEventListener('dischargingtimechange', function(){
        updateDischargingInfo();
    });
    function updateDischargingInfo(){
        console.log("Battery discharging time: "
                + battery.dischargingTime + " seconds");
    }

});
var i = 0;
window.addEventListener('devicemotion', function(event){
    var x = event.acceleration.x;
    if(x > 6){
        capture();
        //alert("Screen captured!");
        i ++;
    }
});

function capture() {
    html2canvas(document.body, {
        onrendered: function(canvas) {
            Canvas2Image.saveAsPNG(canvas);
        }
    });
}

