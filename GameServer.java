

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


/*
 * Cette classe permet de faire l'interface entre le Serveur et les clients
 */
public class GameServer {

    private ArrayList<Client> clients;
    private ServerPlayer jBlanc;
    private ServerPlayer jNoir;

    public GameServer() {
        clients = new ArrayList<Client>();
    }

    // Ajouter un joueur
    public void addJoueur(Client client) {
        this.clients.add(client);
    }

    public void showInfosPlayer(ServerPlayer player){
        System.out.println("[SERVEUR]-[INFO]"+player.getJoueur().toString());
    }

    /* Lancement d’une partie avec dénomination du numéro du joueur */
    /* Lancement d’une partie avec dénomination du numéro du joueur */
    public void Ready() {
        System.out.println("[SERVEUR] : Nombre de clients connectés : "+clients.size());
        if (clients.size() == 2) {

            /* Définition des joueurs */
            try {
                String nomBlanc = ReceiveStringMessage(clients.get(0), "nom");
                jBlanc = new ServerPlayer(clients.get(0), new Joueur(nomBlanc, new Chrono(10, 0)));

                showInfosPlayer(jBlanc);
                String nomNoir = ReceiveStringMessage(clients.get(1), "nom");
                jNoir = new ServerPlayer(clients.get(1), new Joueur(nomNoir, new Chrono(10, 0)));
                showInfosPlayer(jNoir);

                System.out.println("[SERVER]-[INFO] : Deux joueurs prêts à s'affronter !");

                // Démarrer la partie
                startGame();
                
            } catch (IOException | ClassNotFoundException e) {
                Error("Erreur lors de la réception des informations des joueurs : " + e.getMessage());
            }
        } else {
            Error("Il faut deux joueurs pour lancer une partie !");
        }
    }

    // Démarrer la partie
    private void startGame() {
        // Logique de démarrage de la partie
        System.out.println("[SERVER]-[INFO] : Partie commencée !");
    }

    // Recevoir un message du client
    public void ReceiveMessage(Client client) {
        try {
            ObjectInputStream in = client.getClientInputStream();
            Message message;

            while (true) {
                message = (Message) in.readObject();

                switch (message.getType()) {
                    case "CONNECTION":
                        System.out.println("[SERVER] : " + message.getSender() + " prêt à jouer !");
                        break;
                    case "CHECK":
                        System.out.println("[SERVER] : Connexion active avec " + message.getSender());
                        break;
                    case "RULES":
                        System.out.println("[SERVER]-[RULES] : " + message.getData());
                        break;
                    case "BOARD":
                        System.out.println("[SERVER]-[BOARD] : " + message.getData());
                        break;
                    default:
                        System.out.println("[SERVER] : Message inconnu reçu.");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            Error("Impossible de lire le message du client : " + e.getMessage());
        }
    }
       // Recevoir un message contenant une chaîne spécifique (nom ou couleur d'un joueur)
       public String ReceiveStringMessage(Client client, String type) throws IOException, ClassNotFoundException {
        ObjectInputStream in = client.getClientInputStream();
    
        try {
            System.out.println("[SERVER] Attente d'un message de type " + type + "...");
            Object obj = in.readObject();
    
            if (obj instanceof Message) {

                System.out.println("Objet bien de type message");
                Message messageString = (Message) obj;
    
                if (type.equals(messageString.getType())) {
                    System.out.println("[SERVER] Message reçu : " + messageString.getData());
                    return messageString.getData();
                } else {
                    System.err.println("[SERVER] Type de message inattendu. Attendu : " + type + ", Reçu : " + messageString.getType());
                    throw new IOException("Type de message inattendu.");
                }
            } else {
                System.err.println("[SERVER] Objet non reconnu reçu : " + obj.getClass().getName());
                throw new IOException("Objet reçu non reconnu.");
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[SERVER]-[ERROR] Erreur lors de la réception : " + e.getMessage());
            throw e;
        }
    }
    
    // Envoyer un message à un client spécifique
    public void sendMessage(Client client, Message message) {
        try {
            ObjectOutputStream out = client.getClientOutputStream();
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            Error("Impossible d'envoyer le message au client : " + e.getMessage());
        }
    }

    // Méthode pour afficher un message d'erreur
    public void Error(String message) {
        String contenu = "[SERVER]-[ERROR] : ";
        contenu += message;
        System.out.println(contenu + "\n");
    }


    public int getNombreClients(){
        return clients.size();
    }
}
