package pt.ua.tripfinder_android;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;

import androidx.recyclerview.widget.RecyclerView;


/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private String[] mDataSet;
    private String[] trips_descrps;

    // BEGIN_INCLUDE(recyclerViewSampleViewHolder)
    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final ImageView trip_image;
        private final TextView trip_descrp;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.

            textView = (TextView) v.findViewById(R.id.tripName);
            trip_descrp = (TextView) v.findViewById(R.id.trip_descrp);
            trip_image = (ImageView) v.findViewById(R.id.trip_image);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                    v.getContext().startActivity(new Intent(v.getContext(), TripInfo_Activity.class));

                }
            });
        }

        public TextView getTextView() {
            return textView;
        }

        public TextView getTrip_descrp() {
            return trip_descrp;
        }

        public ImageView getTrip_image() {
            return trip_image;
        }
    }
    // END_INCLUDE(recyclerViewSampleViewHolder)

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public CustomAdapter(String[] dataSet, String[] trips_descrps) {
        mDataSet = dataSet;
        this.trips_descrps = trips_descrps;
    }

    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.trips, viewGroup, false);

        return new ViewHolder(v);
    }
    // END_INCLUDE(recyclerViewOnCreateViewHolder)

    // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the contents of the view
        // with that element

        viewHolder.getTextView().setText(mDataSet[position]);
        viewHolder.getTrip_descrp().setText(trips_descrps[position]);
    }
    // END_INCLUDE(recyclerViewOnBindViewHolder)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.length;
    }

}