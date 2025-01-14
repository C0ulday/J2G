package Echecs.reseau;

import Echecs.Joueur;



public class ServerPlayer {
    
    private ServerClient client;
    private Joueur joueur;

    public ServerPlayer(ServerClient client,Joueur joueur){
        this.client = client;
        this.joueur = joueur;
    }


    public ServerClient getClient(){
        return this.client;
    }

    public Joueur getJoueur(){
        return this.joueur;
    }


}
