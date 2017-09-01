package com.vther.fastxml;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Wither on 2016/10/30.
 */
public class ViewMain {

	// 能够复用，设置成全局变量
	private static ObjectMapper mapper = new ObjectMapper();// can reuse, share globally


	public static void main(String[] args) throws IOException {

		People people = new People("test name", "some address", 20);
//		配置是不是包含不视图注解的属性，默认是包含，即会输出age字段
//		mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
		// 反序列化时，忽略未定义属性。允许对端添加属性，而不影响解析
		//mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		String apiJson = mapper.writerWithView(View.Default.class).writeValueAsString(people);
		String iosJson = mapper.writerWithView(View.Ios.class).writeValueAsString(people);
		String andJson = mapper.writerWithView(View.Android.class).writeValueAsString(people);
		System.out.println(apiJson);//{"age":20}
		System.out.println(iosJson);        //{"name":"test name","age":20}
		System.out.println(andJson);        //{"address":"some address","age":20}

	}

	public interface View {
		interface Default {}

		interface Ios extends View.Default {}

		interface Android extends View.Default {}

	}

	public static class People {
		@JsonView({View.Ios.class})
		private String name;
		@JsonView({View.Android.class})
		private String address;

		private int age;


		public People(String name, String address, int age) {
			this.name = name;
			this.address = address;
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}


		@Override
		public String toString() {
			return "People{" +
					"name='" + name + '\'' +
					", address='" + address + '\'' +
					", age=" + age +
					'}';
		}
	}

}
