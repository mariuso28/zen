<!DOCTYPE html>
<html lang="en">
<head>
<title>registration</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/../../../../../../../../../js/bootstrap.min.js"></script>


<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="../../../css/bootstrap.min.css" />
<link rel="stylesheet" href="../../../css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="../../../css/fullcalendar.css" />
<link rel="stylesheet" href="../../../css/matrix-style.css" />
<link rel="stylesheet" href="../../../css/matrix-media.css" />
<link href="/../../../font-awesome/css/font-awesome.css" rel="stylesheet" />
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

  .required-field::before {
  content: "*";
  color: red;
  float: right;
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
         async: false,
       	 success: function(data) {
    //      alert(JSON.stringify(data));
     			if (data == '')
            {
							alert("could not get punter")
               return;
            }

          var resultJson = $.parseJSON(JSON.stringify(data));
					if (resultJson.status!='OK')
					{
						alert(resultJson.status + " " + resultJson.message);
            return;
					}
    //      alert(JSON.stringify(resultJson.result));
					punter = resultJson.result;
          if (punter.paymentMethods.length==0)
          {
            alert('You have no payment methods set up.\nPlease edit profile to set up at least one.');
            window.location="/zen/zx4/web/anon/goEditProfile";
            return;
          }
        },
				error:function (e) {
	  			alert("getPunter ERROR : " + e.status + " - " + e.statusText);
	      }
     });
 }

function generateRandom()
{
  access_token = sessionStorage.getItem("access_token");
//	alert(access_token);

  var bearerHeader = 'Bearer ' + access_token;
     $.ajax({

    type: "GET",
         url : '/zen/zx4/api/punter/getRandomUsername',
    headers: { 'Authorization': bearerHeader },
  cache: false,
 contentType: 'application/json;',
      dataType: "json",
      success: function(data) {
        if (data == '')
         {
           alert("could not getRandomUsername")
            return null;
         }

       var resultJson = $.parseJSON(JSON.stringify(data));
       if (resultJson.status=='OK')
       {
         username = resultJson.result;
         document.getElementById('contact').value=username;
       }
       else
       {
         alert(resultJson.message);
       }
     },
     error:function (e) {
       alert("generateRandom ERROR : " + e.status + " - " + e.statusText);
     }
  });
}

var labels;

