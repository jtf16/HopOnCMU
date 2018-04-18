package pt.ulisboa.tecnico.cmov.hoponcmu.data.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import pt.ulisboa.tecnico.cmov.hoponcmu.InterestingConfigChanges;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.AppDatabase;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.UserQuiz;

public class UserQuizByIdAndUserLoader extends
        AsyncTaskLoader<UserQuiz> {

    final InterestingConfigChanges mLastConfig = new InterestingConfigChanges();
    private UserQuiz mData;
    private AppDatabase appDatabase;

    private long id;
    private String username;

    public UserQuizByIdAndUserLoader(Context context, long id, String username) {
        super(context);
        appDatabase = AppDatabase.getAppDatabase(context);
        this.id = id;
        this.username = username;
    }

    @Override
    public UserQuiz loadInBackground() {
        // Retrieve all known users based on query.
        return appDatabase.userQuizDAO().loadUserQuizByIdAndUsername(id, username);
    }

    @Override
    public void deliverResult(UserQuiz data) {
        if (isReset()) {
            if (data != null) {
                onReleaseResources(data);
            }
        }
        UserQuiz oldData = mData;
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
    public void onCanceled(UserQuiz data) {
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

    protected void onReleaseResources(UserQuiz data) {

    }
}
