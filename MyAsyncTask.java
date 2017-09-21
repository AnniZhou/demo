package zhouyafei.bwei.com.zhouyafeiyuekao;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by Zhouyf on 2017/9/15.
 */

public class MyAsyncTask extends AsyncTask<String ,Integer ,String>{
    private Context context;
    private GridView gridView;
    private ListView listView;
    private int type=1;
    private int page;

    public MyAsyncTask(Context context, GridView gridView, ListView listView,int page) {
        this.context = context;
        this.gridView = gridView;
        this.listView = listView;
        this.page = page;
    }

    private JsonBean bean;

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL("https://api.tianapi.com/wxnew/?key=8d6e3228d25298f13af4fc40ce6c9679&num=10&page="+page);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
            int responseCode = connection.getResponseCode();
            if (responseCode==200){
                InputStream inputStream = connection.getInputStream();
                String text = readText(inputStream, "utf-8");
                Gson gson = new Gson();
                bean = gson.fromJson(text, JsonBean.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private String readText(InputStream inputStream,String charset){
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,charset);
            BufferedReader buffer = new BufferedReader(inputStreamReader);
            String s=null;
            StringBuilder builder = new StringBuilder();
            while ((s=buffer.readLine())!=null){
                builder.append(s);
            }
            buffer.close();
            inputStreamReader.close();
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        List<JsonBean.NewslistBean> data = bean.getNewslist();
        if (data!=null){
            Main2_Adapter adapter = new Main2_Adapter(context, data,type);
            gridView.setAdapter(adapter);
            if (type==1) {
                ++type;
                Main2_Adapter adapter2 = new Main2_Adapter(context, data,type);
                listView.setAdapter(adapter2);
            }
        }
    }
}
