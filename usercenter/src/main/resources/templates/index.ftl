<!DOCTYPE html>
<html>
    <#include "common/header.ftl" />
<body style="background-color: #f9f9f9;">
<div id="wrapper" style="padding:0 180px;">
    <div style="text-align: center;">
        <h3>Olympus Car Scheme</h3>
    </div>
    <#include "common/customerNav.ftl" />

    <div class="index-home-rent">
        <div>
        <form id="login_submit" method="post" action="/customer/home/rentCar">
            <div class="form-group">
                <label for="pickUpLocation">Pick Up Location</label>
                <input id="pickUpLocation"  name="pickupLocation" type="text" class="form-control" readonly="readonly"
                       onfocus="showMap('pickup');" placeholder="Input Your PickUpLocation">
            </div>

            <div class="form-group">
                <label for="dropOffLocation">Drop Off Location</label>
                <input id="dropOffLocation" name="dropoffLocation"type="text"  class="form-control" readonly="readonly"
                       onfocus="showMap('dropoff');" placeholder="Input Your DropOffLocation">
            </div>

            <div class="form-group">
                <label for="pickUpTime">Pick up Time</label>
                <input type="datetime-local" class="form-control" id="pickUpTime" name="puTime">
            </div>

            <div class="form-group">
                <label for="dropOffTime">Pick up Time</label>
                <input type="datetime-local" class="form-control" id="dropOffTime" name="doTime">
            </div>

            <div id="showCars" class="form-group"></div>
        </form>
        <br/><br/><br/>
        <button id="submitBtn" class="btn btn-success btn-block" onclick="toSubmit()" >Create this Order</button>
    </div>


</div>

</body>
<script>
    $(function(){
       $("#nav_home").addClass("active");

        var pickupL =  getSession("pickupL");
        var dropoffL =  getSession("dropoffL");

       if("pickup" == "${type !''}"){
            pickupL = "${location!''}";
            setSession("pickupL",pickupL);
        }else if("dropoff" == "${type!''}"){
           dropoffL = "${location!''}";
           setSession("dropoffL",dropoffL);
        }else{
            console.log("just nothing");
        }
        $("#pickUpLocation").val(pickupL);
        $("#dropOffLocation").val(dropoffL);
        showLocationCar(pickupL);
    });


    function showMap(type){
        window.location.href="/customer/home/map?type="+type;
    }

    function showLocationCar(pickupLocation){
        $.ajax({
            url:'/customer/home/getCarList',
            data:{
                pickupLocation:pickupLocation
            },
            dataType:'JSON',
            type:'GET',
            success:function(res){
                var cars = res.cars;
                var options = "";
                for(var i = 0;i<cars.length;i++){
                    options = options + "<option value='"+cars[i].cid+"'>"+cars[i].maker+" "+cars[i].model +"</option>";
                }
                var content = "<label>Choose Car</label>"
                        + "<select id='carId' name='carId' class='form-control' onchange='chooseCar(this.value)'>"
                        + options
                        + "</select>";
                $("#showCars").html(content);
            }
        })
    }

    // 存储sessionStorage
    function setSession(sname,svalue){
        sessionStorage.setItem(sname,svalue);
    }

    // 获取sessionStorage
    function getSession(sname){
        return sessionStorage.getItem(sname);
    }
    //删除session
    function clearL() {
        sessionStorage.removeItem("pickupL");
        sessionStorage.removeItem("dropoffL");
    }

    function toSubmit(){
        var flag = 0;
        var pickupLocation = $.trim($("#pickUpLocation").val());
        if(pickupLocation == "" || pickupLocation == null){
            alert("Pick Up Location cannot be null");
            flag++;
        }
        var pickupTime = $.trim($("#pickUpTime").val());
        if(pickupTime == "" || pickupTime == null){
            alert("Pick Up Time cannot be null");
            flag++;
        }
        var dropoffLocation = $.trim($("#dropOffLocation").val());
        if(dropoffLocation == "" || dropoffLocation == null){
            alert("Drop Off Location cannot be null");
            flag++;
        }
        var dropoffTime = $.trim($("#dropOffTime").val());
        if(dropoffTime == "" || dropoffTime == null){
            alert("Drop Off Time cannot be null");
            flag++;
        }
        if(flag==0){
            $("#login_submit").submit();
        }else{
            alert("Please fill in the information ");
        }

    }

</script>



</html>
