package com.oldoldb.doudoulandlords;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.oldoldb.doudoulandlords.GameLogic.CombinationType;

public class GameAI {

	private  static GameAI instance = null;
	
	private GameAI()
	{
		
	}
	
	public static GameAI getInstance()
	{
		if(instance == null){
			synchronized (GameAI.class) {
				if(instance == null){
					instance = new GameAI();
				}
			}
		}
		return instance;
	}
	
	public void getAIPopCards(CombinationType lastType, List<Card> lastCards, List<Card> cards, List<Card> popCards)
	{
		switch (lastType) {
		case NEWROUND:
			getAIPopCardsForTypeNONE(lastCards, cards, popCards);
			break;
		case NONE:
			getAIPopCardsForTypeNONE(lastCards, cards, popCards);
			break;
		case ONE:
			getAIPopCardsForTypeONE(lastCards, cards, popCards);
			break;
		case TWO:
			getAIPopCardsForTypeTWO(lastCards, cards, popCards);
			break;
		case THREE:
			getAIPopCardsForTypeTHREE(lastCards, cards, popCards);
			break;
		case FOUR:
			getAIPopCardsForTypeFOUR(lastCards, cards, popCards);
			break;
		case THREE_WITH_ONE:
			getAIPopCardsForTypeTHREEWITHONE(lastCards, cards, popCards);
			break;
		case THREE_WITH_TWO:
			getAIPopCardsForTypeTHREEWITHTWO(lastCards, cards, popCards);
			break;
		case FOUR_WITH_TWO:
			getAIPopCardsForTypeFOURWITHTWO(lastCards, cards, popCards);
			break;
		case SINGLE_STRAIGHT:
			getAIPopCardsForTypeSINGLESTRAIGHT(lastCards, cards, popCards);
			break;
		case DOUBLE_STRAIGHT:
			getAIPopCardsForTypeDOUBLESTRAIGHT(lastCards, cards, popCards);
			break;
		case KING_KILLER:
			break;
		default:
			break;
		}
	}
	private void getAIPopCardsForTypeNONE(List<Card> lastCards, List<Card> cards, List<Card> popCards)
	{
		popCards.add(cards.get(0));
		cards.remove(0);
	}
	private void getAIPopCardsForTypeONE(List<Card> lastCards, List<Card> cards, List<Card> popCards)
	{
		int last = lastCards.get(0).getCardType().getValue();
		int size = cards.size();
		for(int i=0;i<size;i++){
			Card card = cards.get(i);
			if(card.getCardType().getValue() > last){
				popCards.add(card);
				cards.remove(i);
				break;
			}
		}
	}
	
