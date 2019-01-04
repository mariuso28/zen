<html>
<head>
	<title>treeAjax</title>
	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>

	<link href="../../../css/core.css" rel="stylesheet" type="text/css">
  <link href="../../../css/tree.css" rel="stylesheet" type="text/css">
  <script src="../../../js/core.js" type="text/javascript"></script>
  <script src="../../../js/tree.js" type="text/javascript">></script>
</head>

<script type="text/javascript">

function getTree()
{
	$.ajax({
	type: "POST",
	 url : "/zen/zx4/api/anon/getPunterTree",
	 cache: false,
	 contentType: 'application/json;',
	 dataType: "json",
		success: function(data) {
			var result = $.parseJSON(JSON.stringify(data));
			if (result.status != 'OK')
			{
				alert('Error : ' + result.message);
				return;
			}
		//	alert('Ok : + ' + JSON.stringify(result.result));

	//		var pText = [{text:"z1.1.2.1@test.com",children:[{text:"z1.1.2.1.1@test.com",children:[]}]}];
			var dText = result.result;
//			alert('P: ' + pText);
//			alert('D: ' + dText);

			$('#tree').tree({
						dataSource: dText,
						imageUrlField: 'imageUrl',  
						primaryKey: 'id'
				});
		}
	});
}
</script>

<body style="padding: 6px;">

<div class="container-fluid">
	  <div id="tree"></div>
</div>

<script>
	getTree();
</script>

</body>
</html>
