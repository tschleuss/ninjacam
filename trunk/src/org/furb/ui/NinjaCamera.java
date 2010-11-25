package org.furb.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.text.AttributedString;

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
	
	private boolean debug = true;
	private final static BasicStroke stroke = new BasicStroke(5.0f);
	private PixelSource camera;
	private BufferedImage borda;
	private BufferedImage imagemCapturada;
	private MarcadorObj marcador;
	private FruitAnimation fruitAnimation;
	private ColisionDetector colisionDetector;
	private Graphics2D g2d;
	private Line2D line;
	private Rectangle retang;
	private AttributedString titleScore;
	private AttributedString score;
	
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
		this.camera = new QTLivePixelSource(SystemConfig.CAM_WIDTH, SystemConfig.CAM_HEIGHT, 30);
		this.borda = new BufferedImage(SystemConfig.APP_WIDTH, SystemConfig.APP_HEIGHT, BufferedImage.TYPE_INT_ARGB);
		
		titleScore = new AttributedString("PONTUACAO: ");
		titleScore.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
		titleScore.addAttribute(TextAttribute.FOREGROUND, Color.WHITE);
		titleScore.addAttribute(TextAttribute.SIZE, 24);

		colisionDetector = new ColisionDetector();
		fruitAnimation = new FruitAnimation();
		fruitAnimation.init();

		//inicia a captacao dos frames
		this.camera.addVideoListener(this);
		this.addMouseListener(this);
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
		
		//aplica a translacao
		imagemCapturada = operaTransform.filter(imagemCapturada, null); 
	}
	
	//exibe imagem da camera
	public void paint(Graphics g) 
	{
		if (this.imagemCapturada != null) 
		{
			//imagem com frame + marcador
			g2d = this.borda.createGraphics();
			
			//desenha frame
			g2d.drawImage(this.imagemCapturada, 0, 0, SystemConfig.APP_WIDTH, SystemConfig.APP_HEIGHT, null);
			
			line = this.marcador.getMarcador();
			retang = this.marcador.rect;
			
			if(retang != null && debug )
			{
				g2d.drawRect(retang.x, retang.y,retang.width, retang.height);
			}
			
			//desenha marcador
			if (line != null )
			{
				if( debug == true )
				{
					g2d.setColor(Color.BLACK);
					g2d.setBackground(Color.BLACK);
					g2d.setStroke(stroke);
					g2d.drawLine(
						(int)line.getX1(), (int)line.getY1(), 
						(int)line.getX2(), (int)line.getY2()
					);	
				}
				
				//Verifica a colisao com as frutas
				colisionDetector.check(this.fruitAnimation.getFruitList(), line);
			}
			
			g.drawImage(borda, 0, 0, null);
			
			//Recalcula e redesenha as frutas
			fruitAnimation.recalcule();
			fruitAnimation.paint(g);
			
			//title do score
			g.drawString(titleScore.getIterator(), this.getWidth()-250, this.getHeight()-20);
			
			//valor do score
			score = new AttributedString(String.valueOf(colisionDetector.getScore()));
			score.addAttribute(TextAttribute.FOREGROUND, Color.RED);
			score.addAttribute(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
			score.addAttribute(TextAttribute.SIZE, 24);

			g.drawString(score.getIterator(), this.getWidth()-50, this.getHeight()-20);
		}
	}

	//indica qual é a nova cor rastreada
	public void mouseClicked(MouseEvent e) 
	{
		WritableRaster rasterImagem = this.imagemCapturada.getRaster();
		
		int[] cor = new int[4];
		//Como a imagem e esticada apos o processamento, converter os pontos em tela
		final int coords[] = SystemConfig.convertPointToReal(e.getX(), e.getY());
		rasterImagem.getPixel(coords[0], coords[1], cor);
		
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
