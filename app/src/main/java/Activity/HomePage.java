package Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import Models.NewsArticle;
import Models.Topic;

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
    private RecyclerView newsRecyclerView;
    private List<Topic> topics = new ArrayList<>();
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
        topics = (List<Topic>) getIntent().getSerializableExtra("topics");
        timeline = (List<NewsArticle>) getIntent().getSerializableExtra("timeline");
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.showOverflowMenu();

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
                Intent intent = new Intent(HomePage.this, SelectTopics.class);
                intent.putExtra("User_ID", userId);
                intent.putExtra("topics", (Serializable) topics);
                startActivity(intent);
                return true;
        }
        return false;
    }
}