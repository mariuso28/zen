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
</style>
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
      <h2>Upgrade</h2>
    </div>
    <div class="alert alert2"> <a class="close" data-dismiss="alert" href="#">×</a>
      <h4 class="alert-heading">Congratulations!</h4>
      You are now <strong>Rank 1</strong>. Upgrade today to <strong>Rank 2</strong> to start earning more!
    </div>


    <div class="row-fluid">
      <div class="span12">
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"><i class="icon-th"></i></span>
            <h5><font color="darkorange" size="5">Upline Information</font></h5>
          </div>
          <div class="widget-content nopadding">
            <table class="table table-bordered">
              <thead>
                <tr>
                  <th style="text-align:left;">Full Name</th>
                  <th style="text-align:left;">Username</th>
                  <th style="text-align:left;">Contact</th>
                  <th style="text-align:left;">Country</th>
                  <th style="text-align:left;">Rank</th>
                </tr>
              </thead>
              <tbody>
                <tr class="gradeA">
                  <td>Cirillo Branden</td>
                  <td><i>cirillo123</i></td>
                  <td>012-3456789</td>
                  <td>Cambodia</td>
                  <td>2</td>
                </tr>

              </tbody>
            </table>
          </div>  <!-- widget-content nopadding -->
        </div>  <!-- widget-box -->
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"><i class="icon-time"></i></span>
            <h3><font color="darkgreen">1. Make Payment to Upline</font></h3>
          </div>
          <div class="widget-content nopadding">
            <h3 style="color:darkred; text-indent: 2.5em;">Upgrade Fee: USD 50</h3>
          </div>
          <div class="widget-content nopadding">
            <table class="table table-bordered">
              <thead>
                <tr>
                  <th style="text-align:left;">Method</th>
                  <th style="text-align:left;">Account</th>
                  <th style="text-align:left;">Country</th>
                </tr>
              </thead>
              <tbody>
                <tr class="gradeA">
                  <td>Wing</td>
                  <td>0958779473</td>
                  <td>Cambodia</td>
                </tr>
                <tr class="gradeA">
                  <td>TrueMoney</td>
                  <td>0958779473</td>
                  <td>Cambodia</td>
                </tr>
              </tbody>
            </table>
          </div>  <!-- widget-content nopadding -->
        </div>  <!-- widget-box -->
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"><i class="icon-time"></i></span>
            <h3><font color="darkgreen">2. Submit Payment Form</font></h3>
          </div>
          <div class="widget-box">
            <div class="widget-content nopadding">
              <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
                <h4>Personal Information</h4>
              </div>
              <div class="form-horizontal">
                <div class="control-group">
                  <label class="control-label">Title :</label>
                  <div class="controls">
                    <input type="text" id="title" class="span11" value="Upgrade to Rank 2"/>
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label">Username:</label>
                  <div class="controls">
                    <input type="text" id="contact" class="span11" value="My Username"/>
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label">Full Name:</label>
                  <div class="controls">
                    <input type="text" id="fullName" class="span11" value="My Name"/>
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label">Phone No.:</label>
                  <div class="controls">
                    <input type="text" id="phone" class="span11" value="My PHone Number"/>
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label">Email:</label>
                  <div class="controls">
                    <input type="text" id="email" class="span11" value="My Email"/>
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label">Transaction Date:</label>
                  <div class="controls">
                    <input type="text" data-date="05-13-2019" data-date-format="dd-mm-yyyy" value="05-13-2019" class="datepicker span11">
                    <span class="help-block">Date in (mm-dd-yyyy) format</span> </div>
                </div>
                <div class="control-group">
                  <label class="control-label">Transaction Details</label>
                  <div class="controls">
                    <textarea class="span11" rows="8" cols="50">Copy and Paste Transaction Details</textarea>
                  </div>
                </div>
                <div class="control-group">
                  <label class="control-label">Upload Transaction Slip:</label>
                  <div class="controls">
                    <input type="file" />
                  </div>
                </div>
                <div class="form-actions">
                  <button type="submit" class="btn btn-success">Submit</button>
                  <button type="submit" class="btn btn-danger">Cancel</button>
                </div>
              </div>
            </div>
          </div>
        </div>  <!-- widget-box -->
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"><i class="icon-time"></i></span>
            <h3><font color="darkgreen">3. Check Upgrade Email</font></h3>
          </div>
          <div class="widget-content nopadding">
            <h4 style="color:darkblue; text-indent: 2.5em;">Check your email for the upgrade confirmation from us.</h4>
            <h4 style="color:darkblue; text-indent: 2.5em;">If you do not receive the email confirmation within 24 hours, please check your SPAM folder.</h4>
            <h4 style="color:darkblue; text-indent: 2.5em;">Write to <a href="mailto:help@goldmine.online">&lt;help@goldmine.online&gt;</a> for further assistance.</h4>
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
</body>
</html>
