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

var labels;

function getLabels()
{
  $.ajax({

 type: "GET",
      url : '/zen/zx4/api/anon/getLabels?jsp=login',
  cache: false,
 contentType: 'application/json;',
      dataType: "json",
       async: false,
      success: function(data) {
        if (data == '')
         {
           alert("could not getLabels")
            return null;
         }

       var resultJson = $.parseJSON(JSON.stringify(data));
       if (resultJson.status=='OK')
       {
         labels = resultJson.result;
         displayLabels();
       }
       else
       {
         alert(resultJson.message);
       }
     },
     error:function (e) {
       alert("getLabels ERROR : " + e.status + " - " + e.statusText);
     }
  });
}

function displayLabels()
{
//  console.log(labels);
  const entries = Object.entries(labels);
  for (const [lab, val] of entries)
  {
  //  console.log(lab + val);
    elem = document.getElementById(lab)
    if (elem!=null)
      elem.innerHTML=val;
  }
}


function lostPassword() {


 	var username = document.getElementById('username').value;

  if (username=="")
  {
    alert(labels["alert1"]);
    return;
  }

  if (!confirm(labels["alert2"] + " " + username + " " + labels["alert3"]))
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
      <h3>
        <a href="/zen/zx4/web/anon/changeLanguage?isoCode=km">
          <img src="../../../img/flag_km.png" alt="Logo" width="50"/>
        </a>
        <img src="../../../img/GoldenCircle_02.png" alt="Logo" width="150"/>
        <a href="/zen/zx4/web/anon/changeLanguage?isoCode=en">
          <img src="../../../img/flag_en.png" alt="Logo" width="50"/>
        </a>
      </h3>
      </div>
      <div class="control-group">
        <div class="controls">
          <div class="main_input_box">
            <span class="add-on bg_lg"><i class="icon-user"></i></span>
            <input type="text" id="username" value="mony" placeholder="Username" />
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
            <span class="pull-left"><a href="#" onclick="return lostPassword()" class="flip-link btn btn-info"
              id="lostPasswordLabel"></a></span>
            <span class="pull-right">
              <a type="submit" onclick="return login()" class="btn btn-success" id="loginLabel"/></a></span>
          </div>
    </form>
  </div>

  <script src="js/jquery.min.js"></script>
  <script src="js/matrix.login.js"></script>

<script>
  getLabels();
</script>

</body>

</html>
