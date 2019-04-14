<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <title>maps</title>
    <style type="text/css">
        html { height: 100%; }
        body { height: 100%; margin: 0px; padding: 0px; }
        #map_canvas { width:70%; height: 80%; margin: 100px auto; }
    </style>
    <script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false&key=AIzaSyCxeeU2rsMCYAgZ1__gT3lySlI82hcGha0"></script>
</head>
<body onload="initialize()">
<div id="map_canvas">
</div>
</body>

<script type="text/javascript">
    var map;
    function initialize(){
        var myLatlng = new google.maps.LatLng(-37.81,144.96);
        var myOptions = {
            zoom: 10,
            center: myLatlng,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        }
        map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
        var locations = ${locationList};
        var latlngs = new Array();
        for(var i=0;i<locations.length;i++){
            var latlngStr = locations[i].latlng;
            var latlngStrArr = latlngStr.split(",");
            var latlng_0 = new google.maps.LatLng(parseFloat(latlngStrArr[0]),parseFloat(latlngStrArr[1]));
            latlngs.push(latlng_0);
        }

        for (var i = 0; i < latlngs.length; i++){
            var location = new google.maps.LatLng(latlngs[i].lat(),latlngs[i].lng());
            var marker = new google.maps.Marker({
                position: location,
                map: map
            });
            var j = i + 1;
            marker.setTitle(locations[i].locationName);
            clickListener(marker);
        }
        /*google.maps.event.addListener(marker, 'click', function()
        {
            alert(marker);
        });*/
    }

    function clickListener(marker){
        google.maps.event.addListener(marker, 'click', function() {
            window.location.href="/customer/home/comfirmLocation?location="+marker.title+"&type=${type}";
        });
    }
</script>
</html>