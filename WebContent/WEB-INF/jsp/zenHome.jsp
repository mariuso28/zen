<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page trimDirectiveWhitespaces="true" %>

<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

	<link rel="stylesheet" href="../../css/font-awesome.css">
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
					height: 400px;
					background-color: #a2dbd7;
					/*margin: auto;*/
					padding: 10px;
					border: 1px solid #888;
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
					color: #356aea;
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
					color: #356aea;
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
					width: 160px;
					height: 30px;
					text-align: left;
					padding-left: 5px;
					padding-top: 15px;
				}

				.profileEntryErrorMessage {
					float: left;
					width: 500px;
					height: 20px;
					text-align: left;
					padding-left: 0px;
					padding-top: 20px;
					 font-size: 18px;
				}

				.signinEntryPanel{
					float: left;
					width: 475px;
					height: 120px;
					text-align: left;
					line-height: 22px;
					padding-left: 5px;
				}

				.modal-signin-content {
					width: 544px;
					height: 160px;
					background-color: #a2dbd7;
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
	alert('IN');

	err = document.getElementById('profileError');
	err.innerHTML="";

	var email = document.getElementById('email-input');
	var contact = document.getElementById('contact-input');
	var password = document.getElementById('password-input');
	var vpassword = document.getElementById('vpassword-input');
	var phone = document.getElementById('phone-input');
	var sponsorContactId = document.getElementById('sponsorContactId-input');

	if (email.value=='' || contact.value=='' || password.value=='' || vpassword.value=='' || phone.value=='' || sponsorContactId=='')
	{
			err.appendChild(document.createTextNode('Please key in all fields'));
			return;
	}

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
	jsonData['sponsorContactId'] = sponsorContactId.value;

	access_token = sessionStorage.getItem("access_token");
	var bearerHeader = 'Bearer ' + access_token;

	$.ajax({

		type: "POST",
			 url : "/zen/zx4/api/anon/register",
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

function setUpModalSignin()
{
	$(document).ready(function(){
		$("#signin").click(function(){
				$("#modalSignin").modal();
		});
	});
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
        url : "/zen/api/a/authorize1",
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
              	window.location.replace("/zen/web/anon/goPunterHome");
        }
     });
 }


</script>

<html>
<body>
	<input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
	<div class="headingPanelLogonHeader">
		<div class="headingPanelLogonHeaderCell">
			Already a member?&nbsp&nbsp<a id='signin' href="#">Sign in</a>
		</div>
		<div class="headingPanelLogonHeaderCell">
			<a id='register' href="#">Register to become a Zen Recruiter</a>
		</div>
	</div>

	<div id="modalProfile" class="modal fade" role="dialog">
		<div class="modal-dialog" style="float:left;width=1200px;height:700px;">
				<div class="modal-profile-content">
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
								<div class="profileEntryLine">
									<div class="profileEntryPrompt">
										Sponsor Contact Id:
									</div>
									<div class="profileEntryInput">
											<input id="sponsorContactId-input" type="text"
																	value="" style="height: 26px; width: 240px; font-size: 14px; "/>
									</div>
								</div>

								<div class="profileEntrySubmitButton">
									<input id="registerBtn" value="Register"
										type="button"
										style="height:26px; width:140px; white-space:normal; font-family:inherit; font-size:large;
																			color:#666; background-color:#fff; font-weight:bold;"
										onClick="return registerProfile();"
									/>
								</div>

								<div class="profileEntryErrorMessage" id='profileError'>
								</div>
							</div>  <!-- profileEntryPanel -->
						</div>
				</div>
		</div>
</div>
<div id="modalLogon" class="modal fade" role="dialog">
		<div class="modal-dialog" style="float:left;width=800px;height:200px;">
				<div class="modal-signin-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title" id="si-title" style="width=800px">Zen Sign In</h4>
						</div>
						<div class="modal-body" style="width=800px">
							<div class="profileSigninPanel">
								<div class="profileEntryLine">
									<div class="profileEntryPrompt">
										Zen Contact Name:
									</div>
									<div class="profileEntryInput">
											<input id="signin-contact-input" type="text"
																	value="" style="height: 26px; width: 240px; font-size: 14px; "/>
									</div>
								</div>
								<div class="profileEntryPasswordLine">
									<div class="profileEntryPasswordPrompt">
													Password:
									</div>
									<div class="profileEntryPasswordInput">
												<input id="signin-password-input" type="password"
													 style="height: 26px; width: 240px; font-size: 14px; "/>
									</div>
								<div class="profileEntrySubmitButton">
									<input id="sigin" value="Sign In"
										type="button"
										style="height:26px; width:140px; white-space:normal; font-family:inherit; font-size:large;
																			color:#666; background-color:#fff; font-weight:bold;"
										onClick="return sigin();"
									/>
								</div>

								<div class="profileEntryErrorMessage" id='siginError'>
								</div>
							</div>  <!-- profileEntryPanel -->
						</div>
				</div>
			</div>
	</div>
</div>


<script>
	setUpModalProfile();
	setUpModalSignin();
</script>
</html>
