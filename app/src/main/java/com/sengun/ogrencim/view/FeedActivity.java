package com.sengun.ogrencim.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.sengun.ogrencim.R;
import com.sengun.ogrencim.adapter.Postadapter;
import com.sengun.ogrencim.databinding.ActivityFeedBinding;
import com.sengun.ogrencim.model.Post;

import java.util.ArrayList;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    ArrayList <Post> postArrayList;
    private ActivityFeedBinding binding;
    Postadapter postadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        binding = ActivityFeedBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        postArrayList = new ArrayList<>();


        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        getData();

        binding.RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        postadapter =new Postadapter(postArrayList);
        binding.RecyclerView.setAdapter(postadapter);


    }

    private void getData() {

        firebaseFirestore.collection("Posts").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    Toast.makeText(FeedActivity.this,error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }

                if (value != null){

                    for (DocumentSnapshot snapshot : value.getDocuments()){

                        Map<String, Object> data = snapshot .getData();

                        String userEmail = (String) data.get("useremail");
                        String comment = (String) data.get("comment");
                        String downloadUrl = (String) data.get("downloadurl");

                        Post post = new Post(userEmail,comment,downloadUrl);
                        postArrayList.add(post);




                    }
                    postadapter.notifyDataSetChanged();
                }

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu,menu);

        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== R.id.add_post){
            //upload
            Intent intentATSupload = new Intent(FeedActivity.this,Upload.class);
            startActivity(intentATSupload);

        }else if(item.getItemId()==R.id.signout){
            //signout



            auth.signOut();
        Intent intentATSmain = new Intent(FeedActivity.this,MainActivity.class);
        startActivity(intentATSmain);
        finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
