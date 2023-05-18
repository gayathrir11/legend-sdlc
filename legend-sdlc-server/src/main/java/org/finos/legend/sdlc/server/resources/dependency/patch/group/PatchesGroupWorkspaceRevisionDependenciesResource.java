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

package org.finos.legend.sdlc.server.resources.dependency.patch.group;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.finos.legend.sdlc.domain.model.project.configuration.ProjectDependency;
import org.finos.legend.sdlc.domain.model.version.VersionId;
import org.finos.legend.sdlc.server.domain.api.dependency.DependenciesApi;
import org.finos.legend.sdlc.server.domain.api.workspace.WorkspaceSpecification;
import org.finos.legend.sdlc.server.error.LegendSDLCServerException;
import org.finos.legend.sdlc.server.resources.BaseResource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

@Path("/projects/{projectId}/patches/{patchReleaseVersionId}/groupWorkspaces/{workspaceId}/revisions/{revisionId}/upstreamProjects")
@Api("Dependencies")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PatchesGroupWorkspaceRevisionDependenciesResource extends BaseResource
{
    private final DependenciesApi dependenciesApi;

    @Inject
    public PatchesGroupWorkspaceRevisionDependenciesResource(DependenciesApi dependenciesApi)
    {
        this.dependenciesApi = dependenciesApi;
    }

    @GET
    @ApiOperation("Get projects that the current group workspace revision depends on. Use transitive=true for transitive dependencies for patch release version.")
    public Set<ProjectDependency> getUpstreamDependencies(@PathParam("projectId") String projectId,
                                                          @PathParam("patchReleaseVersionId") String patchReleaseVersionId,
                                                          @PathParam("workspaceId") String workspaceId,
                                                          @PathParam("revisionId") String revisionId,
                                                          @QueryParam("transitive") @DefaultValue("false") boolean transitive)
    {
        LegendSDLCServerException.validateNonNull(patchReleaseVersionId, "patchReleaseVersionId may not be null");
        VersionId versionId;
        try
        {
            versionId = VersionId.parseVersionId(patchReleaseVersionId);
        }
        catch (IllegalArgumentException e)
        {
            throw new LegendSDLCServerException(e.getMessage(), Response.Status.BAD_REQUEST, e);
        }
        return executeWithLogging(
                "getting upstream dependencies of project " + projectId + " for patch release version " + patchReleaseVersionId + ", group workspace " + workspaceId + ", revision " + revisionId + " (fetch transitively = " + transitive + ")",
                () -> this.dependenciesApi.getWorkspaceRevisionUpstreamProjects(projectId, WorkspaceSpecification.newGroupWorkspaceSpecification(workspaceId, versionId), revisionId, transitive)
        );
    }
}
