package com.example.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.example.davila_multi_screen_application.AddActivity;

/**
 * Handler class for the Person Object List, This acts as a singleton across the
 * application.
 * 
 */
public class PersonHandler {

	/**
	 * Contains the singleton list Object.
	 */
	private static ArrayList<Person> list;

	/**
	 * Retrieves the list data from cache and returns it.
	 */
	@SuppressWarnings("unchecked")
	private static ArrayList<Person> retrieveData() {
		ObjectInput myReader;
		ArrayList<Person> list = null;
		try {
			// Load in an object

			InputStream file = new FileInputStream(AddActivity.CACHE_FILE);
			InputStream buffer = new BufferedInputStream(file);
			myReader = new ObjectInputStream(buffer);

			list = (ArrayList<Person>) myReader.readObject();
			myReader.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * Saves a new Person object in list of the the cache.
	 */
	public static boolean savePerson(Person daoObj) {

		ArrayList<Person> daoList = getList();
		if (daoList == null) {
			daoList = new ArrayList<Person>();
		}
		daoList.add(daoObj);
		return savePersonList(daoList);

	}

	/**
	 * Saves an entire List to cache.
	 */
	private static boolean savePersonList(ArrayList<Person> daoList) {
		try {

			OutputStream file = new FileOutputStream(AddActivity.CACHE_FILE);
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput out = new ObjectOutputStream(buffer);

			out.writeObject(daoList);
			out.close();
			buffer.close();
			file.close();
			return true;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static Person getPerson(int i) {
		if (getListSize() > 0) {
			return getList().get(i);
		}
		return null;

	}

	public static ArrayList<Person> getList() {
		if (list == null) {
			list = retrieveData();
		}
		return list;
	}

	public static int getListSize() {
		return getList().size();
	}

	public static void deletePerson(int index) {
		getList().remove(index);
		// Saves the List again after removing the Person
		savePersonList(list);

	}
}
