package com.carleton;

import java.io.Serializable;

public class YahtzeeMessage implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 4878730180620366043L;
	private String type;
	private int[] hand;

	public YahtzeeMessage(String type, int[] hand) {
		this.setType(type);
		this.setHand(hand);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int[] getHand() {
		return hand;
	}

	public void setHand(int[] hand) {
		this.hand = hand;
	}
}
