package com.nathan96169.toolshelper.trans;

import android.text.TextUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class OJsonGet {

	public static JsonArray getJsonArray(JsonObject object, String name){
		try {
			return object.get(name).getAsJsonArray();
		} catch (Exception e) {
			return null;
		}
	}
	public static JsonObject getJsonObject(JsonObject object, String name){
		try {
			return object.get(name).getAsJsonObject();
		} catch (Exception e) {
			return null;
		}
	}
	public static String getString(JsonObject object, String name){
		try {
			JsonElement value = object.get(name);
			if(value == null)return "";
			String result = value.getAsString();
			if(TextUtils.isEmpty(result))result = value.toString();
			return result;
		} catch (Exception e) {
			return "";
		}
	}
	public static long getLong(JsonObject object, String name){
		try {
			return object.get(name).getAsLong();
		} catch (Exception e) {
			return 0;
		}
	}
	public static boolean getBoolean(JsonObject object, String name){
		try {
			return object.get(name).getAsBoolean();
		} catch (Exception e) {
			return false;
		}
	}
	public static double getDouble(JsonObject object, String name){
		try {
			return object.get(name).getAsDouble();
		} catch (Exception e) {
			return 0;
		}
	}
	public static int getInteger(JsonObject object, String name){
		try {
			return object.get(name).getAsInt();
		} catch (Exception e) {
			return 0;
		}
	}
}
