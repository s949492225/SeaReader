package com.syiyi.reader

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.syiyi.reader.engine.JSEngine
import com.syiyi.reader.model.Book
import com.syiyi.reader.model.Source
import com.syiyi.reader.repository.LocalSourceRepository
import com.syiyi.reader.util.toJson
import com.syiyi.reader.util.toModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SourceTest {
    private lateinit var appContext: Context
    private lateinit var source: Source

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        appContext = InstrumentationRegistry.getInstrumentation().targetContext
        source = appContext.assets.open("source.json")
            .bufferedReader()
            .use { it.readText() }
            .toModel<Source>()!!
    }

    @Test
    fun search() = runBlocking {
        val result: List<Book>? = JSEngine.execute(source.script, "search", "斗破苍穹")

        println(result.toJson())

        assert(!result.isNullOrEmpty())
    }

    @Test
    fun add() = runBlocking {
        val localSourceRepository = LocalSourceRepository(appContext)
        localSourceRepository.add(source)

        val list = localSourceRepository.list()
        val targetSource = list.find { it.name == source.name }

        assert(targetSource != null)
        if (targetSource != null) {
            assert(targetSource.script.isNotEmpty())
        }
    }
}