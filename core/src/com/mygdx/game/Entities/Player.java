package com.mygdx.game.Entities;

import java.util.Set;
import java.util.TreeSet;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Interfaces.Collidable;
import com.mygdx.game.Interfaces.HitBox;

import Enums.CardinalDirection;



public class Player extends Entity implements Collidable{

	private HitBox hitbox;
	private final Set<Integer> pressed = new TreeSet<Integer>();
	private final Set<CardinalDirection> blockedDirections = new TreeSet<CardinalDirection>();
	private float speed = 3f;
	public Rectangle cellRect;

	public Player(String id,String character) {
		super(new Texture(character+".png"),id);
		hitbox = new HitBox(getWidth()*.25f,0,getWidth()*.5f,15);
	}

	@Override
	public void draw(Batch batch) {
		super.draw(batch);
	}

	@Override
	public void update() {

		float velocity = getSpeed();
		float velx=0;
		float vely=0;

		if(pressed.size()>1)
			velocity = velocity/2;

		Integer[] pressArray = pressed.toArray(new Integer[pressed.size()]);

		for(int i =0;i<pressArray.length;i++){

			int keycode = pressArray[i];
			switch(keycode) {
			case Keys.A:
				if(!blockedDirections.contains(CardinalDirection.WEST))
					velx-=getSpeed();
				break;
			case Keys.D:
				if(!blockedDirections.contains(CardinalDirection.EAST))
					velx=getSpeed();
				break;
			case Keys.W:
				if(!blockedDirections.contains(CardinalDirection.NORTH))
					vely=getSpeed();
				break;
			case Keys.S:
				if(!blockedDirections.contains(CardinalDirection.SOUTH))
					vely-=getSpeed();
				break;
			}
		}

		setX(getX()+velx);
		setY(getY()+vely);
	}
	public void press(int key)
	{
		pressed.add(key);
	}
	public void release(int key)
	{
		pressed.remove(key);
	}







	@Override
	public void checkMapCollisions(TiledMapTileLayer collisionLayer) 
	{
		float playerXCoord = (this.getHitbox().x/collisionLayer.getTileWidth());
		float playerYCoord = (this.getHitbox().y/collisionLayer.getTileHeight());

		float playerTileWidth =  hitbox.getWidth()/collisionLayer.getTileWidth();
		float playerTileHeight =  hitbox.getHeight()/collisionLayer.getTileHeight();

		float rectWidth = collisionLayer.getTileWidth();
		float rectHeight = collisionLayer.getTileHeight();

		if(collidesRight(collisionLayer, playerXCoord, playerYCoord, rectWidth, rectHeight, playerTileWidth, playerTileHeight)){
			blockedDirections.add(CardinalDirection.EAST);
		}
		else{
			blockedDirections.remove(CardinalDirection.EAST);
		}

		if(collidesLeft(collisionLayer, playerXCoord, playerYCoord, rectWidth, rectHeight, playerTileWidth, playerTileHeight)){
			blockedDirections.add(CardinalDirection.WEST);
		}
		else{
			blockedDirections.remove(CardinalDirection.WEST);
		}

		if(collidesTop(collisionLayer, playerXCoord, playerYCoord, rectWidth, rectHeight, playerTileWidth, playerTileHeight)){
			blockedDirections.add(CardinalDirection.NORTH);
		}
		else{
			blockedDirections.remove(CardinalDirection.NORTH);
		}

		if(collidesBottom(collisionLayer, playerXCoord, playerYCoord, rectWidth, rectHeight, playerTileWidth, playerTileHeight)){
			blockedDirections.add(CardinalDirection.SOUTH);
		}
		else{
			blockedDirections.remove(CardinalDirection.SOUTH);
		}
	}


