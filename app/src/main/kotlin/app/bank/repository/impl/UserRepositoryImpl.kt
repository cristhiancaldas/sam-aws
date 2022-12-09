package app.bank.repository.impl

import app.bank.bean.User
import app.bank.config.Configuration
import app.bank.config.LoggerDelegate
import app.bank.config.UserFilter
import app.bank.repository.SaveUserException
import app.bank.repository.UserNotFoundException
import app.bank.repository.UserRepository
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression

class UserRepositoryImpl(private val ddbClient: AmazonDynamoDB) : UserRepository {

    private val ddbMapper = DynamoDBMapper(ddbClient)
    private val log by LoggerDelegate()

    companion object {
        val instance: UserRepositoryImpl = UserRepositoryImpl(
            Configuration.getDdbClient()
        )
    }

    override fun getUser(id: Long): User {
        try {
            return ddbMapper.load(User::class.java, id)
        } catch (ex: Exception) {
            throw UserNotFoundException(id)
        }
    }

    override fun save(user: User): User {
        try {
            log.info("fun saveUser ::$user")
            ddbMapper.save<User>(user)
            return user
        } catch (ex: Exception) {
            throw SaveUserException(ex.message)
        }
    }

    override fun save(users: List<User>): Int {
        try {
            val result = ddbMapper.batchSave(users)
            val updatedRows = users.size - result.size
            log.debug(
                "Total rows : ${users.size} | Updated or Inserted rows count : $updatedRows" +
                        " | Failed rows count : ${result.size}"
            )
            return updatedRows
        } catch (ex: Exception) {
            log.error("Users could not be saved | Exception : ${ex.message}")
            throw SaveUserException(ex.message)
        }
    }


    override fun getUsers(userFilter: UserFilter): List<User> {
        val request = DynamoDBScanExpression()
            .withFilterExpression(userFilter.buildCriteriaExpression())
            .withExpressionAttributeValues(userFilter.buildCriteriaValues())
            .withExpressionAttributeNames(userFilter.buildCriteriaExpressionNames())

        return ddbMapper.scan(User::class.java, request)
    }

    override fun warMDdbConnection() {
        ddbMapper.load(User::class.java, -1L)
    }
}
