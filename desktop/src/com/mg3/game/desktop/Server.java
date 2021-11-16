package com.mg3.game.desktop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.backends.lwjgl.LwjglNet;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.SerializationException;
import com.badlogic.gdx.utils.TimeUtils;
import com.mg3.game.Buff_Collectable;
import com.mg3.game.Coin_Collectable;
import com.mg3.game.Enemy;
import com.mg3.game.GameState;
import com.mg3.game.Heart_Collectable;
import com.mg3.game.Laser;
import com.mg3.game.Player;
import com.mg3.game.Item;
import com.mg3.game.Wall;


public class Server {
	
	
	public static void main(String[] args) {
		
		int playerNumber;
		ServerSocketHints hints = new ServerSocketHints();
		hints.acceptTimeout = 0;
		ServerSocket server = new LwjglNet().newServerSocket(Protocol.TCP, 4444, hints);
		System.out.println("Server Started, waiting for Client");
		playerNumber = 0;
		GameState state = new GameState();
		Player p;
		String data;
		Json json = new Json();
		
		Thread stateHandler = new Thread(new StateHandler(state));
		stateHandler.start();
		//loop waiting for clients to connect
		while(true) {
			Socket socket = null;
			//when a client connects to the server create a new player and add them to the players ArrayList
			socket = server.accept(null);
			playerNumber++;
			p = new Player();
			p.setID(playerNumber);
			p.playerRespawn();
			state.addPlayer(p);
			System.out.println(p.getID());
			System.out.println("Player " + playerNumber + " Connected to server");
			
			//serialize the player object and send it to the client
			data = json.toJson(p) + "\n";
			try {
				socket.getOutputStream().write(data.getBytes());
				socket.getOutputStream().flush();
			} catch(IOException e) {
				e.printStackTrace();
			}
			//Start a new thread to handle client interaction
			Thread t = new Thread(new ClientHandler(socket, playerNumber, state));
			
			t.start();
		}
	}
	
}

class StateHandler implements Runnable {
	GameState state;
	Array<Wall> walls = new Array<Wall>();
	Wall wl, wall;
	Player pl, player;
	Laser laser;
	Enemy en, enemy;
	Buff_Collectable bf, buff;
	Heart_Collectable ht, heart;
	Coin_Collectable cn, coin;
	Item item;
	int i, j, k;
	Vector2 dir = new Vector2();
	Random rand = new Random();
	boolean spawnEnemy = false;
	
	String NewsFeed = "";
	
