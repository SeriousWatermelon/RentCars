<!DOCTYPE html>
<html>
<head>
    <title>Olympus Car Scheme</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link href="/css/styles.css" rel="stylesheet" />
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body style="background-color: #f9f9f9;">
<div id="wrapper" style="padding:0 180px;">
    <div style="text-align: center;">
        <h3>Olympus Car Scheme</h3>
    </div>

<#include "../common/customerNav.ftl" />

    <div class="container">
        <div class="col-md-10 col-md-offset-1">
            <form action="/customer/home/saveUser" method="post">
            <#--input user name-->
                <div class="form-group has-feedback">
                    <div class="input-group">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                        <input id="username" name="username" class="form-control" placeholder="please enter user name" maxlength="20" type="text">
                    </div>
                    <span style="color:red;display: none;" class="tips"></span>
                    <span style="display: none;" class=" glyphicon glyphicon-remove form-control-feedback"></span>
                    <span style="display: none;" class="glyphicon glyphicon-ok form-control-feedback"></span>
                </div>

                <div class="form-group has-feedback">
                    <div class="input-group">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-credit-card"></span></span>
                        <input id="idnum" name="idCard"   class="form-control" placeholder="Please enter your ID number" maxlength="18" type="text">
                    </div>
                    <span style="color:red;display: none;" class="tips"></span>
                    <span style="display: none;" class=" glyphicon glyphicon-remove form-control-feedback"></span>
                    <span style="display: none;" class="glyphicon glyphicon-ok form-control-feedback"></span>
                </div>

                <div class="form-group has-feedback">
                    <div class="input-group">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
                        <input id="password" name="password"  class="form-control" placeholder="Please enter your password" maxlength="20" type="password">
                    </div>
                    <span style="color:red;display: none;" class="tips"></span>
                    <span style="display: none;" class="glyphicon glyphicon-remove form-control-feedback"></span>
                    <span style="display: none;" class="glyphicon glyphicon-ok form-control-feedback"></span>
                </div>

                <div class="form-group has-feedback">
                    <div class="input-group">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
                        <input id="passwordConfirm" class="form-control" placeholder="Please confirm your password" maxlength="20" type="password">
                    </div>
                    <span style="color:red;display: none;" class="tips"></span>
                    <span style="display: none;" class="glyphicon glyphicon-remove form-control-feedback"></span>
                    <span style="display: none;" class="glyphicon glyphicon-ok form-control-feedback"></span>
                </div>

                <div class="row">
                    <div class="col-xs-8">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-qrcode"></span></span>
                                <input id="idcode-btn" class="form-control" placeholder="Verification code on the right" maxlength="4" type="text">
                            </div>
                            <span style="color:red;display: none;" class="tips"></span>
                            <span style="display: none;" class="glyphicon glyphicon-remove form-control-feedback"></span>
                            <span style="display: none;" class="glyphicon glyphicon-ok form-control-feedback"></span>
                        </div>
                    </div>
                    <div class="col-xs-4">
                        <div id="idcode" style="background: transparent;color:#0000ff;"></div>
                    </div>
                </div>

                <div class="form-group has-feedback">
                    <div class="input-group">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-phone"></span></span>
                        <input id="phoneNum" name="phone" class="form-control" placeholder="cellphone number" maxlength="11" type="text">
                    </div>
                    <span style="color:red;display: none;" class="tips"></span>
                    <span style="display: none;" class="glyphicon glyphicon-remove form-control-feedback"></span>
                    <span style="display: none;" class="glyphicon glyphicon-ok form-control-feedback"></span>
                </div>

                <div class="form-group has-feedback">
                    <div class="input-group">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-home"></span></span>
                        <input id="address" name="address" class="form-control" placeholder="Your current residential address" type="text">
                    </div>
                    <span style="color:red;display: none;" class="tips"></span>
                    <span style="display: none;" class="glyphicon glyphicon-remove form-control-feedback"></span>
                    <span style="display: none;" class="glyphicon glyphicon-ok form-control-feedback"></span>
                </div>
                <input name="id" value="${(userInfo.id)!''}" class="form-control" type="hidden">
                <div class="form-group">
                    <input class="form-control btn btn-primary" id="submit" value="Update now" type="submit">
                </div>
                <div class="form-group">
                    <input value="Reset" id="reset" class="form-control btn btn-danger" type="reset">
                </div>
            </form>
        </div>
    </div>

