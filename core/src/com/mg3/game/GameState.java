package com.mg3.game;

import com.badlogic.gdx.utils.Array;

public class GameState {

	Array<Player> players;
	Array<Laser> lasers;
	Array<Enemy> enemies;
	Array<Item> items;
	Array<Coin_Collectable> coins;
	Array<Buff_Collectable> buffs;
	Array<Heart_Collectable> hearts;
	String NewsFeed;
	
	public GameState() {
		this.players = new Array<Player>();
		this.lasers = new Array<Laser>();
		this.enemies = new Array<Enemy>();
		this.items = new Array<Item>();
		this.buffs = new Array<Buff_Collectable>();
		this.hearts = new Array<Heart_Collectable>();
		this.coins = new Array<Coin_Collectable>();
		NewsFeed = "";
	}
	
	public void setPlayers(Array<Player> a) {
		this.players = a;
	}
	
	public void setLasers(Array<Laser> a) {
		this.lasers = a;
	}
	
	public void setEnemies(Array<Enemy> a) {
		this.enemies = a;
	}
	
	public void setItems(Array<Item> a) {
		this.items = a;
	}
	
	public void setBuffs(Array<Buff_Collectable> a) {
		this.buffs = a;
	}
	
	public void setHearts(Array<Heart_Collectable> a) {
		this.hearts = a;
	}
	
	public void setCoins(Array<Coin_Collectable> a) {
		this.coins = a;
	}
	
	public Array<Player> getPlayers() {
		return this.players;
	}
	
	public Array<Laser> getLasers() {
		return this.lasers;
	}
	
	public Array<Enemy> getEnemies() {
		return this.enemies;
	}
	
	public Array<Buff_Collectable> getBuffs() {
		return this.buffs;
	}
	
	public Array<Item> getItems() {
		return this.items;
	}
	
	public Array<Heart_Collectable> getHearts() {
		return this.hearts;
	}
	
	public Array<Coin_Collectable> getCoins() {
		return this.coins;
	}
	
	public void addPlayer(Player p) {
		this.players.add(p);
	}
	
	public void addLaser(Laser l) {
		this.lasers.add(l);
	}
	
	public void addEnemy(Enemy e) {
		this.enemies.add(e);
	}
	
	public void addItem(Item o) {
		this.items.add(o);
	}
	
	public void addBuff(Buff_Collectable b) {
		this.buffs.add(b);
	}
	
	public void addHeart(Heart_Collectable h) {
		this.hearts.add(h);
	}
	
	public void addCoin(Coin_Collectable c) {
		this.coins.add(c);
	}
	
	public void removePlayer(Player p) {
		this.players.removeValue(p, true);
	}
	
	public void removeLaser(Laser l) {
		this.lasers.removeValue(l, true);
	}
	
	public void removeEnemy(Enemy e) {
		this.enemies.removeValue(e, true);
	}
	
	public void removeItem(Item o) {
		this.items.removeValue(o, true);
	}
	
	public void removeBuff(Buff_Collectable b) {
		this.buffs.removeValue(b, true);
	}
	
	public void removeHeart(Heart_Collectable h) {
		this.hearts.removeValue(h, true);
	}
	
	public void removeCoin(Coin_Collectable c) {
		this.coins.removeValue(c, true);
	}
	
	public void setNewsFeed(String news)
	{
		NewsFeed = news;
	}
	public String returnNewsFeed()
	{
		return NewsFeed;
	}
	
}
