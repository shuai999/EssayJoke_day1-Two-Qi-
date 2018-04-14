package cn.novate.essayjoke_day01;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.framelibrary.BaseSkinActivity;

import java.io.File;
import java.lang.reflect.Method;

/**
 * Email: 2185134304@qq.com
 * Created by JackChen 2018/4/14 20:03
 * Version 1.0
 * Params:
 * Description:  换肤测试 示例demo
*/
public class MainActivity extends BaseSkinActivity implements View.OnClickListener{

    private Button test_btn;
    private ImageView test_iv;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        test_btn = (Button) findViewById(R.id.test_btn);
        test_btn.setOnClickListener(this);

        test_iv = (ImageView) findViewById(R.id.test_iv);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    /**
     * 1>：写一个demo，里边就只在drawable中放一张 图片，不做任何操作，然后会在app - build - outputs - apk下边生成一个 app-debug.apk；
     * 2>：把这个apk复制到桌面，然后重命名为 red.skin，或者其他的以 .skin结尾的都可以；
     * 3>：这里为了测试效果，就直接把 red.skin 文件复制到手机存储目录，表示已经从服务器下载了 皮肤到本地；
     * 4>：然后点击 换肤的 按钮，就需要从手机目录中读取 red.skin文件，然后从中获取到 它drawable下边的那一张图片；
     * 5>：然后用获取到的图片 替换 换肤按钮 下边的 这张图片；
     *
     *      以上，就达到换肤的目的；
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.test_iv:
                try {
                    // 读取本地的一个 .skin里面的资源
                    Resources superRes = getResources() ;

                    // 创建AssetsManager
                    // 不能直接 new AssetManager() ;
                    // 通过反射来创建 asset对象
                    AssetManager asset = AssetManager.class.newInstance() ;
                    // 添加本地下载好的 资源皮肤，就是复制到 手机目录中的 red.skin
                    // 不能直接调用 addAssetPath()方法，只能通过反射调用 该方法
                    // 参数1：表示方法名称  参数2：表示方法里边的参数类型 如果是String path -> String.class int path -> int.class 等等

                    Method method = AssetManager.class.getDeclaredMethod("addAssetPath" , String.class) ;
                    method.setAccessible(true);  // 设置权限，防止addAssetPath()方法是私有private的

                    // 反射执行addAssetPath()方法   File.separator就和 "/" 是一样的
                    method.invoke(asset , Environment.getExternalStorageDirectory().getAbsolutePath() +
                            File.separator + "red.skin") ;
                    Resources resources = new Resources(asset , superRes.getDisplayMetrics() , superRes.getConfiguration()) ;

                    // 获取资源 id
                    // 参数1：表示图片的名称  参数2：表示类型  参数3：表示包名
                    int drawableId = resources.getIdentifier("image_src", "drawable", "cn.novate.skinpagin");
                    Drawable drawable = resources.getDrawable(drawableId) ;
                    test_iv.setImageDrawable(drawable);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                 break;

        }
    }
}
