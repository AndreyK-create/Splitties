/*
 * Copyright (c) 2018. Louis Cognault Ayeva Derman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package splitties.arch.lifecycle

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations

@ObsoleteSplittiesLifecycleApi
inline fun <T> LifecycleOwner.observe(
    liveData: LiveData<T>,
    crossinline observer: (t: T?) -> Unit
): Observer<T> = Observer<T> {
    observer(it)
}.also { liveData.observe(this, it) }

@ObsoleteSplittiesLifecycleApi
inline fun <T : Any> LifecycleOwner.observeNotNull(
    liveData: LiveData<T>,
    crossinline observer: (t: T) -> Unit
): Observer<T> = Observer<T> {
    if (it != null) observer(it)
}.also { liveData.observe(this, it) }

@ObsoleteSplittiesLifecycleApi
@JvmName("observeWithLiveDataOfNullable")
inline fun <T : Any> LifecycleOwner.observeNotNull(
    liveData: LiveData<T?>,
    crossinline observer: (t: T) -> Unit
): Observer<T?> = Observer<T?> {
    if (it != null) observer(it)
}.also { liveData.observe(this, it) }

/**
 * Applies the given function on the main thread to each value emitted by source
 * LiveData and returns LiveData, which emits resulting values.
 *
 * The given function [transform] will be **executed on the main thread**.
 *
 * @param transform   a function to apply
 * @param X           a type of source LiveData
 * @param Y           a type of resulting LiveData.
 * @return            a LiveData which emits resulting values
 */
@ObsoleteSplittiesLifecycleApi
inline fun <X, Y> LiveData<X>.map(
    crossinline transform: (X?) -> Y
): LiveData<Y> = Transformations.map(this) { input -> transform(input) }

@ObsoleteSplittiesLifecycleApi
inline fun <X, Y> LiveData<X>.mapNotNull(
    crossinline transform: (X) -> Y
): LiveData<Y> = Transformations.map(this) { input: X? ->
    input?.let { transform(it) }
}

@ObsoleteSplittiesLifecycleApi
inline fun <X, Y> LiveData<X>.switchMap(
    crossinline transform: (X?) -> LiveData<Y>?
): LiveData<Y> = Transformations.switchMap(this) { input -> transform(input) }

@ObsoleteSplittiesLifecycleApi
inline fun <X, Y> LiveData<X>.switchMapNotNull(
    crossinline transform: (X) -> LiveData<Y>?
): LiveData<Y> = Transformations.switchMap(this) { input: X? ->
    input?.let { transform(it) }
}