	public boolean collidesBottom(TiledMapTileLayer collisionLayer,float playerXCoord, float playerYCoord, float rectWidth,float rectHeight,float playerTileWidth,float playerTileHeight) {

		float southY = playerYCoord-1;

		for(int i = (int)playerXCoord;i<= (int)(playerXCoord+playerTileWidth);i++){

			Cell southCell = collisionLayer.getCell((int)i, (int) southY);

			if(southCell !=null && southCell.getTile()!=null){

				float rectX = i*collisionLayer.getTileWidth();
				float rectY = (int)southY*collisionLayer.getTileHeight();

				Rectangle cellRect = new Rectangle(rectX,rectY,rectWidth,rectHeight+this.speed);

				if(this.overlaps(cellRect)){
					return true;
				}

			}
		}	
		return false;
	}
	public boolean collidesRight(TiledMapTileLayer collisionLayer,float playerXCoord, float playerYCoord, float rectWidth,float rectHeight,float playerTileWidth,float playerTileHeight) {

		float eastX = (playerXCoord+playerTileWidth+1);

		for(int i = (int)playerYCoord;i<= (int)(playerYCoord+playerTileHeight);i++){

			Cell eastCell = collisionLayer.getCell((int)eastX, (int)i);

			if(eastCell !=null && eastCell.getTile()!=null){

				float rectX = (int)eastX*collisionLayer.getTileWidth();
				float rectY = i*collisionLayer.getTileHeight();


				Rectangle cellRect = new Rectangle(rectX-this.speed,rectY,rectWidth,rectHeight);

				if(this.overlaps(cellRect)){
					return true;
				}
			}
		}	
		return false;
	}

	public boolean collidesLeft(TiledMapTileLayer collisionLayer,float playerXCoord, float playerYCoord, float rectWidth,float rectHeight,float playerTileWidth,float playerTileHeight) {

		float westX = playerXCoord-1;

		for(int i = (int)playerYCoord;i<= (int)(playerYCoord+playerTileHeight);i++){

			Cell westCell = collisionLayer.getCell((int)westX, (int)i);

			if(westCell !=null && westCell.getTile()!=null){

				float rectX = (int)westX*collisionLayer.getTileWidth();
				float rectY = i*collisionLayer.getTileHeight();

				Rectangle cellRect = new Rectangle(rectX,rectY,rectWidth+this.speed,rectHeight);

				if(this.overlaps(cellRect)){
					return true;
				}
			}
		}	
		return false;
	}

	public boolean collidesTop(TiledMapTileLayer collisionLayer,float playerXCoord, float playerYCoord, float rectWidth,float rectHeight,float playerTileWidth,float playerTileHeight) {

		float northY = playerYCoord+playerTileHeight+1;

		for(int i = (int)playerXCoord;i<= (int)(playerXCoord+playerTileWidth);i++){

			Cell northCell = collisionLayer.getCell((int)i, (int)northY);

			if(northCell !=null && northCell.getTile()!=null){

				float rectX = i*collisionLayer.getTileWidth();
				float rectY = (int)northY*collisionLayer.getTileHeight();

				Rectangle cellRect = new Rectangle(rectX,rectY-this.getSpeed(),rectWidth,rectHeight);

				if(this.overlaps(cellRect)){
					return true;
				}
			}
		}

		return false;

	}





	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	@Override
	public boolean overlaps (Rectangle r) {
		return hitbox.getX()	 +this.getX() 					  <= r.x + r.width 
				&& hitbox.getX() +this.getX() + hitbox.getWidth() >= r.x 
				&& hitbox.getY() +this.getY() 					  <= r.y + r.height 
				&& hitbox.getY() +this.getY() + hitbox.getHeight()>= r.y;
	}

	public HitBox getHitbox()
	{
		return new HitBox(hitbox.x+this.getX(),hitbox.y+this.getY(),hitbox.width,hitbox.height);
	}
	public void setHitbox(HitBox hitbox) {
		this.hitbox = hitbox;
	}

	@Override
	public void handleMovement() {
		// TODO Auto-generated method stub

	}
}



