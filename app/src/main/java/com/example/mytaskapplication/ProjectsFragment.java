package com.example.mytaskapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mytaskapplication.databinding.LayoutProjctsFragmentBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectsFragment extends Fragment {

    LayoutProjctsFragmentBinding binding;
    Context context;
    RecyclerView rvProjects;
    String token;
    private RecyclerView.LayoutManager layoutManager;
    ProjectModel projectModel;
    ProjectsAdaptor projectsAdaptor;
    private List<ProjectModel> projectList;
    String name;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=LayoutProjctsFragmentBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        projectList=new ArrayList<>();
        projectsAdaptor=new ProjectsAdaptor(context,projectList);

        getProjects();
    }

    public void getProjects(){
        token="eyJ0eXBlIjoiQlMiLCJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzaXRlaWQiOiJkbFZQV0V0aEwzcG1SMkpFUVc1NFpsYzJaREpSWnowOSIsImRldmljZW5vIjoiWm1OQmVtOTZVVEJNT0RWc1NqSmhSRFl4V0c4dlppc3h" +
                "TSEJNVjI5NE1FZEtjblpzYlZaTGVrNWFPRDA9IiwiY2xpZW50bmFtZSI6ImF6RXZOU3Q0VG1aSk1rTTBSR1V5ZUVKMk1GUkJUakZTWnpsMVNubGhNRUphTlZWdk9HczVlVko2T0QwPSIsInVzZXJpZCI6Il" +
                "JuUnhhbVI0U1hkUVdrdElja1ZMVWt0eVMyRjBRVDA5In0.Fg5JbggGjDSqu4L3xlD7XofiKCnOon_MlCeRzU1UWQI";
        String url= "https://development-mobile.builderstorm.com/mobileapp/getUserAssignedProjects";

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                   //  Toast.makeText(context, ""+response.toString(), Toast.LENGTH_SHORT).show();
                    JSONObject jsonObject=new JSONObject(response);
                    JSONObject jsonObject1=jsonObject.getJSONObject("data");
                    JSONArray jsonArray=jsonObject1.getJSONArray("user_projects");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject2=jsonArray.getJSONObject(i);
                        name=jsonObject2.getString("title");
                        projectModel=new ProjectModel();
                        projectModel.setName(name);
                        projectModel.setDiscription(jsonObject2.getString("type"));
                        projectModel.setAddress(jsonObject2.getString("address"));
                        projectList.add(projectModel);
                    }

                }
                catch (JSONException e){
                    Toast.makeText(context,"c"+ e.toString(),Toast.LENGTH_LONG).show();
                }
                binding.rvProjets.setLayoutManager(layoutManager);
                // recyclerView.setHasFixedSize(true);
                binding.rvProjets.setAdapter(projectsAdaptor);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,""+ error.toString(),Toast.LENGTH_LONG).show();
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("authToken", token);
                return params;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }


}
