package com.thegorgeouscows.team.finalrev;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment {
    private RecyclerView post_list_view;
    List<Posts>posts_list;
    private FirebaseFirestore firebaseFirestore,db;
    private PostRecyclerAdapter postRecyclerAdapter;


    public FeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_feed,container,false);

        post_list_view = (RecyclerView) view.findViewById(R.id.blog_list_view);
        posts_list = new ArrayList<>();
        postRecyclerAdapter = new PostRecyclerAdapter(posts_list);

        post_list_view.setLayoutManager(new LinearLayoutManager(container.getContext()));
        post_list_view.setAdapter(postRecyclerAdapter);
        post_list_view.setHasFixedSize(true);


        db = FirebaseFirestore.getInstance();
        db.collection("POSTS").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (final QueryDocumentSnapshot document: task.getResult()){
                        //Log.i("my",document.getId()+ "=>"+document.getData());
                        DocumentReference docRef = db.collection("POSTS").document(document.getId());
                        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                Posts post = documentSnapshot.toObject(Posts.class);
                                posts_list.add(post);
                                //System.out.println(Arrays.toString(posts_list.toArray()));
                                postRecyclerAdapter.notifyDataSetChanged();
                            }
                        });

                    }
                }
                else {
                    Log.d("my", "Error getting documents: ", task.getException());
                }

            }
        });


        return view;
    }

}
