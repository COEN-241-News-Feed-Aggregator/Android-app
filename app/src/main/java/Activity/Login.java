package Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.newsfeedaggregator.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Login extends AppCompatActivity {
    private EditText editText_userEmail;
    private EditText editText_password;
    private Button login;
    private String LOGIN_URL;
    private String TIMELINE_URL;
    private int userId;
    public NewsArticle newsArticle = new NewsArticle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //AndroidNetworking.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        editText_userEmail = findViewById(R.id.userEmail);
        editText_password = findViewById(R.id.password);
        login = findViewById(R.id.login);
//        LOGIN_URL = "http://10.0.2.2:8080/user/login";
//        TIMELINE_URL = "http://10.0.2.2:8080/timeline/get/{userId}";
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
        topics.add("All");
        topics.add("Tech");
        topics.add("Entertainment");
        topics.add("Science");
        Intent intent = new Intent(Login.this, HomePage.class);
        intent.putExtra("User_ID", userId);
        intent.putExtra("topics", (Serializable) topics);
        intent.putExtra("timeline", (Serializable) timeline);
        startActivity(intent);

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
