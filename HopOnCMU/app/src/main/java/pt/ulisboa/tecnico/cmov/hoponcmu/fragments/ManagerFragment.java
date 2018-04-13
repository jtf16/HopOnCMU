package pt.ulisboa.tecnico.cmov.hoponcmu.fragments;

import android.support.v4.app.Fragment;

public abstract class ManagerFragment extends Fragment {

    public abstract void refreshSearch(String string);

    public abstract void refreshMonuments();

    public abstract void refreshRanking();
}
