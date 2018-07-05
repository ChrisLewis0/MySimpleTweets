package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    // constants
    TwitterClient client;
    EditText etTweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        // instantiate client
        client = TwitterApp.getRestClient(this);
        // find tweet text
        etTweet = findViewById(R.id.etTweet);
        // connect to submit button
        final Button button = findViewById(R.id.bSubmit);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                client.sendTweet(etTweet.getText().toString(), new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        // creates Tweet
                        try {
                            // prepare data intent
                            Intent data = new Intent();
                            // pass tweet back to parent activity
                            Tweet tweet = Tweet.fromJSON(response);
                            data.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));
                            // activity finished, return data
                            setResult(RESULT_OK, data);
                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.d("Test", errorResponse.toString());
                        throwable.printStackTrace();
                    }
                });
            }
        });
    }


}
