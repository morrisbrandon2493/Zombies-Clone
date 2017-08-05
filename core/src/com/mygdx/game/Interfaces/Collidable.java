package com.mygdx.game.Interfaces;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;

public interface Collidable
{
	public void checkMapCollisions(TiledMapTileLayer collisionObjectLayer);
	boolean overlaps(Rectangle r);
}
