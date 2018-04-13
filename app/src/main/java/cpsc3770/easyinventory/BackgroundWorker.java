package cpsc3770.easyinventory;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundWorker extends AsyncTask<String, Void, String> {

    public AsyncResponse delegate;

    @Override
    protected String doInBackground(String... voids) {
        String type = voids[0];
        String ipaddress = "http://192.168.0.10/";
        String getProduct_url = ipaddress + "getproduct.php";
        String updateStock_url = ipaddress + "updatestock.php";
        String getAll_url = ipaddress + "getall.php";
        String updateProduct_url = ipaddress + "updateproduct.php";

        switch(type) {
            case "findProduct" :
                try {
                    String productID = voids[1];
                    URL url = new URL(getProduct_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    // Output to server
                    OutputStream os = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String post_data = URLEncoder.encode("productID", "UTF-8")+"="+URLEncoder.encode(productID, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    os.close();

                    // Input from server
                    InputStream is = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "ISO-8859-1"));
                    String result = "";
                    String line;
                    while((line = bufferedReader.readLine()) != null) {
                        result+=line;
                    }
                    bufferedReader.close();
                    is.close();
                    httpURLConnection.disconnect();

                    // Return result from server
                    return result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "updateStock" :
                try {
                    String productID = voids[1];
                    String newStock = voids[2];
                    URL url = new URL(updateStock_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    // Output to server
                    OutputStream os = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String post_data = URLEncoder.encode("productID", "UTF-8")+"="+URLEncoder.encode(productID, "UTF-8")+"&"
                            +URLEncoder.encode("newStock", "UTF-8")+"="+URLEncoder.encode(newStock, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    os.close();

                    // Input from server
                    InputStream is = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "ISO-8859-1"));
                    String result = "";
                    String line;
                    while((line = bufferedReader.readLine()) != null) {
                        result+=line;
                    }
                    bufferedReader.close();
                    is.close();
                    httpURLConnection.disconnect();

                    // Return result from server
                    return result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "updateProduct" :
                try {
                    String productID = voids[1];
                    String productName = voids[2];
                    String price = voids[3];
                    String size = voids[4];
                    String unit = voids[5];
                    String description = voids[6];
                    URL url = new URL(updateProduct_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    // Output to server
                    OutputStream os = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String post_data = URLEncoder.encode("productID", "UTF-8")+"="+URLEncoder.encode(productID, "UTF-8")+"&"
                            +URLEncoder.encode("productName", "UTF-8")+"="+URLEncoder.encode(productName, "UTF-8")+"&"
                            +URLEncoder.encode("price", "UTF-8")+"="+URLEncoder.encode(price, "UTF-8")+"&"
                            +URLEncoder.encode("size", "UTF-8")+"="+URLEncoder.encode(size, "UTF-8")+"&"
                            +URLEncoder.encode("unit", "UTF-8")+"="+URLEncoder.encode(unit, "UTF-8")+"&"
                            +URLEncoder.encode("description", "UTF-8")+"="+URLEncoder.encode(description, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    os.close();

                    // Input from server
                    InputStream is = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "ISO-8859-1"));
                    String result = "";
                    String line;
                    while((line = bufferedReader.readLine()) != null) {
                        result+=line;
                    }
                    bufferedReader.close();
                    is.close();
                    httpURLConnection.disconnect();

                    // Return result from server
                    return result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "getAll" :
                try {
                    URL url = new URL(getAll_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    // Output to server
                    OutputStream os = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    String post_data = URLEncoder.encode("", "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    os.close();

                    // Input from server
                    InputStream is = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "ISO-8859-1"));
                    String result = "";
                    String line;
                    while((line = bufferedReader.readLine()) != null) {
                        result+=line;
                    }
                    Log.e("My Result", result);
                    bufferedReader.close();
                    is.close();
                    httpURLConnection.disconnect();

                    // Return result from server
                    return result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        return null;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        delegate.processFinish(result);
    }

    public interface AsyncResponse {
        void processFinish(String output);
    }

    public BackgroundWorker(AsyncResponse delegate) {
        this.delegate = delegate;
    }
}
