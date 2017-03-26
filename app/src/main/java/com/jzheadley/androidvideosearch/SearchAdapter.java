package com.jzheadley.androidvideosearch;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private final Uri videoUri;
    private List<Double> results;

    public SearchAdapter(List<Double> results, Uri videoUri) {
        this.results = results;
        this.videoUri = videoUri;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_row, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.searchResultTV.setText(results.get(position) + "");
        holder.searchCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent videoPlaybackIntent = new Intent(view.getContext(), VideoPlaybackActivity.class);
                videoPlaybackIntent.putExtra("videoUri", videoUri);
                videoPlaybackIntent.putExtra("videoTime", results.get(position));

                view.getContext().startActivity(videoPlaybackIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (results == null) {
            return 0;
        }
        return results.size();
    }

    public void updateList(List<Double> newResults) {
        this.results = newResults;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.search_result)
        TextView searchResultTV;
        @BindView(R.id.search_card)
        CardView searchCard;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
