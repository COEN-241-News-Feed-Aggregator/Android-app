package Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.newsfeedaggregator.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class TopicRecyclerViewAdapter extends RecyclerView.Adapter<TopicRecyclerViewAdapter.ViewHolder>{
    Context mContext;
    private int userId;
    private List<String> topics;
    public TopicRecyclerViewAdapter(Context context, List<String> topics, int userId) {
        this.mContext = context;
        this.topics = topics;
        this.userId = userId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.topic_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String topic = topics.get(position);
        holder.topicSelect.setText(topic);
        holder.topicSelect.setOnClickListener(arg0 -> {
// TODO Auto-generated method stub
            holder.topicSelect.setBackgroundColor(Color.GRAY);
            onclickTopic(topic);
        });
    }

    // we need to send data back to HomePage, to get news articles of the selected topic
    public void onclickTopic(String topic){
        Intent intent = new Intent("get topic");
        intent.putExtra("topic",topic);
        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
//        if(comment.length()!=0){
//            JSONObject jsonObject = new JSONObject();
//            try {
//                jsonObject.put("userId", userId);
//                jsonObject.put("postId", postId);
//                jsonObject.put("comment", comment);
//                jsonObject.put("postUserId", postUserId);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            AndroidNetworking.post(COMMENT_URL)
//                    .addJSONObjectBody(jsonObject) // posting java object
//                    .build()
//                    .getAsOkHttpResponse(new OkHttpResponseListener() {
//                        @Override
//                        public void onResponse(Response response) {
//                            Toast.makeText(mContext, "Successfully posted the comment", Toast.LENGTH_LONG).show();
//                        }
//
//                        @Override
//                        public void onError(ANError anError) {
//                            Toast.makeText(mContext, "Failed, please try again!", Toast.LENGTH_LONG).show();
//                        }
//                    });
//        }
    }


    @Override
    public int getItemCount() {
        return topics.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Button topicSelect;
        public ViewHolder(View view) {
            super(view);
            topicSelect = view.findViewById(R.id.button);
        }
    }
}
