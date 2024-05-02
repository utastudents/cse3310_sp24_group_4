package uta.cse3310;
import java.util.Arrays;
import java.util.List;
import org.java_websocket.WebSocket;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Messaging {
    private String[] msgHistory;
    public String msg;
    private String[] filter = {"test1", "test2", "fuck", "bitch", "ass"};
    private App webSocketServer;

    public Messaging(App webSocketServer) {
        this.webSocketServer = webSocketServer;
    }

    public String updateTextBox(String msg) {
        Gson gson = new Gson();

        if (msg == "*******") {
            String jsonMessage = gson.toJson("msg: "+ msg);
            //System.out.println("broadcasting: " + jsonMessage);
            webSocketServer.broadcast(jsonMessage);
        } else {
            JsonObject obj = new JsonObject();
            obj.addProperty("type", "message");
            obj.addProperty("msg", msg);
            //String jsonMessage = gson.toJson(msg);
            //System.out.println("broadcasting: " + jsonMessage);
            webSocketServer.broadcast(obj.toString());
        }

        return "Sent";
    }

    public String sendMsg(String input) {
        //when message is sent check in filter and do update so other person can see it
        //if bad word then dont update for it
        //System.out.println(input);
        List<String> filterList = Arrays.asList(filter);
        if (filterList.contains(input.substring(5, input.length()))) {
            //System.out.println(input.substring(4, input.length()));
            updateTextBox("*******");
            return "Unfriendly Language";
        } 
        else {
            updateTextBox(input);
            return "Successful";
        }
    }
}
