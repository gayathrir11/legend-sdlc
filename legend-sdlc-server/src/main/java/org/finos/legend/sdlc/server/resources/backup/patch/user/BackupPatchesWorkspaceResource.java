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

package org.finos.legend.sdlc.server.resources.backup.patch.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.finos.legend.sdlc.domain.model.project.workspace.Workspace;
import org.finos.legend.sdlc.domain.model.project.workspace.WorkspaceType;
import org.finos.legend.sdlc.server.domain.api.backup.BackupApi;
import org.finos.legend.sdlc.server.domain.api.workspace.WorkspaceApi;
import org.finos.legend.sdlc.server.error.LegendSDLCServerException;
import org.finos.legend.sdlc.server.resources.BaseResource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/projects/{projectId}/patches/{patchReleaseVersion}/workspaces/{workspaceId}/backup")
@Api("Backup")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BackupPatchesWorkspaceResource extends BaseResource
{
    private final BackupApi backupApi;
    private final WorkspaceApi workspaceApi;

    @Inject
    public BackupPatchesWorkspaceResource(BackupApi backupApi, WorkspaceApi workspaceApi)
    {
        this.backupApi = backupApi;
        this.workspaceApi = workspaceApi;
    }

    @GET
    @ApiOperation("Get a backup user workspace by id for patch release version")
    public Workspace getUserWorkspace(@PathParam("projectId") String projectId, @PathParam("patchReleaseVersion") String patchReleaseVersion, @PathParam("workspaceId") String workspaceId)
    {
        LegendSDLCServerException.validateNonNull(patchReleaseVersion, "patchReleaseVersion may not be null");
        return executeWithLogging(
                "getting backup user workspace " + workspaceId + " for project " + projectId + " for patch release version " + patchReleaseVersion,
                () -> this.workspaceApi.getBackupWorkspace(projectId, patchReleaseVersion, workspaceId, WorkspaceType.USER)
        );
    }

    @GET
    @Path("outdated")
    @ApiOperation("Check if a backup user workspace is outdated for patch release version")
    public boolean isWorkspaceOutdated(@PathParam("projectId") String projectId, @PathParam("patchReleaseVersion") String patchReleaseVersion, @PathParam("workspaceId") String workspaceId)
    {
        LegendSDLCServerException.validateNonNull(patchReleaseVersion, "patchReleaseVersion may not be null");
        return executeWithLogging(
                "checking if backup user workspace " + workspaceId + " of project " + projectId + " for patch release version " + patchReleaseVersion + " is outdated",
                () -> this.workspaceApi.isBackupWorkspaceOutdated(projectId, patchReleaseVersion, workspaceId, WorkspaceType.USER)
        );
    }

    @DELETE
    @ApiOperation("Discard a backup user workspace for patch release version")
    public void discardBackup(@PathParam("projectId") String projectId, @PathParam("patchReleaseVersion") String patchReleaseVersion, @PathParam("workspaceId") String workspaceId)
    {
        LegendSDLCServerException.validateNonNull(patchReleaseVersion, "patchReleaseVersion may not be null");
        executeWithLogging(
                "discarding backup user workspace " + workspaceId + " in project " + projectId + " for patch release version " + patchReleaseVersion,
                () -> this.backupApi.discardBackupWorkspace(projectId, patchReleaseVersion, workspaceId, WorkspaceType.USER)
        );
    }

    @POST
    @Path("recover")
    @ApiOperation("Recover the user workspace from backup for patch release version")
    public void recoverBackup(@PathParam("projectId") String projectId,
                              @PathParam("patchReleaseVersion") String patchReleaseVersion,
                              @PathParam("workspaceId") String workspaceId,
                              @QueryParam("forceRecovery") @ApiParam("Whether to override the workspace if it exists with the backup") boolean forceRecovery)
    {
        LegendSDLCServerException.validateNonNull(patchReleaseVersion, "patchReleaseVersion may not be null");
        executeWithLogging(
                forceRecovery ? "force " : "" + "recovering user workspace " + workspaceId + " from backup in project " + projectId + " for patch release version " + patchReleaseVersion,
                () -> this.backupApi.recoverBackupWorkspace(projectId, patchReleaseVersion, workspaceId, WorkspaceType.USER, forceRecovery)
        );
    }
}
