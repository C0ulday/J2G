
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

public class Serveur {
    
    private static int PORT = 8585;
    /*Liste des clients connectés */
    private static List<Socket> clientsConnected = new ArrayList<>(); 

    public static void main(String args[]) throws IOException {

        try {

            System.out.println("Je suis le serveur");
            // Connexion au port
            ServerSocket serveurSocket = new ServerSocket(PORT);
            System.out.println("Serveur en attente...'");
            
            while (true) {
                
                // Accepter une connexion
                Socket clientSocket = serveurSocket.accept();
                // Ajout du nouveau client à la liste de clients connectés
                clientsConnected.add(clientSocket);
                
                System.out.println("Nouvelle connexion établie. Nombre total de connexions actives : " + clientsConnected.size());
                
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                // Crée un nouveau thread pour gérer la communication avec ce client
                new Thread(new Client(clientSocket)).start();

                reader.close();

                System.out.println(reader.readLine());
                // Créer un thread pour gérer chaque client de manière indépendante
                //new Thread(new clientHandler(clientSocket)).start();
            }

        } catch (Exception e) {

            System.out.println("[ERREUR]" + e);
        }

    }

}
