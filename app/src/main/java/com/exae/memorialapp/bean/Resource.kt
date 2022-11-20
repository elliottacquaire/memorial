package com.exae.memorialapp.bean

data class Resource<out T>(
  val status: Status,
  val data: T?,
  val message: String?,
  val code: String? = null
) {
  fun isSuccess(): Boolean = status == Status.SUCCESS

  fun isFail(): Boolean = status == Status.ERROR


  companion object {
    fun <T> success(data: T?): Resource<T> {
      return Resource(Status.SUCCESS, data, null)
    }

    fun <T> error(msg: String, data: T?, code: String?): Resource<T> {

      return Resource(Status.ERROR, data, msg, code)
    }

    fun <T> loading(data: T?): Resource<T> {
      return Resource(Status.LOADING, data, null)
    }
  }
}