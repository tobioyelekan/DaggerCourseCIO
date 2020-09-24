package com.example.daggercourse.poweradapter

import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
@Retention(AnnotationRetention.RUNTIME)
annotation class RendererKey(val value: KClass<out RecyclerItem>)