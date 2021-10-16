@file:Suppress("BlockingMethodInNonBlockingContext")

package com.syiyi.reader.repository

import com.syiyi.reader.model.Source
import com.syiyi.reader.util.toJson
import com.syiyi.reader.util.toModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import java.io.File

class LocalSourceRepository(cacheRoot: File) : SourceRepository {

    private val cacheDir = File(cacheRoot, "source")
    private val writeMutex = Mutex()

    init {
        if (!cacheDir.exists() || cacheDir.isFile) {
            cacheDir.mkdirs()
        }
    }

    override suspend fun add(source: Source) = withContext(Dispatchers.IO) {
        writeMutex.withLock {
            //创建源文件
            val sourceFile = File(cacheDir, "${source.key}.source")
            if (!sourceFile.exists()) {
                val success = sourceFile.createNewFile()
                check(success) { "源文件创建失败,请检查磁盘是否故障" }
            }
            //写入源
            sourceFile.writeText(source.toJson())
        }

    }

    override suspend fun add(sourceList: List<Source>) = withContext(Dispatchers.IO) {
        writeMutex.withLock {
            sourceList.forEach { source ->
                //创建源文件
                val sourceFile = File(cacheDir, "${source.key}.source")
                if (!sourceFile.exists()) {
                    val success = sourceFile.createNewFile()
                    check(success) { "源文件创建失败,请检查磁盘是否故障" }
                }
                //写入源
                sourceFile.writeText(source.toJson())
            }
        }

    }

    override suspend fun delete(source: Source) = withContext(Dispatchers.IO) {
        writeMutex.withLock {
            File(cacheDir, "${source.key}.source").deleteOnExit()
        }
    }

    suspend fun deleteAll() = withContext(Dispatchers.IO) {
        writeMutex.withLock {
            cacheDir.listFiles()?.forEach { it.deleteOnExit() }
        }
    }


    override suspend fun list(): List<Source> = withContext(Dispatchers.IO) {
        val list = cacheDir.listFiles()

        if (list.isNullOrEmpty()) {
            return@withContext emptyList()
        }

        list.sortBy { it.name }

        return@withContext list.map { file ->
            file.readText().toModel<Source>()!!.apply {
                script = null
            }
        }
    }

    override suspend fun inflateScript(source: Source): String = withContext(Dispatchers.IO) {
        val file = File(cacheDir, "${source.key}.source")

        if (!file.exists()) {
            throw RuntimeException("source文件丢失")
        }

        return@withContext file.readText().toModel<Source>()!!.script!!
    }
}