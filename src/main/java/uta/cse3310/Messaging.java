package uta.cse3310;
import java.util.Arrays;
import java.util.List;
import org.java_websocket.WebSocket;
import com.google.gson.Gson;

public class Messaging {
    private String msgHistory;
    public String msg;
    private String[] filter = {"test1", "test2"};
    private App webSocketServer;

    public Messaging(App webSocketServer) {
        this.webSocketServer = webSocketServer;
    }

    public String updateTextBox(String msg) {
        Gson gson = new Gson();
        String jsonMessage = gson.toJson(msg);

        webSocketServer.broadcast(jsonMessage);

        return "Sent";
    }

    public String sendMsg(String input) {
        //when message is sent check in filter and do update so other person can see it
        //if bad word then dont update for it
        List<String> filterList = Arrays.asList(filter);
        if (filterList.contains(input)) {
            updateTextBox("*******");
            return "Unfriendly Language";
        } 
        else {
            updateTextBox(input);
            return "Successful";
        }
    }
}
