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

<style>

.headingPanel {
			float: left;
			width: 1100px;
			height: 90px;

		}

	.headingPanelMiddle {
			float: left;
			width: 1100px;
			height: 90px;
			margin-left: 20px;
			margin-bottom: 20px;
		}

	.headingPanelRight {
			float: left;
			width: 100px;
			height: 90px;
		}

		.headingPanelLogonHeader {
				float: left;
				width: 1000px;
				height: 18px;
				font-family: myFont;
				font-size: 120%;
				font-style: normal;
				font-weight: 500;
				line-height: 30px;
				color: #28166f;
				margin-left: 0px;
				margin-top: 10px;
			}

			.headingPanelLogonHeaderCell {
					float: right;
					width: 200px;
					height: 12px;
					margin-right: 5px;
					font-family: Arial, Helvetica, sans-serif;
					font-size: 12px;
					font-style: normal;
					font-weight: 700;
					line-height: 18px;
					color: #blue;
					text-align: center;
				}

	.headingPanelMiddleSearchHeader {
			float: left;
			width: 1100px;
			height: 20px;
			font-family: myFont;
			font-size: 120%;
			font-style: normal;
			font-weight: 500;
			line-height: 30px;
			color: #28166f;
			margin-left: 0px;
			margin-top: 10px;
		}

	.headingPanelMiddleSearchHeaderCell {
			float: left;
			width: 65px;
			height: 12px;
			margin-right: 5px;
			font-family: Arial, Helvetica, sans-serif;
			font-size: 12px;
			font-style: normal;
			font-weight: 700;
			line-height: 18px;
			color: #aaa;
			text-align: center;
		}

	.headingPanelMiddleSearch{
			float: left;
			height: 60px;
			width: 1100px;
			font-family: myFont;
			font-size: 20px;
			font-style: normal;
			font-weight: bold;
			line-height: 40px;
			color: #d95c00;
			vertical-align: middle;
			margin-left: 0px;
		}

	.headingPanelMiddleSearchCell {
			float: left;
			width: 65px;
			height: 50px;
			margin-right: 5px;
		}

  .headingPanelMiddleSearchSpecialCell {
			float: left;
			width: 150px;
			height: 50px;
			margin-right: 5px;
		}

  .headingPanelMiddleSearchButton {
			float: left;
			width: 100px;
			height: 50px;
			margin-right: 5px;
		}


