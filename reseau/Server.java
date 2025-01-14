package Echecs.reseau;

import Echecs.reseau;
import java.net.ServerSocket;
import java.net.Socket;



public class Server extends Thread {
    

    private static int PORT = 8585;
    private GameServer gs;

    public Server(){
        start();
    }

    public void run(){
        
        try{
            ServerSocket socket = new ServerSocket(PORT);
            System.out.println("[SERVEUR] : Lancement du serveur.");
            gs = new GameServer();

            do{
			    System.out.println("[SERVER]-[INFO] En attente de nouvelles connexions...");
			    Socket s = socket.accept();
			    gs.addJoueur(new Client(s));    
			} while(gs.getNombreClients() <2);
			System.out.println("[SERVER] Lancement d'une nouvelle partie");

        } catch(Exception e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args){
        Server server = new Server();
        server.start();
    }

}


