package util;

import io.netty.handler.timeout.ReadTimeoutException;
import header.RequestBody;
import model.UserRegister;

import javax.net.ssl.SSLHandshakeException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;

public class RequestUtil {
    public static String URL_REGISTER = "https://dia-backend.numbersprotocol.io/auth/users/";

    public synchronized static String postRequest(RequestBody requestBody,String url) throws IOException {
        URL uri = new URL(url);
        Proxy webProxy
                = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.0.162", 10000));
        HttpURLConnection httpURLConnection
                = (HttpURLConnection) uri.openConnection(webProxy);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod(requestBody.getMethod());
        httpURLConnection.addRequestProperty("Content-Type","application/json");
        if (requestBody.getXapiKey()!=null){
            httpURLConnection.addRequestProperty("x-api-key",requestBody.getXapiKey());
        }
        httpURLConnection.addRequestProperty("User-Agent",requestBody.getUserAgent());
        httpURLConnection.addRequestProperty("x-requested-with",requestBody.xrequestWith);
        httpURLConnection.setReadTimeout(60000);

        StringBuilder stringBuilder = new StringBuilder();
        if (requestBody.getData()!=null) {
            try (OutputStream outputStream = httpURLConnection.getOutputStream()) {
                outputStream.write(requestBody.getJsonBody().getBytes());
                outputStream.flush();
            }
        }
        UserRegister userRegister = (UserRegister) requestBody.getData();
        try {

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_CREATED || httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println(userRegister.getEmail()+" create successfully");
            }
            else if (httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_BAD_REQUEST){

                System.out.println(userRegister.getEmail()+" already exist");
            }
        }
        catch (ReadTimeoutException e){
            System.out.println("Request timeout try again: ");
            postRequest(requestBody,url);
        }
        catch (SocketTimeoutException e){
            try {
                Thread.sleep(5000);
                System.out.println("Socket time out. Waiting...");
                postRequest(requestBody,url);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        catch (SSLHandshakeException e){
            try {
                Thread.sleep(30000);
                System.out.println("Socket time out. Waiting...");
                postRequest(requestBody,url);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        catch (IOException e){
            if (httpURLConnection.getResponseCode()==500){
                System.out.println("Server busy");
                postRequest(requestBody,url);
            }
        }
        return stringBuilder.toString();

    }

    public synchronized static String postRequest2(RequestBody requestBody,String url) throws IOException {
        URL uri = new URL(url);
        Proxy webProxy
                = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.0.162", 10001));
        HttpURLConnection httpURLConnection
                = (HttpURLConnection) uri.openConnection(webProxy);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod(requestBody.getMethod());
        httpURLConnection.addRequestProperty("Content-Type","application/json");
        if (requestBody.getXapiKey()!=null){
            httpURLConnection.addRequestProperty("x-api-key",requestBody.getXapiKey());
        }
        httpURLConnection.addRequestProperty("User-Agent",requestBody.getUserAgent());
        httpURLConnection.addRequestProperty("x-requested-with",requestBody.xrequestWith);
        httpURLConnection.setReadTimeout(60000);

        StringBuilder stringBuilder = new StringBuilder();
        if (requestBody.getData()!=null) {
            try (OutputStream outputStream = httpURLConnection.getOutputStream()) {
                outputStream.write(requestBody.getJsonBody().getBytes());
                outputStream.flush();
            }
        }
        UserRegister userRegister = (UserRegister) requestBody.getData();
        try {

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_CREATED || httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println(userRegister.getEmail()+" create successfully");
            }
            else if (httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_BAD_REQUEST){

                System.out.println(userRegister.getEmail()+" already exist");
            }
        }
        catch (ReadTimeoutException e){
            System.out.println("Request timeout try again: ");
            postRequest(requestBody,url);
        }
        catch (SocketTimeoutException e){
            try {
                Thread.sleep(5000);
                System.out.println("Socket time out. Waiting...");
                postRequest(requestBody,url);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        catch (SSLHandshakeException e){
            try {
                Thread.sleep(30000);
                System.out.println("Socket time out. Waiting...");
                postRequest(requestBody,url);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        catch (IOException e){
            if (httpURLConnection.getResponseCode()==500){
                System.out.println("Server busy");
                postRequest(requestBody,url);
            }
        }
        return stringBuilder.toString();

    }

    public synchronized static String asyncRequest(RequestBody requestBody) throws IOException {
        URL uri = new URL(requestBody.getUrl());
        /*Proxy webProxy
                = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.0.162", 10000));
        HttpURLConnection httpURLConnection
                = (HttpURLConnection) uri.openConnection(webProxy);*/
        HttpURLConnection httpURLConnection = (HttpURLConnection) uri.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod(requestBody.getMethod());
        httpURLConnection.addRequestProperty("Content-Type","application/json");
        if (requestBody.getXapiKey()!=null){
            httpURLConnection.addRequestProperty("x-api-key",requestBody.getXapiKey());
        }
        if (requestBody.getToken()!=null){
            httpURLConnection.addRequestProperty("Authorization","token "+requestBody.getXapiKey());
        }
        httpURLConnection.addRequestProperty("User-Agent",requestBody.getUserAgent());
        httpURLConnection.addRequestProperty("x-requested-with",requestBody.xrequestWith);
        httpURLConnection.setReadTimeout(60000);

        StringBuilder stringBuilder = new StringBuilder();
        if (requestBody.getData()!=null) {
            try (OutputStream outputStream = httpURLConnection.getOutputStream()) {
                outputStream.write(requestBody.getJsonBody().getBytes());
                outputStream.flush();
            }
        }
        try {

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_CREATED || httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))) {
                    String line = "";

                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                }
            }

        }
        catch (ReadTimeoutException e){
            System.out.println("Request timeout try again: ");
            asyncRequest(requestBody);
        }
        catch (SocketTimeoutException e){
            try {
                Thread.sleep(5000);
                System.out.println("Socket time out. Waiting...");
                asyncRequest(requestBody);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        catch (IOException e){
            if (httpURLConnection.getResponseCode()==500){
                System.out.println("Server busy");
                asyncRequest(requestBody);
            }
        }
        return stringBuilder.toString();

    }

}
