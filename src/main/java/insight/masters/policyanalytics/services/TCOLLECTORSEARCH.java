package insight.masters.policyanalytics.services;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;

public class TCOLLECTORSEARCH {

	/**
	 * @author Yusuke Yamamoto - yusuke at mac.com
	 * @since Twitter4J 2.1.7
	 */

	/**
	 * Usage: java twitter4j.examples.search.SearchTweets [query]
	 *
	 * @param args
	 *            search query
	 */
	public static void main(String[] args) {
		// if (args.length < 1) {
		// System.out.println("java twitter4j.examples.search.SearchTweets [query]");
		// System.exit(-1);
		// }

		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey("bvZgp3YGaiOurqe2t4RTnE5zp");
		// HSh0WjRjn6ItTM3MCKrPyWVDb
		// bvZgp3YGaiOurqe2t4RTnE5zp
		// 61IW6OoqWxjdNh8wKaT1piKin
		cb.setOAuthConsumerSecret("RB0AP6FWeoTw7KQ7uKKoEG8fri9T5Ioee0upPGmWco5u4av8tn");
		// edRqBjXg4TbqLW0vtebYJF7GBtFiRpJkeBf5BvKoBdjrzPz2eD
		// RB0AP6FWeoTw7KQ7uKKoEG8fri9T5Ioee0upPGmWco5u4av8tn
		// 5OmSccbp1kxPLhe3OHjrE9cGpbmH17g1OgdxMJX0bSPwh3Fzvp
		cb.setOAuthAccessToken("2297213312-jwUiUKTPSLqOiGpLQHnDgYSH7ocldUSufL2bDQE");
		// 2297213312-KbRf1SPnjMfuLZuIKR6uFDCBVEs1GJ6MpzAGaBu
		// 2297213312-jwUiUKTPSLqOiGpLQHnDgYSH7ocldUSufL2bDQE
		// 2297213312-gDkwBHBksmVSOwcIeOIbGWl1yagCeYsbz0maqzZ
		cb.setOAuthAccessTokenSecret("CUQV9MMTyfZ4RA7AwbSTVPRSL3rLmKrmB093PzbRPhFGa");
		// djnero4OcdvglBXADQ7FlDa34SaE2z1DHqYnyAgE4HS52
		// CUQV9MMTyfZ4RA7AwbSTVPRSL3rLmKrmB093PzbRPhFGa
		// xwo3h1d92Mo8RvDFH96AX9jOB6yGGnp6P8AtrhVqZskeG

		
		String [] keys={
				"Pennsylvania",
				"Trump",
				"﻿Donald_Trump",
				"Ted_Cruz",
				"California",
				"Brooks",
				"Joe_Schmo",
				"Indiana",
				"Illinois",
				"New_York",
				"Nate_Cohn",
				"Puerto_Rico",
				"West_Virginia",
				"Ivanka",
				"Donald_Jr.",
				"Ivanka_Trump",
				"Trump_Tower",
				"Trump",
				"Kushner",
				"Jared_Kushner",
				"CNN",
				"Eric",
				"New_Jersey",
				"Don_Jr.",
				"Charles_Kushner",
				"Melania",
				"Washington",
				"Donald_J._Trump",
				"New_York_Times",
				"Donald",
				"Megyn_Kelly",
				"Republican_Party",
				"Don_Rickles",
				"Jeb",
			"United_States",
				"Jeb_Bush",
				"Manhattan",
				"Democrat_Party",
				"Hope_Hicks",
				"Wisconsin",
				"Donald_J._Trump_Jr.",
				"MAGGIE_HABERMANAPRIL",
				"America",
				"ASHLEY_PARKER",
				"China",
				"Iraq",
				"Baghdad",
				"Syria",
				"Andrew_Rae_HYPERLINK",
				"White_House",
				"Andrew_RaeI",
				"United_States.I",
				"Zeckendorf",
				"ADAM_DAVIDSON",
				"Milstein",
				"Saddam_Hussein",
				"Durst",
				"Tishman",
				"Rose",
				"LeFrak",
				"Richard_LeFrak",
				"Fred",
				"Scarborough",
				"Al_Sharpton",
				"Chris_Christie",
				"Florida",
				"Joe_Scarborough",
				"Wallach",
				"Regis_Philbin",
				"John_Angelo",
				"Vector_Group",
				"New_York_Yankee",
				"Sater",
				"rump_SoHo",
				"Arif",
				"Bayrock",
				"Bayrock",
				"Garten",
			"Bailey",
				"Kazakhstan",
				"Russia",
				"Tevfik_Arif",
				"F.B.I.",
				"Felix_H._Sater",
				"Lauria",
				"Benito_Mussolini",
				"Heidi_Cruz",
				"Tiffany",
				"Melania_Trump",
				"S.C.",
				"Republican_Party",
				"Twitter",
				"Anderson_Cooper",
				"Marla_Maples",
				"Barron",
				"Chelsea_Clinton",
				"Facebook",
				"Western_New_York",
				"Mexico",
				"Obama",
				"Hillary_Clinton",
				"Rochester",
				"United_States.America",
				"N.Y.",
				"John_Kasich",
				"Congress",
				"Erie_County",
				"Rubinstein",
				"Central_Park_South",
				"Rozenholc",
				"New_York_City",
				"Blackmer",
				"CreditDanny_Ghitis",
				"Trump_Parc_East",
				"New_York_Post",
				"Putin",
				"Tishman_Speyer",
				"Iran",
				"Rockettes",
				"Central_Park_South",
				"Hudson_Yards",
				"Trump_University",
				"Dartmouth_College",
				"Anaheim",
				"Kaplan_College",
				"Anytown",
				"DeVry_University",
				"University_of_Technology_and_Management",
				"Cambridge",
				"Arlington",
				"Wilson_Boulevard",
				"Central_Utah_Vocational_School",
				"Strayer_College",
				"California_University",
				"Carey",
				"Strayer_University,organization",
				"Northern_State_University",
				"Va."
		};
		
		
		
		
		Twitter twitter = new TwitterFactory(cb.build()).getInstance();
		try {
			for (int i = 0; i < keys.length; i++) {
				Query query = new Query("Joe_Schmo " + keys[i]);
				query.setCount(20);
				query.setLang("en");
				query.setResultType(query.POPULAR);
				QueryResult result;
				// do {
				result = twitter.search(query);
				List<Status> tweets = result.getTweets();
				if (tweets.size() != 0) {
					System.out.println(";("+i+"----"+keys[i]+")***********************************");
					System.out.println(tweets.size());
					for (Status tweet : tweets) {
						// System.out.println(tweet.getId());
						System.out.println("@"
								+ tweet.getUser().getScreenName() + " - "
								+ tweet.getText());
					}
					for (Status tweet : tweets) {
						System.out.println(tweet.getId());
						// System.out.println("@" +
						// tweet.getUser().getScreenName() + " - " +
						// tweet.getText());
					}
					// } while ((query = result.nextQuery()) != null);
					// System.exit(0);
				} else {

					query.setResultType(query.MIXED);
					result = twitter.search(query);
					List<Status> tweets2 = result.getTweets();
					System.out.println(";("+i+"----"+keys[i]+")***********************************");

					System.out.println(tweets2.size());
					for (Status tweet : tweets2) {
						// System.out.println(tweet.getId());
						System.out.println("@"
								+ tweet.getUser().getScreenName() + " - "
								+ tweet.getText());
					}
					for (Status tweet : tweets2) {
						System.out.println(tweet.getId());
						// System.out.println("@" +
						// tweet.getUser().getScreenName() + " - " +
						// tweet.getText());

					}
					// System.exit(0);
				}
			}
			System.exit(0);
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets: " + te.getMessage());
			System.exit(-1);
		}
	}
}
