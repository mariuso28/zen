<!DOCTYPE html>
<html>
<head>
	<title>verifyEmail</title>
	<script>
	  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
	  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
	  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
	  })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');

	  ga('create', 'UA-45865892-2', 'auto');
	  ga('send', 'pageview');

	</script>


</head>
<body onload="setTimeout(function() { document.frm1.submit() }, 3000)">
   <form action="/zen/zx4/web/anon/submitVerify" name="frm1" method="post">
      <input type="hidden" name="id" value="${id}" />
      <input type="hidden" name="${_csrf.parameterName}"  value="${_csrf.token}" />
   </form>
</body>
</html>
