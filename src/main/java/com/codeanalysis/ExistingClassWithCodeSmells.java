package com.codeanalysis;

public class ExistingClassWithCodeSmells {

	private int height = 10;
	private int width = 20;

	public ExistingClassWithCodeSmells(int height, int width) {
		this.height = height;
		this.width = width;
	}
	
	public int calculateArea() {
		return height * width;
	}
	
	public void printData(String data1, String data2 , String data3, String data4, String data5, String data6, String data7) {
		System.out.println(data1+data2+data3+data4+data5+data6+data7);
		
		String thisdataistoolbigtobeconsideredforcalculation = "data";
	}
}
