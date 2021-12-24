package com.fooda.wadalzaki.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.fooda.wadalzaki.R;
import com.fooda.wadalzaki.SQLdatabase.SQLEditor;
import com.fooda.wadalzaki.adapters.FavoriteRvAdapter;
import com.fooda.wadalzaki.model.UserInfo;
import com.fooda.wadalzaki.ui.home.ViewContactActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leosunzh on 2015/12/12.
 */
public class FavoriteFragment extends Fragment implements FavoriteRvAdapter.OnRecyclerViewItemClickListener{
    private static final String ARG_POSITION = "position";

    SQLEditor sqlEditor;

    List<UserInfo> list;
    FavoriteRvAdapter favoriteRvAdapter;
    RecyclerView recyclerView;
    TextView hello;

    public static FavoriteFragment newInstance(int position) {
        FavoriteFragment f = new FavoriteFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    /**
     * 初始化页标和数据库操作对象
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sqlEditor = new SQLEditor(getActivity());
        // 创建Adapter，并指定数据集
        list = new ArrayList<>();
        favoriteRvAdapter = new FavoriteRvAdapter(list,getActivity());
        //设置自定义监听器
        favoriteRvAdapter.setOnRecyclerViewItemClickListener(this);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_favorite_layout, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.favorite_rv);
        hello = (TextView) rootView.findViewById(R.id.hello_world);
        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // 设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        // 设置Adapter
        recyclerView.setAdapter(favoriteRvAdapter);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        list.clear();
        list.addAll(sqlEditor.query(null));
        favoriteRvAdapter.notifyDataSetChanged();
        if(list.size()==0){
            hello.setVisibility(View.VISIBLE);
        }else {
            hello.setVisibility(View.GONE);
        }
    }


    @Override
    public void onRvItemClick(UserInfo user) {
        ViewContactActivity.actionStart(getContext(),user);
        //PersonInfoActivity.actionStart(getActivity(),number,name,photo);
    }
}
