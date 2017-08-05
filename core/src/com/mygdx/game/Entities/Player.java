package com.mygdx.game.Entities;

import java.util.Set;
import java.util.TreeSet;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Interfaces.CardinalDirection;
import com.mygdx.game.Interfaces.Collidable;
import com.mygdx.game.Interfaces.Entity;
import com.mygdx.game.Interfaces.HitBox;



public class Player extends Entity implements Collidable{

	private HitBox hitbox;
	private final Set<Integer> pressed = new TreeSet<Integer>();
	private final Set<CardinalDirection> blockedDirections = new TreeSet<CardinalDirection>();
	private float speed = 4f;
	public Rectangle cellRect;

	public Player(String id,String character) {
		super(new Texture(character+".png"),id);
		hitbox = new HitBox(getWidth()/4,0,getWidth()*.5f,25);
	}

	@Override
	public void draw(Batch batch) {
		super.draw(batch);
	}
	/*public HitBox getAttachedHitbox() {
		return hitbox;
	}*/
	public HitBox getHitbox()
	{
		return new HitBox(hitbox.x+this.getX(),hitbox.y+this.getY(),hitbox.width,hitbox.height);
	}
	public void setHitbox(HitBox hitbox) {
		this.hitbox = hitbox;
	}

	@Override
	public void update() {

		float velocity = getSpeed();
		float velx=0;
		float vely=0;

		if(pressed.size()>1)
			velocity = velocity/2;
		Integer[] pressArray = pressed.toArray(new Integer[pressed.size()]);

		for(int i =0;i<pressArray.length;i++)
		{
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
		if(collidesRight(collisionLayer))
		{
			blockedDirections.add(CardinalDirection.EAST);
		}
		else
		{
			blockedDirections.remove(CardinalDirection.EAST);
		}

		if(collidesLeft(collisionLayer))
		{
			blockedDirections.add(CardinalDirection.WEST);
		}
		else
		{
			blockedDirections.remove(CardinalDirection.WEST);
		}

		if(collidesTop(collisionLayer))
		{
			blockedDirections.add(CardinalDirection.NORTH);
		}
		else
		{
			blockedDirections.remove(CardinalDirection.NORTH);
		}

		if(collidesBottom(collisionLayer))
		{
			blockedDirections.add(CardinalDirection.SOUTH);
		}
		else
		{
			blockedDirections.remove(CardinalDirection.SOUTH);
		}
	}
	public boolean collidesBottom(TiledMapTileLayer collisionLayer) {

		float playerXCoord = (hitbox.getX()/collisionLayer.getTileWidth());
		float playerYCoord = (hitbox.getY()/collisionLayer.getTileHeight());
		float playerTileWidth = hitbox.getWidth()/collisionLayer.getTileWidth();
		float southY = playerYCoord-1;

		for(int i = (int)playerXCoord;i<= (int)(playerXCoord+playerTileWidth);i++)
		{

			Cell southCell = collisionLayer.getCell((int)i, (int) southY);


			if(southCell !=null && southCell.getTile()!=null)
			{
				float rectX = i*collisionLayer.getTileWidth();
				float rectY = (int)southY*collisionLayer.getTileHeight();
				float rectWidth = collisionLayer.getTileWidth();
				float rectHeight = collisionLayer.getTileHeight();

				Rectangle cellRect = new Rectangle(rectX,rectY,rectWidth,rectHeight+this.speed);

				if(this.overlaps(cellRect))
				{
					return true;
				}
				
			}
		}	
		return false;
	}
	public boolean collidesRight(TiledMapTileLayer collisionLayer) {

		float playerXCoord = (this.getX()/collisionLayer.getTileWidth());
		float playerYCoord = (this.getY()/collisionLayer.getTileHeight());
		float playerTileWidth =  this.getHitbox().getWidth()/collisionLayer.getTileWidth();
		float playerTileHeight =  this.getHitbox().getHeight()/collisionLayer.getTileHeight();
		float EastX = (int)(playerXCoord+playerTileWidth+1);
		for(int i = (int)playerYCoord;i<= (int)(playerYCoord+playerTileHeight);i++)
		{
			Cell eastCell = collisionLayer.getCell((int)EastX, (int)i);

			if(eastCell !=null && eastCell.getTile()!=null)
			{
				float rectX = (int)EastX*collisionLayer.getTileWidth();
				float rectY = i*collisionLayer.getTileHeight();
				float rectWidth = collisionLayer.getTileWidth();
				float rectHeight = collisionLayer.getTileHeight();

				Rectangle cellRect = new Rectangle(rectX-this.speed,rectY,rectWidth,rectHeight);

				if(this.overlaps(cellRect))
				{
					return true;
				}
			}
		}	
		return false;
	}

	public boolean collidesLeft(TiledMapTileLayer collisionLayer) {
		float playerXCoord = (this.getX()/collisionLayer.getTileWidth());
		float playerYCoord = (this.getY()/collisionLayer.getTileHeight());
		float playerTileHeight =  this.getHitbox().getHeight()/collisionLayer.getTileHeight();;

		float WestX = playerXCoord-1;

		for(int i = (int)playerYCoord;i<= (int)(playerYCoord+playerTileHeight);i++)
		{
			Cell westCell = collisionLayer.getCell((int)WestX, (int)i);

			if(westCell !=null && westCell.getTile()!=null)
			{
				//System.out.println("x:"+i*collisionLayer.getTileWidth()+"   Y:"+southY*collisionLayer.getTileHeight()+" Width:"+collisionLayer.getTileWidth()+ " Height:"+collisionLayer.getTileHeight() );
				float rectX = (int)WestX*collisionLayer.getTileWidth();
				float rectY = i*collisionLayer.getTileHeight();
				float rectWidth = collisionLayer.getTileWidth();
				float rectHeight = collisionLayer.getTileHeight();
				Rectangle cellRect = new Rectangle(rectX,rectY,rectWidth+this.speed,rectHeight);

				if(this.overlaps(cellRect))
				{
					return true;
				}
			}
		}	
		return false;
	}

	public boolean collidesTop(TiledMapTileLayer collisionLayer) {
		float playerXCoord = (this.getX()/collisionLayer.getTileWidth());
		float playerYCoord = (this.getY()/collisionLayer.getTileHeight());
		float playerTileWidth =  this.getHitbox().getWidth()/collisionLayer.getTileWidth();
		float playerTileHeight =  this.getHitbox().getHeight()/collisionLayer.getTileHeight();
		float northY = playerYCoord+playerTileHeight+1;

		for(int i = (int)playerXCoord;i<= (int)(playerXCoord+playerTileWidth);i++)
		{
			Cell northCell = collisionLayer.getCell((int)i, (int)northY);

			if(northCell !=null && northCell.getTile()!=null)
			{
				//System.out.println("x:"+i*collisionLayer.getTileWidth()+"   Y:"+southY*collisionLayer.getTileHeight()+" Width:"+collisionLayer.getTileWidth()+ " Height:"+collisionLayer.getTileHeight() );
				float rectX = i*collisionLayer.getTileWidth();
				float rectY = (int)northY*collisionLayer.getTileHeight();
				float rectWidth = collisionLayer.getTileWidth();
				float rectHeight = collisionLayer.getTileHeight();

				//System.out.println("-x:"+rectX+"   Y:"+rectY+" Width:"+rectWidth+ " Height:"+rectHeight );
				//System.out.println("_x:"+hitbox.x+"   Y:"+hitbox.y+" Width:"+hitbox.width+ " Height:"+hitbox.height );
				Rectangle cellRect = new Rectangle(rectX,rectY-this.getSpeed(),rectWidth,rectHeight);

				if(this.overlaps(cellRect))
				{
					return true;
				}
			}
		}

		return false;

	}



	@Override
	public void handleMovement() {


	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	@Override
	public boolean overlaps (Rectangle r) {
		return hitbox.getX()+this.getX() <= r.x + r.width 
				&& hitbox.getX() + hitbox.getWidth()+this.getX() >= r.x 
				&& hitbox.getY()+this.getY() <= r.y + r.height 
				&& hitbox.getY() + hitbox.getHeight()+this.getY() >= r.y;
	}
}



