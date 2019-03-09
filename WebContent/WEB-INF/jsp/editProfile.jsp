<!DOCTYPE html>
<html lang="en">
<head>
<title>editProfile</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/../../../js/bootstrap.min.js"></script>

<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="../../../css/bootstrap.min.css" />
<link rel="stylesheet" href="../../../css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="../../../css/fullcalendar.css" />
<link rel="stylesheet" href="../../../css/matrix-style.css" />
<link rel="stylesheet" href="../../../css/matrix-media.css" />
<link href="font-awesome/../../../css/font-awesome.css" rel="stylesheet" />
<link rel="stylesheet" href="../../../css/jquery.gritter.css" />
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

var countries;

function getCountries()
{
  $.ajax({

 type: "GET",
      url : '/zen/zx4/api/anon/getCountries',
  cache: false,
 contentType: 'application/json;',
      dataType: "json",
      success: function(data) {
        if (data == '')
         {
           alert("could not getCountries")
            return null;
         }

       var resultJson = $.parseJSON(JSON.stringify(data));
       if (resultJson.status=='OK')
       {
         countries = resultJson.result;
         $.each(countries, function(i, option) {
            $('#country').append($('<option/>').attr("value", option.code).text(option.country));
         });
       }
       else
       {
         alert(resultJson.message);
       }
     },
     error:function (e) {
       alert("getCountries ERROR : " + e.status + " - " + e.statusText);
     }
  });
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
    //      alert(JSON.stringify(resultJson.result));
					punter = resultJson.result;
          populatePunter();
        },
				error:function (e) {
	  			alert("getPunter ERROR : " + e.status + " - " + e.statusText);
	      }
     });
 }

function populatePunter()
{
    document.getElementById('contact').value = punter.contact;
    document.getElementById('sponsorContact').value = punter.sponsorContact;
    document.getElementById('phone').value = punter.phone;
    document.getElementById('email').value = punter.email;
    document.getElementById('fullName').value = punter.fullName;
    document.getElementById('passportIc').value = punter.passportIc;
    document.getElementById('address').value = punter.address;
    document.getElementById('country').value = punter.country;
    document.getElementById('state').value = punter.state;
    document.getElementById('gender').value = punter.gender;
}