	StateHandler(GameState state){
		this.state = state;
		
		
		
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
	
	@Override
	public void run() {	
		
		long millis = TimeUtils.millis();
		while(true) {
			
			while(state.getEnemies().size < 6) {
				spawnEnemy = true;
				en = new Enemy(rand.nextInt(4000), rand.nextInt(4000));
				for(i = 0; i < walls.size; i++) {
					wall = walls.get(i);
					if(en.getBody().overlaps(wall.getWall())) {
						spawnEnemy = false;
					}
				}
				if(spawnEnemy) {
					state.addEnemy(en);
					System.out.println("Enemy Spawned");
				}
			}
			while(state.getHearts().size < 6) {
				spawnEnemy = true;
				ht = new Heart_Collectable(rand.nextInt(4000), rand.nextInt(4000));
				for(i = 0; i < walls.size; i++) {
					wall = walls.get(i);
					if(ht.getBody().overlaps(wall.getWall())) {
						spawnEnemy = false;
					}
				}
				if(spawnEnemy) {
					state.addHeart(ht);
					System.out.println("Heart Spawned");
				}
			}
			
			while(state.getBuffs().size < 6) {
				spawnEnemy = true;
				bf = new Buff_Collectable(rand.nextInt(4000), rand.nextInt(4000));
				for(i = 0; i < walls.size; i++) {
					wall = walls.get(i);
					if(bf.getBody().overlaps(wall.getWall())) {
						spawnEnemy = false;
					}
				}
				if(spawnEnemy) {
					state.addBuff(bf);
					System.out.println("Buff Spawned");
				}
			}
			
			
			if(TimeUtils.millis() - millis > 16.66) {
				for(i = 0; i < state.getPlayers().size; i++) {
					player = state.getPlayers().get(i);
					if(!player.isAlive()) {
						if(TimeUtils.timeSinceMillis(player.getDeathTime()) > 5000) {
							player.playerRespawn();
						}
					}
					if(player.isBuffed() && TimeUtils.timeSinceMillis(player.getBuffTime()) > 10000) {
						player.removeBuff();
					}
				}
				
				
				//moves bullets and detects if they hit another player, walls, or enemies
				for(i = 0; i < state.getLasers().size; i++) {
					laser = state.getLasers().get(i);
					laser.move(1/60f);
					//removes a bullet 600 milliseconds after it is fired
					if(TimeUtils.millis() - laser.getTimeSpawned() > 600) {
						state.getLasers().removeValue(laser, true);
					}
					//detect if a bullet hits a wall
					for(k = 0; k < walls.size; k++) {
						wall = walls.get(k);
						if(laser.getBody().overlaps(wall.getWall())) {
							state.getLasers().removeValue(laser, true);
						}
					}
					//detect if a bullet hits a player
					for(k = 0; k < state.getPlayers().size; k++) {
						player = state.getPlayers().get(k);
						if(laser.getBody().overlaps(player.getBody()) && laser.getID() != player.getID() && player.isAlive()) {
							if(player.getSheild() > 0) {
								player.setSheild(player.getSheild() - laser.getDamage());
								state.getLasers().removeValue(laser, true);
							}else {
								player.setHealth(player.getHealth() - laser.getDamage());
								state.getLasers().removeValue(laser, true);
								if(player.getHealth() <= 0) {
									if(laser.getID() != 0) {
										for(j = 0; j < state.getPlayers().size; j++) {
											pl = state.getPlayers().get(j);
											if(pl.getID() == laser.getID()) {
												break;
											}
										}
										System.out.println("Player " + pl.getID() + " Killed Player " + player.getID());
										this.NewsFeed = pl.getName() + " has killed " + player.getName() + "!";
										state.setNewsFeed(NewsFeed);
										pl.getKill();
										player.playerDied();
										spawnCoins(true,player.getXPos(),player.getYPos());
									}else {
										System.out.println("Player " + player.getID() + " Killed by Enemy");
										this.NewsFeed = "An Enemy has killed " + player.getName() + "!";
										state.setNewsFeed(NewsFeed);
										player.playerDied();
									}							
								}
							}
						}
					}
					//detect if a bullet hits an enemy
					for(k = 0; k < state.getEnemies().size; k++) {
						enemy = state.getEnemies().get(k);
						if(laser.getBody().overlaps(enemy.getBody()) && laser.getID() != 0) {
							enemy.setHealth(enemy.getHealth() - laser.getDamage());
							state.getLasers().removeValue(laser, true);
							if(enemy.getHealth() <= 0) {
								for(j = 0; j < state.getPlayers().size; j++) {
									pl = state.getPlayers().get(j);
									if(pl.getID() == laser.getID()) {
										break;
									}
								}
								pl.getEnemyKill();
								spawnCoins(false,enemy.getXPos(), enemy.getYPos());
								state.removeEnemy(enemy);
								this.NewsFeed = pl.getName() + "has slain an NPC!";
							}
						}
					}
				}
				//detect if a player has picked up a buff
				for(i = 0; i < state.getPlayers().size; i++) {
					player = state.getPlayers().get(i);
					//newbuff
					if(player.isAlive() && !player.isBuffed()) {
						for(k = 0; k < state.getBuffs().size; k++) {
							 buff = state.getBuffs().get(k);
							 if(player.getBody().overlaps(buff.getBody())) {
								 player.playerSpeedBuff();
								 state.removeBuff(buff);
							 }
						 }
					}
					//refreshes
					if(player.isAlive() && player.isBuffed())
					{
						for(k = 0; k < state.getBuffs().size; k++) {
							 buff = state.getBuffs().get(k);
							 if(player.getBody().overlaps(buff.getBody())) {
								 player.removeBuff();
								 player.playerSpeedBuff();
								 state.removeBuff(buff);
							 }
						 }
					}
				}
				//detect if a player has picked up a heart
				for(i = 0; i < state.getPlayers().size; i++) {
					player = state.getPlayers().get(i);
					if(player.isAlive()) {
						for(k = 0; k <state.getHearts().size; k++) {
							 heart = state.getHearts().get(k);
							 if(player.getBody().overlaps(heart.getBody())) {
								 player.playerHealthPickup();
								 state.removeHeart(heart);
							 }
						 }
					} 
				}
				//detect if a player has picked up a coin
				for(i = 0; i < state.getPlayers().size; i++) {
					player = state.getPlayers().get(i);
					if(player.isAlive()) {
						 for(k = 0; k <state.getCoins().size; k++) {
							 coin = state.getCoins().get(k);
							 if(player.getBody().overlaps(coin.getBody())) {
								 player.playerCoinTotal();
								 state.removeCoin(coin);
							 }
						 }
					}	
				}
				//detect if a player has picked up a equipment
				for(i = 0; i < state.getPlayers().size; i++) {
					player = state.getPlayers().get(i);
					 for(k = 0; k < state.getItems().size; k++) {
						 item = state.getItems().get(k);
						 if(player.getBody().overlaps(item.getBody())) {
							 player.playerItemPickup(item.getId());
							 state.removeItem(item);
						 }
					 }
				}
				//detect if a player has entered an enemy's detection radius
				for(i = 0; i < state.getEnemies().size; i++) {
					enemy = state.getEnemies().get(i);
					if(enemy.getTarget().getID() == -1) {
						for(k = 0; k < state.getPlayers().size; k++) {
							player = state.getPlayers().get(k);
							if(enemy.getDetection().contains(player.getXPos(), player.getYPos()) && player.isAlive()){
								enemy.setTarget(player);
								System.out.println("Player " + enemy.getTarget().getID() + " targeted");
							}
						}
					}else{
						for(k = 0; k < state.getPlayers().size; k++) {
							player = state.getPlayers().get(k);
							if(player.getID() == enemy.getTarget().getID()) {
								if(!enemy.getDetection().contains(player.getXPos(), player.getYPos()) || !player.isAlive()) {
									enemy.setNoTarget();
									System.out.println("Enemy lost target");
								}else if(TimeUtils.timeSinceMillis(enemy.getTimer()) > 800) {
									dir.x = player.getXPos() - enemy.getXPos();
									dir.y = player.getYPos() - enemy.getYPos();
									laser = new Laser();
									laser.setID(0);
									laser.setDamage(10);
									laser.setMoveDirection(dir);
									laser.setXPos(enemy.getXPos() + 17);
									laser.setYPos(enemy.getYPos() + 17);
									state.addLaser(laser);
									enemy.setTimer();
								}
							}
						}
					}	
				}
				
				millis = TimeUtils.millis();
				//MOVE THIS ELSEWHERE LOCALHOST TECHNICALLY MAKES THIS ZERO.
			}
		}
	}
	
	private void spawnCoins(boolean player, float x, float y) {
		int numCoins = 0;
		boolean spawnCoin = false;
		Coin_Collectable cn;
		if(player) {
			while(numCoins < 10) {
				spawnCoin = true;
				cn = new Coin_Collectable(x - 50 + rand.nextInt(100), y - 50 + rand.nextInt(100));
				for(i = 0; i < walls.size; i++) {
					wall = walls.get(i);
					if(cn.getBody().overlaps(wall.getWall())) {
						spawnCoin = false;
					}
				}
				if(spawnCoin) {
					state.addCoin(cn);
					numCoins++;
					System.out.println("Coin Spawned");
				}
			}
		}else{
			while(numCoins < 5) {
				spawnCoin = true;
				cn = new Coin_Collectable(x - 50 + rand.nextInt(100), y - 50 + rand.nextInt(100));
				for(i = 0; i < walls.size; i++) {
					wall = walls.get(i);
					if(cn.getBody().overlaps(wall.getWall())) {
						spawnCoin = false;
					}
				}
				if(spawnCoin) {
					state.addCoin(cn);
					numCoins++;
					System.out.println("Coin Spawned");
				}
			}
		}
	}
}

class ClientHandler implements Runnable {
	Socket s;
	int playerID, i;
	private Boolean stop = false;
	GameState state;
	Player pl, player;
	String data;
	String in;
	Json json = new Json();
	
	
	ClientHandler(Socket socket, int playerID, GameState state){
		this.s = socket;
		this.playerID = playerID;
		this.state = state;	
	}
	
