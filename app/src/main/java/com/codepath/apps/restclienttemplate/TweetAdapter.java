package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import java.util.List;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder>{

    private List<Tweet> mTweets;
    // pass in the tweets array in the constructor
    public TweetAdapter(List<Tweet> tweets) {
        mTweets = tweets;
    }
    Context context;

    // for each row inflate the layout and cache references in ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // get context
        context = parent.getContext();
        // instantiate inflater
        LayoutInflater inflater = LayoutInflater.from(context);
        // inflate tweet
        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        // return view
        return viewHolder;
    }
    // bind the values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // get position of tweet in data
        Tweet tweet = mTweets.get(position);
        // populate views according to data
        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);

        Glide.with(context)
                .load(tweet.user.profileImageUrl)
                .apply(new RequestOptions()
                        .transforms(new CircleCrop()))
                .into(holder.ivProfileImage);
    }

    // size of data
    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    // create the ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // views
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;

        public ViewHolder(View itemView) {
            super(itemView);
            // perform findViewById lookups
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvUsername = itemView.findViewById(R.id.tvUserName);
            tvBody = itemView.findViewById(R.id.tvBody);
            // set up click listener
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            // gets item position
            int position = getAdapterPosition();
            // make sure the position exists
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position
                Tweet tweet = mTweets.get(position);
                // create intent to new activity
                Intent intent = new Intent(context, TweetDetailActivity.class);
                // serialize the tweet
                intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                // start new activity
                context.startActivity(intent);
            }
        }
    }

    // Clean all elements of the recycler
    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        mTweets.addAll(list);
        notifyDataSetChanged();
    }
}
