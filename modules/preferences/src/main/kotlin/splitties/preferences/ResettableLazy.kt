/*
 * Copyright (c) 2017. Louis Cognault Ayeva Derman
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

package splitties.preferences

import kotlin.reflect.KProperty

/**
 * A Lazy delegate that keeps a reference to it's initializer and resets it's value if you set it's
 * value with it's current value.
 *
 * Note that this doesn't support nullable values.
 *
 * Example:
 * ```kotlin
 * var myMember by ResettableLazy { Editor(preferences) }
 * ...
 * myMember = myMember // This line resets myMember. Next access will call the initializer again.
 * ```
 */
internal class ResettableLazy<T : Any>(private val initializer: () -> T) {

    private var value: T? = null

    operator fun getValue(thisRef: Any?, prop: KProperty<*>): T {
        return value ?: initializer().apply { value = this }
    }

    operator fun setValue(thisRef: Any?, prop: KProperty<*>, value: T) {
        check(this.value == value) { "New values aren't accepted to reset this delegated property" }
        invalidate()
    }

    fun invalidate() {
        value = null
    }
}