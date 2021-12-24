package util;

import io.netty.handler.timeout.ReadTimeoutException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

public class DemoUtil {
    public static String URL_REGISTER = "https://dia-backend.numbersprotocol.io/auth/users/";

    public synchronized static String postRequest() throws IOException {
        URL uri = new URL("https://api.ipify.org?format=json");
        Proxy webProxy
                = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.0.162", 10000));
        HttpURLConnection httpURLConnection
                = (HttpURLConnection) uri.openConnection(webProxy);
        httpURLConnection.setDoOutput(true);

        httpURLConnection.setReadTimeout(60000);

        StringBuilder stringBuilder = new StringBuilder();

        try {

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_CREATED || httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))) {
                    String line = "";

                    while ((line = bufferedReader.readLine()) != null) {
                        System.out.println(line);
                    }
                }
            }

        }
        catch (ReadTimeoutException e){
            System.out.println("Request timeout try again: ");

        }
        catch (IOException e){
            if (httpURLConnection.getResponseCode()==500){
                System.out.println("Server busy");
            }
        }
        return stringBuilder.toString();

    }

    public static void main(String[] args) {
        try {
            postRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
