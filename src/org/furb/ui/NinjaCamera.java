package org.furb.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import org.furb.processor.ColisionDetector;
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
	private MarcadorObj marcador;
	private FruitAnimation fruitAnimation;
	private ColisionDetector colisionDetector;
	private Graphics2D offScreenGraphics;
	private Line2D line;
	private final static BasicStroke stroke = new BasicStroke(5.0f);
	
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
		this.setLocationRelativeTo(null);
	}
	
	//inicializa atributos e objetos necessários para capturar a imagem da webcam
	private void initComponents()
	{
		this.marcador = new MarcadorObj();
		this.camera = new QTLivePixelSource(SystemConfig.APP_WIDTH, SystemConfig.APP_HEIGHT, 30);
		
		//algumas vezes a largura do video é diferente da largura que foi forçada
		//this.largura = camera.getVideoWidth();
		this.borda = new BufferedImage(SystemConfig.APP_WIDTH, SystemConfig.APP_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		
		colisionDetector = new ColisionDetector();
		fruitAnimation = new FruitAnimation();
		fruitAnimation.init();
		
		//LISTENERS
		//{
			//inicia a captacao dos frames
			this.camera.addVideoListener(this);
			this.addMouseListener(this);
		//}
	}
	
	//método obrigatório (VideoListener) 
	public void newFrame() 
	{
		this.camera.grabFrame();
		
		//transforma o frame em imagem
		this.imagemCapturada = camera.getImage();
		this.inverterImagem();
		
		//verifica aonde está o objeto rastreado na imagem atual
		this.marcador.calcularMarcador(imagemCapturada);
		
		//atualiza imagem
		repaint();
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
			offScreenGraphics = this.borda.createGraphics();
			
			//desenha frame
			offScreenGraphics.drawImage(this.imagemCapturada, 0, 0, null);
			
			line = this.marcador.getMarcador();
			
			//desenha marcador
			if (line != null)
			{
				offScreenGraphics.setColor(Color.BLACK);
				offScreenGraphics.setBackground(Color.BLACK);
				offScreenGraphics.setStroke(stroke);
				offScreenGraphics.drawLine(
					(int)line.getX1(), (int)line.getY1(), 
					(int)line.getX2(), (int)line.getY2()
				);
				
				colisionDetector.check(this.fruitAnimation.getFruitList(), line);
				//drawRect(sinalizadorObj.getX1()x, sinalizadorObj.y
				//,sinalizadorObj.width, sinalizadorObj.height);
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
