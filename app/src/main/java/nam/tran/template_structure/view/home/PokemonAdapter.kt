package nam.tran.template_structure.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import nam.tran.data.model.response.pokemon_list.PokemonInfo
import nam.tran.template_structure.R
import nam.tran.template_structure.databinding.ItemPokemonBinding
import nam.tran.ui.view.adapter.DataBoundPagingAdapter
import nam.tran.ui.view.adapter.DataBoundViewHolder

class PokemonAdapter : DataBoundPagingAdapter<PokemonInfo,ItemPokemonBinding>(diffCallback = object : DiffUtil.ItemCallback<PokemonInfo>(){
    override fun areItemsTheSame(oldItem: PokemonInfo, newItem: PokemonInfo): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: PokemonInfo, newItem: PokemonInfo): Boolean {
        return oldItem.url == newItem.url && oldItem.name == newItem.name
    }
}) {
    override fun getItemType(): Int {
        return R.layout.item_pokemon
    }

    override fun onCreateItemHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): DataBoundViewHolder<ItemPokemonBinding> {
        return DataBoundViewHolder(ItemPokemonBinding.inflate(inflater,parent,false))
    }

    override fun onBindItemHolder(holder: DataBoundViewHolder<ItemPokemonBinding>, position: Int) {
        holder.binding.item = getItem(position)
    }
}