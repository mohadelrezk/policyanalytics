package insight.masters.policyanalytics.services;

import insight.masters.policyanalytics.model.Category;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import com.mongodb.BasicDBObject;

import de.linguatools.disco.CorruptConfigFileException;
import de.linguatools.disco.DISCO;
import de.linguatools.disco.ReturnDataBN;
import de.linguatools.disco.TextSimilarity;
import de.linguatools.disco.WrongWordspaceTypeException;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;


public class PolicyAnalytics {

	static final Logger logger = Logger.getLogger(MongoConnect.class.getName());
	static String datasetspath = "/home/mohade/workspace/gov_policy_analytics/resources/datasets/policies/";
	
	static String[] aspects = {
			"The priority accorded to promoting equality in higher education will be reflected in the strategic planning and development of the Higher Education Authority and of higher-education institutions",
			"The lifelong learning agenda will be progressed through the development of a broader range of entry routes, a significant expansion of part-time/flexible courses and measures to address the student support implications of lifelong learning",
			"The priority accorded to promoting equality in higher education will be reflected in the allocation of public funds to higher-education institutions",
			"Students will be assisted to access supports and those supports will better address the financial barriers to access and successful participation in higher education",
			"The higher-education participation rates of people with disabilities will be increased through greater opportunities and supports" };

	static String wikipediadumb_olddiscoversion = "/home/mohade/workspace/DISCO-DATASET/enwiki-20130403-sim-lemma-mwl-lc";
	static String wikipediadumb_newdiscoversion = "/home/mohade/workspace/gov_policy_analytics/resources/datasets/enwiki-20130403-sim-lemma-mwl-lc";

	static BasicDBObject PolicyDataBaseJsonEntryObject = new BasicDBObject();
	
