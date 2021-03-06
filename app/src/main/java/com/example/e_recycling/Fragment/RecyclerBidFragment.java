// A fragment for recycler bid
package com.example.e_recycling.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.e_recycling.R;
import com.example.e_recycling.SlideMenuActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerBidFragment extends FragmentMaster {

    View view;
    int images[] = {R.drawable.moto, R.drawable.iphone, R.drawable.samsung};

    // String arrays for sample renders
    String descriptions[] = {"Moto G7", "Iphone"};
    String bids[] = {"$10", "$30"};
    String location[] = {"Halifax", "Halifax"};
    SharedPreferences sharedPreferences;
    String userEmail, userMode;

    RecyclerView recyclerView;

    public RecyclerBidFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Fetch data from SharedPreferences
        sharedPreferences = getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userEmail = sharedPreferences.getString("email", "NOT_FOUND");
        userMode = sharedPreferences.getString("userType", "NOT_FOUND");
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
            EditText edit_bid_amount;
            ImageView image_row_prod_pic;
            Button button_edit;

            MainCategoryViewHolder(View itemView) {
                super(itemView);

                // Initialize views
                text_row_description = itemView.findViewById(R.id.text_row_recycler_bid_description);
                text_row_location = itemView.findViewById(R.id.text_row_recycler_bid_location);
                edit_bid_amount = itemView.findViewById(R.id.edit_row_recycler_bid_amount);
                image_row_prod_pic = itemView.findViewById(R.id.image_row_recycler_bid_image);
                button_edit = itemView.findViewById(R.id.button_row_bid_edit);
                button_edit.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (v.getId() == button_edit.getId()) {
                    if (button_edit.getText().toString().equals("Edit")) {
                        edit_bid_amount.setEnabled(true);
                        button_edit.setText("Done");
                    } else if (button_edit.getText().toString().equals("Done")) {
                        edit_bid_amount.setEnabled(false);
                        button_edit.setText("Edit");
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
            holder.text_row_description.setText("Desc : " + descriptions[i]);
            holder.text_row_location.setText("Location : " + location[i]);
            holder.edit_bid_amount.setText(bids[i]);
        }

        @Override
        public int getItemCount() {
            return descriptions.length;
        }
    }
}