</div>

</body>
<script>

    $(function(){
        $("#nav_customer_info").addClass("active");
    })

    var settings = {
        e: 'idcode',
        codeType: {
            name: 'follow',
            len: 4
        }, //len是修改验证码长度
        codeTip: 'Change',
        inputID: 'idcode-btn' //验证元素的ID
    };

    var _set = {
        storeLable: 'codeval',
        store: '#ehong-code-input',
        codeval: '#ehong-code'
    }
    $.idcode = {
        getCode: function(option) {
            _commSetting(option);
            return _storeData(_set.storeLable, null);
        },
        setCode: function(option) {
            _commSetting(option);
            _setCodeStyle("#" + settings.e, settings.codeType.name, settings.codeType.len);

        },
        validateCode: function(option) {
            _commSetting(option);
            var inputV;
            if (settings.inputID) {
                inputV = $('#' + settings.inputID).val();
            } else {
                inputV = $(_set.store).val();
            }
            if (inputV.toUpperCase() == _storeData(_set.storeLable, null).toUpperCase()) { //修改的不区分大小写
                return true;
            } else {
                _setCodeStyle("#" + settings.e, settings.codeType.name, settings.codeType.len);
                return false;
            }
        }
    };

    function _commSetting(option) {
        $.extend(settings, option);
    }

    function _storeData(dataLabel, data) {
        var store = $(_set.codeval).get(0);
        if (data) {
            $.data(store, dataLabel, data);
        } else {
            return $.data(store, dataLabel);
        }
    }

    function _setCodeStyle(eid, codeType, codeLength) {
        var codeObj = _createCode(settings.codeType.name, settings.codeType.len);
        var randNum = Math.floor(Math.random() * 6);
        var htmlCode = ''
        if (!settings.inputID) {
            htmlCode = '<span><input id="ehong-code-input" type="text" maxlength="4" /></span>';
        }
        htmlCode += '<div id="ehong-code" class="ehong-idcode-val ehong-idcode-val';
        htmlCode += String(randNum);
        htmlCode += '" href="#" onblur="return false" onfocus="return false" oncontextmenu="return false" onclick="$.idcode.setCode()">' + _setStyle(codeObj) + '</div>' + '<span id="ehong-code-tip-ck" class="ehong-code-val-tip" onclick="$.idcode.setCode()">' + settings.codeTip + '</span>';
        $(eid).html(htmlCode);
        _storeData(_set.storeLable, codeObj);
    }

    function _setStyle(codeObj) {
        var fnCodeObj = new Array();
        var col = new Array('#BF0C43', '#E69A2A', '#707F02', '#18975F', '#BC3087', '#73C841', '#780320', '#90719B', '#1F72D8', '#D6A03C', '#6B486E', '#243F5F', '#16BDB5');
        var charIndex;
        for (var i = 0; i < codeObj.length; i++) {
            charIndex = Math.floor(Math.random() * col.length);
            fnCodeObj.push('<font color="' + col[charIndex] + '">' + codeObj.charAt(i) + '</font>');
        }
        return fnCodeObj.join('');
    }

    function _createCode(codeType, codeLength) {
        var codeObj;
        if (codeType == 'follow') {
            codeObj = _createCodeFollow(codeLength);
        } else if (codeType == 'calc') {
            codeObj = _createCodeCalc(codeLength);
        } else {
            codeObj = "";
        }
        return codeObj;
    }

    function _createCodeCalc(codeLength) {
        var code1, code2, codeResult;
        var selectChar = new Array('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
        var charIndex;
        for (var i = 0; i < codeLength; i++) {
            charIndex = Math.floor(Math.random() * selectChar.length);
            code1 += selectChar[charIndex];
            charIndex = Math.floor(Math.random() * selectChar.length);
            code2 += selectChar[charIndex];
        }
        return [parseInt(code1), parseInt(code2), parseInt(code1) + parseInt(code2)];
    }

    function _createCodeFollow(codeLength) {
        var code = "";
        var selectChar = new Array('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');
        for (var i = 0; i < codeLength; i++) {
            var charIndex = Math.floor(Math.random() * selectChar.length);
            if (charIndex % 2 == 0) {
                code += selectChar[charIndex].toLowerCase();
            } else {
                code += selectChar[charIndex];
            }
        }
        return code;
    }
    var regUsername = /^[a-zA-Z_][a-zA-Z0-9_]{4,19}$/;
    var regIdNum = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
    var regPasswordSpecial = /[~!@#%&=;':",./<>_\}\]\-\$\(\)\*\+\.\[\?\\\^\{\|]/;
    var regPasswordAlpha = /[a-zA-Z]/;
    var regPasswordNum = /[0-9]/;
    var password;
    var check = [false, false, false, false, false, false, false];

    //校验成功函数
    function success(Obj, counter) {
        Obj.parent().parent().removeClass('has-error').addClass('has-success');
        $('.tips').eq(counter).hide();
        $('.glyphicon-ok').eq(counter).show();
        $('.glyphicon-remove').eq(counter).hide();
        check[counter] = true;
    }

    // 校验失败函数
    function fail(Obj, counter, msg) {
        Obj.parent().parent().removeClass('has-success').addClass('has-error');
        $('.glyphicon-remove').eq(counter).show();
        $('.glyphicon-ok').eq(counter).hide();
        $('.tips').eq(counter).text(msg).show();
        check[counter] = false;
    }

    // 用户名匹配
    $('.container').find('input').eq(0).change(function() {
        if (regUsername.test($(this).val())) {
            success($(this), 0);
        } else if ($(this).val().length < 5) {
            fail($(this), 0, 'Username is too short, no less than 5 characters');
        } else {
            fail($(this), 0, 'Usernames can only be alphanumeric and underscore, and cannot begin with a digit')
        }
    });

    // ID号码校验
    $('.container').find('input').eq(1).change(function() {
        if (regIdNum.test($(this).val())) {
            success($(this), 1);
        } else {
            fail($(this), 1, 'ID number is incorrect');
        }
    });


    // 密码匹配
    // 匹配字母、数字、特殊字符至少两种的函数
    function atLeastTwo(password) {
        var a = regPasswordSpecial.test(password) ? 1 : 0;
        var b = regPasswordAlpha.test(password) ? 1 : 0;
        var c = regPasswordNum.test(password) ? 1 : 0;
        return a + b + c;
    }

    $('.container').find('input').eq(2).change(function() {
        password = $(this).val();
        if ($(this).val().length < 8) {
            fail($(this), 2, 'The password is too short and cannot be less than 8 characters');
        } else {
            if (atLeastTwo($(this).val()) < 2) {
                fail($(this), 2, 'Password must contain letters, numbers, and special characters')
            } else {
                success($(this), 2);
            }
        }
    });

    // 再次输入密码校验
    $('.container').find('input').eq(3).change(function() {
        if ($(this).val() == password) {
            success($(this), 3);
        } else {
            fail($(this), 3, 'Inconsistent password');
        }
    });

    // 验证码
    $.idcode.setCode();
    $('.container').find('input').eq(4).change(function() {
        var IsBy = $.idcode.validateCode();
        if (IsBy) {
            success($(this), 4);
        } else {
            fail($(this), 4, 'incorrect verification code');
        }
    });

    // 手机号码
    var regPhoneNum = /^[0-9]{11}$/
    $('.container').find('input').eq(5).change(function() {
        if (regPhoneNum.test($(this).val())) {
            success($(this), 5);
        } else {
            fail($(this), 5, 'Mobile number can only be 11 digits');
        }
    });

    // 用户住址
    $('.container').find('input').eq(6).change(function() {
        var address = $.trim($(this).val());
        if("" == address){
            fail($(this), 6, 'Address cannot be empty');
        }else{
            success($(this), 6);
        }
    });

    $('#submit').click(function(e) {
        if (!check.every(function(value) {
                    return value == true
                })) {
            e.preventDefault();
            for (key in check) {
                if (!check[key]) {
                    $('.container').find('input').eq(key).parent().parent().removeClass('has-success').addClass('has-error')
                }
            }
        }
    });

    $('#reset').click(function() {
        $('input').slice(0, 6).parent().parent().removeClass('has-error has-success');
        $('.tips').hide();
        $('.glyphicon-ok').hide();
        $('.glyphicon-remove').hide();
        check = [false, false, false, false, false, false, ];
    });
</script>



</html>
