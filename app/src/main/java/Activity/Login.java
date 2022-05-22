package Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.newsfeedaggregator.R;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Login extends AppCompatActivity implements Serializable {
    private EditText editText_userEmail;
    private EditText editText_password;
    private Button login;
    private String LOGIN_URL;
    private String TIMELINE_URL;
    private int userId = 21;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidNetworking.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home_page_layout);
        setContentView(R.layout.login_layout);
        editText_userEmail = findViewById(R.id.userEmail);
        editText_password = findViewById(R.id.password);
        login = findViewById(R.id.login);
//        LOGIN_URL = "http://10.0.2.2:8080/user/login";
        TIMELINE_URL = "http://13.52.211.197:8080/timeline/get/{userId}";
    }

    public void login(View view) {
        String userEmail = editText_userEmail.getText().toString().trim();
        String password = editText_password.getText().toString().trim();
        if (userEmail.isEmpty()) {
            editText_userEmail.setError("Please provide username");
            editText_userEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            editText_password.setError("Please provide password");
            editText_password.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            editText_userEmail.setError("Please provide valid email");
            editText_userEmail.requestFocus();
            return;
        }

        getTimeline(userId);
//        JSONObject jsonObject = new JSONObject();
//        try {
//            jsonObject.put("userName", userEmail);
//            jsonObject.put("password", password);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        AndroidNetworking.post(LOGIN_URL)
//                .addJSONObjectBody(jsonObject) // posting java object
//                .build()
//                .getAsString(new StringRequestListener() {
//                    @Override
//                    public void onResponse(String response) {
//                        userId = Integer.parseInt(response);
//                        getTimeline(userId);
//                    }
//                    @Override
//                    public void onError(ANError error) {
//                        Toast.makeText(Login.this, "Failed to login! Please check your credentials!", Toast.LENGTH_LONG).show();
//                    }
//                });

    }

    public void createUser(View view) {
        Intent intent = new Intent(Login.this, CreateUser.class);
        startActivity(intent);
    }

    private void getTimeline(int userId) {
        List<String> topics = new ArrayList<>();
        List<NewsArticle> timeline = new ArrayList<>();

               String image_url = "https://static01.nyt.com/images/2022/05/22/multimedia/22wordplay-splat/22wordplay-splat-articleLarge.jpg";
        topics.add("All");
        topics.add("Tech");
        topics.add("Entertainment");
        topics.add("Science");
        timeline.add( new NewsArticle(1,"Article no 10","author 1", "01/02/2018", "content for the news", image_url,"https://www.cnn.com/2022/05/17/politics/pennsylvania-north-carolina-primary-election/index.html",new ArrayList<String>(Arrays.asList("science,technology"))));
        timeline.add( new NewsArticle(2,"Article no 20","author 2", "01/02/2018", "content for the news", image_url,"https://apnews.com/article/russia-ukraine-zelenskyy-kyiv-finland-6203a57a7ec5fe2c49c9ccb83f2ca7e0",new ArrayList<String>(Arrays.asList("movie"))));
        timeline.add( new NewsArticle(3,"Article no 30","author 3", "01/02/2018", "content for the news", image_url,"https://www.democratandchronicle.com/story/news/2022/05/17/president-biden-buffalo-shooting-scene-visit-missed-some-residents/9807343002/",new ArrayList<String>(Arrays.asList("entertainment"))));
        timeline.add( new NewsArticle(4,"Article no 40","author 4", "01/02/2018", "content for the news", image_url,"https://www.npr.org/2022/05/17/1099463223/north-carolina-senate-results",new ArrayList<String>(Arrays.asList("education"))));
//                AndroidNetworking.get(TIMELINE_URL)
//                .addPathParameter("userId", String.valueOf(userId))
//                .build().getAsJSONArray(new JSONArrayRequestListener() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        if (response != null) {
//                            for (int i=0;i<response.length();i++){
//                                try {
//                                    timeline.add((NewsArticle) response.get(i));
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onError(ANError anError) {
//                        System.out.println(anError);
//                    }
//                } {
//                    @Override
//                    public void onResponse(List<NewsArticle> timeline) {
//                        Intent intent = new Intent(Login.this, HomePage.class);
//                        intent.putExtra("User_ID", userId);
//                        intent.putExtra("topics", (Serializable) topics);
//                        intent.putExtra("timeline", (Serializable) timeline);
//                        startActivity(intent);
//                    }
//
//                    @Override
//                    public void onError(ANError anError) {
//
//                    }
            //    });

//        AndroidNetworking.get(TIMELINE_URL)
//                .addPathParameter("userId", String.valueOf(userId))
//                .build().getAsObject(Activity.Timeline.class, new ParsedRequestListener<Activity.Timeline>() {
//            @Override
//            public void onResponse(Activity.Timeline timeline) {
//                Intent intent = new Intent(Login.this, HomePage.class);
//                intent.putExtra("User_ID", userId);
//                intent.putExtra("posts", (Serializable) timeline.postsList);
//                startActivity(intent);
//            }
//            @Override
//            public void onError(ANError anError) {
//                Intent intent = new Intent(Login.this, HomePage.class);
//                intent.putExtra("User_ID", userId);
//                intent.putExtra("posts", (Serializable) new ArrayList<>());
//                startActivity(intent);
//            }
//        });

    }
}
