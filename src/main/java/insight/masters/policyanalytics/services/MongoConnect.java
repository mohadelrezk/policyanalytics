package insight.masters.policyanalytics.services;

import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Level;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

public class MongoConnect {

	static MongoClient mongoclient;
	BasicDBObject query = new BasicDBObject();

	public static void init() {

		MongoConnect.connect();
	}

	public static void main(String[] args) throws UnknownHostException {
		// MongoConnect m = new MongoConnect();
		MongoConnect.init();

	}

	public static void connect() {

		try {
			mongoclient = new MongoClient("localhost", 27017);
		} catch (Exception e) {
			PolicyAnalytics.logger.log(Level.SEVERE,
					"Not connected to DataBase!" + e.toString(), e);
		}
		PolicyAnalytics.logger.log(Level.INFO, "connected to DataBase!");

		/**
		 * secure mode: 
		 * MongoClient mongoClient = new MongoClient(); DB db =
		 * mongoClient.getDB("database name"); boolean auth =
		 * db.authenticate("username", "password".toCharArray());
		 */
		
		/** just listing existing dbs in the mongo instance */
		List<String> dbs = mongoclient.getDatabaseNames();
		for (String Db : dbs) {
			PolicyAnalytics.logger.log(Level.INFO, Db);
		}

	}

	public void Create_DB(String dbname) {
		// if doesn't exist mongo will create one
		DB db = mongoclient.getDB(dbname);

	}

	public void Create_Collection(String dbname, String collectionname) {

		DB db = mongoclient.getDB("dbname");
		DBCollection table = db.getCollection("collectionname");

	}

	public void Insert_JsonString(String json, String dbname,
			String collectionname) {

		DBObject dbObject = (DBObject) JSON.parse(json);
		DB db = mongoclient.getDB("dbname");
		DBCollection table = db.getCollection("collectionname");
		table.insert(dbObject);
	}

	public void Insert_DBObject(BasicDBObject entry, String dbname,
			String collectionname) {

		DBObject dbObject = entry;

		DB db = mongoclient.getDB(dbname);
		DBCollection table = db.getCollection(collectionname);
		table.insert(dbObject);
	}

	public void Update_DBObject(BasicDBObject entry, BasicDBObject query,
			String dbname, String collectionname) {

		DBObject dbObject = entry;
		DB db = mongoclient.getDB(dbname);
		DBCollection table = db.getCollection(collectionname);
		table.update(query, dbObject);
	}

	public static void Save_DBObject(BasicDBObject entry, String dbname,
			String collectionname) {

		DBObject dbObject = entry;
		DB db = mongoclient.getDB(dbname);
		DBCollection table = db.getCollection(collectionname);
		table.save(dbObject);
		table.createIndex(new BasicDBObject("RSSfeed.pubDate", 1));
	}

	/**@deprecated
	 * Retrieve News Wire Feeds "By Providing its ID", Sorted by Retweeted Count
	 * (not working fine) Using Map/Reduce Function
	 * 
	 * 
	public static String get_SortedPolicyAnalytics_ByDate(String dbname,
			String collectionname, int Asce_or_Desc) {

		String SortedPolicyAnalytics_ByDate = "";
		DB db = mongoclient.getDB(dbname);
		DBCollection table = db.getCollection(collectionname);
		BasicDBObject Sort_ByPubDate = new BasicDBObject().append(
				"RSSfeed.pubDate", Asce_or_Desc);
		try {
//			table.createIndex(new BasicDBObject("RSSfeed.pubDate", 1));
			DBCursor response_cursor = table.find().sort(Sort_ByPubDate);
			SortedPolicyAnalytics_ByDate += response_cursor.toArray().toString();
		} catch (Exception e) {
			PolicyAnalytics.logger.log(Level.SEVERE, "At get_SortedPolicyAnalytics_ByDate"
					+ e.toString(), e);
			SortedPolicyAnalytics_ByDate = "Wrong Parameters!";
		}

		return SortedPolicyAnalytics_ByDate;

	}
*/
	/**
	 * Retrieve News Wire Feeds Headers, Sorted by Publication Date
	 * */
	public static String get_SortedNewsHeaders_ByDate(String dbname,
			String collectionname, int Asce_or_Desc) {
		String SortedNewsHeaders_ByDate = "";

		DB db = mongoclient.getDB(dbname);
		DBCollection table = db.getCollection(collectionname);
		BasicDBObject whereQuery_all = new BasicDBObject();
		BasicDBObject fields = new BasicDBObject();
		fields.put("RSSfeed.pubDate", 1);
		fields.put("RSSfeed.title", 1);
		BasicDBObject Sort_NewsHeaders_ByDate = new BasicDBObject().append(
				"RSSfeed.pubDate", Asce_or_Desc);
		try {
			
			DBCursor cursor = table.find(whereQuery_all, fields).sort(
					Sort_NewsHeaders_ByDate);
			SortedNewsHeaders_ByDate += cursor.toArray().toString();

		} catch (Exception e) {
			PolicyAnalytics.logger.log(Level.SEVERE,
					"At get_SortedNewsHeaders_ByDate" + e.toString(), e);
			SortedNewsHeaders_ByDate = "Wrong Parameters!";
		}
		return SortedNewsHeaders_ByDate;

	}

