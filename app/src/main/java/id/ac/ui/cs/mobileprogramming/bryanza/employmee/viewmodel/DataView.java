package id.ac.ui.cs.mobileprogramming.bryanza.employmee.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import id.ac.ui.cs.mobileprogramming.bryanza.employmee.data.DataRepository;
import id.ac.ui.cs.mobileprogramming.bryanza.employmee.model.ModelEmployee;

import java.util.List;

public class DataView extends AndroidViewModel {
    private DataRepository repository;
    private LiveData<List<ModelEmployee>> allNotes;

    public DataView(@NonNull Application application) {
        super(application);
        repository = new DataRepository(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(ModelEmployee note) {
        repository.insert(note);
    }

    public void update(ModelEmployee note) {
        repository.update(note);
    }

    public void delete(ModelEmployee note) {
        repository.delete(note);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public LiveData<List<ModelEmployee>> getAllNotes() {
        return allNotes;
    }
}
