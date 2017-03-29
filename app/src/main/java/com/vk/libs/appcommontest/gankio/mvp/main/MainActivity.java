package com.vk.libs.appcommontest.gankio.mvp.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.vk.libs.appcommon.ui.BaseActivity;
import com.vk.libs.appcommon.ui.extra.BottomNavFragment;
import com.vk.libs.appcommontest.R;
import com.vk.libs.appcommontest.gankio.mvp.discover.DiscoverFragment;
import com.vk.libs.appcommontest.gankio.mvp.home.HomeFragment;
import com.vk.libs.appcommontest.gankio.mvp.mine.MineFragment;
import com.vk.libs.appcommontest.gankio.mvp.setting.SettingFragment;

import butterknife.ButterKnife;

import static com.vk.libs.appcommontest.R.id.tabContent;

/**
 * Created by VK on 2017/1/19.
 * Contact with trh5176891@126.com<br/><br/>
 * -
 */
public class MainActivity extends BaseActivity implements MainContract.IView {

    public static final String TAG = "MainActivity";

    private MainContract.Presenter mPresenter;

    private String[] tabNames = {"首页","发现","我的","设置"};

    private int[] tabIconRes = {R.drawable.ic_nav_home,R.drawable.ic_nav_discover,R.drawable.ic_nav_my,R.drawable.ic_nav_settting};

    private Class[] tabClass = {HomeFragment.class, DiscoverFragment.class, MineFragment.class, SettingFragment.class};
//
//    @BindView(R.id.fragmentTabHost)
//    FragmentTabHost mFragmentTabHost;
//    @BindView(R.id.tabContent)
//    FrameLayout tabContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.setDebug(true);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        Log.d(TAG, "initView: "+tabContent);
        FragmentManager fragmentManager = getSupportFragmentManager();
        BottomNavFragment bottomNavFragment = (BottomNavFragment) fragmentManager.findFragmentById(R.id.fragment_nav);
        bottomNavFragment.setup(tabNames,tabIconRes,tabClass);

//        //关联Content
//        mFragmentTabHost.setup(this,getSupportFragmentManager(), tabContent);
//        //去除系统分割线
//        mFragmentTabHost.getTabWidget().setDividerDrawable(null);
//        //加入tab
//        for(int i = 0;i<tabClass.length;i++){
//            TabHost.TabSpec tabSpec = mFragmentTabHost.newTabSpec(tabNames[i]).setIndicator(getTabSpecView(i));
//            mFragmentTabHost.addTab(tabSpec,tabClass[i],null);
//        }

    }

//    private View getTabSpecView(int index){
//        View view = LayoutInflater.from(this).inflate(R.layout.main_tabspec,null);
//        ImageView icon = (ImageView) view.findViewById(R.id.tabspec_iv);
//        icon.setImageResource(tabIconRes[index]);
//        TextView tvName = (TextView)view.findViewById(R.id.tabspec_tv);
//        tvName.setText(tabNames[index]);
//        return view;
//    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {

    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void hideLoading(String message) {

    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void refresh(String message) {

    }
}