<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Operation Failed</title>
    <link href="/bootstrap-4.3.1-dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body onload="initError()">

<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="alert alert-dismissable alert-danger">
                <h4>
                    Operation Failed!
                </h4> <strong>${msg!"Operation Failed"}</strong>..........<a id="tips" href="${url}" class="alert-link">loading...<label id="time">5</label>s</a>
            </div>
        </div>
    </div>
</div>

</body>

<script>
    var restTime=5;
    function initError(){
        setInterval(function(){
            restTime--;
            if(restTime>0){
                document.getElementById("time").innerHTML=restTime;
            }else{
                location.href='${url}';
            }
        },1000);
    }
</script>

</html>