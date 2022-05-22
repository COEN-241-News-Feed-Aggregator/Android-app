package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsfeedaggregator.R;

import java.util.List;

import Models.NewsArticle;
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
        String image_url = news.imageUrl;
        if(image_url.length() < 1){
            image_url = mContext.getString(R.string.image_url);
        }
        String url = news.webUrl;
        holder.topic.setText(String.join(",",news.topics));
        holder.title.setText(news.title);
        holder.date.setText(news.publishedDate);
        holder.author.setText(news.author);
        holder.content_text.setText(news.content);
        Glide.with(mContext).load(image_url).into(holder.displayImage);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, WebViewActivity.class);
            intent.putExtra("URL", url);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return timeline.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView topic;
        public TextView date;
        public TextView title;
        public TextView author;
        public ImageView displayImage;
        public TextView content_text;
        public ViewHolder(View view) {
            super(view);
            topic = view.findViewById(R.id.topic);
            date = view.findViewById(R.id.date);
            title = view.findViewById(R.id.title);
            author = view.findViewById(R.id.author);
            displayImage = view.findViewById(R.id.display_image);
            content_text = view.findViewById(R.id.content_text);
        }
    }
}