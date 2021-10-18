package com.syiyi.reader

import android.content.Context
import com.syiyi.reader.engine.JSEngine
import com.syiyi.reader.model.Book
import com.syiyi.reader.model.Source
import com.syiyi.reader.repository.SourceRepository
import com.syiyi.reader.util.toJson
import com.syiyi.reader.util.toModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class SourceTest {
    private lateinit var source: Source

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Inject
    lateinit var sourceRepository: SourceRepository

    @ApplicationContext
    @Inject
    lateinit var appContext: Context

    @Before
    fun setup() {
        hiltRule.inject()
        
        source = appContext.assets.open("source.json")
            .bufferedReader()
            .use { it.readText() }
            .toModel<Source>()!!
    }

    @Test
    fun search() = runBlocking {
        val result: List<Book>? = JSEngine.execute(source.script!!, "search", "斗破苍穹")

        println(result.toJson())

        assert(!result.isNullOrEmpty())
    }

    @Test
    fun add() = runBlocking {
        sourceRepository.add(source)

        val list = sourceRepository.list()
        val targetSource = list.find { it.name == source.name }

        assert(targetSource != null)
    }

    @Test
    fun addList() = runBlocking {

        sourceRepository.deleteAll()

        val sourceList = (0 until 60).map {
            source.copy().apply {
                key += "$it"
                name += "$it"
            }
        }
        sourceRepository.add(sourceList)

        val list = sourceRepository.list()
        assert(list.size == 60)
    }
}