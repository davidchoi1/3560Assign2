package main.java.models.UserModels;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafxgui.UserControl;
import main.java.exceptions.NoUserException;
import main.java.models.interfaces.Observer;
import main.java.models.interfaces.UserEntity;

public class User implements UserEntity, Observer {
    private String userID;
    private List<String> followers;
    private List<String> following;
    private Map<String, List<String>> newsFeed;
    private UserControl userControl;
    private static String tweetTime;

    public User(String userID) {
        this.userID = userID;
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
        this.newsFeed = new HashMap<>();
    }

    public void updateTweetTime() {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        tweetTime = currentTime.format(formatter);
    }

    public String getTweetTime() {
        return tweetTime;
    }

    public List<String> getFollowers() {
        return followers;
    }

    public void setFollowers(String userID) {
        followers.add(userID);
    }

    public List<String> getFollowing() {
        return following;
    }

    public void setFollowing(String userID) {
        following.add(userID);
    }

    public Map<String, List<String>> getNewsFeed() {
        return newsFeed;
    }

    @Override
    public void updateNewsFeed(String userID, String tweet) throws NoUserException {

        // update user's newsFeed with tweets of those user follows (and self)
        if (following.contains(userID) || this.userID.equals(userID)) {
            List<String> tweetList = newsFeed.get(userID);
            if (tweetList == null) { // initialize tweetlist if empty
                tweetList = new ArrayList<>();
                newsFeed.put(userID, tweetList);
            }
            tweetList.add(tweet);
            this.updateTweetTime();
            userControl.updateLastUpdate("Last Updated: " + getTweetTime());
            userControl.updateNewsFeedUI();

            // if (this.userID.equals(userID) && userControl != null) {
            //     userControl.updateNewsFeedUI();
            //     userControl.updateLastUpdate("Last Updated: " + getTweetTime());
            // }
        } else {
            throw new NoUserException("Attempted to update newsFeed with non-following user ID " + userID);
        }
    }

    public void setUserControl(UserControl userControl) {
        this.userControl = userControl;
    }

    @Override
    public String getID() {
        return userID;
    }

    // override toString() method for treeView to return id string
    @Override
    public String toString() {
        return userID;
    }

}
