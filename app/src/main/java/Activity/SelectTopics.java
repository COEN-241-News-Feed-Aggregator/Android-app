package Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.newsfeedaggregator.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Models.NewsArticle;
import Models.Topic;

public class SelectTopics extends AppCompatActivity implements View.OnClickListener {
    Button next;
    ListView listView;
    int userId;
    List<Topic> userTopics = new ArrayList<>();
    ArrayAdapter<String> adapter;
    List<String> selectedTopics;
    Map<String,Integer> topicMap = new HashMap<>();
    private String TOPIC_URL = "http://10.0.2.2:8080/post/create";
    private String TIMELINE_URL = "http://13.52.211.197:8080/timeline/get/{userId}";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_topics_layout);
        setMap();
        
        userId = getIntent().getIntExtra("User_ID", -1);
        userTopics = (List<Topic>) getIntent().getSerializableExtra("topics");
        listView = (ListView) findViewById(R.id.list);
        next = (Button) findViewById(R.id.nextButton);
        String[] topics = getResources().getStringArray(R.array.topics_array);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, topics);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);
        next.setOnClickListener(this);
        if(userTopics.size() >0) {
            for(Topic userT:userTopics){
                int position = Arrays.asList(topics).indexOf(userT.topicName);
                listView.setItemChecked(position, true);
            }
        }
    }
    public void setMap(){
        topicMap.put("Technology",1);
        topicMap.put("Science",2);
        topicMap.put("Politics",3);
        topicMap.put("Sports",4);
        topicMap.put("Entertainment",5);
        topicMap.put("Finance",6);
    }
    
    @Override
    public void onClick(View v) {
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        List<String> selectedItems = new ArrayList<String>();
        for (int i = 0; i < checked.size(); i++) {
            // Item position in adapter
            int position = checked.keyAt(i);
            // Add topic if it is checked i.e.) == TRUE!
            if (checked.valueAt(i))
                selectedItems.add(adapter.getItem(position));
        }
        String[] outputStrArr = new String[selectedItems.size()];

        for (int i = 0; i < selectedItems.size(); i++) {
            outputStrArr[i] = selectedItems.get(i);
        }
        selectedTopics = Arrays.asList(outputStrArr);
        followTopics(userId,selectedTopics);
        getTimeline(userId);
    }

    private void followTopics(int userId, List<String> topics) {
        for(String topic: topics){
            int id = topicMap.get(topic);
            userTopics.add(new Topic(id,topic));
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("userId", userId);
            jsonObject.put("userTopics", userTopics);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(TOPIC_URL)
                .addJSONObjectBody(jsonObject) // posting java object
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        
                    }
                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(SelectTopics.this, "Failed to save your preference! Please try again!", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void getTimeline(int userId) {
        AndroidNetworking.get(TIMELINE_URL)
                .addPathParameter("userId", String.valueOf(userId))
                .build().getAsObjectList(NewsArticle.class, new ParsedRequestListener<List<NewsArticle>>(){
            @Override
            public void onResponse(List<NewsArticle> timeline) {
                Intent intent = new Intent(SelectTopics.this, HomePage.class);
                intent.putExtra("User_ID", userId);
                intent.putExtra("topics", (Serializable) userTopics);
                intent.putExtra("timeline", (Serializable) timeline);
                startActivity(intent);
            }
            @Override
            public void onError(ANError anError) {
                Toast.makeText(SelectTopics.this,"Failed to load your timeline",Toast.LENGTH_LONG).show();
            }
        });
    }
}