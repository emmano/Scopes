package me.emmano.scopes.app.services;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by emmanuelortiguela on 12/26/14.
 */
public interface GithubService {

    @GET("/BlurStickyHeaderListView/stargazers")
    public void starGazers(Callback<List<Repo>> repos);

}
