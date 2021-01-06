package com.example.fffff.model;

import com.example.fffff.connection.Connection;
import com.example.fffff.utils.Constant;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Scanner;

public class CheckVerify {
    private Scanner mSc = new Scanner(System.in);

    public CheckVerify() {
        // TODO Auto-generated constructor stub
    }

    public void checkVer() {
        try {
            System.out.println("enter phonenumber: ");
            String phone = mSc.next();

            System.out.println("enter verify code: ");
            String verCode = mSc.next();

            StringBuffer stringBuffer = new StringBuffer();
            HttpURLConnection con = Connection.conect(Constant.CHECK_VERIFY_CODE+"?phonenumber="+phone+"&code_verify="+verCode,"POST");

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
                }else {
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
                System.out.println("err: "+e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {

        }
    }

    private void testCaseApi(StringBuffer stringBuffer) {
        // TODO Auto-generated method stub
        Gson gson = new Gson();
        Login.LoginReponse rp = gson.fromJson(stringBuffer.toString(), Login.LoginReponse.class);

        switch (rp.getMCode() + "") {
            case "1000":
                // System.out.println("Unit test 1: ");
                System.out.println("check verify success");
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
}
