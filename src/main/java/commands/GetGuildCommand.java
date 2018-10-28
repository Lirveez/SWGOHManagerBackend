package commands;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import services.Profile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Scanner;

public class GetGuildCommand implements AjaxCommand {
    @Override
    public Object doCommand(Map parameters) throws IOException {
        String get_url = "https://swgoh.gg/api/guild/26825/";
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
        JSONObject jsonObject = new JSONObject(Tokener);
        JSONArray players = jsonObject.getJSONArray("players");
        JSONArray arr = new JSONArray();
        Profile[] guild = new Profile[players.length()];
        for (int i=0;i<players.length();i++){
            guild[i] = new Profile();
            guild[i].setName(players.getJSONObject(i).getJSONObject("data").getString("name"));
            System.out.println(guild[i].getName());
        }
        httpGet.releaseConnection();
        ((CloseableHttpClient) client).close();
        return guild;
    }
}
