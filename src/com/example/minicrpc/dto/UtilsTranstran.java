package com.example.minicrpc.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.minicrpc.databasedao.LuckyDog;
import com.example.minicrpc.entity.LuckyPeople;

public class UtilsTranstran {

	private UtilsTranstran() {}
	
	
	public static LuckyPeople fromLuckyDog(final LuckyDog luckydog) {
		return new LuckyPeople(luckydog.getId(),luckydog.getPortraitPath(), luckydog.getName(), luckydog.getSex(), luckydog.getBirthday(), luckydog.getTel(), luckydog.getNote());
	}
	
	public static List<LuckyPeople> fromLuckyDog(final List<LuckyDog> list) {
		List<LuckyPeople> l = new ArrayList<LuckyPeople>();
		
		for (LuckyDog item : list) {
			LuckyPeople lp = fromLuckyDog(item);
			l.add(lp);
		}
		
		return l;
	}
	
}
