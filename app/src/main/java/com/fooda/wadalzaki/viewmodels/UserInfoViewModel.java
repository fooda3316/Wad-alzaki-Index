//package com.fooda.wadalzaki.viewmodels;
//
//import android.app.Application;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.AndroidViewModel;
//import androidx.lifecycle.LiveData;
//
//import com.fooda.wadalzaki.model.UserInfo;
//import com.fooda.wadalzaki.reposatories.UserInfoRepository;
//
//import java.util.List;
//
//public class UserInfoViewModel  extends AndroidViewModel {
//private UserInfoRepository repository=null;
//private LiveData<List<UserInfo>> allUsers;
//
//public UserInfoViewModel(@NonNull Application application) {
//        super(application);
//        repository = new UserInfoRepository(application);
//        allUsers = repository.getAllUsers();
//        }
//
//public void insert(UserInfo info) {
//        repository.insert(info);
//        }
//
//public void update(UserInfo info) {
//        repository.update(info);
//        }
//
//public void delete(UserInfo info) {
//        repository.delete(info);
//        Log.d("repository",(repository==null)+"");
//        }
//
//public void deleteAllUsers() {
//        repository.deleteAllUsers();
//        }
//
//public LiveData<List<UserInfo>> getAllUsers() {
//       // Log.d("repository","re");
//        return allUsers;
//        }
//
////        public Boolean isFavorite(String lesson){
////                Log.d("is REP null",(null==repository)+"");
////      ///  return true;
////                return repository.isLessonFavorite(lesson);
////        }
//        public Boolean isFavorite(String lesson){
//               // Log.d("is REP null",(null==repository)+"");
//                ///  return true;
//                return repository.getUser(lesson)!=null;
//        }
//}
