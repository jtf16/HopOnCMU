package pt.ulisboa.tecnico.cmov.hoponcmu.data.loaders;

import android.content.Context;
import android.location.Location;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

import pt.ulisboa.tecnico.cmov.hoponcmu.InterestingConfigChanges;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.AppDatabase;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Monument;

public class MonumentByPartNameLoader extends
        AsyncTaskLoader<List<Monument>> {

    final InterestingConfigChanges mLastConfig = new InterestingConfigChanges();
    private List<Monument> mData;
    private AppDatabase appDatabase;

    private String query;
    private Location location;

    public MonumentByPartNameLoader(Context context, String query, Location location) {
        super(context);
        appDatabase = AppDatabase.getAppDatabase(context);
        this.query = query;
        this.location = location;
    }

    @Override
    public List<Monument> loadInBackground() {
        // Retrieve all known users based on query.
        if (location != null) {
            return appDatabase.monumentDAO().loadMonumentsOrderByDistance(query,
                    Math.sin(Math.toRadians(location.getLatitude())),
                    Math.cos(Math.toRadians(location.getLatitude())),
                    Math.sin(Math.toRadians(location.getLongitude())),
                    Math.cos(Math.toRadians(location.getLongitude())));
        } else {
            return appDatabase.monumentDAO().loadMonumentsOrderByName(query);
        }
    }

    @Override
    public void deliverResult(List<Monument> data) {
        if (isReset()) {
            if (data != null) {
                onReleaseResources(data);
            }
        }
        List<Monument> oldData = mData;
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
    public void onCanceled(List<Monument> data) {
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

    protected void onReleaseResources(List<Monument> data) {

    }
}
