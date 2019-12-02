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

public class RecyclerListFragment extends FragmentMaster {

    View view;
    int images[] = {R.drawable.user,R.drawable.user,R.drawable.user,R.drawable.user};
    String name[] = {"Nihir Shah", "Chintan Patel","Zeel Patel","Sneha Sagar"};
    String location[] = {"Halifax", "Dartmouth","Sackville","Halifax"};

    RecyclerView recyclerView;

    public RecyclerListFragment() {
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
        view = inflater.inflate(R.layout.fragment_recycler_bid, container, false);

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
            TextView text_row_description, text_row_location;
            ImageView image_row_prod_pic;
            Button button_accept,button_reject;

            MainCategoryViewHolder(View itemView) {
                super(itemView);
                text_row_description = itemView.findViewById(R.id.text_row_recycler_bid_description);
                text_row_location = itemView.findViewById(R.id.text_row_recycler_bid_location);
                image_row_prod_pic = itemView.findViewById(R.id.image_row_recycler_bid_image);
                button_accept = itemView.findViewById(R.id.button_row_bid_accept);
                button_reject = itemView.findViewById(R.id.button_row_bid_reject);
                button_accept.setOnClickListener(this);
                button_reject.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (v.getId() == button_accept.getId()) {
                    if (button_accept.getText().toString().equals("Accept")) {
                        button_accept.setText("Accept");
                    } else if (button_reject.getText().toString().equals("Reject")) {
                        button_reject.setText("Reject");
                    }
                }
            }
        }

        @Override
        public PostsAdapter.MainCategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_bid_items, parent, false);
            PostsAdapter.MainCategoryViewHolder pvh = new PostsAdapter.MainCategoryViewHolder(view);
            return pvh;
        }

        public void onBindViewHolder(PostsAdapter.MainCategoryViewHolder holder, int i) {
            holder.image_row_prod_pic.setImageResource(images[i]);
            holder.text_row_description.setText("Name : " + name[i]);
            holder.text_row_location.setText("Location : " + location[i]);
        }

        @Override
        public int getItemCount() {
            return name.length;
        }
    }
}
