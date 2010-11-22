package org.furb.utils;

/**
 * Contem informacoes staticas
 * do programa, que podem ser
 * compartilhadas por todo o sistema
 * @author tschleuss - tschleuss@gmail.com
 * @since Nov 13, 2010
 */
public final class SystemConfig {

	/**
	 * Width da janela da aplicacao.
	 */
	public static final int APP_WIDTH = 480;//320;//480;
	
	/**
	 * Height da janela da aplicacao.
	 */
	public static final int APP_HEIGHT = 360;//240;//360;
	
	/**
	 * Width da imagem da fruta.
	 */
	public static final int FRUIT_WIDTH = 48;
	
	/**
	 * Height da imagem da fruta.
	 */
	public static final int FRUIT_HEIGHT = 48;
	
	/**
	 * Construtor padrao.
	 */
	private SystemConfig() {
		throw new UnsupportedOperationException();
	}	
}
