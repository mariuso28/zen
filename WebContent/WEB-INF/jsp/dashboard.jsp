<!DOCTYPE html>
<html lang="en">
<head>
<title>dashboard</title>



<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="../../../css/bootstrap.min.css" />
<link rel="stylesheet" href="../../../css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="../../../css/fullcalendar.css" />
<link rel="stylesheet" href="../../../css/matrix-style.css" />
<link rel="stylesheet" href="../../../css/matrix-media.css" />
<link rel="stylesheet" href="../../../css/font-awesome.css" />
<link rel="stylesheet" href="../../../css/jquery.gritter.css" />
<link href='http:/fonts.googleapis.com/css?family=Open+Sans:400,700,800' rel='stylesheet' type='text/css'>

<script>

var labels;

function getLabels()
{
  $.ajax({

 type: "GET",
      url : '/zen/zx4/api/anon/getLabels?jsp=dashboard',
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
         displayLabels();
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
var punter;
var notifications;

function getPunter() {

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
					punter = resultJson.result;
        },
				error:function (e) {
	  			alert("getPunter ERROR : " + e.status + " - " + e.statusText);
	      }
     });
 }

function getNotifications() {

var bearerHeader = 'Bearer ' + access_token;
    $.ajax({
   type: "GET",
        url : '/zen/zx4/api/punter/getNotifications',
   headers: { 'Authorization': bearerHeader },
   cache: false,
   contentType: 'application/json;',
        dataType: "json",
      	 success: function(data) {
 //         alert(JSON.stringify(data));
    			if (data == '')
           {
							alert("could not get notifications")
              return null;
           }

        var resultJson = $.parseJSON(JSON.stringify(data));
					if (resultJson.status!='OK')
					{
						alert(resultJson.status + " " + resultJson.message);
					}
					notifications = resultJson.result;
         displayNotifications();
        },
				error:function (e) {
	  			alert("getNotifications ERROR : " + e.status + " - " + e.statusText);
	      }
    });
}

function displayNotifications()
{
    posts = document.getElementById('recentPosts');
    posts.innerHTML="";

    notifications.forEach((notification, i) => {
        li=createNotificationLi(notification);
        posts.appendChild(li);
      })
}

function createNotificationLi(n)
{
    li = document.createElement('li');
    d1 = document.createElement('div');
    d1.classList.add('user-thumb');
    html = '<img style="height:40px;width:40px;" alt="User" src="../../../img/demo/av' + n.priority + '.jpg">';
    d1.innerHTML = html;
    li.appendChild(d1);
    d2 = document.createElement('div');
    d2.classList.add('article-post');
    html = '<p><a href="' + n.href + '">' + n.message + '</a></p>';
    d2.innerHTML = html;
    li.appendChild(d2);
    return li;
}


</script>

<style>
  body {
    font-size: 16px;
    width:100%;
  }

  .center {
    display: block;
    margin-left: auto;
    margin-right: auto;
    width: 30%;
    padding: 10px;
  }

  .logo{
    text-align: center;
  }

</style>


</head>
<body>

<!--sidebar-menu-->

<jsp:include page="sidebar.jsp"/>

<!--sidebar-menu-->



<!--main-container-part-->
<div id="content">


  <div class="container-fluid" >
    <jsp:include page="actions.jsp"/>

    <div class="row-fluid">
      <div class="span12">
        <div class="widget-box">
          <div class="widget-title bg_ly" data-toggle="collapse" href="#collapseG2"><span class="icon"><i class="icon-chevron-down"></i></span>
            <h5 id="latestLabel"></h5>
          </div>
          <div class="widget-content nopadding collapse in" id="collapseG2">
            <ul class="recent-posts" id="recentPosts">
          <!--
              <li>

                <div class="user-thumb"> <img width="40" height="40" alt="User" script src="../../../img/demo/av1.jpg"> </div>
                <div class="article-post"> <span class="user-info"> By: john Deo / Date: 2 Aug 2012 / Time:09:27 AM </span>
                  <p><a href="#">This is a much longer one that will go on for a few lines.It has multiple paragraphs and is full of waffle to pad out the comment.</a> </p>
                </div>

              </li>
          -->
            </ul>
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

<script type="text/javascript">

$.ajaxSetup({
   async: false
});


access_token = sessionStorage.getItem("access_token");

getLabels();
getPunter();
getNotifications();

// set Important Lables
document.getElementById('paymentsPending').innerHTML = punter.actions.paymentsPending;
document.getElementById('upgradable').innerHTML = punter.actions.upgradable;

</script>
</body>
</html>
