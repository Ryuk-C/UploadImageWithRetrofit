package com.haznedar.uploadimage.util

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> internet(data: T?): Resource<T> {
            return Resource(Status.INTERNET, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

    }
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING,
    INTERNET
}