<!DOCTYPE html>
<html lang="en">
<head>
<title>geneology</title>


<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>

<link href="../../../css/core.css" rel="stylesheet" type="text/css">
<link href="../../../css/tree.css" rel="stylesheet" type="text/css">

<link rel="stylesheet" href="../../../css/bootstrap.min.css" />
<link rel="stylesheet" href="../../../css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="../../../css/fullcalendar.css" />
<link rel="stylesheet" href="../../../css/matrix-style.css" />
<link rel="stylesheet" href="../../../css/matrix-media.css" />
<link rel="stylesheet" href="../../../css/jquery.gritter.css" />
<link href='http:/fonts.googleapis.com/css?family=Open+Sans:400,700,800' rel='stylesheet' type='text/css'>


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

var punter1;

function getPunterDetails(contact)
{
  access_token = sessionStorage.getItem("access_token");
  var bearerHeader = 'Bearer ' + access_token;
     $.ajax({
    type: "GET",
         url : '/zen/zx4/api/punter/getPunterByContact?contact=' + contact,
    headers: { 'Authorization': bearerHeader },
    cache: false,
    contentType: 'application/json;',
         dataType: "json",
       	 success: function(data) {
  //         alert(JSON.stringify(data));
     			if (data == '')
            {
							alert("could not get punter by contact")
               return null;
            }
          var resultJson = $.parseJSON(JSON.stringify(data));
					if (resultJson.status!='OK')
					{
						alert(resultJson.status + " " + resultJson.message);
					}
    //      alert(JSON.stringify(resultJson.result));
					punter1 = resultJson.result;
          populatePunter1();
        },
				error:function (e) {
	  			alert("getPunter ERROR : " + e.status + " - " + e.statusText);
	      }
     });

}

function populatePunter1()
{
    document.getElementById('contact').value = punter1.contact;
    document.getElementById('sponsorContact').value = punter1.sponsorContact;
    document.getElementById('phone').value = punter1.phone;
    document.getElementById('email').value = punter1.email;
    document.getElementById('fullName').value = punter1.fullName;
    document.getElementById('passportIc').value = punter1.passportIc;
    document.getElementById('address').value = punter1.address;
    document.getElementById('country').value = punter1.country;
    document.getElementById('state').value = punter1.state;
    document.getElementById('gender').value = punter1.gender;
}

var punter;

function getPunterTree(searchTerm) {

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

          getModel(searchTerm);

          punter1 = punter;
          populatePunter1();

          if (searchTerm != "")
          {
              expand(searchTerm);
              getPunterDetails(searchTerm);
      		}
        },
				error:function (e) {
	  			alert("getPunter ERROR : " + e.status + " - " + e.statusText);
	      }
     });
 }

var tree;

function getModel()
{
	$.ajax({
	type: "POST",
	 url : "/zen/zx4/api/anon/getModel?contact="+punter.contact,
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

			var dText = model.punters;

			tree = $('#tree').tree({
						dataSource: dText,
						imageUrlField: 'imageUrl',
						primaryKey: 'id'
				});
    }
	});
}

function expand(id)
{
  n1 = tree.getNodeById(id);
  tree.expand(n1);
  while (true)
  {
    var data = tree.getDataById(id);
    if (data.parentId == null)
      break;
    nn = tree.getNodeById(data.parentId);
    tree.expand(nn);
    id = data.parentId;
  }
}

function collapse(id)
{
  nn = tree.getNodeById(id);
  tree.collapse(nn);
  while (true)
  {
    var data = tree.getDataById(id);
    if (data.parentId == null)
      break;
    nn = tree.getNodeById(data.parentId);
    tree.collapse(nn);
    id = data.parentId;
  }
}

var searchTerm = "";

function Search() {
//  alert("In : " + $('#query').val());
  if (searchTerm != "")
    collapse(searchTerm);
  searchTerm = $('#query').val();
  expand(searchTerm);
  getPunterDetails(searchTerm);
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
      <h2>My Downline</h2>
    </div>

    <div class="row-fluid">
      <div class="span12">
        <div style="float:left;width:1000px;" id="tree"></div>
      </div>
    </div>
</div>

<!--end-main-container-part-->

<!-- Profile display -->
<div class="row-fluid">

        <div class="span12">
          <input type="text" id="query" /> <button onclick="Search()">Search</button>
        </div>

          <div class="widget-box">
            <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
              <h3>Agent Information</h3>
            </div>
            <div class="widget-content nopadding">
              <div class="form-horizontal">
                <div class="control-group">
                  <label class="control-label">Username :</label>
                  <div class="controls">
                    <input readonly type="text" id="contact" class="span11" value="" readonly/>
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label">Zen Sponser&nbsp</label>
                  <div class="controls">
                    <input readonly type="text" id="sponsorContact" class="span11" value="" readonly/>
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label">Full Name&nbsp</label>
                  <div class="controls">
                    <input type="text" id="fullName" class="span11" value="" readonly/>
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label">Passport/ID No.&nbsp</label>
                  <div class="controls">
                    <input type="text" id="passportIc" class="span11" value="" readonly/>
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label">Gender&nbsp</label>
                  <div class="controls">
                      <input type="text" id="gender" class="span11" value="" readonly/>
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label">Address&nbsp</label>
                  <div class="controls">
                    <input type="text" id="address" class="span11" value="" readonly/>
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label">Postcode&nbsp</label>
                  <div class="controls">
                    <input type="text" id="postcode" class="span11" value="" readonly/>
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label">State&nbsp</label>
                  <div class="controls">
                    <input type="text" id="state" class="span11" value="" readonly/>
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label">Country&nbsp</label>
                  <div class="controls">
                    <input type="text" id="country" class="span11" value="" readonly/>
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label">Phone No.&nbsp</label>
                  <div class="controls">
                    <input type="text" id="phone" class="span11" value="" readonly/>
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label">Email&nbsp</label>
                  <div class="controls">
                    <input type="text" id="email" class="span11" value="" readonly/>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
</div>

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

<script src="../../../js/core.js" type="text/javascript"></script>
<script src="../../../js/tree.js" type="text/javascript">></script>

<script type="text/javascript">

$.ajaxSetup({
   async: false
});

getPunterTree("${map['contact']}");

</script>


</body>
</html>
