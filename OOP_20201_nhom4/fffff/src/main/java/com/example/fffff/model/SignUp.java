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
import java.util.UUID;

public class SignUp {
    private Scanner sc = new java.util.Scanner(System.in);
//    static UUID uuid = UUID.randomUUID();
    static String phone,pass;

    public void signUp() {
        System.out.println("Enter your phone number");
        phone = sc.next();
        if(phone.isEmpty()){
            System.out.println("Phone khong duoc rong");
        }

        System.out.println("Enter your password");
        pass = sc.next();
        if(pass.isEmpty()){
            System.out.println("Pass khong duoc rong");
        }

//        System.out.println("Your UUID: " + uuid);

        if (Validations.checkValidation(phone, pass)) {
            try {
                StringBuffer stringBuffer = new StringBuffer();
                HttpURLConnection con = Connection
                        .conect(Constant.SIGNUP + "?phonenumber=" + phone + "&password=" + pass , "POST");

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
        SignUpResponse rp = gson.fromJson(stringBuffer.toString(), SignUpResponse.class);
        switch (rp.getMCode() + "") {
            case "1000":
                // System.out.println("Unit test 1: ");
                System.out.println("SignUp success");
                rp.showInfo();
                break;
            case "1004":
                // System.out.println("Unit test 1: ");
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
    public class SignUpResponse {
        @Getter
        @Setter
        @AllArgsConstructor
        public class SignUpReponse {
            @SerializedName("phoneNumber")
            public String mUserName;

            @SerializedName("id")
            public String id;

            @SerializedName("verifyCode")
            public String ver;

            @SerializedName("isVerified")
            public boolean isVer;

            public void showInfo() {
                System.out.println("id: " + this.getId());
                System.out.println("name: " + this.getMUserName());
                System.out.println("verifyCode: " + this.getVer());
                System.out.println("isVerified: "+this.isVer);
            }
        }

        @SerializedName("message")
        public String mMessage;

        @SerializedName("code")
        public int mCode;

        @SerializedName("data")
        public SignUpReponse usrRes;

        public void showInfo() {
            System.out.println("message: " + mMessage);
            System.out.println("code: " + mCode);
            System.out.println("data:{ ");
            usrRes.showInfo();

            System.out.println("}");

        }


    }
}
