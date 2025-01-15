package Echecs.Reseau;

import Echecs.Reseau;
import java.net.ServerSocket;
import java.net.Socket;



public class Server extends Thread {
    

    private static int PORT = 9090;
    private GameServer gs;

    public Server(){
        start();
    }

    public void run(){
        
        try{
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("[SERVEUR] : Lancement du serveur.");
            gs = new GameServer();

            while(gs.getNombreClients() <2){
			    System.out.println("[SERVER]-[INFO] En attente de nouvelles connexions...");
                Socket s;
			    try {
                    s = serverSocket.accept();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Client client = new Client(s);
                if (client.getClientInputStream() != null && client.getClientOutputStream() != null) {
                    gs.addJoueur(client);
                } else {
                    System.err.println("[SERVER] : Flux du client non initialisé, connexion ignorée.");
                }
			}
			System.out.println("[SERVER] Lancement d'une nouvelle partie");

        } catch(Exception e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args){
        new Server();
    }

}


