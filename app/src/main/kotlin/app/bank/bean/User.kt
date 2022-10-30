package app.bank.bean

import app.bank.config.Configuration
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import kotlinx.serialization.Serializable

@Serializable
@DynamoDBTable(tableName = Configuration.TABLE_NAME)
data class User(

    @DynamoDBHashKey(attributeName = "ID_USER_PK")
    var id: Long = -1,

    @DynamoDBAttribute(attributeName = "FIRST_NAME")
    var firstName: String = "",

    @DynamoDBAttribute(attributeName = "LAST_NAME")
    var lastName: String = "",

    @DynamoDBAttribute(attributeName = "EMAIL")
    var email: String = "",

    @DynamoDBAttribute(attributeName = "PHONE")
    var phone: String = "",

    @DynamoDBAttribute(attributeName = "COUNTRY")
    var country: String = "",

    @DynamoDBAttribute(attributeName = "REGION")
    var region: String = "",

    @DynamoDBAttribute(attributeName = "ACTIVE")
    var active: String = "1"


)
