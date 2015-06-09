package cloud.project.parse;

public class ArticleCategory {
	//source
	public static final int SRC_CNN = 1;
	public static final int SRC_VOA = 2;
	public static final int SRC_60S = 3;
	
	//category
	public static final int CAT_DEFAULT = 1;	
	public static final int CAT_SCIENCE_TECHNOLOGY = 2;
	public static final int CAT_HEALTH = 3;
	public static final int CAT_SPORT = 4;
	public static final int CAT_WORLD = 5;
	public static final int CAT_ENTERTAINMENT = 6;
	public static final int CAT_POLITICS = 7;
	
	
	public static int getCatId(int src, String catStr){
		switch(src){
			case SRC_60S:
				if(catStr.equalsIgnoreCase("health")) return CAT_HEALTH;
				if(catStr.equalsIgnoreCase("technology")) return CAT_SCIENCE_TECHNOLOGY;				
				return CAT_SCIENCE_TECHNOLOGY;
			case SRC_VOA:
				if(catStr.equalsIgnoreCase("usa") || catStr.equalsIgnoreCase("africa") || catStr.equalsIgnoreCase("asia") || catStr.equalsIgnoreCase("mideast") || catStr.equalsIgnoreCase("europe")) return CAT_WORLD;
				if(catStr.equalsIgnoreCase("Science & Technology")) return CAT_SCIENCE_TECHNOLOGY;
				if(catStr.equalsIgnoreCase("health")) return CAT_HEALTH;
				if(catStr.equalsIgnoreCase("Arts & Entertainment")) return CAT_ENTERTAINMENT;
				return CAT_DEFAULT;
			case SRC_CNN:
				if(catStr.equalsIgnoreCase("entertainment")) return CAT_ENTERTAINMENT;
				if(catStr.equalsIgnoreCase("tennis") || catStr.equalsIgnoreCase("football") || catStr.equalsIgnoreCase("motosport") || catStr.equalsIgnoreCase("sport") || catStr.equalsIgnoreCase("golf")) return CAT_SPORT;
				if(catStr.equalsIgnoreCase("tech")) return CAT_SCIENCE_TECHNOLOGY;
				if(catStr.equalsIgnoreCase("us") || catStr.equalsIgnoreCase("asia") || catStr.equalsIgnoreCase("world") || catStr.equalsIgnoreCase("europe")) return CAT_WORLD;				
				if(catStr.equalsIgnoreCase("politics")) return CAT_POLITICS;
				return CAT_DEFAULT;
			default:
				return CAT_DEFAULT;				
		}
	}
}
