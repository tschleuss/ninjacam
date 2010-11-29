package org.furb.processor;

import java.awt.Shape;
import java.util.List;

import org.furb.ui.fruit.Fruit;

/**
 * Classe responsavel por veriricar
 * a colisao entre o item detectado
 * em tela com os elementos interativos.
 * @since Nov 22, 2010
 */
public class ColisionDetector {

	private int score;
	
	/**
	 * Construtor padrao.
	 */
	public ColisionDetector() {
		super();
		score = 0;
	}
	
	/**
	 * Verifica as colisoes entre os shapes em scena
	 * @param slaveList
	 * @param rootObj
	 */
	public void check(List<Fruit> shapeList, Shape rootObj)
	{
		for( Fruit shape : shapeList )
		{
			//só verifica os objetos ainda não destruidos
			if(!shape.isDestroyed())
			{
				final boolean isIntersection = rootObj.intersects(
						shape.getX(),
						shape.getY(),
						shape.getWidth(), 
						shape.getHeight()
					);
					
					if( isIntersection ) 
					{
						shape.setDestroyed(true);
						shape.setHits( shape.getHits()+1 );
						
						if(!shape.isBomb())
						{
							score += 10;	
						}
						else
						{
							score -= 20;
						}
						
					}
			}

		}
	}

	public int getScore() 
	{
		return score;
	}
	
	
}
