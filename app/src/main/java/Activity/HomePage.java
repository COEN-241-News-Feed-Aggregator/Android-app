package Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.newsfeedaggregator.R;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import Adapter.NewsRecyclerViewAdapter;

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

public class HomePage extends AppCompatActivity implements Serializable {
    private int userId;
    private RecyclerView topicRecyclerView;
    private RecyclerView newsRecyclerView;
    private List<String> topics = new ArrayList<>();
    private String selectedTopic = "All";
    private List<NewsArticle> timeline = new ArrayList<>();
    public TextView date;
//    private String TOPIC_URL = "http://10.0.2.2:8080/post/create";
//    private String TIMELINE_URL = "http://10.0.2.2:8080/timeline/get/{userId}";
//    private String USER_URL = "http://10.0.2.2:8080/user/get/all?userId=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_layout);
        date = findViewById(R.id.date);
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        date.setText(currentDate);
        userId = getIntent().getIntExtra("User_ID", -1);
        topics = (List<String>) getIntent().getSerializableExtra("topics");
        timeline = (List<NewsArticle>) getIntent().getSerializableExtra("timeline");
        if(topics.size() < 1){
            topics = getTopics(userId);
        }
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.showOverflowMenu();

//        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
//        topicRecyclerView = findViewById(R.id.topics);
//        topicRecyclerView.setLayoutManager(layoutManager);
//        topicRecyclerView.setAdapter(
//                new TopicRecyclerViewAdapter(getApplicationContext(), topics, userId));
//        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
//                new IntentFilter("get topic"));

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        newsRecyclerView = findViewById(R.id.newsCard);
        newsRecyclerView.setLayoutManager(layoutManager2);
        newsRecyclerView.setAdapter(
                new NewsRecyclerViewAdapter(getApplicationContext(), timeline, userId));
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.app_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_topic:
                List<String> userTopics = getTopics(userId);
                Intent intent = new Intent(HomePage.this, SelectTopics.class);
                intent.putExtra("User_ID", userId);
                intent.putExtra("topics", (Serializable) userTopics);
                startActivity(intent);
                return true;
        }
        return false;
    }


    //local broadcast to get the selected topic from the recycler view
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            selectedTopic = intent.getStringExtra("topic");
            getTimeline(userId,selectedTopic);
        }
    };

    // get topics for the user from database
    public List<String> getTopics(int userId){
        List<String> list = new ArrayList<>();
        list.add("Technology");
        list.add("Science");
        return list;
    }



    //get the timeline for the current user when topic is changed
    private void getTimeline(int userId, String selectedTopic) {
        Map<String, NewsArticle> timelineMap = new HashMap();
        List<NewsArticle> timeline2 = new ArrayList<>();
        String image_url = "https://static01.nyt.com/images/2022/05/22/multimedia/22wordplay-splat/22wordplay-splat-articleLarge.jpg";
        timeline2.add( new NewsArticle(1,"Article no 10","author 1", "01/02/2018", "content for the news", image_url,"https://www.cnn.com/2022/05/17/politics/pennsylvania-north-carolina-primary-election/index.html",new ArrayList<String>(Arrays.asList("science,technology"))));
        timeline2.add( new NewsArticle(2,"Article no 20","author 2", "01/02/2018", "content for the news", image_url,"https://apnews.com/article/russia-ukraine-zelenskyy-kyiv-finland-6203a57a7ec5fe2c49c9ccb83f2ca7e0",new ArrayList<String>(Arrays.asList("movie"))));
        timeline2.add( new NewsArticle(3,"Article no 30","author 3", "01/02/2018", "content for the news", image_url,"https://www.democratandchronicle.com/story/news/2022/05/17/president-biden-buffalo-shooting-scene-visit-missed-some-residents/9807343002/",new ArrayList<String>(Arrays.asList("entertainment"))));
        timeline2.add( new NewsArticle(4,"Article no 40","author 4", "01/02/2018", "content for the news", image_url,"https://www.npr.org/2022/05/17/1099463223/north-carolina-senate-results",new ArrayList<String>(Arrays.asList("education"))));
        Intent intent = new Intent(HomePage.this, HomePage.class);
        intent.putExtra("User_ID", userId);
        intent.putExtra("topics", (Serializable) topics);
        intent.putExtra("timeline", (Serializable) timeline2);
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