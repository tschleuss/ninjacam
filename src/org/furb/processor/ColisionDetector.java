package org.furb.processor;

import java.awt.Shape;
import java.util.List;

import org.furb.ui.fruit.Fruit;

/**
 * Classe responsavel por veriricar
 * a colisao entre o item detectado
 * em tela com os elementos interativos.
 * @author tschleuss - tschleuss@gmail.com
 * @since Nov 22, 2010
 */
public class ColisionDetector {

	/**
	 * Construtor padrao.
	 */
	public ColisionDetector() {
		super();
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
			}
		}
	}
}
