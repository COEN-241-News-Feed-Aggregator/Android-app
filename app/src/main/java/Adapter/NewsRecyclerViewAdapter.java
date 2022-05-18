package Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.newsfeedaggregator.R;

import java.util.List;

import Activity.NewsArticle;
import Activity.WebViewActivity;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder>{
    Context mContext;
    private int userId;
    private List<NewsArticle> timeline;
    public NewsRecyclerViewAdapter(Context context, List<NewsArticle> timeline, int userId) {
        this.mContext = context;
        this.timeline = timeline;
        this.userId = userId;
    }

    @Override
    public NewsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.news_cardview, parent, false);
        return new NewsRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NewsRecyclerViewAdapter.ViewHolder holder, int position) {
        NewsArticle news = timeline.get(position);
        Uri uri = news.image_url;
        String url = news.article_url;
        holder.topic.setText("");
        holder.displayImage.setImageURI(uri);
        holder.content_text.setText(news.content);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, WebViewActivity.class);
            intent.putExtra("URL", url);
            mContext.startActivity(intent);
        });
    }
    
    public void onclickTopic(String comment,int postId, int postUserId){

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
        return timeline.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView topic;
        public ImageView displayImage;
        public TextView content_text;
        public ViewHolder(View view) {
            super(view);
            topic = view.findViewById(R.id.topic);
            displayImage = view.findViewById(R.id.display_image);
            content_text = view.findViewById(R.id.content_text);

        }
    }
}