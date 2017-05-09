package insight.masters.policyanalytics.services;


import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.json.DataObjectFactory;



	
	@SuppressWarnings("deprecation")
	public class TCOLLECTORSTREAM extends Thread{


//		BasicDBList tweetsJsonArray = new BasicDBList();
//		BasicDBObject tweetJson = new BasicDBObject();
//		int Counter = 0;
//		TwitterObjectFactory t;
//		ArrayList<String> Keywords;
//		int TargetedTweetsVolume = NewsWire2.TargetedTweetsVolume+1;


//		public TweetStreamSearch(ArrayList<String> keywordsList) {
//			// store parameter for later user
//			this.Keywords = keywordsList;
//
//		}

//		public BasicDBList get_tweets() {
//			// returning recent tweets
//			return this.tweetsJsonArray;
//
//		}

//		public void run() {
public static void main (String[] args){
//			Thread t = Thread.currentThread();
//			System.out.println("Thread started: " + t.getName());

//			Counter = 0;
//			tweetsJsonArray.clear();

			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true);
			cb.setOAuthConsumerKey("HSh0WjRjn6ItTM3MCKrPyWVDb");
			// HSh0WjRjn6ItTM3MCKrPyWVDb
			// bvZgp3YGaiOurqe2t4RTnE5zp
			// 61IW6OoqWxjdNh8wKaT1piKin
			cb.setOAuthConsumerSecret("edRqBjXg4TbqLW0vtebYJF7GBtFiRpJkeBf5BvKoBdjrzPz2eD");
			// edRqBjXg4TbqLW0vtebYJF7GBtFiRpJkeBf5BvKoBdjrzPz2eD
			// RB0AP6FWeoTw7KQ7uKKoEG8fri9T5Ioee0upPGmWco5u4av8tn
			// 5OmSccbp1kxPLhe3OHjrE9cGpbmH17g1OgdxMJX0bSPwh3Fzvp
			cb.setOAuthAccessToken("2297213312-KbRf1SPnjMfuLZuIKR6uFDCBVEs1GJ6MpzAGaBu");
			// 2297213312-KbRf1SPnjMfuLZuIKR6uFDCBVEs1GJ6MpzAGaBu
			// 2297213312-jwUiUKTPSLqOiGpLQHnDgYSH7ocldUSufL2bDQE
			// 2297213312-gDkwBHBksmVSOwcIeOIbGWl1yagCeYsbz0maqzZ
			cb.setOAuthAccessTokenSecret("djnero4OcdvglBXADQ7FlDa34SaE2z1DHqYnyAgE4HS52");
			// djnero4OcdvglBXADQ7FlDa34SaE2z1DHqYnyAgE4HS52
			// CUQV9MMTyfZ4RA7AwbSTVPRSL3rLmKrmB093PzbRPhFGa
			// xwo3h1d92Mo8RvDFH96AX9jOB6yGGnp6P8AtrhVqZskeG


			TwitterStream twitterStream = new TwitterStreamFactory(cb.build())
			.getInstance();
	        StatusListener listener = new StatusListener() {

	        	 @Override
	             public void onException(Exception arg0) {
	                 // TODO Auto-generated method stub

	             }

	             @Override
	             public void onDeletionNotice(StatusDeletionNotice arg0) {
	                 // TODO Auto-generated method stub

	             }

	             @Override
	             public void onScrubGeo(long arg0, long arg1) {
	                 // TODO Auto-generated method stub

	             }

	             @Override
	             public void onStatus(Status status) {
	                 User user = status.getUser();
	                 
	                 // gets Username
	                 String username = status.getUser().getScreenName();
	                 System.out.println(username);
	                 String profileLocation = user.getLocation();
	                 System.out.println(profileLocation);
	                 long tweetId = status.getId(); 
	                 System.out.println(tweetId);
	                 String content = status.getText();
	                 System.out.println(content +"\n");
	                 
	                 System.out.println(status +"\n");
//	                 String statusJson = DataObjectFactory.getRawJSON(status);
	                 System.out.println(TwitterObjectFactory.getRawJSON(status)+"\n");


	             }

	             @Override
	             public void onTrackLimitationNotice(int arg0) {
	                 // TODO Auto-generated method stub

	             }

				@Override
				public void onStallWarning(StallWarning arg0) {
					// TODO Auto-generated method stub
					
				}

	         };

			FilterQuery fq = new FilterQuery();
		    
	        String keywords[] = {"ireland"};

	        fq.track(keywords);

	        twitterStream.addListener(listener);
	        twitterStream.filter(fq);  
		}

	}



