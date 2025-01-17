

public class Message {
    

    private String type;
    private String sender;
    private String data;
    private String date;


    public Message(String type, String sender, String data, String date){
        this.type = type;
        this.sender = sender;
        this.data = data;
        this.date = date;
    }

    public String getType(){
        return type;
    }

    public String getSender(){
        return sender;
    }

    public String getData(){
        return data;
    }

    public String getDate(){
        return date;
    }

    public String toString(){
        return "Type : " + type + "Sender : " + sender + "Data" + data + "Date +" + date + "\n";
    }
}
