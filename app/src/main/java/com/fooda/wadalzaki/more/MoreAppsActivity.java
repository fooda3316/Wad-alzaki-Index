package com.fooda.wadalzaki.more;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fooda.wadalzaki.R;

import java.util.ArrayList;
import java.util.List;

public class MoreAppsActivity extends AppCompatActivity implements MoreAppsAdapter.MoreAppsListener {
    private RecyclerView recyclerView;
    private MoreAppsAdapter adapter;
    private List<MoreApps> appsList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_more_apps);
        fillList();
        adapter=new MoreAppsAdapter(appsList,this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.more_apps));
        }
        recyclerView=findViewById(R.id.more_apps_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

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