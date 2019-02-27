<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page trimDirectiveWhitespaces="true" %>

<head>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


	<link rel="stylesheet" href="../../css/font-awesome.css">
	<link rel="stylesheet" href="../../css/bootstrap.min.css" />
	<link rel="stylesheet" href="../../css/style.css" />

<title>zen</title>

</head>

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

function registerProfile()
{
	err = document.getElementById('profileError');
	err.innerHTML="";

	var email = document.getElementById('email-input');
	var contact = document.getElementById('contact-input');
	var password = document.getElementById('password-input');
	var vpassword = document.getElementById('vpassword-input');
	var phone = document.getElementById('phone-input');
	var sponsorContactId = document.getElementById('sponsorContactId-input');

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
			 url : "/zen/api/anon/register",
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
	<div id="myModalP" class="modal">
		<div class="modal-content">
			<span class="closeP">&times;</span>
				<div id="#129c94r2P" class="topHiddenBar">
						<div class="headingLoginPanel">
							<div class="headingLoginUsernameLine">
								<div class="headingLoginUsernamePrompt">
									Email:
								</div>
								<div class="headingLoginUsernameInput">
										<input id="email" type="text"
																value="koko@test.com" style="height: 26px; width: 240px; font-size: 14px; "/>
								</div>
							</div>
							<div class="headingLoginPasswordLine">
								<div class="headingLoginPasswordPrompt">
												Password:
								</div>
								<div class="headingLoginPasswordInput">
											<input id="password" type="password"
																								value="Coco2828" style="height: 26px; width: 240px; font-size: 14px; "/>
								</div>
							</div>
							<div class="headingLoginSubmitButton">
									<input type="button" id="logon" value="Logon"
										onClick="return login()"
										class="btn btn-primary"
										style="height: 26px; line-height:0px; font-weight:700; text-shadow:1px 1px 1px #666;" />
							</div>
							<div class="headingLoginErrorMessage">
							</div>
						</div>
					</div>
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
	setUpModal();
	setUpModalProfile();
</script>
</html>
