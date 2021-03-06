package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.mygdx.game.Entities.Entity;
import com.mygdx.game.Entities.Player;


public class GameScreen  implements Screen, InputProcessor
{
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private SpriteBatch hud;

	private Player player;
	private List<Entity> entityList = new ArrayList<Entity>();
	private TiledMapTileLayer collisionObjectLayer ;
	private ShapeRenderer shapeRenderer;
	private BitmapFont font = new BitmapFont(); 



	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		this.doGameTick();
		player.update();

		//HitBox tmpRect = player.getHitbox();

		camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
		camera.update();

		renderer.setView(camera);
		shapeRenderer.setProjectionMatrix(camera.combined);


		renderer.render(new int[] {0});		
		
		
		/*
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.rect(tmpRect.x,tmpRect.y,tmpRect.width,tmpRect.height);
		shapeRenderer.end();
		*/

		
		renderer.getBatch().begin();
		player.draw(renderer.getBatch());
		
		renderer.getBatch().end();
		renderer.render(new int[] {1});


		
		//renderer.render(new int[] {3});


		hud.begin();
		font.draw(hud, "FPS:"+Gdx.graphics.getFramesPerSecond(),10,100);
		font.draw(hud, "Coords:"+(int)Math.ceil(player.getX()/collisionObjectLayer.getTileWidth())+":"+(int)Math.ceil(player.getY()/collisionObjectLayer.getTileHeight()), 10, 20);
		font.draw(hud, "Tile Size:"+collisionObjectLayer.getTileWidth()+":"+collisionObjectLayer.getTileHeight(), 10, 60);
		font.draw(hud, "X:"+(int)player.getX()+" Y:"+(int)player.getY() , 10, 40);
		hud.end();
		
	}
	public void doGameTick() 
	{
		for(Entity e: entityList)
		{
			if(e instanceof Player)
			{
				((Player) e).checkMapCollisions(collisionObjectLayer);
			}

		}

	}
	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width/2f;
		camera.viewportHeight = height/2f;
	}

	@Override
	public void show() {

		map = new TmxMapLoader().load("testroom.tmx");
		collisionObjectLayer = (TiledMapTileLayer)map.getLayers().get("Collision");

		renderer 		= new OrthogonalTiledMapRenderer(map);
		camera 			= new OrthographicCamera();
		hud 			= new SpriteBatch();
		shapeRenderer 	= new ShapeRenderer();
		shapeRenderer.setColor(Color.RED);

		player = new Player("player1","pik");
		player.setPosition(136,60);

		entityList.add(player);

		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		map.dispose();
		renderer.dispose();
		player.dispose();
	}



	@Override
	public boolean keyDown(int keycode) {

		switch(keycode) {
		case Keys.A:
			player.press(keycode);
			break;
		case Keys.D:
			player.press(keycode);
			break;
		case Keys.W:
			player.press(keycode);
			break;
		case Keys.S:
			player.press(keycode);
			break;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch(keycode) {
		case Keys.A:
			player.release(keycode);
			break;
		case Keys.D:
			player.release(keycode);
			break;
		case Keys.W:
			player.release(keycode);
			break;
		case Keys.S:
			player.release(keycode);
			break;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
/*
	public GameScreen()
	{


	}

	public void init()
	{


		System.out.println("init");
		spriteBatch = new SpriteBatch();

		player = new Player("player1","pik");
		entityList.add(player);

		map = new TmxMapLoader().load("testroom.tmx");

		renderer = new OrthogonalTiledMapRenderer(map);

		camera = new OrthographicCamera();


		player.setPos(100,100);

	}

	public void draw() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.position.set(player.getPos().x+32, player.getPos().y + 32, 0);
		camera.update();
		System.out.println("draw");
		renderer.setView(camera);

		renderer.render(new int[] {0});

		camera.position.set(player.getPos().x,player.getPos().y,0);
		camera.update();


		renderer.getBatch().begin();
		for (Entity e: entityList) {
			if(e instanceof Player)
			{
				player.draw(renderer.getBatch());

			}
		}
		renderer.getBatch().end();
		renderer.render(new int[] {1});

	}
	public void update()
	{

	}

	public void dispose() {
		map.dispose();
		renderer.dispose();

	}

	public void keyUp(int keycode) {
		player.setPos(new Vector2(player.getPos().x+5,player.getPos().y));
	}
	public void keyDown(int keycode) {

	}
	public void keyTyped(char character) {

	}
	public void touchDown(int screenX, int screenY, int pointer, int button) {

		float posY = Gdx.graphics.getHeight() - screenY;

		player.setPos(new Vector2(0,0));

	}

	public void touchUp(int screenX, int screenY, int pointer, int button) {

	}

	public void touchDragged(int screenX, int screenY, int pointer) {

	}


	public void mouseMoved(int screenX, int screenY) {

	}


	public void scrolled(int amount) {

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

}
 */