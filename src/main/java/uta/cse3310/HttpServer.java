package uta.cse3310;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.freeutils.httpserver.HTTPServer;
import net.freeutils.httpserver.HTTPServer.ContextHandler;
import net.freeutils.httpserver.HTTPServer.FileContextHandler;
import net.freeutils.httpserver.HTTPServer.Request;
import net.freeutils.httpserver.HTTPServer.Response;
import net.freeutils.httpserver.HTTPServer.VirtualHost;
import java.util.List;

// http server include is a GPL licensed package from
//            http://www.freeutils.net/source/jlhttp/

public class HttpServer {
    private Board board;
    private static final String HTML = "./html";
    int port = 8080;
    String dirname = HTML;

    public HttpServer(int portNum, String dirName) {
        port = portNum;
        dirname = dirName;
        this.board = new Board();
    }

    public void start() {
        try {
            File dir = new File(dirname);
            if (!dir.canRead())
                throw new FileNotFoundException(dir.getAbsolutePath());


            // set up server
            HTTPServer server = new HTTPServer(port);
            VirtualHost host = server.getVirtualHost(null); // default host
            host.setAllowGeneratedIndex(true); // with directory index pages
            host.addContext("/", new FileContextHandler(dir));
            host.addContext("/api/time", new ContextHandler() {
                public int serve(Request req, Response resp) throws IOException {
                    long now = System.currentTimeMillis();
                    resp.getHeaders().add("Content-Type", "text/plain");
                    resp.send(200, String.format("%tF %<tT", now));
                    return 0;
                }
            });

            // Add a context handler for serving the word grid
            host.addContext("/wordgrid", new ContextHandler() {
                public int serve(Request req, Response resp) throws IOException {
                    char[][] grid = board.getBoard();
                    String htmlGrid = convertGridToHTML(grid);
                    resp.getHeaders().add("Content-Type", "text/html");
                    resp.send(200, htmlGrid);
                    return 0;
                }
            });

            // Define endpoint to serve word bank HTML
            host.addContext("/wordbank", new ContextHandler() {
                public int serve(Request req, Response resp) throws IOException {
                    List<String> placedWords = board.getPlacedWords();
                    String htmlWordBank = convertWordBankToHTML(placedWords);
                    resp.getHeaders().add("Content-Type", "text/html");
                    resp.send(200, htmlWordBank);
                    return 0;
                }
            });
            

            server.start();

        } catch (Exception e) {
            System.err.println("error: " + e);
        }

    }
    
    private static String convertGridToHTML(char[][] grid) {
        StringBuilder html = new StringBuilder("<table>");
        for (char[] row : grid) {
            html.append("<tr>");
            for (char cell : row) {
                html.append("<td>").append(cell).append("</td>");
            }
            html.append("</tr>");
        }
        html.append("</table>");
        return html.toString();
    }

    private static String convertWordBankToHTML(List<String> placedWords) {
        StringBuilder html = new StringBuilder("<div id=\"wordBank\" style=\"border: 1px solid black; padding: 10px; max-width: 200px;\">");
        html.append("<h2>Word Bank</h2>\n");
        html.append("<ul>\n");
    
        for (String word : placedWords) {
            html.append("<li>").append(word).append("</li>\n");
        }
    
        html.append("</ul>\n");
        html.append("</div>\n");
    
        return html.toString();
      }

    public Board getBoard() {
        return board;
    }

}
