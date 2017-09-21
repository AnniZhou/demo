package zhouyafei.bwei.com.zhouyafeiyuekao;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhouyf on 2017/9/20.
 */

public class MyFragment extends Fragment {

    private TabLayout tab;
    private ViewPager vp;
    private List<String> mtitle = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.f1,null);
        tab = v.findViewById(R.id.tab);
        vp = v.findViewById(R.id.f_vp);
        getData();
        vp.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public CharSequence getPageTitle(int position) {
                return mtitle.get(position);
            }

            @Override
            public Fragment getItem(int position) {
                MyFragment_show fragment = new MyFragment_show();
                Bundle bundle = new Bundle();
                if (mtitle.get(position).equals("社会新闻")){
                    bundle.putString("name","social");
                }else if (mtitle.get(position).equals("国内新闻")){
                    bundle.putString("name","guonei");
                }else if (mtitle.get(position).equals("国际新闻")){
                    bundle.putString("name","world");
                }else if (mtitle.get(position).equals("娱乐新闻")){
                    bundle.putString("name","huabian");
                }else if (mtitle.get(position).equals("体育新闻")){
                    bundle.putString("name","tiyu");
                }else if (mtitle.get(position).equals("科技新闻")){
                    bundle.putString("name","keji");
                }else if (mtitle.get(position).equals("创业新闻")){
                    bundle.putString("name","startup");
                }else if (mtitle.get(position).equals("军事新闻")){
                    bundle.putString("name","military");
                }
                fragment.setArguments(bundle);
                return fragment;
            }
            @Override
            public int getCount() {
                return mtitle.size();
            }
        });
        tab.setupWithViewPager(vp);
        return v;
    }
    public void getData(){
        mtitle.add("社会新闻");
        mtitle.add("国内新闻");
        mtitle.add("国际新闻");
        mtitle.add("娱乐新闻");
        mtitle.add("体育新闻");
        mtitle.add("科技新闻");
        mtitle.add("创业新闻");
        mtitle.add("军事新闻");
    }
}
