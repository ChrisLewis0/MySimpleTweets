package com.codepath.apps.restclienttemplate;

import android.graphics.Movie;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class TweetDetailActivity extends AppCompatActivity {

    // the tweet to display
    Tweet tweet;
    // view objects
    ImageView ivProfileImage;
    TextView tvUsername;
    TextView tvBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);
        // perform findViewById lookups
        ivProfileImage = findViewById(R.id.ivProfileImage);
        tvUsername = findViewById(R.id.tvUsername);
        tvBody = findViewById(R.id.tvBody);
        // unwrap the tweet passed in via intent, using its simple name as a key
        tweet = Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        // populate views according to data
        tvUsername.setText(tweet.user.name);
//      tvBody.setText(tweet.body);
//
//        Glide.with(this)
//                .load(tweet.user.profileImageUrl)
//                .into(ivProfileImage);
    }
}
