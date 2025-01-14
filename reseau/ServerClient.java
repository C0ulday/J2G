package Echecs.reseau;
import java.io.*;
import java.net.Socket;

/*
 * 
 * Réprésentation du client sur le serveur
 * 
 */
public class ServerClient {

    // Socket utilisé pour établir une connexion entre le client et le serveur
    private Socket socket;

    // Canal d'entrée permettant de recevoir les données
    InputStream canal;

    // Lecteur tamponné pour lire les données
    BufferedReader in;

    // Canal de sortie pour envoyer des messages au serveur
    PrintWriter out;

   // Constructeur de la classe Client, qui initialise le socket du client.
   
   public ServerClient(Socket socket) {
    
    this.socket = socket;
        
        try {
            // Initialisation du flux d'entrée pour lire les données reçues via le socket
            this.canal = socket.getInputStream();

            // Initialisation du BufferedReader en l'associant au flux d'entrée
            // Cela permet une lecture facile des messages sous forme de texte
            this.in = new BufferedReader(new InputStreamReader(canal));

            // Initialisation du flux de sortie pour envoyer des messages
            // Auto-flush est activé pour forcer l'envoi immédiat des données après un println
            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            // Gestion des erreurs liées à l'initialisation des flux.
            e.printStackTrace();
        }
    }
}
