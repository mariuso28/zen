<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Example</title>

  <script src="https://code.jquery.com/jquery-3.2.1.min.js" integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4=" crossorigin="anonymous"></script> 

  <link href="../../../css/core.css" rel="stylesheet" type="text/css">
  <link href="../../../css/tree.css" rel="stylesheet" type="text/css">
  <script src="../../../js/core.js" type="text/javascript"></script> 
  <script src="../../../js/tree.js" type="text/javascript">></script> 
</head>
<body style="padding: 6px;">
 <div id="tree"></div>
 <script>
     $('#tree').tree({
         dataSource: [ { text: 'foo', children: [ { text: 'bar' } ] } ]
     });
 </script> 
</body>
</html>
