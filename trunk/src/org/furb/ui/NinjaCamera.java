package org.furb.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import org.furb.processor.MarcadorObj;

import vxp.PixelSource;
import vxp.QTLivePixelSource;
import vxp.VideoListener;

public class NinjaCamera extends JFrame implements VideoListener, MouseListener
{
	
	private PixelSource camera;
	private BufferedImage borda;
	private BufferedImage imagemCapturada;

	private int largura;
	private int altura;

	private long tempoAtualizacao;

	private MarcadorObj marcador;
	
	public NinjaCamera()
	{
		this.initConfig();
		this.initComponents();
	}
	
	private void initConfig()
	{
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	
		this.setIconImage(new ImageIcon(this.getClass().getResource("/org/furb/img/icon.png")).getImage());
		this.setTitle("NinjaCam");	
		this.setSize(this.largura, this.altura);
	}
	
	//inicializa atributos e objetos necess�rios para capturar a imagem da webcam
	private void initComponents()
	{
		//este atributo pode ser utilizado para exiber qual � a taxa de atualizao da imagem 
		this.tempoAtualizacao = 0;
		this.largura = 320;
		this.altura = 240;
		
		this.marcador = new MarcadorObj();
		this.camera = new QTLivePixelSource(largura, altura, 30);
		
		//algumas vezes a largura do video � diferente da largura que foi for�ada
		this.largura = camera.getVideoWidth(); 

		this.borda = new BufferedImage(this.largura, this.altura, BufferedImage.TYPE_INT_ARGB);

		//LISTENERS
		//{
			//inicia a capta��o dos frames
			this.camera.addVideoListener(this);
			
			this.addMouseListener(this);
		//}
	}
	
	//m�todo obrigat�rio (VideoListener) 
	public void newFrame() 
	{
		
		// called everytime there is a new frame
		long inicioAtualizacao = System.currentTimeMillis();
		
		this.camera.grabFrame();
		
		//transforma o frame em imagem
		this.imagemCapturada = camera.getImage();
		
		//verifica aonde est� o objeto rastreado na imagem atual
		this.marcador.calcularMarcador(imagemCapturada);
		
		//atualiza imagem
		repaint();
		
		this.tempoAtualizacao = System.currentTimeMillis() - inicioAtualizacao;
	}

	//exibe imagem da camera
	public void paint(Graphics g) 
	{
		if (this.imagemCapturada != null) 
		{
			//imagem com frame + marcador
			Graphics2D offScreenGraphics = this.borda.createGraphics();
			
			//desenha frame
			offScreenGraphics.drawImage(this.imagemCapturada, 0, 0, null);
			
			Rectangle sinalizadorObj = this.marcador.getMarcador();
			
			//desenha marcador
			if (sinalizadorObj != null)
			{
				offScreenGraphics.drawRect(sinalizadorObj.x, sinalizadorObj.y
										  ,sinalizadorObj.width, sinalizadorObj.height);
			}
			
			g.drawImage(borda, 0, 0, null);
		}
	}

	//indica qual � a nova cor rastreada
	public void mouseClicked(MouseEvent e) 
	{
		WritableRaster rasterImagem = this.imagemCapturada.getRaster();
		
		int[] cor = new int[4];
		rasterImagem.getPixel(e.getX(), e.getY(), cor);
		
		this.marcador.setCorRastreada(cor[0], cor[1], cor[2]);
	}

	public void mouseEntered(MouseEvent e) 
	{
	}

	public void mouseExited(MouseEvent e) 
	{
	}

	public void mousePressed(MouseEvent e) 
	{
	}

	public void mouseReleased(MouseEvent e) 
	{
	}
	
}
