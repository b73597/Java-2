package com.davila.actionbarexample.utils;

import java.util.ArrayList;

public class DataHandler {

	private static ArrayList<String> dataList;

	// Private constructor restricts object creation.
	private DataHandler() {
	}

	public static ArrayList<String> getDataList() {
		if(dataList == null){
			dataList = new ArrayList<String>();
			defaultDataLoader();
		}
		return dataList;
	}

	public static void addDataToList(String data){
		getDataList().add(data);
	}
	
	public static void removeDataFromList(int position){
		getDataList().remove(position);
	}

	private static void defaultDataLoader() {
		dataList.add("FOOTBALL");
		dataList.add("CRICKET");
		dataList.add("BASKETBALL");
		dataList.add("HOCKEY");
		dataList.add("TENNIS");
		dataList.add("VOLLEYBALL");
		dataList.add("TABLE TENNIS");
		dataList.add("BASE BALL");
		dataList.add("AMERICAN FOOTBALL");
		dataList.add("RUGBY");
		dataList.add("HOCKEY");
		dataList.add("HOCKEY");
		dataList.add("SWIMMING AND DIVING");
		dataList.add("FORMULA ONE");
		dataList.add("BOXING");
		dataList.add("BADMINGTON");
		dataList.add("SNOOKER");
		dataList.add("CYCLING");
		dataList.add("WRESTLING");
		dataList.add("HANDBALL");
	}

}
