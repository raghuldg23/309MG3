package com.mg3.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.mg3.game.Item;


public class Player  {
	
	ItemList iList = new ItemList();
	public AmountList inventory = new AmountList();
	
	//-1 means no item equipped
	int	 weaponId = -1;
	int	 armorId = -1;
	int hybridId = -1;
	
	Rectangle playerBody = new Rectangle();
	
	int id = 0;
	
	int score = 0;
	double killCount = 0;
	int deathCount = 0;
	
	int health = 100;
	int damage = 10;
	int shield = 0;
	int coinTotal = 100;
	public int equip = 3;
	
	String name = "";
	
	float speed = 300f;
	long lastLaserTime, deathTime, buffTime;
	boolean alive = true;
	boolean buffed = false;
	
	Array<Vector2> spawnPoints;
	
	public Player() {
		this.playerBody.width = 64;
		this.playerBody.height = 64;
		
		spawnPoints = new Array<Vector2>();
		
		Vector2 v = new Vector2();
		v.x = 20;
		v.y = 20;
		spawnPoints.add(v);
		v = new Vector2();
		v.x = 2000;
		v.y = 20;
		spawnPoints.add(v);
		v = new Vector2();
		v.x = 3900;
		v.y = 20;
		spawnPoints.add(v);
		v = new Vector2();
		v.x = 20;
		v.y = 2000;
		spawnPoints.add(v);
		v = new Vector2();
		v.x = 3900;
		v.y = 2000;
		spawnPoints.add(v);
		v = new Vector2();
		v.x = 20;
		v.y = 3900;
		spawnPoints.add(v);
		v = new Vector2();
		v.x = 2000;
		v.y = 3900;
		spawnPoints.add(v);
		v = new Vector2();
		v.x = 3900;
		v.y = 3900;
		spawnPoints.add(v);
		
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public void setXPos(float x) {
		this.playerBody.x = x;
	}
	
	public void setYPos(float y) {
		this.playerBody.y = y;
	}
	
	public void setSpeed(float s) {
		this.speed = s;
	}
	
	public void setHealth(int h) {
		this.health = h;
	}
	
	public void setScore(int s) {
		this.score = s;
	}
	
	public void setName(String s) {
		this.name = s;
	}
	
	public void setCoins(int n) {
		this.coinTotal = n;
	}
	
	public void setInventory(AmountList i) {
		this.inventory = i;
	}
	
	public void setDamage(int d) {
		this.damage = d;
	}
	
	public void setSheild(int s) {
		this.shield = s;
	}
	
	public int getID() {
		return this.id;
	}
	
	public float getXPos() {
		return this.playerBody.x;
	}
	
	public float getYPos() {
		return this.playerBody.y;
	}
	
	public float getSpeed() {
		return this.speed;
	}
	
	public int getHealth() {
		return this.health;
	}
	
	public Rectangle getBody() {
		return this.playerBody;
	}
	
	public String getName() {
		return this.name;
	}
	
	public long getDeathTime() {
		return this.deathTime;
	}
	
	public long getBuffTime() {
		return this.buffTime;
	}
	
	public int getDamage() {
		return this.damage;
	}
	
	public AmountList getInventory() {
		return this.inventory;
	}
	
	public int getSheild() {
		return this.shield;
	}
	
	public double getKillCount() {
		return this.killCount;
	}
	
	public boolean isAlive() {
		return this.alive;
	}
	
	public boolean isBuffed() {
		return this.buffed;
	}
	
	public void playerRespawn() {
		this.health = 100;
		this.alive = true;
		this.speed = 300f;
		Vector2 spawn = spawnPoints.random();
		this.getBody().x = spawn.x;
		this.getBody().y = spawn.y;
	}
	
	public void playerSpeedBuff() {
		this.speed = this.speed * 2;
		this.buffed = true;
		this.buffTime = TimeUtils.millis();
	}
	
	public void removeBuff() {
		this.speed = this.speed / 2;
		this.buffed = false;
	}
	
	public void playerHealthPickup() {
		this.health = 100;
	}
	
	public void playerItemPickup(int id) {
		inventory.addItem(id);
	}
	
	public void playerCoinTotal(){
		this.coinTotal = this.coinTotal + 1;
	}
	
	public void equip(int id){
		//if nothing is equipped it equips the item
		if(equip == 3 && inventory.getAmount(id) != 0){
			this.damage += iList.getItem(id).effects[0];
			this.shield += iList.getItem(id).effects[1];
			equip = id;
			inventory.deleteItem(id);
		}
		//if an item is already equip it replaces the item with a different one
		else if (equip != 3 && inventory.getAmount(id) != 0){
			this.damage = 10;
			this.shield = 0;
			this.damage += iList.getItem(id).effects[0];
			this.shield += iList.getItem(id).effects[1];
			equip = id;
			inventory.deleteItem(id);
		}
		//when a player dies the damage and shield are set to default
		else if(id == 3){
			this.damage = 10;
			this.shield = 0;
			equip = id;
		}
	}
	
	public void unequip(int id, int itemType) {
		
		if(itemType == 1) {
			weaponId = -1;
		}else if(itemType == 2) {
			armorId = -1;
		}else if(itemType == 3) {
			hybridId = -1;
		}
		inventory.addItem(id);
		this.damage -= iList.getItem(id).effects[0];
		this.shield -= iList.getItem(id).effects[1];
	}

	public void playerDied(){
		deathCount++;
		alive = false;
		equip(3);
		deathTime = TimeUtils.millis();
	}
	public void getKill()
	{
		killCount++;
	}
	public void getEnemyKill()
	{
		killCount = killCount + 0.25;
	}
	
	public String getKDA()
	{
		return killCount + "/" + deathCount;
	}
	public int getCoins()
	{
		return coinTotal;
	}
	public long buffTime()
	{
		return buffTime;
	}
	public int getInventoryCount(int id)
	{
		return inventory.getAmount(id);
	}
	
}

