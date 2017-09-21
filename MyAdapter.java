package zhouyafei.bwei.com.zhouyafeiyuekao;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;

/**
 * Created by Zhouyf on 2017/9/20.
 */

public class MyAdapter extends BaseAdapter{
    private Context context;
    private List<JsonBean.NewslistBean> data = new ArrayList<>();

    public MyAdapter(Context context, List<JsonBean.NewslistBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolderOne holderOne;
        ViewHolderTwo holderTwo;
        int type = getItemViewType(i);
        if (view==null){
            switch (type){
                case 0:
                    view = View.inflate(context,R.layout.item1,null);
                    holderOne = new ViewHolderOne();
                    holderOne.title = view.findViewById(R.id.one_text1);
                    holderOne.date = view.findViewById(R.id.one_text2);
                    holderOne.one_img = view.findViewById(R.id.one_img);
                    view.setTag(holderOne);
                    break;
                case 1:
                    view = View.inflate(context,R.layout.item2,null);
                    holderTwo = new ViewHolderTwo();
                    holderTwo.two_img1 = view.findViewById(R.id.two_img1);
                    holderTwo.two_img2 = view.findViewById(R.id.two_img2);
                    holderTwo.two_img3 = view.findViewById(R.id.two_img3);
                    view.setTag(holderTwo);
                    break;
            }
        }
        switch (type){
            case 0:
                holderOne = (ViewHolderOne) view.getTag();
                holderOne.title.setText(data.get(i).getTitle());
                holderOne.date.setText(data.get(i).getCtime());
                ImageLoader.getInstance().displayImage(data.get(i).getPicUrl(),holderOne.one_img);
                break;
            case 1:
                holderTwo = (ViewHolderTwo) view.getTag();
                ImageLoader.getInstance().displayImage(data.get(i).getPicUrl(),holderTwo.two_img1);
                ImageLoader.getInstance().displayImage(data.get(i).getPicUrl(),holderTwo.two_img2);
                ImageLoader.getInstance().displayImage(data.get(i).getPicUrl(),holderTwo.two_img3);

                break;
        }
        return view;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        int p = position%2;
        if (p==0){
           return 0;
        }else{
           return 1;
        }

    }

    class ViewHolderOne{
        TextView title,date;
        ImageView one_img;
    }
    class ViewHolderTwo{
        ImageView two_img1,two_img2,two_img3;
    }
}
