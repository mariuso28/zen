<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page trimDirectiveWhitespaces="true" %>

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="../../css/overlaystyle.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/foundation/6.4.1/css/foundation.min.css">
<link rel="stylesheet" href="../../css/bootstrap.min.css" />
<link rel="stylesheet" href="../../css/style.css" />


<html>
<head>
    <title>register</title>
<style>

.inner {
   margin-left: auto;
   margin-right: auto;
   width: 1100px;
   height: 300px;
	font-family: 'myFont';
	font-size: 16px;
	font-style: normal;
	font-weight: bold;
}

.topHeading {
    height: 36px;
    margin-left: auto ;
    margin-right: auto ;
    font-size: large;
    font-style: normal;
    font-weight: bold;
    line-height: 40px;
    color: #FFF;
    padding-left: 5px;
    padding-top: 5px;
    box-sizing: content-box;
    font-family: myFont;
  }

.regPanel {
	width: 800px;
	height: 300px;
	float: left;
	margin-left: auto;
	margin-right: auto;
	background-color: #FFF;
	border: solid 1px #CCC;
	padding: 0px;
}

.regPanel:after{
   content: "";
   display: table;
   clear: both;
}

.regPanelLeft {
	width: 800px;
	height: 300px;
	float: left;
	background-color: #0fcabf;
	border: 0px solid #999;
	margin-top: 4px;
}

	.regPanelRight {
		width: 450px;
		height:300px;
		float: left;
		padding: 0px;
		margin-top: 4px;
		margin-left: 0px;
		background-color: #0fcabf;
	}

  .regPanelMargin {
		width: 200px;
		height: 540px;
		float: left;
		padding: 5px;
		margin-top: 4px;
		margin-left: 0px;
		background-color: #0fcabf;
    overflow:auto;
	}

  .regLineMargin {
  	width: 180px;
  	height: 30px;
  	float: left;
  	margin: 2px;
    color: #0c4779;
  }

.regLine {
	width: 800px;
	height: 30px;
	float: left;
	margin: 2px;
	background-color: #0fcabf;
}

.regLineLeft {
	width: 400px;
	height: 26px;
	float: left;
	margin: 2px;
	line-height: 26px;
	background-color: #0fcabf;
	text-align: right;
	color: #0c4779;

}

.regLineRight {
	width: 280px;
	height: 26px;
	float: left;
	margin: 2px;
	margin-left: 4px;
	background-color: #0fcabf;
}

.bottomSaveSection{
			margin-left: auto;
			margin-right: auto;
			width: 800px;
			height: 32px;
			margin-top: 4px;
		}

		.bottomSaveSectionSaveButton{
			float: left;
			width: 200px;
			height: 30px;
			background-color: #fff;
			padding-right: 0px;
			text-align: right;
		}

		.bottomSaveSectionCancelButton{
			float: right;
			width: 80px;
			height: 30px;
			background-color: #fff;
		}

		.bottomSaveSectionInfoMessage{
			float: left;
			width: 800px;
			height: 22px;
      color: blue;
			background-color: #0fcabf;
		}

    .bottomSaveSectionErrorMessage{
      float: left;
      width: 800px;
      height: 22px;
      color: red;
      background-color: #0fcabf;
    }

		.inputText{
			color: #008BE8;
		}

</style>

<script>

function doRegister()
{
  var error = document.getElementById('error');
  error.innerHTML="";
  var info = document.getElementById('info');
  info.innerHTML="";

  var email = document.getElementById('email');
  var contact = document.getElementById('contact');
  var phone = document.getElementById('phone');
  var fullName = document.getElementById('fullName');
  var gender = document.getElementById('gender');
  var passportIc = document.getElementById('passportIc');
  var address = document.getElementById('address');
  var state = document.getElementById('state');
  var postcode = document.getElementById('postcode');
  var country = document.getElementById('country');
  var password = document.getElementById('password');
  var vpassword = document.getElementById('vpassword');

  if (password.value != vpassword.value)
  {
    error.appendChild(document.createTextNode("ពាក្យសម្ងាត់ផ្ទៀងផ្ទាត់និងពាក្យសម្ងាត់ត្រូវតែដូចគ្នា"));
    return;
  }

  var jsonData = {};
  jsonData['email'] = email.value;
  jsonData['contact'] = contact.value;
  jsonData['phone'] = phone.value;
  jsonData['password'] = password.value;
  jsonData['fullName'] = fullName.value;
  jsonData['gender'] = gender.value;
  jsonData['passportIc'] = passportIc.value;
  jsonData['address'] = address.value;
  jsonData['state'] = state.value;
  jsonData['postcode'] = postcode.value;
  jsonData['country'] = country.value;

  registerJson(jsonData);
}

function registerJson(jsonData) {

     $.ajax({

    type: "POST",
        url : "/zen/zx4/api/anon/register",
        cache: false,
        contentType: 'application/json;',
        dataType: "json",
        data:JSON.stringify(jsonData),
         success: function(data) {
        //    alert(JSON.stringify(data));
	          if (data == '')
            {
               return;
            }

     	      var result = $.parseJSON(JSON.stringify(data));
            if (result.status != 'OK')
            {
              var error = document.getElementById('error');
              error.appendChild(document.createTextNode(result.message));
              return;
            }

            var info = document.getElementById('info');
            info.appendChild(document.createTextNode(result.result));
          }
      })
}

