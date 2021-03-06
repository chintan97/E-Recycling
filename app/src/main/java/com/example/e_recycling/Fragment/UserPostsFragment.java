// A fragment to show user posts
package com.example.e_recycling.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_recycling.R;
import com.example.e_recycling.SlideMenuActivity;
import com.example.e_recycling.UserPostDetails;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class UserPostsFragment extends FragmentMaster {

    View view;
    int images[] = {R.drawable.moto, R.drawable.iphone, R.drawable.samsung};

    // String arrays for sample renders
    String descriptions[] = {"Moto G7", "Iphone", "Samsung"};
    String quantity[] = {"2", "1", "5"};
    String location[] = {"Halifax", "Halifax", "Halifax"};
    FloatingActionButton redirect_new_post;
    String userEmail, userMode;
    SharedPreferences sharedPreferences;

    RecyclerView recyclerView;

    public UserPostsFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_user_posts, container, false);

        prepareViews(view);
        initializeViews();

        return view;
    }

    @Override
    public void prepareViews(View view) {
        recyclerView = view.findViewById(R.id.recycler_user_post);
        LinearLayoutManager gr = new LinearLayoutManager(getActivity());
        gr.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(gr);

        redirect_new_post = view.findViewById(R.id.button_new_order);

        redirect_new_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SlideMenuActivity.class);
                intent.putExtra("email",userEmail);
                intent.putExtra("userType",userMode);
                intent.putExtra("redirectedFrom", "UserPostsFragment");
                startActivity(intent);
            }
        });
    }

    @Override
    public void initializeViews() {
        recyclerView.setAdapter(new PostsAdapter(getContext()));
    }

    private class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.MainCategoryViewHolder> {
        Context context;

        PostsAdapter(Context act) {
            this.context = act;
        }

        public class MainCategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            TextView text_row_description, text_row_quantity, text_row_location;
            ImageView image_row_prod_pic;
            LinearLayout layout;

            MainCategoryViewHolder(View itemView) {
                super(itemView);

                // Initialize views
                text_row_description = itemView.findViewById(R.id.text_row_user_post_description);
                text_row_quantity = itemView.findViewById(R.id.text_row_user_post_quantity);
                text_row_location = itemView.findViewById(R.id.text_row_user_post_location);
                image_row_prod_pic = itemView.findViewById(R.id.image_row_user_post_product_image);
                layout = itemView.findViewById(R.id.user_posts);
                layout.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (v.getId() == layout.getId()) {
                    startActivity(new Intent(getActivity(), UserPostDetails.class));
                }
            }
        }

        @Override
        public MainCategoryViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_users_posts, parent, false);
            MainCategoryViewHolder pvh = new MainCategoryViewHolder(view);
            return pvh;
        }

        public void onBindViewHolder(MainCategoryViewHolder holder, int i) {

            // Render data dynamically
            holder.image_row_prod_pic.setImageResource(images[i]);
            holder.text_row_description.setText("Desc : "+descriptions[i]);
            holder.text_row_quantity.setText("Quantity : "+quantity[i]);
            holder.text_row_location.setText("Location : "+location[i]);
        }

        @Override
        public int getItemCount() {
            return descriptions.length;
        }
    }
}
