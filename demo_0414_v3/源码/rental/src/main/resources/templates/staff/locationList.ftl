<html>
<#include "../common/header.ftl" />
<body>
<div id="wrapper" class="toggled">
    <#include "../common/staffNav.ftl" />

    <div style="width:400px;text-align:center;height:40px;line-height:40px;margin: 10px auto -50px auto;font-size:28px;font-weight:bold;">
        Vehicles Location Information
    </div>

     <#--搜索框-->
    <div style="width:580px;margin:80px auto -50px auto;">
        <form class="form-inline" method="post" action="/staff/location/search">
            <div class="form-group">
                <label for="locationName">Location Name</label>
                <input name="locationName" type="text" class="form-control" id="locationName" placeholder="Input LocationName">
            </div>
            <button type="submit" class="btn btn-info">search</button>
        </form>
    </div>


    <#-- 展示 车辆网点列表 页面 -->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered table-hover table-condensed" style="text-align: center;">
                        <thead>
                        <tr style="font-weight: bold;">
                            <td>NO</td>
                            <td>Location Name</td>
                            <td>latlng</td>
                            <td colspan="3">operations</td>
                        </tr>
                        </thead>
                        <tbody>
                        <#list pageInfo.getList() as location>
                        <tr>
                            <td>${location.locationId}</td>
                            <td>${location.locationName}</td>
                            <td>${location.latlng}</td>
                            <td>
                                <a href="/staff/location/edit?locationId=${location.locationId}">Edit</a>
                            </td>
                            <td>
                                <a href="javascript:deleteAlert(${location.locationId})">Delete</a>
                            </td>
                            <td>
                                <a href="/staff/location/showCars?locationId=${location.locationId}">ShowCars</a>
                            </td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>
                    <ul class="pagination pull-right">

                    <#--上一页-->
                    <#if pageInfo.isFirstPage == true>
                        <li class="disabled"><a href="#">previous</a></li>
                    <#else>
                        <li><a href="/staff/location/list?page=${currentPage-1}&size=${size}">previous</a></li>
                    </#if>
                    <#--页码-->
                    <#list 1..pageInfo.getPages() as index>
                        <#if currentPage == index>
                            <li class="disabled"><a href="#">${index}</a></li>
                        <#else>
                            <li><a href="/staff/location/list?page=${index}&size=${size}">${index}</a></li>
                        </#if>
                    </#list>
                    <#--下一页-->
                    <#if pageInfo.isLastPage == true>
                        <li class="disabled"><a href="#">next</a></li>
                    <#else>
                        <li><a href="/staff/location/list?page=${currentPage+1}&size=${size}">next</a></li>
                    </#if>

                    </ul>
                    <button type="button" class="btn btn-success" onclick="addLocation()">Add Location</button>
                </div>
            </div>
        </div>
    </div>


</div>

</body>

<script>
    function addLocation(){
        window.location.href="/staff/location/edit";
    }
    function deleteAlert(locationId){
        var flag = confirm("Cannot be restored after deletion, continue?");
        if(flag) {
            window.location.href = "/staff/location/delete?locationId=" + locationId;
        }
    }

</script>

</html>