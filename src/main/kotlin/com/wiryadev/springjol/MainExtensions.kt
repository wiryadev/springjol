package com.wiryadev.springjol

inline fun <reified T> T?.orThrow(): T {
    return this ?: throw CustomException("Value ${T::class.simpleName} is null")
}

inline fun <reified T> T?.toResult(): Result<T> {
    return if (this != null) {
        Result.success(this)
    } else {
        Result.failure(CustomException("Value ${T::class.simpleName} is null"))
    }
}

fun <T> Result<T>.toResponse(message: String? = null): BaseResponse<T> {
    return if (isFailure) {
//        BaseResponse.failure(message)
        throw CustomException(message ?: exceptionOrNull()?.message ?: "Unknown Failure")
    } else {
        BaseResponse.success(getOrNull())
    }
}