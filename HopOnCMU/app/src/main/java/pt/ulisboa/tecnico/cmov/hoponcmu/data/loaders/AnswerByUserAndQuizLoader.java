package pt.ulisboa.tecnico.cmov.hoponcmu.data.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import pt.ulisboa.tecnico.cmov.hoponcmu.InterestingConfigChanges;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.AppDatabase;

public class AnswerByUserAndQuizLoader extends
        AsyncTaskLoader<List> {

    final InterestingConfigChanges mLastConfig = new InterestingConfigChanges();
    private List mData;
    private AppDatabase appDatabase;

    private String username;
    private long quizID;

    public AnswerByUserAndQuizLoader(Context context, String username, long quizID) {
        super(context);
        appDatabase = AppDatabase.getAppDatabase(context);
        this.username = username;
        this.quizID = quizID;
    }

    @Override
    public List loadInBackground() {
        // Retrieve all known users based on query.
        return appDatabase.answerDAO().loadAnswersByUserAndQuiz(username, quizID);
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
