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
      <h2>Payment Received</h2>
    </div>

    <div class="row-fluid">
      <div class="span12">
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"><i class="icon-th"></i></span>
            <h5><font color="darkorange" size="5">Pending</font></h5>
          </div>
          <div class="widget-content nopadding">
            <table class="table table-bordered data-table">
              <thead>
                <tr>
                  <th>No.</th>
                  <th>From</th>
                  <th>Contact</th>
                  <th>Description</th>
                  <th>Amount</th>
                  <th>Payment Slip</th>
                  <th>Status</th>
                  <th>Date/Time</th>
                </tr>
              </thead>
              <tbody>
                <tr class="gradeA">
                  <td>1</td>
                  <td>
                    <i>cirillo123</i><br>Cirillo Branden
                  </td>
                  <td>012-3456789</td>
                  <td>
                    Upgrade to Rank&colon; 1 (New Agent)
                  </td>
                  <td>50.00</td>
                  <td><a href=""><center><button class="btn btn-success btn-mini">VIEW</button></center></a></td>
                  <!-- Let Upline approve Downline after viewing -->
                  <td><center>Pending</center></td>
                  <td>
                    03-03-2019
                    <br>
                    11:38
                  </td>
                </tr>
                <tr class="gradeA">
                  <td>2</td>
                  <td>
                    <i>hroderich99</i><br>Lakisha Hroderich
                  </td>
                  <td>012-3456789</td>
                  <td>
                    Upgrade to Rank&colon; 2
                  </td>
                  <td>50.00</td>
                  <td><a href=""><center><button class="btn btn-success btn-mini">VIEW</button></center></a></td>
                  <!-- Let Upline approve Downline after viewing -->
                  <td><center>Pending</center></td>
                  <td>
                    04-03-2019
                    <br>
                    12:34
                  </td>
                </tr>
              </tbody>
            </table>
          </div>  <!-- widget-content nopadding -->
        </div>  <!-- widget-box -->
        <div class="widget-box">
          <div class="widget-title"> <span class="icon"><i class="icon-th"></i></span>
            <h5><font color="darkgreen" size="5">Successful</font></h5>
          </div>
          <div class="widget-content nopadding">
            <table class="table table-bordered data-table">
              <thead>
                <tr>
                  <th>No.</th>
                  <th>From</th>
                  <th>Contact</th>
                  <th>Description</th>
                  <th>Amount</th>
                  <th>Payment Slip</th>
                  <th>Status</th>
                  <th>Date/Time</th>
                </tr>
              </thead>
              <tbody>
                <tr class="gradeA">
                  <td>2</td>
                  <td>
                    <i>alyosha99</i><br>Alyosha Zübeyde
                  </td>
                  <td>012-3456789</td>
                  <td>
                    Upgrade to Rank&colon; 2
                    <br>
                    Transaction&colon;
                    <br>
                    Transaction text copied and pasted.
                  </td>
                  <td>50.00</td>
                  <td><a href=""><center><button class="btn btn-success btn-mini">VIEW</button></center></a></td>
                  <td><center>Successful</center></td>
                  <td>
                    04-03-2019
                    <br>
                    12:34
                  </td>
                </tr>
                <tr class="gradeA">
                  <td>2</td>
                  <td>
                    <i>fagostina</i><br>Farvald Agostina
                  </td>
                  <td>012-3456789</td>
                  <td>
                    Upgrade to Rank&colon; 3
                    <br>
                    Transaction&colon;
                    <br>
                    (Transaction text copied and pasted.)
                  </td>
                  <td>200.00</td>
                  <td><a href=""><center><button class="btn btn-success btn-mini">VIEW</button></center></a></td>
                  <td><center>Successful</center></td>
                  <td>
                    05-03-2019
                    <br>
                    11:22
                  </td>
                </tr>
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
