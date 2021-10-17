package com.syiyi.reader.module

import android.content.Context
import com.syiyi.reader.repository.SourceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import java.io.File

@Module
@InstallIn(ViewModelComponent::class)
internal object ViewModelSourceModule {

    @ViewModelScoped
    @Provides
    fun provideSourceRepository(@ApplicationContext appContext: Context): SourceRepository {
        val cacheRoot = File(appContext.cacheDir, "source").apply {
            if (!exists() || isFile) {
                mkdirs()
            }
        }
        return SourceRepository(cacheRoot)
    }
}