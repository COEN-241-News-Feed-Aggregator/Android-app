package Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;

import com.example.newsfeedaggregator.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Adapter.NewsRecyclerViewAdapter;
import Adapter.TopicRecyclerViewAdapter;

//public class HomePage extends AppCompatActivity {
//    private RecyclerView mRecyclerView1;
//    private RecyclerView.Adapter mAdapter1;
//    private RecyclerView newsRecyclerView;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.home_page_layout);
//    }
//    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
//    mRecyclerView1 = (RecyclerView) findViewById(R.id.topics);

public class HomePage extends AppCompatActivity {
    private int userId;
    private RecyclerView topicRecyclerView;
    private RecyclerView newsRecyclerView;
    private List<String> topics = new ArrayList<>();
    private String selectedTopic = "All";
    private List<NewsArticle> timeline = new ArrayList<>();
//    private String TOPIC_URL = "http://10.0.2.2:8080/post/create";
//    private String TIMELINE_URL = "http://10.0.2.2:8080/timeline/get/{userId}";
//    private String USER_URL = "http://10.0.2.2:8080/user/get/all?userId=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_layout);
        userId = getIntent().getIntExtra("User_ID", -1);
        topics = (List<String>) getIntent().getSerializableExtra("topics");
        timeline = (List<NewsArticle>) getIntent().getSerializableExtra("timeline");
        if(topics.size() < 1){
            topics = getTopics(userId);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        topicRecyclerView = findViewById(R.id.topics);
        topicRecyclerView.setLayoutManager(layoutManager);
        topicRecyclerView.setAdapter(
                new TopicRecyclerViewAdapter(getApplicationContext(), topics, userId));
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("get topic"));

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        newsRecyclerView = findViewById(R.id.newsCard);
        newsRecyclerView.setLayoutManager(layoutManager2);
        newsRecyclerView.setAdapter(
                new NewsRecyclerViewAdapter(getApplicationContext(), timeline, userId));
    }

    //local broadcast to get the selected topic from the recycler view
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            selectedTopic = intent.getStringExtra("topic");
        }
    };

    // get topics for the user from database
    public List<String> getTopics(int userId){
        List<String> list = new ArrayList<>();
        return list;
    }



    //get the timeline for the current user when topic is changed
    private void getTimeline(int userId, String selectedTopic) {
        Map<String, NewsArticle> timelineMap = new HashMap();
        Uri uri = Uri.parse("https://www.seekpng.com/png/detail/209-2096780_weekly-events-update-june-clipart-library-breaking-news.png");
        timelineMap.put("All", new NewsArticle(userId,"Article no 1", uri,"https://www.cnn.com/2022/05/17/politics/pennsylvania-north-carolina-primary-election/index.html"));
        timelineMap.put("Tech", new NewsArticle(userId,"Article no 2", uri,"https://apnews.com/article/russia-ukraine-zelenskyy-kyiv-finland-6203a57a7ec5fe2c49c9ccb83f2ca7e0"));
        timelineMap.put("Entertainment", new NewsArticle(userId,"Article no 3", uri,"https://www.democratandchronicle.com/story/news/2022/05/17/president-biden-buffalo-shooting-scene-visit-missed-some-residents/9807343002/"));
        timelineMap.put("Science", new NewsArticle(userId,"Article no 4", uri,"https://www.npr.org/2022/05/17/1099463223/north-carolina-senate-results"));
        Intent intent = new Intent(HomePage.this, HomePage.class);
        intent.putExtra("User_ID", userId);
        intent.putExtra("topics", (Serializable) topics);
        intent.putExtra("timeline", (Serializable) timelineMap.get(selectedTopic));
        startActivity(intent);

//        AndroidNetworking.get(TIMELINE_URL)
//                .addPathParameter("userId", String.valueOf(userId))
//                .build().getAsObject(Activity.Timeline.class, new ParsedRequestListener<Activity.Timeline>() {
//            @Override
//            public void onResponse(Activity.Timeline timeline) {
//                Intent intent = new Intent(HomePage.this, HomePage.class);
//                intent.putExtra("User_ID", userId);
//                intent.putExtra("posts", (Serializable) timeline.postsList);
//                startActivity(intent);
//            }
//
//            @Override
//            public void onError(ANError anError) {
//                Intent intent = new Intent(HomePage.this, HomePage.class);
//                intent.putExtra("User_ID", userId);
//                intent.putExtra("posts", (Serializable) new ArrayList<>());
//                startActivity(intent);
//            }
//        });
    }
}