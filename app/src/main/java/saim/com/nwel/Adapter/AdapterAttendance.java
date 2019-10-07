package saim.com.nwel.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import saim.com.afcdemoapps.Model.ModelImage;
import saim.com.afcdemoapps.R;
import saim.com.nwel.Model.ModelAttendance;


public class AdapterAttendance extends RecyclerView.Adapter<AdapterAttendance.PostViewHolder>{

    ArrayList<ModelAttendance> adapterList = new ArrayList<>();
    Context mContext;

    public static String post_id = "";

    public AdapterAttendance(ArrayList<ModelAttendance> adapterList) {
        this.adapterList = adapterList;
    }

    public AdapterAttendance(ArrayList<ModelAttendance> adapterList, Context mContext) {
        this.adapterList = adapterList;
        this.mContext = mContext;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_all_post, parent, false);
        PostViewHolder postViewHolder = new PostViewHolder(view);
        return postViewHolder;
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        holder.txtListTitle.setText(adapterList.get(position).title);
        holder.txtListDimension.setText(adapterList.get(position).width + "x" + adapterList.get(position).height);
        holder.txtListDate.setText(adapterList.get(position).upload_at);
    }

    @Override
    public int getItemCount() {
        return adapterList.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgList;
        TextView txtListTitle, txtListDimension, txtListDate;

        public PostViewHolder(View itemView) {
            super(itemView);

            imgList = (ImageView) itemView.findViewById(R.id.imgList);

            txtListTitle = (TextView) itemView.findViewById(R.id.txtListTitle);
            txtListDimension = (TextView) itemView.findViewById(R.id.txtListDimension);
            txtListDate = (TextView) itemView.findViewById(R.id.txtListDate);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), adapterList.get(getAdapterPosition()).getINSERT_TIME(), Toast.LENGTH_SHORT).show();
        }
    }
}