</script>

</head>
<body>
  <header>
    <div class="container" style="background-color: #129c94; max-width:100%; height:52px;">
            <div class="topHeading" style="max-width:920px;">
              <%-- Register to Recruit on Zen --%>
              ចុះឈ្មោះដើម្បីជ្រើសរើសនៅលើ Zen
            <%-- <a style="font-family:inherit;font-size: 18px; color:green; font-weight: bold;" href="/zen/web/anon/login">Cancel</a> --%>
            <a style="font-family:inherit;font-size: 18px; color:green; font-weight: bold;" href="/zen/web/anon/login">បោះបង់</a>
            <input id="register" value="Register" onClick="return doRegister();" type="button" style="font-family:inherit;font-size: 18px; color:green; font-weight: bold;" />
      </div>
    </div>
  </header>

  <div class="inner">
  <div class="regPanel">
				<div class="regPanelLeft">
          <div class="regLine">
            <div class="regLineLeft">
							<%-- zen contact --%>
              Zen អ្នក​ដែល​អាច​ទាក់ទង​បាន&colon;
						</div>
						<div class="regLineRight">
							<input id="contact" type="text" value=""
									maxlength="64" style="width:280px; height:28px; font-family:inherit;" />
						</div>
          </div>
					<div class="regLine">
						<div class="regLineLeft">
							<%-- email --%>
              អ៊ីមែល&colon;
						</div>
						<div class="regLineRight">
							<input id="email" type="text" value=""
									maxlength="64" style="width:280px; height:28px; font-family:inherit;" />
						</div>
          </div>
          <div class="regLine">
            <div class="regLineLeft">
							<%-- password --%>
              ពាក្យសម្ងាត់&colon;
						</div>
						<div class="regLineRight">
							<input id="password" type="text" value=""
									maxlength="64" style="width:280px; height:28px; font-family:inherit;" />
						</div>
          </div>
          <div class="regLine">
            <%-- (at least 8 characters and contains 1 number and 1 uppercase character) --%>
            (យ៉ាងហោចណាស់ 8 តួអក្សរហើយមានលេខ 1 ខ្ទង់និងតួអក្សរធំ 1)
          </div>
          <div class="regLine">
            <div class="regLineLeft">
              <%-- verify password --%>
              ផ្ទៀងផ្ទាត់​ពាក្យសម្ងាត់&colon;
            </div>
            <div class="regLineRight">
              <input id="vpassword" type="text" value=""
                  maxlength="64" style="width:280px; height:28px; font-family:inherit;" />
            </div>
          </div>
          <div class="regLine">
            <div class="regLineLeft">
							<%-- phone --%>
              ទូរស័ព្ទ&colon;
						</div>
						<div class="regLineRight">
							<input id="phone" type="text" value=""
									maxlength="64" style="width:280px; height:28px; font-family:inherit;" />
						</div>
					</div>
        <div class="regLine">
          <div class="regLineLeft">
            <%-- Full Name --%>
            ឈ្មោះ​ពេញ&colon;
          </div>
          <div class="regLineRight">
            <input id="fullName" type="text" value=""
                maxlength="64" style="width:280px; height:28px; font-family:inherit;" />
          </div>
        </div>
        <div class="regLine">
          <div class="regLineLeft">
            <%-- gender --%>
            ភេទ&colon;
          </div>
          <div class="regLineRight">
            <input id="gender" type="text" value=""
                maxlength="64" style="width:280px; height:28px; font-family:inherit;" />
          </div>
        </div>
        <div class="regLine">
          <div class="regLineLeft">
            <%-- Passport/IC --%>
            លិខិតឆ្លងដែន/លេខអត្តសញ្ញាណ&colon;
          </div>
          <div class="regLineRight">
            <input id="passportIc" type="text" value=""
                maxlength="64" style="width:280px; height:28px; font-family:inherit;" />
          </div>
        </div>
        <div class="regLine">
          <div class="regLineLeft">
            <%-- address --%>
            អាសយដ្ឋាន&colon;
          </div>
          <div class="regLineRight">
            <input id="address" type="text" value=""
                maxlength="256" style="width:280px; height:28px; font-family:inherit;" />
          </div>
        </div>
        <div class="regLine">
          <div class="regLineLeft">
            <%-- state --%>
            រដ្ឋ&colon;
          </div>
          <div class="regLineRight">
            <input id="state" type="text" value=""
                maxlength="64" style="width:280px; height:28px; font-family:inherit;" />
          </div>
        </div>
        <div class="regLine">
          <div class="regLineLeft">
            <%-- postcode --%>
            លេខកូដប្រៃសនី&colon;
          </div>
          <div class="regLineRight">
            <input id="postcode" type="text" value=""
                maxlength="16" style="width:280px; height:28px; font-family:inherit;" />
          </div>
        </div>
        <div class="regLine">
          <div class="regLineLeft">
            <%-- country --%>
            ប្រទេស&colon;
          </div>
          <div class="regLineRight">
            <input id="country" type="text" value=""
                maxlength="128" style="width:280px; height:28px; font-family:inherit;" />
          </div>
        </div>
        </div>
      </div>
    </div>
    <div id="info" class="bottomSaveSectionInfoMessage">
    </div>
    <div id="error" class="bottomSaveSectionErrorMessage">
    </div>
</div> <!-- inner -->

</body>
</html>
