<!DOCTYPE html>
<html lang="en">
<head>
<title>upgrade</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="../../../css/bootstrap.min.css" />
<link rel="stylesheet" href="../../../css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="../../../css/uniform.css" />
<link rel="stylesheet" href="../../../css/select2.css" />
<link rel="stylesheet" href="../../../css/matrix-style.css" />
<link rel="stylesheet" href="../../../css/matrix-media.css" />
<link rel="stylesheet" href="../../../css/datepicker.css" />
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

  .a {
    float: left;
    white-space: nowrap;
    overflow:hidden;
  }

</style>
</head>

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
      url : '/zen/zx4/api/anon/getLabels?jsp=upgrade',
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


function submitTransactionDetails()
{
    access_token = sessionStorage.getItem("access_token");
    var bearerHeader = 'Bearer ' + access_token;

  	// Attach file
  	var uploadForm = document.getElementById('upload-form');
  	var formData = new FormData(uploadForm);

    details = document.getElementById("transactionDetails").value;
    date = document.getElementById("transactionDate").value;
  	formData.append("transactionDate",date);
    formData.append("transactionDetails",details);

  	$.ajax({
  	    url: "/zen/zx4/api/punter/submitTransactionDetails",
  			headers: { 'Authorization': bearerHeader },
  	//		enctype: 'multipart/form-data',
  	    data: formData,
  	    type: 'POST',
  			cache: false,
  	    contentType: false, // NEEDED, DON'T OMIT THIS (requires jQuery 1.6+)
  	    processData: false, // NEEDED, DON'T OMIT THIS
  			success: function(data) {
  				var result = $.parseJSON(JSON.stringify(data));
  				if (result.status != 'OK')
  				{
  					alert("Failed " + result.message);
  					return;
  				}
  				alert("Transaction details successfully submitted.\nCheck email for comfirmation of receipt.");
          redirectDashboard();
  			},
  			error:function (e) {
    			alert(e.status + " - " + e.statusText);
          redirectDashboard();
        }
  	});
}

var punter;

