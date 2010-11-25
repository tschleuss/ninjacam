package org.furb.ui.fruit;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.furb.utils.ResourceLocator;
import org.furb.utils.SystemConfig;

/**
 * Classe responsavel por realizar
 * as animacoes com as frutas em cena.
 * @author tschleuss - tschleuss@gmail.com
 * @since Nov 13, 2010
 */
public class FruitAnimation {

	private List<Fruit> fruitList = null;
	private Random rnd = null;
	private ImageIcon explosion = null;
	
	private int velocidadeQueda;
	
	/**
	 * Construtor padrao.
	 */
	public FruitAnimation() {
		super();
	}
	
	/**
	 * Inicializa as frutas a serem
	 * exibidas em tela.
	 */
	public void init() 
	{
		rnd = new Random(System.currentTimeMillis());
		fruitList = new ArrayList<Fruit>();
		explosion = new ImageIcon(this.getClass().getResource("/org/furb/img/gif/Explosion.gif"));
				
//		fruitList.add( getFruit("Apple.png")		);
//		fruitList.add( getFruit("Apple2.png")		);
//		fruitList.add( getFruit("Kiwi.png") 		);
//		fruitList.add( getFruit("Lemon.png")		);
//		fruitList.add( getFruit("Lime.png")			);
//		fruitList.add( getFruit("Orange.png")		);
//		fruitList.add( getFruit("Orange2.png")		);
//		fruitList.add( getFruit("Strawberry.png")	);
//		fruitList.add( getFruit("Tomato.png")		);

		for( int i = 0; i < 2; i++ )
		{
			fruitList.add( getFruit("gif/apple.gif")		);
			fruitList.add( getFruit("gif/Strawberry.gif")	);
			fruitList.add( getFruit("gif/Orange.gif")		);	
		}
		
		Fruit bomb = null;
		
		for( int i = 0; i < 3; i++ )
		{
			bomb = getFruit("gif/Bomb.gif");
			bomb.setBomb(true);
			fruitList.add(bomb);	
		}
		
		this.velocidadeQueda = 10;
	}
	
	/**
	 * Recalcula a posicao
	 * das frutas na tela.
	 */
	public void recalcule() 
	{
		for( Fruit f : fruitList ) {
			
			if( f.getY() < SystemConfig.APP_HEIGHT) 
			{
				f.setY( f.getY() + this.velocidadeQueda );
			} 
			else 
			{
				f.setX( rnd.nextInt(SystemConfig.APP_WIDTH) );
				f.setY( rnd.nextInt(SystemConfig.APP_HEIGHT) * -1 );
				
				//reseta estado da fruta
				f.clearExplosion();
			}
		}
	}
	
	/**
	 * Renderiza as frutas sobre o stream
	 * de video da webcam, de acordo com as
	 * coordenadas X,Y das mesmas.
	 * @param g
	 */
	public void paint(Graphics g) {
		
		for( Fruit f : fruitList ) {
			//desenha apenas se está visível
			if(!f.isInvisible())
			{
				if( f.isDestroyed() ) {
					//exibe a imagem da explosão
					g.drawImage(explosion.getImage(), f.getX(), f.getY(), null);
					f.CountExplosion();
				}
				else
				{
					g.drawImage(f.getImg().getImage(), f.getX(), f.getY(), null);
				}
			}
		}
	}
	
	/**
	 * Cria uma instancia da fruta
	 * de acordo com a imagem especificada
	 * por parametro.
	 * @param name
	 * @return
	 */
	private Fruit getFruit(final String name) 
	{
		Fruit fruit = null;
		
		try {
			
			fruit = new Fruit();
			
			if( name.indexOf(".gif") == -1 ) 
			{
				final MessageFormat mf = new MessageFormat("/org/furb/img/{0}");
				final String fruitPath = mf.format(new Object[]{name});
				
				InputStream fruitIs = ResourceLocator.getResource(fruitPath, FruitAnimation.class); 
				BufferedImage fruitImg = ImageIO.read(fruitIs);
				fruitImg = resize(fruitImg, SystemConfig.FRUIT_WIDTH, SystemConfig.FRUIT_HEIGHT);
				fruit.setImg(new ImageIcon(fruitImg));
			} 
			else {
				
				ImageIcon fruitImage = new ImageIcon(this.getClass().getResource("/org/furb/img/" + name));
				fruit.setImg(fruitImage);
			}
			
			fruit.setX( rnd.nextInt(SystemConfig.APP_WIDTH) );
			fruit.setY( rnd.nextInt(SystemConfig.APP_HEIGHT)*-1 );
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return fruit;
	}
	
	/**
	 * Redimensiona o png para uma tamanho menor,
	 * ja que a tela da webcam e menor. A imagem
	 * original e grande para caso a janela da webcam
	 * seja aumentada nao precisa criar imagens novas,
	 * apenas nao precisa redimensionar.
	 * @param image
	 * @param width
	 * @param height
	 * @return
	 */
	private static BufferedImage resize(BufferedImage image, int width, int height) {
		BufferedImage resizedImage = new BufferedImage(width, height,
		BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();
		return resizedImage;
	}

	/**
	 * @return the fruitList
	 */
	public final List<Fruit> getFruitList() {
		return fruitList;
	}
}
