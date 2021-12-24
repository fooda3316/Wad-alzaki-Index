package com.fooda.wadalzaki.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

//import com.baoyz.swipemenulistview.SwipeMenu;
//import com.baoyz.swipemenulistview.SwipeMenuCreator;
//import com.baoyz.swipemenulistview.SwipeMenuItem;
//import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.fooda.wadalzaki.helpers.SharedPreferencesHelper;
import com.fooda.wadalzaki.model.UserInfo;
import com.fooda.wadalzaki.ui.home.NewPersonActivity;
import com.fooda.wadalzaki.ui.home.ViewContactActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.fooda.wadalzaki.R;
import com.fooda.wadalzaki.adapters.MyListViewAdapter;

import com.fooda.wadalzaki.search.AlphabetSearch;
import com.fooda.wadalzaki.utils.FabAnimatorSet;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;


public class PersonFragment extends Fragment implements AdapterView.OnItemClickListener,View.OnClickListener{

    private static final String ARG_POSITION = "position";

    int index = 0;
    List<UserInfo> list;
    ListView listView;
    MyListViewAdapter myListViewAdapter;
    FloatingActionButton add_fab;
    private FirebaseFirestore db;
    private CollectionReference allUsersRef;
    LinearLayout alphabetLayout;
    TextView current;
    AlphabetSearch alphabetSearch;
    private SharedPreferencesHelper preferencesHelper;
    private static final String TAG = "PersonFragment";
    public static PersonFragment newInstance(int position) {
        PersonFragment f = new PersonFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }


    /**
     * 初始化页标和数据操作对象
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();
        myListViewAdapter = new MyListViewAdapter(getActivity(), R.layout.list_item_layout,list);
        preferencesHelper=new SharedPreferencesHelper(getContext());
        //position = getArguments().getInt(ARG_POSITION);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_persons_layout, container, false);
        db = FirebaseFirestore.getInstance();
        allUsersRef = db.collection("UserList");
        add_fab =  rootView.findViewById(R.id.add_fab);
        add_fab.setOnClickListener(this);
        listView =  rootView.findViewById(R.id.persons_list);
        listView.setOnItemClickListener(this);
        alphabetLayout =  rootView.findViewById(R.id.alphabetLayout);
        current =  rootView.findViewById(R.id.current);
//        query_ET = (EditText) rootView.findViewById(R.id.query_ET);

//        list = myResolver.readContacts(null);//在onStart里面赋值，减少读取次数
        listView.setAdapter(myListViewAdapter);
        //添加字母侧栏索引
        alphabetSearch = new AlphabetSearch(alphabetLayout,listView,current);
        //添加fab_add动画
        new FabAnimatorSet().FabAnimatorSet(listView,add_fab);
        if (preferencesHelper.isUserLogged()){
            getAllUsersFromFireStore();
        }
       // addSwipeFunctionality(listView);
        return rootView;

    }

    /*private void addSwipeFunctionality(SwipeMenuListView listView) {
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(90));
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem callItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                callItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                callItem.setWidth(90);
                // set a icon
                callItem.setIcon(R.drawable.icon_call);
                // add to menu
                menu.addMenuItem(callItem);
            }
        };

// set creator
        listView.setMenuCreator(creator);

    }*/


    /**
     * 可见时刷新ListView
     * 刷新搜索框
     */
    @Override
    public void onStart() {
        super.onStart();
        if (preferencesHelper.isUserLogged()){
        allUsersRef.orderBy("name").addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    Log.e(TAG, "onEvent: "+e.toString() );
                    return;
                }

                List<UserInfo> list=new ArrayList<>();
                if (!queryDocumentSnapshots.isEmpty()){
                    for (DocumentSnapshot snapshot:queryDocumentSnapshots){
                        UserInfo userInfo =  snapshot.toObject(UserInfo.class);
                        list.add(userInfo);
                    }
                    reFlesh(list);
                }

            }
        });}



        listView.setSelection(index);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        index = this.listView.getFirstVisiblePosition();
    }


    public void reFlesh(List<UserInfo> newList){
        this.list.clear();
        this.list.addAll(newList);
        myListViewAdapter.notifyDataSetChanged();
        alphabetSearch.whenListChange(newList);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UserInfo person = (UserInfo) listView.getItemAtPosition(position);
        ViewContactActivity.actionStart(getActivity(),person);
        //PersonInfoActivity.actionStart(getActivity(),person);
        //PersonInfoActivity.actionStart(getActivity(),person.getPhoneNumber(),person.getName(),person.getPhoneNumber());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_fab) {
            NewPersonActivity.actionStart(getActivity(), false);
        }
    }
    private void getAllUsersFromFireStore() {
        allUsersRef.orderBy("name", Query.Direction.ASCENDING).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
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
                    reFlesh(list);
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