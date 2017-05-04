package com.example.sreer.geekspad.ui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.sreer.geekspad.R;
import com.example.sreer.geekspad.db.FireBaseHelper;
import com.example.sreer.geekspad.model.User;
import com.example.sreer.geekspad.ui.adapter.UsersAdapter;
import com.example.sreer.geekspad.utils.ItemClickSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kalirajkalimuthu on 4/6/17.
 */

public class UsersListViewFragment extends Fragment implements ItemClickSupport.OnItemClickListener, FireBaseHelper.GetAllUsersInterface {

    private List<User> usersList = new ArrayList<User>();
    private LinearLayoutManager mLayoutManager;
    private RecyclerView recyclerView;
    private UsersAdapter mAdapter;
    private FireBaseHelper fireBaseHelper;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       // displayUsers(null, false);
        getActivity().setTitle("Users List View");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_users_list_view, container, false);
        fireBaseHelper = new FireBaseHelper(UsersListViewFragment.this);
        initRecycleView(view);
        initUsersList();
        return view;
    }

    public void initUsersList(){
        fireBaseHelper.getAllUsers();
    }


    public void initRecycleView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        // mLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new UsersAdapter(usersList, recyclerView);
        recyclerView.setAdapter(mAdapter);
        ItemClickSupport.addTo(recyclerView)
                .setOnItemClickListener(this);

    }
    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

    }

    public UsersListViewFragment() {
        super();
    }

    @Override
    public void onSuccessGetAllUsers(List<User> users) {
        mAdapter.clear();
        for(User user:users){
            mAdapter.add(user);
        }
        recyclerView.smoothScrollToPosition(mAdapter.getItemCount()-1);
    }

    @Override
    public void onFailureGetAllUsers() {

    }
}
