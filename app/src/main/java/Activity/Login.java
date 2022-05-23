package Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.example.newsfeedaggregator.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Models.NewsArticle;
import Models.Topic;


public class Login extends AppCompatActivity implements Serializable {
    private EditText editText_userEmail;
    private EditText editText_password;
    private Button login;
    private String LOGIN_URL;
    private String TIMELINE_URL;
    private String USERTOPIC_URL;
    private int userId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidNetworking.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home_page_layout);
        setContentView(R.layout.login_layout);
        editText_userEmail = findViewById(R.id.userEmail);
        editText_password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        LOGIN_URL = "http://10.0.2.2:8080/user/login";
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
        //get userId from userService by passing a json object
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("emailId", userEmail);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(LOGIN_URL)
                .addJSONObjectBody(jsonObject) // posting java object
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        userId = Integer.parseInt(response);
                        getTimeline(userId);
                    }
                    @Override
                    public void onError(ANError error) {
                        Toast.makeText(Login.this, "Failed to login! Please check your credentials!", Toast.LENGTH_LONG).show();
                    }
                });
        
    }

    public void createUser(View view) {
        Intent intent = new Intent(Login.this, CreateUser.class);
        startActivity(intent);
    }


    private void getTimeline(int userId) {
        List<Topic> topics = getTopics(userId);
        //List<Topic> topics = new ArrayList<>();
        AndroidNetworking.get(TIMELINE_URL)
                .addPathParameter("userId", String.valueOf(userId))
                .build().getAsObjectList(NewsArticle.class, new ParsedRequestListener<List<NewsArticle>>() {
            @Override
            public void onResponse(List<NewsArticle> timeline) {
                Intent intent = new Intent(Login.this, HomePage.class);
                intent.putExtra("User_ID", userId);
                intent.putExtra("topics", (Serializable) topics);
                intent.putExtra("timeline", (Serializable) timeline);
                startActivity(intent);
            }

            @Override
            public void onError(ANError anError) {
                Toast.makeText(Login.this, "Failed to login! Please try again", Toast.LENGTH_LONG).show();
            }
        });
    }

    // get topics for the user from database
    public List<Topic> getTopics(int userId) {
        final List<Topic>[] topics = new List[]{new ArrayList<>()};
        AndroidNetworking.get(USERTOPIC_URL)
                .addPathParameter("userId", String.valueOf(userId))
                .build().getAsObjectList(Topic.class, new ParsedRequestListener<List<Topic>>() {
            @Override
            public void onResponse(List<Topic> timeline) {
                topics[0] = timeline;
            }

            @Override
            public void onError(ANError anError) {
                Toast.makeText(Login.this, "Failed to login! Please try again", Toast.LENGTH_LONG).show();
            }
        });
        return topics[0];
    }
}
