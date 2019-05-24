<!DOCTYPE html>
<html lang="en">

<head>
  <title>login</title>
  <meta charset="UTF-8" />

  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <link rel="stylesheet" href="../../../css/bootstrap.min.css" />
  <link rel="stylesheet" href="../../../css/bootstrap-responsive.min.css" />
  <link rel="stylesheet" href="../../../css/matrix-login.css" />
  <link href="font-awesome/css/font-awesome.css" rel="stylesheet" />
  <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700,800' rel='stylesheet' type='text/css'>

<script>

function lostPassword() {


 	var username = document.getElementById('username').value;

  if (username=="")
  {
    alert("Please supply your Zen username so we can reset your password.");
    return;
  }

  if (!confirm("A new password for zen member : " + username + " will be sent to your email"))
    return;

  $.ajax({

     type: "GET",
        url : "/zen/zx4/api/anon/resetPassword?username=" + username,
        cache: false,
        contentType: 'application/json;',
        dataType: "json",
           success: function(data) {
           var result = $.parseJSON(JSON.stringify(data));
           if (result.status != 'OK')
           {
             alert(result.message);
             return;
           }
           alert(result.result);
         }
     });
 }

function login() {


 	var username = document.getElementById('username');
  var password = document.getElementById('password');

	var jsonData = {};
	  jsonData['username'] = username.value;
	  jsonData['password'] = password.value;

  //   alert("login with : " + username.value + " " + password.value);

	$.ajax({

     type: "POST",
        url : "/zen/zx4/api/a/authorize1",
        cache: false,
        contentType: 'application/json;',
        dataType: "json",
        data:JSON.stringify(jsonData),
         success: function(data) {
           var result = $.parseJSON(JSON.stringify(data));
           if (result.status != 'OK')
           {
             alert(result.message);
             return;
           }
           var authorized = result.result;
  //         alert(authorized.body.access_token + " : " + authorized.role);
           var access_token = authorized.body.access_token;
           sessionStorage.setItem("access_token",access_token);
           at = sessionStorage.getItem("access_token");
  //         alert(at);
						if (authorized.role=='ROLE_PUNTER')
              	window.location.replace("/zen/zx4/web/anon/goDashboard");
        }
     });
 }

</script>

</head>

<body>
  <div id="loginbox">
    <form id="loginform" class="form-vertical" action="index.html">
      <div class="control-group normal_text">
        <h3><img src="../../../img/GoldenCircle_02.png" alt="Logo" width="100"/></h3>
      </div>
      <div class="control-group">
        <div class="controls">
          <div class="main_input_box">
            <span class="add-on bg_lg"><i class="icon-user"> </i></span>
            <input type="text" id="username" value="zen" placeholder="Username" />
          </div>
        </div>
        </div>
        <div class="control-group">
          <div class="controls">
            <div class="main_input_box">
              <span class="add-on bg_ly"><i class="icon-lock"></i></span>
                <input id="password" type="password" value="88888888" placeholder="Password" />
            </div>
          </div>
          </div>
          <div class="form-actions">
            <span class="pull-left"><a href="#" onclick="return lostPassword()" class="flip-link btn btn-info" id="to-recover">Lost password?</a></span>
            <span class="pull-right">
              <a type="submit" onclick="return login()" class="btn btn-success" />Login</a></span>
          </div>
    </form>
    <form id="recoverform" action="#" class="form-vertical">
      <p class="normal_text">Enter your e-mail address below and we will send you instructions how to recover a password.</p>

      <div class="controls">
        <div class="main_input_box">
          <span class="add-on bg_lo"><i class="icon-envelope"></i></span><input type="text" placeholder="E-mail address" />
                        </div>
        </div>

        <div class="form-actions">
          <span class="pull-left"><a href="#" class="flip-link btn btn-success" id="to-login">&laquo; Back to login</a></span>
          <span class="pull-right"><a class="btn btn-info"/>Reecover</a></span>
        </div>
    </form>
  </div>

  <script src="js/jquery.min.js"></script>
  <script src="js/matrix.login.js"></script>
</body>

</html>
