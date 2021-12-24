//package com.fooda.wadalzaki.reposatories;
//
//import android.app.Application;
//import android.content.pm.ApplicationInfo;
//import android.util.Log;
//
//import androidx.lifecycle.MutableLiveData;
//
//
//import com.fooda.wadalzaki.helpers.SharedPreferencesHelper;
//import com.fooda.wadalzaki.model.RemoteUser;
//import com.google.firebase.firestore.CollectionReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.EventListener;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.FirebaseFirestoreException;
//import com.google.firebase.firestore.Query;
//import com.google.firebase.firestore.QuerySnapshot;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import io.reactivex.Observable;
//import io.reactivex.Observer;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.schedulers.Schedulers;
//import phoenixlib.io.rxfirebase.database.RxDatabase;
//
//public class UsersRepository {
//    public MutableLiveData<List<RemoteUser>> liveData;
//    Observable<RemoteUser> observable;
//    private SharedPreferencesHelper preferencesHelper;
//    private static final String TAG = "UsersRepository";
//    private FirebaseFirestore db;
//    private CollectionReference allUsersRef;
//    public UsersRepository(Application application) {
//        preferencesHelper=new SharedPreferencesHelper(application);
//        db = FirebaseFirestore.getInstance();
//        allUsersRef = db.collection("UserList");
//        liveData = new MutableLiveData<>();
//        try {
//
//            if (preferencesHelper.isUserLogged()){
//                allUsersRef.orderBy("name").addSnapshotListener(application, new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
//                        if (e != null) {
//                            Log.e(TAG, "onEvent: "+e.toString() );
//                            return;
//                        }
//
//                        List<RemoteUser> list=new ArrayList<>();
//                        if (!queryDocumentSnapshots.isEmpty()){
//                            for (DocumentSnapshot snapshot:queryDocumentSnapshots){
//                                RemoteUser remoteUser=  snapshot.toObject(RemoteUser.class);
//                                list.add(remoteUser);
//                            }
//
//                        }
//                        Query query = allUsersRef.orderBy("name", Query.Direction.DESCENDING);
//
//                        RxDatabase.query(query)
//                                .subscribe(dataSnapshot -> {/*Your DataSnapshot*/});
//                        list.map(Observable::fromIterable) // Single<List> to Observable
//                                .map(new Function<Observable<Item>, Observable<Item2>>() {
//                                    // I don't really know how I can do it here
//                                })
//                                .subscribeOn(//.../)
//                                .observeOn(//.../);
//                        ObservableList<RemoteUser> oListStavaka = FXCollections.observableArrayList(listStavaka);
//                        observable.toList()= data
//                                .subscribeOn(Schedulers.io())
//                                .observeOn(AndroidSchedulers.mainThread());
//                    }
//                });}
//
//        }
//        catch (Exception ex){
//            Log.e("observableException",ex.toString());
//        }
//
//    }
//
//
//    public Observable<RemoteUser> getObservable() {
//      //  Log.d("is null", String.valueOf(observable==null));
//        if (observable==null)
//            return new Observable<RemoteUser>() {
//                @Override
//                protected void subscribeActual(Observer<? super RemoteUser> observer) {
//
//                }
//            };
//        return observable;
//    }
//}
