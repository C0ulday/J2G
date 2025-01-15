package Echecs.Reseau;

import Echecs.Message;
import java.awt.SystemTray;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;



public class Client {
    

    private String ServerAddress;
    private int ServerPort;
    private Socket socket;

    private ObjectInputStream in;
    private ObjectOutputStream out; 

    public Client(String ServerAddress, int ServerPort){
        try{
            this.ServerAddress = ServerAddress;
            this.ServerPort = ServerPort;
            this.socket = new Socket(ServerAddress,ServerPort);
            this.in = new ObjectInputStream(socket.getInputStream());
            this.out = new ObjectOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            System.out.println("Impossible de créer un Socket pour le Client " + e.getMessage());
        }
    }

    public Client(Socket socket){
        try { 
            this.socket = socket;
            this.in = new ObjectInputStream(socket.getInputStream());
            this.out = new ObjectOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            System.out.println("Impossible de créer un Socket pour le Client " + e.getMessage());
        }
    }


    public void Ready(){

        System.out.println("[CLIENT] : Connecté au serveur à " + ServerAddress + ":" + ServerPort);

        // Envoyer ses infos de connexion
        Connection();
        System.out.println("[CLIENT] : En attente de connexion...");

        // En attente du serveur
        new Thread(this::ReceiveMessage).start();

    
    }

    public void Connection(){

        String nom;
        String couleur;

        Scanner scanner = new Scanner(System.in);
        System.out.print("Entrez votre nom : ");
        nom = scanner.nextLine();
        System.out.print("Choisissez votre couleur : ");
        couleur = scanner.nextLine();

        // Encapsulation du message à envoyer

        LocalDateTime maintenant = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String temps = maintenant.format(formatter);

        Message messNom = new Message("nom",""+socket,nom,temps);
        Message messCouleur= new Message("couleur",""+socket,couleur,temps);
        
        try {
            out.writeObject(messNom);
            out.writeObject(messCouleur);
            System.out.println("[CLIENT] : Informations envoyées au serveur.");
        } catch (IOException e) {
            System.err.println("[CLIENT] : Erreur lors de l'envoi des informations.");
        }
    }

    public void CheckConnection(){

        LocalDateTime maintenant = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String temps = maintenant.format(formatter);

        Message mess = new Message("CHECK",""+socket,"",temps);
        try{
            out.writeObject(mess);

        } catch (IOException e){
            Error("Impossible de tester la connexion avec le serveur");
            e.printStackTrace();
        }

    }

    public void ReceiveMessage(){
        try {
            while (true) {
                Message message = (Message) in.readObject();

                if ("CONNECTION".equals(message.getType())){
                    System.out.println("[CLIENT] : " + "prêt à jouer !");
                }

                if("CHECK".equals(message.getType())){
                    System.out.println("[CLIENT] : " + "Connexion active.");
                }

                if("RULES".equals(message.getType())){
                    System.out.println("[CLIENT]-[REGLES]: " + message.getData());
                }

                if("BOARD".equals(message.getType())){
                    System.out.println("[CLIENT]-[BOARD]: " + message.getData());
                }
            }
        } catch (IOException e) {
            Error("Impossible de lire le message du serveur");
            e.printStackTrace();
        }
    }

    public void Error(String message){

        String contenu = "[CLIENT]-[ERROR] :";
        contenu += message;
        System.out.println(contenu + "\n");

    }

    public ObjectInputStream getClientInputStream(){
        return in;
    }

    public ObjectOutputStream getClientOutputStream(){
        return out;
    }

    
    public static void main(String[] args){

        String serverAddress = "localhost";
        int serverPort = 9090;
        System.out.println("Creation du client");
        Client client = new Client(serverAddress,serverPort);
        System.out.println("[CLIENT] : Tentative de connexion au serveur à " + serverAddress + ":" + serverPort + "...");
        client.Ready();
    }

}
