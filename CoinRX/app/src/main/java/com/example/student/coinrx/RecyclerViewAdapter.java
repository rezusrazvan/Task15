package com.example.student.coinrx;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.student.coinrx.Date.Datum;
import com.example.student.coinrx.Info.Coin;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<Datum> mData;
    private ItemClickListener mClickListener;
    Context context;
    private Map<String, Coin> cryptoListIcons = new HashMap<>();
    public Map<String, Coin> getCryptoListIcons() {
        return cryptoListIcons;
    }

    RecyclerViewAdapter(List<Datum> data) {
        this.mData = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.crypto_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @SuppressLint({"CheckResult", "SetTextI18n"})
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Datum datum = mData.get(position);
        TextView name = holder.name;
        name.setText(datum.getName() + " (" + datum.getSymbol() + ")");
        TextView price = holder.price;
        price.setText("Price: $" + String.format("%,f", datum.getQuote().getUSD().getPrice()));
        TextView marketCap = holder.marketCap;
        marketCap.setText("Market Cap: $" + String.format("%,d", Math.round(datum.getQuote().getUSD().getMarketCap())));
        TextView volume24h = holder.volume24h;
        volume24h.setText("Volume/24h: $" + String.format("%,d", Math.round(datum.getQuote().getUSD().getVolume24h())));
        TextView textView1h = holder.textView1h;
        textView1h.setText(String.format("1h: %.2f", datum.getQuote().getUSD().getPercentChange1h()) + "%");
        TextView textView24h = holder.textView24h;
        textView24h.setText(String.format("24h: %.2f", datum.getQuote().getUSD().getPercentChange24h()) + "%");
        TextView textView7d = holder.textView7d;
        textView7d.setText(String.format("7d: %.2f", datum.getQuote().getUSD().getPercentChange7d()) + "%");
        ImageView icon = holder.icon;
        String logoURL = cryptoListIcons.get(datum.getSymbol()).getLogo();
        logoURL = logoURL.replace("64x64", "200x200");
        try {
            Picasso.with(context).load(logoURL).into(icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView name;
        TextView price;
        TextView marketCap;
        TextView volume24h;
        TextView textView1h;
        TextView textView24h;
        TextView textView7d;
        ImageView icon;
            ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            marketCap = itemView.findViewById(R.id.marketCap);
            volume24h = itemView.findViewById(R.id.volume24h);
            textView1h = itemView.findViewById(R.id.textView1h);
            textView24h = itemView.findViewById(R.id.textView24h);
            textView7d = itemView.findViewById(R.id.textView7d);
            icon = itemView.findViewById(R.id.icon);
        }
        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
        @Override
        public boolean onLongClick(View view) {
            if (mClickListener != null) mClickListener.onItemLongClick(view, getAdapterPosition());
            return true;
        }
    }
    Datum getItem(int id) {
        return mData.get(id);
    }
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }}
