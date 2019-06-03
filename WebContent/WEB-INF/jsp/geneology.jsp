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

var labels;

function getLabels()
{
  $.ajax({

 type: "GET",
      url : '/zen/zx4/api/anon/getLabels?jsp=geneology',
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

var access_token;
var punter1;
var ids;

function getPunterDetails(contact)
{
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
						alert("Error " + resultJson.status + " " + resultJson.message);
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
    document.getElementById('fullName').value = punter1.fullName;
    if (!punter.systemOwned)
      if (punter.contact != punter1.contact && punter.contact != punter1.sponsorContact)
      {
        document.getElementById('systemOnly').innerHTML = "";
        displayLabels();
        return;
      }

      document.getElementById('systemOnly').innerHTML =
      '<div class="control-group">' +
        '<label class="control-label" id="passportIcLabel"></label>' +
        '<div class="controls">' +
        '<input type="text" id="passportIc" class="span11" value="" readonly/>' +
        '</div>' +
        '</div>' +
        '<div class="control-group">' +
        '<label class="control-label" id="genderLabel"></label>' +
        '<div class="controls">' +
            '<input type="text" id="gender" class="span11" value="" readonly/>' +
        '</div>' +
      '</div>' +
      '<div class="control-group">' +
        '<label class="control-label" id="addressLabel"></label>' +
        '<div class="controls">' +
          '<input type="text" id="address" class="span11" value="" readonly/>' +
        '</div>' +
      '</div>' +
      '<div class="control-group">' +
        '<label class="control-label" id="postCodeLabel"></label>' +
        '<div class="controls">' +
          '<input type="text" id="postcode" class="span11" value="" readonly/>' +
        '</div>' +
      '</div>' +
      '<div class="control-group">' +
        '<label class="control-label" id="stateLabel"></label>' +
        '<div class="controls">' +
          '<input type="text" id="state" class="span11" value="" readonly/>' +
        '</div>' +
      '</div>' +
      '<div class="control-group">' +
        '<label class="control-label" id="countryLabel"></label>' +
        '<div class="controls">' +
          '<input type="text" id="country" class="span11" value="" readonly/>' +
        '</div>' +
      '</div>' +
      '<div class="control-group">' +
        '<label class="control-label" id="phoneLabel"></label>' +
        '<div class="controls">' +
          '<input type="text" id="phone" class="span11" value="" readonly/>' +
        '</div>' +
        '<div class="control-group">' +
          '<label class="control-label" id="emailLabel"></label>' +
          '<div class="controls">' +
            '<input type="text" id="email" class="span11" value="" readonly/>' +
          '</div>' +
        '</div>' +
      '</div>';

    document.getElementById('phone').value = punter1.phone;
    document.getElementById('email').value = punter1.email;
    document.getElementById('passportIc').value = punter1.passportIc;
    document.getElementById('address').value = punter1.address;
    document.getElementById('country').value = punter1.country;
    document.getElementById('state').value = punter1.state;
    document.getElementById('gender').value = punter1.gender;

    displayLabels();
}

var punter;

function getPunterTree(searchTerm) {

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

          getLabels();
          getModel();

          punter1 = punter;
          populatePunter1();


        },
				error:function (e) {
	  			alert("getPunter ERROR : " + e.status + " - " + e.statusText);
	      }
     });
 }

var tree;

function getModel()
{
  	tree = $('#tree').tree({
						dataSource: "/zen/zx4/api/punter/getModel?access_token="+access_token,
						imageUrlField: 'imageUrl',
						primaryKey: 'id',
            lazyLoading: true,
            width: 500
				});

}

function searchModelByContact()
{
  var bearerHeader = 'Bearer ' + access_token;
     $.ajax({
    type: "GET",
         url : '/zen/zx4/api/punter/searchModelByContact?searchTerm=' + searchTerm,
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
          ids = resultJson.result;
					expandModelToContact();
        },
				error:function (e) {
	  			alert("getPunter ERROR : " + e.status + " - " + e.statusText);
	      }
     });

}

function expandModelToContact()
{
  tree.collapseAll();
  for (i=0; i<ids.length; i++)
  {
    n1 = tree.getNodeById(ids[i]);
    tree.expand(n1);
  }
  getPunterDetails(ids[ids.length-1]);
}

/*
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

*/

var searchTerm = "";

function Search() {

  searchTerm = $('#query').val();
  searchModelByContact();
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
      <h2 id="myDownlineLabel">My Downline</h2>
    </div>

    <div style="overflow-x: auto">
      <div style="float:left;" id="tree"></div>
    </div>
</div>

<!--end-main-container-part-->

<!-- Profile display -->
<div class="row-fluid">

        <div class="span12">
          <input type="text" id="query" /> <button onclick="Search()" id="searchLabel">Search</button>
        </div>

          <div class="widget-box">
            <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
              <h3 id="agentInformationLabel">Agent Information</h3>
            </div>
            <div class="widget-content nopadding">
              <div class="form-horizontal">
                <div class="control-group">
                  <label class="control-label" id="usernameLabel"></label>
                  <div class="controls">
                    <input readonly type="text" id="contact" class="span11" value="" readonly/>
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label" id="zenSponsorLabel"></label>
                  <div class="controls">
                    <input readonly type="text" id="sponsorContact" class="span11" value="" readonly/>
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label" id="fullNameLabel"></label>
                  <div class="controls">
                    <input type="text" id="fullName" class="span11" value="" readonly/>
                  </div>
                </div>
                <div id="systemOnly">
  <!--
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
               <!-- systemOnly -->
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

access_token = sessionStorage.getItem("access_token");

getPunterTree("${map['contact']}");

</script>


</body>
</html>
