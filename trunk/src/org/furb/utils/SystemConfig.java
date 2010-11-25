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
	public static final int APP_WIDTH = 800;//320;//480;
	
	/**
	 * Height da janela da aplicacao.
	 */
	public static final int APP_HEIGHT = 600;//240;//360;
	
	/**
	 * Width da camera da aplicacao.
	 */
	public static final int CAM_WIDTH = 320;//320;//480;
	
	/**
	 * Height da camera da aplicacao.
	 */
	public static final int CAM_HEIGHT = 240;//240;//360;
	
	/**
	 * Width da imagem da fruta.
	 */
	public static final int FRUIT_WIDTH = 32;
	
	/**
	 * Height da imagem da fruta.
	 */
	public static final int FRUIT_HEIGHT = 32;
	
	/**
	 * Construtor padrao.
	 */
	private SystemConfig() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Converte as coordenadas na imagem esticada
	 * para o as coordenadas da imagem no tamanho real.
	 * @param x
	 * @param y
	 * @return
	 */
	public static int[] convertPointToReal(int x, int y) {
		final int[] newCoords = new int[]{0,0};
		newCoords[0] = ( x * SystemConfig.CAM_WIDTH  ) / SystemConfig.APP_WIDTH;
		newCoords[1] = ( y * SystemConfig.CAM_HEIGHT ) / SystemConfig.APP_HEIGHT;
		return newCoords;
	}
	
	/**
	 * Converte as coordenadas na imagem normal
	 * para o as coordenadas da imagem esticada.
	 * @param x
	 * @param y
	 * @return
	 */
	public static int[] convertPointToVirtual(int x, int y) {
		final int[] newCoords = new int[]{0,0};
		newCoords[0] = ( x * SystemConfig.APP_WIDTH  ) / SystemConfig.CAM_WIDTH;
		newCoords[1] = ( y * SystemConfig.APP_HEIGHT ) / SystemConfig.CAM_HEIGHT;
		return newCoords;
	}
}
