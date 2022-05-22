package Activity;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.androidnetworking.AndroidNetworking;
//import com.androidnetworking.error.ANError;
//import com.androidnetworking.interfaces.JSONArrayRequestListener;
//import com.androidnetworking.interfaces.OkHttpResponseListener;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.OkHttpResponseListener;
import com.example.newsfeedaggregator.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.Calendar;

import okhttp3.Response;


public class CreateUser extends AppCompatActivity {
    private EditText editText_email;
    private EditText editText_password;
    private EditText editText_fname;
    private EditText editText_lname;
    private int userId;
    private Button login;
    private String USER_CREATE = "http://10.0.2.2:8080/user/create";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home_page_layout);
        setContentView(R.layout.create_user_layout);
        editText_email = findViewById(R.id.email);
        editText_password = findViewById(R.id.password);
        editText_fname = findViewById(R.id.fName);
        editText_lname = findViewById(R.id.lName);
        login = findViewById(R.id.login);
        //url = "https://localhost:8081";
    }
    public void createUser(View view){
        String userEmail = editText_email.getText().toString().trim();
        String password = editText_password.getText().toString().trim();
        String fname = editText_fname.getText().toString();
        String lname = editText_lname.getText().toString();
        if(userEmail.isEmpty())
        {
            editText_email.setError("Please provide username");
            editText_email.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            editText_password.setError("Please provide password");
            editText_password.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()){
            editText_email.setError("Please provide valid email");
            editText_email.requestFocus();
            return;
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("firstName", fname);
            jsonObject.put("lastName", lname);
            jsonObject.put("emailId", userEmail);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(USER_CREATE)
                .addJSONObjectBody(jsonObject) // posting java object
                .build()
                .getAsOkHttpResponse(new OkHttpResponseListener() {
                    @Override
                    public void onResponse(Response response) {
                        Intent intent = new Intent(CreateUser.this, SelectTopics.class);
                        intent.putExtra("User_ID", userId);
                        startActivity(intent);
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Toast.makeText(CreateUser.this,"Failed to create user! Email already in use!",Toast.LENGTH_LONG).show();
                    }
                });
    }
}