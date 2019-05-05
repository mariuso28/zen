<script>

function testAllowed()
{
  if (punter.rating!=0)
    return true;
  alert("Feature not available until payment to join complete.");
  return false;
}

</script>


<div id="sidebar"><a href="#" class="visible-phone"><i class="icon icon-home"></i> Dashboard</a>
  <div class="">
    <img script src="../../../img/GoldenCircle_02.png" width="220" height="220"/>
  </div>
  <ul>
    <li class="active"><a href="/zen/zx4/web/anon/goDashboard"><i class="icon icon-home"></i> <span>Dashboard</span></a> </li>
    <li class="submenu"> <a href="#"><i class="icon icon-user"></i> <span>My Profile</span><span class="caret" style="margin-left:10px; margin-top:8px;"></span></a>
      <ul>
        <li><a href="/zen/zx4/web/anon/goEditProfile">Edit Profile</a></li>
          <li><a href="/zen/zx4/web/anon/goChangePassword">Change Password</a></li>
      </ul>
    </li>
    <li class="submenu"> <a href="#"><i class="icon icon-group"></i> <span>Agents</span><span class="caret" style="margin-left:10px; margin-top:8px; border"></span></a>
      <ul>
        <li><a href="/zen/zx4/web/anon/goAgentList" onclick="return testAllowed()">Agent List</a></li>
        <li><a href="/zen/zx4/web/anon/goUpgrade">Upgrade<span class="label label-important" style="margin-left:5px;" id='canUpgrade'></span></a></li>
        <li><a href="/zen/zx4/web/anon/goNewRegistration" onclick="return testAllowed()">New Registration</a></li>
        <li><a href="/zen/zx4/web/anon/goGeneology">Geneology</a></li>
        <li><a href="#" onclick="return testAllowed()">Grade Summary</a></li>
      </ul>
    </li>
    <li class="submenu"> <a href="#"><i class="icon icon-money"></i> <span>Payment</span><span class="caret" style="margin-left:10px; margin-top:8px;"></span></a>
      <ul>
        <li><a href="/zen/zx4/web/anon/goPaymentReceived">Payment Received<span class="label label-important" style="margin-left:5px;" id='paymentsPending'></span></a></li>
        <li><a href="/zen/zx4/web/anon/goPaymentSent">Payment Sent</a></li>
      </ul>
    </li>
    <li><a href="/zen/zx4/web/anon/login"><i class="icon icon-off"></i> <span>Logout</span></a> </li>
  </ul>
</div>
