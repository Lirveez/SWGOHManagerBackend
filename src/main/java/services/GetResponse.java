package services;

import jdk.nashorn.internal.parser.JSONParser;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class GetResponse {
    public static JSONTokener getResponse(String response) throws IOException {
        String get_url = response;
        HttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(get_url);
        InputStream input = client.execute(httpGet).getEntity().getContent();
        Scanner scan = new Scanner(input);
       // StringBuilder str = new StringBuilder();
        StringBuffer str = new StringBuffer();
        while (scan.hasNextLine())
        {
            str.append(scan.nextLine());
        }
        JSONTokener Tokener = new JSONTokener(str.toString());
        scan.close();
        input.close();
        httpGet.releaseConnection();
        ((CloseableHttpClient) client).close();
        return Tokener;
    }
}
