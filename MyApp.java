package zhouyafei.bwei.com.zhouyafeiyuekao;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * Created by Zhouyf on 2017/9/20.
 */

public class MyApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)
                .displayer(new FadeInBitmapDisplayer(2000)).cacheOnDisk(true)
                .cacheInMemory(true).build();
        ImageLoaderConfiguration configs = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(options).threadPoolSize(5)
                .build();

        ImageLoader.getInstance().init(configs);
    }
}
