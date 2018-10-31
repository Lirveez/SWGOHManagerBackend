package services;

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
    public static JSONObject getResponse(String response,int id) throws IOException {
        String get_url = response+id+"/";
        HttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(get_url);
        InputStream input = client.execute(httpGet).getEntity().getContent();
        Scanner scan = new Scanner(input);
        StringBuilder str = new StringBuilder();
        while (scan.hasNext())
        {
            str.append(scan.nextLine());
        }
        JSONTokener Tokener = new JSONTokener(str.toString());
        httpGet.releaseConnection();
        ((CloseableHttpClient) client).close();
        return new JSONObject(Tokener);
    }
}