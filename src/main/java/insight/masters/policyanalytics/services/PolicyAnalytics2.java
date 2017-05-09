package insight.masters.policyanalytics.services;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.logging.Logger;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;



import com.mongodb.BasicDBObject;

public class PolicyAnalytics2 {
	static final Logger logger = Logger.getLogger(MongoConnect.class.getName());
	static String policy_pdf = "/home/mohade/workspace/gov_policy_analytics/resources/datasets/5142-bioenergy-strategy-.pdf";
	static String[] aspects = {
			"The priority accorded to promoting equality in higher education will be reflected in the strategic planning and development of the Higher Education Authority and of higher-education institutions",
			"The lifelong learning agenda will be progressed through the development of a broader range of entry routes, a significant expansion of part-time/flexible courses and measures to address the student support implications of lifelong learning",
			"The priority accorded to promoting equality in higher education will be reflected in the allocation of public funds to higher-education institutions",
			"Students will be assisted to access supports and those supports will better address the financial barriers to access and successful participation in higher education",
			"The higher-education participation rates of people with disabilities will be increased through greater opportunities and supports" };

	static String wikipediadumb_olddiscoversion = "/home/mohade/workspace/DISCO-DATASET/enwiki-20130403-sim-lemma-mwl-lc";
	static String wikipediadumb_newdiscoversion = "/home/mohade/workspace/gov_policy_analytics/resources/datasets/enwiki-20130403-sim-lemma-mwl-lc";

	static BasicDBObject PolicyDataBaseJsonEntryObject = new BasicDBObject();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		if (args.length > 0) {
			policy_pdf = args[0];
		}

		/**
		 * 1 POlicy text Policy Aspects
		 */

		PDFParser parser = null;
		PDDocument pdDoc = null;
		COSDocument cosDoc = null;
		PDFTextStripper pdfStripper;

		String parsedText;
		String policytext = null;
		File file = new File(policy_pdf);
		try {
			parser = new PDFParser(new FileInputStream(file));
			parser.parse();
			cosDoc = parser.getDocument();
			pdfStripper = new PDFTextStripper();
			pdDoc = new PDDocument(cosDoc);
			parsedText = pdfStripper.getText(pdDoc);
			parsedText.replaceAll("[^A-Za-z0-9. ]+", "");
			policytext = parsedText;
//			 System.out.println(policytext);
			String[] splited= policytext.split("\\r?\\n");
			for(String s : splited){
				 System.out.println("{\"text\":\"");
				 System.out.println(s);
				 System.out.println("\"}");

			


			}
			
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

		
	}

}
