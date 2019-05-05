<!DOCTYPE html>
<html lang="en">
<head>
<title>paymentReceived</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="../../../css/bootstrap.min.css" />
<link rel="stylesheet" href="../../../css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="../../../css/uniform.css" />
<link rel="stylesheet" href="../../../css/select2.css" />
<link rel="stylesheet" href="../../../css/matrix-style.css" />
<link rel="stylesheet" href="../../../css/matrix-media.css" />
<link href="../../font-awesome/css/font-awesome.css" rel="stylesheet" />
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

var payment;

function approvePayment()
{
  var bearerHeader = 'Bearer ' + access_token;
     $.ajax({

    type: "GET",
         url : '/zen/zx4/api/punter/approvePayment?paymentId='+payment.id,
    headers: { 'Authorization': bearerHeader },
    cache: false,
    contentType: 'application/json;',
         dataType: "json",
       	 success: function(data) {
  //         alert(JSON.stringify(data));
     			if (data == '')
          {
						alert("could not approvePayment")
            return;
          }

          var resultJson = $.parseJSON(JSON.stringify(data));
					if (resultJson.status!='OK')
					{
						alert(resultJson.status + " " + resultJson.message);
            return;
					}
					alert("Payment approved. Member upgraded and notified.")
        },
				error:function (e) {
	  			alert("approvePayment ERROR : " + e.status + " - " + e.statusText);
	      }
     });
}

function rejectPayment()
{
  var bearerHeader = 'Bearer ' + access_token;
     $.ajax({

    type: "GET",
         url : '/zen/zx4/api/punter/rejectPayment?paymentId='+payment.id,
    headers: { 'Authorization': bearerHeader },
    cache: false,
    contentType: 'application/json;',
         dataType: "json",
       	 success: function(data) {
  //         alert(JSON.stringify(data));
     			if (data == '')
          {
						alert("could not rejectPayment")
            return;
          }

          var resultJson = $.parseJSON(JSON.stringify(data));
					if (resultJson.status!='OK')
					{
						alert(resultJson.status + " " + resultJson.message);
            return;
					}
					alert("Payment rejected. Member notified.")
        },
				error:function (e) {
	  			alert("rejectPayment ERROR : " + e.status + " - " + e.statusText);
	      }
     });
}

function getPaymentDetails(paymentId,memberType) {

  var bearerHeader = 'Bearer ' + access_token;
     $.ajax({

    type: "GET",
         url : '/zen/zx4/api/punter/getPaymentDetails?paymentId='+paymentId+'&memberType='+memberType,
    headers: { 'Authorization': bearerHeader },
    cache: false,
    contentType: 'application/json;',
         dataType: "json",
       	 success: function(data) {
  //         alert(JSON.stringify(data));
     			if (data == '')
            {
							alert("could not getPaymentDetails")
              return;
            }

          var resultJson = $.parseJSON(JSON.stringify(data));
					if (resultJson.status!='OK')
					{
						alert(resultJson.status + " " + resultJson.message);
            return;
					}
					payment = resultJson.result;
          displayPaymentDetails(memberType);
        },
				error:function (e) {
	  			alert("getPaymentDetails ERROR : " + e.status + " - " + e.statusText);
	      }
     });
 }

 function displayPaymentDetails(memberType)
 {
     if (memberType=='payee')
        document.getElementById('cp').innerHTML='Payment From';
     else
     {
        document.getElementById('cp').innerHTML='Payment To';
     }

     if (memberType=='payer' || payment.status!='Pending')
     {
       ardiv = document.getElementById('approveRejectDiv');
       if (ardiv)
         ardiv.parentNode.removeChild(ardiv);
     }

     var pl = document.getElementById('paymentDetails');
     pl.innerHTML="";
     tr = document.createElement('tr');
     tr.className = 'gradeA';
     td = document.createElement('td');
     td.appendChild(document.createTextNode(payment.id));
     tr.appendChild(td);
     td = document.createElement('td');
     td.innerHTML="<i>" + payment.contact+"</i><br>" + payment.fullName;
     tr.appendChild(td);
     td = document.createElement('td');
     td.appendChild(document.createTextNode(payment.phone));
     tr.appendChild(td);
     td = document.createElement('td');
     td.appendChild(document.createTextNode(payment.description));
     tr.appendChild(td);
     td = document.createElement('td');
     td.style = "text-align:center";
     td.appendChild(document.createTextNode("USD " + payment.amount));
     tr.appendChild(td);
     td = document.createElement('td');
     td.style = "text-align:center";
     td.appendChild(document.createTextNode(payment.status));
     tr.appendChild(td);
     td = document.createElement('td');
     td.style = "text-align:center";
     td.appendChild(document.createTextNode(payment.date));
     tr.appendChild(td);
     pl.appendChild(tr);

     document.getElementById('transactionDate').value = payment.paymentInfo.transactionDate;

     if (payment.paymentInfo.uploadFileImage!=null)
     {
       tddiv = document.getElementById('transactionDetailsDiv');
       if (tddiv)
         tddiv.parentNode.removeChild(tddiv);

       img = document.createElement("img");
       img.src = "data:image/jpg;base64,"+payment.paymentInfo.uploadFileImage;
       document.getElementById('image').appendChild(img);
     }
     else {
       tsdiv = document.getElementById('transactionSlipDiv');
       if (tsdiv)
         tsdiv.parentNode.removeChild(tsdiv);
       document.getElementById('transactionDetails').value = payment.paymentInfo.transactionDetails;
     }
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
    <div class="row-fluid">
      <div class="span12">
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"><i class="icon-th"></i></span>
            <h5><font color="darkorange" size="5">Payment Details</font></h5>
          </div>
          <div class="widget-content nopadding">
            <table class="table table-bordered">
              <thead>
                <tr>
                  <th>Id</th>
                  <th id='cp'></th>
                  <th>Phone</th>
                  <th>Description</th>
                  <th>Amount</th>
                  <th>Status</th>
                  <th>Date/Time</th>
                </tr>
              </thead>
              <tbody id="paymentDetails">
              </tbody>
            </table>
          </div>
        </div>
        <div class="widget-box">
          <div class="widget-content nopadding">
            <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
              <h4>Transaction Information</h4>
            </div>
              <div class="control-group">
                <label class="control-label">Transaction Date:</label>
                <div class="controls">
                  <input id="transactionDate" type="text" data-date="" data-date-format="dd-mm-yyyy" value=""
                        readonly class="datepicker span11">
                  <span class="help-block">Date in (mm-dd-yyyy) format</span> </div>
              </div>
              <div class="control-group" id="transactionDetailsDiv">
                <label class="control-label">Transaction Details:</label>
                <div class="controls">
                  <textarea readonly id="transactionDetails" class="span11" rows="8" cols="50"></textarea>
                </div>
              </div>
              <div class="control-group" id="transactionSlipDiv">
                <label class="control-label">Transaction Slip:</label>
                <div class="controls">
                  <div id="image"></div>
                </div>
              </div>
              <div class="form-actions" id="approveRejectDiv">
                <button type="submit" onclick="return approvePayment()" class="btn btn-success">Approve</button>
                <button type="submit" onclick="return rejectPayment()" class="btn btn-danger">Reject</button>
              </div>
            </div>
          </div>
    </div>  <!-- row-fluid -->
  </div>  <!-- container-fluid -->
</div>  <!-- content -->

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
access_token = sessionStorage.getItem("access_token");
getPaymentDetails(${info['paymentId']},"${info['memberType']}");
</script>

</body>
</html>