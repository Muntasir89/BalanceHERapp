package com.example.balanceher.MVVM.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.balanceher.MVVM.Model.Note;
import com.example.balanceher.MVVM.Repository.NoteRepo;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private final NoteRepo noteRepo;
    private LiveData<List<Note>> noteList;
    public NoteViewModel(@NonNull Application application){
        super(application);
        noteRepo = new NoteRepo(application);
        noteList = noteRepo.getAllData();
    }
    public void insert(Note note){
        noteRepo.insertData(note);
    }

    public void delete(Note note){
        noteRepo.deleteData(note);
    }

    public void update(Note note){
        noteRepo.updateData(note);
    }

    public LiveData<List<Note>> getNoteList(){
        return noteList;
    }
}
