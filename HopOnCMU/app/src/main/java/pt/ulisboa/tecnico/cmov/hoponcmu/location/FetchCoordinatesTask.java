package pt.ulisboa.tecnico.cmov.hoponcmu.location;

import android.location.Location;
import android.os.AsyncTask;

public class FetchCoordinatesTask extends AsyncTask<Location, Void, Location> {

    private OnTaskCompleted mListener;

    public FetchCoordinatesTask(OnTaskCompleted listener) {
        mListener = listener;
    }

    @Override
    protected Location doInBackground(Location... locations) {
        return locations[0];
    }

    @Override
    protected void onPostExecute(Location location) {
        mListener.onTaskCompleted(location);
        super.onPostExecute(location);
    }

    public interface OnTaskCompleted {
        void onTaskCompleted(Location location);
    }
}
