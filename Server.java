
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



public class Server extends Thread {
    

    private static int PORT = 9090;
    private GameServer gs;

    public Server(){
        start();
    }

    public void run() {
    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
        System.out.println("[SERVEUR] : Serveur lancé sur le port " + PORT);
        gs = new GameServer();

        // Ajout du serveur lui-même en tant que client
        // Créer un client qui se connecte à lui-même (l'ordinateur hôte joue aussi)
        Socket selfSocket = new Socket("localhost", PORT);
        Client selfClient = new Client(selfSocket);
        if (selfClient.getClientInputStream() != null && selfClient.getClientOutputStream() != null) {
            gs.addJoueur(selfClient);
        } else {
            System.err.println("[SERVEUR] : Flux non initialisés pour le client virtuel.");
        }
        while (gs.getNombreClients() < 2) {
            System.out.println("[SERVEUR] : En attente de connexion des clients...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("[SERVEUR] : Nouvelle connexion acceptée.");
            Client client = new Client(clientSocket);
            if (client.getClientInputStream() != null && client.getClientOutputStream() != null) {
                gs.addJoueur(client);
            } else {
                System.err.println("[SERVEUR] : Flux non initialisés pour ce client.");
            }
        }
        System.out.println("[SERVEUR] : Deux clients connectés, lancement de la partie...");
        gs.Ready();
        while(true){
        }

        } catch (IOException e) {
            System.err.println("[SERVEUR] : Erreur lors de l'exécution du serveur: " + e.getMessage());
        }
    }

    public int getPORT(){
        return PORT;
    }

    public static void main(String[] args) {
        new Server();
    }

}


