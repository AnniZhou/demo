package zhouyafei.bwei.com.zhouyafeiyuekao;

import android.content.Context;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.Arrays;
import java.util.List;

public class Main2Activity extends AppCompatActivity implements OnItemClickListener {
    private ConvenientBanner convenientBanner;
    private List<String> networkImages;
    private String[] images = {"http://img2.imgtn.bdimg.com/it/u=3093785514,1341050958&fm=21&gp=0.jpg",
            "http://img2.3lian.com/2014/f2/37/d/40.jpg",
            "http://d.3987.com/sqmy_131219/001.jpg",
            "http://img2.3lian.com/2014/f2/37/d/39.jpg",
            "http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg",
            "http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg"
    };
    protected ImageLoader imageLoader;
    private ListView lv;
    private MyAsyncTask myAsyncTask;
    private int type;
    private PullToRefreshScrollView ps;
    private int page=1;
    private GridView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ps = (PullToRefreshScrollView) findViewById(R.id.pull_scrollview);
        convenientBanner = (ConvenientBanner) findViewById(R.id.convenientBanner);
        gv = (GridView) findViewById(R.id.gridview);
        lv = (ListView) findViewById(R.id.lv);
        myAsyncTask = new MyAsyncTask(this, gv,lv,page);
        myAsyncTask.execute();
        ps.setMode(PullToRefreshBase.Mode.BOTH);
        ps.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                ps.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page++;
                        myAsyncTask = new MyAsyncTask(Main2Activity.this, gv,lv,page);
                        myAsyncTask.execute();
                        ps.onRefreshComplete();
                    }
                },2000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                ps.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page=1;
                        myAsyncTask = new MyAsyncTask(Main2Activity.this, gv,lv,page);
                        myAsyncTask.execute();
                        ps.onRefreshComplete();
                    }
                },2000);
            }
        });

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));

        //网络加载例子
        networkImages = Arrays.asList(images);
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, networkImages)
                //设置指示器是否可见
                .setPointViewVisible(true)
                //设置自动切换（同时设置了切换时间间隔）
                .startTurning(2000)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                //设置指示器的方向（左、中、右）
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                //设置点击监听事件
                .setOnItemClickListener(this)
                //设置手动影响（设置了该项无法手动切换）
                .setManualPageable(true);
    }

    //图片的点击事件
    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "position:" + position, Toast.LENGTH_SHORT).show();
    }

    public class NetworkImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            Glide.with(Main2Activity.this).load(data).into(imageView);
        }
    }

}
