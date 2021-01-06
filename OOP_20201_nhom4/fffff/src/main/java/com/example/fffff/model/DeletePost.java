package com.example.fffff.model;

import com.example.fffff.connection.Connection;
import com.example.fffff.utils.Constant;
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

public class DeletePost {
    private Scanner mSc = new Scanner(System.in);

    public DeletePost() {
        // TODO Auto-generated constructor stub
    }

    public void delPost() {
        System.out.println("enter token: ");
        String token = mSc.nextLine();
        if(token.isEmpty()){
            System.out.println("Token khong duoc rong");
        }

        System.out.println("enter post id: ");
        String pId = mSc.nextLine();
        if(pId.isEmpty()){
            System.out.println("Id khong duoc rong");
        }

        try {
            StringBuffer stringBuffer = new StringBuffer();
            HttpURLConnection con = Connection.conect(Constant.DELETE_POST + "?token=" + token + "&id=" + pId, "POST");

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
                // TODO Auto-generated catch block
                System.out.println("err: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {

        }
    }

    private void testCaseApi(StringBuffer stringBuffer) {
        // TODO Auto-generated method stub
        Gson gson = new Gson();
        DelRes rp = gson.fromJson(stringBuffer.toString(), DelRes.class);

        switch (rp.getMCode() + "") {
            case "1000":
                // System.out.println("Unit test 1: ");
                System.out.println("delete success");
                rp.showInfo();
                break;
            case "1004":
                // System.out.println("Unit test 1: ");
                System.out.println("Parameter value is invalid");
                break;
            case  "9992":
                System.out.println("Post not exist");
                break;
            default:
                System.out.println(stringBuffer.toString());
                throw new IllegalArgumentException("Unexpected value: " + rp.getMCode());
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public class DelRes {
        @SerializedName("message")
        public String mMessage;

        @SerializedName("code")
        public int mCode;

//        @SerializedName("data")
//        public Login.LoginReponse.UserReponse userReponse;

        public void showInfo() {
            System.out.println("message: " + mMessage);
            System.out.println("code: " + mCode);
//            System.out.println("data:{ ");
//
//            userReponse.showInfo();
//            System.out.println("}");
        }

    }
}

