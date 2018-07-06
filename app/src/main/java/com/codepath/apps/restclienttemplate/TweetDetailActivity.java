package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class TweetDetailActivity extends AppCompatActivity {


    TwitterClient client;

    Tweet tweet;

    ImageView ivProfileImage;
    TextView tvUsername;
    TextView tvScreenName;
    TextView tvBody;
    ImageButton ibRetweet;
    ImageButton ibFav;

    boolean isFaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_detail);

        tweet = Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));

        client = TwitterApp.getRestClient(this);

        ivProfileImage = findViewById(R.id.ivProfileImage);
        tvUsername = findViewById(R.id.tvUsername);
        tvScreenName = findViewById(R.id.tvScreenName);
        tvBody = findViewById(R.id.tvBody);
        ibRetweet = findViewById(R.id.ibRetweet);
        ibFav = findViewById(R.id.ibFav);

        tvUsername.setText(tweet.user.name);
        tvScreenName.setText("@" + tweet.user.screenName);
        tvBody.setText(tweet.body);
        Glide.with(this)
                .load(tweet.user.profileImageUrl)
                .apply(new RequestOptions()
                        .transforms(new CircleCrop()))
                .into(ivProfileImage);

        ibRetweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.reTweet(tweet.uid, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        // return to timeline
                        Intent intent = new Intent(getApplicationContext(), TimelineActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });

        isFaved = tweet.isFaved;
        if (isFaved) {
            ibFav.setImageResource(R.drawable.ic_vector_heart);
        }
        else {
            ibFav.setImageResource(R.drawable.ic_vector_heart_stroke);
        }
        ibFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFaved) {
                    client.unFavorite(tweet.uid, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            ibFav.setImageResource(R.drawable.ic_vector_heart_stroke);
                            isFaved =! isFaved;
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Log.d("TwitterClient", errorResponse.toString());
                            throwable.printStackTrace();
                        }
                    });
                }
                else {
                    client.favorite(tweet.uid, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            ibFav.setImageResource(R.drawable.ic_vector_heart);
                            isFaved =! isFaved;
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            Log.d("TwitterClient", errorResponse.toString());
                            throwable.printStackTrace();                        }
                    });
                }
            }
        });
    }
}
