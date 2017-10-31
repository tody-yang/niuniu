package cn.com.niuniu.yj.demo.activity.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

/**
 * <h1>自定义Activity基类</h1>
 * <p>一个自定义的Activity的基类
 *
 * @author: niuniu
 * @date: 2017/10/10.
 */

public class BaseActivity extends AppCompatActivity {

    /**
     * ProgressDialog对象
     */
    protected ProgressDialog mProgressDialog;

    /**
     * 是否显示OptionMenu
     */
    protected boolean showOptionMenu;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissPopWindow();
        setShowOptionMenu(false);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (showOptionMenu){
            showOptionMenu(menu);
        }else{
            hideOptionMenu(menu);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * 显示toast提示
     * @param str   需要显示的字符串
     */
    protected void showToast(String str){
        if (!TextUtils.isEmpty(str)){
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 跳转Activity
     * @param myClass 要跳转的界面
     */
    protected void startMyActivity(Class myClass){
        if(myClass != null){
            Intent intent = new Intent(this, myClass);
            startActivity(intent);
        }else{
            showToast("扑街，你跳转的目的地界面为空");
        }
    }

    /**
     * 跳转Activity
     * @param myClass 要跳转的界面
     * @param bundle  Bundle对象
     */
    protected void startMyActivity(Class myClass, Bundle bundle){
        if(myClass != null){
            Intent intent = new Intent(this, myClass);
            intent.putExtras(bundle);
            startActivity(intent);
        }else{
            showToast("扑街，你跳转的目的地界面为空");
        }
    }


    /**
     * 跳转Activity
     * @param myClass 要跳转的界面
     * @param bundle  Bundle对象
     */
    protected void startMyActivityForResult(Class myClass, Bundle bundle){
        if(myClass != null){
            Intent intent = new Intent(this, myClass);
            intent.putExtras(bundle);
            startActivityForResult(intent,RESULT_OK);
        }else{
            showToast("扑街，你跳转的目的地界面为空");
        }
    }


    /**
     * 显示PopWindow
     * @param title      标题
     * @param content    内容
     */
    protected void showPopWindow(String title, String content){
        if (mProgressDialog != null){
            mProgressDialog.dismiss();
        }
        mProgressDialog = ProgressDialog.show(this, title, content);
    }

    /**
     * 隐藏PopWindow
     *
     */
    protected void dismissPopWindow(){
        if (mProgressDialog != null){
            mProgressDialog.dismiss();
        }
    }

    /**
     * 设置标题
     * @param str  标题类容
     */
    protected void setActivityTitle(String str){
        if (TextUtils.isEmpty(str)){
            setTitle(str);
        }
    }


    /**
     * 显示View
     * @param view view对象
     */
    protected void showView(View view){
        if (view != null){
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 显示View
     * @param viewID view对象ID
     */
    protected void showView(int viewID){
        if (findViewById(viewID) != null){
            findViewById(viewID).setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置View的显示方式为gone
     * @param view view对象
     */
    protected void goneView(View view){
        if (view != null){
            view.setVisibility(View.GONE);
        }
    }

    /**
     * 设置View的显示方式为gone
     * @param viewID view对象ID
     */
    protected void goneView(int viewID){
        if (findViewById(viewID) != null){
            findViewById(viewID).setVisibility(View.GONE);
        }
    }

    /**
     * 设置View的显示方式为invisible
     * @param view view对象
     */
    protected void invisibleView(View view){
        if (view != null){
            view.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 设置View的显示方式为invisible
     * @param viewID view对象ID
     */
    protected void invisibleView(int viewID){
        if (findViewById(viewID) != null){
            findViewById(viewID).setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 显示OptionMenu
     * @param menu   menu对象
     */
    protected void showOptionMenu(Menu menu){
        if (null != menu){
            showOptionMenu = true;
            for (int i = 0; i < menu.size(); i++) {
                menu.getItem(i).setVisible(true);
            }
        }
    }

    /**
     * 隐藏OptionMenu
     * @param menu    menu对象
     */
    protected void hideOptionMenu(Menu menu){
        if (null != menu){
            showOptionMenu = false;
            for (int i = 0; i < menu.size(); i++) {
                menu.getItem(i).setVisible(false);
            }
        }
    }

    /**
     * 设置是否显示OptionMenu（默认为false）
     * @param showOptionMenu   boolean变量
     */
    public void setShowOptionMenu(boolean showOptionMenu) {
        this.showOptionMenu = showOptionMenu;
    }


}
