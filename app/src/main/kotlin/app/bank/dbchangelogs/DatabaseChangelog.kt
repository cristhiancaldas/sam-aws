package app.bank.dbchangelogs

import app.bank.bean.User
import app.bank.config.Configuration
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import com.amazonaws.services.dynamodbv2.model.UpdateItemRequest
import com.github.dynamobee.changeset.ChangeLog
import com.github.dynamobee.changeset.ChangeSet

@ChangeLog
class DatabaseChangelog {

    @ChangeSet(order = "001", id = "add_active_values", author = "ccalda@gmail.com")
    fun addActiveValues(dbClient: AmazonDynamoDB) {
        val dbMapper = DynamoDBMapper(dbClient)
        val newActiveValue = "1"

        val users = dbMapper.scan(
            User::class.java,
            DynamoDBScanExpression().withProjectionExpression("ID_USER_PK")
        )

        users.forEach {
            dbClient.updateItem(
                UpdateItemRequest()
                    .withTableName(Configuration.TABLE_NAME)
                    .withKey(mapOf("ID_USER_PK" to AttributeValue().withN(it.id.toString())))
                    .withUpdateExpression("SET ACTIVE = :activeValue")
                    .withExpressionAttributeValues(mapOf(":activeValue" to AttributeValue().withS(newActiveValue)))
            )
        }
    }
}
