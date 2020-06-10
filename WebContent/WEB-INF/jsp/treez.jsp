<!DOCTYPE html>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page trimDirectiveWhitespaces="true" %>

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/foundation/6.4.1/css/foundation.min.css">

<html>

<style>

  .modal-profile-content {
    width: 544px;
    height: 300px;
    background-color: #fff;
    /*margin: auto;*/
    padding: 10px;
    border: 1px solid #888;
  }

  .profileTopBar2 {
  width: 500px;
  height: 4px;
  background-color: #129c94;
  }

  .profileTopBar {
  width: 500px;
  height: 44px;
  background-color: #129c94;
  color: #666;
  font-size: 14px;
  line-height: 16px;
  font-weight: 900;
  }

  .profileTopBarMiddle {
  float: left;
  width: 495px;
  height: 40px;
  text-align: left;
  padding-left: 5px;
  }

  .profileTopHiddenBar {
  width: 500px;
  height: 280px;
  background-color: #129c94;
  }

  .profileEntryPanel{
    float: left;
    width: 475px;
    height: 280px;
    text-align: left;
    line-height: 22px;
    padding-left: 5px;
  }

  .profileEntryLine {
    float: left;
    width: 500px;
    height: 28px;
    padding-left: 0px;
    margin-top: 10px;
    margin-bottom: 5px;
  }

  .profileEntryPrompt {
    float: left;
    width: 180px;
    height: 24px;
    text-align: left;
    font-family: myFont;
    font-size: 14px;
    font-weight: 700;
    color: #fff;
    text-shadow: 1px 1px 1px #666;
    line-height: 28px;
    padding-left: 0px;
  }

  .profileEntryInput {
    float: left;
    width: 280px;
    height: 28px;
    text-align: left;
    padding-left: 5px;
  }

  .profileEntryPasswordLine {
    float: left;
    width: 500px;
    height: 28px;
    padding-left: 0px;
  }

  .profileEntryPasswordPrompt {
    float: left;
    width: 180px;
    height: 24px;
    text-align: left;
    font-family: myFont;
    font-size: 14px;
    font-weight: 700;
    color: #fff;
    text-shadow: 1px 1px 1px #666;
    line-height: 28px;
  }

  .profileEntryPasswordInput {
    float: left;
    width: 280px;
    height: 28px;
    text-align: left;
    padding-left: 5px;
    padding-right: 10px;
  }

  .profileEntrySubmitButton {
    float: left;
    width: 70px;
    height: 26px;
    text-align: left;
    padding-left: 5px;
  }

  .profileEntryErrorMessage {
    float: left;
    width: 500px;
    height: 20px;
    text-align: left;
    padding-left: 0px;
     font-size: 18px;
  }

  /* The Close Button */
  .closePr {
  color: #333;
  float: right;
  font-size: 28px;
  font-weight: bold;
  line-height: 20px;
  }

  .closePr:hover,
  .closePr:focus {
  color: #000;
  text-decoration: none;
  cursor: pointer;
  }

  </style>


<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>treez</title>

  <link href="../../../css/core.css" rel="stylesheet" type="text/css">
  <link href="../../../css/tree.css" rel="stylesheet" type="text/css">
  <script src="../../../js/core.js" type="text/javascript"></script>
  <script src="../../../js/tree.js" type="text/javascript">></script>
</head>

<script>

function setUpModalProfile()
{
	var modalPr = document.getElementById('myModalProfile');
	var btnPr = document.getElementById("register");
	var spanPr = document.getElementsByClassName("closePr")[0];
// When the user clicks the button, open the modal

	refreshOn = false;
	modalPr.style.display = "none";

	btnPr.onclick = function() {
		refreshOn = false;
		modalPr.style.display = "block";
		return false;
	}

	spanPr.onclick = function() {
		refreshOn = true;
		modalPr.style.display = "none";
		return false;
	}

	window.onclick = function(event) {
			if (event.target == modalPr) {
				refreshOn = true;
				modalPr.style.display = "none";
		}
	}
}

</script>

<body style="padding: 6px;">
<div id="tree"></div>

<script>

function getTree()
{
      $('#tree').tree({
            dataSource: '/zen/zx4/api/anon/getPunterTree',
            imageUrlField: 'image',
            primaryKey: 'id'
        });

}
</script>

<script>
  getTree();
</script>

<div id="myModalProfile" class="modal">
    <div class="modal-profile-content">
      <span class="closePr">&times;</span>
          <div id="#129c94r2P" class="profileTopHiddenBar">
            <div class="profileEntryPanel">
              <div class="profileEntryLine">
                <div class="profileEntryPrompt">
                  Email:
                </div>
                <div class="profileEntryInput">
                    <input id="email-input" type="text"
                                value="" style="height: 26px; width: 240px; font-size: 14px; "/>
                </div>
              </div>
              <div class="profileEntryLine">
                <div class="profileEntryPrompt">
                  Contact:
                </div>
                <div class="profileEntryInput">
                    <input id="contact-input" type="text"
                                value="" style="height: 26px; width: 240px; font-size: 14px; "/>
                </div>
              </div>
              <div class="profileEntryPasswordLine">
                <div class="profileEntryPasswordPrompt">
                        Password:
                </div>
                <div class="profileEntryPasswordInput">
                      <input id="password-input" type="password"
                         style="height: 26px; width: 240px; font-size: 14px; "/>
                </div>
              </div>
              <div class="profileEntryPasswordLine">
                <div class="profileEntryPasswordPrompt">
                        Verify Password:
                </div>
                <div class="profileEntryPasswordInput">
                      <input id="vpassword-input" type="password"
                         style="height: 26px; width: 240px; font-size: 14px; "/>
                </div>
              </div>
              <div class="profileEntryLine">
                <div class="profileEntryPrompt">
                  Phone:
                </div>
                <div class="profileEntryInput">
                    <input id="phone-input" type="text"
                                value="" style="height: 26px; width: 240px; font-size: 14px; "/>
                </div>
              </div>
              <div class="profileEntrySubmitButton">
                  <input type="button" id="profile" value="Save Profile"
                    onClick="return registerProfile();"
                    class="btn btn-primary"
                    style="height: 26px; line-height:0px; font-weight:700; text-shadow:1px 1px 1px #666;" />
              </div>
            </br>
              <div class="profileEntryErrorMessage" id='profileError'>
              </div>
            </div>  <!-- profileEntryPanel -->
          </div>  <!-- topHiddenBar -->
      </div>  <!-- modal-content  -->
    </div>  <!-- modalProfile  -->

</body>
</html>
