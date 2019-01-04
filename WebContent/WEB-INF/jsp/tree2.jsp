<html>
<head>
	<title>tree1</title>
	<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
  <script src="https://unpkg.com/gijgo@1.9.11/js/gijgo.min.js" type="text/javascript"></script>
  <link href="https://unpkg.com/gijgo@1.9.11/css/gijgo.min.css" rel="stylesheet" type="text/css" />
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
</head>

<script type="text/javascript">

function getTree()
{
			$('#tree').tree({
						dataSource: '/zen/zx4/api/anon/getPunterTree',
						imageUrlField: 'image'
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
