package com.example.tom.itistracker.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.tom.itistracker.models.network.AttachStoryToSprintObject;
import com.example.tom.itistracker.models.network.Credentials;
import com.example.tom.itistracker.models.network.Sprint;
import com.example.tom.itistracker.models.network.Task;
import com.example.tom.itistracker.models.network.TaskStatusChangeObject;
import com.example.tom.itistracker.models.network.User;
import com.example.tom.itistracker.models.network.UserStory;
import com.example.tom.itistracker.models.network.project.Project;
import com.example.tom.itistracker.models.network.project.ProjectDetails;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceApi {

    @POST("auth")
    Single<User> login(@Body @NonNull final Credentials credentials);

    @GET("projects")
    Single<List<Project>> getUserProjects(@Query("member") final long userId);

    @GET("projects/{projectId}")
    Single<ProjectDetails> getProjectDetails(@Path("projectId") final long projectId);

    @GET("milestones")
    Single<List<Sprint>> getSprints(@Query("project") final long projectId);

    @DELETE("milestones/{sprintId}")
    Single<Void> deleteSprint(@Path("sprintId") final long sprintId);

    @GET("userstories?milestone=null")
    Single<List<UserStory>> getUnassignedUserStories(@Query("project") final long projectId);

    /**
     * - send correct sprintId in AttachStoryToSprintObject in Body to attach story to sprint;
     * - send null as sprintId - to detach;
     */
    @PATCH("userstories/{storyId}")
    Single<UserStory> attachUserStoryToSprint(@Path("storyId") final long userStoryId,
                                              @Body @NonNull final AttachStoryToSprintObject attachObject);

    @DELETE("userstories/{storyId}")
    Single<Void> deleteUserStory(@Path("storyId") final long userStoryId);

    @GET("tasks")
    Single<List<Task>> getTasks(@Query("project") final long projectId,
                                @Query("milestone") @Nullable final Long sprintId);

    @PATCH("tasks/{taskStatusId}")
    Single<Task> changeTaskStatus(@Path("taskStatusId") final long taskStatusId,
                                  @Body @NonNull final TaskStatusChangeObject taskStatusChangeObject);

}