.prodListContainer {
			float: left;
			height: 100%;
			width: 1100px;
			background-color: #FFF;
		}

		.prodList {
			float: left;
			height: 180px;
			width: 1098px;
			background-color: #00aef0;
			margin-left: 1px;
		}

		.prodListEntry {
			float: left;
			height: 140px;
			width: 200px;
			margin-left: 6px;
			margin-top: 7px;
			margin-right: 3px;
			padding: 4px;
			background-color: #FFF;
			border: solid;
			border-top-width: 1px;
			border-right-width: 1px;
			border-bottom-width: 1px;
			border-left-width: 1px;
		}

    .prodListEntryHeading {
			float: left;
			height: 22px;
			width: 190px;
			background-color: #FFF;
			text-align: center;
			font-family: myFont;
			font-size: 100%;
			font-weight: bold;
		}

    .prodListEntryPrice {
			float: left;
			height: 18px;
			width: 200px;
			text-align: center;
			font-family: myFont;
			font-size: 18px;
			font-weight: normal;
		}

    .numberPlateSpe {
    			font-family: Arial, Helvetica, sans-serif;
    			font-size: 20px;
    			font-weight: 600;
    			text-shadow: 1px 1px 0 #111,
    						  2px 2px 0 #222,
    						  3px 3px 2px #333;
    			color: #fff;
    			background-color:#fce14d;
    			border-radius: 10px;
    			border: 2px solid #666;
    			border-color: #dbc344 #b8a438 #b8a438;
    			box-shadow: 3px 3px 8px #888;
    			width: 180px;
    			height: 50px;
    			text-align: center;
    			line-height: 50px;
    			float: left;
    			margin: 6px;
    			margin-left: 9px;
    			background-image: -webkit-linear-gradient(top, rgba(255, 255, 255, 0.3), rgba(255, 255, 255, 0) 50%, rgba(0, 0, 0, 0.12) 51%, rgba(0, 0, 0, 0.04));
    			background-image: -moz-linear-gradient(top, rgba(255, 255, 255, 0.3), rgba(255, 255, 255, 0) 50%, rgba(0, 0, 0, 0.12) 51%, rgba(0, 0, 0, 0.04));
    			background-image: -o-linear-gradient(top, rgba(255, 255, 255, 0.3), rgba(255, 255, 255, 0) 50%, rgba(0, 0, 0, 0.12) 51%, rgba(0, 0, 0, 0.04));
    			background-image: linear-gradient(to bottom, rgba(255, 255, 255, 0.3), rgba(255, 255, 255, 0) 50%, rgba(0, 0, 0, 0.12) 51%, rgba(0, 0, 0, 0.04));
    		}

    		.numberPlateSpe2 {
    			font-family: Arial, Helvetica, sans-serif;
    			font-size: 20px;
    			font-weight: 600;
    			text-shadow: 1px 1px 0 #111,
    						  2px 2px 0 #222,
    						  3px 3px 2px #333;
    			color: #fff;
    			background-color:#C0C0C0;
    			border-radius: 10px;
    			border: 2px solid #666;
    			border-color: #dbc344 #b8a438 #b8a438;
    			box-shadow: 3px 3px 8px #888;
    			width: 180px;
    			height: 50px;
    			text-align: center;
    			line-height: 50px;
    			float: left;
    			margin: 6px;
    			margin-left: 9px;
    			background-image: -webkit-linear-gradient(top, rgba(255, 255, 255, 0.3), rgba(255, 255, 255, 0) 50%, rgba(0, 0, 0, 0.12) 51%, rgba(0, 0, 0, 0.04));
    			background-image: -moz-linear-gradient(top, rgba(255, 255, 255, 0.3), rgba(255, 255, 255, 0) 50%, rgba(0, 0, 0, 0.12) 51%, rgba(0, 0, 0, 0.04));
    			background-image: -o-linear-gradient(top, rgba(255, 255, 255, 0.3), rgba(255, 255, 255, 0) 50%, rgba(0, 0, 0, 0.12) 51%, rgba(0, 0, 0, 0.04));
    			background-image: linear-gradient(to bottom, rgba(255, 255, 255, 0.3), rgba(255, 255, 255, 0) 50%, rgba(0, 0, 0, 0.12) 51%, rgba(0, 0, 0, 0.04));
    		}

    		.numberPlateSpe3 {
    			font-family: Arial, Helvetica, sans-serif;
    			font-size: 20px;
    			font-weight: 600;
    			text-shadow: 1px 1px 0 #111,
    						  2px 2px 0 #222,
    						  3px 3px 2px #333;
    			color: #fff;
    			background-color:#cd7f32;
    			border-radius: 10px;
    			border: 2px solid #666;
    			border-color: #dbc344 #b8a438 #b8a438;
    			box-shadow: 3px 3px 8px #888;
    			width: 180px;
    			height: 50px;
    			text-align: center;
    			line-height: 50px;
    			float: left;
    			margin: 6px;
    			margin-left: 9px;
    			background-image: -webkit-linear-gradient(top, rgba(255, 255, 255, 0.3), rgba(255, 255, 255, 0) 50%, rgba(0, 0, 0, 0.12) 51%, rgba(0, 0, 0, 0.04));
    			background-image: -moz-linear-gradient(top, rgba(255, 255, 255, 0.3), rgba(255, 255, 255, 0) 50%, rgba(0, 0, 0, 0.12) 51%, rgba(0, 0, 0, 0.04));
    			background-image: -o-linear-gradient(top, rgba(255, 255, 255, 0.3), rgba(255, 255, 255, 0) 50%, rgba(0, 0, 0, 0.12) 51%, rgba(0, 0, 0, 0.04));
    			background-image: linear-gradient(to bottom, rgba(255, 255, 255, 0.3), rgba(255, 255, 255, 0) 50%, rgba(0, 0, 0, 0.12) 51%, rgba(0, 0, 0, 0.04));
    		}

				.modal-content {
					width: 544px;
					height: 144px;
					background-color: #fff;
					/*margin: auto;*/
					padding: 10px;
					border: 1px solid #888;
				}

			/* The Close Button */
			.closeP {
			color: #333;
			float: right;
			font-size: 28px;
			font-weight: bold;
			line-height: 20px;
	}

	.closeP:hover,
	.closeP:focus {
	    color: #000;
	    text-decoration: none;
	    cursor: pointer;
	}

			.topBar {
						margin-left: auto;
						margin-right: auto;
						width: 600px;
						height: 104px;
					}

			.headingLogon {
					width: 500px;
					height: 44px;
					/*margin-left: auto;
					margin-right: auto;*/
				}

		.headingTopBar2 {
				width: 500px;
				height: 4px;
				background-color: #129c94;
			}

			.headingTopBar {
				width: 500px;
				height: 44px;
				background-color: #129c94;
				color: #666;
				font-size: 14px;
				line-height: 16px;
				font-weight: 900;
			}

			.headingTopBarMiddle {
			float: left;
			width: 495px;
			height: 40px;
			text-align: left;
			padding-left: 5px;
		}

		.topHiddenBar {
			width: 500px;
			height: 120px;
			background-color: #129c94;
		}
		.headingLoginPanel{
					float: left;
					width: 475px;
					height: 120px;
					text-align: left;
					line-height: 22px;
					padding-left: 5px;
				}

				.headingLoginUsernameLine {
					float: left;
					width: 500px;
					height: 28px;
					padding-left: 0px;
					margin-top: 10px;
					margin-bottom: 5px;
				}

				.headingLoginUsernamePrompt {
					float: left;
					width: 95px;
					height: 24px;
					text-align: left;
					font-family: myFont;
				  font-size: 14px;
				  font-weight: 700;
					color: #fff;
					text-shadow: 1px 1px 1px #666;
					line-height: 28px;
					padding-left: 0px;
				}

				.headingLoginUsernameInput {
					float: left;
					width: 280px;
					height: 28px;
					text-align: left;
					padding-left: 5px;
				}

				.headingLoginPasswordLine {
					float: left;
					width: 500px;
					height: 28px;
					padding-left: 0px;
				}

				.headingLoginPasswordPrompt {
					float: left;
					width: 95px;
					height: 24px;
					text-align: left;
					font-family: myFont;
				  font-size: 14px;
				  font-weight: 700;
					color: #fff;
					text-shadow: 1px 1px 1px #666;
					line-height: 28px;
				}

				.headingLoginPasswordInput {
					float: left;
					width: 280px;
					height: 28px;
					text-align: left;
					padding-left: 5px;
					padding-right: 10px;
				}

				.headingLoginSubmitButton {
					float: left;
					width: 70px;
					height: 26px;
					text-align: left;
					padding-left: 5px;
				}

				.headingLoginErrorMessage {
					float: left;
					width: 500px;
					height: 20px;
					text-align: left;
					padding-left: 0px;
				   font-size: 18px;
				}

				.modal-profile-content {
					width: 544px;
					height: 300px;
					background-color: #fff;
					/*margin: auto;*/
					padding: 10px;
					border: 1px solid #888;
				}

				.profileTopBar2 {
				width: 500px;
				height: 4px;
				background-color: #129c94;
				}

				.profileTopBar {
				width: 500px;
				height: 44px;
				background-color: #129c94;
				color: #666;
				font-size: 14px;
				line-height: 16px;
				font-weight: 900;
				}

				.profileTopBarMiddle {
				float: left;
				width: 495px;
				height: 40px;
				text-align: left;
				padding-left: 5px;
				}

				.profileTopHiddenBar {
				width: 500px;
				height: 280px;
				background-color: #129c94;
				}

				.profileEntryPanel{
					float: left;
					width: 475px;
					height: 280px;
					text-align: left;
					line-height: 22px;
					padding-left: 5px;
				}

				.profileEntryLine {
					float: left;
					width: 500px;
					height: 28px;
					padding-left: 0px;
					margin-top: 10px;
					margin-bottom: 5px;
				}

				.profileEntryPrompt {
					float: left;
					width: 180px;
					height: 24px;
					text-align: left;
					font-family: myFont;
					font-size: 14px;
					font-weight: 700;
					color: #fff;
					text-shadow: 1px 1px 1px #666;
					line-height: 28px;
					padding-left: 0px;
				}

				.profileEntryInput {
					float: left;
					width: 280px;
					height: 28px;
					text-align: left;
					padding-left: 5px;
				}

				.profileEntryPasswordLine {
					float: left;
					width: 500px;
					height: 28px;
					padding-left: 0px;
				}

				.profileEntryPasswordPrompt {
					float: left;
					width: 180px;
					height: 24px;
					text-align: left;
					font-family: myFont;
					font-size: 14px;
					font-weight: 700;
					color: #fff;
					text-shadow: 1px 1px 1px #666;
					line-height: 28px;
				}

				.profileEntryPasswordInput {
					float: left;
					width: 280px;
					height: 28px;
					text-align: left;
					padding-left: 5px;
					padding-right: 10px;
				}

				.profileEntrySubmitButton {
					float: left;
					width: 70px;
					height: 26px;
					text-align: left;
					padding-left: 5px;
				}

				.profileEntryErrorMessage {
					float: left;
					width: 500px;
					height: 20px;
					text-align: left;
					padding-left: 0px;
					 font-size: 18px;
				}

				modal-profile-content  {
				width: 600px;
				height: 600px;
				background-color: #fff;
				/*margin: auto;*/
				padding: 10px;
				border: 1px solid #888;
			}

