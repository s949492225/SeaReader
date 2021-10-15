@file:Suppress("unused")

package com.syiyi.reader.model

import com.squareup.moshi.JsonClass

/**
 * 书源
 */
@JsonClass(generateAdapter = true)
data class Source(
    var id: Int?,
    var name: String,//名称 例如起点中文网
    var key: String,//名称 例如起点中文网
    var categories: List<SourceCategory>,//类别列表 例如:热门,武侠,科幻这些都是类别
    var script: String?,//javascript脚本
)

/**
 * 书源中类别 例如:热门,武侠,科幻这些都是类别,最多具备两级
 */
@JsonClass(generateAdapter = true)
data class SourceCategory(
    var id: Int?,
    var name: String,//类别名称
    var extras: String?,
    var list: List<SourceCategory>?, //子类别 注意此处可以没有子类别
    var source: Source?,//当前归属的书源
)

/**
 * 书本概况
 */
@JsonClass(generateAdapter = true)
data class Book(
    var id: Int?,
    var name: String?,//书的名称
    var author: String?,//书的作者,
    var coverUrl: String?,//书的封面的地址
    var desc: String?,//书的描述
    var wordsNumber: String?,//字数 例如10.2万字
    var categoryDesc: String?,//类别说明 例如现代言情,完结
    var newsSection: String?,//当前最新章节
    var source: Source?,//当前归属的书源
    var sections: List<BookSection>?
)

/**
 * 书本章节
 */
@JsonClass(generateAdapter = true)
data class BookSection(
    var id: Int?,
    var name: String,//章节名称
    var content: String?,//内容
)