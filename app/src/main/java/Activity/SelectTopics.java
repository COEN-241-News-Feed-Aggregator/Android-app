package Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.newsfeedaggregator.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectTopics extends AppCompatActivity implements View.OnClickListener {
    Button next;
    ListView listView;
    int userId;
    List<String> userTopics = new ArrayList<>();
    ArrayAdapter<String> adapter;
    List<String> selectedTopics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_topics_layout);
        userId = getIntent().getIntExtra("User_ID", -1);
        userTopics = (List<String>) getIntent().getSerializableExtra("topics");
        listView = (ListView) findViewById(R.id.list);
        next = (Button) findViewById(R.id.nextButton);
        String[] topics = getResources().getStringArray(R.array.topics_array);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, topics);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);
        next.setOnClickListener(this);
        for(String userT:userTopics){
            int position = Arrays.asList(topics).indexOf(userT);
            listView.setItemChecked(position, true);
        }
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
        getTimeline(userId,selectedTopics);
    }

    private void getTimeline(int userId, List<String> topics) {
        List<NewsArticle> timeline = new ArrayList<>();
        String image_url = "https://www.seekpng.com/png/detail/209-2096780_weekly-events-update-june-clipart-library-breaking-news.png";
        timeline.add( new NewsArticle(1,"Article no 10","author 1", "01/02/2018", "content for the news", image_url,"https://www.cnn.com/2022/05/17/politics/pennsylvania-north-carolina-primary-election/index.html",new ArrayList<String>(Arrays.asList("science,technology"))));
        timeline.add( new NewsArticle(2,"Article no 20","author 2", "01/02/2018", "content for the news", image_url,"https://apnews.com/article/russia-ukraine-zelenskyy-kyiv-finland-6203a57a7ec5fe2c49c9ccb83f2ca7e0",new ArrayList<String>(Arrays.asList("movie"))));
        timeline.add( new NewsArticle(3,"Article no 30","author 3", "01/02/2018", "content for the news", image_url,"https://www.democratandchronicle.com/story/news/2022/05/17/president-biden-buffalo-shooting-scene-visit-missed-some-residents/9807343002/",new ArrayList<String>(Arrays.asList("entertainment"))));
        timeline.add( new NewsArticle(4,"Article no 40","author 4", "01/02/2018", "content for the news", image_url,"https://www.npr.org/2022/05/17/1099463223/north-carolina-senate-results",new ArrayList<String>(Arrays.asList("education"))));
        Intent intent = new Intent(SelectTopics.this, HomePage.class);
        intent.putExtra("User_ID", userId);
        intent.putExtra("topics", (Serializable) topics);
        intent.putExtra("timeline", (Serializable) timeline);
        startActivity(intent);
    }
}