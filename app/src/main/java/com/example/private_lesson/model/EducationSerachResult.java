package com.example.private_lesson.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EducationSerachResult {

    @SerializedName("Search")
    List<Education> search;
    public List<Education> getSearch() {
        return search;
    }

    public void setSearch(List<Education> search) {
        this.search = search;
    }
}
