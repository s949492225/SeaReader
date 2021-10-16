package com.syiyi.reader.repository

import com.syiyi.reader.model.Source


interface SourceRepository {

    suspend fun add(source: Source)

    suspend fun delete(source: Source)

    suspend fun add(sourceList: List<Source>)

    suspend fun list(): List<Source>

    suspend fun inflateScript(source: Source): String
}