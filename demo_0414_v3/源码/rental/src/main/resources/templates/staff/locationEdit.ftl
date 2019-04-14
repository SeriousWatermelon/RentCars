<html>
<#include "../common/header.ftl" />
<body>
<div id="wrapper" class="toggled">
    <#include "../common/staffNav.ftl" />

    <div style="width:400px;height:40px;line-height:40px;margin: 10px auto -50px auto;font-size:28px;font-weight:bold;">
        Vehicles Location Information
    </div>

    <#-- 新增/修改 停车网点信息-->
        <div id="page-content-wrapper">
            <div class="container-fluid">
                <div class="row clearfix col-md-offset-2">
                    <div class="col-md-8 column">
                        <form role="form" method="post" action="/staff/location/save">
                            <input type="hidden" name="locationId" value="${(locationInfo.locationId)!''}" />
                            <div class="form-group">
                                <label>Location Name</label>
                                <input name="locationName" type="text" class="form-control" value="${(locationInfo.locationName)!''}" placeholder="Input Location Name" />
                            </div>
                            <div class="form-group">
                                <label>Latlng</label>
                                <input name="latlng" type="text" class="form-control" value="${(locationInfo.latlng)!''}" placeholder="Input location's Latlng" />
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