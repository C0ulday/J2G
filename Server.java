import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

    private static final int PORT = 9090; // Port du serveur
    private GameServer gs; // Instance du GameServer pour gérer la logique de jeu et les clients

    public Server() {
        start(); // Démarre le thread du serveur
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("[SERVEUR] : Serveur lancé sur le port " + PORT);
            gs = new GameServer(); // Initialiser l'instance de GameServer

            // Gestion des connexions des clients externes
            while (gs.getNombreClients() < 2) {
                System.out.println("[SERVEUR] : En attente de connexion des clients...");
                try {
                    Socket clientSocket = serverSocket.accept(); // Accepter une nouvelle connexion client
                    System.out.println("[SERVEUR] : Nouvelle connexion acceptée.");
                    ajouterClient(clientSocket); // Ajouter le client connecté
                } catch (IOException e) {
                    System.err.println("[SERVEUR] : Erreur lors de l'acceptation d'un client : " + e.getMessage());
                }
            }

            System.out.println("[SERVEUR] : Deux clients connectés, lancement de la partie...");
            gs.Ready(); // Lancer la partie

        } catch (IOException e) {
            System.err.println("[SERVEUR] : Erreur lors de l'exécution du serveur : " + e.getMessage());
        }
    }

    private void ajouterClient(Socket clientSocket) {
        try {
            Client client = new Client(clientSocket);
            if (client.getClientInputStream() != null && client.getClientOutputStream() != null) {
                gs.addJoueur(client); // Ajouter le client au GameServer
                System.out.println("[SERVEUR] : Client ajouté avec succès.");
            } else {
                System.err.println("[SERVEUR] : Flux non initialisés pour ce client.");
                clientSocket.close(); // Fermer la connexion si les flux ne sont pas valides
            }
        } catch (IOException e) {
            System.err.println("[SERVEUR] : Erreur lors de l'ajout du client : " + e.getMessage());
        }
    }

    public int getPORT() {
        return PORT;
    }

    public static void main(String[] args) {
        new Server(); // Créer et démarrer une instance du serveur
    }
}
