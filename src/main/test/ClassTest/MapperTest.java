import com.alibaba.fastjson.JSONObject;
import com.amayadream.webchat.dto.ChatMessage;
import com.amayadream.webchat.mapping.ChatMessageMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * FileName: MapperTest
 * Author:  wangzicheng
 * Date:     2019/10/4 0004 17:16
 * Description: 测试类
 * History:
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-mvc.xml", "classpath:spring/spring-mybatis.xml"})
public class MapperTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private ChatMessageMapper chatMessageMapper;

    @Test
    public void test01() {
        List<ChatMessage> list = chatMessageMapper.getChatRecord("admin", "wjw");
        System.err.println(JSONObject.toJSONString(list));
    }
}
