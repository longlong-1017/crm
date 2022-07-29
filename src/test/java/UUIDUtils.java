import org.junit.Test;

import java.util.UUID;

/**
 * @Author: YunLong
 * @Date: 2022/7/29 17:05
 */
public class UUIDUtils {
    @Test
    public void getUUID(){
        String uuid =UUID.randomUUID().toString();
        String replace = uuid.replace("-", "");
        System.out.println(replace);
    }
}
