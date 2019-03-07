<!DOCTYPE html>
<html lang="en">
<head>
<title>changePassword</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/../../../../../../jsbootstrap.min.js"></script>


<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="../../../cssbootstrap.min.css" />
<link rel="stylesheet" href="../../../cssbootstrap-responsive.min.css" />
<link rel="stylesheet" href="../../../cssfullcalendar.css" />
<link rel="stylesheet" href="../../../cssmatrix-style.css" />
<link rel="stylesheet" href="../../../cssmatrix-media.css" />
<link href="font-awesome/../../../cssfont-awesome.css" rel="stylesheet" />
<link rel="stylesheet" href="../../../cssjquery.gritter.css" />
<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700,800' rel='stylesheet' type='text/css'>

<style>
  body {
    font-size: 16px;
  }

  .toplogo {
    width: 220px;
    height: 220px;
  }
</style>

<script>

function redirectDashboard()
{
  window.location.replace("/zen/zx4/web/anon/goDashboard");
}

var punter;

function getPunter() {

	access_token = sessionStorage.getItem("access_token");
//	alert(access_token);

  var bearerHeader = 'Bearer ' + access_token;
     $.ajax({

    type: "GET",
         url : '/zen/zx4/api/punter/getPunter',
    headers: { 'Authorization': bearerHeader },
    cache: false,
    contentType: 'application/json;',
         dataType: "json",
       	 success: function(data) {
  //         alert(JSON.stringify(data));
     			if (data == '')
            {
							alert("could not get punter")
               return null;
            }

          var resultJson = $.parseJSON(JSON.stringify(data));
					if (resultJson.status!='OK')
					{
						alert(resultJson.status + " " + resultJson.message);
					}
					punter = resultJson.result;
          populateProfile();
        },
				error:function (e) {
	  			alert("getPunter ERROR : " + e.status + " - " + e.statusText);
	      }
     });
 }

 function changePassword() {

   access_token = sessionStorage.getItem("access_token");
   var bearerHeader = 'Bearer ' + access_token;

  if (document.getElementById('password').value != document.getElementById('vpassword').value)
  {
    alert("Password/verify password must match.");
    return;
  }

 	var jsonData = {};
 	  jsonData['oldPassword'] = document.getElementById('oldPassword').value;
 	  jsonData['password'] = document.getElementById('password').value;

 	$.ajax({

      type: "POST",
         url : '/zen/zx4/api/punter/changePassword',
         headers: { 'Authorization': bearerHeader },
         cache: false,
         contentType: 'application/json;',
         dataType: "json",
         data:JSON.stringify(jsonData),
          success: function(data) {
               var result = $.parseJSON(JSON.stringify(data));
               if (data == '')
               {
    						  alert("could not get punter")
                 return null;
               }

               var resultJson = $.parseJSON(JSON.stringify(data));
  					    if (resultJson.status!='OK')
  					    {
  						    alert(resultJson.message);
  					    }
                else {
                  alert("Password successfully changed.");
                }
           },
           error:function (e) {
  	  			    alert("updatePunter ERROR : " + e.status + " - " + e.statusText);
  	        }
      });
  }


</script>


</head>
<body>

<!--sidebar-menu-->

<div id="sidebar"><a href="#" class="visible-phone"><i class="icon icon-home"></i> Dashboard</a>
  <div class="toplogo">
    <img src="img/GoldenCircle_02a.png" width="220" height="220"/>
  </div>
  <ul>
    <li><a href="index.html"><i class="icon icon-home"></i> <span>Dashboard</span></a> </li>
    <li class="active submenu"> <a href="#"><i class="icon icon-user"></i> <span>My Profile</span><span class="caret" style="margin-left:10px; margin-top:8px;"></span></a>
      <ul>
        <li><a href="/zen/zx4/web/anon/goEditProfile">Edit Profile</a></li>
        <li><a href="/zen/zx4/web/anon/goChangePassword">Change Password</a></li>
      </ul>
    </li>
    <li class="submenu"> <a href="#"><i class="icon icon-group"></i> <span>Agents</span><span class="caret" style="margin-left:10px; margin-top:8px; border"></span></a>
      <ul>
        <li><a href="#">Agent List</a></li>
        <li><a href="#">Upgrade<span class="label label-important" style="margin-left:5px;">1</span></a></li>
        <li><a href="#">New Registration</a></li>
        <li><a href="#">Geneology</a></li>
        <li><a href="#">Grade Summary</a></li>
      </ul>
    </li>
    <li class="submenu"> <a href="#"><i class="icon icon-money"></i> <span>Payment</span><span class="caret" style="margin-left:10px; margin-top:8px;"></span></a>
      <ul>
        <li><a href="#">Payment Received<span class="label label-important" style="margin-left:5px;">2</span></a></li>
        <li><a href="#">Payment Sent</a></li>
      </ul>
    </li>
    <li><a href="#"><i class="icon icon-off"></i> <span>Logout</span></a> </li>
  </ul>
