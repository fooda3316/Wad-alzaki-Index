//package com.fooda.wadalzaki.room;
//
//import androidx.lifecycle.LiveData;
//import androidx.room.Dao;
//import androidx.room.Delete;
//import androidx.room.Insert;
//import androidx.room.OnConflictStrategy;
//import androidx.room.Query;
//import androidx.room.Update;
//
//import com.fooda.wadalzaki.model.UserInfo;
//
//import java.util.List;
//
//@Dao
//public interface UserInfoDao {
//    @Insert(onConflict = OnConflictStrategy.IGNORE)  // or OnConflictStrategy.REPLACE
//    void insert(UserInfo userInfo);
//
//    @Update
//    void update(UserInfo userInfo);
//
//    @Delete
//    void delete(UserInfo note);
//
//    @Query("DELETE FROM UserInfo")
//    void deleteAllNotes();
//
//    @Query("SELECT * FROM UserInfo ORDER BY name DESC")
//    LiveData<List<UserInfo>> getAllNotes();
//    @Query("SELECT * from UserInfo where  name  LIKE :title")
//    boolean isLessonFavorite(String title);
//    @Query("SELECT * FROM UserInfo WHERE name =:name")
//    LiveData<UserInfo> getTask(String name);
//
//}
//
