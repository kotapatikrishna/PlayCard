package com.play;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class PlayCard implements PlayGame{

	private List<Cards> cards;
	private List<Player> players = new ArrayList<Player>();
	private Map<Player, List<Cards>> cardsPlayerMap = new HashMap<Player, List<Cards>>();
	private int currentPlayerIndex = 0;
	private static final int noOfCardsPerPlayer = 3;
	private int noOfPlayers = 4;
	
	public int getNumberOfPlayers(){
		return noOfPlayers;
	}
	
	public List<Player> getPlayers(){
		return players;
	}
	
	public PlayCard(){
		cards = Cards.getPackOfCards();
	}
	
	public void distributeCardsToPlayers(List<Player> mans){
		this.players = mans;
		Cards.refreshCards(cards);
		
		if(cardsPlayerMap.size() ==0){
			cardsPlayerMap.clear();
		}
		int m=0;
		for(Player pr: players){
			pr.setPoints(0);
			List<Cards> crds = new ArrayList<Cards>();
			int crdsLimit = m+noOfCardsPerPlayer;
			for(int i=m; i<crdsLimit; i++){
				crds.add(cards.get(i));
			}
			m = crdsLimit;
			cardsPlayerMap.put(pr, crds);
		}
	}
	
	public void playGame(int noOfPlayers) {
		this.noOfPlayers = noOfPlayers;
		createMultipleUsers(noOfPlayers);
		int i=0;
		System.out.print("Started game ....");
		List<Cards> selCards = new ArrayList<Cards>();
        Cards maxCard = null;
        Player maxPlayer = new Player(0);
        distributeCardsToPlayers(players);
        for (int j = 0; j < noOfCardsPerPlayer; j++)
        {
                int s = 0;
                do
                {
                	Player pr = getNextPlayer();
                	Scanner in = new Scanner(System.in);
                	i = in.nextInt();
                	switch(i){
                	case 1:
                		displayCardsToPlayer(pr);
                		in = new Scanner(System.in);
                        int m = in.nextInt();
                        Cards c = cardsPlayerMap.get(pr).get(m - 1);
                        System.out.println("Card Selected -> " + c.toString());
                        cardsPlayerMap.get(pr).remove(m - 1);
                        if (maxCard == null)
                        {
                                maxCard = c;
                                maxPlayer = pr;
                        }
                        else
                        {
                                if (maxCard.compareTo(c) < 0)
                                {
                                        maxCard = c;
                                        maxPlayer = pr;
                                }
                        }
                        selCards.add(c);

                        break;
                	case 2:
                        return;
                	}
                	s++;
                }while(s < players.size());
                if (maxPlayer.getPlayerId() > 0)
                    maxPlayer.setPoints((maxPlayer.getPoints()) + 1);
            maxCard = null;
            maxPlayer = null;
            displayScores();
        }
	}
	private void displayScores() {
		for (Player pl : players)
        {
                System.out.println("Player " + pl.getPlayerId() + " Score -> " + pl.getPoints());
        }
		
	}

	private void displayCardsToPlayer(Player pr) {
		int cards = cardsPlayerMap.get(pr).size();
        for (int i = 0; i < cards;)
        {
                System.out.print((++i) + " ");
        }
	}

	private void createMultipleUsers(int k) {
		if(players.size() !=0){
			players.clear();
		}
		for( int i=0; i<k; i++){
			int id = i+1;
			Player pu = new Player(id);
			players.add(pu);
		}
		distributeCardsToPlayers(players);
	}
	
	private Player getNextPlayer(){
		Player p = null;
		if(currentPlayerIndex == players.size()){
			currentPlayerIndex = 1;
			p = players.get(0);
		}else{
			p = players.get(currentPlayerIndex);
			currentPlayerIndex ++;
		}
		return p;
	}

	public void winner() {
		 Collections.sort(players);
         int maxPoints = 0;
         Map<String, List<Player>> playerPointsMap = new TreeMap<String, List<Player>>();
         for (Player p : players)
         {
        	 maxPoints = p.getPoints();
             if (playerPointsMap.get(maxPoints + "") != null)
             {
                     List<Player> lst = playerPointsMap.get(maxPoints + "");
                     lst.add(p);
                     playerPointsMap.put(maxPoints + "", lst);
             }
             else
             {
                     List<Player> lst = new ArrayList<Player>();
                     lst.add(p);
                     playerPointsMap.put(maxPoints + "", lst);
             }
         }
         String pts = new Integer(players.get(players.size() - 1).getPoints()).toString();
         if (playerPointsMap.get(pts) != null && playerPointsMap.get(pts).size() > 1)
         {
                 System.out.println("Its a draw among the following players ");
                 for (Player p : players)
                 {
                         System.out.println("Player -> " + p.getPlayerId());
                 }
         }
         else if (playerPointsMap.get(pts) != null)
         {
                 System.out.println("And the winner is :");
                 System.out.println("Player -> " + playerPointsMap.get(pts).get(0).getPlayerId());
         }
		
	}
	
}
