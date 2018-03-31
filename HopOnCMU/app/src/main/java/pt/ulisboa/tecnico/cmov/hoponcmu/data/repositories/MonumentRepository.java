package pt.ulisboa.tecnico.cmov.hoponcmu.data.repositories;

import android.content.Context;
import android.os.AsyncTask;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.AppDatabase;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Monument;

public class MonumentRepository {

    static AppDatabase appDatabase;

    public MonumentRepository(Context context) {
        appDatabase = AppDatabase.getAppDatabase(context);
    }

    public void insertMonument(final Monument... monuments) {
        new GetMonumentInsertTask().execute(monuments);
    }

    public void deleteMonument(final Monument... monuments) {
        new GetMonumentDeleteTask().execute(monuments);
    }

    public void updateMonument(final Monument... monuments) {
        new GetMonumentUpdateTask().execute(monuments);
    }

    private static class GetMonumentInsertTask extends AsyncTask<Monument, Void, Void> {

        @Override
        protected Void doInBackground(Monument... monuments) {
            appDatabase.monumentDAO().insertMonuments(monuments);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private static class GetMonumentDeleteTask extends AsyncTask<Monument, Void, Void> {

        @Override
        protected Void doInBackground(Monument... monuments) {
            appDatabase.monumentDAO().deleteMonuments(monuments);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    private static class GetMonumentUpdateTask extends AsyncTask<Monument, Void, Void> {

        @Override
        protected Void doInBackground(Monument... monuments) {
            appDatabase.monumentDAO().updateMonuments(monuments);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
