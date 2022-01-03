package com.example.gamestate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class made to retrieve message of the day from provided API.
 *
 * @author Fjoraldo Gordoni.
 * @version 1.0.
 */
public class MOTD {
	private static HttpURLConnection connection;
	private static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/**
	 * function that gets puzzle from API.
	 *
	 * @return puzzle to be solved.
	 */
	private static String getAPI(){

		BufferedReader reader;
		String line;
		String result = "";
		StringBuffer responseContent = new StringBuffer();

		try {
			URL url = new URL("http://cswebcat.swansea.ac.uk/puzzle");
			connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);

			int status = connection.getResponseCode();

			if(status>299){
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while((line = reader.readLine())!= null){
					responseContent.append(line);
				}

				reader.close();

			} else {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while((line = reader.readLine())!= null){
					responseContent.append(line);
				}

				reader.close();
			}

			result = responseContent.toString();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException y){
			y.printStackTrace();
		}
		return result;
	}

	private static String elaborateAPI(){
		String puzzleString = getAPI();
		String result = "";

		//SHIFTING :  1st char goes 1 backwards
		//			  2nd char goes 2 forward
		//			  3rd char goes 3 backwards
		//			  4th char goes 4 forward etc.

		for (int i = 0; i < puzzleString.length();i++) {
			int charIndex = alphabet.indexOf(puzzleString.charAt(i));

			if(i % 2 == 0){
				if ((charIndex - (i+1)) < 0){
					char cipherChar =  alphabet.charAt((charIndex - (i+1))+26);
					result += cipherChar;
				} else {
					char cipherChar =  alphabet.charAt(charIndex - (i+1));
					result += cipherChar;
				}
			} else{
				if ((charIndex + (i+1)) > 25){
					char cipherChar =  alphabet.charAt((charIndex + (i+1))-26);
					result += cipherChar;
				} else {
					char cipherChar =  alphabet.charAt(charIndex + (i+1));
					result += cipherChar;
				}
			}
        }

		result += "CS-230";
		result = result.length() + result;
		
		return result;

	}

	/**
	 * gets the message of the day from liam's API.
	 *
	 * @return the message of the day.
	 */
	public static String getMOTD(){

		BufferedReader reader;
		String line;
		String result = "";
		StringBuffer responseContent = new StringBuffer();

		try {
			URL url = new URL("http://cswebcat.swansea.ac.uk/message?solution=" + elaborateAPI());
			connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);

			int status = connection.getResponseCode();

			if(status>299){
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while((line = reader.readLine())!= null){
					responseContent.append(line);
				}

				reader.close();

			} else {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while((line = reader.readLine())!= null){
					responseContent.append(line);
				}

				reader.close();
			}

			result = responseContent.toString();
			result = result.substring(0, result.indexOf("("));

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException y){
			y.printStackTrace();
		}
		return result;
	}
}
