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
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class SetComment {
    private Scanner sc = new Scanner(System.in);

    public void setCmt() {
        System.out.println("Enter your token:");
        String token = sc.nextLine();
        if(token.isEmpty()){
            System.out.println("Token khong duoc rong");
        }

        System.out.println("Post ID:");
        String id = sc.nextLine();
        if(id.isEmpty()){
            System.out.println("Id khong duoc rong");
        }

        System.out.println("Comment:");
        String cmt = sc.nextLine();
        if(cmt.isEmpty()){
            System.out.println("Cmt khong duoc rong");
        }

        System.out.println("Index:");
        int index = Integer.parseInt(sc.nextLine());
        String regex="[0-9]+";
        boolean matches = Pattern.matches(regex, String.valueOf(index));
        if(matches==false){
            System.out.println("Index phai chua so");
        }
        if(String.valueOf(index).isEmpty()){
            System.out.println("Index khong duoc rong");
        }

        System.out.println("Count:");
        int count = Integer.parseInt(sc.nextLine());
        boolean matchCount = Pattern.matches(regex, String.valueOf(index));
        if(matchCount==false){
            System.out.println("Count phai chua so");
        }
        if(String.valueOf(count).isEmpty()){
            System.out.println("Count khong duoc rong");
        }

        try {
            StringBuffer stringBuffer = new StringBuffer();
            HttpURLConnection con = Connection
                    .conect(Constant.SET_COMMENT + "?token=" + token + "&id=" + id + "&comment=" + cmt + "&index=" + index + "&count=" + count, "POST");

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


    private void testCaseApi(StringBuffer stringBuffer) {
        // TODO Auto-generated method stub
        Gson gson = new Gson();
        SetCmtRes rp = gson.fromJson(stringBuffer.toString(), SetCmtRes.class);
        switch (rp.getMCode() + "") {
            case "1000":
                // System.out.println("Unit test 1: ");
                System.out.println("Set cmt success");
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
    public class SetCmtRes {
        @Getter
        @Setter
        @AllArgsConstructor
        public class SetCmtData {
            @SerializedName("id")
            public String id;

            @SerializedName("comment")
            public String cmt;

            @SerializedName("created")
            public long created;

            @SerializedName("poster")
            public Poster poster;

            public void showInfo() {
                System.out.println("id: " + this.getId());
                System.out.println("name: " + this.getCmt());
                System.out.println("Created: " + this.getCreated());
                System.out.println("Poster:{ " );
                System.out.println("id: "+this.getPoster().getId());
                System.out.println("name: "+this.getPoster().getName());
                System.out.println("avatar: "+this.getPoster().getAva());
                System.out.println("}");
            }

            @Getter
            @Setter
            @AllArgsConstructor
            public class Poster{
                @SerializedName("id")
                public String id;

                @SerializedName("name")
                public String name;

                @SerializedName("avatar")
                public String ava;

                public void showInfo() {
                    System.out.println("id: " + this.getId());
                    System.out.println("name: " + this.getName());
                    System.out.println("Avatar: " + this.getAva());
                }
            }
        }

        @SerializedName("message")
        public String mMessage;

        @SerializedName("code")
        public int mCode;

        @SerializedName("data")
        public List<SetCmtData> usrRes;

        public void showInfo() {
            System.out.println("message: " + mMessage);
            System.out.println("code: " + mCode);
            System.out.println("data:{ ");
            for (SetCmtData d:usrRes) {
                 d.showInfo();
            }

            System.out.println("}");

        }


    }
}
