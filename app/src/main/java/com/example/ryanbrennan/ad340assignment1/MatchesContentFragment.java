package com.example.ryanbrennan.ad340assignment1;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

import com.example.ryanbrennan.ad340assignment1.FirebaseMatchesViewModel;

import java.util.ArrayList;

public class MatchesContentFragment extends Fragment {
    private MatchesContentFragment.OnListFragmentInteractionListener mListener;
    private FirebaseMatchesViewModel viewModel;
    private ArrayList<Match> matches = new ArrayList<Match>();
    public static final String ARG_DATA_SET = "data-set";
    public RecyclerView view;

    public MatchesContentFragment() { }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);

        viewModel = new FirebaseMatchesViewModel();

        viewModel.getMatches(
                (ArrayList<Match> matches) -> {
                    updateMatches(matches);

                    ContentAdapter adapter = new ContentAdapter(view.getContext());
                    view.setAdapter(adapter);
                    view.setHasFixedSize(true);
                    view.setLayoutManager(new LinearLayoutManager(getActivity()));
                }

        );
        return view;
    }

    public void updateMatches(ArrayList<Match> matches) {
        this.matches = matches;

        ContentAdapter adapter = new ContentAdapter(view.getContext());
        view.setAdapter(adapter);
        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void updateMatches(ArrayList<Match> matches, Location userLocation){
        this.matches = matches;
        ContentAdapter adapter = new ContentAdapter(view.getContext(), userLocation);
        view.setAdapter(adapter);
        view.setHasFixedSize(true);
        view.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public final ImageView mImageView;
        public ImageButton favIcon;
        public Match mItem;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_matches, parent, false));
            mIdView = (TextView) itemView.findViewById(R.id.card_title);
            mContentView = (TextView) itemView.findViewById(R.id.card_text);
            mImageView = (ImageView) itemView.findViewById(R.id.card_image);
            favIcon = (ImageButton) itemView.findViewById(R.id.favorite_button);
            favIcon.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(mListener != null){
                        mItem.liked = !mItem.liked;
                        if(mItem.liked){
                            Toast.makeText(itemView.getContext(), "You liked " + mIdView.getText(),Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(itemView.getContext(), "You unliked " + mIdView.getText(),Toast.LENGTH_SHORT).show();
                        }
                        mListener.onListFragmentInteraction(mItem);
                    }
                }

            });

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }

    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        private  int LENGTH;

        public ContentAdapter(Context context) {
            ArrayList<Match> cards = new ArrayList<Match>();
            Location mLocation = new Location("");
            mLocation.setLatitude(47.61100);
            mLocation.setLongitude(-122.3365);
            for (Match m : matches) {
                Location match = new Location("");
                match.setLongitude(Double.parseDouble(m.longitude));
                match.setLatitude(Double.parseDouble(m.lat));
                float distance = mLocation.distanceTo(match);
                if (distance < 16093.4) {
                    cards.add(m);
                }

            }
            matches = cards;
            LENGTH = matches.size();
        }

        public ContentAdapter(Context context, Location mLocation) {
            ArrayList<Match> cards = new ArrayList<Match>();
            mLocation = new Location("");
            mLocation.setLatitude(47.61100);
            mLocation.setLongitude(-122.3365);
            for (Match m : matches) {
                Location match = new Location("");
                match.setLongitude(Double.parseDouble(m.longitude));
                match.setLatitude(Double.parseDouble(m.lat));
                float distance = mLocation.distanceTo(match);
                if (distance < 16093.4) {
                    cards.add(m);
                }

            }
            matches = cards;
            LENGTH = matches.size();


        }

        @NonNull
        @Override
        public MatchesContentFragment.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.mIdView.setText(matches.get(position).name);
            holder.mContentView.setText(matches.get(position).name);
            Picasso.get().load(matches.get(position).imageUrl).into(holder.mImageView);
            Boolean liked = matches.get(position).liked;
            holder.mItem = matches.get(position);
            if(liked){
                holder.favIcon.setColorFilter(Color.RED);
            }else{
                holder.favIcon.setColorFilter(Color.LTGRAY);
            }
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Match item);
    }
}
