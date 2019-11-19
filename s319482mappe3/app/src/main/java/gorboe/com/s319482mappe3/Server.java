package gorboe.com.s319482mappe3;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Server extends AsyncTask<String, Void,String> {
    @Override
    protected String doInBackground(String... urls){
        StringBuilder output = new StringBuilder();
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < urls.length; i++){
            try{
                URL url = new URL(urls[i]); //get the url your working with
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();

                if(connection.getResponseCode() != 200){
                    throw new RuntimeException("Failed : HTTP error code : "
                            + connection.getResponseCode());
                }
                InputStreamReader isr = new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(isr);

                String temp;
                while((temp = reader.readLine()) != null){
                    output.append(temp);
                }
                connection.disconnect();

                //to json objects todo: following 5lines should maybe be elsewhere?
                    /*JSONArray jsonObjects = new JSONArray(output.toString());
                    for(int j = 0; j < jsonObjects.length(); j++){
                        JSONObject jsonObject = jsonObjects.getJSONObject(j);
                        System.out.println(jsonObject); //todo:to convert to object Gson gson = new Gson();Object object = gson.fromJson(jsonObject, object.class);
                        String name = jsonObject.getString("name");
                        result.append(name).append(" ");
                    }*/
            }catch (Exception e){
                return "noe gikk feil";
            }
        }

        return output.toString();
    }
}
