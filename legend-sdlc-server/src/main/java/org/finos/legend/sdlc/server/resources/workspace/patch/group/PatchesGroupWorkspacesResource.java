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

package org.finos.legend.sdlc.server.resources.workspace.patch.group;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.finos.legend.sdlc.domain.model.project.workspace.Workspace;
import org.finos.legend.sdlc.domain.model.project.workspace.WorkspaceType;
import org.finos.legend.sdlc.server.domain.api.workspace.WorkspaceApi;
import org.finos.legend.sdlc.server.error.LegendSDLCServerException;
import org.finos.legend.sdlc.server.resources.BaseResource;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
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

@Path("/projects/{projectId}/patches/{patchReleaseVersion}/groupWorkspaces")
@Api("Workspaces")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PatchesGroupWorkspacesResource extends BaseResource
{
    private final WorkspaceApi workspaceApi;

    @Inject
    public PatchesGroupWorkspacesResource(WorkspaceApi workspaceApi)
    {
        this.workspaceApi = workspaceApi;
    }

    @GET
    @ApiOperation("Get all group workspaces for a project for patch release version")
    public List<Workspace> getGroupWorkspaces(@PathParam("projectId") String projectId,
                                              @PathParam("patchReleaseVersion") String patchReleaseVersion,
                                              @QueryParam("includeUserWorkspaces")
                                              @DefaultValue("false")
                                              @ApiParam("include user workspaces owned by current user") boolean includeUserWorkspaces)
    {
        return executeWithLogging(
                "getting all group" + (includeUserWorkspaces ? " and user" : "") + " workspaces for project " + projectId + " for patch release version " + patchReleaseVersion,
                () -> includeUserWorkspaces ? this.workspaceApi.getAllWorkspaces(projectId, patchReleaseVersion, EnumSet.allOf(WorkspaceType.class)) : this.workspaceApi.getWorkspaces(projectId, patchReleaseVersion, Collections.singleton(WorkspaceType.GROUP))
        );
    }

    @GET
    @Path("{workspaceId}")
    @ApiOperation("Get a group workspace for a project by id for patch release version")
    public Workspace getGroupWorkspace(@PathParam("projectId") String projectId, @PathParam("patchReleaseVersion") String patchReleaseVersion, @PathParam("workspaceId") String workspaceId)
    {
        LegendSDLCServerException.validateNonNull(patchReleaseVersion, "patchReleaseVersion may not be null");
        return executeWithLogging(
                "getting group workspace " + workspaceId + " for patch release " + patchReleaseVersion +  " for project " + projectId + " for patch release version " + patchReleaseVersion,
                () -> this.workspaceApi.getWorkspace(projectId, patchReleaseVersion, workspaceId, WorkspaceType.GROUP)
        );
    }

    @GET
    @Path("{workspaceId}/outdated")
    @ApiOperation("Check if a group workspace is outdated for patch release version")
    public boolean isGroupWorkspaceOutdated(@PathParam("projectId") String projectId, @PathParam("patchReleaseVersion") String patchReleaseVersion, @PathParam("workspaceId") String workspaceId)
    {
        LegendSDLCServerException.validateNonNull(patchReleaseVersion, "patchReleaseVersion may not be null");
        return executeWithLogging(
                "checking if group workspace " + workspaceId + " for patch release " + patchReleaseVersion + " of project " + projectId + " for patch release version " + patchReleaseVersion + " is outdated",
                () -> this.workspaceApi.isWorkspaceOutdated(projectId, patchReleaseVersion, workspaceId, WorkspaceType.GROUP)
        );
    }

    @GET
    @Path("{workspaceId}/inConflictResolutionMode")
    @ApiOperation("Check if a group workspace is in conflict resolution mode for patch release version")
    public boolean isGroupWorkspaceInConflictResolutionMode(@PathParam("projectId") String projectId, @PathParam("patchReleaseVersion") String patchReleaseVersion, @PathParam("workspaceId") String workspaceId)
    {
        LegendSDLCServerException.validateNonNull(patchReleaseVersion, "patchReleaseVersion may not be null");
        return executeWithLogging(
                "checking if group workspace " + workspaceId + " for patch release " + patchReleaseVersion + " of project " + projectId + " for patch release version " + patchReleaseVersion + " is in conflict resolution mode",
                () -> this.workspaceApi.isWorkspaceInConflictResolutionMode(projectId, patchReleaseVersion, workspaceId, WorkspaceType.GROUP)
        );
    }

    @POST
    @Path("{workspaceId}")
    @ApiOperation("Create a new group workspace for patch release version")
    public Workspace createGroupWorkspace(@PathParam("projectId") String projectId, @PathParam("patchReleaseVersion") String patchReleaseVersion, @PathParam("workspaceId") String workspaceId)
    {
        LegendSDLCServerException.validateNonNull(patchReleaseVersion, "patchReleaseVersion may not be null");
        return executeWithLogging(
                "creating new group workspace " + workspaceId + " for patch release " + patchReleaseVersion + " for project " + projectId,
                () -> this.workspaceApi.newWorkspace(projectId,
                        patchReleaseVersion,
                        workspaceId,
                        WorkspaceType.GROUP)

        );
    }

    @DELETE
    @Path("{workspaceId}")
    @ApiOperation("Delete a group workspace for patch release version")
    public void deleteGroupWorkspace(@PathParam("projectId") String projectId, @PathParam("patchReleaseVersion") String patchReleaseVersion, @PathParam("workspaceId") String workspaceId)
    {
        LegendSDLCServerException.validateNonNull(patchReleaseVersion, "patchReleaseVersion may not be null");
        executeWithLogging(
                "deleting group workspace " + workspaceId + " for patch release " + patchReleaseVersion + " for project " + projectId,
                () -> this.workspaceApi.deleteWorkspace(projectId, patchReleaseVersion, workspaceId, WorkspaceType.GROUP)
        );
    }

    @POST
    @Path("{workspaceId}/update")
    @ApiOperation("Update a group workspace for patch release version")
    public WorkspaceApi.WorkspaceUpdateReport updateGroupWorkspace(@PathParam("projectId") String projectId, @PathParam("patchReleaseVersion") String patchReleaseVersion, @PathParam("workspaceId") String workspaceId)
    {
        LegendSDLCServerException.validateNonNull(patchReleaseVersion, "patchReleaseVersion may not be null");
        return executeWithLogging(
                "updating user workspace " + workspaceId + " in project " + projectId + " for patch release version " + patchReleaseVersion + " to latest revision",
                () -> this.workspaceApi.updateWorkspace(projectId, patchReleaseVersion, workspaceId, WorkspaceType.GROUP)
        );
    }
}