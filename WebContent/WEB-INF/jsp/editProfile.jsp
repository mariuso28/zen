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
<link href="../../../css/font-awesome.css" rel="stylesheet" />
<link rel="stylesheet" href="../../../css/jquery.gritter.css" />
<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700,800' rel='stylesheet' type='text/css'>

<style>
  body {
    font-size: 16px;
  }

  .center {
    display: block;
    margin-left: auto;
    margin-right: auto;
    width: 30%;
    padding: 10px;
  }

  .logo{
    text-align: center;
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
var countries;
var paymentMethods;

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

function getCountries()
{
  $.ajax({

 type: "GET",
      url : '/zen/zx4/api/anon/getCountries',
  cache: false,
 contentType: 'application/json;',
      dataType: "json",
       async: false,
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
    if (punter.countryCode==country.code)
     $('#country').append($('<option selected/>').attr("value", country.code).text(country.country));
    else {
      $('#country').append($('<option/>').attr("value", country.code).text(country.country));
    }
  });
}

function getAvailablePaymentMethods()
{
     $.ajax({

    type: "GET",
         url : '/zen/zx4/api/anon/getAvailablePaymentMethods',
         cache: false,
         contentType: 'application/json;',
         dataType: "json",
         async: false,
         success: function(data) {
         if (data == '')
         {
           alert("could not getPaymentMethods")
            return null;
         }
         // alert(JSON.stringify(data));
       var resultJson = $.parseJSON(JSON.stringify(data));
       if (resultJson.status=='OK')
       {
         paymentMethods = resultJson.result;
        $('#avaiLabelPaymentMethods').empty();
         $('#avaiLabelPaymentMethods').append($('<option id="methodCountryLabel"/>').attr("value", -1).text("Method - Country"));
         $.each(paymentMethods, function(i, option) {
            $('#avaiLabelPaymentMethods').append($('<option/>').attr("value", i).text(option.method + " - " + option.country));
         });
       }
       else
       {
         alert(resultJson.message);
       }
     },
     error:function (e) {
       alert("getAvailablePaymentMethods ERROR : " + e.status + " - " + e.statusText);
     }
  });
}

function deletePaymentMethod(id)
{
  confirm('Will delete this payment method - are you sure?')
  access_token = sessionStorage.getItem("access_token");
  //	alert(access_token);

  var bearerHeader = 'Bearer ' + access_token;
     $.ajax({
       type: "GET",
       url : '/zen/zx4/api/punter/deletePaymentMethod?id='+id,
       headers: { 'Authorization': bearerHeader },
       cache: false,
       contentType: 'application/json;',
       dataType: "json",
       success: function(data) {
       if (data == '')
       {
          alert("could not deletePaymentMethod")
          return null;
       }

     var resultJson = $.parseJSON(JSON.stringify(data));
     if (resultJson.status=='OK')
     {
       refreshProfile();
     }
     else
     {
       alert(resultJson.message);
     }
   },
   error:function (e) {
     alert("deletePaymentMethod ERROR : " + e.status + " - " + e.statusText);
   }
 });
}

function addPaymentMethod(value)
{
    if (value==-1)
      return;
    method = paymentMethods[value];
//    alert(method.method + " - " + method.country);
    var accountNumber = prompt("Please enter your account number for payment method : "+method.method + " - " + method.country, "88888888");
    if (accountNumber == null)
      return;

    addPunterPaymentMethod(method.id,accountNumber);
}


function addPunterPaymentMethod(id,accountNumber)
{
  access_token = sessionStorage.getItem("access_token");
  //	alert(access_token);

  var bearerHeader = 'Bearer ' + access_token;
     $.ajax({

    type: "GET",
         url : '/zen/zx4/api/punter/addPaymentMethod?id='+id+'&accountNumber='+accountNumber,
         headers: { 'Authorization': bearerHeader },
         cache: false,
         contentType: 'application/json;',
         dataType: "json",
         success: function(data) {
         if (data == '')
         {
            alert("could not addPaymentMethod")
            return null;
         }

       var resultJson = $.parseJSON(JSON.stringify(data));
       if (resultJson.status=='OK')
       {
         refreshProfile();
       }
       else
       {
         alert(resultJson.message);
       }
     },
     error:function (e) {
       alert("addPunterPaymentMethod ERROR : " + e.status + " - " + e.statusText);
     }
  });
}

function refreshProfile()
{
  getLabels();

  getPunter();
//set Important Labels
  document.getElementById('paymentsPending').innerHTML = punter.actions.paymentsPending;
  document.getElementById('upgradable').innerHTML = punter.actions.upgradable;

  getCountries();
  getAvailablePaymentMethods();

  displayLabels();
}

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
//    document.getElementById('country').value = punter.country;
    document.getElementById('state').value = punter.state;
    document.getElementById('gender').value = punter.gender;
    displayPaymentMethods();
}