function getLabels()
{
  $.ajax({

 type: "GET",
      url : '/zen/zx4/api/anon/getLabels?jsp=editProfile',
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
         displayCountries();
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

function displayCountries()
{
  $('#country').empty();
  $.each(countries, function(i,country) {
      $('#country').append($('<option/>').attr("value", country).text(country));
  });
}


var newPunter;

function initializeRegistration() {

	access_token = sessionStorage.getItem("access_token");
//	alert(access_token);

  var bearerHeader = 'Bearer ' + access_token;
     $.ajax({

    type: "GET",
         url : '/zen/zx4/api/punter/initializeRegistration',
    headers: { 'Authorization': bearerHeader },
    cache: false,
    contentType: 'application/json;',
         dataType: "json",
       	 success: function(data) {
  //         alert(JSON.stringify(data));
     			if (data == '')
            {
							alert("could not initialize registration")
               return null;
            }

          var resultJson = $.parseJSON(JSON.stringify(data));
					if (resultJson.status=='OK')
					{
			//		alert(resultJson.status + " " + resultJson.message);
            newPunter = resultJson.result;
      //      alert(newPunter);
            populateNewProfile();
					}
				  else
          {
            alert(resultJson.message);
          }
        },
				error:function (e) {
	  			alert("initializeRegistration ERROR : " + e.status + " - " + e.statusText);
	      }
     });
 }

 function storeRegistration() {

 	access_token = sessionStorage.getItem("access_token");
 //	alert(access_token);

   var jsonData = {};
     jsonData['contact'] = document.getElementById('contact').value;
     jsonData['password'] = document.getElementById('password').value;
     jsonData['vpassword'] = document.getElementById('vpassword').value;
     jsonData['email'] = document.getElementById('email').value;
     jsonData['phone'] = document.getElementById('phone').value;
     jsonData['fullName'] = document.getElementById('fullName').value;
     jsonData['gender'] = document.getElementById('gender').value;
     jsonData['passportIc'] = document.getElementById('passportIc').value;
     jsonData['address'] = document.getElementById('address').value;
     jsonData['state'] = document.getElementById('state').value;
     jsonData['postcode'] = document.getElementById('postcode').value;
     jsonData['country'] = document.getElementById('country').value;


   var bearerHeader = 'Bearer ' + access_token;

      	$.ajax({

           type: "POST",
              url : '/zen/zx4/api/punter/storeRegistration',
              headers: { 'Authorization': bearerHeader },
              cache: false,
              contentType: 'application/json;',
              dataType: "json",
              data:JSON.stringify(jsonData),
               success: function(data) {
                    var result = $.parseJSON(JSON.stringify(data));
                    if (data == '')
                      {
          							alert("could not store registration")
                         return null;
                      }

                    var resultJson = $.parseJSON(JSON.stringify(data));
          					if (resultJson.status!='OK')
          					{
          						alert(resultJson.status + " " + resultJson.message);
                      newPunter = resultJson.result;
                      populateNewProfile();
          					}
          				  else
                    {
                      alert("New agent successfully created.");
                      redirectDashboard();
                    }
                  },
          				error:function (e) {
          	  			alert("initializeRegistration ERROR : " + e.status + " - " + e.statusText);
          	      }
           });
  }


function populateNewProfile()
{
  document.getElementById('contact').value = newPunter.contact;
  document.getElementById('sponsorContact').value = newPunter.sponsorContact;
  document.getElementById('phone').value = newPunter.phone;
  document.getElementById('email').value = newPunter.email;
  document.getElementById('fullName').value = newPunter.fullName;
  document.getElementById('passportIc').value = newPunter.passportIc;
  document.getElementById('address').value = newPunter.address;
  document.getElementById('country').value = newPunter.country;
  document.getElementById('state').value = newPunter.state;
  document.getElementById('gender').value = newPunter.country;
}

</script>

</head>
<body>

<!--sidebar-menu-->

<jsp:include page="sidebar.jsp"/>
<!--sidebar-menu-->

<!--main-container-part-->
<div id="content">


<div class="container-fluid">
    <!--Action boxes-->
    <jsp:include page="actions.jsp"/>
<!--End-Action boxes-->
    <div id="content-header">
      <h2 id="newUserRegistrationLabel">New User Registration</h2>
    </div>

    <div class="row-fluid">
      <div class="span12">
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
            <h3 id="sponsorInformationLabel">Sponsor Information</h3>
          </div>
          <div class="widget-content nopadding">
            <div class="form-horizontal">
              <div class="control-group">
                <label class="control-label" id="zenSponsorUsernameLabel">Zen Username :</label>
                <div class="controls">
                  <input type="text" readonly id="sponsorContact" class="span11"/>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
            <h3 id="newAgentInformationLabel">New Agent Information</h3>
          </div>
          <div class="widget-content nopadding">
            <div class="form-horizontal">
              <div class="control-group">
                <label class="control-label required-field" id="zenUsername">Zen Username&nbsp</label>
                <div class="controls">
                  <input type="text" id="contact" class="span11"/><a href="#" onClick="return generateRandom()" id="generateLabel"> Generate</a>
                </div>
              </div><div class="control-group">
                <label class="control-label required-field" id="passwordLabel">Password&nbsp</label>
                <div class="controls">
                  <input type="password" id="password" class="span11"/>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label required-field" id="confirmPasswordLabel">Confirm Password&nbsp</label>
                <div class="controls">
                  <input type="password" id="vpassword" class="span11"/>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label required-field" id='fullNameLabel'>Full Name&nbsp</label>
                <div class="controls">
                  <input type="text" id="fullName" class="span11"/>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" id="passportIcLabel">Passport/ID No.&nbsp</label>
                <div class="controls">
                  <input type="text" id="passportIc" class="span11"/>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label required-field" id="genderLabel">Gender&nbsp</label>
                <div class="controls">
                  <select id="gender">
                  	<option id="maleLabel" value="Male">Male</option>
                  	<option id="femaleLabel" value="Female">Female</option>
                  	<option id="otherLabel" value="Other">Other</option>
                  </select>
              </div>
              <div class="control-group">
                <label class="control-label" id="addressLabel">Address&nbsp</label>
                <div class="controls">
                  <input type="text" id="address" class="span11"/>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" id="postCodeLabel">Postcode&nbsp</label>
                <div class="controls">
                  <input type="text" id="postcode" class="span11"/>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" id="stateLabel">State&nbsp</label>
                <div class="controls">
                  <input type="text" id="state" class="span11"/>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label required-field" id="countryLabel">Country&nbsp</label>
                <div class="controls">
                  <select id="country">
                  </select>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label required-field" id="phoneLabel">>Phone No.&nbsp</label>
                <div class="controls">
                  <input type="text" id="phone" class="span11" />
                </div>
              </div>
              <div class="control-group">
                <label class="control-label required-field" id="emailLabel">Email&nbsp</label>
                <div class="controls">
                  <input type="text" id="email" class="span11"/>
                </div>
              </div>
              <div class="form-actions">
                <button type="submit" onclick="return storeRegistration();" class="btn btn-success" id="saveLabel">Save</button>
                <button type="submit" onclick="return redirectDashboard();" class="btn btn-danger" id="cancelLabel">Cancel</button>
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

<script>

$.ajaxSetup({
   async: false
});


getCountries();
initializeRegistration();
getPunter();
getLabels();

//set Important Lables
document.getElementById('paymentsPending').innerHTML = punter.actions.paymentsPending;
document.getElementById('upgradable').innerHTML = punter.actions.upgradable;

</script>
</body>
</html>
