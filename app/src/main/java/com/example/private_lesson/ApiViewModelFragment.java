package com.example.private_lesson;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.private_lesson.model.Education;
import com.example.private_lesson.model.EducationModel;

import java.util.List;

public class ApiViewModelFragment extends ViewModel{


        private LiveData<List<Education>> dataByTitle = EducationModel.instance().searchEducationByTitle("title");


        LiveData<List<Education>> getData(){
            return dataByTitle;
        }

    }

