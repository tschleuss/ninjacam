package org.furb.ui.fruit;

import java.awt.image.BufferedImage;

/**
 * Entidade responsavel por armazenas
 * as propriedades das frutas que
 * sao desenhas na tela.
 * @author tschleuss - tschleuss@gmail.com
 * @since Nov 13, 2010
 */
public class Fruit {

	private int x;
	private int y;
	private int width;
	private int height;
	private BufferedImage img;
	private int hits;
	private int destroyed;
	
	public Fruit() {
		super();
	}

	/**
	 * @return the x
	 */
	public final int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public final void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public final int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public final void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the width
	 */
	public final int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public final void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public final int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public final void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the img
	 */
	public final BufferedImage getImg() {
		return img;
	}

	/**
	 * @param img the img to set
	 */
	public final void setImg(BufferedImage img) {
		this.img = img;
		setWidth(img.getWidth());
		setHeight(img.getHeight());
	}

	/**
	 * @return the hits
	 */
	public final int getHits() {
		return hits;
	}

	/**
	 * @param hits the hits to set
	 */
	public final void setHits(int hits) {
		this.hits = hits;
	}

	/**
	 * @return the destroyed
	 */
	public final int getDestroyed() {
		return destroyed;
	}

	/**
	 * @param destroyed the destroyed to set
	 */
	public final void setDestroyed(int destroyed) {
		this.destroyed = destroyed;
	}
}
