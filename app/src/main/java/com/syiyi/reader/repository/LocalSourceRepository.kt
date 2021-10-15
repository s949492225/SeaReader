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
            cacheDir.mkdir()
        }
    }

    override suspend fun add(source: Source) = withContext(Dispatchers.IO) {
        writeMutex.withLock {
            val list = cacheDir.listFiles()
            //找到最后一个源文件
            var lastIndex = 0
            if (!list.isNullOrEmpty()) {
                list.sortBy { it.name }
                val lastName = list.last().nameWithoutExtension
                lastIndex = lastName.toInt()
            }

            //创建源文件
            val sourceFile = File(cacheDir, "${source.name}.source")
            sourceFile.deleteOnExit()
            val success = sourceFile.createNewFile()
            check(success) { "源文件创建失败,请检查磁盘是否故障" }

            //写入源
            sourceFile.writeText(source.toJson())
        }

    }

    override suspend fun add(sourceList: List<Source>) = withContext(Dispatchers.IO) {
        writeMutex.withLock {
            sourceList.forEach { source ->
                //创建源文件
                val sourceFile = File(cacheDir, "${source.name}.source")
                sourceFile.deleteOnExit()
                val success = sourceFile.createNewFile()
                check(success) { "源文件创建失败,请检查磁盘是否故障" }

                //写入源
                sourceFile.writeText(source.toJson())
            }
        }

    }

    override suspend fun delete(source: Source) = withContext(Dispatchers.IO) {
        writeMutex.withLock {
            File(cacheDir, "${source.name}.source").deleteOnExit()
        }
    }


    override suspend fun list(): List<Source> = withContext(Dispatchers.IO) {
        val list = cacheDir.listFiles()

        if (list.isNullOrEmpty()) {
            return@withContext emptyList()
        }

        list.sortBy { it.name }

        return@withContext list.map { file ->
            file.readText().toModel<Source>()!!
        }
    }
}