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

package org.finos.legend.sdlc.server.resources.comparison.patch;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.finos.legend.sdlc.domain.model.project.configuration.ProjectConfiguration;
import org.finos.legend.sdlc.server.domain.api.project.ProjectConfigurationApi;
import org.finos.legend.sdlc.server.error.LegendSDLCServerException;
import org.finos.legend.sdlc.server.resources.EntityAccessResource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/projects/{projectId}/patches/{patchReleaseVersion}/reviews/{reviewId}/comparison")
@Api("Comparison")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ComparisonPatchReviewProjectConfigurationResource extends EntityAccessResource
{
    private final ProjectConfigurationApi projectConfigurationApi;

    @Inject
    public ComparisonPatchReviewProjectConfigurationResource(ProjectConfigurationApi projectConfigurationApi)
    {
        this.projectConfigurationApi = projectConfigurationApi;
    }

    @GET
    @Path("from/configuration")
    @ApiOperation("Get [from] project configuration for a given review for patch release version")
    public ProjectConfiguration getReviewFromProjectConfiguration(@PathParam("projectId") String projectId, @PathParam("patchReleaseVersion") String patchReleaseVersion, @PathParam("reviewId") String reviewId)
    {
        LegendSDLCServerException.validateNonNull(patchReleaseVersion, "patchReleaseVersion may not be null");
        return executeWithLogging(
                "getting [from] project configuration for review " + reviewId + " of project " + projectId + " for patch release version " + patchReleaseVersion,
                () -> this.projectConfigurationApi.getReviewFromProjectConfiguration(projectId, patchReleaseVersion, reviewId)
        );
    }

    @GET
    @Path("to/configuration")
    @ApiOperation("Get [to] project configuration for a given review for patch release version")
    public ProjectConfiguration getReviewToProjectConfiguration(@PathParam("projectId") String projectId, @PathParam("patchReleaseVersion") String patchReleaseVersion, @PathParam("reviewId") String reviewId)
    {
        LegendSDLCServerException.validateNonNull(patchReleaseVersion, "patchReleaseVersion may not be null");
        return executeWithLogging(
                "getting [to] project configuration for review " + reviewId + " of project " + projectId + " for patch release version " + patchReleaseVersion,
                () -> this.projectConfigurationApi.getReviewToProjectConfiguration(projectId, patchReleaseVersion, reviewId)
        );
    }

}
