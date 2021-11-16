package com.mg3.game;

import com.mg3.game.Item;

public class ItemList {
	
	//[ATK,SHLD]

	public static final int total_Num_Items = 4;
	Item[] Items;

	public ItemList() {
		
		Items = new Item[total_Num_Items];
		
		int[] effect0 = {10,0};
		Items[0] = new Item("Weapon",0,1, effect0);
		
		int[] effect1 = {0,100};
		Items[1] = new Item("Armor",1,2,effect1);
		
		int[] effect2 = {5,50};
		Items[2] = new Item("Hybrid",2,3,effect2);
		
		int[] effect3 = {0,0};
		Items[3] = new Item("Nothing",3,4,effect3);
		
	//etc
	}
	
	public Item getItem(int id) {
		return Items[id];
	}
}
