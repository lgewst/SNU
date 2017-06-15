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

window.addEventListener('devicemotion', function(event){
    var x = event.acceleration.x;
    var y = event.acceleration.y;
    var z = event.acceleration.z;
    var options = {
        canvas: '#canvas'
    }
    if(x > 6){
        alert("shake");
        capture();
    }
});

function capture() {
  html2canvas(document.body, {
    onrendered: function(canvas) {
      document.body.appendChild(canvas);
    }
  });
}
