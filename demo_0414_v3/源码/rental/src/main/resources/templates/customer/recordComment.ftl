<!DOCTYPE html>
<html>
<#include "../common/header.ftl" />
<body style="background-color: #f9f9f9;">
<div id="wrapper" style="padding:0 180px;">
    <div style="text-align: center;">
        <h3>Olympus Car Scheme</h3>
    </div>
    <#include "../common/customerNav.ftl" />

    <#-- 订单评分 -->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form action="/customer/home/saveComment" method="post">
                        <input type="hidden" name="orderId" value="${order.orderId}" />
                        <table class="table table-bordered table-hover table-condensed" style="text-align: center;">
                            <thead>
                            <tr style="font-weight: bold;">
                                <td>Order ID</td>
                                <td>Customer's Name</td>
                                <td>Car's Maker</td>
                                <td>PickUp Location</td>
                                <td>PickUp Time</td>
                                <td>DropOff Location</td>
                                <td>DropOff Time</td>
                                <td>Service Rating(100 points)</td>
                                <td>Service Feedback</td>
                            </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>${order.orderId}</td>
                                    <td>${order.userName}</td>
                                    <td>${order.carMaker}</td>
                                    <td>${order.pickupLocation}</td>
                                    <td>${order.pickupTime?date}</td>
                                    <td>${order.dropoffLocation}</td>
                                    <td>${order.dropoffTime?date}</td>
                                    <td>
                                        <input name="commentScore" type="number" class="form-control" value="${(comment.commentScore)!'80'}" />
                                    </td>
                                    <td>
                                        <input name="commentDesc" type="text" class="form-control" value="${(comment.commentDesc)!'Default comment: Satisfied'}" />
                                    </td>
                            </tbody>
                        </table>
                        <button class="btn btn-success pull-right" type="submit">Comment Submit</button>
                    </form>
                </div>
            </div>
        </div>
    </div>




</div>
</body>
<script>


</script>
</html>