function getPunter() {

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
         async: false,
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
						alert(resultJson.message);
            redirectDashboard();
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


var upgradeRequest;

function getUpgradeRequest() {

 	access_token = sessionStorage.getItem("access_token");
 //	alert(access_token);

   var bearerHeader = 'Bearer ' + access_token;
      $.ajax({

     type: "GET",
          url : '/zen/zx4/api/punter/getUpgradeRequest',
     headers: { 'Authorization': bearerHeader },
     cache: false,
     contentType: 'application/json;',
          dataType: "json",
          async: false,
        	 success: function(data) {
     //      alert(JSON.stringify(data));
      			if (data == '')
             {
 							alert("could not get UpgradeRequest")
                return;
             }

           var resultJson = $.parseJSON(JSON.stringify(data));
 					if (resultJson.status!='OK')
 					{
 						 alert(resultJson.message);
             redirectDashboard();
             return;
 					}
          // alert(JSON.stringify(resultJson.result));
 					upgradeRequest = resultJson.result;
          displayUpgradeRequest();
         },
 				error:function (e) {
 	  			alert("getPunter ERROR : " + e.status + " - " + e.statusText);
 	      }
      });
}

function displayUpgradeRequest()
{
  if (punter.rating==0)
  {
    document.getElementById("msg1").innerHTML = labels['uplabel1'] + "<strong>"
        + labels['Rank'] +
        '1</strong>' + labels['uplabel2'];
    document.getElementById("title").value = labels['uplabel3'] + "1";
    document.getElementById("fee").innerHTML = labels['uplabel4'] +"USD " + upgradeRequest.fee;
  }
  else {
    document.getElementById("msg1").innerHTML = labels['uplabel5'] +"<strong>"
                    + upgradeRequest.currentRank +
    "</strong>." + labels['uplabel6']  + labels['Rank'] + upgradeRequest.upgradeRank
    + "</strong>"+ labels['uplabel7'];
    document.getElementById("title").value = labels['uplabel8'] + upgradeRequest.upgradeRank;
    document.getElementById("fee").innerHTML = labels['uplabel9']+ "USD " + upgradeRequest.fee;
  }
  document.getElementById('uplineFullName').appendChild(document.createTextNode(upgradeRequest.upline.fullName));
  document.getElementById('uplineContact').appendChild(document.createTextNode(upgradeRequest.upline.contact));
/*  document.getElementById('uplinePhone').appendChild(document.createTextNode(upgradeRequest.upline.phone));
  document.getElementById('uplineCountry').appendChild(document.createTextNode(upgradeRequest.upline.country));
*/
  document.getElementById('uplineRank').appendChild(document.createTextNode(upgradeRequest.upline.rating));

  var pl = document.getElementById('paymentMethods');
  pl.innerHTML="";

  upgradeRequest.upline.paymentMethods.forEach((pm, i) => {
      tr = document.createElement('tr');
      tr.className = 'gradeA';
      td = document.createElement('td');
      td.appendChild(document.createTextNode(pm.method));
      tr.appendChild(td);
      td = document.createElement('td');
      td.appendChild(document.createTextNode(pm.accountNum));
      tr.appendChild(td);
      td = document.createElement('td');
      td.appendChild(document.createTextNode(pm.country));
      tr.appendChild(td);
      pl.appendChild(tr);
    })

    document.getElementById("contact").value = punter.contact;
    document.getElementById("fullName").value = punter.fullName;
//    document.getElementById("phone").value = punter.phone;
//    document.getElementById("email").value = punter.email;
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
      <h2 id="upgradeLabel">Upgrade</h2>
    </div>
    <div class="alert alert2"> <a class="close" data-dismiss="alert" href="#">X</a>
      <h4 class="alert-heading" id="congratulationsLabel">Congratulations!</h4>
      <div class="a" id="msg1"></div>
    </div>


    <div class="row-fluid">
      <div class="span12">
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"><i class="icon-th"></i></span>
            <h5><font color="darkorange" size="5" id="uplineInformationLabel">Upline Information</font></h5>
          </div>
          <div class="widget-content nopadding">
            <table class="table table-bordered">
              <thead>
                <tr>
                  <th style="text-align:left;" id="fullNameLabel">Full Name</th>
                  <th style="text-align:left;" id="zenUsernameLabel">Zen Username</th>
                  <th style="text-align:left;" id="rankLabel">Rank</th>
                </tr>
              </thead>
              <tbody>
                <tr class="gradeA">
                  <td id="uplineFullName"></td>
                  <td><i id="uplineContact"></i></td>
                  <td id="uplineRank"></td>
                </tr>
              </tbody>
            </table>
          </div>  <!-- widget-content nopadding -->
        </div>  <!-- widget-box -->
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"><i class="icon-time"></i></span>
            <h3><font color="darkgreen">1. <span float="left" id='makePaymentUplineLabel'>"Make Payment Upline"</span></font></h3>
          </div>
          <div class="widget-content nopadding">
            <h3 style="color:darkred; text-indent: 2.5em;" id="fee"></h3>
          </div>
          <div class="widget-content nopadding">
            <table class="table table-bordered">
              <thead>
                <tr>
                  <th style="text-align:left;" id="methodLabel">Method</th>
                  <th style="text-align:left;" id="accountNumberLabel">Account No.</th>
                  <th style="text-align:left;" id="pmCountryLabel">Country</th>
                </tr>
              </thead>
              <tbody id="paymentMethods">
              </tbody>
            </table>
          </div>  <!-- widget-content nopadding -->
        </div>  <!-- widget-box -->
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"><i class="icon-time"></i></span>
            <h3><font color="darkgreen">2. <span float="left" id='submitPaymentFormLabel'>Submit Payment Form</span></font></h3>
          </div>
          <div class="widget-box">
            <div class="widget-content nopadding">
              <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
                <h4 id="submissionInformationLabel">Summission Information</h4>
              </div>
              <div class="form-horizontal">
                <div class="control-group">
                  <label class="control-label" id="titleLabel">Title :</label>
                  <div class="controls">
                    <input readonly type="text" id="title" class="span11" value=""/>
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label" id="zenUsernameLabel2">Zen Username:</label>
                  <div class="controls">
                    <input readonly type="text" id="contact" class="span11" value=""/>
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label" id="fullNameLabel2">Full Name:</label>
                  <div class="controls">
                    <input readonly type="text" id="fullName" class="span11" value=""/>
                  </div>
                </div>
        <!--
                <div class="control-group">
                  <label class="control-label">Phone No.:</label>
                  <div class="controls">
                    <input readonly type="text" id="phone" class="span11" value=""/>
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label">Email:</label>
                  <div class="controls">
                    <input readonly type="text" id="email" class="span11" value=""/>
                  </div>
                </div>
        -->
                <div class="control-group">
                  <label class="control-label" id="transactionDateLabel">Transaction Date:</label>
                  <div class="controls">
                    <input id="transactionDate" type="text" data-date="05-13-2019" data-date-format="dd-mm-yyyy" value="05-13-2019"
                                  class="datepicker span11">
                    <span class="help-block" id="dateInFormatLabel">Date in (mm-dd-yyyy) format</span> </div>
                </div>
                <div class="control-group">
                  <label class="control-label" id="transactionDetailsLabel">Transaction Details</label>
                  <div class="controls">
                    <textarea id="transactionDetails" class="span11" rows="8" cols="50">
                      </textarea>
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label" id="uploadTransactionSlipLabel">Upload Transaction Slip:</label>
                  <div class="controls">
                    <form id="upload-form" enctype="multipart/form-data"  method="post">
    									<input id="upload-input" type="file"
    										required name="uploadfile"
    															style="height: 28px; font-size: 14px; "/>
    								</form>
                  </div>
                </div>
                <div class="form-actions">
                  <button type="submit" onclick="return submitTransactionDetails()"
                    class="btn btn-success" id="submitButton">Submit</button>
                  <button type="submit" onclick="return redirectDashboard()"
                    class="btn btn-danger" id="cancelButton">Cancel</button>
                </div>
              </div>
            </div>
          </div>
        </div>  <!-- widget-box -->
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"><i class="icon-time"></i></span>
        </div>
          <h3><font color="darkgreen">3. <span float="left" id='checkPaymentEmailLabel'></span></font></h3>
          <div class="widget-content nopadding">
            <h4 style="color:darkblue; text-indent: 2.5em;" id='cpLabel1'></h4>
            <h4 style="color:darkblue; text-indent: 2.5em;" id='cpLabel2'></h4>
            <h4 style="color:darkblue; text-indent: 2.5em;"><span id='cpLabel3'></span>
            <a href="mailto:help@goldmine.online">&lt;help@goldmine.online&gt;</a>
            <span id='cpLabel4'></span></h4>
          </div>
        </div>
      </div>  <!-- span12 -->
    </div>  <!-- row-fluid -->
    <div class="row-fluid">
      <div class="span12">

      </div>
    </div>
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

$.ajaxSetup({
   async: false
});

  getLabels();

  getPunter();
//set Important Lables
  document.getElementById('paymentsPending').innerHTML = punter.actions.paymentsPending;
  document.getElementById('upgradable').innerHTML = punter.actions.upgradable;

  getUpgradeRequest();


</script>

</body>
</html>
