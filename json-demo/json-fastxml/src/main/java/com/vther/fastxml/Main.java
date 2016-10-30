package com.vther.fastxml;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vther.fastxml.domain.Result;
import com.vther.fastxml.domain.User;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wither on 2016/10/30.
 */
public class Main {

    // 能够复用，设置成全局变量
    private static ObjectMapper mapper = new ObjectMapper();// can reuse, share globally


    public static void main(String[] args) throws IOException {

        // 1 JSON字符串转对象
        User user = mapper.readValue(new File(Main.class.getResource("/user.json").getPath()), User.class);
        System.out.println("JSON字符串转对象 -> " + user);
        mapper.writeValue(getOrCreateFileByFileName("user-modified-1.json"), user);
        System.out.println("打印成为JSON格式 ->" + mapper.writeValueAsString(user));


        // 2 JSON字符串转Map
        Map<String, Object> userDataMap = mapper.readValue(new File(Main.class.getResource("/user.json").getPath()), Map.class);
        // Intellij IDEA 输入it可以快速补全遍历代码（Ctrl+J，map不行）
        System.out.println("JSON字符串转Map -> " + userDataMap);

        Map<String, Object> userData = new HashMap<String, Object>();
        Map<String, String> nameStruct = new HashMap<String, String>();
        nameStruct.put("first", "Joe");
        nameStruct.put("last", "Sixpack");
        userData.put("name", nameStruct);
        userData.put("gender", "MALE");
        userData.put("verified", Boolean.FALSE);
        userData.put("userImage", "Rm9vYmFyIQ==");
        mapper.writeValue(getOrCreateFileByFileName("user-modified-2.json"), userData);


        // 3 泛型
        Result<User> result = new Result<User>();
        result.setCode(200);
        result.setMsg("success");
        result.setResultData(user);
        String jsonGe = mapper.writeValueAsString(result);
        System.out.println("3 - JSON带有泛型 ->" + jsonGe);

        // http://kuyur.info/blog/archives/2782
        Result<User> result2 = mapper.readValue(jsonGe, new TypeReference<Result<User>>() {});
        System.out.println("3 - JSON带有泛型,转换回来 ->" + result2);
        User user2 = result2.getResultData();
        System.out.println("3 - JSON带有泛型,转换回来 ->" + user2);


        // 4 JACKSON ANNOTATIONS http://tutorials.jenkov.com/java-json/jackson-annotations.html

    }

    private static File getOrCreateFileByFileName(String fileName) throws IOException {
        File file = new File(Main.class.getResource("/").getPath() + fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }


}
