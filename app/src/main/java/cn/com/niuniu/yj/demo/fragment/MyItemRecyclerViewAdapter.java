package cn.com.niuniu.yj.demo.fragment;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.com.niuniu.yj.demo.R;
import cn.com.niuniu.yj.demo.bean.User;
import cn.com.niuniu.yj.demo.fragment.GreenDaoFragment.OnListFragmentInteractionListener;
import cn.com.niuniu.yj.demo.greendao.MyDbManager;
import cn.com.niuniu.yj.demo.greendao.UserDao;


public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<User> mValues;
    private final OnListFragmentInteractionListener mListener;
    /**
     * 主Fragment
     */
    private GreenDaoFragment parentFragment;



    public MyItemRecyclerViewAdapter(List<User> items, OnListFragmentInteractionListener listener, GreenDaoFragment parentFragment) {
        mValues = items;
        mListener = listener;
        this.parentFragment = parentFragment;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_greendao, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);

        holder.itemID.setText(holder.mItem.getId().toString());
        holder.itemName.setText(holder.mItem.getUserName());
        holder.itemAge.setText(String.valueOf(holder.mItem.getUserAge()));
        holder.itemDesc.setText(holder.mItem.getUserDesc());
        holder.itemPhone.setText(holder.mItem.getPhone());

        holder.btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (deleteUser(holder.mItem)){
                            mValues.remove(position);
                            notifyDataSetChanged();
                        }
                    }
                }
        );

        holder.btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (null != mListener) {
                            // Notify the active callbacks interface (the activity, if the
                            // fragment is attached to one) that an item has been selected.

                            mListener.onListFragmentInteraction(holder.mItem);
                        }
                    }
                }
        );


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public User mItem;

        @BindView(R.id.item_id)
        TextView itemID;
        @BindView(R.id.item_name)
        TextView itemName;
        @BindView(R.id.item_age)
        TextView itemAge;
        @BindView(R.id.item_desc)
        TextView itemDesc;
        @BindView(R.id.item_phone)
        TextView itemPhone;
        @BindView(R.id.btn_delete)
        Button btnDelete;
        @BindView(R.id.btn_update)
        Button btnUpdate;


        public ViewHolder(View view) {
            super(view);
            //仔细对比下activity的绑定方法，同时this不是getActivity()
            ButterKnife.bind(this,view);
            mView = view;

        }

        @Override
        public String toString() {
            return super.toString() + " '" + itemName.getText() + "'";
        }


    }

    /**
     * 删除User对象
     * @param user  删除的User对象
     * @return  boolean
     */
    private boolean deleteUser(User user){
        UserDao userDao = MyDbManager.getDaoSession(parentFragment.getActivity()).getUserDao();

        if (null != user){
            userDao.delete(user);
        }else{
            Activity activity = parentFragment.getActivity();
            Toast.makeText(activity, activity.getString(R.string.error_delete_db), Toast.LENGTH_SHORT).show();
        }

        User userDB = userDao.load(user.getId());
        if (userDB == null){
            return true;
        }

        return false;

//        return (null != userDao.load(user.getId()));
    }



}
