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
            alert('អ្នកមិនមានវិធីសាស្រ្តទូទាត់.\nសូមកែសម្រួលទម្រង់ដើម្បីបង្កើតយ៉ាងហោចណាស់មួយ។');
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
            $('#country').append($('<option/>').attr("value", option.country).text(option.country));
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
      <%-- <h2>New User Registration</h2> --%>
      <h2>ការចុះឈ្មោះអ្នកប្រើថ្មី</h2>
    </div>

    <div class="row-fluid">
      <div class="span12">
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
            <%-- <h3>Sponsor Information</h3> --%>
            <h3>ព័ត៌មានអ្នកឧបត្ថម្ភ</h3>
          </div>
          <div class="widget-content nopadding">
            <div class="form-horizontal">
              <div class="control-group">
                <%-- <label class="control-label">Zen Username :</label> --%>
                <label class="control-label">ឈ្មោះអ្នកប្រើ Zen&colon;</label>
                <div class="controls">
                  <input type="text" readonly id="sponsorContact" class="span11"/>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
            <%-- <h3>New Agent Information</h3> --%>
            <h3>ព័ត៌មានភ្នាក់ងារថ្មី</h3>
          </div>
          <div class="widget-content nopadding">
            <div class="form-horizontal">
              <div class="control-group">
                <%-- <label class="control-label required-field">Zen Username&colon;</label> --%>
                <label class="control-label required-field">ឈ្មោះអ្នកប្រើ Zen&colon;</label>
                <div class="controls">
                  <%-- <input type="text" id="contact" class="span11"/><a href="#" onClick="return generateRandom()"> Generate</a> --%>
                  <input type="text" id="contact" class="span11"/><a href="#" onClick="return generateRandom()">បង្កើត</a>
                </div>
              </div><div class="control-group">
                <%-- <label class="control-label required-field">Password&colon;</label> --%>
                <label class="control-label required-field">ពាក្យសម្ងាត់&colon;</label>
                <div class="controls">
                  <input type="password" id="password" class="span11"/>
                </div>
              </div>
              <div class="control-group">
                <%-- <label class="control-label required-field">Confirm Password&colon;</label> --%>
                <label class="control-label required-field">បញ្ជាក់ពាក្យសម្ងាត់&colon;</label>
                <div class="controls">
                  <input type="password" id="vpassword" class="span11"/>
                </div>
              </div>
              <div class="control-group">
                <%-- <label class="control-label required-field">Full Name&colon;</label> --%>
                <label class="control-label required-field">ឈ្មោះ​ពេញ&colon;</label>
                <div class="controls">
                  <input type="text" id="fullName" class="span11"/>
                </div>
              </div>
              <div class="control-group">
                <%-- <label class="control-label">Passport/ID No.&colon;</label> --%>
                <label class="control-label">លិខិតឆ្លងដែន/លេខអត្តសញ្ញាណ&colon;</label>
                <div class="controls">
                  <input type="text" id="passportIc" class="span11"/>
                </div>
              </div>
              <div class="control-group">
                <%-- <label class="control-label required-field">Gender&colon;</label> --%>
                <label class="control-label required-field">ភេទ&colon;</label>
                <div class="controls">
                  <label style="margin-top:4px;">
                    <select id="gender">
                    	<option value="Male">ប្រុស</option>
                    	<option value="Female">ស្រី</option>
                    	<option value="Other">ផ្សេងទៀត</option>
                    </select>
                </div>
              </div>
              <div class="control-group">
                <%-- <label class="control-label">Address&colon;</label> --%>
                <label class="control-label">អាសយដ្ឋាន&colon;</label>
                <div class="controls">
                  <input type="text" id="address" class="span11"/>
                </div>
              </div>
              <div class="control-group">
                <%-- <label class="control-label">Postcode&colon;</label> --%>
                <label class="control-label">លេខកូដប្រៃសនី&colon;</label>
                <div class="controls">
                  <input type="text" id="postcode" class="span11"/>
                </div>
              </div>
              <div class="control-group">
                <%-- <label class="control-label">State&colon;</label> --%>
                <label class="control-label">រដ្ឋ&colon;</label>
                <div class="controls">
                  <input type="text" id="state" class="span11"/>
                </div>
              </div>
              <div class="control-group">
                <%-- <label class="control-label required-field">Country&colon;</label> --%>
                <label class="control-label required-field">ប្រទេស&colon;</label>
                <div class="controls">
                  <select id="country">
                  </select>
                </div>
              </div>
              <div class="control-group">
                <%-- <label class="control-label required-field">Phone No.&colon;</label> --%>
                <label class="control-label required-field">ទូរស័ព្ទ&colon;</label>
                <div class="controls">
                  <input type="text" id="phone" class="span11" />
                </div>
              </div>
              <div class="control-group">
                <%-- <label class="control-label required-field">Email&colon;</label> --%>
                <label class="control-label required-field">អ៊ីមែល&colon;</label>
                <div class="controls">
                  <input type="text" id="email" class="span11"/>
                </div>
              </div>
              <div class="form-actions">
                <button type="submit" onclick="return storeRegistration();" class="btn btn-success">អភិរក្ស</button>
                <button type="submit" onclick="return redirectDashboard();" class="btn btn-danger">បោះបង់</button>
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
//set Important Lables
document.getElementById('paymentsPending').innerHTML = punter.actions.paymentsPending;
document.getElementById('upgradable').innerHTML = punter.actions.upgradable;

</script>
</body>
</html>
