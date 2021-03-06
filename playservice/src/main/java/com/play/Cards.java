/**
 * 
 */
package com.play;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Krishna Kotapati
 *
 */
public class Cards implements Comparable<Cards> {
	private BUNDLECARDS cdNumber;
	private CARDTYPE cdType;
	
	private Cards(){}

	public int compareTo(Cards o) {
		if(this.getCdNumber() == o.getCdNumber()){
			return 0;
		}else if(this.getCdNumber().getOrd() > o.getCdNumber().getOrd()){
			return 1;
		}else{
			return -1;
		}
	}
	
	public enum BUNDLECARDS{
		TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), QUEEN(11), KING(12), ACE(13);
		
		private int ord;
		
		private BUNDLECARDS(int i){
			this.ord = i;
		}
		public int getOrd(){
			return ord;
		}
	}
	
	public enum CARDTYPE{
		CLUB, DIAMOND, HEARTS, SPADE;
	}
	
	
	public BUNDLECARDS getCdNumber(){
		return cdNumber;
	}
	
	public CARDTYPE getCdType(){
		return cdType;
	}
	
	public static void refreshCards(List<Cards> cards){
		Collections.shuffle(cards);
	}
	
	public static List<Cards> getPackOfCards(){
		List<Cards> list = new ArrayList<Cards>();
		for(CARDTYPE types: CARDTYPE.values()){
			for(BUNDLECARDS cNums: BUNDLECARDS.values()){
				Cards ct = new Cards();
				ct.cdNumber= cNums;
				ct.cdType = types;
				list.add(ct);
			}
		}
		return list;
	}
	
	@Override
	public String toString(){
		return "CARD [cdNumber= "+ cdNumber + ", cdType= " + cdType + "]";
	}
}
