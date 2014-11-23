package com.yummly.utils;

import org.json.JSONObject;

public class Weather {

	private static String currentWeatherLocation;
	private static String hourlyWeatherLocation;
	private static String dailyWeatherLocation;

	private static JSONObject currentWeather;
	private static JSONObject hourlytWeather;
	private static JSONObject dailytWeather;
	public static String getCurrentWeatherLocation() {
		return currentWeatherLocation;
	}
	public static void setCurrentWeatherLocation(String currentWeatherLocation) {
		Weather.currentWeatherLocation = currentWeatherLocation;
	}
	public static String getHourlyWeatherLocation() {
		return hourlyWeatherLocation;
	}
	public static void setHourlyWeatherLocation(String hourlyWeatherLocation) {
		Weather.hourlyWeatherLocation = hourlyWeatherLocation;
	}
	public static String getDailyWeatherLocation() {
		return dailyWeatherLocation;
	}
	public static void setDailyWeatherLocation(String dailyWeatherLocation) {
		Weather.dailyWeatherLocation = dailyWeatherLocation;
	}
	public static JSONObject getCurrentWeather() {
		return currentWeather;
	}
	public static void setCurrentWeather(JSONObject currentWeather) {
		Weather.currentWeather = currentWeather;
	}
	public static JSONObject getHourlytWeather() {
		return hourlytWeather;
	}
	public static void setHourlytWeather(JSONObject hourlytWeather) {
		Weather.hourlytWeather = hourlytWeather;
	}
	public static JSONObject getDailytWeather() {
		return dailytWeather;
	}
	public static void setDailytWeather(JSONObject dailytWeather) {
		Weather.dailytWeather = dailytWeather;
	}

}
