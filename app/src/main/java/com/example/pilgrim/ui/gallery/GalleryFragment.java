package com.example.pilgrim.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pilgrim.ImageDTO;
import com.example.pilgrim.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private List<ImageDTO> galleryListUri=new ArrayList<>();
    private FirebaseDatabase database;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_gallery3, container, false);
        database=FirebaseDatabase.getInstance();
        RecyclerView recyclerView=root.findViewById(R.id.recycierview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        final GalleryRecyclerViewAdapter galleryRecyclerViewAdapter=new GalleryRecyclerViewAdapter();
        recyclerView.setAdapter(galleryRecyclerViewAdapter);

        database.getReference("GalleryUris").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                galleryListUri.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ImageDTO imageDTO=snapshot.getValue(ImageDTO.class);
                     galleryListUri.add(imageDTO);
                }
                galleryRecyclerViewAdapter.notifyDataSetChanged();//새로고침
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
            return root;
    }
    class GalleryRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery,parent,false);
            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Glide.with(holder.itemView.getContext()).load(galleryListUri.get(position).imageUrI).into(((CustomViewHolder)holder).imageView);
        }

        @Override
        public int getItemCount() {
            return galleryListUri.size();
        }
        private class CustomViewHolder extends RecyclerView.ViewHolder{
            private ImageView imageView;
            public CustomViewHolder(View view){
                super(view);
                imageView=view.findViewById(R.id.item_ImageView);

            }
        }
    }
    /*public class GalleryAdapter extends BaseAdapter {
        private Context mContext;

        public GalleryAdapter(Context c){
            mContext=c;
        }
        public int getCount(){
            return mThumbIds.length;
        }
        public Object getItem(int position){
            return  null;
        }

        public long getItemId(int position){
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if(convertView==null){
                imageView=new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(300,300));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8,8,8,8);
            }else{
                imageView=(ImageView)convertView;
            }
            imageView.setImageResource(mThumbIds[position]);
            return imageView;
        }


    }*/

    /*  Bundle extra=getArguments();
        if(extra!=null){

           galleryUri=extra.getString("uri");
        }
        test.setText(galleryUri);*/



       /* GridView gridView=root.findViewById(R.id.gridview);
        gridView.setAdapter(new GalleryAdapter(getContext()));

        galleryList.add(galleryUri);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View Bigview = View.inflate(getActivity(), R.layout.fragment_gallery_big_size, null);
                ImageView bigPitcure = Bigview.findViewById(R.id.imageView4);
                bigPitcure.setImageResource(mThumbIds[position]);
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                dialog.setTitle("사진");
                dialog.setIcon(R.drawable.gallery);
                dialog.setView(Bigview);
                dialog.setNegativeButton("닫기", null);
                dialog.show();

            }
        });*/
}
