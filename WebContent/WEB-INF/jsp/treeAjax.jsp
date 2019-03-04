<html>
<head>
<title>treeAjax</title>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>

<!--
<link rel="stylesheet" href="../../css/overlaystyle.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/foundation/6.4.1/css/foundation.min.css">
<link rel="stylesheet" href="../../css/bootstrap.min.css" />
<link rel="stylesheet" href="../../css/style.css" />
-->

<link href="../../../css/core.css" rel="stylesheet" type="text/css">
<link href="../../../css/tree.css" rel="stylesheet" type="text/css">
<script src="../../../js/core.js" type="text/javascript"></script>
<script src="../../../js/tree.js" type="text/javascript">></script>

<style>

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

.topHiddenBar {
	width: 500px;
	height: 120px;
	background-color: #129c94;
}

</style>

</head>

<script type="text/javascript">

var refreshOn = false;

function setUpModal()
{
	var modalP = document.getElementById('myModalP');
	var spanP = document.getElementsByClassName("closeP")[0];
// When the user clicks the button, open the modal

	refreshOn = false;
	modalP.style.display = "none";

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

function getPunterDetails(email)
{
	alert("In with : " + email);
	$.ajax({
	type: "POST",
	 url : "/zen/zx4/api/anon/getModel",
	 cache: false,
	 contentType: 'application/json;',
	 dataType: "json",
		success: function(data) {
			var result = $.parseJSON(JSON.stringify(data));
			if (result.status != 'OK')
			{
				alert('Error : ' + result.message);
				return;
			}

			var punterDetails = result.result;
			email = document.getElementById("email-input");
			email.innerHtml = "";
			email.appendChild(document.createTextNode(punterDetails.email));
		}
	});
	refreshOn = false;
	modalP.style.display = "block";
	return false;
}

function getModel()
{
	$.ajax({
	type: "POST",
	 url : "/zen/zx4/api/anon/getModel?contact=zen",
	 cache: false,
	 contentType: 'application/json;',
	 dataType: "json",
		success: function(data) {
			var result = $.parseJSON(JSON.stringify(data));
			if (result.status != 'OK')
			{
				alert('Error : ' + result.message);
				return;
			}
		//	alert('Ok : + ' + JSON.stringify(result.result));

			var model = result.result;
			pop = document.getElementById("population");
			pop.innerHtml = "";
			pop.appendChild(document.createTextNode("Population: " + model.population));

			rev = document.getElementById("revenue");
			rev.innerHtml = "";
			rev.appendChild(document.createTextNode("Top Revenue: RM" + model.topRevenue));

			rev = document.getElementById("sysRevenue");
			rev.innerHtml = "";
			rev.appendChild(document.createTextNode("System Revenue: RM" + model.systemOwnedRevenue));

			var dText = model.punters;

			$('#tree').tree({
						dataSource: dText,
						imageUrlField: 'imageUrl',
						primaryKey: 'id'
				});
		}
	});
}
</script>

<body style="padding: 6px;">

<div class="container-fluid">
	<div style="float:left;width:1000px;">
		<div style="float:left;width:200px;" id="population"></div>
		<div style="float:left;width:300px;" id="revenue"></div>
		<div style="float:left;width:300px;" id="sysRevenue"></div>
		<div style="float:left;width:200px;"><img src="../../../img/GoldenCircle_02.png" width="100px" height="100px"/></div>
	</div>
	<div style="float:left;width:1000px;" id="tree"></div>
</div>

<div id="myModalP" class="modal fade" role="dialog">
	<div class="modal-dialog" style="width=520px">
			<div class="modal-content" style="width=520px">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title" style="width=520px">Punter Profile</h4>
					</div>
					<div class="modal-body" style="width=520px">
						<span class="closeP">&times;</span>
						<div id="#129c94r2P" class="topHiddenBar">
							<div class="profileEntryPanel">
								<div class="profileEntryLine">
									<div class="profileEntryPrompt">
										Email:
									</div>
									<div class="profileEntryInput">
											<input id="email-input" type="text" readonly
															readonly value="" style="height: 26px; width: 240px; font-size: 14px; "/>
									</div>
								</div>
								<div class="profileEntryLine">
									<div class="profileEntryPrompt">
										Contact:
									</div>
									<div class="profileEntryInput">
											<input id="contact-input" type="text"
													readonly value="" style="height: 26px; width: 240px; font-size: 14px; "/>
									</div>
								</div>
								<div class="profileEntryLine">
									<div class="profileEntryPrompt">
										Phone:
									</div>
									<div class="profileEntryInput">
											<input id="phone-input" type="text"
													readonly value="" style="height: 26px; width: 240px; font-size: 14px; "/>
									</div>
								</div>
							</div>  <!-- profileEntryPanel -->
						</div>  <!-- topHiddenBar -->
					</div>
			</div>
		</div>
</div>  <!-- modalP  -->
<script>
	setUpModal();
	getModel();
</script>
</body>
</html>
