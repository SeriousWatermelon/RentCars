<html>
<#include "../common/header.ftl" />
<body>
<div id="wrapper" class="toggled">
    <#include "../common/staffNav.ftl" />

    <div style="width:300px;height:40px;line-height:40px;margin: 10px auto -50px auto;font-size:28px;font-weight:bold;">
        Customer Information
    </div>

     <#--搜索框-->
    <div style="width:800px;margin:80px auto -50px auto;">
        <form class="form-inline" method="post" action="/staff/user/search">
            <div class="form-group">
                <label for="username">UserName</label>
                <input name="username" type="text" class="form-control" id="username" placeholder="Input UserName">
            </div>
            <div class="form-group">
                <label for="phone">Phone</label>
                <input name="phone" type="text" class="form-control" id="phone" placeholder="Input Phone">
            </div>
            <button type="submit" class="btn btn-info">search</button>
        </form>
    </div>

    <#-- 展示 人员信息列表 页面 -->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered table-hover table-condensed" style="text-align: center;">
                        <thead>
                        <tr style="font-weight: bold;">
                            <td>NO</td>
                            <td>username</td>
                            <td>phone</td>
                            <td>address</td>
                            <td>credit</td>
                            <td>blacklisted</td>
                            <td>CreateTime</td>
                            <td>LastLoginTime</td>
                            <td colspan="3">operations</td>
                        </tr>
                        </thead>
                        <tbody>
                        <#list pageInfo.getList() as user>
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.username}</td>
                            <td>${user.phone}</td>
                            <td>${user.address}</td>
                            <td>${user.credit}</td>
                            <td>
                                <#if user.isBlacklist == true>
                                    YES
                                    <#else>NO
                                </#if>
                            </td>
                            <td>${(user.createTime?string("yyyy-MM-dd"))!}</td>
                            <td>${(user.lastLoginTime?string("yyyy-MM-dd"))!}</td>
                            <td><a href="/staff/user/resetCredit?id=${user.id}">ResetCreditScore</a></td>
                            <td>
                                <#if user.isBlacklist == true>
                                    <a href="/staff/user/blacklist?id=${user.id}">NotBlacklisted</a>
                                <#else><a href="/staff/user/blacklist?id=${user.id}">Blacklisted</a>
                                </#if>
                            </td>
                            <td>
                                <a href="/staff/user/edit?id=${user.id}">edit</a>
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
                        <li><a href="/staff/user/list?page=${currentPage-1}&size=${size}">上一页</a></li>
                    </#if>

                    <#--页码-->
                    <#list 1..pageInfo.getPages() as index>
                        <#if currentPage == index>
                            <li class="disabled"><a href="#">${index}</a></li>
                        <#else>
                            <li><a href="/staff/user/list?page=${index}&size=${size}">${index}</a></li>
                        </#if>
                    </#list>
                    <#--下一页-->
                    <#if pageInfo.isLastPage == true>
                        <li class="disabled"><a href="#">下一页</a></li>
                    <#else>
                        <li><a href="/staff/user/list?page=${currentPage+1}&size=${size}">下一页</a></li>
                    </#if>
                    </ul>
                    <button type="button" class="btn btn-success" onclick="addUser()">Add User</button>
                </div>
            </div>
        </div>
    </div>


</div>

</body>

<script>
    function addUser(){
        window.location.href="/staff/user/edit";
    }
</script>

</html>