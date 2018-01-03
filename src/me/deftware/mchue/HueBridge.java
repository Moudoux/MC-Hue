package me.deftware.mchue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Allows communication between your Philips Hue lights and this mod
 * 
 * @author Deftware
 *
 */
public class HueBridge {

	/*
	 * Edit the values below to match your node server
	 */
	private final int port = 8080;
	private final String server = "http://127.0.0.1:" + port + "/";

	public HueBridge() {

	}

	/**
	 * Set's the color of a light
	 * 
	 * @param hue
	 *            The color of a light, min = 0, max = 50k
	 * @param light
	 *            The light ID you want to set
	 */
	public void setColor(int hue, int light) {
		sendCommand("{\"cmd\":\"color\",\"data\":\"" + hue + "," + String.valueOf(light) + "\"}");
	}

	/**
	 * Set's the state of a light, on or off
	 * 
	 * @param light
	 * @param state
	 */
	public void setState(int light, boolean state) {
		sendCommand("{\"cmd\":\"state\",\"data\":\"" + String.valueOf(light) + "," + String.valueOf(state) + "\"}");
	}

	private String sendCommand(String command) {
		try {
			URL url = new URL(server);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setConnectTimeout(8 * 1000);
			connection.setRequestProperty("User-Agent", "MC-Hue");
			connection.setRequestProperty("cmd", command);

			connection.setRequestMethod("GET");

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String text;
			StringBuilder result = new StringBuilder();
			while ((text = in.readLine()) != null)
				result.append(text);

			in.close();

			return result.toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

}
