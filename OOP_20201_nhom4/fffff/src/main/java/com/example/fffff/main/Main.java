package com.example.fffff.main;

import com.example.fffff.model.*;

import java.util.Scanner;

public class Main {

	public static void main(String[] args){
//		Scanner in=new Scanner(System.in);
//		System.out.println("Enter your phone number");
//		String phone = in.next();
//
//		System.out.println("Enter your password");
//		String pass = in.next();

//		new SignUp().signUp();
		new Login().login("0312222222","aaaaaa");
//		new Logout().logout();
//		new GetVerifyCode().getVerify();
//		new DeletePost().delPost();
//		new CheckVerify().checkVer();
//		new LikePost().likePost();
//		new ReportPost().report();
		new SetComment().setCmt();
	}

}
