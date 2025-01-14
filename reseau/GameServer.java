package Echecs.reseau;

import java.util.ArrayList;

import Echecs.Joueur;


/*
 * Cette classe permet de faire l'interface entre le Serveur et les clients
 * 
 * 
 */


public class GameServer{


    private ArrayList<ServerClient> clients;
    private ServerPlayer jBlanc;
    private ServerPlayer jNoir;

    public GameServer(){
        clients = new ArrayList<ServerClient>();
    }

    public void addJoueur(ServerClient client){
        this.clients.add(client);
        System.out.println("[SERVER]-[INFO] : Ajout d'un nouveau joueur");
    }

    /* Lancement d’une partie avec dénomination du numéro du joueur */
    public void Ready(){
        if(clients.size() ==2){

            /* Définition des joueurs */

            System.out.println("[SERVER]-[INFO] : Deux joueurs prêts à s'affronter !");
        // démarrer partie
        }
        // Message erreur
        Error("Il faut deux joueurs pour lancer une partie !");
    }

    public void Error(String message){

        String contenu = "[SERVER]-[ERROR] :";
        contenu += message;
        System.out.println(contenu + "\n");

    }
}