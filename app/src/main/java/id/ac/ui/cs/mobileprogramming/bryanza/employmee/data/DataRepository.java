package id.ac.ui.cs.mobileprogramming.bryanza.employmee.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import id.ac.ui.cs.mobileprogramming.bryanza.employmee.model.ModelEmployee;

import java.util.List;

public class DataRepository {
    private DataInterface noteDao;
    private LiveData<List<ModelEmployee>> allNotes;

    public DataRepository(Application application) {
        DataInstance database = DataInstance.Companion.getInstance(application);
        noteDao = database.DataInterface();
        allNotes = noteDao.getLiveData();
    }

    public void insert(ModelEmployee note) {
        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    public void update(ModelEmployee note) {
        new UpdateNoteAsyncTask(noteDao).execute(note);
    }

    public void delete(ModelEmployee note) {
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(noteDao).execute();
    }

    public LiveData<List<ModelEmployee>> getAllNotes() {
        return allNotes;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<ModelEmployee, Void, Void> {
        private DataInterface noteDao;

        private InsertNoteAsyncTask(DataInterface noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(ModelEmployee... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<ModelEmployee, Void, Void> {
        private DataInterface noteDao;

        private UpdateNoteAsyncTask(DataInterface noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(ModelEmployee... notes) {
            noteDao.updateModel(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<ModelEmployee, Void, Void> {
        private DataInterface noteDao;

        private DeleteNoteAsyncTask(DataInterface noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(ModelEmployee... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private DataInterface noteDao;

        private DeleteAllNotesAsyncTask(DataInterface noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAll();
            return null;
        }
    }
}

