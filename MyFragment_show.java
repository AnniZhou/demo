package zhouyafei.bwei.com.zhouyafeiyuekao;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by Zhouyf on 2017/9/20.
 */

public class MyFragment_show extends Fragment {

    private PullToRefreshListView listView;
    private String mstrings;
    private int page=1;
    private int num=10;
    private View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.show,null);
        listView = v.findViewById(R.id.pull_listView);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        num+=10;
                        page++;
                        getDataFromNet(mstrings,num,page);
                        listView.onRefreshComplete();
                    }
                },2000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                listView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        num=10;
                        page=1;
                        getDataFromNet(mstrings,num,page);
                        listView.onRefreshComplete();
                    }
                },2000);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(),Main2Activity.class);
                startActivity(intent);
            }
        });
        return v;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        mstrings = bundle.getString("name");
        getDataFromNet(mstrings,num,page);

    }

    private void getDataFromNet(final String string,final int n,final int p) {
        AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                String path = "http://api.tianapi.com/"+string+"/?key=48844f6abf6f664f645221f5c9894824&num="+n+"&page="+p;
                try {
                    URL url = new URL(path);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //设置
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(5000);
                    connection.setConnectTimeout(5000);
                    //获取
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200){
                        InputStream inputStream = connection.getInputStream();
                        String json = streamToString(inputStream,"utf-8");
                        return json;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return "";
            }

            @Override
            protected void onPostExecute(String json) {
                //原生json
                Gson gson = new Gson();
                JsonBean dataDataBean = gson.fromJson(json, JsonBean.class);
                List<JsonBean.NewslistBean> list = dataDataBean.getNewslist();
                if(list!=null){
                    MyAdapter myAdapter = new MyAdapter(getActivity(), list);
                    listView.setAdapter(myAdapter);
                }

            }
        };

        asyncTask.execute();

    }
    private String streamToString(InputStream inputStream, String charset) {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,charset);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String s = null;
            StringBuilder builder = new StringBuilder();
            while ((s = bufferedReader.readLine()) != null){
                builder.append(s);
            }
            bufferedReader.close();
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }
}
