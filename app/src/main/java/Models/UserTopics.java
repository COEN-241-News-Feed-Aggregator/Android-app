package Models;

import java.io.Serializable;
import java.util.List;

public class UserTopics implements Serializable{
    public int userId;
    public List<Topic> userTopics;

    public UserTopics(int userId, List<Topic> userTopics) {
        this.userId = userId;
        this.userTopics = userTopics;
    }
}
