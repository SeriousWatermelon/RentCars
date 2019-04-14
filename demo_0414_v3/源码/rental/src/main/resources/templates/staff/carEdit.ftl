<html>
<#include "../common/header.ftl" />

<body>
<div id="wrapper" class="toggled">
<#include "../common/staffNav.ftl" />

    <div style="width:300px;height:40px;line-height:40px;margin: 10px auto -50px auto;font-size:28px;font-weight:bold;">
        Car's Information
    </div>

    <#--新增/修改 车辆信息-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix col-md-offset-2">
                <div class="col-md-8 column">
                    <form role="form" method="post" action="/staff/car/save" enctype="multipart/form-data">
                        <input type="hidden" name="cid" value="${(carInfo.cid)!''}" />
                        <div class="form-group">
                            <label>Maker</label>
                            <input name="maker" type="text" class="form-control" value="${(carInfo.maker)!''}" placeholder="Input Car Maker" />
                        </div>
                        <div class="form-group">
                            <label>Model</label>
                            <input name="model" type="text" class="form-control" value="${(carInfo.model)!''}" placeholder="Input Car Model" />
                        </div>
                        <div class="form-group">
                            <label>Type</label>
                            <input name="type" type="text" class="form-control" value="${(carInfo.type)!''}" placeholder="Input Car Type" />
                        </div>
                        <div class="form-group">
                            <label>Product Time</label>
                            <input name="product_time" type="date" class="form-control" value="${(carInfo.productTime?string('yyyy-MM-dd')) ! '' }" placeholder="Input Car ProductTime" />
                        </div>
                        <div class="form-group">
                            <label>Rent</label>
                            <input name="Rent" type="text" class="form-control" value="${(carInfo.rent)!''}" placeholder="Input the Rent" />
                        </div>
                        <div class="form-group">
                            <label>Car Picture</label>
                            <div class="form-inline">
                                <input name="iconFile" type="file" class="form-control" />
                                <img src="${(carInfo.cicon)! '/imgs/no.jpg'}" width="100" height="100">
                            </div>
                        </div>
                        <button type="submit" class="btn btn-success">Submit</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>

</body>
</html>