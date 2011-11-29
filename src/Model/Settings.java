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
	
	private int playfieldWidth;
	private int playfieldHeight;
	private int boxPercentage;
	private int rockPercentage;
	private int happerAmount;

	public Settings() {
		playfieldWidth = 16;
		playfieldHeight = 16;
		boxPercentage = 15;
		rockPercentage = 15;
		happerAmount = 1;
	}
	
	public int getBoxPercentage() {
		return boxPercentage;
	}

	public void setBoxPercentage(int boxPercentage) {
		this.boxPercentage = boxPercentage;
	}
	
	public int getHapperAmount() {
		return happerAmount;
	}

	public void setHapperAmount(int happerAmount) {
		this.happerAmount = happerAmount;
	}

	public int getRockPercentage() {
		return rockPercentage;
	}

	public void setRockPercentage(int rockPercentage) {
		this.rockPercentage = rockPercentage;
	}

	public int getPlayfieldHeight() {
		return playfieldHeight;
	}

	public void setPlayfieldHeight(int playfieldHeight) {
		this.playfieldHeight = playfieldHeight;
	}

	public int getPlayfieldWidth() {
		return playfieldWidth;
	}

	public void setPlayfieldWidth(int playfieldWidth) {
		this.playfieldWidth = playfieldWidth;
	}
}
