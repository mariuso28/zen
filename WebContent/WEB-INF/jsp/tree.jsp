<!DOCTYPE html>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page trimDirectiveWhitespaces="true" %>

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/foundation/6.4.1/css/foundation.min.css">

<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Example</title>

  <link href="../../../css/core.css" rel="stylesheet" type="text/css">
  <link href="../../../css/tree.css" rel="stylesheet" type="text/css">
  <script src="../../../js/core.js" type="text/javascript"></script>
  <script src="../../../js/tree.js" type="text/javascript">></script>
</head>
<body style="padding: 6px;">
 <div id="tree"></div>
 <script>

 var treeSource;

 $.ajax({

 type: "POST",
    url : "/zen/zx4/api/anon/getPunterTree",
    cache: false,
    contentType: 'application/json;',
    dataType: "json",
     success: function(data) {
       var result = $.parseJSON(JSON.stringify(data));
       alert(JSON.stringify(data));
       if (result.status != 'OK')
       {
         alert(result.message);
         return;
       }
       alert(result.result);
       treeSource = result.result;

       $('#tree').tree({
             dataSource: treeSource,
             imageUrlField: 'image'
         });
     }
   });


 </script>
</body>
</html>
