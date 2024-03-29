package pt.ulisboa.tecnico.cmov.hoponcmu.data.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.AppDatabase;

public class QuizByMonumentIdLoader extends AsyncTaskLoader {

    private Object mData;

    private AppDatabase appDatabase;

    private String monumentID;

    public QuizByMonumentIdLoader(Context context, String monumentID) {
        super(context);
        appDatabase = AppDatabase.getAppDatabase(context);
        this.monumentID = monumentID;
    }

    @Override
    public Object loadInBackground() {
        // Retrieve all known quizzes based on query.
        return appDatabase.quizDAO().loadQuizByMonumentId(monumentID);
    }

    @Override
    public void deliverResult(Object data) {
        if (isReset()) {
            if (data != null) {
                onReleaseResources(data);
            }
        }
        Object oldData = mData;
        mData = data;

        if (isStarted()) {
            super.deliverResult(data);
        }

        if (oldData != null) {
            onReleaseResources(oldData);
        }
    }

    @Override
    protected void onStartLoading() {
        if (mData != null) {
            deliverResult(mData);
        }

        if (takeContentChanged() || mData == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    public void onCanceled(Object data) {
        super.onCanceled(data);

        onReleaseResources(data);
    }

    @Override
    protected void onReset() {
        super.onReset();

        onStopLoading();

        if (mData != null) {
            onReleaseResources(mData);
            mData = null;
        }
    }

    protected void onReleaseResources(Object data) {

    }
}
