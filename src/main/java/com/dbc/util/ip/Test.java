package com.dbc.util.ip;

public class Test {
	 public static void main(String[] args) {
		 IPSeeker seeker = new IPSeeker("qqwry.dat","WebRoot/util");
         String ipadd="42.121.110.22";
         System.out.println("的所在地址是:"+seeker.getArea(ipadd)+"国家"+seeker.getCountry(ipadd));
	 }
}
