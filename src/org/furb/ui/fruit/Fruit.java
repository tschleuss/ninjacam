package org.furb.ui.fruit;

import javax.swing.ImageIcon;

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
	private ImageIcon img;
	private int hits;
	private boolean destroyed;
	private boolean invisible;
	private boolean bomb;
	private int countExplosion;
	private final int maxCountExplosion = 10;
	
	public Fruit() {
		super();
		this.countExplosion = 0;
		this.destroyed = false;
		this.invisible = false;
		this.bomb = false;
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
	public final ImageIcon getImg() {
		return img;
	}

	/**
	 * @param img the img to set
	 */
	public final void setImg(ImageIcon img) {
		this.img = img;
		setWidth(img.getIconWidth());
		setHeight(img.getIconHeight());
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
	public final boolean isDestroyed() {
		return destroyed;
	}

	/**
	 * @param destroyed the destroyed to set
	 */
	public final void setDestroyed(boolean destroyed) 
	{
		this.destroyed = destroyed;
	}
	
	public final boolean isInvisible() {
		return invisible;
	}
	
	public final void setInvisible(boolean invisible)
	{
		this.invisible = invisible;
	}
	
	public final void setBomb(boolean bomb)
	{
		this.bomb = bomb;
	}
	
	public final boolean isBomb()
	{
		return bomb;
	}

	public final void clearExplosion()
	{
		countExplosion = 0;
		this.invisible = false;
		this.destroyed = false;
	}
	
	/**
	 * Controla a quantidade de frames de explosão
	 */
	public final void CountExplosion() 
	{
		//desenha o frame da explosão apenas 5 vezes
		if(countExplosion < maxCountExplosion)
		{
			countExplosion++;
		}
		else
		{
			this.invisible = true;
		}
	}
}
