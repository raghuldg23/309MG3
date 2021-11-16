package com.mg3.screens;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.TimeUtils;
import com.mg3.game.AmountList;
import com.mg3.game.Buff_Collectable;
import com.mg3.game.Coin_Collectable;
import com.mg3.game.Enemy;
import com.mg3.game.GameState;
import com.mg3.game.Heart_Collectable;
import com.mg3.game.INVPopUp;
import com.mg3.game.Item;
import com.mg3.game.Laser;
import com.mg3.game.MG3game;
import com.mg3.game.Player;
import com.mg3.game.Shop;
import com.mg3.game.ShopPopUp;
import com.mg3.game.Wall;

public class MainGameScreen implements Screen {

	private OrthographicCamera camera, cameraMini;

	private SpriteBatch batch, batchMini, HUD, INV, SHP;

	private Player player;
	private Music BGM;
	private Texture playerImg,NPCImg,HeartImg,CoinImg,BuffImg,EnemyImg,bulletImg,bulletImg2,wallImg,floorIMG,winIMG,loseIMG;

	private Sound BulletSFX, DeadSFX, HeartSFX;

	
	private MG3game game;
	
	private BitmapFont playerHealth;
	private BitmapFont font;
	private BitmapFont HUDText;
	private BitmapFont INVText;
	private BitmapFont SHPText;
	int FPS;

	Socket socket;
	String data;
	String action;
	Json json;
	BufferedReader buffer;
	
	GameState state = new GameState();
	Array<Wall> walls = new Array<Wall>();

	long lastLaserTime;
	long gameStartTime;
	String equipment;
	
	boolean inShop = false;
	boolean inINV = false;
	boolean win = false;
	boolean lose = false;
	ShopPopUp shopPopUp = new ShopPopUp();
	INVPopUp invPopUp = new INVPopUp();
	
	Shop shop = new Shop();
	
