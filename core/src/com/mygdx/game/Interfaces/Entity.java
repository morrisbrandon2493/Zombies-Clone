package com.mygdx.game.Interfaces;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
	private float x, y;
	private float width, height;
	private float originX, originY;
	private String entityID;
	private float xVelocity;
	private float yVelocity;

	private Texture texture;

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public Entity(Texture texture, String id) {
		this.texture = texture;
		width = texture.getWidth();
		height = texture.getHeight();
		this.setEntityID(id);
	}

	public String getEntityID() {
		return entityID;
	}

	public void setEntityID(String entityID) {
		this.entityID = entityID;
	}

	public void draw(Batch batch) {
		batch.draw(texture, x, y, width, height);
	}

	public Vector2 getVelocity() {
		return new Vector2(xVelocity, yVelocity);
	}

	public abstract void update();
	public abstract void handleMovement();

	public void setXVelocity(float x) {
		this.xVelocity = x;
	}

	public void setYVelocity(float y) {
		this.yVelocity = y;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public float getOriginX() {
		return originX;
	}

	public void setPosition(float x, float y) {

		this.x = x;
		this.y = y;
	}

	public float getOriginY() {
		return originY;
	}

	public float getHeight() {
		return height;
	}

	public void dispose() {

	}

	public void moveDirection(CardinalDirection dir) {

	}

}
