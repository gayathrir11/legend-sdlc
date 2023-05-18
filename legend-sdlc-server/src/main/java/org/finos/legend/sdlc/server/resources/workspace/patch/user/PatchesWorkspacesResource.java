// Copyright 2023 Goldman Sachs
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.finos.legend.sdlc.server.resources.workspace.patch.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.finos.legend.sdlc.domain.model.project.workspace.Workspace;
import org.finos.legend.sdlc.domain.model.project.workspace.WorkspaceType;
import org.finos.legend.sdlc.server.domain.api.workspace.WorkspaceApi;
import org.finos.legend.sdlc.server.error.LegendSDLCServerException;
import org.finos.legend.sdlc.server.resources.BaseResource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

@Path("/projects/{projectId}/patches/{patchReleaseVersion}/workspaces")
@Api("Workspaces")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PatchesWorkspacesResource extends BaseResource
{
    private final WorkspaceApi workspaceApi;

    @Inject
    public PatchesWorkspacesResource(WorkspaceApi workspaceApi)
    {
        this.workspaceApi = workspaceApi;
    }

    @GET
    @ApiOperation("Get all user workspaces for a project for patch release version")
    public List<Workspace> getUserWorkspaces(@PathParam("projectId") String projectId,
                                             @PathParam("patchReleaseVersion") String patchReleaseVersion,
                                             @QueryParam("owned")
                                             @DefaultValue("true")
                                             @ApiParam("Only include workspaces owned by current user") boolean ownedOnly)
    {
        LegendSDLCServerException.validateNonNull(patchReleaseVersion, "patchReleaseVersion may not be null");
        return executeWithLogging(
                "getting " + (ownedOnly ? "user" : "all") + " workspaces for patch release version " + patchReleaseVersion + " for project " + projectId,
                () -> ownedOnly ? this.workspaceApi.getWorkspaces(projectId, patchReleaseVersion, Collections.singleton(WorkspaceType.USER)) : this.workspaceApi.getAllWorkspaces(projectId, patchReleaseVersion, EnumSet.allOf(WorkspaceType.class))
        );
    }

    @GET
    @Path("{workspaceId}")
    @ApiOperation("Get a user workspace for a project by id for patch release version")
    public Workspace getUserWorkspace(@PathParam("projectId") String projectId, @PathParam("patchReleaseVersion") String patchReleaseVersion, @PathParam("workspaceId") String workspaceId)
    {
        LegendSDLCServerException.validateNonNull(patchReleaseVersion, "patchReleaseVersion may not be null");
        return executeWithLogging(
                "getting user workspace " + workspaceId + " for patch release " + patchReleaseVersion +  " for project " + projectId,
                () -> this.workspaceApi.getWorkspace(projectId, patchReleaseVersion, workspaceId, WorkspaceType.USER)
        );
    }

    @GET
    @Path("{workspaceId}/outdated")
    @ApiOperation("Check if a user workspace is outdated for patch release version")
    public boolean isUserWorkspaceOutdated(@PathParam("projectId") String projectId, @PathParam("patchReleaseVersion") String patchReleaseVersion, @PathParam("workspaceId") String workspaceId)
    {
        LegendSDLCServerException.validateNonNull(patchReleaseVersion, "patchReleaseVersion may not be null");
        return executeWithLogging(
                "checking if user workspace " + workspaceId + " for patch release " + patchReleaseVersion + " of project " + projectId + " is outdated",
                () -> this.workspaceApi.isWorkspaceOutdated(projectId, patchReleaseVersion, workspaceId, WorkspaceType.USER)
        );
    }

    @GET
    @Path("{workspaceId}/inConflictResolutionMode")
    @ApiOperation("Check if a user workspace is in conflict resolution mode for patch release version")
    public boolean isUserWorkspaceInConflictResolutionMode(@PathParam("projectId") String projectId, @PathParam("patchReleaseVersion") String patchReleaseVersion, @PathParam("workspaceId") String workspaceId)
    {
        LegendSDLCServerException.validateNonNull(patchReleaseVersion, "patchReleaseVersion may not be null");
        return executeWithLogging(
                "checking if user workspace " + workspaceId + " for patch release " + patchReleaseVersion + " of project " + projectId + " is in conflict resolution mode",
                () -> this.workspaceApi.isWorkspaceInConflictResolutionMode(projectId, patchReleaseVersion, workspaceId, WorkspaceType.USER)
        );
    }

    @POST
    @Path("{workspaceId}")
    @ApiOperation("Create a new user workspace for patch release version")
    public Workspace createUserWorkspace(@PathParam("projectId") String projectId, @PathParam("patchReleaseVersion") String patchReleaseVersion, @PathParam("workspaceId") String workspaceId)
    {
        LegendSDLCServerException.validateNonNull(patchReleaseVersion, "patchReleaseVersion may not be null");
        return executeWithLogging(
                "creating new user workspace " + workspaceId + " for patch release " + patchReleaseVersion + " for project " + projectId,
                () -> this.workspaceApi.newWorkspace(projectId,
                        patchReleaseVersion,
                        workspaceId,
                        WorkspaceType.USER)

        );
    }

    @DELETE
    @Path("{workspaceId}")
    @ApiOperation("Delete a user workspace for patch release version")
    public void deleteUserWorkspace(@PathParam("projectId") String projectId, @PathParam("patchReleaseVersion") String patchReleaseVersion, @PathParam("workspaceId") String workspaceId)
    {
        LegendSDLCServerException.validateNonNull(patchReleaseVersion, "patchReleaseVersion may not be null");
        executeWithLogging(
                "deleting user workspace " + workspaceId + " for patch release " + patchReleaseVersion + " for project " + projectId,
                () -> this.workspaceApi.deleteWorkspace(projectId, patchReleaseVersion, workspaceId, WorkspaceType.USER)
        );
    }

    @POST
    @Path("{workspaceId}/update")
    @ApiOperation("Update a user workspace for patch release version")
    public WorkspaceApi.WorkspaceUpdateReport updateUserWorkspace(@PathParam("projectId") String projectId, @PathParam("patchReleaseVersion") String patchReleaseVersion, @PathParam("workspaceId") String workspaceId)
    {
        LegendSDLCServerException.validateNonNull(patchReleaseVersion, "patchReleaseVersion may not be null");
        return executeWithLogging(
                "updating user workspace " + workspaceId + " in project " + projectId + " to latest revision",
                () -> this.workspaceApi.updateWorkspace(projectId, patchReleaseVersion, workspaceId, WorkspaceType.USER)
        );
    }
}