package com.fooda.wadalzaki.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.fooda.wadalzaki.R;
import com.fooda.wadalzaki.helpers.SetUpImagesHelper;
import com.fooda.wadalzaki.model.UserInfo;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class FavoriteRvAdapter extends RecyclerView.Adapter<FavoriteRvAdapter.ViewHolder> {
    String[] colors = {"#FFDF2323","#FFCE591B","#FF1746E1","#FF1D8138","#FF0FB66B","#FF6660C1","#FF6615A4","#FFA91245"};
    final static int NORMAL = 1;
    int count;

    List<UserInfo> list;
    //MyResolver myResolver;
    OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;//传入的点击监听对象
    Context context;
    public FavoriteRvAdapter(List<UserInfo> list, Context context){
        this.context=context;
        this.list = list;
       // myResolver = new MyResolver(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder;
        switch (viewType){
            default:
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_item_layout,parent,false);
                viewHolder = new ViewHolder(view1,onRecyclerViewItemClickListener);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserInfo user = list.get(position);
       // List<CallLog> logs = myResolver.readCallLog(android.provider.CallLog.Calls.CACHED_NAME + "='" + user.getName() + "'");

        holder.textView_name.setText(user.getName());
        holder.textView_number.setText(user.getPhoneNumber());
        new SetUpImagesHelper(context).setupImage(holder.imageView, holder.favoriteProgress, user.getImagePath());
            //holder.imageView.setImageResource(person.getPhoto());
            //holder.imageView.setImageBitmap(myResolver.getHighPhoto(person.getId()));


    }


    @Override
    public int getItemViewType(int position) {
        switch (list.size()){
            default:return NORMAL;
        }
    }


    @Override
    public int getItemCount() {
        return list.size();//getItemCount()==0时适配器不会绘制itemView
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView;
        TextView textView_name;
        TextView textView_number;
        ImageView imageView_log_type,favoriteIv;
        private ProgressBar favoriteProgress;
        TextView textView_log;
        OnRecyclerViewItemClickListener onRecyclerViewItemClickListener;
        public ViewHolder(View itemView,OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
            super(itemView);
            this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
            itemView.setOnClickListener(this);
            imageView = (ImageView) itemView.findViewById(R.id.favorite_item_iv);
            textView_name = (TextView) itemView.findViewById(R.id.favorite_name_tv);
            textView_number = (TextView) itemView.findViewById(R.id.favorite_number_tv);
            imageView_log_type = (ImageView) itemView.findViewById(R.id.favorite_type_iv);
            favoriteProgress =  itemView.findViewById(R.id.favorite_progress);
            textView_log = (TextView) itemView.findViewById(R.id.favorite_log_tv);
        }

        @Override
        public void onClick(View v) {
            UserInfo person = list.get(getPosition());
            onRecyclerViewItemClickListener.onRvItemClick(person);
        }
    }

    //定义监听器外部实现接口，
    public static interface OnRecyclerViewItemClickListener {
        void onRvItemClick(UserInfo user);
    }

    //模仿ListView的设置监听对象方法
    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener){
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }
}
