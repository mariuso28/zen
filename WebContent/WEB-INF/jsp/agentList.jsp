<!DOCTYPE html>
<html lang="en">
<head>
<title>agentList</title>
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

    .table th {
      font-size: 14px;
    }

  </style>

</head>

<script>

var punterList;

function getDownstreamPunters() {

	access_token = sessionStorage.getItem("access_token");
//	alert(access_token);

  var bearerHeader = 'Bearer ' + access_token;
     $.ajax({

    type: "GET",
         url : '/zen/zx4/api/punter/getDownstreamPunters',
    headers: { 'Authorization': bearerHeader },
    cache: false,
    contentType: 'application/json;',
         dataType: "json",
       	 success: function(data) {
  //         alert(JSON.stringify(data));
     			if (data == '')
            {
							alert("could not getDownstreamPunters")
              return null;
            }

          var resultJson = $.parseJSON(JSON.stringify(data));
					if (resultJson.status!='OK')
					{
						alert(resultJson.status + " " + resultJson.message);
					}
					punterList = resultJson.result;
          displayPunterList();
        },
				error:function (e) {
	  			alert("getPunter ERROR : " + e.status + " - " + e.statusText);
	      }
     });
 }

 function displayPunterList()
 {
     var pl = document.getElementById('punterList');
     pl.innerHTML="";

     punterList.forEach((punter, i) => {
         tr = document.createElement('tr');
         tr.className = 'gradeA';
         td = document.createElement('td');
         td.appendChild(document.createTextNode(i));
         tr.appendChild(td);
         td = document.createElement('td');
         td.appendChild(document.createTextNode(punter.contact));
         tr.appendChild(td);
         td = document.createElement('td');
         td.appendChild(document.createTextNode(punter.upstreamContact));
         tr.appendChild(td);
         td = document.createElement('td');
         td.appendChild(document.createTextNode(punter.email));
         tr.appendChild(td);
         td = document.createElement('td');
         td.appendChild(document.createTextNode(punter.phone));
         tr.appendChild(td);
         td = document.createElement('td');
         td.style = "text-align:center";
         td.appendChild(document.createTextNode(punter.rating));
         tr.appendChild(td);
         td = document.createElement('td');
         td.appendChild(document.createTextNode(punter.activated));
         tr.appendChild(td);
         pl.appendChild(tr);
       })
}

</script>


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
      <h2>My Agents</h2>
    </div>

    <div class="row-fluid">
      <div class="span12">
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"><i class="icon-th"></i></span>
            <h5>Data table</h5>
          </div>
          <div class="widget-content nopadding">
            <table class="table table-bordered data-table">
              <thead>
                <tr>
                  <th>No.</th>
                  <th>Username</th>
                  <th>Upline</th>
                  <th>Email</th>
                  <th>Phone</th>
                  <th>Rating</th>
                  <th>Activated</th>
                </tr>
              </thead>
              <tbody id='punterList'>
              </tbody>
            </table>
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

<script src="../../../js/jquery.min.js"></script>
<script src="../../../js/jquery.ui.custom.js"></script>
<script src="../../../js/bootstrap.min.js"></script>
<script src="../../../js/jquery.uniform.js"></script>
<script src="../../../js/select2.min.js"></script>
<script src="../../../js/jquery.dataTables.min.js"></script>
<script src="../../../js/matrix.js"></script>
<script src="../../../js/matrix.tables.js"></script>


<script>
getDownstreamPunters();
</script>

</body>
</html>
