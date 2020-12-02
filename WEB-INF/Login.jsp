<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>IFT287 - Jardin Collectif</title>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
  </head>
  <body>
    <div class="container">
      <h1 class="text-center">Connexion/Inscription</h1>
			<div class="col-md-4 offset-md-4">
        <form action="Login" method="POST">
          <div class="form-group">
            <label for="username">Nom d'utilisateur</label>
            <input class="form-control" type="text" name="username" placeholder="Nom">
          </div>
          <div class="form-group">
            <label for="password">Mot de passe</label>
            <input class="form-control" type="password" name="password" placeholder="Mot de passe">
          </div>
          <div class="row">
			    	<div class="col-md-6">
						  <input class="btn btn-primary" type="SUBMIT" name="login" value="Se connecter">
					  </div>
					  <div class="col-md-6 text-right">
						  <input class="btn btn-outline-primary" type="SUBMIT" name="register" value="S'inscrire" onclick="form.action='Register';">
					  </div>
				  </div>
        </form>
			</div>
    </div>
  </body>
</html>