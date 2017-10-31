package cn.com.niuniu.yj.demo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.greenrobot.eventbus.EventBus;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.niuniu.yj.demo.R;
import cn.com.niuniu.yj.demo.activity.base.BaseActivity;
import cn.com.niuniu.yj.demo.bean.EventMsgList;
import cn.com.niuniu.yj.demo.bean.OttoMsgList;
import cn.com.niuniu.yj.demo.bean.User;
import cn.com.niuniu.yj.demo.entity.KeyEntity;
import cn.com.niuniu.yj.demo.greendao.MyDbManager;
import cn.com.niuniu.yj.demo.greendao.UserDao;
import cn.com.niuniu.yj.demo.otto.MyOtto;

/**
 * 修改GreenDao界面
 *
 * @author niuniu
 */
public class UpdateGreenDaoActivity extends BaseActivity {

    @BindView(R.id.tv_id)
    TextView tvId;
    @BindView(R.id.et_id)
    EditText etId;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.et_age)
    EditText etAge;
    @BindView(R.id.tv_desc)
    TextView tvDesc;
    @BindView(R.id.et_desc)
    EditText etDesc;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.ed_phone)
    EditText edPhone;

    private User mUser;

    private UserDao mUserDao;

    private Long id;

    private MainActivity mainActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_green_dao);
        setTitle(getString(R.string.update_greendao_title));
        ButterKnife.bind(this);
        mUser = getIntent().getParcelableExtra(KeyEntity.KEY_GREENDAO_USER);
        id = getIntent().getLongExtra(KeyEntity.KEY_GREENDAO_ID, 0);
        mUserDao = MyDbManager.getDaoSession(this).getUserDao();
        setDefaultView(mUser);
    }

    private void setDefaultView(User mUser) {
        etId.setText(id.toString());
        etName.setText(mUser.getUserName());
        etAge.setText(String.valueOf(mUser.getUserAge()));
        etDesc.setText(mUser.getUserDesc());
        edPhone.setText(mUser.getPhone());
    }

    @OnClick(R.id.button)
    public void onClickUpdate() {
        String idStr = etId.getText().toString().trim();
        if (TextUtils.isEmpty(idStr)) {
            showToast(getString(R.string.error_update_db));
            return;
        }

        try {
            Long id = Long.parseLong(idStr);
            String nameStr = etName.getText().toString().trim();
            String ageStr = etAge.getText().toString().trim();
            String descStr = etDesc.getText().toString().trim();
            int age = Integer.parseInt(ageStr);
            String phoneStr = edPhone.getText().toString().trim();


            User user = new User();
            user.setId(id);
            user.setUserName(nameStr);
            user.setUserAge(age);
            user.setUserDesc(descStr);
            user.setPhone(phoneStr);

            mUserDao.update(user);
            if (mainActivity != null) {
                mainActivity.onUpdateDB();
            }
            finish();
            /**
             * 返回刷新
             * 1.直接finish当前界面，然后pause状态的界面OnResume的时候调用查询
             * 2.用广播
             * 3.eventBus
             * 4.OTTO
             */
            EventBus.getDefault().post(new EventMsgList.GreenDaoEvent("汇总类---EventBus"));
            MyOtto.getInstance().post(new OttoMsgList.GreenDaoOtto());

        } catch (NumberFormatException e) {
            showToast(getString(R.string.error_data_parse));
        }
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
}
