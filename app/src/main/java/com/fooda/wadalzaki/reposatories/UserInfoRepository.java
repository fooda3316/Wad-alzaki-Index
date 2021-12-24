//package com.fooda.wadalzaki.reposatories;
//
//import android.app.Application;
//import android.os.AsyncTask;
//
//import androidx.lifecycle.LiveData;
//
//import com.fooda.wadalzaki.room.UserInfoDao;
//
//import java.util.List;
//
//public class UserInfoRepository {
//    private UserInfoDao infoDao;
//    private LiveData<List<UserInfo>> allNotes;
//    private static Boolean isFavorite;
//
//    public UserInfoRepository(Application application) {
//        UserInfoDatabase database = UserInfoDatabase.getInstance(application);
//        infoDao = database.userInfoDao();
//        allNotes = infoDao.getAllNotes();
//    }
//
//    public void insert(UserInfo info) {
//        new InsertNoteAsyncTask(infoDao).execute(info);
//    }
//
//    public void update(UserInfo info) {
//        new UpdateNoteAsyncTask(infoDao).execute(info);
//    }
//
//    public void delete(UserInfo info) {
//        new DeleteNoteAsyncTask(infoDao).execute(info);
//    }
//
//    public void deleteAllUsers() {
//        new DeleteAllNotesAsyncTask(infoDao).execute();
//    }
//
//    public LiveData<List<UserInfo>> getAllUsers() {
//        return allNotes;
//    }
//    public LiveData<UserInfo> getUser(String user) {
//        return infoDao.getTask(user);
//    }
//
//
//    private static class InsertNoteAsyncTask extends AsyncTask<UserInfo, Void, Void> {
//        private UserInfoDao userInfoDao;
//
//        private InsertNoteAsyncTask(UserInfoDao noteDao) {
//            this.userInfoDao = noteDao;
//        }
//
//        @Override
//        protected Void doInBackground(UserInfo... notes) {
//            userInfoDao.insert(notes[0]);
//            return null;
//        }
//    }
//
//    private static class UpdateNoteAsyncTask extends AsyncTask<UserInfo, Void, Void> {
//        private UserInfoDao noteDao;
//
//        private UpdateNoteAsyncTask(UserInfoDao noteDao) {
//            this.noteDao = noteDao;
//        }
//
//        @Override
//        protected Void doInBackground(UserInfo... notes) {
//            noteDao.update(notes[0]);
//            return null;
//        }
//
//    }
//
//    private static class DeleteNoteAsyncTask extends AsyncTask<UserInfo, Void, Void> {
//        private UserInfoDao infoDao;
//
//        private DeleteNoteAsyncTask(UserInfoDao infoDao) {
//            this.infoDao = infoDao;
//        }
//
//        @Override
//        protected Void doInBackground(UserInfo... notes) {
//            infoDao.delete(notes[0]);
//            return null;
//        }
//    }
//
//    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
//        private UserInfoDao infoDao;
//
//        private DeleteAllNotesAsyncTask(UserInfoDao noteDao) {
//            this.infoDao = noteDao;
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            infoDao.deleteAllNotes();
//            return null;
//        }
//    }
//    private static class IsFavoriteNotesAsyncTask extends AsyncTask<String, Void, Boolean> {
//
//        private UserInfoDao infoDao;
//
//        private IsFavoriteNotesAsyncTask(UserInfoDao infoDao) {
//            this.infoDao = infoDao;
//        }
//
//
//        @Override
//        protected Boolean doInBackground(String... favorites) {
//            return infoDao.isLessonFavorite(favorites[0]);
//        }
//
//        @Override
//        protected void onPostExecute(Boolean aBoolean) {
//            super.onPostExecute(aBoolean);
//            isFavorite=aBoolean;
//        }
//    }
//
//}
