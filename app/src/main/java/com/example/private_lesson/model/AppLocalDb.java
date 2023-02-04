package com.example.private_lesson.model;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.private_lesson.MyApplication;
import com.example.private_lesson.model.PostDao;
import com.example.private_lesson.model.TeacherDao;



@Database(entities = {Teacher.class,Post.class}, version =215)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract PostDao postDao();
    public abstract TeacherDao teacherDao();
}

public class AppLocalDb {

        static public AppLocalDbRepository getAppDb() {
            return Room.databaseBuilder(MyApplication.getMyContext(),
                            AppLocalDbRepository.class,
                            "dbFileName.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        private AppLocalDb(){}

}
