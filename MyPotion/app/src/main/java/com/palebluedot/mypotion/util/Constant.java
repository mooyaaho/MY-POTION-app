package com.palebluedot.mypotion.util;

import java.text.SimpleDateFormat;

public class Constant {
    public static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("aa hh:mm#", java.util.Locale.getDefault());  //#으로 split하여 사용
    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
    public static SimpleDateFormat FREINDLY_DATE_FORMAT = new SimpleDateFormat("yyyy년 MM월 dd일", java.util.Locale.getDefault());

    public static final String[] TAGS={
            "기억력 상승",
            "혈행 개선",
            "간 건강",
            "체지방 감소",
            "갱년기 완화",
            "혈당 조절",
            "눈 건강",
            "면역 기능",
            "관절/뼈 건강",
            "전립선 건강",
            "피로 감소",
            "피부 건강",
            "콜레스테롤 개선",
            "혈압 조절",
            "긴장 감소",
            "장 건강",
            "배변 활동 원활",
            "칼슘 흡수력 상승",
            "요로 건강",
            "소화 기능 상승",
            "항산화",
            "혈중 중성 지방 감소",
            "인지 능력 상승",
            "운동 수행 능력 상승",
            "지구력 상승",
            "치아 건강",
            "배뇨기능 개선",
            "피부의 면역과민반응 방어",
            "월경 전 증후군 완화",
            "정자 운동성 상승",
            "질 건강",
            "키 성장",
            "골다공증 발생 감소",
            "충치 발생 감소"
    };
    public static final String[] TAGS_FOR_EXTRACT={
            "기억력 상승",
            "혈행 개선",
            "간 건강", "간 건강",
            "체지방 감소",
            "갱년기 완화",
            "혈당 조절",
            "눈 건강",
            "면역 기능", "면역 기능",
            "관절/뼈 건강", "관절/뼈 건강",
            "전립선 건강",
            "피로 감소",
            "피부 건강", "피부 건강",
            "콜레스테롤 개선",
            "혈압 조절",
            "긴장 감소",
            "장 건강", "장 건강",
            "배변 활동 원활",
            "칼슘 흡수력 상승",            "칼슘 흡수력 상승",
            "요로 건강",
            "소화 기능 상승",
            "항산화",
            "혈중 중성 지방 감소",            "혈중 중성 지방 감소",
            "인지 능력 상승",
            "운동 수행 능력 상승",
            "지구력 상승",
            "치아 건강",
            "배뇨기능 개선",
            "피부의 면역과민반응 방어", "피부의 면역과민반응 방어",
            "월경 전 증후군 완화",
            "정자 운동성 상승","정자 운동성 상승",
            "질 건강","질 건강",
            "키 성장",
            "골다공증 발생 감소",
            "충치 발생 감소"
    };
}
