package com.fooda.wadalzaki.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.fooda.wadalzaki.R;
import com.fooda.wadalzaki.adapters.MyListViewAdapter;
import com.fooda.wadalzaki.model.UserInfo;
import com.fooda.wadalzaki.ui.home.ViewContactActivity;
import com.fooda.wadalzaki.utils.Utility;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Dialog search interface واجهة البحث
 * Created by leosunzh on 2015/12/23.
 */
public class SearchActivity extends AppCompatActivity implements TextWatcher,
        AdapterView.OnItemClickListener,View.OnClickListener{
    //search field
    EditText editText;
    //Search suggestion listView
    ListView listView;
    MyListViewAdapter myListViewAdapter;
    List<UserInfo> list;
    List<UserInfo> consult;//Search suggestion list
    private static final String TAG = "SearchActivity";
  //  PinYinAndNumSearch pinYinAndNumSearch;
  private FirebaseFirestore db;
    private CollectionReference allUsersRef;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_layout);
        Window window = getWindow();
        db = FirebaseFirestore.getInstance();
        allUsersRef = db.collection("UserList");
        getAllUsersFromFireStore();

        //userInfoViewModel = ViewModelProviders.of(this).get(UserInfoViewModel.class);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        //Set the size and transparency of the search window
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = layoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.TOP;
//        layoutParams.alpha = 0.5f;
        window.setAttributes(layoutParams);
        //back button
        findViewById(R.id.back_imb_dia).setOnClickListener(this);
        findViewById(R.id.clear_imb_dia).setOnClickListener(this);
        editText = (EditText) findViewById(R.id.search_ed_dia);
        editText.addTextChangedListener(this);
        listView = (ListView) findViewById(R.id.result_lv_dia);
        listView.setOnItemClickListener(this);
        //Initialize the data إعداد البيانات
        list = new ArrayList<>();
        //fill the search list by reading all contacts

      //  pinYinAndNumSearch = new PinYinAndNumSearch();

        myListViewAdapter = new MyListViewAdapter(this,R.layout.list_item_layout,list);
        listView.setAdapter(myListViewAdapter);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}
    @Override
    public void afterTextChanged(Editable s) {
            list.clear();
            list.addAll(Utility.searchForPerson(consult,s));
            myListViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UserInfo user = (UserInfo) listView.getItemAtPosition(position);
        ViewContactActivity.actionStart(this,user);
       // PersonInfoActivity.actionStart(this,person.getNumber(),person.getName(),person.getPhoto());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_imb_dia:
                finish();break;
            case R.id.clear_imb_dia:
                editText.setText(null);break;
        }
    }

    /**
     * come back to SearchActivity
     */
    public static void actionStart(Context context){
        Intent intent = new Intent(context,SearchActivity.class);
        context.startActivity(intent);
    }
    private void getAllUsersFromFireStore() {
        allUsersRef.orderBy("name").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<UserInfo> list=new ArrayList<>();
                if (!queryDocumentSnapshots.isEmpty()){
                    Log.e(TAG, "queryDocumentSnapshots is not empty: " );
                    for (DocumentSnapshot snapshot:queryDocumentSnapshots){
                        UserInfo userInfo =  snapshot.toObject(UserInfo.class);
                        Log.e(TAG, "onEvent: Name "+ userInfo.getName() );
                        Log.e(TAG, "onEvent: Email "+ userInfo.getEmail() );
                        Log.e(TAG, "onEvent: Uid "+ userInfo.getUid() );

                        list.add(userInfo);
                    }
                    consult = list;
                    //
                    //
                }
                // userInfoViewModel.insert();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.e(TAG, "onFailure: "+e.toString());
            }
        });
    }

}
