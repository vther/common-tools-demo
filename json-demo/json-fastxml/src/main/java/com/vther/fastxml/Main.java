package com.vther.fastxml;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vther.fastxml.domain.Result;
import com.vther.fastxml.domain.Student1;
import com.vther.fastxml.domain.Student2;
import com.vther.fastxml.domain.User;

import java.io.File;
import java.io.IOException;
import java.util.Date;
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
        System.out.println("1, JSON字符串转对象 -> " + user);
        mapper.writeValue(getOrCreateFileByFileName("user-modified-1.json"), user);
        System.out.println("1, 打印成为JSON格式 ->" + mapper.writeValueAsString(user));


        // 2 JSON字符串转Map
        System.out.println();
        Map<String, Object> userDataMap = mapper.readValue(new File(Main.class.getResource("/user.json").getPath()), Map.class);
        // Intellij IDEA 输入it可以快速补全遍历代码（Ctrl+J，map不行）
        System.out.println("2, JSON字符串转Map -> " + userDataMap);

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
        System.out.println();
        Result<User> result = new Result<User>();
        result.setCode(200);
        result.setMsg("success");
        result.setResultData(user);
        String jsonGe = mapper.writeValueAsString(result);
        System.out.println("3, JSON带有泛型 ->" + jsonGe);

        // http://kuyur.info/blog/archives/2782
        Result<User> result2 = mapper.readValue(jsonGe, new TypeReference<Result<User>>() {});
        System.out.println("3, JSON带有泛型,转换回来 ->" + result2);
        User user2 = result2.getResultData();
        System.out.println("3, JSON带有泛型,转换回来 ->" + user2);


        // 4 JACKSON ANNOTATIONS http://tutorials.jenkov.com/java-json/jackson-annotations.html

        // 5 多参数 少参数的转换
        System.out.println();
        Student1 student1 = new Student1();
        student1.setAge(10);
        student1.setName("Jim Green");
        student1.setSchool("Bei Jing Da Xue");
        String student1Str = mapper.writeValueAsString(student1);
        // System.out.println("5, 多参数 少参数的转换 ->" + student1Str);
        System.out.println("5, 多参数 少参数的转换:从少到多 ->" + mapper.readValue(student1Str, Student2.class));


        Student2 student2 = new Student2();
        student2.setAge(10);
        student2.setName("Jim Green");
        student2.setSchool("Bei Jing Da Xue");
        student2.setTeacher("teacher");
        student2.setEntranceTime(new Date());
        // 启用缺少属性不报异常，可以完成从多到少的转换
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        String student2Str = mapper.writeValueAsString(student2);
        System.out.println(student2Str);
        System.out.println("5, 多参数 少参数的转换 ->从多到少" + mapper.readValue(student2Str, Student1.class));

        // 6 时间类型的转换
        System.out.println();
        System.out.println("6, 时间类型的转换" + student2Str);

        // 如果转换回来的时候格式不对会抛出	 com.fasterxml.jackson.databind.exc.InvalidFormatException
        System.out.println("6, 如果转换回来的时候格式不对会抛出 com.fasterxml.jackson.databind.exc.InvalidFormatException"
                /*+ mapper.readValue(student2Str.replace("UTC", "aaa"), Student2.class)*/);

    }

    private static File getOrCreateFileByFileName(String fileName) throws IOException {
        File file = new File(Main.class.getResource("/").getPath() + fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }


}
