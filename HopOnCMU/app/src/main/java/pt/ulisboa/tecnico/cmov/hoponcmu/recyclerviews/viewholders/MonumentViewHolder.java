package pt.ulisboa.tecnico.cmov.hoponcmu.recyclerviews.viewholders;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import pt.ulisboa.tecnico.cmov.hoponcmu.R;
import pt.ulisboa.tecnico.cmov.hoponcmu.activities.ManagerActivity;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.CommunicationTask;
import pt.ulisboa.tecnico.cmov.hoponcmu.communication.command.DownloadQuizCommand;
import pt.ulisboa.tecnico.cmov.hoponcmu.data.objects.Monument;

public class MonumentViewHolder extends RecyclerView.ViewHolder {
    private TextView name;
    private TextView distance;
    private Monument monument;
    private ManagerActivity activity;

    public MonumentViewHolder(View itemView) {
        super(itemView);
        // Define click listener for the ViewHolder's View.
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadQuizCommand dqc = new DownloadQuizCommand(monument);
                new CommunicationTask(activity, dqc).execute();
                Log.d(MonumentViewHolder.class.getName(),
                        "Element " + getAdapterPosition() + " clicked.");
            }
        });
        name = itemView.findViewById(R.id.monument_name);
        distance = itemView.findViewById(R.id.monument_distance);
    }

    public void setMonument(ManagerActivity managerActivity, Monument monument) {
        this.activity = managerActivity;
        this.monument = monument;
        name.setText(monument.getName());
        double floatDistance = (double) monument.getCosDistance();
        if (floatDistance == 0) {
            distance.setVisibility(View.INVISIBLE);
        } else {
            distance.setVisibility(View.VISIBLE);
            floatDistance = 6371000 * Math.acos(floatDistance);
            if (floatDistance >= 1000) {
                distance.setText(String.format("%.1fKm", floatDistance / 1000));
            } else {
                distance.setText(String.format("%dm", (int) floatDistance));
            }
        }
    }
}
