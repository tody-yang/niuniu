package cn.com.niuniu.yj.demo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.greendao.query.QueryBuilder;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.niuniu.yj.demo.R;
import cn.com.niuniu.yj.demo.bean.EventMsgList;
import cn.com.niuniu.yj.demo.bean.OttoMsgList;
import cn.com.niuniu.yj.demo.bean.User;
import cn.com.niuniu.yj.demo.fragment.base.BaseFragment;
import cn.com.niuniu.yj.demo.greendao.MyDbManager;
import cn.com.niuniu.yj.demo.greendao.UserDao;
import cn.com.niuniu.yj.demo.otto.MyOtto;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class GreenDaoFragment extends BaseFragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    @BindView(R.id.et_id)
    EditText etId;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_age)
    EditText etAge;
    @BindView(R.id.et_desc)
    EditText etDesc;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.btn_query_id)
    QMUIRoundButton btnQueryId;
    @BindView(R.id.btn_insert)
    QMUIRoundButton btnInsert;
    @BindView(R.id.list)
    RecyclerView listView;
    Unbinder unbinder;
    @BindView(R.id.btn_query_all)
    QMUIRoundButton btnQueryAll;
    @BindView(R.id.layout_list)
    LinearLayout layoutList;

    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * 适配器
     */
    private MyItemRecyclerViewAdapter myItemRecyclerViewAdapter;

    /**
     * UserDao
     */
    private UserDao mUserDao;

    /**
     * List数据对象
     */
    private List<User> userArrayList = new ArrayList<>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GreenDaoFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static GreenDaoFragment newInstance(int columnCount) {
        GreenDaoFragment fragment = new GreenDaoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_greendao_list, container, false);

        myItemRecyclerViewAdapter = new MyItemRecyclerViewAdapter(userArrayList, mListener, this);

        unbinder = ButterKnife.bind(this, view);

        listView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        listView.setAdapter(myItemRecyclerViewAdapter);
        mUserDao = MyDbManager.getDaoSession(getActivity()).getUserDao();

        checkShowList();
        //注册EventBus
        EventBus.getDefault().register(this);
        //注册OTTO
        MyOtto.getInstance().register(this);

        return view;
    }


    @Subscribe(threadMode = ThreadMode.POSTING)
    public void OnHandleGreenDaoEvent(EventMsgList.GreenDaoEvent eventMsg){
        Log.e("EventBus", "---" +eventMsg.logMsg);
        showToast(eventMsg.logMsg);
        btnQueryAll.performClick();
    }

    @com.squareup.otto.Subscribe
    public void OnHandleGreenDaoOtto(OttoMsgList.GreenDaoOtto greenDaoOtto){
        Log.e("OTTO", "---OTTO----");
        btnQueryAll.performClick();
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        //取消监听
        EventBus.getDefault().unregister(this);
        MyOtto.getInstance().unregister(this);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(User item);
    }

    /**
     * 添加数据库
     *
     * @param user 添加的对象
     * @return
     */
    private boolean addUserToDB(User user) {
        if (null == user) {
            showToast(getString(R.string.error_add_db1));
            return false;
        }

        Long index = mUserDao.insert(user);
        if (-1 != index) {
            return true;
        }
        return false;
    }

    @OnClick(R.id.btn_query_all)
    public void onClickQueryAll() {
        List<User> userList = mUserDao.loadAll();

        if (userList.size() > 0) {
            userArrayList.clear();
            userArrayList.addAll(userList);
            myItemRecyclerViewAdapter.notifyDataSetChanged();
        } else {
            showToast(getString(R.string.query_no_data));
        }

        checkShowList();
    }

    @OnClick(R.id.btn_query_id)
    public void onClickQueryById() {
        String idStr = etId.getText().toString().trim();

        if (TextUtils.isEmpty(idStr)) {
            showToast(getString(R.string.error_query_db));
            return;
        }

        try {
            Long id = Long.parseLong(idStr);
            QueryBuilder qb = mUserDao.queryBuilder();
            qb.where(UserDao.Properties.Id.eq(id));
            List<User> userList = qb.list();
            if (userList.size() > 0) {
                userArrayList.clear();
                userArrayList.addAll(userList);
                myItemRecyclerViewAdapter.notifyDataSetChanged();
            } else {
                showToast(getString(R.string.query_no_data));
            }
            checkShowList();
        } catch (NumberFormatException e) {
            showToast(getString(R.string.error_data_parse));
        }
    }

    @OnClick(R.id.btn_insert)
    public void onClickInsert() {
        String idStr = etId.getText().toString().trim();
        if (TextUtils.isEmpty(idStr)) {
            showToast(getString(R.string.error_add_db2));
            return;
        }

        try {
            Long id = Long.parseLong(idStr);
            String nameStr = etName.getText().toString().trim();
            String ageStr = etAge.getText().toString().trim();
            String descStr = etDesc.getText().toString().trim();
            String phoneStr = etPhone.getText().toString().trim();
            int age = Integer.parseInt(ageStr);


            User user = new User();
            user.setId(id);
            user.setUserName(nameStr);
            user.setUserAge(age);
            user.setUserDesc(descStr);
            user.setPhone(phoneStr);
            boolean success = addUserToDB(user);
            if (success){
                myItemRecyclerViewAdapter.notifyDataSetChanged();
                checkShowList();
            }

        } catch (NumberFormatException e) {
            showToast(getString(R.string.error_data_parse));
        }
    }

    /**
     * 显示列表控件
     */
    private void checkShowList(){
        invisibleView(layoutList);
        if(null != userArrayList & !userArrayList.isEmpty()){
            showView(layoutList);
        }
    }
}
