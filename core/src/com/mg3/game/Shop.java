package com.mg3.game;



public class Shop {
	
	String description;
	
	
	public void shop(){
		
	}
	
	public void buyWeapon(Player p){
		if(p.getCoins() >= 25){
			p.setCoins(p.getCoins() - 25);
			p.playerItemPickup(0);
		}
	}
	public void buyArmor(Player p){
		if(p.getCoins() >= 25){
			p.setCoins(p.getCoins() - 25);
			p.playerItemPickup(1);
		}
	}
	public void buyHybrid(Player p){
		if(p.getCoins() >= 25){
			p.setCoins(p.getCoins() - 25);
			p.playerItemPickup(2);
		}
	}
	
	public String getDescription(int n){
		
		if(n == 0){
			description = "doubles the players attack damage";
		}
		if(n == 1){
			description = "adds a shield to the player that is worth 100 hp";
		}
		if(n == 2){
			description = "adds a shield to th eplayer that is worth 50 hp and mulitplies the attack damge by 1.5";
		}
		return description;
	}
	
	
}
