package com.example.e_recycling;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAcceptedListFragment extends FragmentMaster {

    View view;
    int images[] = {R.drawable.user};
    String name[] = {"Shrey Amin"};
    String location[] = {"Halifax"};
    String status[] = {"Further Notification sent."};

    RecyclerView recyclerView;

    public RecyclerAcceptedListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void prepareViews(View view) {
        recyclerView = view.findViewById(R.id.recycler_bid_items);
        LinearLayoutManager gr = new LinearLayoutManager(getActivity());
        gr.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gr);
    }

    @Override
    public void initializeViews() {
        recyclerView.setAdapter(new PostsAdapter(getContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_recycler_accepted, container, false);

        prepareViews(view);
        initializeViews();

        return view;
    }

    private class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.MainCategoryViewHolder> {
        Context context;

        PostsAdapter(Context act) {
            this.context = act;
        }

        public class MainCategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView text_row_description, text_row_location,text_row_recycler_status;
            ImageView image_row_prod_pic;
            Button button_accept,button_reject;

            MainCategoryViewHolder(View itemView) {
                super(itemView);
                text_row_description = itemView.findViewById(R.id.text_row_recycler_bid_description);
                text_row_location = itemView.findViewById(R.id.text_row_recycler_bid_location);
                image_row_prod_pic = itemView.findViewById(R.id.image_row_recycler_bid_image);
                text_row_recycler_status = itemView.findViewById(R.id.text_row_recycler_status);
            }

            @Override
            public void onClick(View v) {

            }
        }

        @Override
        public PostsAdapter.MainCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_accepted_requests, parent, false);
            PostsAdapter.MainCategoryViewHolder pvh = new PostsAdapter.MainCategoryViewHolder(view);
            return pvh;
        }

        public void onBindViewHolder(PostsAdapter.MainCategoryViewHolder holder, int i) {
            holder.image_row_prod_pic.setImageResource(images[i]);
            holder.text_row_description.setText("Name : " + name[i]);
            holder.text_row_location.setText("Location : " + location[i]);
            holder.text_row_recycler_status.setText("Status : " + status[i]);
        }

        @Override
        public int getItemCount() {
            return name.length;
        }
    }
}
