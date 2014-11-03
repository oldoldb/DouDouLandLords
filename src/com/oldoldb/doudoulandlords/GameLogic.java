package com.oldoldb.doudoulandlords;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameLogic {

	public enum CombinationType{
		NEWROUND,NONE,ONE,TWO,THREE,FOUR,THREE_WITH_ONE,THREE_WITH_TWO,FOUR_WITH_TWO,SINGLE_STRAIGHT,DOUBLE_STRAIGHT,KING_KILLER;
	}
	public static boolean isMeetLogic(CombinationType lastType, List<Card> lastCards, List<Card> selectedCards)
	{
		boolean flag = false;
		CombinationType type = getCardsType(selectedCards);
		if(lastType != CombinationType.NONE && lastType != CombinationType.NEWROUND && type != lastType){
			if(lastType == CombinationType.FOUR){
				if(type == CombinationType.KING_KILLER){
					return true;
				} else {
					return false;
				}
			} else if(lastType == CombinationType.KING_KILLER){
				return false;
			} else if(type == CombinationType.FOUR || type == CombinationType.KING_KILLER){
				return true;
			}
		}
		if((lastType == CombinationType.NONE || lastType == CombinationType.NEWROUND) && type != CombinationType.NONE){
			return true;
		}
		switch (type) {
		case NONE:
			flag = false;
			break;
		case ONE:
		case TWO:
		case THREE:
		case FOUR:
			flag = selectedCards.get(0).getCardType().getValue() > lastCards.get(0).getCardType().getValue();
			break;
		case THREE_WITH_ONE:
			flag = selectedCards.get(1).getCardType().getValue() > lastCards.get(1).getCardType().getValue();
			break;
		case THREE_WITH_TWO:
			flag = selectedCards.get(2).getCardType().getValue() > lastCards.get(2).getCardType().getValue();
			break;
		case FOUR_WITH_TWO:
			flag = selectedCards.get(2).getCardType().getValue() > lastCards.get(2).getCardType().getValue();
			break;
		case SINGLE_STRAIGHT:
			flag = selectedCards.size() == lastCards.size() && selectedCards.get(0).getCardType().getValue() > lastCards.get(0).getCardType().getValue();
			break;
		case DOUBLE_STRAIGHT:
			flag = selectedCards.size() == lastCards.size() && selectedCards.get(0).getCardType().getValue() > lastCards.get(0).getCardType().getValue();
			break;
		case KING_KILLER:
			flag = true;
			break;
		default:
			break;
		}
		return flag;
	}
	
	public static CombinationType getCardsType(List<Card> selectedCards)
	{
		CombinationType type = CombinationType.NONE;
		int size = selectedCards.size();
		switch (size) {
		case 1:
			type = CombinationType.ONE;
			break;
		case 2:
			if(hasSameTwo(selectedCards)){
				type = CombinationType.TWO;
			} else if(isKingKiller(selectedCards)){
				type = CombinationType.KING_KILLER;
			}
			break;
		case 3:
			if(hasSameThree(selectedCards)){
				type = CombinationType.THREE;
			}
			break;
		case 4:
			if(hasSameFour(selectedCards)){
				type = CombinationType.FOUR;
			} else if(hasThreeWithOne(selectedCards)){
				type = CombinationType.THREE_WITH_ONE;
			}
			break;
		case 5:
			if(hasThreeWithTwo(selectedCards)){
				type = CombinationType.THREE_WITH_TWO;
			}
			break;
		case 6:
			if(hasFourWithTwo(selectedCards)){
				type = CombinationType.FOUR_WITH_TWO;
			}
			break;
		default:
			break;
		}
		if(type == CombinationType.NONE){
			if(isSingleStraight(selectedCards)){
				type = CombinationType.SINGLE_STRAIGHT;
			} else if(isDoubleStraight(selectedCards)){
				type = CombinationType.DOUBLE_STRAIGHT;
			}
		}
		return type;
	}
	
	public static boolean hasSameTwo(List<Card> selectedCards)
	{
		return selectedCards.get(0).getCardType().getValue() == selectedCards.get(1).getCardType().getValue();
	}
	
	public static boolean hasSameThree(List<Card> selectedCards)
	{
		return selectedCards.get(0).getCardType().getValue() == selectedCards.get(1).getCardType().getValue()
				&& selectedCards.get(1).getCardType().getValue() == selectedCards.get(2).getCardType().getValue();
	}
	
	public static boolean hasSameFour(List<Card> selectedCards)
	{
		return selectedCards.get(0).getCardType().getValue() == selectedCards.get(1).getCardType().getValue()
				&& selectedCards.get(1).getCardType().getValue() == selectedCards.get(2).getCardType().getValue()
				&& selectedCards.get(2).getCardType().getValue() == selectedCards.get(3).getCardType().getValue();
	}
	
	public static boolean hasThreeWithOne(List<Card> selectedCards)
	{
		Collections.sort(selectedCards);
		return  selectedCards.get(1).getCardType().getValue() == selectedCards.get(2).getCardType().getValue()
				&& (selectedCards.get(0).getCardType().getValue() == selectedCards.get(1).getCardType().getValue()
					|| selectedCards.get(3).getCardType().getValue() == selectedCards.get(1).getCardType().getValue());
	}
	
	public static boolean hasThreeWithTwo(List<Card> selectedCards)
	{
		Collections.sort(selectedCards);
		return selectedCards.get(0).getCardType().getValue() == selectedCards.get(1).getCardType().getValue()
				&& selectedCards.get(3).getCardType().getValue() == selectedCards.get(4).getCardType().getValue()
				&& (selectedCards.get(2).getCardType().getValue() == selectedCards.get(0).getCardType().getValue()
					|| selectedCards.get(2).getCardType().getValue() == selectedCards.get(4).getCardType().getValue());
	}
	
	public static boolean hasFourWithTwo(List<Card> selectedCards)
	{
		Collections.sort(selectedCards);
		return selectedCards.get(2).getCardType().getValue() == selectedCards.get(3).getCardType().getValue()
				&& selectedCards.get(0).getCardType().getValue() == selectedCards.get(1).getCardType().getValue()
				&& selectedCards.get(4).getCardType().getValue() == selectedCards.get(5).getCardType().getValue()
				&& (selectedCards.get(2).getCardType().getValue() == selectedCards.get(0).getCardType().getValue()
					|| selectedCards.get(2).getCardType().getValue() == selectedCards.get(4).getCardType().getValue());
	}
	
	public static boolean isSingleStraight(List<Card> selectedCards)
	{
		int size = selectedCards.size();
		if(size < 5 || size > 12){
			return false;
		}
		Collections.sort(selectedCards);
		for(int i=0;i<size-1;i++){
			if(selectedCards.get(i).getCardType().getValue() + 1 != selectedCards.get(i+1).getCardType().getValue()){
				return false;
			}
		}
		return true;
	}
	
	public static boolean isDoubleStraight(List<Card> selectedCards)
	{
		int size = selectedCards.size();
		if(size % 2 == 1 || size < 6 || size > 20){
			return false;
		}
		Collections.sort(selectedCards);
		for(int i=0;i<size-2;i+=2){
			if(selectedCards.get(i).getCardType().getValue() != selectedCards.get(i+1).getCardType().getValue()
					|| selectedCards.get(i).getCardType().getValue() + 1 != selectedCards.get(i+2).getCardType().getValue()){
				return false;
			}
		}
		return true;
	}
	
	public static boolean isKingKiller(List<Card> selectedCards)
	{
		int size = selectedCards.size();
		if(size != 2){
			return false;
		}
		Collections.sort(selectedCards);
		if(selectedCards.get(0).getCardType().getValue() == 16 && selectedCards.get(1).getCardType().getValue() == 17){
			return true;
		}
		return false;
	}
	
	public static boolean isGameOver(List<Card> playerCards, List<Card> leftCards, List<Card> rightCards)
	{
		return playerCards.isEmpty() || leftCards.isEmpty() || rightCards.isEmpty();
	}
	
	public static void shuffleCards(List<Card> allCards)
	{
		int size = allCards.size();
		Random random = new Random();
		for(int i=1;i<size;i++){
			int index = random.nextInt(i+1);
			if(index != i){
				Collections.swap(allCards, index, i);
			}
		}
	}
	
	public static void sortCards(List<Card> playerCards, List<Card> leftCards, List<Card> rightCards)
	{
		sortPlayerCards(playerCards);
		sortLeftCards(leftCards);
		sortRightCards(rightCards);
	}
	
	public static void sortPlayerCards(List<Card> playerCards)
	{
		Collections.sort(playerCards);
	}
	
	private static void sortLeftCards(List<Card> leftCards)
	{
		Collections.sort(leftCards);
	}
	private static void sortRightCards(List<Card> rightCards)
	{
		Collections.sort(rightCards);
	}
}
