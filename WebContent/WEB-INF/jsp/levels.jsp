<html>
<head>
    <meta charset="utf-8" />
    <title>Bootstrap 4 TreeView</title>
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://unpkg.com/gijgo@1.9.11/js/gijgo.min.js" type="text/javascript"></script>
    <link href="https://unpkg.com/gijgo@1.9.11/css/gijgo.min.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <div class="container-fluid">
        <div id="tree"></div>
    </div>
    <script type="text/javascript">
            $(document).ready(function () {
                $('#tree').tree({
                    uiLibrary: 'bootstrap4',
                    dataSource: '/zen/zx4/getPunters',
                    primaryKey: 'id',
                    imageUrlField: 'image'
                });
            });
    </script>
</body>
</html>
