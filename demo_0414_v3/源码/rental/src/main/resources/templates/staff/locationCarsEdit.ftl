<html>
<#include "../common/header.ftl" />
<body>
<div id="wrapper" class="toggled">
<#include "../common/staffNav.ftl" />

    <div style="width:800px;text-align:center;height:40px;line-height:40px;margin: 10px auto -50px auto;font-size:28px;font-weight:bold;">
    Add Cars to ${locationName}
    </div>


<#-- 展示 未加入任何网点的车辆信息列表 页面 -->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered table-hover table-condensed" style="text-align: center;">
                        <thead>
                        <tr style="font-weight: bold;">
                            <td>NO</td>
                            <td>Picture</td>
                            <td>Maker</td>
                            <td>Model</td>
                            <td>Type</td>
                            <td>Rent</td>
                            <td colspan="1">operations</td>
                        </tr>
                        </thead>
                        <tbody>
                        <#list carList as car>
                        <tr>
                            <td>${car.cid}</td>
                            <td><img src="${car.cicon ! '/imgs/no.jpg'}" width="50px" height="50px" /> </td>
                            <td>${car.maker}</td>
                            <td>${car.model}</td>
                            <td>${car.type}</td>
                            <td>${car.rent}/h</td>
                            <td>
                                <a href="javascript:saveLocationCars(${locationId},${car.cid});">Add Car to ${locationName}</a>
                            </td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>


</div>

</body>

<script>
    function saveLocationCars(locationId,cid){
        var flag = confirm("You will add the vehicle to the location, continue? ");
        if(flag) {
            window.location.href="/staff/location/saveLocationCars?locationId="+locationId+"&cid="+cid;
        }
    }
</script>

</html>