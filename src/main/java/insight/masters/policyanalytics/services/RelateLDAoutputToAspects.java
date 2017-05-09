package insight.masters.policyanalytics.services;

import java.io.IOException;

import de.linguatools.disco.CorruptConfigFileException;
import de.linguatools.disco.DISCO;
import de.linguatools.disco.TextSimilarity;

public class RelateLDAoutputToAspects {
	static boolean stanf=false;
	static String wikipediadumb_newdiscoversion = "/home/mohade/workspace/gov_policy_analytics/resources/datasets/enwiki-20130403-sim-lemma-mwl-lc";

	 static String[] aspects_set2= {
		"The priority accorded to promoting equality in higher education will be reflected in the strategic planning and development of the Higher Education Authority and of higher-education institutions",
		"The lifelong learning agenda will be progressed through the development of a broader range of entry routes, a significant expansion of part-time/flexible courses and measures to address the student support implications of lifelong learning",
		"The priority accorded to promoting equality in higher education will be reflected in the allocation of public funds to higher-education institutions",
		"Students will be assisted to access supports and those supports will better address the financial barriers to access and successful participation in higher education",
		"The higher-education participation rates of people with disabilities will be increased through greater opportunities and supports" };
	static String[] aspects_set1={
		"Policies that support bioenergy should deliver genuine carbon reductions that help meet UK carbon emissions objectives to 2050 and beyond. This assessment should look – to the best degree possible – at carbon impacts for the whole system, including indirect impacts such as ILUC, where appropriate, and any changes to carbon stores."
		,
		"Support for bioenergy should make a cost effective contribution to UK carbon emission objectives in the context of overall energy goals. Bioenergy should be supported when it offers equivalent or lower carbon emissions for each unit of expenditure compared to alternative investments which also meet the requirements of the policies."
		,
		"Support for bioenergy should aim to maximise the overall benefits and minimise costs (quantifiable and non-quantifiable) across the economy. Policy makers should consider the impacts and unintended consequences of policy interventions on the wider energy system and economy, including non-energy industries."
		,
		"At regular intervals and when policies promote significant additional demand for bioenergy in the UK, beyond that envisaged by current use, policy makers should assess and respond to the impacts of this increased deployment. This assessment should include analysis of whether UK bioenergy demand is likely to significantly hinder the achievement of other objectives, such as maintaining food security, halting bio-diversity loss, achieving wider environmental outcomes or global development and poverty reduction."
	};
	
	 static Object [] keywords_set2_1= {
		"education",
		"higher",
		"year",
		"community",
		"educational",
		"social",
//		"education",
//		"higher",
		"level",
		"entry",
		"participation",
		"greater",
		"cent",
		"data",
		"students",
		"learning",
		"access",
		"support",
		"national"
//		"access"

	};
	 static Object [] keywords_set2_2= {
	 "ireland",
	 "educational",
	 "levels",
	 "level",
	 "access",
	 "plan",
	 "national",
	 "development",
	 "education",
	 "higher",
	 "access",
	 "institutions",
	 "higher",
	 "education",
	 "cent",
	 "entry",
	 "education",
	 "higher",
	 "national",
	 "equality"

	 };
	 
	static Object [] keywords_set1_1= {
		"biomass",
		"energy",
		"carbon",
		"power",
//		"carbon",
		"emissions",
		"impacts",
		"sustainability",
		"land", 
//		"energy",
		"production",
		"food",
		"waste",
		"renewable",
		"wood",
		"deployment",
		"bioenergy",
		"uk",
//		"energy",
		"strategy"
		
	};
	
	static Object [] keywords_set1_2= {

	"carbon",
	"emissions",
	"biomass",
	"energy",
	"biomass",
	"analysis",
	"supply",
	"uk",
	"bioenergy",
	"land",
	"production",
	"food",
	"biomass",
	"wood",
	"energy",
	"fuel",
	"bioenergy",
	"uk",
	"energy",
	"government"
	};
	static Object [] keywords_set1_0= {
	"energy",
	"cost",
	"supply",
	"wood",
	"carbon",
	"low",
	"bioenergy",
	"government",
	"emissions",
	"feedstocks",
	"biomass",
	"power",
	"land",
	"production",
	"biomass",
	"fuel",
	"waste",
	"ccs",
	"bioenergy",
	"uk"
	};

	public static void main (String [] args){
		
		
		relateToAspectspls(aspects_set2,keywords_set2_2);
		
	}
	
	
	  static void relateToAspectspls (String[] aspects,Object[] keywords){
		String temp2 = "";
		String temp3 = "";
		/**
		 * 6
		 * 
		 * Relate Keywords to aspects
		 * Compute the semantic similarity of text and hypothesis according to the algorithm given in Jijkoun & De Rijke (2005): "Recognizing Textual Entailment Using Lexical Similarity".
The method tests if the hypothesis is licensed by the text.
		 * */
		DISCO disco;
		TextSimilarity t=new TextSimilarity();
		float sim;
		int count=1;
		try {
		 disco = new DISCO(wikipediadumb_newdiscoversion, false);
//		 sim= t.textSimilarity("arabic", "arabic", disco, DISCO.SimilarityMeasure.KOLB );
//		 System.out.println(sim);

		 for(Object originkeyword:keywords ){ 
		if (stanf==true){
			 temp3 = originkeyword.toString()
						.substring(0, originkeyword.toString().indexOf(","))
						.toLowerCase();
		}else{
			 temp3 = originkeyword.toString();
			
		}
				temp2 = temp3.replaceAll(" ", "_");
				count=1;
			 for(String aspect : aspects){
//				 aspect=aspect.replaceAll(" ", "_");
				 sim= t.textSimilarity(temp2, aspect, disco, DISCO.SimilarityMeasure.KOLB );

//		 System.out.println(temp2+"-->>"+aspect+"="+sim);
				 System.out.println(temp2+"-->>"+count+"="+sim);
					count++;
			}
		
		}
		
		} catch (IOException | CorruptConfigFileException ex) {
			System.out.println("Error: IOException: " + ex);
		}
			

	}

}
