package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import java.io.InputStream;

public class TweetDetailsActivity extends AppCompatActivity {

    ImageView ivProfileImage;
    TextView tvHandler;
    TextView tvUserName;
    TextView tvBody;
    TextView tvTimestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);

        ivProfileImage = findViewById(R.id.ivProfileImage);
        tvHandler = findViewById(R.id.tvHandler);
        tvUserName = findViewById(R.id.tvUserName);
        tvBody = findViewById(R.id.tvBody);
        tvTimestamp = findViewById(R.id.tvTimestamp);

        //retrieve data
        Tweet tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));

        tvUserName.setText(tweet.user.name);
        tvHandler.setText("@" + tweet.user.screenName);
        tvBody.setText(tweet.body);
        tvTimestamp.setText(tweet.getFullTimestamp());

        //improve this later using glade (fix issues having whe using glade here)
        new DownloadImageTask(ivProfileImage).execute(tweet.user.profileImageURl);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urlDisplay = urls[0];
            Bitmap myProfile = null;
            try {
                InputStream in = new java.net.URL(urlDisplay).openStream();
                myProfile = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return myProfile;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}