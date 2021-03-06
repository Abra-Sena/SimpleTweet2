package com.codepath.apps.restclienttemplate.models;

import android.widget.ImageView;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.codepath.apps.restclienttemplate.TimeFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId")) //to define table
public class Tweet {

    @PrimaryKey
    @ColumnInfo
    public long id;

    @ColumnInfo
    public String body;

    @ColumnInfo
    public String createdAt;

    @ColumnInfo
    public String formattedTime;

    @ColumnInfo
    public String fullTime;

    @ColumnInfo
    public long userId;

    @Ignore
    public User user;

    //needed for Parceler
    public Tweet() {};

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.id = jsonObject.getLong("id");
//        tweet.media = jsonObject.getString("media_ids");

        User user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.user = user;
        tweet.userId = user.id;
//        tweet.media = user.profileImageURl;
        return tweet;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for(int i =0; i < jsonArray.length(); i++) {
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }

    public String getFormattedTimestamp() {
        formattedTime = TimeFormatter.getTimeDifference(createdAt);
        return formattedTime;
    }

    public String getFullTimestamp() {
        fullTime = TimeFormatter.getTimeStamp(createdAt);
        return fullTime;
    }
}
