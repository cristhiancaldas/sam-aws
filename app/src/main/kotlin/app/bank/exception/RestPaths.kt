package app.bank.exception

import com.jayway.jsonpath.PathNotFoundException

enum class RestPaths(val path: String){
    USER("/user"),
    VALIDATE("/validate");
    companion object {
        fun checkPathExists(path: String) = getPath(path) ?: throw PathNotFoundException(path)

        fun getPath(path: String): RestPaths? = values().find { it.path == path }
    }
}
