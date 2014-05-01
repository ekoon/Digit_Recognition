package vowel_detection;

public class Who {
	
    private static int MALE   = 0;
    private static int FEMALE = 1; 

    Speaker[] speakers = {
        new Speaker ("Andrew" , 0 , MALE  ),
        new Speaker ("Bill"	  , 1 , MALE  ),
        new Speaker ("David"  , 2 , MALE  ),
        new Speaker ("Mark"   , 3 , MALE  ),
        new Speaker ("Jo"     , 4 , FEMALE),
        new Speaker ("Kate"   , 5 , FEMALE),
        new Speaker ("Penny"  , 6 , FEMALE),
        new Speaker ("Rose"   , 7 , FEMALE),
        new Speaker ("Mike"   , 8 , MALE  ),
        new Speaker ("Nick"   , 9 , MALE  ),
        new Speaker ("Rich"   , 10, MALE  ),
        new Speaker ("Tim"    , 11, MALE  ),
        new Speaker ("Sarah"  , 12, FEMALE),
        new Speaker ("Sue"    , 13, FEMALE),
        new Speaker ("Wendy"  , 14, FEMALE)
    };
    
	public Speaker[] AllSpeakers(){					
		return speakers;
	}
    
	public Speaker IndexSpeakers(int index){					
		return speakers[index];
	}
	
	public Speaker IndexSpeakers(float index){				
		return speakers[convertFloatToInt(index)];
	}
	
	public String SpeakersName(int index){					
		return speakers[index].first_name();
	}
	
	public String SpeakersName(float index){					
		return speakers[convertFloatToInt(index)].first_name();
	}
	
	public String SpeakersSex(int index){					
		return speakers[index].sexString();
	}
	
	public String SpeakersSex(float index){					
		return speakers[convertFloatToInt(index)].sexString();
	}
	
	private int convertFloatToInt(float temp){		
		return Math.round((int)temp);		
	}
}