</style>

<script>

function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

function registerProfile()
{
	err = document.getElementById('profileError');
	err.innerHTML="";

	var email = document.getElementById('email-input');
	var contact = document.getElementById('contact-input');
	var password = document.getElementById('password-input');
	var vpassword = document.getElementById('vpassword-input');
	var phone = document.getElementById('phone-input');

	if (password.value != vpassword.value)
	{
			err.appendChild(document.createTextNode('Password/verify password should match'));
			return;
	}

	var jsonData = {};
	jsonData['email'] = email.value;
	jsonData['contact'] = contact.value;
	jsonData['phone'] = phone.value;
	jsonData['password'] = password.value;

	access_token = sessionStorage.getItem("access_token");
	var bearerHeader = 'Bearer ' + access_token;


	$.ajax({

		type: "POST",
			 url : "/platez/api/anon/register",
			 cache: false,
			 contentType: 'application/json;',
			 dataType: "json",
			 data:JSON.stringify(jsonData),
				success: function(data) {
					var result = $.parseJSON(JSON.stringify(data));

					if (result.status != 'OK')
					{
						err = document.getElementById('profileError');
						err.appendChild(document.createTextNode(result.message));
						return;
					}
					err = document.getElementById('profileError');
					err.appendChild(document.createTextNode(result.result));
		}
	});
}