	private void getAIPopCardsForTypeTWO(List<Card> lastCards, List<Card> cards, List<Card> popCards)
	{
		int last = lastCards.get(0).getCardType().getValue();
		int size = cards.size();
		List<Card> tempCards = new ArrayList<Card>();
		for(int i=0;i<size-1;i++){
			tempCards = getRangeList(cards, i, i+1);
			if(GameLogic.hasSameTwo(tempCards)){
				if(tempCards.get(0).getCardType().getValue() > last){
					popCards.addAll(tempCards);
					cards.remove(i);
					cards.remove(i);
					break;
				}
			}
		}
	}
	private void getAIPopCardsForTypeTHREE(List<Card> lastCards, List<Card> cards, List<Card> popCards)
	{
		int last = lastCards.get(0).getCardType().getValue();
		int size = cards.size();
		List<Card> tempCards = new ArrayList<Card>();
		for(int i=0;i<size-2;i++){
			tempCards = getRangeList(cards, i, i+2);
			if(GameLogic.hasSameThree(tempCards)){
				if(tempCards.get(0).getCardType().getValue() > last){
					popCards.addAll(tempCards);
					cards.remove(i);
					cards.remove(i);
					cards.remove(i);
					break;
				}
			}
		}
	}
	private void getAIPopCardsForTypeFOUR(List<Card> lastCards, List<Card> cards, List<Card> popCards)
	{
		int last = lastCards.get(0).getCardType().getValue();
		int size = cards.size();
		List<Card> tempCards = new ArrayList<Card>();
		for(int i=0;i<size-3;i++){
			tempCards = getRangeList(cards, i, i+3);
			if(GameLogic.hasSameFour(tempCards)){
				if(tempCards.get(0).getCardType().getValue() > last){
					popCards.addAll(tempCards);
					cards.remove(i);
					cards.remove(i);
					cards.remove(i);
					cards.remove(i);
					break;
				}
			}
		}
	}
	private void getAIPopCardsForTypeTHREEWITHONE(List<Card> lastCards, List<Card> cards, List<Card> popCards)
	{
		int last = lastCards.get(2).getCardType().getValue();
		int size = cards.size();
		List<Card> tempCards = new ArrayList<Card>();
		for(int i=0;i<size-3;i++){
			tempCards = getRangeList(cards, i, i+3);
			if(GameLogic.hasThreeWithOne(tempCards)){
				if(tempCards.get(2).getCardType().getValue() > last){
					popCards.addAll(tempCards);
					cards.remove(i);
					cards.remove(i);
					cards.remove(i);
					cards.remove(i);
					break;
				}
			}
		}
	}
	private void getAIPopCardsForTypeTHREEWITHTWO(List<Card> lastCards, List<Card> cards, List<Card> popCards)
	{
		int last = lastCards.get(2).getCardType().getValue();
		int size = cards.size();
		List<Card> tempCards = new ArrayList<Card>();
		for(int i=0;i<size-4;i++){
			tempCards = getRangeList(cards, i, i+4);
			if(GameLogic.hasThreeWithTwo(tempCards)){
				if(tempCards.get(2).getCardType().getValue() > last){
					popCards.addAll(tempCards);
					cards.remove(i);
					cards.remove(i);
					cards.remove(i);
					cards.remove(i);
					cards.remove(i);
					break;
				}
			}
		}
	}
	private void getAIPopCardsForTypeFOURWITHTWO(List<Card> lastCards, List<Card> cards, List<Card> popCards)
	{
		int last = lastCards.get(2).getCardType().getValue();
		int size = cards.size();
		List<Card> tempCards = new ArrayList<Card>();
		for(int i=0;i<size-5;i++){
			tempCards = getRangeList(cards, i, i+5);
			if(GameLogic.hasFourWithTwo(tempCards)){
				if(tempCards.get(2).getCardType().getValue() > last){
					popCards.addAll(tempCards);
					cards.remove(i);
					cards.remove(i);
					cards.remove(i);
					cards.remove(i);
					cards.remove(i);
					cards.remove(i);
					break;
				}
			}
		}
	}
	private int findTargetIndex(List<Card> cards, int target, int start)
	{
		int size = cards.size();
		for(int i=start;i<size;i++){
			Card card = cards.get(i);
			if(card.getCardType().getValue() == target){
				return i;
			}
		}
		return -1;
	}
	private void getAIPopCardsForTypeSINGLESTRAIGHT(List<Card> lastCards, List<Card> cards, List<Card> popCards)
	{
		int num = lastCards.size();
		int prev = lastCards.get(0).getCardType().getValue();
		int start = prev + 1;
		int end = 15 - num;
		Collections.sort(cards);
		for(int i=start;i<=end;i++){
			List<Integer> res = new ArrayList<Integer>();
			for(int j=0;j<num;j++){
				int index = findTargetIndex(cards, i + j, 0);
				if(index == -1){
					break;
				} else {
					res.add(index);
				}
			}
			if(res.size() < num){
				res.clear();
			} else {
				getPopCardsFromIndexList(cards, popCards, res);
				break;
			}
		}
	}
	private void getPopCardsFromIndexList(List<Card> cards, List<Card> popCards, List<Integer> indexList)
	{
		int size = indexList.size();
		for(int i=size-1;i>=0;i--){
			popCards.add(0, cards.get(indexList.get(i)));
			cards.remove(indexList.get(i).intValue());
		}
	}
	private void getAIPopCardsForTypeDOUBLESTRAIGHT(List<Card> lastCards, List<Card> cards, List<Card> popCards)
	{
		int num = lastCards.size();
		int prev = lastCards.get(0).getCardType().getValue();
		int start = prev + 1;
		int end = 15 - num / 2;
		Collections.sort(cards);
		for(int i=start;i<=end;i++){
			List<Integer> res = new ArrayList<Integer>();
			for(int j=0;j<num;j++){
				int index;
				if(j % 2 == 1){
					index = findTargetIndex(cards, i + j / 2, res.get(res.size() - 1) + 1);
				} else {
					index = findTargetIndex(cards, i + j / 2, 0);
				}
				if(index == -1){
					break;
				} else {
					res.add(index);
				}
			}
			if(res.size() < num){
				res.clear();
			} else {
				getPopCardsFromIndexList(cards, popCards, res);
				break;
			}
		}
	}
	private List<Card> getRangeList(List<Card> cards, int start, int end)
	{
		List<Card> ans = new ArrayList<Card>();
		for(int i=start;i<=end;i++){
			ans.add(cards.get(i));
		}
		return ans;
	}
}
