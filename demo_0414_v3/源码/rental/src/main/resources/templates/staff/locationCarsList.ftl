<html>
<#include "../common/header.ftl" />
<body>
<div id="wrapper" class="toggled">
<#include "../common/staffNav.ftl" />

    <div style="width:800px;text-align:center;height:40px;line-height:40px;margin: 10px auto -50px auto;font-size:28px;font-weight:bold;">
        ${locationName} Cars
    </div>


    <#-- 展示 车辆信息列表 页面 -->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered table-hover table-condensed" style="text-align: center;">
                        <thead>
                        <tr style="font-weight: bold;">
                            <td>NO</td>
                            <td>Belongs</td>
                            <td>Picture</td>
                            <td>Maker</td>
                            <td>Model</td>
                            <td>Type</td>
                            <td>Product Time</td>
                            <td>Rent</td>
                            <td>Create Time</td>
                            <td>Update Time</td>
                            <td colspan="1">operations</td>
                        </tr>
                        </thead>
                        <tbody>
                        <#list pageInfo.getList() as car>
                        <tr>
                            <td>${car.cid}</td>
                            <td>${locationName}</td>
                            <td><img src="${car.cicon ! '/imgs/no.jpg'}" width="50px" height="50px" /> </td>
                            <td>${car.maker}</td>
                            <td>${car.model}</td>
                            <td>${car.type}</td>
                            <td>${car.productTime?string("yyyy-MM-dd")}</td>
                            <td>${car.rent}/h</td>
                            <td>${car.createTime?string("yyyy-MM-dd")}</td>
                            <td>${(car.updateTime?string("yyyy-MM-dd"))!}</td>
                            <td>
                                <a href="javascript:removeAlert(${locationId},${car.cid});">Remove from ${locationName} Car List</a>
                            </td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                    <ul class="pagination pull-right">

                    <#--上一页-->
                    <#if pageInfo.isFirstPage == true || pageInfo.hasNextPage == false>
                        <li class="disabled"><a href="#">previous</a></li>
                    <#else>
                        <li><a href="/staff/location/showCars?page=${currentPage-1}&size=${size}">previous</a></li>
                    </#if>
                    <#--页码-->
                    <#list 1..pageInfo.getPages() as index>
                        <#if currentPage == index>
                            <li class="disabled"><a href="#">${index}</a></li>
                        <#else>
                            <li><a href="/staff/location/showCars?page=${index}&size=${size}">${index}</a></li>
                        </#if>
                    </#list>
                    <#--下一页-->
                    <#if pageInfo.isLastPage == true>
                        <li class="disabled"><a href="#">next</a></li>
                    <#else>
                        <li><a href="/staff/location/showCars?page=${currentPage+1}&size=${size}">next</a></li>
                    </#if>
                    </ul>
                    <button type="button" class="btn btn-success" onclick="addLocationCars(${locationId})">Add Cars To ${locationName}</button>
                </div>
            </div>
        </div>
    </div>


</div>

</body>

<script>
    function addLocationCars(locationId){
        window.location.href="/staff/location/toAddLocationCars?locationId="+locationId;
    }
    function removeAlert(locationId,cid){
        var flag = confirm("Cannot be restored after operation, continue?");
        if(flag) {
            window.location.href = "/staff/location/remove?locationId="+locationId+"&cid="+cid;
        }
    }
</script>

</html>