	/**
	 * Retrieve Tweets related to a certain News Wire Feed
	 * "By Providing News Wire Feed ID", Sorted by Date
	 * */
	//TODO adding sorting query by tweet date
	public static String get_SortedNewsFeedTweets_ByDate(String dbname,
			String collectionname, String rssID, int Asce_or_Desc) {
		String SortedNewsFeedTweets_ByDate = "";
		DB db = mongoclient.getDB(dbname);
		DBCollection table = db.getCollection(collectionname);
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("_id", rssID);
		BasicDBObject fields = new BasicDBObject();
		fields.put("recent_tweets", 1);
		try {
			DBCursor tweets = table.find(whereQuery, fields);
			SortedNewsFeedTweets_ByDate += tweets.toArray().toString();
		} catch (Exception e) {
			PolicyAnalytics.logger.log(Level.SEVERE,
					"At get_SortedNewsFeedTweets_ByDate" + e.toString(), e);
			SortedNewsFeedTweets_ByDate = "Wrong Parameters!";
		}
		return SortedNewsFeedTweets_ByDate;

	}

	/**
	 * Retrieve Top Retweeted Tweets related to a certain News Wire Feed
	 * "By Providing its ID", Sorted by Retweeted Count Using Map/Reduce
	 * Function
	 * 
	 * */
	public static String get_TopRetweetedMapReduce(String dbname,
			String collectionname, String rssID, int Asce_or_Desc) {

		String TopRetweetedMapReduce = "";
		DBObject Sort_ByRetweetCount_Value = new BasicDBObject("value",
				Asce_or_Desc);
		DB db = mongoclient.getDB(dbname);
		DBCollection collection = db.getCollection(collectionname);

		String map = "function () {"
//				+ "var hamo = ;"
				+ "if(this._id.indexOf("+"\""+rssID+"\""+") > -1)"
				+ "{"
				+ "if(this.recent_tweets!=null)"
				+ "{"
				+ "for (var idx = 0; idx < this.recent_tweets.length; idx++)"
				+ "{"
				+ "        if(this.recent_tweets[idx].retweeted_status!=null)"
				+ "     {"
				+ "        if(this.recent_tweets[idx].retweeted_status.retweet_count!=null)"
				+ "{"
				+ "        	var key =  this.recent_tweets[idx].retweeted_status.text;"
				+ "        	var value =  this.recent_tweets[idx].retweeted_status.retweet_count;"
				+ "        	emit(key, value);" + "        }" + "    }" + "}"
				+ "}" + "}" + "}";
		String reduce = "function (key, values) { "
				+ "return Array.sum(values); }";

		String outputCollectionName = String.format("%s_%s_%s_%s", dbname,
				collectionname, "tweetcount", "sorted");
		try {
			MapReduceCommand cmd = new MapReduceCommand(collection, map,
					reduce, outputCollectionName,
					MapReduceCommand.OutputType.REPLACE, null);
			MapReduceOutput out = collection.mapReduce(cmd);
			DBCollection outputCollection = db
					.getCollection(outputCollectionName);
			// outputCollection.ensureIndex(new BasicDBObject("_id", 1));

			/** sorting resulted aggregations part */
			DBCursor TopRetweetedCollection_cursor = db.getCollection(
					outputCollectionName).find();
			TopRetweetedCollection_cursor.sort(Sort_ByRetweetCount_Value);

			/** applying the threshold top 25 */
			DBObject obj = new BasicDBObject();

			if (TopRetweetedCollection_cursor.hasNext()) {

				int i = 0;
				while (i < 25 && i < TopRetweetedCollection_cursor.count()) {

					obj = TopRetweetedCollection_cursor.next();

					if (obj.get("_id") != null) {
						PolicyAnalytics.logger.log(Level.INFO, obj.get("_id")
								.toString());
						PolicyAnalytics.logger.log(Level.INFO, obj.get("value")
								.toString());
						TopRetweetedMapReduce += "\n"
								+ obj.get("_id").toString();
						TopRetweetedMapReduce += "\n"
								+ obj.get("value").toString();

						i++;
					}
				}

			}
			outputCollection.drop();
		} catch (Exception e) {
			PolicyAnalytics.logger.log(Level.SEVERE, "At get_TopRetweetedMapReduce"
					+ e.toString(), e);
			TopRetweetedMapReduce = "Wrong Parameters!";
		}

		return TopRetweetedMapReduce;

	}
	/**
	 * Retrieve Top Images related to a certain News Wire Feed
	 * "By Providing its ID", Sorted by Appearing count Using Map/Reduce
	 * Function
	 * 
	 * */
	//TODO coding it!!
	public static String get_TopImagesMapReduce(String dbname,
			String collectionname, String rssID, int Asce_or_Desc) {

		String TopImagesMapReduce = "";

		DBObject Sort_ByRepeating_Value = new BasicDBObject("value",
				Asce_or_Desc);

		DB db = mongoclient.getDB(dbname);
		DBCollection collection = db.getCollection(collectionname);

		String map = "function () {" + "if(this.entities.media!=null){"
				+ "if(this.entities.media[0].media_url!=null){"
				+ "emit(this.entities.media[0]." + "media_url" + ", 1);" + "}"
				+ "}" + "}";
		String reduce = "function (key, values) { "
				+ "return Array.sum(values); }";

		String outputCollectionName = String.format("%s_%s_%s_%s", dbname,
				collectionname, "media_url", "sorted");
		try {
			MapReduceCommand cmd = new MapReduceCommand(collection, map,
					reduce, outputCollectionName,
					MapReduceCommand.OutputType.REPLACE, null);
			// outputCollection.drop();
			MapReduceOutput out = collection.mapReduce(cmd);

			DBCollection outputCollection = db
					.getCollection(outputCollectionName);
			// outputCollection.ensureIndex(new BasicDBObject("_id", 1));

			/** sorting resulted aggregations part */
			DBCursor TopImagesCollection_cursor = db.getCollection(
					outputCollectionName).find();
			TopImagesCollection_cursor.sort(Sort_ByRepeating_Value);
			/** applying the threshold top 25 */
			DBObject obj = new BasicDBObject();

			if (TopImagesCollection_cursor.hasNext()) {

				int i = 0;
				while (i < 25 && i < TopImagesCollection_cursor.count()) {
					obj = TopImagesCollection_cursor.next();
					if (obj.get("_id") != null) {
						System.out.println(obj.get("_id").toString());
						System.out.println(obj.get("value").toString());
						TopImagesMapReduce += "\n" + obj.get("_id").toString();
						TopImagesMapReduce += "\n"
								+ obj.get("value").toString();
						i++;
					}
				}

			}
			outputCollection.drop();

		} catch (Exception e) {
			PolicyAnalytics.logger.log(Level.SEVERE,
					"At get_TopImagesMapReduce" + e.toString(), e);
			TopImagesMapReduce = "Wrong Parameters!";
		}
		return TopImagesMapReduce;

	}
}

/**
 * db.neon2.aggregate([ { $match: {_id: ""}}, { $project: { recent_tweets:
 * {$filter: { input: '$list', as: 'item', cond: {$exists:
 * ['$$item.retweeted_status', true]} }} }} ])
 * 
 * db.neon2.aggregate( { $match: {_id: "world-europe-35413384"}}, { $unwind:
 * '$recent_tweets'}, { $match: {'recent_tweets.retweeted_status': {$exists:
 * true}}}, { $group: {_id: '$_id', recent_tweets: {$push: '$recent_tweets'}},
 * {$sort : {total : -1}})
 * 
 * db.neon2.find({_id:"world-europe-35413384",
 * 'recent_tweets.retweeted_status':{$exists:true}},{'recent_tweets.retweeted_status.retweet_count':1}
 * ) . s o r t ( { 'recent_tweets.retweeted_status.retweet_count': -1 } )
 */