	public void run() {
		System.out.println("Client Handler Thread Started");
		BufferedReader buffer = new BufferedReader(new InputStreamReader(s.getInputStream()));

		while(!stop) {			
			//get action from client
			try {
				in = buffer.readLine();
			} catch(IOException e) {
				e.printStackTrace();
				for(Player player : state.getPlayers()) {
					if(pl.getID() == player.getID()) {
						state.removePlayer(player);
					}
				}
				s.dispose();
				stop = true;
			}

			//getArray action sends the server's player array to the client
			boolean gameOver = false;
			if(in.equals("getArray")) {	
				GameState gs = copyGameState(state);
				for(i = 0; i < gs.getPlayers().size; i++) {
					player = gs.getPlayers().get(i);
					if(player.getKillCount() >= 5) {
						gameOver = true;
						break;
					}
				}
				if(!gameOver) {
					try {
						data = json.toJson(gs) + "\n";
						try {
							s.getOutputStream().write("state\n".getBytes());
							s.getOutputStream().flush();
							s.getOutputStream().write(data.getBytes());
							s.getOutputStream().flush();
						} catch(IOException e) {
							e.printStackTrace();
						}
					} catch (SerializationException se) {
						System.out.println("Serialization Exception caught");
						try {
							s.getOutputStream().write("error\n".getBytes());
							s.getOutputStream().flush();
						} catch(IOException e) {
							e.printStackTrace();
						}
					}
				}else {
					if(player.getID() == pl.getID()) {
						try {
							s.getOutputStream().write("win\n".getBytes());
							s.getOutputStream().flush();
						} catch(IOException e) {
							e.printStackTrace();
						}
					}else {
						try {
							s.getOutputStream().write("lose\n".getBytes());
							s.getOutputStream().flush();
						} catch(IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
			//move action takes player object sent from the client and replaces the player with the same id in the server's array
			if(in.equals("move")) {
				try {
					in = buffer.readLine();
				} catch(IOException e) {
					e.printStackTrace();
				} 
				pl = json.fromJson(Player.class, in);
				for(Player player : state.getPlayers()) {
					if(pl.getID() == player.getID()) {
						//int index = state.getPlayers().indexOf(player, true);
						player.setXPos(pl.getXPos());
						player.setYPos(pl.getYPos());
						player.setCoins(pl.getCoins());
						player.setInventory(pl.getInventory());
						player.setDamage(pl.getDamage());
						player.setSheild(pl.getSheild());
						player.equip = pl.equip;
						break;
					}
				}					
			}
			
			//move action takes player object sent from the client and replaces the player with the same id in the server's array
			if(in.equals("name")) {
				try {
					in = buffer.readLine();
				} catch(IOException e) {
					e.printStackTrace();
				} 
				pl = json.fromJson(Player.class, in);
				for(Player player : state.getPlayers()) {
					if(pl.getID() == player.getID()) {
						player.setName(pl.getName());
						state.setNewsFeed(pl.getName() + " has joined the Game!");
						break;
					}
				}					
			}
			
			if(in.equals("laser")) {
				try {
					in = buffer.readLine();
				} catch(IOException e) {
					e.printStackTrace();
				} 			
				Vector2 direction = json.fromJson(Vector2.class, in);
				spawnLaser(direction);
			}
			
			//quit action removes the player from the array and exits the client handler thread
			if(in.equals("quit")) {
				System.out.println("Client" + " Disconnected from Server");
				for(Player player : state.getPlayers()) {
					if(pl.getID() == player.getID()) {
						state.removePlayer(player);
					}
				}
				state.setNewsFeed(pl.getName() + " has left the Game!");
				s.dispose();
				this.stop = true;
			}
		}
	}
	
	private void spawnLaser(Vector2 dir) {
		for(i = 0; i < state.getPlayers().size; i++) {
			player = state.getPlayers().get(i);
			if(pl.getID() == player.getID()) {
				Laser laser = new Laser();
				laser.setID(player.getID());
				laser.setMoveDirection(dir);
				laser.setXPos(player.getXPos() + 17);
				laser.setYPos(player.getYPos() + 17);
				laser.setDamage(pl.getDamage());
				state.addLaser(laser);
				break;
			}
		}	
	}
	
	public GameState copyGameState(GameState gs) {
		GameState g = new GameState();
		g.setPlayers(gs.getPlayers());
		g.setLasers(gs.getLasers());
		g.setEnemies(gs.getEnemies());
		g.setItems(gs.getItems());
		g.setBuffs(gs.getBuffs());
		g.setHearts(gs.getHearts());
		g.setCoins(gs.getCoins());
		g.setNewsFeed(gs.returnNewsFeed());
		return g;
	}
}

