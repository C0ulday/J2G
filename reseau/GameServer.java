package Echecs.reseau;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import Echecs.Joueur;
import Echecs.Message;
import Echecs.Chrono;
import Echecs.reseau.ServerPlayer;

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
        System.out.println("[SERVER]-[INFO] : Ajout d'un nouveau joueur");
    }

    /* Lancement d’une partie avec dénomination du numéro du joueur */
    public void Ready() {
        if (clients.size() == 2) {

            /* Définition des joueurs */
            String nomBlanc = ReceiveStringMessage(clients.get(0));
            String couleurBlanc = ReceiveStringMessage(clients.get(0));
            jBlanc = new ServerPlayer(clients.get(0), new Joueur(nomBlanc, couleurBlanc, new Chrono(0, 10, 0)));

            String nomNoir = ReceiveStringMessage(clients.get(1));
            String couleurNoir = ReceiveStringMessage(clients.get(1));
            jNoir = new ServerPlayer(clients.get(1), new Joueur(nomNoir, couleurNoir, new Chrono(0, 10, 0)));

            System.out.println("[SERVER]-[INFO] : Deux joueurs prêts à s'affronter !");
            
            // Démarrer la partie
            startGame();
        } else {
            // Message d'erreur
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
            ObjectInputStream in = client.getClientInputStream();  // Récupérer le flux d'entrée du client
            Message message;

            while (true) {
                message = (Message) in.readObject();  // Lire le message envoyé par le client

                if ("CONNECTION".equals(message.getType())) {
                    System.out.println("[SERVEUR] : " + message.getSender() + " prêt à jouer !");
                }

                if ("CHECK".equals(message.getType())) {
                    System.out.println("[CLIENT] : " + message.getSender() + " : Connexion active.");
                }

                if ("RULES".equals(message.getType())) {
                    System.out.println("[CLIENT]-[REGLES]: " + message.getData());
                }

                if ("BOARD".equals(message.getType())) {
                    System.out.println("[CLIENT]-[BOARD]: " + message.getData());
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            Error("Impossible de lire le message du client");
            e.printStackTrace();
        }
    }

    // Recevoir un message contenant le nom ou la couleur d'un joueur
    public String ReceiveStringMessage(Client client) {
        try {
            ObjectInputStream in = client.getClientInputStream();  // Récupérer le flux d'entrée du client
            Message messageString = (Message) in.readObject();  // Lire un objet Message

            if ("nom".equals(messageString.getType())) {
                return messageString.getData();
            }

            if ("couleur".equals(messageString.getType())) {
                return messageString.getData();
            }
        } catch (IOException | ClassNotFoundException e) {
            Error("Impossible de lire le message du client");
            e.printStackTrace();
        }

        return "";  // Retourner une chaîne vide en cas d'erreur
    }

    // Envoyer un message à un client spécifique
    public void sendMessage(Client client, Message message) {
        try {
            ObjectOutputStream out = client.getClientOutputStream();  // Récupérer le flux de sortie du client
            out.writeObject(message);
            out.flush();  // Assurer l'envoi immédiat du message
        } catch (IOException e) {
            Error("Impossible d'envoyer le message au client");
            e.printStackTrace();
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
