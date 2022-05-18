package Activity;

import android.net.Uri;

import java.net.URI;

public class NewsArticle {
    public int userId;
    public String content;
    public Uri image_url;
    public String article_url;
    public NewsArticle(){};

    public NewsArticle(int userId, String content, Uri image_url, String article_url){
        this.userId = userId;
        this.content = content;
        this.image_url = image_url;
        this.article_url = article_url;
    }
}
