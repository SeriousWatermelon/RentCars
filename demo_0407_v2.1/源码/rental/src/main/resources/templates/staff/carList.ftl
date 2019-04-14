<html>
<#include "../common/header.ftl" />
<body>
<div id="wrapper" class="toggled">
    <#include "../common/staffNav.ftl" />

    <div style="width:300px;height:40px;line-height:40px;margin: 10px auto -50px auto;font-size:28px;font-weight:bold;">
        Vehicles Information
    </div>

     <#--搜索框-->
    <div style="width:580px;margin:80px auto -50px auto;">
        <form class="form-inline" method="post" action="/staff/car/search">
            <div class="form-group">
                <label for="maker">Maker</label>
                <input name="maker" type="text" class="form-control" id="maker" placeholder="Input maker">
            </div>
            <div class="form-group">
                <label for="model">Model</label>
                <input name="model" type="text" class="form-control" id="model" placeholder="Input model">
            </div>
            <button type="submit" class="btn btn-info">search</button>
        </form>
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
                            <td>Picture</td>
                            <td>Maker</td>
                            <td>Model</td>
                            <td>Type</td>
                            <td>Product Time</td>
                            <td>Rent</td>
                            <td>Create Time</td>
                            <td>Update Time</td>
                            <td>Disable</td>
                            <td colspan="2">operations</td>
                        </tr>
                        </thead>
                        <tbody>
                        <#list pageInfo.getList() as car>
                        <tr>
                            <td>${car.cid}</td>
                            <td><img src="${car.cicon ! '/imgs/no.jpg'}" width="50px" height="50px" /> </td>
                            <td>${car.maker}</td>
                            <td>${car.model}</td>
                            <td>${car.type}</td>
                            <td>${car.productTime?string("yyyy-MM-dd")}</td>
                            <td>${car.rent}/h</td>
                            <td>${car.createTime?string("yyyy-MM-dd")}</td>
                            <td>${(car.updateTime?string("yyyy-MM-dd"))!}</td>
                            <td>
                                <#if car.disable == true>
                                    YES
                                <#else>NO
                                </#if>
                            </td>
                            <td>
                                <#if car.disable == true>
                                    <a href="/staff/car/disabled?cid=${car.cid}">Not Disabled</a>
                                <#else><a href="/staff/car/disabled?cid=${car.cid}">Disabled</a>
                                </#if>

                            </td>
                            <td>
                                <a href="/staff/car/edit?cid=${car.cid}">Edit</a>
                            </td>
                        </tr>
                        </#list>
                        </tbody>
                    </table>

                    <ul class="pagination pull-right">

                    <#--上一页-->
                    <#if pageInfo.isFirstPage == true>
                        <li class="disabled"><a href="#">上一页</a></li>
                    <#else>
                        <li><a href="/staff/car/list?page=${currentPage-1}&size=${size}">上一页</a></li>
                    </#if>

                    <#--页码-->
                    <#list 1..pageInfo.getPages() as index>
                        <#if currentPage == index>
                            <li class="disabled"><a href="#">${index}</a></li>
                        <#else>
                            <li><a href="/staff/car/list?page=${index}&size=${size}">${index}</a></li>
                        </#if>
                    </#list>
                    <#--下一页-->
                    <#if pageInfo.isLastPage == true>
                        <li class="disabled"><a href="#">下一页</a></li>
                    <#else>
                        <li><a href="/staff/car/list?page=${currentPage+1}&size=${size}">下一页</a></li>
                    </#if>
                    </ul>
                    <button type="button" class="btn btn-success" onclick="addUser()">Add Car</button>
                </div>
            </div>
        </div>
    </div>


</div>

</body>

<script>
    function addUser(){
        window.location.href="/staff/car/edit";
    }
</script>

</html>