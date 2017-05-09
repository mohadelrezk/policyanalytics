package insight.masters.policyanalytics.model;

import com.mongodb.BasicDBObject;

/**
 * @Autho Naddao Thongsak.
 * @contain Model Class for data and behavior of category object which contain
 *          the discovered Named Entities to be stored in the database.
 * 
 *          This class for creating extracted entities - name and category -
 *          list to be appended to the mongo db object
 * 
 * 
 *
 */
public class Category extends BasicDBObject {

	private static final long serialVersionUID = 1L;

	/**
	 * @return
	 */
	public String getName() {
		return (String) super.get("name");
	}

	/**
	 * @return
	 */
	public String getValue() {
		return (String) super.get("value");
	}

	/**
	 * @return
	 */
	public String getDisplayName() {
		return (String) super.get("displayName");
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		super.put("name", name);
	}

	/**
	 * @param value
	 */
	public void setValue(String value) {
		super.put("value", value);
	}

	/**
	 * @param displayName
	 */
	public void setDisplayName(String displayName) {
		super.put("displayName", displayName);
	}
}
