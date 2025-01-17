
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
    
            // Lancer le client auto-connecté dans un thread séparé
            new Thread(() -> {
                try {
                    Thread.sleep(500); // Attendre que le serveur soit prêt
                    Socket selfSocket = new Socket("localhost", PORT);
                    Client selfClient = new Client(selfSocket);
                    if (selfClient.getClientInputStream() != null && selfClient.getClientOutputStream() != null) {
                        gs.addJoueur(selfClient);
                        System.out.println("[SERVEUR] : Client auto-connecté ajouté avec succès.");
                    } else {
                        System.err.println("[SERVEUR] : Flux non initialisés pour le client virtuel.");
                    }
                } catch (IOException | InterruptedException e) {
                    System.err.println("[SERVEUR] : Erreur lors de la connexion auto : " + e.getMessage());
                }
            }).start();
    
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


