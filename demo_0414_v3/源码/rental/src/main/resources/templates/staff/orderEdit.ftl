<html>
<#include "../common/header.ftl" />
<body>
<div id="wrapper" class="toggled">
    <#include "../common/staffNav.ftl" />

    <div style="width:300px;text-align:center;height:40px;line-height:40px;margin: 10px auto -50px auto;font-size:28px;font-weight:bold;">
        Order Information
    </div>

    <#-- 新增/修改 订单 页面 -->
        <div id="page-content-wrapper">
            <div class="container-fluid">
                <div class="row clearfix col-md-offset-2">
                    <div class="col-md-8 column">
                        <form id="order_form" role="form" method="post" action="/staff/order/save">
                            <input type="hidden" name="orderId" value="${(orderInfo.orderId)!''}" />
                            <div class="form-group">
                                <label>Customer's Name</label>
                                <input id="userName" name="userName" type="text" class="form-control" value="${(orderInfo.userName)!''}" placeholder="Input Customer's Name" />
                            </div>
                            <div class="form-group">
                                <label>Customer's Phone</label>
                                <input id="userPhone" name="userPhone" type="text" class="form-control" value="${(orderInfo.userPhone)!''}" placeholder="Input Customer's Phone" />
                            </div>
                            <div class="form-group">
                                <label>Pick Up Location</label>
                                <select id="pickupLocation" name="pickupLocation" class="form-control" onchange="pickUpLocation(this.value)">
                                    <option>please choose</option>
                                    <#list locationList as location>
                                        <option value="${location.locationName}" <#if (orderInfo.pickupLocation)?? && orderInfo.pickupLocation == location.locationName >selected</#if> >
                                        ${location.locationName}
                                        </option>
                                    </#list>
                                </select>
                            </div>

                            <div class="form-group">
                                <label>Pick Up Time</label>
                                <input id="pickupTime" name="puTime" type="datetime-local" class="form-control" value="${(orderInfo.pickupTime?date) ! '' }" />
                            </div>

                            <div class="form-group">
                                <label>Drop Off Location</label>
                                <select id="dropoffLocation" name="dropoffLocation" class="form-control" onchange="dropOffLocation(this.value)">
                                    <option>please choose</option>
                                    <#list locationList as location>
                                        <option value="${location.locationName}" <#if (orderInfo.dropoffLocation)?? && orderInfo.dropoffLocation == location.locationName >selected</#if> >
                                        ${location.locationName}
                                        </option>
                                    </#list>
                                </select>
                            </div>

                            <div class="form-group">
                                <label>Drop Off Time</label>
                                <input id="dropoffTime" name="doTime" type="datetime-local" class="form-control" value="${(orderInfo.dropoffTime?date) ! '' }" />
                            </div>
                            <div id="showCars" class="form-group"></div>
                        </form>
                        <button onclick="submitOrder()" class="btn btn-success">Submit</button>
                    </div>
                </div>
            </div>
        </div>
</div>

</body>
<script>

    function submitOrder(){
        var flag = 0;
        var userName = $.trim($("#userName").val());
        if(userName=="" || userName == null){
            alert("Customer's Name Cannot Be Null");
            flag++;
        }
        var userPhone = $.trim($("#userPhone").val());
        if(userPhone == "" || userPhone == null){
            alert("Customer's Phone cannot be null");
            flag++;
        }
        var pickupLocation = $.trim($("#pickupLocation").val());
        if(pickupLocation == "" || pickupLocation == null){
            alert("Pick Up Location cannot be null");
            flag++;
        }
        var pickupTime = $.trim($("#pickupTime").val());
        if(pickupTime == "" || pickupTime == null){
            alert("Pick Up Time cannot be null");
            flag++;
        }
        var dropoffLocation = $.trim($("#dropoffLocation").val());
        if(dropoffLocation == "" || dropoffLocation == null){
            alert("Drop Off Location cannot be null");
            flag++;
        }
        var dropoffTime = $.trim($("#dropoffTime").val());
        if(dropoffTime == "" || dropoffTime == null){
            alert("Drop Off Time cannot be null");
            flag++;
        }
        var carId = $.trim($("#carId").val());
        if(carId == "" || carId == null){
            alert("Please Choose Car.");
            flag++;
        }
        if(flag==0){
            $("#order_form").submit();
        }else{
            alert("Please fill in the information ");
        }

    }

    function pickUpLocation(pickupLocation){
        $.ajax({
            url:'/staff/order/getCarList',
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
</script>

</html>