</div>
<!--sidebar-menu-->

<!--main-container-part-->
<div id="content">


<!--Action boxes-->
  <div class="container-fluid">
    <div class="quick-actions_homepage">
      <ul class="quick-actions">
        <li class="bg_lb"> <a href="#"> <i class="icon-lock"></i>Password </a> </li>
        <li class="bg_lg"> <a href="#"> <i class="icon-signout"></i> Payment Sent</a> </li>
        <!-- <li class="bg_ly"> <a href="widgets.html"> <i class="icon-inbox"></i><span class="label label-success">101</span> Payment Received </a> </li> -->
        <li class="bg_ly"> <a href="#"> <i class="icon-signin"></i>Payment Received </a> </li>
        <li class="bg_lo"> <a href="#"> <i class="icon-upload"></i> Upgrade</a> </li>
        <li class="bg_ls"> <a href="#"> <i class="icon-user"></i> Agent List</a> </li>
        <li class="bg_lo"> <a href="#"> <i class="icon-star"></i> New Registration</a> </li>
      </ul>
    </div>
<!--End-Action boxes-->
    <div id="content-header">
      <h2>My User Profile</h2>
    </div>

    <div class="row-fluid">
      <div class="span12">
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
            <h3>Change Password</h3>
          </div>
          <div class="widget-content nopadding">
            <div class="form-horizontal">
              <div class="control-group">
                <label class="control-label">Old Password :</label>
                <div class="controls">
                  <input type="password" id="oldPassword" class="span11"/>
                </div>
              </div><div class="control-group">
                <label class="control-label">New Password :</label>
                <div class="controls">
                  <input type="password" id="password" class="span11"/>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">Confirm Password :</label>
                <div class="controls">
                  <input type="password" id="vpassword" class="span11"/>
                </div>
              </div>
              <div class="form-actions">
                <button type="submit" onclick="return changePassword();" class="btn btn-success">Save</button>
                <button type="submit" onclick="return redirectDashboard();" class="btn btn-danger">Cancel</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<!--end-main-container-part-->

<!--Footer-part-->

<div class="row-fluid">
  <div id="footer" class="span12"> 2019 &copy; Zen</div>
</div>

<!--end-Footer-part-->

<script src="../../../jsexcanvas.min.js"></script>
<script src="../../../jsjquery.min.js"></script>
<script src="../../../jsjquery.ui.custom.js"></script>
<script src="../../../jsbootstrap.min.js"></script>
<script src="../../../jsjquery.flot.min.js"></script>
<script src="../../../jsjquery.flot.resize.min.js"></script>
<script src="../../../jsjquery.peity.min.js"></script>
<script src="../../../jsfullcalendar.min.js"></script>
<script src="../../../jsmatrix.js"></script>
<script src="../../../jsmatrix.dashboard.js"></script>
<script src="../../../jsjquery.gritter.min.js"></script>
<script src="../../../jsmatrix.interface.js"></script>
<script src="../../../jsmatrix.chat.js"></script>
<script src="../../../jsjquery.validate.js"></script>
<script src="../../../jsmatrix.form_validation.js"></script>
<script src="../../../jsjquery.wizard.js"></script>
<script src="../../../jsjquery.uniform.js"></script>
<script src="../../../jsselect2.min.js"></script>
<script src="../../../jsmatrix.popover.js"></script>
<script src="../../../jsjquery.dataTables.min.js"></script>
<script src="../../../jsmatrix.tables.js"></script>

<script type="text/javascript">
  // This function is called from the pop-up menus to transfer to
  // a different page. Ignore if the value returned is a null string:
  function goPage (newURL) {

      // if url is empty, skip the menu dividers and reset the menu selection to default
      if (newURL != "") {

          // if url is "-", it is this page -- reset the menu:
          if (newURL == "-" ) {
              resetMenu();
          }
          // else, send page to designated URL
          else {
            document.location.href = newURL;
          }
      }
  }

// resets the menu selection upon entry to this page:
function resetMenu() {
   document.gomenu.selector.selectedIndex = 2;
}
</script>
</body>
</html>