	public static void main(String args[]) throws ClassCastException,
			ClassNotFoundException, IOException {

		if (args.length > 0) {
		
		}

	
		/*
		 * NER PART @BETTER BE IN A SEPARATE METHOD OR CLASS@
		 */

		String serializedClassifier = "/home/mohade/workspace/gov_policy_analytics/resources/lib/stanford-ner-2015-04-20/classifiers/english.all.3class.distsim.crf.ser.gz";

		// if (args.length > 0) {
		// serializedClassifier = args[0];
		// }

		AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier
				.getClassifier(serializedClassifier);

		// String[] example={"Good afternoon Rajat Raina, how are you today?",
		// "I go to school at Stanford University, which is located in California."};

		// for (String str : example) {
		// System.out.println(classifier.classifyToString(str));
		// }

		// for (String str : first200) {
		// System.out.println(classifier.classifyWithInlineXML(policytext));

		/**
		 * 2 EXTRACTING STANFORD NER FROM policy doc or pdf
		 */
		// System.out.println(NewsFeedDescription);
		// System.out.println(NewsWire2.classifier
		// .classifyWithInlineXML(NewsFeedDescription));
//		String policy_Stanford_NER_xml = classifier
//				.classifyWithInlineXML(readfromdoc(datasetspath, "Donald Trump Isnt Alone in Exploiting the Word University.doc"));
		String policy_Stanford_NER_xml = classifier
				.classifyWithInlineXML(readfrompdf(datasetspath, "5142-bioenergy-strategy-.pdf"));
		List<Category> policy_Stanford_NER_Locations = getCategoryEntities(
				policy_Stanford_NER_xml, "LOCATION");
		List<Category> policy_Stanford_NER_Persons = getCategoryEntities(
				policy_Stanford_NER_xml, "PERSON");
		List<Category> policy_Stanford_NER_Organizations = getCategoryEntities(
				policy_Stanford_NER_xml, "ORGANIZATION");

		/**
		 * 3 Calculating origin keywords Occurrences
		 * 
		 * */

		HashMap<String, Integer> frequencymap = new HashMap<String, Integer>();
		String temp = "";

		if (policy_Stanford_NER_Locations.isEmpty() != true) {
			for (Category Entity : policy_Stanford_NER_Locations) {
				temp = Entity.getValue() + "," + Entity.getName();
				if (frequencymap.containsKey(temp)) {
					frequencymap.put(temp, frequencymap.get(temp) + 1);
				} else {
					frequencymap.put(temp, 1);
				}
			}

		}

		if (policy_Stanford_NER_Persons.isEmpty() != true) {
			for (Category Entity : policy_Stanford_NER_Persons) {
				temp = Entity.getValue() + "," + Entity.getName();
				if (frequencymap.containsKey(temp)) {
					frequencymap.put(temp, frequencymap.get(temp) + 1);
				} else {
					frequencymap.put(temp, 1);
				}
			}

		}

		if (policy_Stanford_NER_Organizations.isEmpty() != true) {
			for (Category Entity : policy_Stanford_NER_Organizations) {
				temp = Entity.getValue() + "," + Entity.getName();
				if (frequencymap.containsKey(temp)) {
					frequencymap.put(temp, frequencymap.get(temp) + 1);
				} else {
					frequencymap.put(temp, 1);
				}
			}

		}

		/**
		 * 4 sorting top 20 origin
		 * 
		 * */
		System.out.println("Unsort Map......");
		// printMap(frequencymap);

		System.out.println("\nSorted Map......");
		Map<String, Integer> sortedMap = sortByComparator(frequencymap);
		// printMap(sortedMap);

		Object[] sortedmaptoarray = sortedMap.entrySet().toArray();
		System.out.println(sortedMap.size());
		System.out.println(sortedmaptoarray.length);

//		for (int i = sortedmaptoarray.length - 1; i >= sortedmaptoarray.length - 20; i--) {
		int limit=0;
		if(sortedmaptoarray.length>20)
			limit=sortedmaptoarray.length-20;
		
			for (int i = sortedmaptoarray.length - 1; i >= limit; i--) {

			System.out.println(sortedmaptoarray[i].toString().replaceAll(" ",
					"_"));
			/**
			 * 5 branching the origin keywords
			 * 
			 * */
//			branching(sortedmaptoarray);
			/**
			 * 6
			 * 
			 * Relate Keywords to aspects
			 * Compute the semantic similarity of text and hypothesis according to the algorithm given in Jijkoun & De Rijke (2005): "Recognizing Textual Entailment Using Lexical Similarity".
	The method tests if the hypothesis is licensed by the text.
			 * */
			
//			relateToAspects(sortedmaptoarray);
			
			}
			
			
			
			
			
		
}
	

	// int occurrences = Collections.frequency(animals, "bat");

	/**
	 * @param messageText
	 * @param delimiter
	 * @return List<Category>
	 * @contain Extracting discovered entities in tweet text and author profile
	 *          using Stanford NLP tool. And storing it in db object in order to
	 *          store it to mongodb.
	 */

	public static List<Category> getCategoryEntities(String messageText,
			String delimiter) {
		List<Category> entities = new ArrayList<Category>();
		int first_index = 0;
		int second_index = 0;
		int delimiter_size;
		String results;
		boolean keepgoing = true;

		delimiter_size = delimiter.length() + 2;
		while (keepgoing) {
			if (first_index < second_index
					&& second_index + delimiter_size < messageText.length())
				messageText = messageText.substring(second_index
						+ delimiter_size);

			first_index = messageText.indexOf("<" + delimiter + ">");
			second_index = messageText.indexOf("</" + delimiter + ">");

			if (first_index < second_index)
				results = messageText.substring(first_index + delimiter_size,
						second_index);
			else
				results = "";
			if (results.isEmpty()) {
				keepgoing = false;
			} else {
				Category category = new Category();
				category.setName(delimiter.toLowerCase());
				category.setValue(results);
				entities.add(category);
			}
		}
		return entities;
	}

	private static Map<String, Integer> sortByComparator(
			Map<String, Integer> unsortMap) {

		// Convert Map to List
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(
				unsortMap.entrySet());

		// Sort list with comparator, to compare the Map values
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1,
					Map.Entry<String, Integer> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		// Convert sorted map back to a Map
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it
				.hasNext();) {
			Map.Entry<String, Integer> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}

