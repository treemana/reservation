package cn.edu.nefu.library.web;

import cn.edu.nefu.library.common.RestData;
import cn.edu.nefu.library.common.util.JsonUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : Hunter
 * @date : 18-11-2 下午2:58
 * @since : Java 8
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WebTestRoot {
    private MockMvc mockMvc;
    HttpServletRequest request = new MockHttpServletRequest();
    private RestData restData;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testWebRoot() {
        Assert.assertNotNull(request);
        Assert.assertNotNull(mockMvc);

        Map<String, String> user = new HashMap<>(2);
        user.put("studentId", "zy");
        user.put("studentName", "zy");

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/api/login");
        builder.contentType(MediaType.APPLICATION_JSON_UTF8).content(JsonUtil.getJsonString(user));
        try {
            MvcResult mvcResult = mockMvc.perform(builder).andReturn();
            String rtv = mvcResult.getResponse().getContentAsString();
            restData = getRestDataFromJson(rtv);
            Assert.assertNotNull(restData);
            Assert.assertEquals(0, restData.getCode());
            System.out.println(restData);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertNotNull(restData);
        }
    }

    RestData getRestDataFromJson(String jsonString) {
        Map<String, Object> restMap;
        restMap = JsonUtil.getMapFromJson(jsonString);

        RestData rtv = null;
        if (null != restMap) {
            int code = Integer.parseInt(restMap.get("code").toString());
            String message = null != restMap.get("message") ? restMap.get("message").toString() : null;
            String data = restMap.get("data").toString();
            rtv = new RestData(code, message, data);
        }
        return rtv;
    }

}
