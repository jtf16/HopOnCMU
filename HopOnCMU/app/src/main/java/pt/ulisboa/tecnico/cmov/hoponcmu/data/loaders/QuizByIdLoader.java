package pt.ulisboa.tecnico.cmov.hoponcmu.data.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import pt.ulisboa.tecnico.cmov.hoponcmu.InterestingConfigChanges;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.AppDatabase;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Quiz;

public class QuizByIdLoader extends
        AsyncTaskLoader<Quiz> {

    final InterestingConfigChanges mLastConfig = new InterestingConfigChanges();
    private Quiz mData;
    private AppDatabase appDatabase;

    private long id;

    public QuizByIdLoader(Context context, long id) {
        super(context);
        appDatabase = AppDatabase.getAppDatabase(context);
        this.id = id;
    }

    @Override
    public Quiz loadInBackground() {
        // Retrieve all known users based on query.
        return appDatabase.quizDAO().loadQuizById(id);
    }

    @Override
    public void deliverResult(Quiz data) {
        if (isReset()) {
            if (data != null) {
                onReleaseResources(data);
            }
        }
        Quiz oldData = mData;
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

        boolean configChange = mLastConfig.applyNewConfig(getContext().getResources());

        if (takeContentChanged() || mData == null || configChange) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    public void onCanceled(Quiz data) {
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

    protected void onReleaseResources(Quiz data) {

    }
}
