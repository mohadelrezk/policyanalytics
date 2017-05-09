package insight.masters.policyanalytics.services;

import java.io.File;
import java.io.FileInputStream;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class ReadfromPDF {
static String datsetspath="/home/mohade/workspace/gov_policy_analytics/resources/datasets/policies/";
static String Document="national_plan_for_equity_of_access_to_higher_ed.pdf";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
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
			 System.out.println(policytext);
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
