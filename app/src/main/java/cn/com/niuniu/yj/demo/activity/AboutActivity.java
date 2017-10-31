package cn.com.niuniu.yj.demo.activity;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;
import java.util.Date;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.niuniu.yj.demo.R;
import cn.com.niuniu.yj.demo.activity.base.BaseActivity;

import static cn.com.niuniu.yj.demo.R.id.decor_content_parent;
import static cn.com.niuniu.yj.demo.R.id.tv_date;
import static cn.com.niuniu.yj.demo.R.id.tv_email;
import static cn.com.niuniu.yj.demo.R.id.tv_name;
import static cn.com.niuniu.yj.demo.R.id.tv_tel;

/**
 * 关于界面
 */
public class AboutActivity extends BaseActivity {
    //用的butterknife组件
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(tv_name)
    TextView tvName;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_web)
    TextView tvWeb;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        setView();
    }



    /**
     * 设置View
     */

    private void setView() {
        tvContent.setText(getString(R.string.about_tv_content).trim());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
        String dataStr = dateFormat.format(new Date());
        tvDate.setText(dataStr);
        tvName.setText(getString(R.string.about_tv_name).trim());
        //链接属性对应XML文件的 android:autoLink属性，email要起作用需要手机有相关程序
        tvTel.setText(getString(R.string.about_tv_tel).trim());
        tvEmail.setText(getString(R.string.about_tv_email).trim());
        tvWeb.setAutoLinkMask(Linkify.WEB_URLS);
        tvWeb.setText(getString(R.string.about_tv_web));
        setActivityTitle(getString(R.string.about_title).trim());
    }



    @OnClick(tv_name)
    public void clickName() {
        showToast(getString(R.string.about_click_name));
    }

    @OnClick({tv_date, tv_tel, tv_email})
    public void clickView(View view) {
        switch (view.getId()) {
            case R.id.tv_date:
                showToast(getString(R.string.about_click_date));
                break;
            case R.id.tv_tel:
                showToast(getString(R.string.about_click_tel));
                break;
            case R.id.tv_email:
                showToast(getString(R.string.about_click_email));
                break;
            default:
                break;

        }
    }


}
