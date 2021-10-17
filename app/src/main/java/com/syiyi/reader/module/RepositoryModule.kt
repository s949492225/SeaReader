package com.syiyi.reader.module

import android.content.Context
import com.syiyi.reader.repository.SourceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SourceCacheDir

@Module
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {

    @SourceCacheDir
    @Provides
    fun provideSourceCacheDir(@ApplicationContext appContext: Context): File =
        File(appContext.cacheDir, "source").apply {
            if (!exists() || isFile) {
                mkdirs()
            }
        }

    @Singleton
    @Provides
    fun provideSourceRepository(@SourceCacheDir cacheDir: File): SourceRepository =
        SourceRepository(cacheDir)

}