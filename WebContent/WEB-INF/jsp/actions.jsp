
<script>

/*
function testCanRecruit()
{
  if (punter.rating!=0)
  {
    if (punter.canRecruit==false)
    {
      alert(labels['sbDbAlert4']);
      return false;
    }
    return true;
  }
  alert(labels['sbDbAlert2']);
  return false;
}

function testZen()
{
  if (punter.rating>-1)
    return true;
  alert(labels['sbDbAlert1']);
  return false;
}

function testUpgrade()
{
  if (!testZen())
    return false;
  if (punter.canUpgrade==false)
  {
    alert(labels['sbDbAlert3']);
    return false;
  }
  return true;
}
*/
</script>


<div class="quick-actions_homepage">
  <ul class="quick-actions">
    <li class="bg_lb"> <a href="/zen/zx4/web/anon/goChangePassword">
      <i class="icon-lock"></i><span id="dbPasswordLabel"/></a> </li>
    <li class="bg_lg"> <a href="/zen/zx4/web/anon/goPaymentSent" onclick="return testZen()">
      <i class="icon-signout"></i><span id="dbPaymentSentLabel"/></a></li>
    <!-- <li class="bg_ly"> <a href="widgets.html"> <i class="icon-inbox"></i><span class="label label-success">101</span> Payment Received </a> </li> -->
    <li class="bg_ly"> <a href="/zen/zx4/web/anon/goPaymentReceived" id='paymentRecieved'>
      <i class="icon-signin"></i><span id="dbPaymentReceivedLabel"></span></a> </li>
    <li class="bg_lo"> <a href="/zen/zx4/web/anon/goUpgrade" onclick="return testUpgrade()">
      <i class="icon-upload"></i><span id="dbUpgradeLabel"><span></a> </li>
    <li class="bg_ls"> <a href="/zen/zx4/web/anon/goGeneology"
      id='agentList'> <i class="icon-user"></i><span id="dbAgentListLabel"/></a> </li>
    <li class="bg_lo"> <a href="/zen/zx4/web/anon/goNewRegistration" onclick="return testCanRecruit()" id='newRegistration'>
      <i class="icon-star"></i><span id="dbNewRegistrationLabel"/></a> </li>
  </ul>
</div>
