package org.furb.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.LookupOp;
import java.awt.image.ShortLookupTable;
import java.awt.image.WritableRaster;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import org.furb.processor.MarcadorObj;
import org.furb.ui.fruit.FruitAnimation;
import org.furb.utils.SystemConfig;

import vxp.PixelSource;
import vxp.QTLivePixelSource;
import vxp.VideoListener;

public class NinjaCamera extends JFrame implements VideoListener, MouseListener
{
	
	/**
	 * SerialVersion default.
	 */
	private static final long serialVersionUID = 1L;
	
	private PixelSource camera;
	private BufferedImage borda;
	private BufferedImage imagemCapturada;
	private long tempoAtualizacao;
	private MarcadorObj marcador;
	private FruitAnimation fruitAnimation;
	
	/**
	 * Construtor padroa.
	 */
	public NinjaCamera()
	{
		this.initConfig();
		this.initComponents();
	}
	
	/**
	 * Inicializa as configuracoes
	 * da aplicacao.
	 */
	private void initConfig()
	{
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setIconImage(new ImageIcon(this.getClass().getResource("/org/furb/img/icon.png")).getImage());
		this.setTitle("NinjaCam");
		this.setSize(SystemConfig.APP_WIDTH, SystemConfig.APP_HEIGHT);
	}
	
	//inicializa atributos e objetos necessários para capturar a imagem da webcam
	private void initComponents()
	{
		//este atributo pode ser utilizado para exiber qual é a taxa de atualizao da imagem 
		this.tempoAtualizacao = 0;
		this.marcador = new MarcadorObj();
		this.camera = new QTLivePixelSource(SystemConfig.APP_WIDTH, SystemConfig.APP_HEIGHT, 30);
		
		//algumas vezes a largura do video é diferente da largura que foi forçada
		//this.largura = camera.getVideoWidth();
		this.borda = new BufferedImage(SystemConfig.APP_WIDTH, SystemConfig.APP_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		
		fruitAnimation = new FruitAnimation();
		fruitAnimation.init();
		
		//LISTENERS
		//{
			//inicia a captação dos frames
			this.camera.addVideoListener(this);
			
			this.addMouseListener(this);
		//}
	}
	
	//método obrigatório (VideoListener) 
	public void newFrame() 
	{
		
		// called everytime there is a new frame
		long inicioAtualizacao = System.currentTimeMillis();
		
		this.camera.grabFrame();
		
		//transforma o frame em imagem
		this.imagemCapturada = camera.getImage();
		this.inverterImagem();
		
		//verifica aonde está o objeto rastreado na imagem atual
		this.marcador.calcularMarcador(imagemCapturada);
		
		//atualiza imagem
		repaint();
		
		this.tempoAtualizacao = System.currentTimeMillis() - inicioAtualizacao;
	}

	private void inverterImagem()
	{
		AffineTransform transform = AffineTransform.getScaleInstance(-1, 1); 
		
		//translada a imagem
		transform.translate(- imagemCapturada.getWidth(null), 0); 
		
		AffineTransformOp  operaTransform = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		
		//aplica a translação
		imagemCapturada = operaTransform.filter(imagemCapturada, null); 
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
			
			fruitAnimation.recalcule();
			fruitAnimation.paint(g);
		}
	}

	//indica qual é a nova cor rastreada
	public void mouseClicked(MouseEvent e) 
	{
		WritableRaster rasterImagem = this.imagemCapturada.getRaster();
		
		int[] cor = new int[4];
		rasterImagem.getPixel(e.getX(), e.getY(), cor);
		
		this.marcador.setCorRastreada(cor[0], cor[1], cor[2]);
	}

	public void mouseEntered(MouseEvent e) {
		return;
	}

	public void mouseExited(MouseEvent e) {
		return;
	}

	public void mousePressed(MouseEvent e) {
		return;
	}

	public void mouseReleased(MouseEvent e) {
		return;
	}
	
}
