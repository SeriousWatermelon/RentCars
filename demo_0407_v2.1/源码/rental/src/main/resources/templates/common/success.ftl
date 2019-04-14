<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Successful Operation</title>
    <link href="/bootstrap-4.3.1-dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body onload="initSuccess()">

<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="alert alert-dismissable alert-success">
                <h4>
                    Successful operation!
                </h4> <strong>${msg!"Successful operation"}!</strong>.......... <a href="${url}" class="alert-link">loading...<label id="time">3</label>s</a>
            </div>
        </div>
    </div>
</div>

</body>

<script>
    var restTime=3;
    function initSuccess(){
        setInterval(function(){
            restTime--;
            if(restTime>0){
                document.getElementById("time").innerHTML=restTime;
            }else{
                location.href='${url}';
            }
        }, 1000);
    }
</script>

</html>