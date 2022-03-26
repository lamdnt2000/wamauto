package util;

import header.VerifyBody;
import io.netty.handler.timeout.ReadTimeoutException;
import header.RequestBody;
import model.UserRegister;
import okhttp3.*;
import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.net.ssl.SSLHandshakeException;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestUtil {
    public static String URL_REGISTER = "https://dia-backend.numbersprotocol.io/auth/users/";
    static final String COOKIES_HEADER = "Set-Cookie";
    public synchronized static String postRequest(RequestBody requestBody,String url) throws IOException {
        URL uri = new URL(url);
        Proxy webProxy
                = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.0.162", 10000));
        HttpURLConnection httpURLConnection
                = (HttpURLConnection) uri.openConnection(webProxy);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod(requestBody.getMethod());
        httpURLConnection.setRequestProperty("Content-Type","application/json.txt");
        if (requestBody.getXapiKey()!=null){
            httpURLConnection.addRequestProperty("x-api-key",requestBody.getXapiKey());
        }
        httpURLConnection.setRequestProperty("User-Agent",requestBody.getUserAgent());
        httpURLConnection.setRequestProperty("x-requested-with",requestBody.xrequestWith);
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
        HttpURLConnection httpURLConnection = (HttpURLConnection) uri.openConnection();
       /* Proxy webProxy
                = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.0.162", 10000));
        HttpURLConnection httpURLConnection
                = (HttpURLConnection) uri.openConnection(webProxy);*/
        httpURLConnection.setRequestMethod(requestBody.getMethod());
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestProperty("Content-Type","application/json");
        httpURLConnection.setRequestProperty("Accept","application/json");
        httpURLConnection.setReadTimeout(10000);
        if (requestBody.getXapiKey()!=null){
            httpURLConnection.addRequestProperty("x-api-key",requestBody.getXapiKey());
        }
        if (requestBody.getToken()!=null){
            httpURLConnection.setRequestProperty("Authorization","token "+requestBody.getToken());
        }
        httpURLConnection.setRequestProperty("User-Agent",requestBody.getUserAgent());
        httpURLConnection.setRequestProperty("X-Requested-With",requestBody.xrequestWith);
        httpURLConnection.setReadTimeout(60000);

        StringBuilder stringBuilder = new StringBuilder();
        if (requestBody.getData()!=null) {
            try (OutputStream outputStream = httpURLConnection.getOutputStream()) {
                outputStream.write(requestBody.getJsonBody().getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
            }
        }


        try {

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK|| httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_CREATED) {
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))) {
                    String line = "";

                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                }
            }
            else if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_UNAUTHORIZED){
                System.out.println("Unauthorized");
            }
            else if (httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_NO_CONTENT||httpURLConnection.getResponseCode()==302){
                System.out.println(httpURLConnection.getResponseMessage());
            }
            else if (httpURLConnection.getResponseCode()==500||httpURLConnection.getResponseCode()==502){
                asyncRequest(requestBody);
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

    public synchronized static int asyncRequestForm(RequestBody requestBody) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("multipart/form-data");
        MultipartBody  body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("no_blocking","true")
                .addFormDataPart("nft_blockchain_name","thundercore")
                .build();
        Request request = new Request.Builder()
                .url(requestBody.getUrl())
                .method("POST", body)
                .addHeader("Authorization", "token "+requestBody.getToken())
                .addHeader("x-requested-with", requestBody.xrequestWith)
                .addHeader("x-api-key", requestBody.getXapiKey())
                .addHeader("Content-Type", "multipart/form-data")
                .build();
        Response response = client.newCall(request).execute();
        if (response.code()==200||response.code()==400){
            response.close();
        }
        return response.code();
    }

    public synchronized static String asyncRequestHtml(RequestBody requestBody, boolean isCookie) throws IOException {

        URL uri = new URL(requestBody.getUrl());
       /* Proxy webProxy
                = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 10001));
        HttpURLConnection httpURLConnection
                = (HttpURLConnection) uri.openConnection(webProxy);*/
        HttpURLConnection httpURLConnection = (HttpURLConnection) uri.openConnection();

        httpURLConnection.setRequestMethod(requestBody.getMethod());
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestProperty("Content-Type","text/html");
        httpURLConnection.setRequestProperty("User-Agent",requestBody.getUserAgent());
        httpURLConnection.setReadTimeout(6000);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK|| httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_CREATED) {
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))) {
                    String line = "";

                    while ((line = bufferedReader.readLine()) != null) {
                        if (line.contains("<a href")){
                            Pattern p = Pattern.compile("href=\'(.*?)\'");
                            Matcher m = p.matcher(line);
                            if (m.find()) {
                                return m.group(1);
                            }
                            return line;
                        }
                        stringBuilder.append(line);
                    }

                    if (isCookie){

                        Document document =  Jsoup.parseBodyFragment(stringBuilder.toString());
                        Element body = document.body();
                        Element csrfmiddlewaretokenElm = body.select("input[name=csrfmiddlewaretoken]").first();
                        Element uidElm = body.select("input[name=uid]").first();
                        Element tokenElm = body.select("input[name=token]").first();
                        String csrfmiddlewaretoken= csrfmiddlewaretokenElm.attr("value");
                        String uid= uidElm.attr("value");
                        String token= tokenElm.attr("value");
                        VerifyBody verify = new VerifyBody(csrfmiddlewaretoken,uid,token);

                        RequestBody verifyBody = new RequestBody();
                        List<String> cookieList = httpURLConnection.getHeaderFields().get(COOKIES_HEADER);
                        if (cookieList != null) {
                            for (String cookieTemp : cookieList) {
                                verifyBody.setCookieManager(HttpCookie.parse(cookieTemp).get(0).toString());
                                break;
                            }
                        }
                        verifyBody.setUrl(requestBody.getUrl()+"/");
                        verifyBody.setData(verify);
                        verifyBody.setMethod("POST");
                        String verifyData = RequestUtil.asyncRequestHtmlForm(verifyBody);
                    }
                }
            }
            else if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_UNAUTHORIZED){
                System.out.println("Unauthorized");
            }
            else if (httpURLConnection.getResponseCode()==HttpURLConnection.HTTP_NO_CONTENT){
                System.out.println(httpURLConnection.getResponseMessage());
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

    public synchronized static String asyncRequestHtmlForm(RequestBody requestBody) throws IOException {
        VerifyBody verifyBody = (VerifyBody) requestBody.getData();
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, verifyBody.toString());
        Request request = new Request.Builder()
                .url(requestBody.getUrl())
                .method("POST", body)
                .addHeader("User-Agent", requestBody.getUserAgent())
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("sec-fetch-dest", "document")
                .addHeader("origin", "https://dia-backend.numbersprotocol.io")
                .addHeader("host", "dia-backend.numbersprotocol.io")
                .addHeader("Referer", requestBody.getUrl())
                .addHeader("Cookie", requestBody.getCookieManager())
                .build();
        Response response = client.newCall(request).execute();
        Integer code =response.code();
        System.out.println(response.code());
        response.close();
        return null;
    }

}
