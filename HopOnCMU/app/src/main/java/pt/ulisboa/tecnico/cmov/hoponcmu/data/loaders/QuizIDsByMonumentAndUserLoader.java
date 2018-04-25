package pt.ulisboa.tecnico.cmov.hoponcmu.data.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import pt.ulisboa.tecnico.cmov.hoponcmu.InterestingConfigChanges;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.AppDatabase;

public class QuizIDsByMonumentAndUserLoader extends
        AsyncTaskLoader<List<Long>> {

    final InterestingConfigChanges mLastConfig = new InterestingConfigChanges();
    private List<Long> mData;
    private AppDatabase appDatabase;

    private String monumentID;
    private String username;

    public QuizIDsByMonumentAndUserLoader(Context context, String monumentID, String username) {
        super(context);
        appDatabase = AppDatabase.getAppDatabase(context);
        this.monumentID = monumentID;
        this.username = username;
    }

    @Override
    public List<Long> loadInBackground() {
        // Retrieve all known users based on query.
        return appDatabase.transactionDAO().loadUserQuizzesByMonumentIdAndUser(monumentID, username);
    }

    @Override
    public void deliverResult(List<Long> data) {
        if (isReset()) {
            if (data != null) {
                onReleaseResources(data);
            }
        }
        List<Long> oldData = mData;
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
    public void onCanceled(List<Long> data) {
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

    protected void onReleaseResources(List<Long> data) {

    }
}