function setUpModal()
{
	var modalP = document.getElementById('myModalP');
	var btnP = document.getElementById("modalOpener");
	var spanP = document.getElementsByClassName("closeP")[0];
// When the user clicks the button, open the modal

	refreshOn = false;
	modalP.style.display = "none";

	btnP.onclick = function() {
		refreshOn = false;
		modalP.style.display = "block";
		return false;
	}

// When the user clicks on <span> (x), close the modal
	spanP.onclick = function() {
		refreshOn = true;
		modalP.style.display = "none";
		return false;
	}

// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
		if (event.target == modalP) {
				refreshOn = true;
				modalP.style.display = "none";
		}
	}
}

function setUpModalProfile()
{
	$(document).ready(function(){
		$("#register").click(function(){
				$("#modalProfile").modal();
		});
	});
}


function login() {


 	var email = document.getElementById('email');
  var password = document.getElementById('password');

	var jsonData = {};
	  jsonData['username'] = email.value;
	  jsonData['password'] = password.value;

	$.ajax({

     type: "POST",
        url : "/platez/api/a/authorize1",
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
           if (authorized.role=='ROLE_AGENT')
              window.location.replace("/platez/web/anon/goAgentHome");
            else
						if (authorized.role=='ROLE_AUCTIONEER')
							 window.location.replace("/platez/web/anon/goActioneerHome");
            else
						if (authorized.role=='ROLE_PUNTER')
              	window.location.replace("/platez/web/anon/goPunterHome");
        }
     });
 }

function doClearQuery()
{
  getAllPlates();
  getQueryParams();
}

