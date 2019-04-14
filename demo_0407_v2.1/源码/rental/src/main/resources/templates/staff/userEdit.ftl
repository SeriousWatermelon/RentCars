<html>
<#include "../common/header.ftl" />

<body>
<div id="wrapper" class="toggled">
<#include "../common/staffNav.ftl" />

    <div style="width:300px;height:40px;line-height:40px;margin: 10px auto -50px auto;font-size:28px;font-weight:bold;">
        Customer Information
    </div>

    <#--新增/修改 用户-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix col-md-offset-2">
                <div class="col-md-8 column">
                    <form role="form" method="post" action="/staff/user/save">
                        <input type="hidden" name="id" value="${(userInfo.id)!''}" />
                        <div class="form-group">
                            <label>username</label>
                            <input name="username" type="text" class="form-control" value="${(userInfo.username)!''}" />
                        </div>
                        <div class="form-group">
                            <label>IDNumber</label>
                            <input name="idCard" type="text" class="form-control" value="${(userInfo.idCard)!''}" />
                        </div>
                        <div class="form-group">
                            <label>password</label>
                            <input name="password" type="password" class="form-control" value="${(userInfo.password)!''}" />
                        </div>
                        <div class="form-group">
                            <label>phone</label>
                            <input name="phone" type="text" class="form-control" value="${(userInfo.phone)!''}" />
                        </div>
                        <div class="form-group">
                            <label>address</label>
                            <input name="address" type="text" class="form-control" value="${(userInfo.address)!''}" />
                        </div>
                        <div class="form-group">
                            <label>credit</label>
                            <input name="credit" type="text" class="form-control" value="${(userInfo.credit)!''}" />
                        </div>
                        <div class="form-group">
                            <label>isBlacklist</label>
                            <select name="isBlacklist" class="form-control">
                                <option value="${user.isBlacklist?string ("true","false")}" <#if user.isBlacklist?string ("true","false") == "true" >selected</#if> >
                                    YES
                                </option>
                                <option value="${user.isBlacklist?string ("true","false")}" <#if user.isBlacklist?string ("true","false") == "false">selected</#if> >
                                    NO
                                </option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label>Role</label>
                            <select name="roleId" class="form-control">
                            <#list rolesInfo as role>
                                <option value="${role.rid}" <#if (uRole.name)?? && role.name == uRole.name >selected</#if> >
                                        ${role.name}
                                </option>
                            </#list>
                            </select>
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