package cn.com.niuniu.yj.demo.fragment.base;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * <h1>自定义Fragment基类</h1>
 * <p>一个自定义的Fragment基类
 *
 * @author: niuniu
 * @date: 2017/10/10.
 */

public class BaseFragment extends Fragment {
    /**
     * 父类Activity
     */
    private Activity mActivity;

    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissPopWindow();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
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
        mProgressDialog = ProgressDialog.show(mActivity, title, content);
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
        if (mActivity.findViewById(viewID) != null){
            mActivity.findViewById(viewID).setVisibility(View.VISIBLE);
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
        if (mActivity.findViewById(viewID) != null){
            mActivity.findViewById(viewID).setVisibility(View.GONE);
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
        if (mActivity.findViewById(viewID) != null){
            mActivity.findViewById(viewID).setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 显示toast提示
     * @param str   需要显示的字符串
     */
    protected void showToast(String str){
        if (!TextUtils.isEmpty(str)){
            Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
        }
    }

}
