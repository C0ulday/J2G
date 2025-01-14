package Echecs.reseau;

import Echecs.Joueur;
import Echecs.Message;

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
    private ObjectOutput out; 
    
    private Joueur joueur;


    public Client(String ServerAddress, int ServerPort){

        this.ServerAddress = ServerAddress;
        this.ServerPort = ServerPort;
    }

    public void Ready(){

        try{

            socket = new Socket(ServerAddress,ServerPort);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("[CLIENT] : Connecté au serveur à " + ServerAddress + ":" + ServerPort);

            // Envoyer ses infos de connexion
            Connection();
            System.out.println("[CLIENT] : En attente de connexion...");

            // En attente du serveur

            new Thread(this::ReceiveMessage).start();

        } catch (IOException e) {
            System.err.println("[CLIENT] : Erreur lors de la connexion au serveur.");
            e.printStackTrace();
        }
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


    }

    public void ReceiveMessage(){
        try {
            while (true) {
                Message message = (Message) in.readObject();
                System.out.println("[SERVEUR] : " + message);
            }
        } catch (IOException e) {
            System.err.println("[CLIENT] : Connexion perdue avec le serveur.");
        }
    }

}
