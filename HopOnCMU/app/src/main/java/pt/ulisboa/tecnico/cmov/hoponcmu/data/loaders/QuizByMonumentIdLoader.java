package pt.ulisboa.tecnico.cmov.hoponcmu.data.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.AppDatabase;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;

public class QuizByMonumentIdLoader extends AsyncTaskLoader<List<Quiz>> {

    private List<Quiz> mData;

    private AppDatabase appDatabase;

    private String monumentID;

    public QuizByMonumentIdLoader(Context context, String monumentID) {
        super(context);
        appDatabase = AppDatabase.getAppDatabase(context);
        this.monumentID = monumentID;
    }

    @Override
    public List<Quiz> loadInBackground() {
        // Retrieve all known quizzes based on query.
        return appDatabase.quizDAO().loadQuizByMonumentId(monumentID);
    }

    @Override
    public void deliverResult(List<Quiz> data) {
        if (isReset()) {
            if (data != null) {
                onReleaseResources(data);
            }
        }
        List<Quiz> oldData = mData;
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
    public void onCanceled(List<Quiz> data) {
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

    protected void onReleaseResources(List<Quiz> data) {

    }
}
