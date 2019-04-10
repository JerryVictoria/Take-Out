package takeout.yummy.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Author: 161250127 TJW
 * @Description:
 * @Date: 2019/2/1
 */
@Entity
public class Manager {

    @Id
    private String uid;

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
