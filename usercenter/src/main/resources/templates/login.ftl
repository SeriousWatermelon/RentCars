<!DOCTYPE html>
<html>
<#include "common/header.ftl" />
<body class="login-body">

<div class="login-title" ><h3>Olympus Car Scheme</h3></div>
<div class="col-center">
    <form id="login_submit" method="post" action="/loginUser">
        <div class="form-group">
            <label for="inputUsername">Username</label>
            <input type="text" class="form-control" id="inputUsername" name="username" onblur="checkUsername()" placeholder="">
        </div>
        <div id="username_tips" class="login-tips">please enter your username</div>
        <div class="form-group">
            <label for="inputPassword">Password</label>
            <input type="password" class="form-control" id="inputPassword" name="password" onblur="checkPassword()" placeholder="">
        </div>
        <div id="password_tips" class="login-tips">please enter your password</div><br/>
    </form>
    <button id="submitBtn" class="btn btn-success btn-block" onclick="toSubmit()" >Sign In</button>

    <div style="height:60px;line-height: 60px;text-align: center;">
        New to Rental? <a href="/register">click here to sign up!</a>
    </div>
</div>

</body>

<script>
    function checkUsername(){
        var username = $.trim($("#inputUsername").val());
        if(username != ""){
            $("#username_tips").html("");
        }else{
            $("#username_tips").html("please enter your username");
        }
    }
    function checkPassword(){
        var password = $.trim($("#inputPassword").val());
        if(password != ""){
            $("#password_tips").html("");
        }else{
            $("#password_tips").html("please enter your password");
        }
    }
    function toSubmit(){
        var username = $.trim($("#inputUsername").val());
        var password = $.trim($("#inputPassword").val());
        if(username == ""){
            $("#username_tips").html("please enter your username");
        }else if(password == ""){
            $("#password_tips").html("please enter your password");
        }else{
            $("#login_submit").submit();
        }
    }

</script>


</html>