function doSpecialQuery()
{
  var special = document.getElementById('special');
  var jsonData = {};
  // alert("Cot special : " + special.value);
  jsonData['combo'] = special.value;

  queryPlates(jsonData,'/platez/api/anon/queryDigits');
}

function doParamQuery()
{
  var prefix = document.getElementById('prefix');
  var letter1 = document.getElementById('letter1');
  var letter2 = document.getElementById('letter2');
  var number1 = document.getElementById('number1');
  var number2 = document.getElementById('number2');
  var number3 = document.getElementById('number3');
  var number4 = document.getElementById('number4');
  var suffix = document.getElementById('suffix');

  var jsonData = {};
//  alert(prefix.value+letter1.value+letter2.value+number1.value+number2.value
//        +number3.value+number4.value+suffix.value);

  jsonData['prefix'] = prefix.value;
  jsonData['letter1'] = letter1.value;
  jsonData['letter2'] = letter2.value;
  jsonData['number1'] = number1.value;
  jsonData['number2'] = number2.value;
  jsonData['number3'] = number3.value;
  jsonData['number4'] = number4.value;
  jsonData['suffix'] = suffix.value;

  queryPlates(jsonData,'/platez/api/anon/queryPlate');
}

var plates;

function queryPlates(jsonData,targetUrl) {

     $.ajax({

    type: "POST",
        url : targetUrl,
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
              alert(result.message);
              return;
            }
            plates = result.result;
          //  alert('Plates # ' + plates.length);
            displayPlates();
          }
      })
}


function getAllPlates() {

     $.ajax({

    type: "GET",
         url : '/platez/api/anon/getAllPlates',
    cache: false,
 	 contentType: 'application/json;',
         dataType: "json",
           success: function(data) {
        //    alert(JSON.stringify(data));
	          if (data == '')
            {
               return;
            }

     	      var result = $.parseJSON(JSON.stringify(data));
            if (result.status != 'OK')
            {
              alert(result.message);
              return;
            }
            plates = result.result;
      //      alert('Plates # ' + plates.length);
            displayPlates();
          }
      })
}

var queryParams;

function getQueryParams() {

     $.ajax({

    type: "GET",
         url : '/platez/api/anon/getQueryParams',
    cache: false,
 	 contentType: 'application/json;',
         dataType: "json",
        // data: {key:key },
         success: function(data) {
        //    alert(JSON.stringify(data));
	          if (data == '')
            {
               return;
            }
	  //  jdata = JSON.parse(data)

     	      var result = $.parseJSON(JSON.stringify(data));
            if (result.status != 'OK')
            {
              alert(result.message);
              return;
            }
            queryParams = result.result;
            displayQueryParams();
          }
      })
}

function displayQueryParams()
{
    var pf = document.getElementById('prefix');
    queryParams.prefix.forEach((choice,i) => {
      var option = createOption(choice);
      pf.appendChild(option);
    });
    var l1 = document.getElementById('letter1');
    queryParams.letter1.forEach((choice,i) => {
      var option = createOption(choice);
      l1.appendChild(option);
    });
    var l2 = document.getElementById('letter2');
    queryParams.letter2.forEach((choice,i) => {
      var option = createOption(choice);
      l2.appendChild(option);
    });
    var n1 = document.getElementById('number1');
    queryParams.number1.forEach((choice,i) => {
      var option = createOption(choice);
      n1.appendChild(option);
    });
    var n2 = document.getElementById('number2');
    queryParams.number2.forEach((choice,i) => {
      var option = createOption(choice);
      n2.appendChild(option);
    });
    var n3 = document.getElementById('number3');
    queryParams.number3.forEach((choice,i) => {
      var option = createOption(choice);
      n3.appendChild(option);
    });
    var n4 = document.getElementById('number4');
    queryParams.number4.forEach((choice,i) => {
      var option = createOption(choice);
      n4.appendChild(option);
    });
    var sf = document.getElementById('suffix');
    queryParams.suffix.forEach((choice,i) => {
      var option = createOption(choice);
      sf.appendChild(option);
    });
    var sp = document.getElementById('special');
    queryParams.specials.forEach((choice,i) => {
      var option = document.createElement('option');
      option.value = choice[1];
      option.text = choice[0];
      sp.appendChild(option);
    });
}

