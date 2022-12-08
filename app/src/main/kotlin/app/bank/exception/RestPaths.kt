package app.bank.exception

import com.jayway.jsonpath.PathNotFoundException

enum class RestPaths(val path: String) {
    GET_USER("/user/{id}"),
    EDIT_USER("/user/{id}"),
    ADD_USER("/user"),
    LIST_USER("/user"),
    VALIDATE("/validate/{msg}");
    companion object {
        fun checkPathExists(path: String) = getPath(path) ?: throw PathNotFoundException(path)

        fun getPath(path: String): RestPaths? = values().find { it.path == path }
    }
}
