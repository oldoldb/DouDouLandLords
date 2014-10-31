package com.oldoldb.doudoulandlords;

import java.util.Collections;
import java.util.List;

public class GameLogic {

	public enum CombinationType{
		NEWROUND,NONE,ONE,TWO,THREE,FOUR,THREE_WITH_ONE,THREE_WITH_TWO,FOUR_WITH_TWO,SINGLE_STRAIGHT;
	}
	public static boolean isMeetLogic(CombinationType lastType, List<Card> lastCards, List<Card> selectedCards)
	{
		boolean flag = false;
		CombinationType type = getCardsType(selectedCards);
		if(lastType != CombinationType.NONE && lastType != CombinationType.NEWROUND && lastType != type){
			return false;
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
}
