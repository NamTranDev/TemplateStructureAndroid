/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nam.tran.ui.view.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import android.view.ViewGroup
import java.util.concurrent.Executors

abstract class DataBoundListAdapter<T,V : ViewDataBinding>(
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, DataBoundViewHolder<V>>(
    AsyncDifferConfig.Builder(diffCallback)
        .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
        .build()
) {

    abstract fun onCreateItemHolder(parent: ViewGroup, viewType: Int): V
    abstract fun onBindItemHolder(holder: DataBoundViewHolder<ViewDataBinding>, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<V> {
        return DataBoundViewHolder(onCreateItemHolder(parent, viewType))
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder<V>, position: Int) {
        onBindItemHolder(holder, position)
        holder.binding.executePendingBindings()
    }
}
