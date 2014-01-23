package com.oz.utils;

/**
 * Date: 27/03/12
 * Time: 07:45 PM
 *
 * @author José Alberto Sánchez González
 * ***********************
 * Twitter: @jaehoox<br/>
 * Website: <a href="http://www.orbitalzero.com">http://www.orbitalzero.com</a>
 * ***********************
 */
public class Position {

	public String toString() {
		return "["+x + "," + y+"]";
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	private Integer x;
	private Integer y;

	public Position() {
	}

	public Position(Integer x, Integer y) {
		this.x = x;
		this.y = y;

	}
}