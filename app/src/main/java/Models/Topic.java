package Models;

import java.io.Serializable;

public class Topic implements Serializable {
    public int topicId;
    public String topicName;

    public Topic() {

    }

    public Topic(int id, String topic) {
        this.topicId =id;
        this.topicName = topic;
    }
}
