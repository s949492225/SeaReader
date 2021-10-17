package com.syiyi.reader.repository

import com.syiyi.reader.engine.JSEngine
import com.syiyi.reader.model.Book
import com.syiyi.reader.model.BookSection
import com.syiyi.reader.model.SourceCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 书源所有操作的获取逻辑
 */
@Singleton
class SourceExeRepository  @Inject constructor() {

    /**
     * 根据关键词搜索书籍
     * 此处会搜索所有书源
     * param keyword 关键词
     */
    suspend fun search(script: String, keyword: String): List<Book> = withContext(Dispatchers.IO) {
        val result: List<Book>? = JSEngine.execute(script, "search", keyword)
        if (result.isNullOrEmpty()) {
            emptyList()
        } else {
            result
        }
    }


    /**
     * 获取该类别下的书籍
     * 此处需要分页,如果返回的数量等于0则认为没有更多数据,每页返回的数量由书源去控制
     * param category 类别信息
     * param index 当前页面 ,从1开始
     */
    suspend fun searchCategory(category: SourceCategory, index: Int): List<Book> =
        withContext(Dispatchers.IO) {
            emptyList()
        }

    /**
     * 获取书本下的所有章节
     * 此处可以返回全部章节,也可以分页返回,由书源去决定
     * param book 书本信息
     * param index 当前页面 ,从1开始
     */
    suspend fun sectionList(book: Book): List<BookSection> =
        withContext(Dispatchers.IO) {
            emptyList()
        }

    /**
     * 获取章节的内容
     * param book 书本信息
     * param section 章节信息
     */
    suspend fun sectionContent(book: Book, section: BookSection): String? =
        withContext(Dispatchers.IO) {
            null
        }
}