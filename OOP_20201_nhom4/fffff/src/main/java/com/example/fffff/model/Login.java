package com.example.fffff.model;

import com.example.fffff.connection.Connection;
import com.example.fffff.utils.Constant;
import com.example.fffff.utils.Validations;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Scanner;

public class Login {
	private Scanner sc = new java.util.Scanner(System.in);

	public Login() {
		// TODO Auto-generated constructor stub
	}

	public void login(String phone,String pass) {
		if(phone.isEmpty()){
			System.out.println("Phone khong duoc rong");
		}
		if(pass.isEmpty()){
			System.out.println("Pass khong duoc rong");
		}

		if (Validations.checkValidation(phone, pass)) {
			try {
				StringBuffer stringBuffer = new StringBuffer();
				HttpURLConnection con = Connection
						.conect(Constant.LOGIN + "?phonenumber=" + phone + "&password=" + pass, "POST");

				try {
					int responseCode = con.getResponseCode();
					if (responseCode == HttpURLConnection.HTTP_OK) {
						BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

						String line;
						while ((line = bufferedReader.readLine()) != null) {
							stringBuffer.append(line);
							stringBuffer.append(System.lineSeparator());
						}
						testCaseApi(stringBuffer);
					} else {
						BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getErrorStream()));

						String line;
						while ((line = bufferedReader.readLine()) != null) {
							stringBuffer.append(line);
							stringBuffer.append(System.lineSeparator());
						}
						testCaseApi(stringBuffer);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {

			}
		}
	}

	private void testCaseApi(StringBuffer stringBuffer) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		LoginReponse rp = gson.fromJson(stringBuffer.toString(), LoginReponse.class);

		switch (rp.getMCode() + "") {
		case "1000":
//			System.out.println("Unit test 1: ");
			System.out.println("login success");
			rp.showInfo();
			break;
		case "1004":
			//System.out.println("Unit test 1: ");
			System.out.println("Parameter value is invalid");
			break;
		default:
			System.out.println(stringBuffer.toString());
			throw new IllegalArgumentException("Unexpected value: " + rp.getMCode());
		}
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public class LoginReponse {
		@SerializedName("message")
		public String mMessage;

		@SerializedName("code")
		public int mCode;

		@SerializedName("data")
		public UserReponse userReponse;

		public void showInfo() {
			System.out.println("message: " + mMessage);
			System.out.println("code: " + mCode);
			System.out.println("data:{ ");

			userReponse.showInfo();
			System.out.println("}");
		}

		@Getter
		@Setter
		@AllArgsConstructor
		public class UserReponse {
			@SerializedName("id")
			public String mId;

			@SerializedName("username")
			public String mUserName;

			@SerializedName("token")
			public String mToken;

			@SerializedName("avatar")
			public String mAvatar;

			public void showInfo() {
				System.out.println("id: " + this.mId);
				System.out.println("name: " + this.mUserName);
				System.out.println("token: " + this.mToken);
				System.out.println("avatar: " + this.mAvatar);
			}
		}
	}

}
