<!DOCTYPE html>
<html lang="en">
<head>
<title>agentList</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="css/bootstrap.min.css" />
<link rel="stylesheet" href="css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="css/uniform.css" />
<link rel="stylesheet" href="css/select2.css" />
<link rel="stylesheet" href="css/matrix-style.css" />
<link rel="stylesheet" href="css/matrix-media.css" />
<link href="font-awesome/css/font-awesome.css" rel="stylesheet" />
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

     punterList.forEach((punterList, i) => {
         tr = document.createElement('tr');
         td = document.createElement('td');
         td.appendChild(document.createTextNode(i));
         td = document.createElement('td');
         td.appendChild(document.createTextNode(residence.contact));
         td = document.createElement('td');
         td.appendChild(document.createTextNode(residence.email));
         td = document.createElement('td');
         td.style = "text-align:center";
         td.appendChild(document.createTextNode(residence.rating));
         td = document.createElement('td');
         td.appendChild(document.createTextNode(residence.activated));
         pl.appendChild(tr);
       })
}

</script>


<body>

<!--sidebar-menu-->

<div id="sidebar"><a href="#" class="visible-phone"><i class="icon icon-home"></i> Dashboard</a>
  <div class="toplogo">
    <img src="img/GoldenCircle_02a.png" width="220" height="220"/>
  </div>
  <ul>
    <li><a href="index.html"><i class="icon icon-home"></i> <span>Dashboard</span></a> </li>
    <li class="submenu"> <a href="#"><i class="icon icon-user"></i> <span>My Profile</span><span class="caret" style="margin-left:10px; margin-top:8px;"></span></a>
      <ul>
        <li><a href="/zen/zx4/web/anon/goEditProfile">Edit Profile</a></li>
        <li><a href="/zen/zx4/web/anon/goChangePassword">Change Password</a></li>
      </ul>
    </li>
    <li class="active submenu"> <a href="#"><i class="icon icon-group"></i> <span>Agents</span><span class="caret" style="margin-left:10px; margin-top:8px; border"></span></a>
      <ul>
        <li><a href="#">Agent List</a></li>
        <li><a href="#">Upgrade<span class="label label-important" style="margin-left:5px;">1</span></a></li>
        <li><a href="/zen/zx4/web/anon/goNewRegistration">New Registration</a></li>
        <li><a href="/zen/zx4/web/anon/goGeneology">Geneology</a></li>
        <li><a href="#">Grade Summary</a></li>
      </ul>
    </li>
    <li class="submenu"> <a href="#"><i class="icon icon-money"></i> <span>Payment</span><span class="caret" style="margin-left:10px; margin-top:8px;"></span></a>
      <ul>
        <li><a href="#">Payment Received<span class="label label-important" style="margin-left:5px;">2</span></a></li>
        <li><a href="#">Payment Sent</a></li>
      </ul>
    </li>
    <li><a href="#"><i class="icon icon-off"></i> <span>Logout</span></a> </li>
  </ul>
</div>
<!--sidebar-menu-->

<!--main-container-part-->
<div id="content">


