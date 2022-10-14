import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class Handler1 implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    String str = "anewstringtoadd";
    ArrayList<String> items = new ArrayList<String>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("String: %s", str);
        }
        else {
            // System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    items.add(parameters[1]);
                    return String.format("Item added: %s!", parameters[1]);
                }
            } else if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    ArrayList<String> itemHasS = new ArrayList<String>();
                    for(int i = 0; i < items.size(); i++) 
                        if(items.get(i).contains(parameters[1]))
                            itemHasS.add(items.get(i));
                    return itemHasS.toString();
                }
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler1());
    }
}