	public static void printMap(Map<String, Integer> map) {
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			System.out.println("[Key] : " + entry.getKey() + " [Value] : "
					+ entry.getValue());
		}

		// for (HashMap.Entry<String,Integer> entry : frequencymap.entrySet()) {
		// String key = entry.getKey();
		// Integer value = entry.getValue();
		// // do stuff
		// System.out.println(key+"->"+value);
		//
		// }

	}
	
	
	public static String readfromdoc(String datsetspath, String Document)
    {
        File file = null;
        WordExtractor extractor = null;
        String extractedtext="";
        try
        {

            file = new File(datsetspath+Document);
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
            HWPFDocument document = new HWPFDocument(fis);
            extractor = new WordExtractor(document);
            String[] fileData = extractor.getParagraphText();
            for (int i = 0; i < fileData.length; i++)
            {
                if (fileData[i] != null)
//                	 System.out.print("{\"text\":\"");
                System.out.print(fileData[i].replace("\n", "").replace("\r", ""));
                extractedtext+=fileData[i].replace("\n", "").replace("\r", "");
//				 System.out.print("\"}");
            
            }
        }
        catch (Exception exep)
        {
            exep.printStackTrace();
        }
		return extractedtext;
    }
	
	public static String readfrompdf(String datsetspath, String Document)
    {
	/**
	 * 1 POlicy text Policy Aspects
	 */

	PDFParser parser = null;
	PDDocument pdDoc = null;
	COSDocument cosDoc = null;
	PDFTextStripper pdfStripper;

	String parsedText;
	String policytext = null;
	File file = new File(datsetspath+Document);
	try {
		parser = new PDFParser(new FileInputStream(file));
		parser.parse();
		cosDoc = parser.getDocument();
		pdfStripper = new PDFTextStripper();
		pdDoc = new PDDocument(cosDoc);
		parsedText = pdfStripper.getText(pdDoc);
		parsedText.replaceAll("[^A-Za-z0-9. ]+", "");
		policytext = parsedText;
		// System.out.println(policytext);
	} catch (Exception e) {
		e.printStackTrace();
		try {
			if (cosDoc != null)
				cosDoc.close();
			if (pdDoc != null)
				pdDoc.close();
		} catch (Exception e1) {
			e.printStackTrace();
		}

	}
	return policytext;


    }
	
	static void branching (Object[] sortedmaptoarray){
		String temp2 = "";
		String temp3 = "";
//		for (int i = sortedmaptoarray.length - 1; i >= sortedmaptoarray.length - 20; i--) {
			for (int i = sortedmaptoarray.length - 1; i >= 0; i--) {

			System.out.println(sortedmaptoarray[i].toString().replaceAll(" ",
					"_"));
			
			temp3 = sortedmaptoarray[i].toString()
					.substring(0, sortedmaptoarray[i].toString().indexOf(","))
					.toLowerCase();
			temp2 = temp3.replaceAll("\\s+", "_");
//			PolicyDataBaseJsonEntryObject.append("_id",
//					temp2);
			/**
			 * 5 branching the origin keywords
			 * 
			 * */
			ReturnDataBN res = new ReturnDataBN();
			
			DISCO d;
			try {
				 d = new DISCO(wikipediadumb_newdiscoversion, false);
				
				res = d.similarWords(temp2);
				if (res == null) {
					System.out.println("The word \"" + temp2
							+ "\" was not found.");
//					return;
				}else{
				int n = Integer.parseInt("20") - 1;
				for (int k = 1; k < res.words.length; k++) { // BUG im Indexer
																// für V1
					System.out.println(res.words[k] + "\t0." + res.values[k]);
					if (k > n)
						break; // k >= n
				}
				}
			} catch (IOException | CorruptConfigFileException | WrongWordspaceTypeException ex) {
				System.out.println("Error: IOException: " + ex);
			}
		}
		
	
	}
	static void relateToAspects (Object[] sortedmaptoarray){
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

		 for(Object originkeyword:sortedmaptoarray ){ 
		
			 temp3 = originkeyword.toString()
						.substring(0, originkeyword.toString().indexOf(","))
						.toLowerCase();
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
