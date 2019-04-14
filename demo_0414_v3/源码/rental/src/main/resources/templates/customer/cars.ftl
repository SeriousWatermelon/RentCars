<!DOCTYPE html>
<html>
<#include "../common/header.ftl" />
<body style="background-color: #f9f9f9;">
<div id="wrapper" style="padding: 0 180px;">
    <div style="text-align: center;">
        <h3>Olympus Car Scheme</h3>
    </div>
    <#include "../common/customerNav.ftl" />

    <#--搜索框-->
    <div style="width:580px;margin:-20px auto -50px auto;">
        <form class="form-inline" method="post" action="/customer/home/searchCars">
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
                        </tr>
                        </#list>
                        </tbody>
                    </table>

                    <ul class="pagination pull-right">

                    <#--上一页-->
                    <#if pageInfo.isFirstPage == true>
                        <li class="disabled"><a href="#">previous</a></li>
                    <#else>
                        <li><a href="/customer/home/cars?page=${currentPage-1}&size=${size}">previous</a></li>
                    </#if>
                    <#--页码-->
                    <#list 1..pageInfo.getPages() as index>
                        <#if currentPage == index>
                            <li class="disabled"><a href="#">${index}</a></li>
                        <#else>
                            <li><a href="/customer/home/cars?page=${index}&size=${size}">${index}</a></li>
                        </#if>
                    </#list>
                    <#--下一页-->
                    <#if pageInfo.isLastPage == true>
                        <li class="disabled"><a href="#">next</a></li>
                    <#else>
                        <li><a href="/customer/home/cars?page=${currentPage+1}&size=${size}">next</a></li>
                    </#if>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

</body>

</html>
