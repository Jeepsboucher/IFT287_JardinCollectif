
<!DOCTYPE html>
<html>
    <head>
        <title>Liste des membres</title>
        <meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=ISO-8859-1">
        <meta NAME="author" CONTENT="Marc Frappier">
        <meta NAME="description"
        CONTENT="Menu d'un membre">
    </head>
    <body>
        <h1>Jardin Collectif</h1>
        <h2>Liste des membres</h2>
        <br>
        <br>
        <form ACTION="ListeMembre" METHOD="GET">
        <%
          MemberRepository MemberRepository = (MemberRepository) session.getAttribute("memberRepository");
          List memberList = MemberRepository.retrieveAll();
          // affichage de la liste des prêts du membre
          if ( !memberList.isEmpty() )
            {
        %>
            <table
            style="width: 50%; text-align: left; margin-left: auto; margin-right: auto;"
            border="1" cellspacing="2" cellpadding="2">
              <tbody>
            <!-- titre des colonnes -->
                <tr>
                <td style="vertical-align: top;">First name<br></td>
                <td style="vertical-align: top;">Last name<br></td>
                <td style="vertical-align: top;">Is admin<br></td>
                </tr>
        <%
            ListIterator it = listePret.listIterator();
            while (it.hasNext())
              {
              TupleLivre tuplelivre = (TupleLivre) it.next();
        %>
                <tr>
                <td style="vertical-align: top;"><INPUT TYPE="RADIO"
                  NAME="pretSelectionne" VALUE="<%= tuplelivre.idLivre %>"><br></td>
                <td style="vertical-align: top;"><%= ServletUtilities.filter(tuplelivre.titre) %><br></td>
                <td style="vertical-align: top;"><%= ServletUtilities.filter(tuplelivre.auteur) %><br></td>
                <td style="vertical-align: top;"><%= tuplelivre.datePret %><br></td>
                </tr>
        <%
              }
        %>
              </tbody>
            </table>
            <BR>
            <INPUT TYPE="SUBMIT" NAME="retourner" VALUE="Retourner">
            <INPUT TYPE="SUBMIT" NAME="renouveler" VALUE="Renouveler">
            <BR>
        <%
            }
          else
            {
        %>
            aucun prêt en cours <BR>
        <%
            }
        %>
        </body>
</html>