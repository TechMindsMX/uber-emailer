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
        <table align="center">
            <thead>
                <tr>

                    <th>
                        <h1>Asignación de Comprador</h1>            
                    </th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    
                    <td>
                        <HR width=100% align="center">
                        <p>Estimado usuario, le informamos que se ha asignado el comprador <span class="text-blue">${nombre comprador}</span> con correo electrónco: <span class="text-blue">${email comprador}</span> para la empresa <span class="text-blue">${empresa}</span></p>        
                    </td>
                </tr>
            </tbody>
        </table>        
    </body>
</html>
