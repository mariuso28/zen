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
  				alert("ព័ត៌មានលម្អិតនៃប្រតិបត្តិការបានដាក់ស្នើដោយជោគជ័យ\nពិនិត្យអ៊ីម៉ែលដើម្បីបញ្ជាក់ការទទួល។");
          redirectDashboard();
  			},
  			error:function (e) {
    			alert("submitTransactionDetails ERROR : " + e.status + " - " + e.statusText);
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
 						 alert(resultJson.status + " " + resultJson.message);
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
    document.getElementById("msg1").innerHTML = "អអ្នកបានចុះឈ្មោះដោយជោគជ័យជាមួយ Zen ។ ធ្វើការទូទាត់របស់អ្នកនៅថ្ងៃនេះដើម្បីបន្តចំណាត់ថ្នាក់។ <strong>ចំណាត់ថ្នាក់ 1</strong> ហើយចាប់ផ្តើមរកលុយ!";
    document.getElementById("title").value = "Zen ធ្វើឱ្យសមាជិកនៅឋានៈ1";
    document.getElementById("fee").innerHTML = "តម្លៃធ្វើឱ្យសកម្ម: USD " + upgradeRequest.fee;
  }
  else {
    document.getElementById("msg1").innerHTML = "ឥឡូវនេះអ្នកស្ថិតនៅចំណាត់ថ្នាក់ <strong>" + upgradeRequest.currentRank +
    "</strong>. ធ្វើបច្ចុប្បន្នភាពថ្ងៃនេះ <strong>ចំណាត់ថ្នាក់ " + upgradeRequest.upgradeRank + "</strong> ដើម្បីចាប់ផ្តើមរកប្រាក់បានច្រើន!";
    document.getElementById("title").value = "Zen ធ្វើឱ្យសមាជិកនៅឋានៈ " + upgradeRequest.upgradeRank;
    document.getElementById("fee").innerHTML = "តម្លៃធ្វើឱ្យសកម: USD " + upgradeRequest.fee;
  }
  document.getElementById('uplineFullName').appendChild(document.createTextNode(upgradeRequest.upline.fullName));
  document.getElementById('uplineContact').appendChild(document.createTextNode(upgradeRequest.upline.contact));
  document.getElementById('uplinePhone').appendChild(document.createTextNode(upgradeRequest.upline.phone));
  document.getElementById('uplineCountry').appendChild(document.createTextNode(upgradeRequest.upline.country));
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
    document.getElementById("phone").value = punter.phone;
    document.getElementById("email").value = punter.email;
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
      <h2>Upgrade</h2>
    </div>
    <div class="alert alert2"> <a class="close" data-dismiss="alert" href="#">X</a>
      <%-- <h4 class="alert-heading">Congratulations!</h4> --%>
      <h4 class="alert-heading">អបអរសាទរ!</h4>
      <div class="a" id="msg1"></div>
    </div>


    <div class="row-fluid">
      <div class="span12">
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"><i class="icon-th"></i></span>
            <h5><font color="darkorange" size="5">ព័ត៌មានឡើងលើ</font></h5>
          </div>
          <div class="widget-content nopadding">
            <table class="table table-bordered">
              <thead>
                <tr>
                  <th style="text-align:left;">ឈ្មោះ​ពេញ</th>
                  <th style="text-align:left;">ឈ្មោះអ្នកប្រើ Zen</th>
                  <th style="text-align:left;">លេខទូរសព្ទ</th>
                  <th style="text-align:left;">ប្រទេស</th>
                  <th style="text-align:left;">ចំណាត់ថ្នាក់</th>
                </tr>
              </thead>
              <tbody>
                <tr class="gradeA">
                  <td id="uplineFullName"></td>
                  <td><i id="uplineContact"></i></td>
                  <td id="uplinePhone"></td>
                  <td id="uplineCountry"></td>
                  <td id="uplineRank"></td>
                </tr>
              </tbody>
            </table>
          </div>  <!-- widget-content nopadding -->
        </div>  <!-- widget-box -->
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"><i class="icon-time"></i></span>
            <h3><font color="darkgreen">1. ធ្វើឱ្យការទូទាត់សងខ្ពស់</font></h3>
          </div>
          <div class="widget-content nopadding">
            <h3 style="color:darkred; text-indent: 2.5em;" id="fee"></h3>
          </div>
          <div class="widget-content nopadding">
            <table class="table table-bordered">
              <thead>
                <tr>
                  <th style="text-align:left;">វិធីសាស្រ្ត</th>
                  <th style="text-align:left;">លេខ​គណនី</th>
                  <th style="text-align:left;">ប្រទេស</th>
                </tr>
              </thead>
              <tbody id="paymentMethods">
                <tr class="gradeA">
                  <td>Wing</td>
                  <td>0968157361</td>
                  <td>Cambodia</td>
                </tr>
                <tr class="gradeA">
                  <td>TrueMoney</td>
                  <td>0968157361</td>
                  <td>Cambodia</td>
                </tr>
              </tbody>
            </table>
          </div>  <!-- widget-content nopadding -->
        </div>  <!-- widget-box -->
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"><i class="icon-time"></i></span>
            <%-- <h3><font color="darkgreen">2. Submit Payment Form</font></h3> --%>
            <h3><font color="darkgreen">2. ដាក់ស្នើទម្រង់ទូទាត់</font></h3>
          </div>
          <div class="widget-box">
            <div class="widget-content nopadding">
              <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
                <%-- <h4>Submission Information</h4> --%>
                <h4>Submission Information</h4>
              </div>
              <div class="form-horizontal">
                <div class="control-group">
                  <label class="control-label">Title :</label>
                  <div class="controls">
                    <input readonly type="text" id="title" class="span11" value=""/>
                  </div>
                </div>
                <div class="control-group">
                  <%-- <label class="control-label">Username:</label> --%>
                  <label class="control-label">Username:</label>
                  <div class="controls">
                    <input readonly type="text" id="contact" class="span11" value=""/>
                  </div>
                </div>
                <div class="control-group">
                  <%-- <label class="control-label">Full Name:</label> --%>
                  <label class="control-label">Full Name:</label>
                  <div class="controls">
                    <input readonly type="text" id="fullName" class="span11" value=""/>
                  </div>
                </div>
                <div class="control-group">
                  <%-- <label class="control-label">Phone No.:</label> --%>
                  <label class="control-label">Phone No.:</label>
                  <div class="controls">
                    <input readonly type="text" id="phone" class="span11" value=""/>
                  </div>
                </div>
                <div class="control-group">
                  <%-- <label class="control-label">Email:</label> --%>
                  <label class="control-label">Email:</label>
                  <div class="controls">
                    <input readonly type="text" id="email" class="span11" value=""/>
                  </div>
                </div>
                <div class="control-group">
                  <%-- <label class="control-label">Transaction Date:</label> --%>
                  <label class="control-label">Transaction Date:</label>
                  <div class="controls">
                    <input id="transactionDate" type="text" data-date="05-13-2019" data-date-format="dd-mm-yyyy" value="05-13-2019"
                                  class="datepicker span11">
                    <span class="help-block">Date in (mm-dd-yyyy) format</span> </div>
                </div>
                <div class="control-group">
                  <%-- <label class="control-label">Transaction Details</label> --%>
                  <label class="control-label">Transaction Details</label>
                  <div class="controls">
                    <textarea id="transactionDetails" class="span11" rows="8" cols="50">Copy and Paste Transaction Details</textarea>
                  </div>
                </div>
                <div class="control-group">
                  <%-- <label class="control-label">Upload Transaction Slip:</label> --%>
                  <label class="control-label">Upload Transaction Slip:</label>
                  <div class="controls">
                    <form id="upload-form" enctype="multipart/form-data"  method="post">
    									<input id="upload-input" type="file"
    										required name="uploadfile"
    															style="height: 28px; font-size: 14px; "/>
    								</form>
                  </div>
                </div>
                <div class="form-actions">
                  <%-- <button type="submit" onclick="return submitTransactionDetails()" class="btn btn-success">Submit</button> --%>
                  <button type="submit" onclick="return redirectDashboard()" class="btn btn-danger">Cancel</button>
                  <%-- <button type="submit" onclick="return submitTransactionDetails()" class="btn btn-success">Submit</button> --%>
                  <button type="submit" onclick="return redirectDashboard()" class="btn btn-danger">Cancel</button>
                </div>
              </div>
            </div>
          </div>
        </div>  <!-- widget-box -->
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"><i class="icon-time"></i></span>
            <h3><font color="darkgreen">3. Check Payment Email</font></h3>
          </div>
          <div class="widget-content nopadding">
            <h4 style="color:darkblue; text-indent: 2.5em;">Check your email for the payment confirmation from us.</h4>
            <h4 style="color:darkblue; text-indent: 2.5em;">If you do not receive the email confirmation within 24 hours, please check your SPAM folder.</h4>
            <h4 style="color:darkblue; text-indent: 2.5em;">Write to <a href="mailto:help@zenwing.net">&lt;help@zenwing.net&gt;</a> for further assistance.</h4>
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

  getPunter();
//set Important Lables
  document.getElementById('paymentsPending').innerHTML = punter.actions.paymentsPending;
  document.getElementById('upgradable').innerHTML = punter.actions.upgradable;

  getUpgradeRequest();
</script>

</body>
</html>