<!--Action boxes-->
  <div class="container-fluid">
    <div class="quick-actions_homepage">
      <ul class="quick-actions">
          <li class="bg_lb"> <a href="/zen/zx4/web/anon/goChangePassword"> <i class="icon-lock"></i>Password </a> </li>
        <li class="bg_lg"> <a href="#"> <i class="icon-signout"></i> Payment Sent</a> </li>
        <!-- <li class="bg_ly"> <a href="widgets.html"> <i class="icon-inbox"></i><span class="label label-success">101</span> Payment Received </a> </li> -->
        <li class="bg_ly"> <a href="#"> <i class="icon-signin"></i>Payment Received </a> </li>
        <li class="bg_lo"> <a href="#"> <i class="icon-upload"></i> Upgrade</a> </li>
        <li class="bg_ls"> <a href="#"> <i class="icon-user"></i> Agent List</a> </li>
        <li class="bg_lo"> <a href="/zen/zx4/web/anon/goNewRegistration"> <i class="icon-star"></i> New Registration</a> </li>
      </ul>
    </div>
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
                  <th>Activated>
                </tr>
              </thead>
              <tbody id='punterList'>
                <tr class="gradeA">
                  <td>1</td>
                  <td>Cirillo Branden</td>
                  <td>Emirhan Jonna</td>
                  <td>Cirillo.Branden@test.com</td>
                  <td>1234567890</td>
                  <td><center>1</center></td>
                  <td>03-03-2019</td>
                </tr>
                <tr class="gradeA">
                  <td>2</td>
                  <td>Lakisha Hroderich</td>
                  <td>Emirhan Jonna</td>
                  <td>Lakisha.Hroderich@test.com</td>
                  <td>0174963448</td>
                  <td><center>1</center></td>
                  <td>01-03-2019</td>
                </tr>
                <tr class="gradeA">
                  <td>3</td>
                  <td>Irma Erskine</td>
                  <td>Emirhan Jonna</td>
                  <td>Irma.Erskine@test.com</td>
                  <td>0115475729</td>
                  <td><center>3</center></td>
                  <td>01-03-2019</td>
                </tr>
                <tr class="gradeA">
                  <td>4</td>
                  <td>Valter Hülya</td>
                  <td>Emirhan Jonna</td>
                  <td>Valter.Hülya@example.com</td>
                  <td>01272830154</td>
                  <td><center>1</center></td>
                  <td>02-03-2019</td>
                </tr>
                <tr class="gradeA">
                  <td>5</td>
                  <td>Frans Oluwaseyi</td>
                  <td>Emirhan Jonna</td>
                  <td>Frans.Oluwaseyi@test.com</td>
                  <td>0115327508</td>
                  <td><center>2</center></td>
                  <td>06-03-2019</td>
                </tr>
                <tr class="gradeA">
                  <td>6</td>
                  <td>Dimitrij Lillia</td>
                  <td>Emirhan Jonna</td>
                  <td>Dimitrij.Lillia@abcde.com</td>
                  <td>0178223253</td>
                  <td><center>1</center></td>
                  <td>01-03-2019</td>
                </tr>
                <tr class="gradeA">
                  <td>7</td>
                  <td>Hana Jada</td>
                  <td>Emirhan Jonna</td>
                  <td>Hana.Jada@test.com</td>
                  <td>01210969184</td>
                  <td><center>1</center></td>
                  <td>03-03-2019</td>
                </tr>
                <tr class="gradeA">
                  <td>8</td>
                  <td>Bandile Majda</td>
                  <td>Emirhan Jonna</td>
                  <td>Cirillo.Branden@test.com</td>
                  <td>0113865170</td>
                  <td><center>2</center></td>
                  <td>03-03-2019</td>
                </tr>
                <tr class="gradeA">
                  <td>9</td>
                  <td>Viltautas Æðelfrið</td>
                  <td>Emirhan Jonna</td>
                  <td>Viltautas@test.com</td>
                  <td>0197095363</td>
                  <td><center>1<center></td>
                  <td>03-03-2019</td>
                </tr>
                <tr class="gradeA">
                  <td>10</td>
                  <td>Jason Sara</td>
                  <td>Emirhan Jonna</td>
                  <td>Jason.Sara@test.com</td>
                  <td>0134145172</td>
                  <td><center>1</center></td>
                  <td>03-03-2019</td>
                </tr>
                <tr class="gradeA">
                  <td>11</td>
                  <td>Murphy Rhianu</td>
                  <td>Emirhan Jonna</td>
                  <td>Murphy .Rhianu@test.com</td>
                  <td>0146971095</td>
                  <td><center>1</center></td>
                  <td>04-03-2019</td>
                </tr>
                <tr class="gradeA">
                  <td>12</td>
                  <td>Bonitus Zawisza</td>
                  <td>Emirhan Jonna</td>
                  <td>Bonitus.Zawisza@test.com</td>
                  <td>0120208928</td>
                  <td><center>1</center></td>
                  <td>01-03-2019</td>
                </tr>
                <tr class="gradeA">
                  <td>13</td>
                  <td>Keinan Amirah</td>
                  <td>Emirhan Jonna</td>
                  <td>Keinan.Amirah@test.com</td>
                  <td>0136477093</td>
                  <td><center>1</center></td>
                  <td>05-03-2019</td>
                </tr>
                <tr class="gradeA">
                  <td>14</td>
                  <td>Evan Judit</td>
                  <td>Emirhan Jonna</td>
                  <td>Evan.Judit@test.com</td>
                  <td>0179746635</td>
                  <td><center>1</center></td>
                  <td>03-03-2019</td>
                </tr>
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
  <div id="footer" class="span12"> 2020 &copy; Zen</div>
</div>

<!--end-Footer-part-->

<script src="js/jquery.min.js"></script>
<script src="js/jquery.ui.custom.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/jquery.uniform.js"></script>
<script src="js/select2.min.js"></script>
<script src="js/jquery.dataTables.min.js"></script>
<script src="js/matrix.js"></script>
<script src="js/matrix.tables.js"></script>
</body>
</html>
