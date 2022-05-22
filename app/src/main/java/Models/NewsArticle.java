package Models;

import java.io.Serializable;
import java.util.List;


public class NewsArticle implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    int id;
    public String title;
    public String author;
    public String publishedDate;
    public String content;
    public String webUrl;
    public String imageUrl;
    public List<String> topics;

    public NewsArticle(int id, String title, String author, String publishedDate, String content, String webUrl, String imageUrl, List<String> topics) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publishedDate = publishedDate;
        this.content = content;
        this.webUrl = webUrl;
        this.imageUrl = imageUrl;
        this.topics = topics;
    }

    public NewsArticle updateTopicList(String topic) {
        this.topics.add(topic);
        return this;
    }


}
