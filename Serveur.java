
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


/*
 * 
 * Code Serveur
 * Protocle de communication : Socket
 * Format de messages : JSON
 */

 /* Implémentation de la classe Runnable afin d'utiliser des Threads et gérer séparément plusieurs clients à la fois */
public class Serveur {
    
    private static int PORT = 8585;
    /*Liste des clients connectés */
    // NB : Le "static" est comme "global"
    private static List<Socket> clientsConnected = new ArrayList<>(); 

    public static void main(String args[]) throws IOException {

        try {

            System.out.println("Je suis le serveur");
            // Connexion au port
            ServerSocket serveurSocket = new ServerSocket(PORT);
            System.out.println("Serveur en attente...'");
            
            /*Tant qu'on a pas 2 connexions, on continue d'accepter */
            while (clientsConnected.size()<2) {
                
                // Accepter une connexion
                Socket clientSocket = serveurSocket.accept();
                // Ajout du nouveau client à la liste de clients connectés
                clientsConnected.add(clientSocket);
                
                System.out.println("Nouvelle connexion établie. Nombre total de connexions actives : " + clientsConnected.size());

                // Pour lire
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                System.out.println(reader.readLine());

                // Crée un nouveau thread pour gérer la communication avec ce client
                new Thread(new ClientHandler(clientSocket)).start();
            }
            System.out.println("Nombre total de connexions actives : " + clientsConnected.size());
            System.out.println("Le jeu peut commencer !");


        } catch (Exception e) {

            System.out.println("[ERREUR]" + e);
        }

    }

}

/* Classe privée pour gérer la communication avec chaque client*/
class ClientHandler implements Runnable {

    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            // Crée un BufferedReader pour lire les messages du client
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Message du client : " + message);
            }

        } catch (IOException e) {
            System.out.println("Erreur de communication avec le client : " + e.getMessage());
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();  // Fermeture du socket à la fin
                }
            } catch (IOException e) {
                System.out.println("Erreur lors de la fermeture du socket : " + e.getMessage());
            }
        }
    }
}