	public MainGameScreen(MG3game game) {
		this.game = game;
	}
	
	
	@Override
	public void show() {
		playerImg = new Texture(Gdx.files.internal("player.png"));
				
		NPCImg = new Texture(Gdx.files.internal("NPC.jpg"));
		BuffImg = new Texture(Gdx.files.internal("buff.png"));
		CoinImg = new Texture(Gdx.files.internal("coin.png"));
		HeartImg = new Texture(Gdx.files.internal("heart.png"));
		EnemyImg= new  Texture(Gdx.files.internal("enemy.png"));
		bulletImg = new Texture(Gdx.files.internal("bullet.png"));
		bulletImg2 = new Texture(Gdx.files.internal("bullet2.png"));
		wallImg = new Texture(Gdx.files.internal("wall.png"));
		floorIMG = new Texture(Gdx.files.internal("field.jpg"));
		winIMG = new Texture(Gdx.files.internal("win.png"));
		loseIMG = new Texture(Gdx.files.internal("lose.png"));
		
		BGM = Gdx.audio.newMusic(Gdx.files.internal("Battle5.mp3"));	//test music pls ignore
		BulletSFX = Gdx.audio.newSound(Gdx.files.internal("ATTACK4.mp3"));
		DeadSFX = Gdx.audio.newSound(Gdx.files.internal("DEAD.mp3"));
		HeartSFX = Gdx.audio.newSound(Gdx.files.internal("1UP.mp3"));
		
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		
		cameraMini = new OrthographicCamera(160,90);
		//cameraMini.setToOrtho(false, 1280, 720);
		cameraMini.zoom = 200;
		
		batch = new SpriteBatch();
		batchMini = new SpriteBatch();
		HUD = new SpriteBatch();
		INV = new SpriteBatch();
		SHP = new SpriteBatch();
		json = new Json();
		playerHealth = new BitmapFont();
		HUDText = new BitmapFont();
		HUDText.setColor(Color.BLACK);
		INVText = new BitmapFont();
		INVText.setColor(Color.GREEN);
		SHPText = new BitmapFont();
		SHPText.setColor(Color.WHITE);
		font = new BitmapFont();
		
		genMap();
		
		/*
		 * Music Control Related Stuff here. HOLY SHIT THATS LOUD
		 */
		
		//BGM.setVolume(0.33f);
		BGM.setVolume(0.05f);
		BGM.setLooping(true);
		BGM.play();
		
		gameStartTime = TimeUtils.millis();
				
		//Sets playername and server ip based on what was entered on the welcome screen
		String name = game.playerName;
		String ipAddress = game.serverIP;
		

		
				
		//switch to UDP later b/c UDP works better for games due to how packets are sent. --Thiena
		try {
			socket = Gdx.net.newClientSocket(Protocol.TCP, ipAddress, 4444, null);
			//socket = Gdx.net.newClientSocket(Protocol.TCP, "10.25.69.40", 4444, null);	//this is the IASTATE IP.
				
		} catch (Exception e) {	
			System.out.println("\n Server was not found or you put dumb letters. \n");
			e.printStackTrace();
		}
		BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

		//When a client is connected the server creates a player with a unique player ID and sends it to the client
		try {
			player = json.fromJson(Player.class, buffer.readLine());
			player.setName(name);
		} catch(IOException e) {
			e.printStackTrace();
		}	
		
		data = json.toJson(player) + "\n";;
		try {	
			socket.getOutputStream().write("name\n".getBytes());
			socket.getOutputStream().flush();
			socket.getOutputStream().write((data+"\n").getBytes());
			socket.getOutputStream().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(float delta) {
//		Gdx.gl.glClearColor(1, 1, 1, 1);

		Gdx.gl.glClearColor(0.2f, 0.2f,0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		camera.position.set(player.getXPos()+32, player.getYPos()+32, 0);
		camera.update();

		batchMini.setProjectionMatrix(cameraMini.combined);
		cameraMini.position.set(-11600, 8800, 0);
		cameraMini.update();
		

		
		BufferedReader buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		//this.buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		
		//get player array from server and update player positions.
		data = "getArray\n";
		String msg = "error";
		try {	
			socket.getOutputStream().write((data+"\n").getBytes());
			socket.getOutputStream().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			msg = buffer.readLine();
		}catch(IOException e) {
			e.printStackTrace();
		}
		if(msg.equals("state")) {
			try {
				state = json.fromJson(GameState.class, buffer.readLine());
				for(int i = 0; i < state.getPlayers().size; i++) {
					Player tmp = state.getPlayers().get(i);
					if(tmp.getID() == player.getID()) {
						player = tmp;
					}
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		}else if(msg.equals("error")) {
			
		}else if(msg.equals("win")) {
			win = true;
			lose = false;
		}else if(msg.equals("lose")) {
			win = false;
			lose = true;
		}
		
		
		/**
		 * Draws the Screen/Players ETC
		 */
		
		batch.begin();
		
	

		batch.draw(floorIMG, 0, 0, 4000,4000);
		for (Wall wall : walls) {
			batch.draw(wallImg, wall.getXPos(), wall.getYPos(), wall.getWidth(), wall.getHeight());
		}
		for (Player player : state.getPlayers()) {
			if(player.isAlive()) {
				batch.draw(playerImg, player.getXPos(), player.getYPos());
				font.draw(batch, player.getName(), player.getXPos(), player.getYPos() + 97);
				font.draw(batch, player.getHealth() + player.getSheild() + "/100", player.getXPos(), player.getYPos() + 82);
			}
		}
		for (Laser laser : state.getLasers()) {
//			batch.draw(bulletImg, laser.getXPos(), laser.getYPos());
			batch.draw(bulletImg2, laser.getXPos(),laser.getYPos(),15,15,30,30,1,1,laser.getAngle(),0,0,30,30,false,false);
//			batch.draw(bulletImg, laser.getXPos(), laser.getYPos());
				
		}
		for (Enemy enemy : state.getEnemies()) {
			batch.draw(EnemyImg, enemy.getXPos(), enemy.getYPos(), 64, 64);
			font.draw(batch, enemy.getHealth() + "/100", enemy.getXPos(), enemy.getYPos() + 82);
		}
		for (Buff_Collectable buff : state.getBuffs()) {
			batch.draw(BuffImg, buff.getXPos(), buff.getYPos(), 30, 30);
		}
		for (Heart_Collectable heart : state.getHearts()) {

			batch.draw(HeartImg, heart.getXPos(), heart.getYPos(), 30, 30);
		}
		for (Coin_Collectable coin : state.getCoins()) {

			batch.draw(CoinImg, coin.getXPos(), coin.getYPos(), 30, 30);
		}
		
		batch.end();
		
		/**
		 * Heads up Display. FPS/PlayerList etc.
		 */
		
		HUD.begin();
		FPS = Gdx.graphics.getFramesPerSecond();
		font.draw(HUD, String.valueOf(FPS) + " FPS", 1200, 700);

		
		if(player.getHealth() <= 100) playerHealth.setColor(Color.BLACK);
		if(player.getHealth() <= 50) playerHealth.setColor(Color.ORANGE);
		if(player.getHealth() <= 30) playerHealth.setColor(Color.RED);
		if(player.getHealth() + player.getSheild() > 100) playerHealth.setColor(Color.BLUE);
		int playerHP = player.getHealth() + player.getSheild();
		playerHealth.draw(HUD, "HP: " + playerHP + "/100", 20, 700);
		HUDText.draw(HUD, "Scoreboard", 20, 660);
		
		if(TimeUtils.timeSinceMillis(player.getDeathTime()) < 16)
		{
			DeadSFX.play(0.03f);
		}
		/*
		 *Player List and Kill Count. 
		 */
		for (Player player : state.getPlayers()) {
			HUDText.draw(HUD, player.getName(), 20, 650 - player.getID() * 20);
			HUDText.draw(HUD, player.getKDA(), 135, 650 - player.getID() * 20);
		}
		HUDText.draw(HUD, state.returnNewsFeed(), 20, 20);
		
		
		
		/*
		 * Music Control Related Stuff here. HOLY SHIT THATS LOUD
		 */
		
		//BGM.setVolume(0.33f);
		BGM.setVolume(0.05f);
		BGM.setLooping(true);
		BGM.play();
		
		HUD.draw(CoinImg, 705, 8, 12, 12);
		HUDText.draw(HUD, "Coins:", 720, 20);
		HUDText.draw(HUD, Integer.toString(player.getCoins()), 760, 20);

		HUDText.draw(HUD, "Equipped:", 720, 60);
		if(player.equip == 0){
			equipment = "weapon";
		}
		else if(player.equip == 1){
			equipment = "armor";
		} 
		else if(player.equip == 2){
			equipment = "hybrid";
		} 
		else if(player.equip == 3){
			equipment = "nothing";
		} 
		HUDText.draw(HUD, equipment, 800, 60);
		
		
		if (this.player.isBuffed()) {
			try {
				// terrible ass formating but we'll worry about that later. 10
				// second duration.
				HUD.draw(BuffImg, 705, 28, 12, 12);
				String formatBuffTime = String.format("%3.3s",
						(Long.toString(10000 - (TimeUtils.timeSinceMillis(player.getBuffTime())))));
				HUDText.draw(HUD, "Buff | Speed Up! | " +

				formatBuffTime.substring(0, 1) + "." + formatBuffTime.substring(1, 2) + "s", 720, 40);
			} catch (StringIndexOutOfBoundsException e) {
				HUDText.draw(HUD, "Buff | Speed Up! | 0.0s", 720, 40);
			}
		}
		
		if(win) {
			HUD.draw(winIMG, 320, 260);
		}
		
		if(lose) {
			HUD.draw(loseIMG, 320, 260);
		}
		
		HUD.end();

		
			//minimap stuff.
		batchMini.begin();
		batchMini.setColor(1,1,1,0.5f);
		batchMini.draw(floorIMG, 0, 0, floorIMG.getWidth()*2, floorIMG.getHeight()*2);
		for (Wall wall : walls) {
			batchMini.draw(wallImg, wall.getXPos(), wall.getYPos(), wall.getWidth(), wall.getHeight());
		}
		for (Player player : state.getPlayers()) {
			if(player.isAlive()) {
				batchMini.draw(playerImg, player.getXPos(), player.getYPos(),128,128);
				//font.draw(batch, player.getName(), player.getXPos(), player.getYPos() + 97);
				//font.draw(batch, player.getHealth() + "/100", player.getXPos(), player.getYPos() + 82);
			}
		}
		batchMini.end();
		
	//buttons to equip
		
		
		if(inShop) {
			SHP.begin();
			SHP.draw(shopPopUp.getShopBG(), 20, 20);
			SHPText.draw(SHP, "Welcome to the Shop!", 50, 550);
			SHPText.draw(SHP, "Each Item is 25 Coins", 50, 530);
			SHP.draw(shopPopUp.getWeaponButton(), 50, 400);
			SHPText.draw(SHP, shop.getDescription(0), 50, 390);
			SHP.draw(shopPopUp.getAromorButton(), 50, 250);
			SHPText.draw(SHP, shop.getDescription(1), 50, 240);
			SHP.draw(shopPopUp.getHybridButton(), 50, 100);
			SHPText.draw(SHP, shop.getDescription(2), 50, 90);
			SHP.end();
		}


		if(inINV) {
			INV.begin();
			
			INV.draw(invPopUp.getINVBG(), 1000, 50);
			INVText.draw(INV, "Equipment", 1050, 500);
			INV.draw(invPopUp.getWeaponButton(), 1050, 400,100,100);
			INVText.draw(INV, Integer.toString(this.player.getInventoryCount(0)), 1150, 400);
			INV.draw(invPopUp.getAromorButton(), 1050, 250,100,100);
			INVText.draw(INV, Integer.toString(player.inventory.getAmount(1)), 1150, 250);
			INV.draw(invPopUp.getHybridButton(), 1050, 100,100,100);
			INVText.draw(INV, Integer.toString(player.inventory.getAmount(2)), 1150, 100);
			
			INV.end();
		}


		/*WASD is used for moving the player
		 *When a movement key is pressed the player object changes position
		 *The client then sends the player object to the server 
		 */
		float playerXPos = player.getXPos();
		float playerYPos = player.getYPos();
		boolean moved = false;
		Wall wall;
		if(TimeUtils.millis() - gameStartTime > 500 && player.isAlive() && !inShop && !inINV && !win && !lose) {
			if(Gdx.input.isKeyPressed(Input.Keys.A)) {
				player.setXPos(player.getXPos() - player.getSpeed() * Gdx.graphics.getDeltaTime());
				for(int i = 0; i < walls.size; i++) {
					wall = walls.get(i);
					if(player.getBody().overlaps(wall.getWall())) {
						player.setXPos(playerXPos);
						player.setYPos(playerYPos);
						moved = false;
					}else {
						moved = true;
					}
				}
			}
			if(Gdx.input.isKeyPressed(Input.Keys.D)) {
				player.setXPos(player.getXPos() + player.getSpeed() * Gdx.graphics.getDeltaTime());
				for(int i = 0; i < walls.size; i++) {
					wall = walls.get(i);
					if(player.getBody().overlaps(wall.getWall())) {
						player.setXPos(playerXPos);
						player.setYPos(playerYPos);
						moved = false;
					}else {
						moved = true;
					}
				}
			}
			if(Gdx.input.isKeyPressed(Input.Keys.W)) {
				player.setYPos(player.getYPos() + player.getSpeed() * Gdx.graphics.getDeltaTime());
				for(int i = 0; i < walls.size; i++) {
					wall = walls.get(i);
					if(player.getBody().overlaps(wall.getWall())) {
						player.setXPos(playerXPos);
						player.setYPos(playerYPos);
						moved = false;
					}else {
						moved = true;
					}
				}
			}
			if(Gdx.input.isKeyPressed(Input.Keys.S)) {
				player.setYPos(player.getYPos() - player.getSpeed() * Gdx.graphics.getDeltaTime());
				for(int i = 0; i < walls.size; i++) {
					wall = walls.get(i);
					if(player.getBody().overlaps(wall.getWall())) {
						player.setXPos(playerXPos);
						player.setYPos(playerYPos);
						moved = false;
					}else {
						moved = true;
					}
				}
			}
			
			
			if(Gdx.input.isButtonPressed(Buttons.LEFT) && TimeUtils.timeSinceMillis(lastLaserTime)  > 400 ) {

				BulletSFX.play(0.03f);
				Vector2 direction = new Vector2();
				direction.x = Gdx.input.getX() - (1280/2);
				direction.y = (720 - Gdx.input.getY()) - (720/2);
				data = json.toJson(direction) + "\n";
				try {
					socket.getOutputStream().write("laser\n".getBytes());
					socket.getOutputStream().flush();
					socket.getOutputStream().write((data+"\n").getBytes());
					socket.getOutputStream().flush();
				} catch(IOException e) {
					e.printStackTrace();
				}
				lastLaserTime = TimeUtils.millis();
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.Y)) {
			if(inShop) {
				inShop = false;
			}else {
				inShop = true;
			}
		}
		

		if(inShop) {
			if(Gdx.input.justTouched()) {
				if(Gdx.input.getX() > 50 && Gdx.input.getX() < 150) {
					if(720 - Gdx.input.getY() > 400 && 720 - Gdx.input.getY() < 500) {
						System.out.println("Weapon Button");
						shop.buyWeapon(player);
						moved = true;
					}
					if(720 - Gdx.input.getY() > 250 && 720 - Gdx.input.getY() < 350) {
						System.out.println("Armor Button");
						shop.buyArmor(player);
						moved = true;
					}
					if(720 - Gdx.input.getY() > 100 && 720 - Gdx.input.getY() < 200) {
						System.out.println("Hybrid Button");
						shop.buyHybrid(player);
						moved = true;
					}
				}
			}
		}
		
		//press I to call inventory
		if(Gdx.input.isKeyJustPressed(Input.Keys.I)) {
			if(inINV) {
				inINV = false;
			}else {
				inINV = true;
			}
		}
		
		if(inINV) {
			if(Gdx.input.justTouched()) {
				if(Gdx.input.getX() > 1050 && Gdx.input.getX() < 1150) {
					if(720 - Gdx.input.getY() > 400 && 720 - Gdx.input.getY() < 500) {
						
						System.out.println("Weapon Equip Button");
						player.equip(0);
						moved = true;
					}
					if(720 - Gdx.input.getY() > 250 && 720 - Gdx.input.getY() < 350) {
						System.out.println("Armor Equip Button");
						player.equip(1);
						moved = true;
					}
					if(720 - Gdx.input.getY() > 100 && 720 - Gdx.input.getY() < 200) {
						System.out.println("Hybrid Equip Button");
						player.equip(2);
						moved = true;
					}
				}
			}
		}
		
		
		//Escape key sends a signal to the server to disconnect and exits the app
		if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {		//actually works fine doesn't freeze.
			data = "quit\n";
			try {
				socket.getOutputStream().write((data+"\n").getBytes());
				socket.getOutputStream().flush();
			} catch(IOException e) {
				e.printStackTrace();
			}
			socket.dispose();
			Gdx.app.exit();
		}	
		
		if(moved) {
			data = json.toJson(player) + "\n";
			try {	
				socket.getOutputStream().write("move\n".getBytes());
				socket.getOutputStream().flush();
				socket.getOutputStream().write((data+"\n").getBytes());
				socket.getOutputStream().flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		playerImg.dispose();
		NPCImg.dispose();
		HeartImg.dispose();
		CoinImg.dispose();
		BuffImg.dispose();
		batch.dispose();		
	}
	
	//Builds walls as obstacles 
	private void genMap() {
		Wall wl;
		wl = new Wall(-100, -100, 4200, 100);
		walls.add(wl);
		wl = new Wall(0, -100, 100, 4000);
		walls.add(wl);
		wl = new Wall(0, 4000, 100, 4000);
		walls.add(wl);
		wl = new Wall(4000, -100, 4200, 100);
		walls.add(wl);
		wl = new Wall(400, 0, 400, 100);
		walls.add(wl);
		wl = new Wall(1000, 200, 700, 100);
		walls.add(wl);
		wl = new Wall(1500, 0, 1000, 100);
		walls.add(wl);
		wl = new Wall(2400, 0, 1000, 100);
		walls.add(wl);
		wl = new Wall(2900, 200, 700, 100);
		walls.add(wl);
		wl = new Wall(3500, 0, 400, 100);
		walls.add(wl);
		wl = new Wall(900, 1000, 300, 100);
		walls.add(wl);
		wl = new Wall(3000, 1000, 300, 100);
		walls.add(wl);
		wl = new Wall(550, 1600, 300, 100);
		walls.add(wl);
		wl = new Wall(1700, 1700, 200, 100);
		walls.add(wl);
		wl = new Wall(2200, 1700, 200, 100);
		walls.add(wl);
		wl = new Wall(3350, 1600, 300, 100);
		walls.add(wl);
		wl = new Wall(550, 2100, 300, 100);
		walls.add(wl);
		wl = new Wall(1700, 2100, 200, 100);
		walls.add(wl);
		wl = new Wall(2200, 2100, 200, 100);
		walls.add(wl);
		wl = new Wall(3350, 2100, 300, 100);
		walls.add(wl);
		wl = new Wall(900, 2700, 300, 100);
		walls.add(wl);
		wl = new Wall(3000, 2700, 300, 100);
		walls.add(wl);
		wl = new Wall(1000, 3100, 700, 100);
		walls.add(wl);
		wl = new Wall(1500, 3000, 1000, 100);
		walls.add(wl);
		wl = new Wall(2400, 3000, 1000, 100);
		walls.add(wl);
		wl = new Wall(2900, 3100, 700, 100);
		walls.add(wl);
		wl = new Wall(400, 3600, 400, 100);
		walls.add(wl);
		wl = new Wall(3500, 3600, 400, 100);
		walls.add(wl);
		wl = new Wall(0, 600, 100, 800);
		walls.add(wl);
		wl = new Wall(1600, 550, 100, 300);
		walls.add(wl);
		wl = new Wall(2100, 550, 100, 300);
		walls.add(wl);
		wl = new Wall(3200, 600, 100, 800);
		walls.add(wl);
		wl = new Wall(0, 900, 100, 300);
		walls.add(wl);
		wl = new Wall(400, 900, 100, 900);
		walls.add(wl);
		wl = new Wall(2700, 900, 100, 900);
		walls.add(wl);
		wl = new Wall(3700, 900, 100, 300);
		walls.add(wl);
		wl = new Wall(1500, 1200, 100, 1000);
		walls.add(wl);
		wl = new Wall(0, 1500, 100, 1000);
		walls.add(wl);
		wl = new Wall(1700, 1600, 100, 600);
		walls.add(wl);
		wl = new Wall(3000, 1500, 100, 1000);
		walls.add(wl);
		wl = new Wall(0, 2400, 100, 1000);
		walls.add(wl);
		wl = new Wall(1700, 2300, 100, 600);
		walls.add(wl);
		wl = new Wall(3000, 2400, 100, 1000);
		walls.add(wl);
		wl = new Wall(1500, 2700, 100, 1000);
		walls.add(wl);
		wl = new Wall(0, 3000, 100, 300);
		walls.add(wl);
		wl = new Wall(400, 3000, 100, 900);
		walls.add(wl);
		wl = new Wall(2700, 3000, 100, 900);
		walls.add(wl);
		wl = new Wall(3700, 3000, 100, 300);
		walls.add(wl);
		wl = new Wall(0, 3300, 100, 800);
		walls.add(wl);
		wl = new Wall(1600, 3350, 100, 300);
		walls.add(wl);
		wl = new Wall(2100, 3350, 100, 300);
		walls.add(wl);
		wl = new Wall(3200, 3300, 100, 800);
		walls.add(wl);
	}

}
