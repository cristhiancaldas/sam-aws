package app.bank.shared

import app.bank.config.LoggerDelegate
import app.bank.repository.UserRepository
import app.bank.repository.impl.UserRepositoryImpl

class LambdaWarmer (private val userRepository: UserRepository) {
    private val log by LoggerDelegate()
 companion object{
     val instance: LambdaWarmer = LambdaWarmer(
         UserRepositoryImpl.instance
     )
 }

    fun run (){
        try {
            userRepository.warMDdbConnection()
        } catch (ex: Exception) {
            log.error("Error de connection")
        }
    }
}