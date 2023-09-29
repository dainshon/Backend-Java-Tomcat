package Controller;

import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static webserver.URL.HOME_URL;
import static webserver.URL.ROOT_URL;

public class HomeController implements Controller {

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            //log.log(Level.SEVERE, e.getMessage());
        }
    }

    public void responseBody() throws IOException {
        byte[] body = Files.readAllBytes(Paths.get(ROOT_URL.getValue() + HOME_URL.getValue()));
        //System.out.println(new String(bytes));
    }

    @Override
    public void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            //log.log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void execute(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException {
        byte[] body = httpResponse.forward("/index.html");

        response200Header(httpResponse.dos, body.length);
        responseBody(httpResponse.dos, body);
    }
}