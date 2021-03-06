<!DOCTYPE html>
<html lang="en">
<head>
<title>paymentSent</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="../../../css/bootstrap.min.css" />
<link rel="stylesheet" href="../../../css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="../../../css/uniform.css" />
<link rel="stylesheet" href="../../../css/select2.css" />
<link rel="stylesheet" href="../../../css/matrix-style.css" />
<link rel="stylesheet" href="../../../css/matrix-media.css" />
<link href="../../../css/font-awesome.css" rel="stylesheet" />
<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700,800' rel='stylesheet' type='text/css'>

<style>
  body {
    font-size: 16px;
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

  .table th {
    font-size: 14px;
  }
</style>
</head>

<script>

var labels;

function getLabels()
{
  $.ajax({

 type: "GET",
      url : '/zen/zx4/api/anon/getLabels?jsp=paymentSent',
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
var pendingPayments;
var successfulPayments;
var failedPayments;
var punter;

function sendQuery(paymentId)
{
  var bearerHeader = 'Bearer ' + access_token;
     $.ajax({

    type: "GET",
         url : '/zen/zx4/api/punter/sendPaymentQuery?paymentId=' + paymentId,
    headers: { 'Authorization': bearerHeader },
    cache: false,
    contentType: 'application/json;',
         dataType: "json",
       	 success: function(data) {
    //      alert(JSON.stringify(data));
     			if (data == '')
            {
							alert("could not sendPaymentQuery")
               return;
            }

          var resultJson = $.parseJSON(JSON.stringify(data));
					if (resultJson.status!='OK')
					{
						alert(resultJson.status + " " + resultJson.message);
            return;
					}
          alert("Zen support has been notified. Will contact payee and get back to you.");

        },
				error:function (e) {
	  			alert("sendPaymentQuery ERROR : " + e.status + " - " + e.statusText);
	      }
     });
 }

function getPunter() {

  var bearerHeader = 'Bearer ' + access_token;
     $.ajax({

    type: "GET",
         url : '/zen/zx4/api/punter/getPunter',
    headers: { 'Authorization': bearerHeader },
    cache: false,
    contentType: 'application/json;',
         dataType: "json",
       	 success: function(data) {
    //      alert(JSON.stringify(data));
     			if (data == '')
            {
							alert("could not get punter")
               return;
            }

          var resultJson = $.parseJSON(JSON.stringify(data));
					if (resultJson.status!='OK')
					{
						alert(resultJson.status + " " + resultJson.message);
            return;
					}
    //      alert(JSON.stringify(resultJson.result));
					punter = resultJson.result;

        },
				error:function (e) {
	  			alert("getPunter ERROR : " + e.status + " - " + e.statusText);
	      }
     });
 }


function getPendingPayments() {

  var bearerHeader = 'Bearer ' + access_token;

 $.ajax({
    type: "GET",
         url : '/zen/zx4/api/punter/getPaymentsSent?paymentStatus=PAYMENTMADE&offset=0&limit=-1',
    headers: { 'Authorization': bearerHeader },
    cache: false,
    contentType: 'application/json;',
         dataType: "json",
       	 success: function(data) {
  //         alert(JSON.stringify(data));
     			if (data == '')
            {
							alert("could not getPaymentsSent")
              return;
            }
          var resultJson = $.parseJSON(JSON.stringify(data));
					if (resultJson.status!='OK')
					{
						alert(resultJson.status + " " + resultJson.message);
            return;
					}
					pendingPayments = resultJson.result;
          displayPaymentsPending();
        },
				error:function (e) {
	  			alert("getPaymentsSent ERROR : " + e.status + " - " + e.statusText);
	      }
     });
 }

 function getSuccessfulPayments() {

   var bearerHeader = 'Bearer ' + access_token;

  $.ajax({
     type: "GET",
          url : '/zen/zx4/api/punter/getPaymentsSent?paymentStatus=PAYMENTSUCCESS&offset=0&limit=-1',
     headers: { 'Authorization': bearerHeader },
     cache: false,
     contentType: 'application/json;',
          dataType: "json",
        	 success: function(data) {
   //         alert(JSON.stringify(data));
      			if (data == '')
             {
 							alert("could not getPaymentsSent")
               return;
             }
           var resultJson = $.parseJSON(JSON.stringify(data));
 					if (resultJson.status!='OK')
 					{
 						alert(resultJson.status + " " + resultJson.message);
             return;
 					}
 					successfulPayments = resultJson.result;
          displayPaymentsSuccess();
         },
 				error:function (e) {
 	  			alert("getPaymentsSent ERROR : " + e.status + " - " + e.statusText);
 	      }
      });
  }

 function getFailedPayments() {

   var bearerHeader = 'Bearer ' + access_token;

  $.ajax({
     type: "GET",
          url : '/zen/zx4/api/punter/getPaymentsSent?paymentStatus=PAYMENTFAIL&offset=0&limit=-1',
     headers: { 'Authorization': bearerHeader },
     cache: false,
     contentType: 'application/json;',
          dataType: "json",
        	 success: function(data) {
   //         alert(JSON.stringify(data));
      			if (data == '')
             {
 							alert("could not getPaymentsSent")
               return;
             }
           var resultJson = $.parseJSON(JSON.stringify(data));
 					if (resultJson.status!='OK')
 					{
 						alert(resultJson.status + " " + resultJson.message);
             return;
 					}
 					failedPayments = resultJson.result;
          displayPaymentsFailed();
         },
 				error:function (e) {
 	  			alert("getPaymentsSent ERROR : " + e.status + " - " + e.statusText);
 	      }
      });
  }

 function displayPaymentsPending()
 {
     var pl = document.getElementById('paymentsPendingTbody');
     pl.innerHTML="";

     pendingPayments.forEach((payment, i) => {
         tr=createPaymentTr(payment,'pending');
         pl.appendChild(tr);
       })
}

function displayPaymentsSuccess()
{
    var pl = document.getElementById('paymentsSuccess');
    pl.innerHTML="";

    successfulPayments.forEach((payment, i) => {
        tr=createPaymentTr(payment,'success');
        pl.appendChild(tr);
      })
}

function displayPaymentsFailed()
{
    var pl = document.getElementById('paymentsFailed');
    pl.innerHTML="";

    failedPayments.forEach((payment, i) => {
        tr=createPaymentTr(payment,'failed');
        pl.appendChild(tr);
      })
}

function createPaymentTr(payment,status)
{
  tr = document.createElement('tr');
  tr.className = 'gradeA';
  td = document.createElement('td');
  td.appendChild(document.createTextNode(payment.id));
  tr.appendChild(td);
  td = document.createElement('td');
  td.innerHTML="<i>" + payment.contact+"</i><br>" + payment.fullName;
  tr.appendChild(td);
  if (status == 'pending')
  {
    td = document.createElement('td');
    td.innerHTML='<a href="#"><center>'+
    '<button class="btn btn-success btn-mini" onclick=sendQuery(' + payment.id+
    ')>'+ labels['queryButton']
    + '</button></center></a>';
  }
  tr.appendChild(td);
  td = document.createElement('td');
  td.appendChild(document.createTextNode(payment.description));
  tr.appendChild(td);
  td = document.createElement('td');
  td.style = "text-align:center";
  td.appendChild(document.createTextNode("RM " + payment.amount));
  tr.appendChild(td);
  td = document.createElement('td');
  link = "/zen/zx4/web/anon/goPaymentDetails?paymentId="+payment.id+"&memberType=payer"
  td.innerHTML='<a href='+link
           +'><center><button class="btn btn-success btn-mini">'+ labels['viewButton']
           + '</button></center></a>';
  tr.appendChild(td);
  td = document.createElement('td');
  td.style = "text-align:center";
  td.appendChild(document.createTextNode(payment.displayStatus));
  tr.appendChild(td);
  td = document.createElement('td');
  td.appendChild(document.createTextNode(payment.date));
  tr.appendChild(td);
  return tr;
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
      <h2 id="paymentSentLabel"></h2>
    </div>

    <div class="row-fluid">
      <div class="span12">
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"><i class="icon-th"></i></span>
            <h5><font color="darkorange" size="5" id="pendingLabel"></font></h5>
          </div>
          <div class="widget-content nopadding">
            <table class="table table-bordered data-table">
              <thead>
                <tr>
                  <th id="idLabel1"></th>
                  <th id="toLabel1"></th>
                  <th id="queryLabel1"></th>
                  <th id="descriptionLabel1"></th>
                  <th id="amountLabel1"></th>
                  <th id="paymentDetailsLabel1"></th>
                  <th id="statusLabel1"></th>
                  <th id="dateTimeLabel1"></th>
                </tr>
              </thead>
              <tbody id="paymentsPendingTbody">
              </tbody>
            </table>
          </div>  <!-- widget-content nopadding -->
        </div>  <!-- widget-box -->
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"><i class="icon-th"></i></span>
            <h5><font color="darkgreen" size="5" id="successfulLabel"></font></h5>
          </div>
          <div class="widget-content nopadding">
            <table class="table table-bordered data-table">
              <thead>
                <tr>
                  <th id="idLabel2"></th>
                  <th id="toLabel2"></th>
                  <th id="descriptionLabel2"></th>
                  <th id="amountLabel2"></th>
                  <th id="paymentDetailsLabel2"></th>
                  <th id="statusLabel2"></th>
                  <th id="dateTimeLabel2"></th>
                </tr>
              </thead>
              <tbody id="paymentsSuccess">
              </tbody>
            </table>
          </div>  <!-- widget-content nopadding -->
        </div>  <!-- widget-box -->
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"><i class="icon-th"></i></span>
            <h5><font color="red" size="5" id="failedLabel"></font></h5>
          </div>
          <div class="widget-content nopadding">
            <table class="table table-bordered data-table">
              <thead>
                <tr>
                  <th id="idLabel3"></th>
                  <th id="toLabel3"></th>
                  <th id="descriptionLabel3"></th>
                  <th id="amountLabel3"></th>
                  <th id="paymentDetailsLabel3"></th>
                  <th id="statusLabel3"></th>
                  <th id="dateTimeLabel3"></th>
                </tr>
              </thead>
              <tbody id="paymentsFailed">
              </tbody>
            </table>
          </div>  <!-- widget-content nopadding -->
        </div>  <!-- widget-box -->
      </div>  <!-- span12 -->
    </div>  <!-- row-fluid -->
  </div>  <!-- container-fluid -->
</div>  <!-- content -->

<!--end-main-container-part-->

<!--Footer-part-->

<div class="row-fluid">
  <div id="footer" class="span12"> 2020 &copy; Zen</div>
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

access_token = sessionStorage.getItem("access_token");

$.ajaxSetup({
   async: false
});

getPunter();
//set Important Lables
document.getElementById('paymentsPending').innerHTML = punter.actions.paymentsPending;
document.getElementById('upgradable').innerHTML = punter.actions.upgradable;

//	alert(access_token);
getLabels();
getPendingPayments();
getSuccessfulPayments();
getFailedPayments();

displayLabels();

</script>

</body>
</html>
