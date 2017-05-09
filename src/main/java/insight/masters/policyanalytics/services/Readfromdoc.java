package insight.masters.policyanalytics.services;
import java.io.*;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

public class Readfromdoc {
	


	    public static void main(String[] args)
	    {
	        File file = null;
	        WordExtractor extractor = null;
	        try
	        {

	            file = new File("/home/mohade/workspace/gov_policy_analytics/resources/datasets/policies/su_sample_policy_template.doc");
	            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
	            HWPFDocument document = new HWPFDocument(fis);
	            extractor = new WordExtractor(document);
	            String[] fileData = extractor.getParagraphText();
	            for (int i = 0; i < fileData.length; i++)
	            {
	                if (fileData[i] != null)
//	                	 System.out.print("{\"text\":\"");
	                System.out.print(fileData[i]);
//					 System.out.print("\"}");
	            
	            }
	        }
	        catch (Exception exep)
	        {
	            exep.printStackTrace();
	        }
	    }
	}


