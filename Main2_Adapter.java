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

/**
 * Created by Zhouyf on 2017/9/20.
 */
//https://api.tianapi.com/wxnew/?key=8d6e3228d25298f13af4fc40ce6c9679&num=10
public class Main2_Adapter extends BaseAdapter {

    private Context context;
    private List<JsonBean.NewslistBean> data = new ArrayList<>();
    private int type;
    public Main2_Adapter(Context context, List<JsonBean.NewslistBean> data, int type) {
        this.context = context;
        this.data = data;
        this.type = type;
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
        ViewHolderOne holderOne = null;
        if (view==null) {
            switch (type){
                case 1:
                    view = View.inflate(context, R.layout.griditem, null);
                    holderOne = new ViewHolderOne();
                    holderOne.title = view.findViewById(R.id.g_text);
                    holderOne.one_img = view.findViewById(R.id.g_img);
                    view.setTag(holderOne);
                    break;
                case 2:
                    view = View.inflate(context, R.layout.item1, null);
                    holderOne = new ViewHolderOne();
                    holderOne.title = view.findViewById(R.id.one_text1);
                    holderOne.one_img = view.findViewById(R.id.one_img);
                    view.setTag(holderOne);
                    break;
            }
            
        }else{
            holderOne = (ViewHolderOne) view.getTag();
        }
        holderOne.title.setText(data.get(i).getTitle());
        ImageLoader.getInstance().displayImage(data.get(i).getPicUrl(),holderOne.one_img);
        return view;
    }

    class ViewHolderOne{
        TextView title;
        ImageView one_img;
    }
}
