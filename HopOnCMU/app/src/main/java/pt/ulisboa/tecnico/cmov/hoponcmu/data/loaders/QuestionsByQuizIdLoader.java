package pt.ulisboa.tecnico.cmov.hoponcmu.data.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import pt.ulisboa.tecnico.cmov.hoponcmu.data.AppDatabase;

public class QuestionsByQuizIdLoader extends
        AsyncTaskLoader<List> {

    private List mData;

    private AppDatabase appDatabase;

    private long id;

    public QuestionsByQuizIdLoader(Context context, long id) {
        super(context);
        appDatabase = AppDatabase.getAppDatabase(context);
        this.id = id;
    }

    @Override
    public List loadInBackground() {
        // Retrieve all known quizzes based on query.
        return appDatabase.transactionDAO().loadQuestionsByQuizId(id);
    }

    @Override
    public void deliverResult(List data) {
        if (isReset()) {
            if (data != null) {
                onReleaseResources(data);
            }
        }
        List oldData = mData;
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
    public void onCanceled(List data) {
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

    protected void onReleaseResources(List data) {

    }
}
