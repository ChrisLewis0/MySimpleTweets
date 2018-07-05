package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    TextView tvWordCount;
    int MAX_WORD_COUNT = 140;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        // instantiate client
        client = TwitterApp.getRestClient(this);
        // find word count text
        tvWordCount = findViewById(R.id.tvWordCount);
        // find tweet text
        etTweet = findViewById(R.id.etTweet);
        // set tweet max character count
        etTweet.setFilters(new InputFilter[] { new InputFilter.LengthFilter(MAX_WORD_COUNT) });
        // connect to submit button
        etTweet.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String currentText = editable.toString();
                int currentLength = currentText.length();
                tvWordCount.setText(currentLength + " of 140 characters");
            }
        });
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
