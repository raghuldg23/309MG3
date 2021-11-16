package com.mg3.game;

import com.mg3.game.ItemList;

public class AmountList {

	private int [] list;
	public static final int amount_limit = 10;
	
	
	// [weapon, armor, hybrid]
	public AmountList() {
		new ItemList();
		list = new int[ItemList.total_Num_Items];
	}
	
	public void addItem(int id) {
		if(list[id]<amount_limit) {
		list[id]++;}
	}
	
	public void deleteItem(int id) {
		if(list[id]>0) {
			list[id]--;		
		}
	}
	
	public int getAmount(int id){
		return list[id];
	}
	
}