function displayPaymentMethods()
{
    var pl = document.getElementById('paymentMethodList');
    pl.innerHTML="";

    punter.paymentMethods.forEach((pm, i) => {
        tr = document.createElement('tr');
        tr.className = 'gradeA';
        td = document.createElement('td');
        td.appendChild(document.createTextNode(i+1));
        tr.appendChild(td);
        td = document.createElement('td');
        td.appendChild(document.createTextNode(pm.method));
        tr.appendChild(td);
        td = document.createElement('td');
        td.appendChild(document.createTextNode(pm.country));
        tr.appendChild(td);
        td = document.createElement('td');
        td.appendChild(document.createTextNode(pm.accountNum));
        tr.appendChild(td);
        td = document.createElement('td');

        var t = document.createElement('IMG');
        t.setAttribute('src',"../../../img/delete.jpeg");
        t.setAttribute('border', 0);
        t.setAttribute('width',"20px");
        t.setAttribute('height',"20px");
        t.addEventListener('click',function (e) {
            deletePaymentMethod(pm.id);
          });
        td.appendChild(t);

        tr.appendChild(td);
        pl.appendChild(tr);
      })
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
   						  alert("could update punter")
                return;
              }

              var resultJson = $.parseJSON(JSON.stringify(data));
 					    if (resultJson.status!='OK')
 					    {
 						    alert(resultJson.message);
                return;
 					    }
   					  alert(labels['epAlert1']);
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

<jsp:include page="sidebar.jsp"/>
<!--sidebar-menu-->

<!--main-container-part-->
<div id="content">

  <div class="container-fluid">
    <!--Action boxes-->
    <jsp:include page="actions.jsp"/>
<!--End-Action boxes-->
    <div id="content-header">
      <h2 id='myUserProfileLabel'></h2>
    </div>

    <div class="row-fluid">
      <div class="span12">
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
            <h3 id='personalInfomationLabel'></h3>
          </div>
          <div class="widget-content nopadding">
            <div class="form-horizontal">
              <div class="control-group">
                <label class="control-label" id='usernameLabel'></label>
                <div class="controls">
                  <input readonly type="text" id="contact" class="span11" value=""/>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" id="zenSponsorLabel"></label>
                <div class="controls">
                  <input readonly type="text" id="sponsorContact" class="span11" value=""/>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label required-field" id='fullNameLabel'></label>
                <div class="controls">
                  <input type="text" id="fullName" class="span11" value=""/>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" id="passportIcLabel"></label>
                <div class="controls">
                  <input type="text" id="passportIc" class="span11" value=""/>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label required-field" id="genderLabel"></label>
                <div class="controls">
                  <select id="gender">
                  	<option id="maleLabel" value="Male"></option>
                  	<option id="femaleLabel" value="Female"></option>
                  	<option id="otherLabel" value="Other"></option>
                  </select>
              </div>
              <div class="control-group">
                <label class="control-label" id="addressLabel"></label>
                <div class="controls">
                  <input type="text" id="address" class="span11" value=""/>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" id="postCodeLabel"></label>
                <div class="controls">
                  <input type="text" id="postcode" class="span11" value=""/>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" id="stateLabel"></label>
                <div class="controls">
                  <input type="text" id="state" class="span11" value=""/>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label required-field" id="countryLabel"></label>
                <div class="controls">
                  <select id="country">
                  </select>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label required-field" id="phoneLabel"></label>
                <div class="controls">
                  <input type="text" id="phone" class="span11" value="+855 "/>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label required-field" id="emailLabel"></label>
                <div class="controls">
                  <input type="text" id="email" class="span11" value=""/>
                </div>
              </div>
              <div class="control-group">
                <label class="control-label" id="yourPaymentMethodsLabel"></label>
                <div class="controls">
                <table class="table table-bordered data-table">
                  <thead>
                    <tr>
                      <th id="noLabel"></th>
                      <th id="methodLabel"></th>
                      <th id="pmCountryLabel"></th>
                      <th id="accountNumberLabel"></th>
                      <th id="deleteLabel"></th>
                    </tr>
                  </thead>
                  <tbody id='paymentMethodList'>
                  </tbody>
                </table>
                <div>
              </div>
              <div class="control-group">
                <label class="control-label" id="addPaymentMethodLabel"></label>
                <div class="controls">
                  <select id="avaiLabelPaymentMethods" onchange="addPaymentMethod(value)">
                  </select>
                </div>
              </div>
              <div class="form-actions">
                <button type="submit" onclick="return updateProfile();" class="btn btn-success" id="saveLabel"></button>
                <button type="submit" class="btn btn-danger" onclick="return redirectDashboard();" id="cancelLabel"></button>
              </div>
            </div>
          </div>
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

$.ajaxSetup({
   async: false
});

refreshProfile();

</script>
</body>
</html>
