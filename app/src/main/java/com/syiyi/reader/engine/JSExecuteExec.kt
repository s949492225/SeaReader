package com.syiyi.reader.engine

import java.lang.RuntimeException

class JSExecuteException(message: String?, cause: Throwable? = null) :
    RuntimeException(message, cause)