function createOption(choice)
{
  var option = document.createElement('option');
  option.value = choice;
  option.text = choice;
  return option;
}

function displayPlates()
{
    document.getElementById('pc').innerHTML="";

    plates.forEach((plate, i) => {
    var pe = document.createElement('div');
    pe.className = 'prodListEntry';

    var peh = document.createElement('div');
    peh.className = 'prodListEntryHeading';

    if (plate.rating==1)
    {
      var np = document.createElement('div');
      np.className = 'numberPlateSpe';
      np.appendChild(document.createTextNode(plate.regNo));
      peh.appendChild(np);
    }
    else
    if (plate.rating==2)
    {
        var np = document.createElement('div');
        np.className = 'numberPlateSpe2';
        np.appendChild(document.createTextNode(plate.regNo));
        peh.appendChild(np);
    }
    else {
      var np = document.createElement('div');
      np.className = 'numberPlateSpe3';
      np.appendChild(document.createTextNode(plate.regNo));
      peh.appendChild(np);
    }

    pe.appendChild(peh);
    var pp = document.createElement('div');
    pp.className = 'prodListEntryPrice';
    var price = numberWithCommas(plate.listPrice);
    pp.appendChild(document.createTextNode('RM'+price));
    peh.appendChild(pp);

    if (!(i % 5)) {
        pl = document.createElement('div');
        pl.className = 'prodList'
        document.getElementById('pc').appendChild(pl);
    }

    pl.appendChild(pe);

    });
}

</script>

<html>
<body>
	<input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
	<div class="headingPanelLogonHeader">
		<div class="headingPanelLogonHeaderCell">
			Already a member?&nbsp&nbsp<a id='modalOpener' href="#">Sign in</a>
		</div>
		<div class="headingPanelLogonHeaderCell">
			<a id='register' href="#">Register to become a Zen Recruiter</a>
		</div>
	</div>

	<div id="modalProfile" class="modal fade" role="dialog">
		<div class="modal-dialog" style="float:left;width=1200px;height:600px;">
				<div class="modal-profile-content ">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title" id="pm-title" style="width=1200px">Profile Details</h4>
						</div>
						<div class="modal-body" style="width=1200px">
							<div class="profileEntryPanel">
								<div class="profileEntryLine">
									<div class="profileEntryPrompt">
										Unique Zen Contact:
									</div>
									<div class="profileEntryInput">
											<input id="contact-input" type="text"
																	value="" style="height: 26px; width: 240px; font-size: 14px; "/>
									</div>
								</div>
								<div class="profileEntryPasswordLine">
									<div class="profileEntryPasswordPrompt">
													Password:
									</div>
									<div class="profileEntryPasswordInput">
												<input id="password-input" type="password"
													 style="height: 26px; width: 240px; font-size: 14px; "/>
									</div>
								</div>
								<div class="profileEntryPasswordLine">
									<div class="profileEntryPasswordPrompt">
													Verify Password:
									</div>
									<div class="profileEntryPasswordInput">
												<input id="vpassword-input" type="password"
													 style="height: 26px; width: 240px; font-size: 14px; "/>
									</div>
								</div>
								<div class="profileEntryLine">
									<div class="profileEntryPrompt">
										Email:
									</div>
									<div class="profileEntryInput">
											<input id="email-input" type="text"
																	value="" style="height: 26px; width: 240px; font-size: 14px; "/>
									</div>
								</div>
								<div class="profileEntryLine">
									<div class="profileEntryPrompt">
										Phone:
									</div>
									<div class="profileEntryInput">
											<input id="phone-input" type="text"
																	value="" style="height: 26px; width: 240px; font-size: 14px; "/>
									</div>
								</div>
								<div class="profileEntrySubmitButton">
										<input type="button" id="profile" value="Save Profile"
											onClick="return registerProfile();"
											class="btn btn-primary"
											style="height: 26px; line-height:0px; font-weight:700; text-shadow:1px 1px 1px #666;" />
								</div>
							</br>
								<div class="profileEntryErrorMessage" id='profileError'>
								</div>
							</div>  <!-- profileEntryPanel -->
						</div>
				</div>
			</div>
	</div>

<script>
	setUpModalProfile();
</script>
</html>
