package app.bank.config

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.github.dynamobee.Dynamobee

object Configuration {

    const val TABLE_NAME: String = "USER_OPS"
    private const val DB_MIGRATION: String = "DB_MIGRATION"
    private lateinit var ddbClient: AmazonDynamoDB

    init {
        initDdbClient()
        runChangeSets()
    }

    fun getDdbClient(): AmazonDynamoDB {
        return ddbClient
    }

    private fun initDdbClient() {
        ddbClient = AmazonDynamoDBClientBuilder.standard().build()
        if(isEnabledDbMigration()){
            runChangeSets()
        }
    }
    private fun runChangeSets(){
        val runner = Dynamobee(ddbClient)
        runner.setChangelogTableName("${TABLE_NAME}-DBCHANGELOG")
        runner.setChangeLogsScanPackage("app.bank.dbchangelogs")
        runner.execute()
    }

    private fun isEnabledDbMigration(): Boolean {
        return getSetting(DB_MIGRATION, true)
            .toBoolean()
    }

    private fun getSetting(key: String, required: Boolean = true): String {
        val value = System.getProperty(key, System.getenv(key))
        if (value == null || value.isBlank()) {
            if (required) throw IllegalArgumentException("setting $key is required")
            else return ""
        }
        return value
    }

}
