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


var punter;

function getPunterTree() {

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

          getModel();
        },
				error:function (e) {
	  			alert("getPunter ERROR : " + e.status + " - " + e.statusText);
	      }
     });
 }


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

			$('#treeD').tree({
						dataSource: dText,
						imageUrlField: 'imageUrl',
						primaryKey: 'id'
				});
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
      <h2>My Downline</h2>
    </div>

    <div class="row-fluid">
      <div class="span12">
        <div style="float:left;width:1000px;" id="treeD"></div>
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

<script src="../../../js/core.js" type="text/javascript"></script>
<script src="../../../js/tree.js" type="text/javascript">></script>


<script type="text/javascript">

getPunterTree();

</script>
</body>
</html>
