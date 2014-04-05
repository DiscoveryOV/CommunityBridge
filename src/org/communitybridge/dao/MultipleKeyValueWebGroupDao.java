package org.communitybridge.dao;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static org.communitybridge.dao.WebGroupDao.EMPTY_LIST;
import org.communitybridge.main.Configuration;
import org.communitybridge.main.SQL;
import org.communitybridge.utility.Log;

public class MultipleKeyValueWebGroupDao extends WebGroupDao
{
	public static final String EXCEPTION_MESSAGE_GETSECONDARY = "Exception during MultipleKeyValueWebGroupDao.getSecondaryGroups(): ";

	public MultipleKeyValueWebGroupDao(Configuration configuration, SQL sql, Log log)
	{
		super(configuration, sql, log);
	}

	@Override
	public void addUserToGroup(String userID, String groupID, int currentGroupCount) throws IllegalAccessException, InstantiationException, MalformedURLException, SQLException
	{
		String query = "INSERT INTO `" + configuration.webappSecondaryGroupTable + "` "
								 + "(`" + configuration.webappSecondaryGroupUserIDColumn + "`, `" + configuration.webappSecondaryGroupKeyColumn + "`, `" + configuration.webappSecondaryGroupGroupIDColumn + "`) "
								 + "VALUES ('" + userID + "', '" + configuration.webappSecondaryGroupKeyName + "', '" + groupID + "')";
		sql.insertQuery(query);
	}

	@Override
	public void removeUserFromGroup(String userID, String groupID) throws IllegalAccessException, InstantiationException, MalformedURLException, SQLException
	{
		String query = "DELETE FROM `" + configuration.webappSecondaryGroupTable + "` "
								 + "WHERE `" + configuration.webappSecondaryGroupKeyColumn + "` = '" + configuration.webappSecondaryGroupKeyName + "' "
								 + "AND `" + configuration.webappSecondaryGroupGroupIDColumn + "` = '" + groupID + "' ";
		sql.deleteQuery(query);
	}

	@Override
	public List<String> getSecondaryGroupIDs(String userID) throws MalformedURLException, InstantiationException, IllegalAccessException, SQLException
	{
		List<String> groupIDs = new ArrayList<String>();

		if (!configuration.webappSecondaryGroupEnabled)
		{
			return EMPTY_LIST;
		}

		String query =
						"SELECT `" + configuration.webappSecondaryGroupGroupIDColumn + "` "
					+ "FROM `" + configuration.webappSecondaryGroupTable + "` "
					+ "WHERE `" + configuration.webappSecondaryGroupUserIDColumn + "` = '" + userID + "' "
					+ "AND `" + configuration.webappSecondaryGroupKeyColumn + "` = '" + configuration.webappSecondaryGroupKeyName + "' ";

			result = sql.sqlQuery(query);

			while (result.next())
			{
				addCleanID(result.getString(configuration.webappSecondaryGroupGroupIDColumn), groupIDs);
			}
			return groupIDs;
	}

	@Override
	public List<String> getSecondaryGroupUserIDs(String groupID)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
