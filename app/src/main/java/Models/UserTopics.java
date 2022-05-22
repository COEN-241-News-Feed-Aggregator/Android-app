package Models;

import java.util.List;

public class UserTopics {
    public int userId;
    public List<Topic> userTopics;

    public UserTopics(int userId, List<Topic> userTopics) {
        this.userId = userId;
        this.userTopics = userTopics;
    }
}