function updateProfile() {

  access_token = sessionStorage.getItem("access_token");
  var bearerHeader = 'Bearer ' + access_token;

	var jsonData = {};
	  jsonData['email'] = document.getElementById('email').value;
	  jsonData['phone'] = document.getElementById('phone').value;
    jsonData['fullName'] = document.getElementById('fullName').value;
    jsonData['gender'] = document.getElementById('gender').value;
    jsonData['passportIc'] = document.getElementById('passportIc').value;
    jsonData['address'] = document.getElementById('address').value;
    jsonData['state'] = document.getElementById('state').value;
    jsonData['postcode'] = document.getElementById('postcode').value;
    jsonData['country'] = document.getElementById('country').value;


	$.ajax({

     type: "POST",
        url : '/zen/zx4/api/punter/updatePunter',
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
   					  alert('Profile successfully updated');
              redirectDashboard();
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

<div id="sidebar"><a href="/zen/zx4/web/anon/goDashboard" class="visible-phone"><i class="icon icon-home"></i> Dashboard</a>
  <div class="toplogo">
    <img src="../../../img/GoldenCircle_02a.png" width="220" height="220"/>
  </div>
  <ul>
    <li><a href="/zen/zx4/web/anon/goDashboard"><i class="icon icon-home"></i> <span>Dashboard</span></a> </li>
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
    <li><a href="/zen/zx4/web/anon/login"><i class="icon icon-off"></i> <span>Logout</span></a> </li>
  </ul>
</div>
<!--sidebar-menu-->

<!--main-container-part-->
<div id="content">


<!--Action boxes-->
  <div class="container-fluid">
    <div class="quick-actions_homepage">
      <ul class="quick-actions">
        <li class="bg_lb"> <a href="/zen/zx4/web/anon/goChangePassword"> <i class="icon-lock"></i>Password </a> </li>
        <li class="bg_lg"> <a href="#"> <i class="icon-signout"></i> Payment Sent</a> </li>
        <!-- <li class="bg_ly"> <a href="widgets.html"> <i class="icon-inbox"></i><span class="label label-success">101</span> Payment Received </a> </li> -->
        <li class="bg_ly"> <a href="#"> <i class="icon-signin"></i>Payment Received </a> </li>
        <li class="bg_lo"> <a href="#"> <i class="icon-upload"></i> Upgrade</a> </li>
        <li class="bg_ls"> <a href="#"> <i class="icon-user"></i> Agent List</a> </li>
        <li class="bg_lo"> <a href="/zen/zx4/web/anon/goNewRegistration"> <i class="icon-star"></i> New Registration</a> </li>
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
            <h3>Personal Information</h3>
          </div>
          <div class="widget-content nopadding">
            <div class="form-horizontal">
              <div class="control-group">
                <label class="control-label">Username :</label>
                <div class="controls">
                  <input readonly type="text" id="contact" class="span11" value=""/>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">Zen Sponser :</label>
                <div class="controls">
                  <input readonly type="text" id="sponsorContact" class="span11" value=""/>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">Full Name :</label>
                <div class="controls">
                  <input type="text" id="fullName" class="span11" value=""/>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">Passport/ID No. :</label>
                <div class="controls">
                  <input type="text" id="passportIc" class="span11" value=""/>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">Gender</label>
                <div class="controls">
                  <select id="gender">
                  	<option value="Male">Male</option>
                  	<option value="Female">Female</option>
                  	<option value="Other">Other</option>
                  </select>
              </div>
              <div class="control-group">
                <label class="control-label">Address :</label>
                <div class="controls">
                  <input type="text" id="address" class="span11" value=""/>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">Postcode :</label>
                <div class="controls">
                  <input type="text" id="postcode" class="span11" value=""/>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">State :</label>
                <div class="controls">
                  <input type="text" id="state" class="span11" value=""/>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">Country :</label>
                <div class="controls">
                  <select id="country">
                  </select>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">Phone No. :</label>
                <div class="controls">
                  <input type="text" id="phone" class="span11" value="+855 "/>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label">Email :</label>
                <div class="controls">
                  <input type="text" id="email" class="span11" value=""/>
                </div>
              </div>
              <div class="form-actions">
                <button type="submit" onclick="return updateProfile();" class="btn btn-success">Save</button>
                <button type="submit" class="btn btn-danger" onclick="return redirectDashboard();">Cancel</button>
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

<script src="../../../js/excanvas.min.js"></script>
<script src="../../../js/jquery.min.js"></script>
<script src="../../../js/jquery.ui.custom.js"></script>
<script src="../../../js/bootstrap.min.js"></script>
<script src="../../../js/jquery.flot.min.js"></script>
<script src="../../../js/jquery.flot.resize.min.js"></script>
<script src="../../../js/jquery.peity.min.js"></script>
<script src="../../../js/fullcalendar.min.js"></script>
<script src="../../../js/matrix.js"></script>
<script src="../../../js/matrix.dashboard.js"></script>
<script src="../../../js/jquery.gritter.min.js"></script>
<script src="../../../js/matrix.interface.js"></script>
<script src="../../../js/matrix.chat.js"></script>
<script src="../../../js/jquery.validate.js"></script>
<script src="../../../js/matrix.form_validation.js"></script>
<script src="../../../js/jquery.wizard.js"></script>
<script src="../../../js/jquery.uniform.js"></script>
<script src="../../../js/select2.min.js"></script>
<script src="../../../js/matrix.popover.js"></script>
<script src="../../../js/jquery.dataTables.min.js"></script>
<script src="../../../js/matrix.tables.js"></script>

<script type="text/javascript">

getCountries();
getPunter();

</script>
</body>
</html>
