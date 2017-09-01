package com.vther.fastxml;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

// https://dzone.com/articles/jackson-mixin-to-the-rescue
public class ViewMixin {

	// 使用MixIn的地方
	// 1, 要被序列化或反序列化的class在第三方jar中
	// 2，不想让jackson侵入代码
	// 3，追求更干净，更模块化的设计
	public static void main(String[] args) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		// It turns out that we have to tell Jackson to use reflection and access the fields.
		mapper.setVisibility(mapper.getSerializationConfig()
				.getDefaultVisibilityChecker()
				.withFieldVisibility(JsonAutoDetect.Visibility.ANY)
				.withGetterVisibility(JsonAutoDetect.Visibility.NONE)
				.withSetterVisibility(JsonAutoDetect.Visibility.NONE)
				.withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
		Address address = new Address("ShenZhen", "CN");
		final String json = mapper.writeValueAsString(address);
		System.out.println(json);
		mapper.addMixIn(Address.class, AddressMixin.class);
		final Address deserializedUser = mapper.readValue(json, Address.class);
		System.out.println(deserializedUser);

	}

	public static class Address {
		private String city;
		private String state;


		public Address() {
		}

		public Address(String city, String state) {
			this.city = city;
			this.state = state;
		}

		@Override
		public String toString() {
			return "Address [city=" + city + ", state=" + state + "]";
		}
	}

	public abstract class AddressMixin {
		@JsonCreator
		public AddressMixin(
				@JsonProperty("city") String city,
				@JsonProperty("state") String state) {
			System.out.println("Wont be called");
		}
	}
}
