package com.devianggraini.utsapilkasicatering.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.devianggraini.utsapilkasicatering.R;
import com.devianggraini.utsapilkasicatering.model.ModelMakanan;
import com.devianggraini.utsapilkasicatering.server.BaseURL;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterMenu extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelMakanan> item;

    public AdapterMenu(Activity activity, List<ModelMakanan> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.content_makanan, null);


        TextView namaMenu = (TextView) convertView.findViewById(R.id.txtnamauser);
        TextView idMenu     = (TextView) convertView.findViewById(R.id.txtidMenu);
        TextView harga         = (TextView) convertView.findViewById(R.id.txtHargamenu);
        TextView rating         = (TextView) convertView.findViewById(R.id.txtrating);
        TextView txtkategori    = (TextView) convertView.findViewById(R.id.txtkategori);
        ImageView gambarMenu        = (ImageView) convertView.findViewById(R.id.gambarmenu);

        namaMenu.setText(item.get(position).getNamaMenu());
        idMenu.setText(item.get(position).getIdMenu());
        harga.setText("Rp." + item.get(position).getHargaMenu());
        rating.setText(item.get(position).getRatingMenu());
        txtkategori.setText(item.get(position).getKategori());
        Picasso.get().load(BaseURL.baseUrl + "gambar/" + item.get(position).getGambar())
                .resize(120, 100)
                .centerCrop()
                .into(gambarMenu);
        return convertView;
    }
}
