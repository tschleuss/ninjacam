package org.furb.processor;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class MarcadorObj
{

	private Rectangle marcador;
	
	private int limiteDifCor;
	private Cor processadorCor;
	private int[] corRastreada;
	
	public MarcadorObj()
	{	
		this.processadorCor = new Cor();
		this.limiteDifCor = 30;
		
		this.corRastreada = new int [3];
		this.setCorRastreada(255,0,0);
	}
	
	//verifica aonde está a cor rastreada na imagem atual
	public void calcularMarcador(BufferedImage imagem)
	{
		WritableRaster rasterImagem = imagem.getRaster();
		
		int[] pixelRGB = new int[4];

		//destroi o marcador antigo
		this.marcador = null;
		
		//varre todas as linahs da imagem
		for (int linhaImagem = 1; linhaImagem < rasterImagem.getHeight() - 1; linhaImagem++) 
		{
			
			//varre todas as colunas desta linha
			for (int colunaImagem = 1; colunaImagem < rasterImagem.getWidth() - 1; colunaImagem++) 
			{
				//pega o RGB de um determinado pixel
				rasterImagem.getPixel(colunaImagem, linhaImagem, pixelRGB);

				int diferencaCor = this.processadorCor.calcularDifCores(pixelRGB, corRastreada);

				if (diferencaCor < this.limiteDifCor) 
				{ 
					if (this.marcador == null) 
					{
						//cria o marcador, sem altura e largura
						this.marcador = new Rectangle(colunaImagem, linhaImagem, 0, 0);
					} else {
						//adiciona o pixel ao retangulo que está marcando a imagem
						this.marcador.add(colunaImagem, linhaImagem);
					}
				}
			}
		}
	}
	
	public Rectangle getMarcador()
	{
		return this.marcador;
	}
	
	public void setCorRastreada(int r, int g, int b)
	{
		this.corRastreada[0] = r;
		this.corRastreada[1] = g;
		this.corRastreada[2] = b;
	}
}
