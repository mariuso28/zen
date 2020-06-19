<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

<script>


var isoCodes;

function getSupportedIsoCodes()
{
  $.ajax({

 type: "GET",
      url : '/zen/zx4/api/anon/getSupportedIsoCodes',
  cache: false,
 contentType: 'application/json;',
      dataType: "json",
       async: false,
      success: function(data) {
        if (data == '')
         {
           alert("could not getSupportedIsoCodes")
            return null;
         }

       var resultJson = data;
       if (resultJson.status=='OK')
       {
         isoCodes = resultJson.result;
         showIsoCodes();
       }
       else
       {
         alert(resultJson.message);
       }
     },
     error:function (e) {
       alert("getSupportedIsoCodes ERROR : " + e.status + " - " + e.statusText);
     }
  });
}

function showIsoCodes()
{
  var i;
  var html="";
  for (i=0; i<isoCodes.length; i++)
  {
    var cd = isoCodes[i];
    var id = cd+'Id';
    html += '<a href="/zen/zx4/web/anon/changeLanguage?isoCode=' + cd + '">' +
                      '<img src="../../../img/flag_' + cd + '.png" alt="Logo" width="45" class="center"/></a>';
  }
  elem = document.getElementById('isoCodesId');
  elem.innerHTML = html;
}

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

</script>


<div id="sidebar">
  <div class="logo">
    <img src="../../../img/GoldenCircle_02.png" width="220" height="220" style="padding-top:20px;"/>
  </div>

  <ul>
    <li class="active"><a href="/zen/zx4/web/anon/goDashboard"><i class="icon icon-home"></i><span id="sbDashboardLabel"></span></a> </li>
    <li class="submenu"> <a href="#"><i class="icon icon-user"></i> <span id="sbMyProfileLabel"></span><span class="caret" style="margin-left:10px; margin-top:8px;"></span></a>
      <ul>
        <li><a href="/zen/zx4/web/anon/goEditProfile"><span id="sbEditProfileLabel"></a></li>
          <li><a href="/zen/zx4/web/anon/goChangePassword"><span id="sbChangePasswordLabel"></a></li>
      </ul>
    </li>
    <li class="submenu"> <a href="#"><i class="icon icon-group"></i><span id="sbAgentsLabel"></span>
    <span class="caret" style="margin-left:10px; margin-top:8px; border"></span></a>
      <ul>
        <li><a href="/zen/zx4/web/anon/goGeneology"><span id="sbAgentListLabel"></span></a></li>
        <li><a href="/zen/zx4/web/anon/goUpgrade" onclick="return testZen()"><span id="sbUpgradeLabel"></span>
                <span class="label label-important" style="margin-left:5px;" id='upgradable'></span></a></li>
        <li><a href="/zen/zx4/web/anon/goNewRegistration" onclick="return testCanRecruit()"><span id="sbNewRegistrationLabel"></a></li>
      </ul>
    </li>
    <li class="submenu"> <a href="#"><i class="icon icon-money"></i>
      <span id="sbPaymentLabel"/><span class="caret" style="margin-left:10px; margin-top:8px;">
      </span></a>
      <ul>
        <li><a href="/zen/zx4/web/anon/goPaymentReceived"><span id="sbPaymentReceivedLabel"></span>
          <span class="label label-important" style="margin-left:5px;" id='paymentsPending'></span>
        </a></li>
        <li><a href="/zen/zx4/web/anon/goPaymentSent" onclick="return testZen()">
          <span id="sbPaymentSentLabel"></a></li>
      </ul>
    </li>
    <li><a href="/zen/zx4/web/anon/login"><i class="icon icon-off"></i><span id="sbLogoutLabel"></a> </li>
    <span id='isoCodesId'>
    </span>
  </ul>
</div>

<script>
  getSupportedIsoCodes();
</script>
