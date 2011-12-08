/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Laurens
 */
public class Settings {
	
	private static int playfieldDimension = 16;
	private static int happerAmount = 1;
	private static String playerName = "Noobie";

	public static int getHapperAmount() {
		return happerAmount;
	}

	public void setHapperAmount(int happerAmount) {
		this.happerAmount = happerAmount;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getPlayfieldDimension() {
		return playfieldDimension;
	}

	public void setPlayfieldDimension(int playfieldDimension) {
		this.playfieldDimension = playfieldDimension;
	}
}
