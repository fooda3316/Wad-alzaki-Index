package com.fooda.wadalzaki.more;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.fooda.wadalzaki.R;

import java.util.ArrayList;
import java.util.List;

public class MoreAppsFragment extends Fragment implements MoreAppsAdapter.MoreAppsListener {
    private RecyclerView recyclerView;
    private MoreAppsAdapter adapter;
    private List<MoreApps> appsList;
    public MoreAppsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more_apps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.more_apps_rv);
        fillList();
        adapter=new MoreAppsAdapter(appsList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private void fillList() {
        appsList=new ArrayList<>();
        appsList.add(new MoreApps("Grammar","market://details?id=com.fooda.grammar",R.drawable.grammar));
        appsList.add(new MoreApps("Grammar tests","market://details?id=com.fooda.grammarexams",R.drawable.grammar_exams));
        appsList.add(new MoreApps("تيسير مصطلح الحديث","market://details?id=com.fooda.hadeeth",R.drawable.hdeath));
        appsList.add(new MoreApps("عمدة الاحكام","market://details?id=com.fooda.omdatalahkam",R.drawable.omada));

    }

    @Override
    public void examplesOnClick(MoreApps example) {
        goToMarket(example.getUri());
    }

    private void goToMarket(String uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }
}