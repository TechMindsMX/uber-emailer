<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Notificación</title>
        <!-- Bootstrap core CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">    
        <!-- Custom styles for this template -->
        <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
     <style type="text/css">
        body
        {
            padding-top: 50px;
        }

        .starter-template
        {
            padding: 40px 15px;
            text-align: center;
        }

        .text-blue
        {
            color: #5bb2ef;
        }
    </style>
    </head>
    <body>
        <header>
            <h1>¿Necesitas resetear tu contraseña?</h1>
        </header>
        <div class="container">
            <div class="text-left"> 
</div>
            <div class="text-left">
                <div class="container">
                    <div class="row">
                        <div class="clearfix clear-columns text-blue">
                            <h3></h3>
                            <hr>
                        </div>
                    </div>
                </div>
                <p>Alguien ha solicitado resetear tu contraseña</p>
                <p>Si solicitaste esto, por favor da clic en el siguiente link para resetear</p>
                <a href="${token}">${token}</a>
                <br>
                <p><br></p>
                <p>Si tu no solicitaste o quieres conservar la contraseña actual, simplemente ignora este mensaje</p>
                <p class="text-center"><br></p>
            </div>
        </div>
        <!-- /.container -->
        <!-- Bootstrap core JavaScript
    ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="assets/js/jquery.min.js"></script>
        <script src="bootstrap/js/bootstrap.min.js"></script>
        <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
        <script src="assets/js/ie10-viewport-bug-workaround.js"></script>
    </body>
</html>
