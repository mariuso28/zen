
<script>

function testAllowed()
{
  if (punter.rating!=0)
    return true;
  alert("Feature not available until payment to join complete.");
  return false;
}

</script>


<div class="quick-actions_homepage">
  <ul class="quick-actions">
    <li class="bg_lb"> <a href="/zen/zx4/web/anon/goChangePassword"> <i class="icon-lock"></i>Password </a> </li>
    <li class="bg_lg"> <a href="#"> <i class="icon-signout"></i> Payment Sent</a> </li>
    <!-- <li class="bg_ly"> <a href="widgets.html"> <i class="icon-inbox"></i><span class="label label-success">101</span> Payment Received </a> </li> -->
    <li class="bg_ly"> <a href="/zen/zx4/web/anon/goPaymentReceived" id='paymentRecieved'> <i class="icon-signin"></i>Payment Received </a> </li>
    <li class="bg_lo"> <a href="/zen/zx4/web/anon/goUpgrade"> <i class="icon-upload"></i> Upgrade</a> </li>
    <li class="bg_ls"> <a href="/zen/zx4/web/anon/goAgentList" onclick="return testAllowed()" id='agentList'> <i class="icon-user"></i> Agent List</a> </li>
    <li class="bg_lo"> <a href="/zen/zx4/web/anon/goNewRegistration" onclick="return testAllowed()" id='newRegistration'> <i class="icon-star"></i> New Registration</a> </li>
  </ul>
</div>
