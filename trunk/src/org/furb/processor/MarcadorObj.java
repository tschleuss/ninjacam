package org.furb.processor;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class MarcadorObj
{

	private Line2D marcador;
	
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
	
	//verifica aonde est� a cor rastreada na imagem atual
	public void calcularMarcador(BufferedImage imagem)
	{
		WritableRaster rasterImagem = imagem.getRaster();
		
		int[] pixelRGB = new int[4];

		//destroi o marcador antigo
		this.marcador = null;
		
		Point p1 = null;
		Point p2 = new Point(0,0);
		
		//varre todas as linahs da imagem
		for (int x = 1; x < rasterImagem.getHeight() - 1; x+=20) 
		{
			//varre todas as colunas desta linha
			for (int y = 1; y < rasterImagem.getWidth() - 1; y+=20) 
			{
				//pega o RGB de um determinado pixel
				rasterImagem.getPixel(y, x, pixelRGB);
				
				int diferencaCor = this.processadorCor.calcularDifCores(pixelRGB, corRastreada);

				if (diferencaCor < this.limiteDifCor) 
				{
					if (p1 == null) {
						//cria o marcador, sem altura e largura
						//this.marcador = new Rectangle(y, x, 0, 0);
						//this.marcador = new Line2D.
						p1 = new Point(y, x);
					} else {
						//adiciona o pixel ao retangulo que est� marcando a imagem
						//this.marcador.add(y, x);
						p2.x =  y;
						p2.y =  x;
					}
				}
			}
		}
		
		if( p2.x == 0 && p2.y == 0 ) {
			this.marcador = null;
		} else {
			this.marcador = new Line2D.Float(p1.x,p1.y,p2.x,p2.y);
		}
	}
	
	public Line2D getMarcador()
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
