<html>
<#include "../common/header.ftl" />
<body>
<div id="wrapper" class="toggled">
    <#include "../common/staffNav.ftl" />

    <div style="width:300px;text-align:center;height:40px;line-height:40px;margin: 10px auto -50px auto;font-size:28px;font-weight:bold;">
        Orders List
    </div>

     <#--搜索框-->
        <div style="width:1100px;text-align:center;margin:80px auto -50px auto;">
            <form class="form-inline" method="post" action="/staff/order/search">
                <div class="form-group">
                    <label for="orderId">Order ID</label>
                    <input name="orderId" type="text" class="form-control" id="orderId" placeholder="Input Order ID">
                </div>
                <div class="form-group">
                    <label for="userName">Customer's Name</label>
                    <input name="userName" type="text" class="form-control" id="userName" placeholder="Input Customer's Name">
                </div>
                <div class="form-group">
                    <label for="userPhone">Customer's PhoneNumber</label>
                    <input name="userPhone" type="text" class="form-control" id="userPhone" placeholder="Input Customer's PhoneNumber">
                </div>
                <button type="submit" class="btn btn-info">search</button>
            </form>
        </div>

    <#-- 展示 订单列表 页面 -->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered table-hover table-condensed" style="text-align: center;">
                        <thead>
                        <tr style="font-weight: bold;">
                            <td>Order ID</td>
                            <td>Customer's Name</td>
                            <td>Customer's Phone</td>
                            <td>Car's Maker</td>
                            <td>PickUp Location</td>
                            <td>PickUp Time</td>
                            <td>DropOff Location</td>
                            <td>DropOff Time</td>
                            <td>Update Time</td>
                            <td>Order Status</td>
                            <td colspan="3">operations</td>
                        </tr>
                        </thead>
                        <tbody>
                        <#list pageInfo.getList() as order>
                        <tr>
                            <td>${order.orderId}</td>
                            <td>${order.userName}</td>
                            <td>${order.userPhone}</td>
                            <td>${order.carMaker}</td>
                            <td>${order.pickupLocation}</td>
                            <td>${order.pickupTime?date}</td>
                            <td>${order.dropoffLocation}</td>
                            <td>${order.dropoffTime?date}</td>
                            <td>${order.updateTime?date}</td>
                            <td style="color: #ff0000;">
                                <#if order.orderStatus == 0>
                                    Picking Up
                                <#elseif order.orderStatus == 1>
                                    Using..
                                <#elseif order.orderStatus == 2>
                                    Finished
                                <#elseif order.orderStatus == 3>
                                    Canceled
                                </#if>
                            </td>
                            <td>
                                <#if order.orderStatus == 0>
                                    <a href="/staff/order/edit?orderId=${order.orderId}">Edit</a>
                                <#else>
                                    <a style="color: #ccc">Edit</a>
                                </#if>
                            </td>
                            <td>
                            <#if order.orderStatus == 0>
                                <a href="/staff/order/cancle?orderId=${order.orderId}">Cancle</a>
                            <#else>
                                <a style="color: #ccc">Cancle</a>
                            </#if>
                        </td>
                            <td>
                                <#if order.orderStatus == 2  || order.orderStatus == 3>
                                    <a style="color: #cccccc;">Change Status</a>
                                <#else>
                                    <a href="/staff/order/status?orderId=${order.orderId}">Change Status</a>
                                </#if>
                                
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
                        <li><a href="/staff/order/list?page=${currentPage-1}&size=${size}">previous</a></li>
                    </#if>
                    <#--页码-->
                    <#list 1..pageInfo.getPages() as index>
                        <#if currentPage == index>
                            <li class="disabled"><a href="#">${index}</a></li>
                        <#else>
                            <li><a href="/staff/order/list?page=${index}&size=${size}">${index}</a></li>
                        </#if>
                    </#list>
                    <#--下一页-->
                    <#if pageInfo.isLastPage == true>
                        <li class="disabled"><a href="#">next</a></li>
                    <#else>
                        <li><a href="/staff/order/list?page=${currentPage+1}&size=${size}">next</a></li>
                    </#if>
                    </ul>
                    <button type="button" class="btn btn-success" onclick="addOrder()">Create A New Order</button>
                </div>
            </div>
        </div>
    </div>


</div>

</body>

<script>
    function addOrder(){
        window.location.href="/staff/order/edit";
    }
</script>

</html>